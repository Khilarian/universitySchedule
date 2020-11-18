package com.rumakin.universityschedule.model;

import java.util.*;

import javax.persistence.*;

import java.time.*;

@Entity
@Table
public class Lesson implements ModelEntity {

    @Id
    @Column(name = "lesson_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_type_id")
    private LessonType lessonType;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_slot_id")
    private TimeSlot timeSlot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auditorium_id")
    private Auditorium auditorium;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "lesson_teacher", joinColumns = @JoinColumn(name = "lesson_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    private Set<Teacher> teachers = new HashSet<>();;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "lesson_group", joinColumns = @JoinColumn(name = "lesson_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups = new HashSet<>();

    public Lesson() {
    }

    @Override
    public int getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public LessonType getLessonType() {
        return lessonType;
    }

    public LocalDate getDate() {
        return date;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setLessonType(LessonType lessonType) {
        this.lessonType = lessonType;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Lesson other = (Lesson) obj;
        return id == other.id;
    }

    @Override
    public String toString() {
        return "Lesson [id=" + id + ", course=" + course + ", lessonType=" + lessonType + ", date=" + date
                + ", timeSlot=" + timeSlot + ", auditorium=" + auditorium + ", teachers=" + teachers + ", groups="
                + groups + "]";
    }

}
