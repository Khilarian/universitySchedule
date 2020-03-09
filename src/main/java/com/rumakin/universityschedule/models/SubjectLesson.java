package com.rumakin.universityschedule.models;

public class SubjectLesson {

    private Subject subject;
    private LessonType type;

    public SubjectLesson(Subject subject, LessonType type) {
        this.subject = subject;
        this.type = type;
    }

    public Subject getSubject() {
        return subject;
    }

    public LessonType getType() {
        return type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
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
        if (subject == null) {
            if (other.subject != null)
                return false;
        } else if (!subject.equals(other.subject))
            return false;
        return type.equals(other.type);
    }

    @Override
    public String toString() {
        return "SubjectLesson [subject=" + subject + ", type=" + type + "]";
    }

}
