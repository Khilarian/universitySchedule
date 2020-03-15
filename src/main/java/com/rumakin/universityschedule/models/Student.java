package com.rumakin.universityschedule.models;

public class Student extends Person {

    private Group group;

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public Student(String firstName, String lastName, Group group) {
        super(firstName, lastName);
        this.group = group;
    }

    public Student(int id, String name, String surname, Group group) {
        super(id, name, surname);
        this.group = group;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((group == null) ? 0 : group.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj) || getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        if (group == null) {
            if (other.group != null)
                return false;
        } else if (!group.equals(other.group))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Student [id=" + getId() + ", getName()=" + getFirstName() + ", getSurname()=" + getLastName() + "]";
    }

}
