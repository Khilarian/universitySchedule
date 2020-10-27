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
    
    public Student(String firstName, String lastName, String email, String phone, Group group) {
        super(firstName, lastName, email, phone);
        this.group = group;
    }

    public Student(int id, String firstName, String lastName, String email, String phone, Group group) {
        super(id, firstName, lastName, email, phone);
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
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Student [id=" + getId() + ", getName()=" + getFirstName() + ", getSurname()=" + getLastName() + "]";
    }

}
