package com.rumakin.universityschedule.models;

import java.util.*;

public class Teacher extends Person {

    private AcademicDegree degree;
    private AcademicRank rank;
    private List<Subject> subjects;

    public Teacher(String name, String surname) {
        super(name, surname);
    }

    public Teacher(String name, String surname, Integer id) {
        super(name, surname, id);
    }

    public void setDegree(AcademicDegree degree) {
        this.degree = degree;
    }

    public void setRank(AcademicRank rank) {
        this.rank = rank;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public AcademicDegree getDegree() {
        return degree;
    }

    public AcademicRank getRank() {
        return rank;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj) || getClass() != obj.getClass())
            return false;
        Teacher other = (Teacher) obj;
        if (degree != other.degree)
            return false;
        if (rank != other.rank)
            return false;
        if (subjects == null) {
            if (other.subjects != null)
                return false;
        } else if (!subjects.equals(other.subjects))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((degree == null) ? 0 : degree.hashCode());
        result = prime * result + ((rank == null) ? 0 : rank.hashCode());
        result = prime * result + ((subjects == null) ? 0 : subjects.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Teacher [id=" + getId() + ", name=" + getName() + ", surname=" + getSurname() + ", degree=" + degree
                + ", rank=" + rank + ", subjects=" + subjects + "]";
    }

}
