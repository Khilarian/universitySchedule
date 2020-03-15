package com.rumakin.universityschedule.models;

import java.time.*;

public class Curriculum {

    private int id;
    private final String name;
    private final Speciality speciality;
    private final LocalDate validFromYearOfRecept;
    private LocalDate validTillYearOfRecept;

    public Curriculum(String name, Speciality speciality, LocalDate validFromYearOfRecept) {
        this.name = name;
        this.speciality = speciality;
        this.validFromYearOfRecept = validFromYearOfRecept;
    }

    public Curriculum(String name, Speciality speciality, LocalDate validFromYearOfRecept,
            LocalDate validTillYearOfRecept) {
        this.name = name;
        this.speciality = speciality;
        this.validFromYearOfRecept = validFromYearOfRecept;
        this.validTillYearOfRecept = validTillYearOfRecept;
    }

    public Curriculum(int id, String name, Speciality speciality, LocalDate validFromYearOfRecept) {
        this.id = id;
        this.name = name;
        this.speciality = speciality;
        this.validFromYearOfRecept = validFromYearOfRecept;
    }

    public Curriculum(int id, String name, Speciality speciality, LocalDate validFromYearOfRecept,
            LocalDate validTillYearOfRecept) {
        this.id = id;
        this.name = name;
        this.speciality = speciality;
        this.validFromYearOfRecept = validFromYearOfRecept;
        this.validTillYearOfRecept = validTillYearOfRecept;
    }

    public void setValidTillYearOfRecept(LocalDate validTillYearOfRecept) {
        this.validTillYearOfRecept = validTillYearOfRecept;
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

    public LocalDate getValidFromYearOfRecept() {
        return validFromYearOfRecept;
    }

    public LocalDate getValidTillYearOfRecept() {
        return validTillYearOfRecept;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((speciality == null) ? 0 : speciality.hashCode());
        result = prime * result + ((validFromYearOfRecept == null) ? 0 : validFromYearOfRecept.hashCode());
        result = prime * result + ((validTillYearOfRecept == null) ? 0 : validTillYearOfRecept.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Curriculum other = (Curriculum) obj;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (speciality == null) {
            if (other.speciality != null)
                return false;
        } else if (!speciality.equals(other.speciality))
            return false;
        if (validFromYearOfRecept == null) {
            if (other.validFromYearOfRecept != null)
                return false;
        } else if (!validFromYearOfRecept.equals(other.validFromYearOfRecept))
            return false;
        if (validTillYearOfRecept == null) {
            if (other.validTillYearOfRecept != null)
                return false;
        } else if (!validTillYearOfRecept.equals(other.validTillYearOfRecept))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Curriculum [id=" + id + ", name=" + name + ", speciality=" + speciality + ", validFromYearOfRecept="
                + validFromYearOfRecept + ", validTillYearOfRecept=" + validTillYearOfRecept + "]";
    }

}
