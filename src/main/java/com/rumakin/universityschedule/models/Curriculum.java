package com.rumakin.universityschedule.models;

import java.time.*;
import java.util.*;

public class Curriculum {

    private final LocalDate validFromYearOfRecept;
    private LocalDate validTillYearOfRecept;
    private Set<TermPlan> learningPath;

    public Curriculum(LocalDate validFromYearOfRecept, Set<TermPlan> learningPath) {
        this.validFromYearOfRecept = validFromYearOfRecept;
        this.learningPath = learningPath;
        this.learningPath = new HashSet<>();
    }

    public Curriculum(LocalDate validFromYearOfRecept, LocalDate validTillYearOfRecept, Set<TermPlan> learningPath) {
        this.validFromYearOfRecept = validFromYearOfRecept;
        this.validTillYearOfRecept = validTillYearOfRecept;
        this.learningPath = learningPath;
    }

    public void setValidTillYearOfRecept(LocalDate validTillYearOfRecept) {
        this.validTillYearOfRecept = validTillYearOfRecept;
    }

    public LocalDate getValidFromYearOfRecept() {
        return validFromYearOfRecept;
    }

    public LocalDate getValidTillYearOfRecept() {
        return validTillYearOfRecept;
    }

    public Set<TermPlan> getLearningPath() {
        return learningPath;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((learningPath == null) ? 0 : learningPath.hashCode());
        result = prime * result + ((validFromYearOfRecept == null) ? 0 : validFromYearOfRecept.hashCode());
        result = prime * result + ((validTillYearOfRecept == null) ? 0 : validTillYearOfRecept.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Curriculum other = (Curriculum) obj;
        if (learningPath == null) {
            if (other.learningPath != null)
                return false;
        } else if (!learningPath.equals(other.learningPath))
            return false;
        if (validFromYearOfRecept == null) {
            if (other.validFromYearOfRecept != null)
                return false;
        } else if (!validFromYearOfRecept.equals(other.validFromYearOfRecept))
            return false;
        if (validTillYearOfRecept == null) {
            if (other.validTillYearOfRecept != null)
                return false;
        } else if (!validTillYearOfRecept.equals(other.validTillYearOfRecept))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Curriculum [validFromYearOfRecept=" + validFromYearOfRecept + ", validTillYearOfRecept="
                + validTillYearOfRecept + ", learningPath=" + learningPath + "]";
    }

}
