package com.rumakin.universityschedule.models;

public class TermPlan {

    private int id;
    private final Term term;
    private final Curriculum curriculum;

    public TermPlan(Term term, Curriculum curriculum) {
        this.term = term;
        this.curriculum = curriculum;
    }

    public TermPlan(int id, Term term, Curriculum curriculum) {
        this.id = id;
        this.term = term;
        this.curriculum = curriculum;
    }

    public int getUuid() {
        return id;
    }

    public Term getTerm() {
        return term;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((curriculum == null) ? 0 : curriculum.hashCode());
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
        if (curriculum == null) {
            if (other.curriculum != null)
                return false;
        } else if (!curriculum.equals(other.curriculum))
            return false;
        if (term != other.term)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TermPlan [id=" + id + ", term=" + term + ", curriculum=" + curriculum + "]";
    }

}
