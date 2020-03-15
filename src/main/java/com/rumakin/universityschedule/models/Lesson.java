package com.rumakin.universityschedule.models;

import java.util.*;
import java.time.*;

public class Lesson {

    private int id;
    private final SubjectLesson subjectLesson;
    private Set<Teacher> teachers;
    private Auditorium auditorium;
    private Set<Group> groups;
    private LocalDate date;
    private TimeSlot timeSlot;

    public Lesson(SubjectLesson subjectLesson, Set<Teacher> teachers, Auditorium auditorium,
            Set<Group> groups, LocalDate date, TimeSlot timeSlot) {
        this.subjectLesson = subjectLesson;
        this.teachers = teachers;
        this.auditorium = auditorium;
        this.groups = groups;
        this.date = date;
        this.timeSlot = timeSlot;
    }

    public Lesson(int id, SubjectLesson subjectLesson, Set<Teacher> teachers, Auditorium auditorium,
            Set<Group> groups, LocalDate date, TimeSlot timeSlot) {
        this.id = id;
        this.subjectLesson = subjectLesson;
        this.teachers = teachers;
        this.auditorium = auditorium;
        this.groups = groups;
        this.date = date;
        this.timeSlot = timeSlot;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public int getId() {
        return id;
    }

    public SubjectLesson getSubjectLesson() {
        return subjectLesson;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public Set<Group> getGroups() {
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
        result = prime * result + ((subjectLesson == null) ? 0 : subjectLesson.hashCode());
        result = prime * result + ((teachers == null) ? 0 : teachers.hashCode());
        result = prime * result + ((timeSlot == null) ? 0 : timeSlot.hashCode());
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
        if (subjectLesson == null) {
            if (other.subjectLesson != null)
                return false;
        } else if (!subjectLesson.equals(other.subjectLesson))
            return false;
        if (teachers == null) {
            if (other.teachers != null)
                return false;
        } else if (!teachers.equals(other.teachers))
            return false;
        return timeSlot == other.timeSlot;
    }

    @Override
    public String toString() {
        return "Lesson [subjectLesson=" + subjectLesson + ", teachers=" + teachers + ", auditorium=" + auditorium
                + ", groups=" + groups + ", date=" + date + ", timeSlot=" + timeSlot + "]";
    }

}
