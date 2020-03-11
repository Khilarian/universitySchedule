package com.rumakin.universityschedule.models;

import java.util.*;

public class Speciality {

    private final String name;
    private List<Curriculum> curriculums;
    private final AcademicDegree academicDegree;

    public Speciality(String name, AcademicDegree academicDegree) {
        this.name = name;
        this.curriculums = new ArrayList<>();
        this.academicDegree = academicDegree;
    }

    public String getName() {
        return name;
    }

    public List<Curriculum> getCurriculums() {
        return curriculums;
    }

    public AcademicDegree getAcademicDegree() {
        return academicDegree;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((academicDegree == null) ? 0 : academicDegree.hashCode());
        result = prime * result + ((curriculums == null) ? 0 : curriculums.hashCode());
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
        if (curriculums == null) {
            if (other.curriculums != null)
                return false;
        } else if (!curriculums.equals(other.curriculums))
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
        return "Speciality [name=" + name + ", curriculums=" + curriculums + ", academicDegree=" + academicDegree + "]";
    }

}
