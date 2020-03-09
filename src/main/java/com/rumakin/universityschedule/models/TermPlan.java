package com.rumakin.universityschedule.models;

import java.util.*;

public class TermPlan {

    private final Term term;
    private List<SubjectPlan> subjectPlans;

    public TermPlan(Term term, List<SubjectPlan> subjectPlan) {
        this.term = term;
        this.subjectPlans = subjectPlan;
    }

    public Term getTerm() {
        return term;
    }

    public List<SubjectPlan> getSubjectPlans() {
        return subjectPlans;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((subjectPlans == null) ? 0 : subjectPlans.hashCode());
        result = prime * result + ((term == null) ? 0 : term.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        TermPlan other = (TermPlan) obj;
        if (subjectPlans == null) {
            if (other.subjectPlans != null)
                return false;
        } else if (!subjectPlans.equals(other.subjectPlans))
            return false;
        if (term != other.term)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TermPlan [term=" + term + ", subjectPlans=" + subjectPlans + "]";
    }

}
