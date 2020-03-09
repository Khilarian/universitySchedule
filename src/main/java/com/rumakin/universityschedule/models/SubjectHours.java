package com.rumakin.universityschedule.models;

import java.util.*;

public class SubjectHours {

    private static final int ACADEMIC_HOURS_IN_SUBJECT_LESSON = 2;
    private List<SubjectLesson> subjectLessons = new ArrayList<>();
    private int academicHours;

    public SubjectHours(List<SubjectLesson> subjectLessons) {
        this.subjectLessons = subjectLessons;
        this.academicHours = this.subjectLessons.size() * ACADEMIC_HOURS_IN_SUBJECT_LESSON;
    }

    public List<SubjectLesson> getSubjectLessons() {
        return subjectLessons;
    }

    public int getAcademicHours() {
        return academicHours;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + academicHours;
        result = prime * result + ((subjectLessons == null) ? 0 : subjectLessons.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        SubjectHours other = (SubjectHours) obj;
        if (academicHours != other.academicHours)
            return false;
        if (subjectLessons == null) {
            if (other.subjectLessons != null)
                return false;
        } else if (!subjectLessons.equals(other.subjectLessons))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SubjectHours [subjectLessons=" + subjectLessons + ", academicHours=" + academicHours + "]";
    }

}
