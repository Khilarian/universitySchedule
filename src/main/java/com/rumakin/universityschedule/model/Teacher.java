package com.rumakin.universityschedule.model;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "teacher")
public class Teacher extends Person {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "teacher_course", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses;

    public Teacher() {
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public void setSubjects(List<Course> subjects) {
        this.courses = subjects;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public List<Course> getSubjects() {
        return courses;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj) || getClass() != obj.getClass()) return false;
        Teacher other = (Teacher) obj;
        if (faculty == null) {
            if (other.faculty != null) return false;
        } else if (!faculty.equals(other.faculty)) return false;
        if (courses == null) {
            if (other.courses != null) return false;
        } else if (!courses.equals(other.courses)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((faculty == null) ? 0 : faculty.hashCode());
        result = prime * result + ((courses == null) ? 0 : courses.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Teacher [id=" + getId() + ", firstName=" + getFirstName() + ", lastName=" + getLastName() + ", faculty="
                + faculty + ", courses=" + courses + "]";
    }

}
