package com.rumakin.universityschedule.models;

public class Faculty {

    private int id;
    private String name;
    private Office office;

    public Faculty(String name, Office office) {
        this.name = name;
        this.office = office;
    }

    public Faculty(int id, String name, Office office) {
        this.id = id;
        this.name = name;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((office == null) ? 0 : office.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null||getClass() != obj.getClass())
            return false;
        Faculty other = (Faculty) obj;
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
        return "Faculty [id=" + id + ", name=" + name + ", office=" + office + "]";
    }

}
