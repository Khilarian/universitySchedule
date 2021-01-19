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

    private static final String TIME_SLOT="timeSlot";
    private static final String AUDITORIUM="auditorium";
    private static final String GROUPS="groups";
    private static final String TEACHERS="teachers";
    
    private static final Logger logger = LoggerFactory.getLogger(CustomizedLessonDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean isAuditoriumFree(int auditoriumId, Integer lessonId, LocalDate date, int timeSlotId) {
        logger.debug("isAuditoriumFree() with agruments {}, {}, {}, {}.", auditoriumId, lessonId, date, timeSlotId);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Boolean> query = criteriaBuilder.createQuery(Boolean.class);
        Root<Lesson> root = query.from(Lesson.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<Lesson, Auditorium> auditoriumJoin = root.join(AUDITORIUM, JoinType.LEFT);
        predicates.add(criteriaBuilder.equal(auditoriumJoin.get("id"), auditoriumId));
        Join<Lesson, TimeSlot> timeSlotJoin = root.join(TIME_SLOT, JoinType.LEFT);
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
    public Set<Integer> getBusyGroupsId(Integer lessonId, LocalDate date, int timeSlotId) {
        logger.debug("getBusyGroupsId() with agruments {}, {}, {}.", lessonId, date, timeSlotId);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> query = criteriaBuilder.createQuery(Integer.class);
        Root<Lesson> root = query.from(Lesson.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<Lesson, TimeSlot> timeSlotJoin = root.join(TIME_SLOT, JoinType.LEFT);
        predicates.add(criteriaBuilder.equal(timeSlotJoin.get("id"), timeSlotId));
        predicates.add(criteriaBuilder.equal(root.get("date"), date));
        if (nonNull(lessonId)) {
            predicates.add(criteriaBuilder.notEqual(root.get("id"), lessonId));
        }
        query.where(predicates.toArray(new Predicate[] {}));
        SetJoin<Lesson, Group> joinGroup = root.joinSet(GROUPS);
        query.multiselect(joinGroup.get("id"));
        TypedQuery<Integer> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultStream().collect(Collectors.toSet());
    }

    @Override
    public Set<Integer> getBusyTeachersId(Integer lessonId, LocalDate date, int timeSlotId) {
        logger.debug("getBusyTeachersId() with agruments {}, {}, {}.", lessonId, date, timeSlotId);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> query = criteriaBuilder.createQuery(Integer.class);
        Root<Lesson> root = query.from(Lesson.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<Lesson, TimeSlot> timeSlotJoin = root.join(TIME_SLOT, JoinType.LEFT);
        predicates.add(criteriaBuilder.equal(timeSlotJoin.get("id"), timeSlotId));
        predicates.add(criteriaBuilder.equal(root.get("date"), date));
        if (nonNull(lessonId)) {
            predicates.add(criteriaBuilder.notEqual(root.get("id"), lessonId));
        }
        query.where(predicates.toArray(new Predicate[] {}));
        SetJoin<Lesson, Teacher> joinTeacher = root.joinSet(TEACHERS);
        query.multiselect(joinTeacher.get("id"));
        TypedQuery<Integer> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultStream().collect(Collectors.toSet());
    }

    @Override
    public List<LessonDto> getMonthScheduleForTeacher(Integer teacherId, LocalDate startDate, LocalDate endDate) {
        logger.debug("findMonthScheduleForTeacher() with agruments {}, {}, {}.", teacherId, startDate, endDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<LessonDto> query = criteriaBuilder.createQuery(LessonDto.class);
        Root<Lesson> root = query.from(Lesson.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<Lesson, Teacher> teacherJoin = root.join(TEACHERS, JoinType.LEFT);
        predicates.add(criteriaBuilder.equal(teacherJoin.get("id"), teacherId));
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), startDate));
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), endDate));
        query.where(predicates.toArray(new Predicate[] {}));
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(criteriaBuilder, root));
        TypedQuery<LessonDto> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultStream().collect(Collectors.toList());
    }

    @Override
    public List<LessonDto> getMonthScheduleForGroup(Integer groupId, LocalDate startDate, LocalDate endDate) {
        logger.debug("findMonthScheduleForGroup() with agruments {}, {}, {}.", groupId, startDate, endDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<LessonDto> query = criteriaBuilder.createQuery(LessonDto.class);
        Root<Lesson> root = query.from(Lesson.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<Lesson, Group> groupJoin = root.join(GROUPS, JoinType.LEFT);
        predicates.add(criteriaBuilder.equal(groupJoin.get("id"), groupId));
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), startDate));
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), endDate));
        query.where(predicates.toArray(new Predicate[] {}));
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(criteriaBuilder, root));
        TypedQuery<LessonDto> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultStream().collect(Collectors.toList());
    }

    @Override
    public List<LessonDto> getDayScheduleForTeacher(Integer teacherId, LocalDate date) {
        logger.debug("findDayScheduleForTeacher() with agruments {}, {}.", teacherId, date);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<LessonDto> query = criteriaBuilder.createQuery(LessonDto.class);
        Root<Lesson> root = query.from(Lesson.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<Lesson, Teacher> teacherJoin = root.join(TEACHERS, JoinType.LEFT);
        predicates.add(criteriaBuilder.equal(teacherJoin.get("id"), teacherId));
        predicates.add(criteriaBuilder.equal(root.get("date"), date));
        query.where(predicates.toArray(new Predicate[] {}));
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(criteriaBuilder, root));
        TypedQuery<LessonDto> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultStream().collect(Collectors.toList());
    }

    @Override
    public List<LessonDto> getDayScheduleForGroup(Integer groupId, LocalDate date) {
        logger.debug("findDayScheduleForGroup() with agruments {}, {}.", groupId, date);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<LessonDto> query = criteriaBuilder.createQuery(LessonDto.class);
        Root<Lesson> root = query.from(Lesson.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<Lesson, Group> groupJoin = root.join(GROUPS, JoinType.LEFT);
        predicates.add(criteriaBuilder.equal(groupJoin.get("id"), groupId));
        predicates.add(criteriaBuilder.equal(root.get("date"), date));
        query.where(predicates.toArray(new Predicate[] {}));
        query.multiselect(buildMultiSelect(root));
        query.orderBy(getOrderList(criteriaBuilder, root));
        TypedQuery<LessonDto> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultStream().collect(Collectors.toList());
    }

    private List<Selection<?>> buildMultiSelect(Root<Lesson> root) {
        Join<Lesson, Course> joinCourse = root.join("course", JoinType.LEFT);
        Join<Lesson, Auditorium> joinAuditorium = root.join(AUDITORIUM, JoinType.LEFT);
        Join<Lesson, TimeSlot> joinTimeSlot = root.join(TIME_SLOT, JoinType.LEFT);
        Join<Lesson, LessonType> joinLessonType = root.join("lessonType", JoinType.LEFT);
        Path<Building> joinBuilding = root.join(AUDITORIUM).get("building");
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
        orderList.add(criteriaBuilder.asc(root.get(TIME_SLOT)));
        return orderList;
    }


}
