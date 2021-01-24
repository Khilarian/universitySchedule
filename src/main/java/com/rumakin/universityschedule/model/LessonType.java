package com.rumakin.universityschedule.model;

import javax.persistence.*;

@Entity
@Table
public class LessonType implements ModelEntity {

    @Id
    @Column(name = "lesson_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "lesson_type_name")
    private String name;

    public LessonType() {
    }

    public LessonType(String name) {
        this.name = name;
    }

    public LessonType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        LessonType other = (LessonType) obj;
        if (id != other.id) return false;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "LessonType [id=" + id + ", name=" + name + "]";
    }

}
