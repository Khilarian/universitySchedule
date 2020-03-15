package com.rumakin.universityschedule.models;

public class SubjectPlan {

    private int id;
    private final TermPlan termPlan;

    public SubjectPlan(TermPlan termPlan) {
        this.termPlan = termPlan;
    }

    public SubjectPlan(int id, TermPlan termPlan) {
        this.id = id;
        this.termPlan = termPlan;
    }

    public int getId() {
        return id;
    }

    public TermPlan getTermPlan() {
        return termPlan;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((termPlan == null) ? 0 : termPlan.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        SubjectPlan other = (SubjectPlan) obj;
        if (id != other.id)
            return false;
        if (termPlan == null) {
            if (other.termPlan != null)
                return false;
        } else if (!termPlan.equals(other.termPlan))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SubjectPlan [id=" + id + ", termPlan=" + termPlan + "]";
    }

}
