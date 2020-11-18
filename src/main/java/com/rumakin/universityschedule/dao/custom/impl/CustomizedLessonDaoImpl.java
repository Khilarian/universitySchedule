package com.rumakin.universityschedule.dao.custom.impl;

import java.time.*;

import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.persistence.criteria.*;

import org.slf4j.*;

import com.rumakin.universityschedule.dao.custom.CustomizedLessonDao;
import com.rumakin.universityschedule.model.*;
import static java.util.Objects.nonNull;

public class CustomizedLessonDaoImpl implements CustomizedLessonDao {

    private static final Logger logger = LoggerFactory.getLogger(CustomizedLessonDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean isAuditoriumFree(int auditoriumId, int lessonId, LocalDate date, int timeSlotId) {
        logger.debug("isAuditoriumFree() with agruments {}, {}, {}, {}.", auditoriumId, lessonId, date, timeSlotId);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Boolean> query = criteriaBuilder.createQuery(Boolean.class);
        Root<Lesson> root = query.from(Lesson.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<Lesson, Auditorium> auditoriumJoin = root.join("auditorium", JoinType.LEFT);
        predicates.add(criteriaBuilder.equal(auditoriumJoin.get("id"), auditoriumId));
        Join<Lesson, TimeSlot> timeSlotJoin = root.join("timeSlot", JoinType.LEFT);
        predicates.add(criteriaBuilder.equal(timeSlotJoin.get("id"), timeSlotId));
        predicates.add(criteriaBuilder.equal(root.get("date"), date));
        if (nonNull(lessonId)) {
            predicates.add(criteriaBuilder.notEqual(root.get("id"), lessonId));
        }
        query.where(predicates.toArray(new Predicate[] {}));
        query.select(criteriaBuilder.equal(criteriaBuilder.count(root), 0));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public Set<Integer> getBusyGroupsId(int lessonId, LocalDate date, int timeSlotId) {
        logger.debug(" getBusyGroupsId() with agruments {}, {}, {}.", lessonId, date, timeSlotId);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> query = criteriaBuilder.createQuery(Integer.class);
        Root<Lesson> root = query.from(Lesson.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<Lesson, TimeSlot> timeSlotJoin = root.join("timeSlot", JoinType.LEFT);
        predicates.add(criteriaBuilder.equal(timeSlotJoin.get("id"), timeSlotId));
        predicates.add(criteriaBuilder.equal(root.get("date"), date));
        if (nonNull(lessonId)) {
            predicates.add(criteriaBuilder.notEqual(root.get("id"), lessonId));
        }
        query.where(predicates.toArray(new Predicate[] {}));
        SetJoin<Lesson, Group> joinGroup = root.joinSet("groups");
        query.multiselect(joinGroup.get("id"));
        TypedQuery<Integer> result = entityManager.createQuery(query);
        return result.getResultStream().collect(Collectors.toSet());
    }

    @Override
    public Set<Integer> getBusyTeachersId(int lessonId, LocalDate date, int timeSlotId) {
        logger.debug(" getBusyTeachersId() with agruments {}, {}, {}.", lessonId, date, timeSlotId);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> query = criteriaBuilder.createQuery(Integer.class);
        Root<Lesson> root = query.from(Lesson.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<Lesson, TimeSlot> timeSlotJoin = root.join("timeSlot", JoinType.LEFT);
        predicates.add(criteriaBuilder.equal(timeSlotJoin.get("id"), timeSlotId));
        predicates.add(criteriaBuilder.equal(root.get("date"), date));
        if (nonNull(lessonId)) {
            predicates.add(criteriaBuilder.notEqual(root.get("id"), lessonId));
        }
        query.where(predicates.toArray(new Predicate[] {}));
        SetJoin<Lesson, Teacher> joinTeacher = root.joinSet("teachers");
        query.multiselect(joinTeacher.get("id"));
        TypedQuery<Integer> result = entityManager.createQuery(query);
        return result.getResultStream().collect(Collectors.toSet());
    }

}