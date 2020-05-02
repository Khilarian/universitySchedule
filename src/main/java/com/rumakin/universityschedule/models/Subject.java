package com.rumakin.universityschedule.models;

import java.util.List;

import javax.persistence.*;

@Entity
@Table
public class Subject implements ModelEntity {

    @Id
    @Column(name = "subject_id")
    private int id;
    
    @Column(name = "subject_name")
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;
    
    @OneToMany(mappedBy = "subject")
    List<Lesson> lessons;

    public Subject() {
    }

    public Subject(String name, Faculty faculty) {
        this.name = name;
        this.faculty = faculty;
    }

    public Subject(int id, String name, Faculty faculty) {
        this.id = id;
        this.name = name;
        this.faculty = faculty;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Subject other = (Subject) obj;
        if (faculty == null) {
            if (other.faculty != null) return false;
        } else if (!faculty.equals(other.faculty)) return false;
        if (id != other.id) return false;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Subject [id=" + id + ", name=" + name + ", faculty=" + faculty + "]";
    }

}
