package com.rumakin.universityschedule.models;

public class Speciality {

    private int id;
    private final String name;
    private final AcademicDegree academicDegree;

    public Speciality(String name, AcademicDegree academicDegree) {
        this.name = name;
        this.academicDegree = academicDegree;
    }

    public Speciality(int id, String name, AcademicDegree academicDegree) {
        this.id = id;
        this.name = name;
        this.academicDegree = academicDegree;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AcademicDegree getAcademicDegree() {
        return academicDegree;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((academicDegree == null) ? 0 : academicDegree.hashCode());
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Speciality other = (Speciality) obj;
        if (academicDegree != other.academicDegree)
            return false;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Speciality [id=" + id + ", name=" + name + ", academicDegree=" + academicDegree + "]";
    }

}
