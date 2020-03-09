package com.rumakin.universityschedule.models;

import java.util.*;

public class Department {

    private String name;
    private Office office;
    private Set<Subject> subjects = new HashSet<>();
    private Set<Teacher> staff = new HashSet<>();

    public Department(String name, Office office) {
        this.name = name;
        this.office = office;
    }

    public Department(String name, Office office, Set<Subject> subjects) {
        super();
        this.name = name;
        this.office = office;
        this.subjects = subjects;
    }

    public Department(String name, Office office, Set<Subject> subjects, Set<Teacher> staff) {
        super();
        this.name = name;
        this.office = office;
        this.subjects = subjects;
        this.staff = staff;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public void setStaff(Set<Teacher> staff) {
        this.staff = staff;
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public void deleteSubject(Subject subject) {
        if (subjects.contains(subject)) {
            subjects.remove(subject);
        }
    }

    public void addTeacher(Teacher teacher) {
        staff.add(teacher);
    }

    public void deleteTeacher(Teacher teacher) {
        if (staff.contains(teacher)) {
            staff.remove(teacher);
        }
    }

    public String getName() {
        return name;
    }

    public Office getOffice() {
        return office;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public Set<Teacher> getStaff() {
        return staff;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((office == null) ? 0 : office.hashCode());
        result = prime * result + ((staff == null) ? 0 : staff.hashCode());
        result = prime * result + ((subjects == null) ? 0 : subjects.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Department other = (Department) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (office == null) {
            if (other.office != null)
                return false;
        } else if (!office.equals(other.office))
            return false;
        if (staff == null) {
            if (other.staff != null)
                return false;
        } else if (!staff.equals(other.staff))
            return false;
        if (subjects == null) {
            if (other.subjects != null)
                return false;
        } else if (!subjects.equals(other.subjects))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Department [name=" + name + ", office=" + office + ", subjects=" + subjects + ", staff=" + staff + "]";
    }

}
