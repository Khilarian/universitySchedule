package com.rumakin.universityschedule.models;

public class Speciality {

    private final String name;
    private final Curriculum curriculum;
    private final AcademicDegree academicDegree;

    public Speciality(String name, Curriculum curriculum, AcademicDegree academicDegree) {
        this.name = name;
        this.curriculum = curriculum;
        this.academicDegree = academicDegree;
    }

    public String getName() {
        return name;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public AcademicDegree getAcademicDegree() {
        return academicDegree;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((academicDegree == null) ? 0 : academicDegree.hashCode());
        result = prime * result + ((curriculum == null) ? 0 : curriculum.hashCode());
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
        if (curriculum == null) {
            if (other.curriculum != null)
                return false;
        } else if (!curriculum.equals(other.curriculum))
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
        return "Speciality [name=" + name + ", curriculum=" + curriculum + ", academicDegree=" + academicDegree + "]";
    }

}
