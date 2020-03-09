package com.rumakin.universityschedule.models;

public class Student extends Person {

    public Student(String name, String surname) {
        super(name, surname);
    }

    public Student(String name, String surname, Integer id) {
        super(name, surname, id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
        result = prime * result + ((this.getName() == null) ? 0 : this.getName().hashCode());
        result = prime * result + ((this.getSurname() == null) ? 0 : this.getSurname().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        if (this.getId() == null) {
            if (other.getId() != null)
                return false;
        } else if (!this.getId().equals(other.getId()))
            return false;
        if (this.getName() == null) {
            if (other.getName() != null)
                return false;
        } else if (!this.getName().equals(other.getName()))
            return false;
        if (this.getSurname() == null) {
            if (other.getSurname() != null)
                return false;
        } else if (!this.getSurname().equals(other.getSurname()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Student [getId()=" + getId() + ", getName()=" + getName() + ", getSurname()=" + getSurname() + "]";
    }

}
