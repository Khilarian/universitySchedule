package com.rumakin.universityschedule.dao.custom.impl;

import java.time.*;

import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.persistence.criteria.*;

import org.slf4j.*;

import com.rumakin.universityschedule.dao.custom.CustomizedLessonDao;
import com.rumakin.universityschedule.dto.LessonDto;
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
        logger.debug("getBusyGroupsId() with agruments {}, {}, {}.", lessonId, date, timeSlotId);
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
        logger.debug("getBusyTeachersId() with agruments {}, {}, {}.", lessonId, date, timeSlotId);
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

    @Override
    public List<LessonDto> findMonthScheduleForTeacher(Integer teacherId, LocalDate startDate, LocalDate endDate) {
        logger.debug("findMonthScheduleForTeacher() with agruments {}, {}, {}.", teacherId, startDate, endDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<LessonDto> query = criteriaBuilder.createQuery(LessonDto.class);
        Root<Lesson> root = query.from(Lesson.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<Lesson, Teacher> teacherJoin = root.join("teachers", JoinType.LEFT);
        predicates.add(criteriaBuilder.equal(teacherJoin.get("id"), teacherId));
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), startDate));
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), endDate));
        query.where(predicates.toArray(new Predicate[] {}));
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(criteriaBuilder, root));
        TypedQuery<LessonDto> result = entityManager.createQuery(query);
        return result.getResultStream().collect(Collectors.toList());
    }

    @Override
    public List<LessonDto> findMonthScheduleForGroup(Integer groupId, LocalDate startDate, LocalDate endDate) {
        logger.debug("findMonthScheduleForGroup() with agruments {}, {}, {}.", groupId, startDate, endDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<LessonDto> query = criteriaBuilder.createQuery(LessonDto.class);
        Root<Lesson> root = query.from(Lesson.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<Lesson, Group> groupJoin = root.join("groups", JoinType.LEFT);
        predicates.add(criteriaBuilder.equal(groupJoin.get("id"), groupId));
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), startDate));
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), endDate));
        query.where(predicates.toArray(new Predicate[] {}));
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(criteriaBuilder, root));
        TypedQuery<LessonDto> result = entityManager.createQuery(query);
        return result.getResultStream().collect(Collectors.toList());
    }

    @Override
    public List<LessonDto> findDayScheduleForTeacher(Integer teacherId, LocalDate date) {
        logger.debug("findDayScheduleForTeacher() with agruments {}, {}.", teacherId, date);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<LessonDto> query = criteriaBuilder.createQuery(LessonDto.class);
        Root<Lesson> root = query.from(Lesson.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<Lesson, Teacher> teacherJoin = root.join("teachers", JoinType.LEFT);
        predicates.add(criteriaBuilder.equal(teacherJoin.get("id"), teacherId));
        predicates.add(criteriaBuilder.equal(root.get("date"), date));
        query.where(predicates.toArray(new Predicate[] {}));
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(criteriaBuilder, root));
        TypedQuery<LessonDto> result = entityManager.createQuery(query);
        return result.getResultStream().collect(Collectors.toList());
    }

    @Override
    public List<LessonDto> findDayScheduleForGroup(Integer groupId, LocalDate date) {
        logger.debug("findDayScheduleForGroup() with agruments {}, {}.", groupId, date);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<LessonDto> query = criteriaBuilder.createQuery(LessonDto.class);
        Root<Lesson> root = query.from(Lesson.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<Lesson, Group> groupJoin = root.join("groups", JoinType.LEFT);
        predicates.add(criteriaBuilder.equal(groupJoin.get("id"), groupId));
        predicates.add(criteriaBuilder.equal(root.get("date"), date));
        query.where(predicates.toArray(new Predicate[] {}));
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(criteriaBuilder, root));
        TypedQuery<LessonDto> result = entityManager.createQuery(query);
        return result.getResultStream().collect(Collectors.toList());
    }

    private List<Selection<?>> buildMultiSelectForLesson(Root<Lesson> root) {
        List<Selection<?>> selectList = buildMultiSelect(root);
        Join<Lesson, Teacher> joinTeacher = root.join("teachers", JoinType.LEFT);
        selectList.add(joinTeacher.get("id"));
        selectList.add(joinTeacher.get("firstName"));
        selectList.add(joinTeacher.get("lastName"));
        return selectList;
    }

    private List<Selection<?>> buildMultiSelectForGroup(Root<Lesson> root) {
        List<Selection<?>> selectList = buildMultiSelect(root);
        Join<Lesson, Group> joinGroup = root.join("groups", JoinType.LEFT);
        selectList.add(joinGroup.get("id"));
        selectList.add(joinGroup.get("name"));
        return selectList;
    }

    private List<Selection<?>> buildMultiSelect(Root<Lesson> root) {
        Join<Lesson, Course> joinCourse = root.join("course", JoinType.LEFT);
        Join<Lesson, Auditorium> joinAuditorium = root.join("auditorium", JoinType.LEFT);
        Join<Lesson, TimeSlot> joinTimeSlot = root.join("timeSlot", JoinType.LEFT);
        Join<Lesson, LessonType> joinLessonType = root.join("lessonType", JoinType.LEFT);
        Path<Building> joinBuilding = root.join("auditorium").get("building");
        List<Selection<?>> selectList = new ArrayList<>();
        selectList.add(root.get("id"));
        selectList.add(joinCourse.get("id"));
        selectList.add(joinCourse.get("name"));
        selectList.add(joinLessonType.get("id"));
        selectList.add(joinLessonType.get("name"));
        selectList.add(root.get("date"));
        selectList.add(joinTimeSlot.get("id"));
        selectList.add(joinTimeSlot.get("number"));
        selectList.add(joinTimeSlot.get("name"));
        selectList.add(joinTimeSlot.get("startTime"));
        selectList.add(joinTimeSlot.get("endTime"));
        selectList.add(joinAuditorium.get("id"));
        selectList.add(joinAuditorium.get("number"));
        selectList.add(joinBuilding.get("id"));
        selectList.add(joinBuilding.get("name"));
        return selectList;
    }

    private List<Order> getOrderList(CriteriaBuilder criteriaBuilder, Root<Lesson> root) {
        List<Order> orderList = new ArrayList<>();
        orderList.add(criteriaBuilder.asc(root.get("date")));
        orderList.add(criteriaBuilder.asc(root.get("timeSlot")));
        return orderList;
    }


}
