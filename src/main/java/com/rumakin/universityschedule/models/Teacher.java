package com.rumakin.universityschedule.models;

import java.util.*;

public class Teacher extends Person {

    private Faculty faculty;
    private List<Subject> subjects;

    public Teacher(String firstName, String lastName, Faculty faculty) {
        super(firstName, lastName);
        this.faculty = faculty;
    }

    public Teacher(int id, String firstName, String lastName, Faculty faculty) {
        super(id, firstName, lastName);
        this.faculty = faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Faculty getFaculty() {
        return faculty;
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
        if (faculty == null) {
            if (other.faculty != null)
                return false;
        } else if (!faculty.equals(other.faculty))
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
        result = prime * result + ((faculty == null) ? 0 : faculty.hashCode());
        result = prime * result + ((subjects == null) ? 0 : subjects.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Teacher [id=" + getId() + ", firstName=" + getFirstName() + ", lastName=" + getLastName()
                + ", faculty=" + faculty + ", subjects=" + subjects + "]";
    }

}
