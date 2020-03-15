package com.rumakin.universityschedule.models;

public class SubjectLesson {

    private int id;
    private Subject subject;
    private LessonType type;
    private SubjectPlan subjectPlan;

    public SubjectLesson(Subject subject, LessonType type, SubjectPlan subjectPlan) {
        this.subject = subject;
        this.type = type;
        this.subjectPlan = subjectPlan;
    }

    public SubjectLesson(int id, Subject subject, LessonType type, SubjectPlan subjectPlan) {
        this.id = id;
        this.subject = subject;
        this.type = type;
        this.subjectPlan = subjectPlan;
    }

    public int getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public LessonType getType() {
        return type;
    }

    public SubjectPlan getSubjectPlan() {
        return subjectPlan;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
        result = prime * result + ((subjectPlan == null) ? 0 : subjectPlan.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        SubjectLesson other = (SubjectLesson) obj;
        if (id != other.id)
            return false;
        if (subject == null) {
            if (other.subject != null)
                return false;
        } else if (!subject.equals(other.subject))
            return false;
        if (subjectPlan == null) {
            if (other.subjectPlan != null)
                return false;
        } else if (!subjectPlan.equals(other.subjectPlan))
            return false;
        return type == other.type;
    }

    @Override
    public String toString() {
        return "SubjectLesson [id=" + id + ", subject=" + subject + ", type=" + type + ", subjectPlan="
                + subjectPlan + "]";
    }

}
