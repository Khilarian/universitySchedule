package com.rumakin.universityschedule.models;

import java.util.*;
import java.time.*;

public class Lesson {

    private final SubjectLesson subjectLesson;
    private Teacher teacher;
    private Set<Teacher> assistants;
    private Auditorium auditorium;
    private Set<Group> groups;
    private LocalDate date;
    private TimeSlot timeSlot;

    public Lesson(SubjectLesson subjectLesson, Teacher teacher, Set<Teacher> assistants, Auditorium auditorium,
            Set<Group> groups, LocalDate date, TimeSlot timeSlot) {
        this.subjectLesson = subjectLesson;
        this.teacher = teacher;
        this.assistants = assistants;
        this.auditorium = auditorium;
        this.groups = groups;
        this.date = date;
        this.timeSlot = timeSlot;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setAssistants(Set<Teacher> assistants) {
        this.assistants = assistants;
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

    public SubjectLesson getSubjectLesson() {
        return subjectLesson;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Set<Teacher> getAssistants() {
        return assistants;
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
        result = prime * result + ((assistants == null) ? 0 : assistants.hashCode());
        result = prime * result + ((auditorium == null) ? 0 : auditorium.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((groups == null) ? 0 : groups.hashCode());
        result = prime * result + ((subjectLesson == null) ? 0 : subjectLesson.hashCode());
        result = prime * result + ((teacher == null) ? 0 : teacher.hashCode());
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
        if (assistants == null) {
            if (other.assistants != null)
                return false;
        } else if (!assistants.equals(other.assistants))
            return false;
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
        if (teacher == null) {
            if (other.teacher != null)
                return false;
        } else if (!teacher.equals(other.teacher))
            return false;
        if (timeSlot != other.timeSlot)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Lesson [subjectLesson=" + subjectLesson + ", teacher=" + teacher + ", assistants=" + assistants
                + ", auditorium=" + auditorium + ", groups=" + groups + ", date=" + date + ", timeSlot=" + timeSlot
                + "]";
    }

}
