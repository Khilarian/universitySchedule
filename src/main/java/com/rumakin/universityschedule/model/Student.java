package com.rumakin.universityschedule.model;

import javax.persistence.*;

@Entity
@Table(name = "student")
public class Student extends Person {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public Student() {
    }

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public Student(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    public Student(String firstName, String lastName, Group group) {
        super(firstName, lastName);
        this.group = group;
    }

    public Student(int id, String name, String surname, Group group) {
        super(id, name, surname);
        this.group = group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
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
        if (this == obj) return true;
        if (!super.equals(obj) || getClass() != obj.getClass()) return false;
        Student other = (Student) obj;
        if (group == null) {
            if (other.group != null) return false;
        } else if (!group.equals(other.group)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Student [id=" + getId() + ", getName()=" + getFirstName() + ", getSurname()=" + getLastName() + "]";
    }

}
