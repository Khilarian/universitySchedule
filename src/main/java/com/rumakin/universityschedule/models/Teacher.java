package com.rumakin.universityschedule.models;

import java.util.*;

import com.rumakin.universityschedule.enums.AcademicDegree;

public class Teacher extends Person {

    private AcademicDegree degree;
    private Department department;
    private List<Subject> subjects;

    public Teacher(String firstName, String lastName, Department department) {
        super(firstName, lastName);
        this.department = department;
    }

    public Teacher(String firstName, String lastName, Department department, AcademicDegree degree) {
        super(firstName, lastName);
        this.department = department;
        this.degree = degree;
    }

    public Teacher(int id, String firstName, String lastName, Department department) {
        super(id, firstName, lastName);
        this.department = department;
    }

    public Teacher(int id, String firstName, String lastName, Department department, AcademicDegree degree) {
        super(id, firstName, lastName);
        this.department = department;
        this.degree = degree;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setDegree(AcademicDegree degree) {
        this.degree = degree;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Department getDepartment() {
        return department;
    }

    public AcademicDegree getDegree() {
        return degree;
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
        if (department == null) {
            if (other.department != null)
                return false;
        } else if (!department.equals(other.department))
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
        result = prime * result + ((department == null) ? 0 : department.hashCode());
        result = prime * result + ((subjects == null) ? 0 : subjects.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Teacher [id=" + getId() + ", firstName=" + getFirstName() + ", lastName=" + getLastName() + "degree="
                + degree + ", department=" + department + ", subjects=" + subjects + "]";
    }

}
