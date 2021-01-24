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
    
    public Teacher(String firstName, String lastName, String email, String phone, Faculty faculty) {
        super(firstName, lastName, email, phone);
        this.faculty = faculty;
    }
    
    public Teacher(int id, String firstName, String lastName, String email, String phone, Faculty faculty) {
        super(id, firstName, lastName, email, phone);
        this.faculty = faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public List<Course> getCourses() {
        return courses;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
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
