package com.rumakin.universityschedule.models;

public class Group {

    private int id;
    private String name;
    private final Speciality speciality;
    private final CourseNumber course;
    private Schedule schedule;

    public Group(String name, Speciality speciality, CourseNumber course) {
        this.name = name;
        this.speciality = speciality;
        this.course = course;
    }

    public Group(int id, String name, Speciality speciality, CourseNumber course) {
        this.id = id;
        this.name = name;
        this.speciality = speciality;
        this.course = course;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public CourseNumber getCourse() {
        return course;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((course == null) ? 0 : course.hashCode());
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((schedule == null) ? 0 : schedule.hashCode());
        result = prime * result + ((speciality == null) ? 0 : speciality.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Group other = (Group) obj;
        if (course != other.course)
            return false;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (schedule == null) {
            if (other.schedule != null)
                return false;
        } else if (!schedule.equals(other.schedule))
            return false;
        if (speciality == null) {
            if (other.speciality != null)
                return false;
        } else if (!speciality.equals(other.speciality))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Group [id=" + id + ", name=" + name + ", speciality=" + speciality + ", course=" + course
                + ", schedule=" + schedule + "]";
    }

}
