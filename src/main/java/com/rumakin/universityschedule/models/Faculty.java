package com.rumakin.universityschedule.models;

import java.util.*;

public class Faculty {

    private String name;
    private Office office;
    private Set<Department> departments = new HashSet<>();

    public Faculty(String name, Office office) {
        this.name = name;
        this.office = office;
    }

    public Faculty(String name, Office office, Set<Department> departments) {
        this.name = name;
        this.office = office;
        this.departments = departments;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    public void addDepartment(Department department) {
        departments.add(department);
    }

    public void deleteDepartment(Department department) {
        if (departments.contains(department)) {
            departments.remove(department);
        }
    }

    public String getName() {
        return name;
    }

    public Office getOffice() {
        return office;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((departments == null) ? 0 : departments.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((office == null) ? 0 : office.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Faculty other = (Faculty) obj;
        if (departments == null) {
            if (other.departments != null)
                return false;
        } else if (!departments.equals(other.departments))
            return false;
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
        return true;
    }

    @Override
    public String toString() {
        return "Faculty [name=" + name + ", office=" + office + ", departments=" + departments + "]";
    }

}
