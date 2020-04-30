package com.rumakin.universityschedule.models;

import java.util.*;

import javax.persistence.*;

import com.rumakin.universityschedule.models.enums.*;

import java.time.*;

@Entity
@Table
public class Lesson implements ModelEntity {

    @Id
    @Column(name = "lesson_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "subject_id")
    private Subject subject;
    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "lesson_type_id")
    private LessonType type;
    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "auditorium_id")
    private Auditorium auditorium;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "lesson_teacher", joinColumns = @JoinColumn(name = "lesson_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Teacher> teachers = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "lesson_group", joinColumns = @JoinColumn(name = "lesson_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<Group> groups = new ArrayList<>();
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "time_slot_id")
    private TimeSlot timeSlot;

    public Lesson() {
    }

    public Lesson(Subject subject, LessonType type, Auditorium auditorium, LocalDate date, TimeSlot timeSlot) {
        this.type = null;
        this.subject = subject;
        this.type = type;
        this.auditorium = auditorium;
        this.date = date;
        this.timeSlot = timeSlot;
    }

    public Lesson(int id, Subject subject, LessonType type, Auditorium auditorium, LocalDate date, TimeSlot timeSlot) {
        this.type = null;
        this.id = id;
        this.subject = subject;
        this.type = type;
        this.auditorium = auditorium;
        this.date = date;
        this.timeSlot = timeSlot;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setType(LessonType type) {
        this.type = type;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public int getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public LessonType getLessonType() {
        return type;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public List<Group> getGroups() {
        return groups;
    }

    @Temporal(TemporalType.DATE)
    public LocalDate getDate() {
        return date;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((auditorium == null) ? 0 : auditorium.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((groups == null) ? 0 : groups.hashCode());
        result = prime * result + id;
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
        result = prime * result + ((teachers == null) ? 0 : teachers.hashCode());
        result = prime * result + ((timeSlot == null) ? 0 : timeSlot.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Lesson other = (Lesson) obj;
        if (auditorium == null) {
            if (other.auditorium != null) return false;
        } else if (!auditorium.equals(other.auditorium)) return false;
        if (date == null) {
            if (other.date != null) return false;
        } else if (!date.equals(other.date)) return false;
        if (groups == null) {
            if (other.groups != null) return false;
        } else if (!groups.equals(other.groups)) return false;
        if (id != other.id) return false;
        if (subject == null) {
            if (other.subject != null) return false;
        } else if (!subject.equals(other.subject)) return false;
        if (teachers == null) {
            if (other.teachers != null) return false;
        } else if (!teachers.equals(other.teachers)) return false;
        if (timeSlot != other.timeSlot) return false;
        return type == other.type;
    }

    @Override
    public String toString() {
        return "Lesson [id=" + id + ", subject=" + subject + ", type=" + type + ", auditorium=" + auditorium
                + ", teachers=" + teachers + ", groups=" + groups + ", date=" + date + ", timeSlot=" + timeSlot + "]";
    }

}
