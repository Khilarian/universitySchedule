package com.rumakin.universityschedule.models;

import java.util.*;

public class Schedule {

    private List<Lesson> lessons;

    public Schedule(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lessons == null) ? 0 : lessons.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Schedule other = (Schedule) obj;
        if (lessons == null) {
            if (other.lessons != null)
                return false;
        } else if (!lessons.equals(other.lessons))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Schedule [lessons=" + lessons + "]";
    }

}
