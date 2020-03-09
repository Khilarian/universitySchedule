package com.rumakin.universityschedule.models;

import java.util.*;

public class Group {

    private final String name;
    private final Speciality speciality;
    private final CourseNumber course;
    private List<Student> students = new ArrayList<>();
    private Schedule schedule;

    public Group(String name, Speciality speciality, CourseNumber course) {
        this.name = name;
        this.speciality = speciality;
        this.course = course;
    }

    public Group(String name, Speciality speciality, CourseNumber course, List<Student> students) {
        this.name = name;
        this.speciality = speciality;
        this.course = course;
        this.students = students;
    }

    public Group(String name, Speciality speciality, CourseNumber course, List<Student> students, Schedule schedule) {
        this.name = name;
        this.speciality = speciality;
        this.course = course;
        this.students = students;
        this.schedule = schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Input sudent can't be null");
        }
        students.add(student);
    }

    public void deleteStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Input sudent can't be null");
        }
        if (students.contains(student)) {
            students.remove(student);
        }
    }

    public String getName() {
        return name;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public CourseNumber getCourse() {
        return course;
    }

    public List<Student> getStudents() {
        return students;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((course == null) ? 0 : course.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((schedule == null) ? 0 : schedule.hashCode());
        result = prime * result + ((speciality == null) ? 0 : speciality.hashCode());
        result = prime * result + ((students == null) ? 0 : students.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Group other = (Group) obj;
        if (course != other.course)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (schedule == null) {
            if (other.schedule != null)
                return false;
        } else if (!schedule.equals(other.schedule))
            return false;
        if (speciality == null) {
            if (other.speciality != null)
                return false;
        } else if (!speciality.equals(other.speciality))
            return false;
        if (students == null) {
            if (other.students != null)
                return false;
        } else if (!students.equals(other.students))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Group [name=" + name + ", speciality=" + speciality + ", course=" + course + ", students=" + students
                + ", schedule=" + schedule + "]";
    }

}
