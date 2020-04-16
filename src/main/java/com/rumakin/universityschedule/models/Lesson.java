package com.rumakin.universityschedule.models;

import java.util.*;

import com.rumakin.universityschedule.models.enums.*;

import java.time.*;

public class Lesson implements ModelEntity{

    private int id;
    private final Subject subject;
    private final LessonType type;
    private Auditorium auditorium;
    private List<Teacher> teachers = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
    private LocalDate date;
    private TimeSlot timeSlot;

    public Lesson(Subject subject, LessonType type, Auditorium auditorium, LocalDate date, TimeSlot timeSlot) {
        this.subject = subject;
        this.type = type;
        this.auditorium = auditorium;
        this.date = date;
        this.timeSlot = timeSlot;
    }

    public Lesson(int id, Subject subject, LessonType type, Auditorium auditorium, LocalDate date, TimeSlot timeSlot) {
        this.id = id;
        this.subject = subject;
        this.type = type;
        this.auditorium = auditorium;
        this.date = date;
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
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Lesson other = (Lesson) obj;
        if (auditorium == null) {
            if (other.auditorium != null)
                return false;
        } else if (!auditorium.equals(other.auditorium))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (groups == null) {
            if (other.groups != null)
                return false;
        } else if (!groups.equals(other.groups))
            return false;
        if (id != other.id)
            return false;
        if (subject == null) {
            if (other.subject != null)
                return false;
        } else if (!subject.equals(other.subject))
            return false;
        if (teachers == null) {
            if (other.teachers != null)
                return false;
        } else if (!teachers.equals(other.teachers))
            return false;
        if (timeSlot != other.timeSlot)
            return false;
        return type == other.type;
    }

    @Override
    public String toString() {
        return "Lesson [id=" + id + ", subject=" + subject + ", type=" + type + ", auditorium=" + auditorium
                + ", teachers=" + teachers + ", groups=" + groups + ", date=" + date + ", timeSlot=" + timeSlot + "]";
    }

}
