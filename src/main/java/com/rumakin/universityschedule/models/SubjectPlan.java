package com.rumakin.universityschedule.models;

import java.util.*;

public class SubjectPlan {

    private List<SubjectLesson> plan;

    public SubjectPlan(List<SubjectLesson> subjectHours) {
        this.plan = subjectHours;
    }

    public List<SubjectLesson> getPlan() {
        return plan;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((plan == null) ? 0 : plan.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        SubjectPlan other = (SubjectPlan) obj;
        if (plan == null) {
            if (other.plan != null)
                return false;
        } else if (!plan.equals(other.plan))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SubjectPlan [plan=" + plan + "]";
    }

}
