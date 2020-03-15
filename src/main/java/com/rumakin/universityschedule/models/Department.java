package com.rumakin.universityschedule.models;

public class Department {

    private int id;
    private String name;
    private Faculty faculty;
    private Office office;

    public Department(String name, Office office, Faculty faculty) {
        this.name = name;
        this.faculty = faculty;
        this.office = office;
    }

    public Department(int id, String name, Office office, Faculty faculty) {
        this.id = id;
        this.name = name;
        this.faculty = faculty;
        this.office = office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Office getOffice() {
        return office;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((faculty == null) ? 0 : faculty.hashCode());
        result = prime * result + id;
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
        Department other = (Department) obj;
        if (faculty == null) {
            if (other.faculty != null)
                return false;
        } else if (!faculty.equals(other.faculty))
            return false;
        if (id != other.id)
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
        return "Department [id=" + id + ", name=" + name + ", faculty=" + faculty + ", office=" + office + "]";
    }

}
