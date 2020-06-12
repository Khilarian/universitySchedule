package com.rumakin.universityschedule.model;

import java.util.*;

public class University {

    private Set<Building> buildings;
    private Set<Faculty> faculties;

    public University(Set<Building> buildings, Set<Faculty> faculties) {
        this.buildings = buildings;
        this.faculties = faculties;
    }

    public void setBuildings(Set<Building> buildings) {
        this.buildings = buildings;
    }

    public void setFaculties(Set<Faculty> faculties) {
        this.faculties = faculties;
    }

    public Set<Building> getBuildings() {
        return buildings;
    }

    public Set<Faculty> getFaculties() {
        return faculties;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((buildings == null) ? 0 : buildings.hashCode());
        result = prime * result + ((faculties == null) ? 0 : faculties.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        University other = (University) obj;
        if (buildings == null) {
            if (other.buildings != null)
                return false;
        } else if (!buildings.equals(other.buildings))
            return false;
        if (faculties == null) {
            if (other.faculties != null)
                return false;
        } else if (!faculties.equals(other.faculties))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "University [buildings=" + buildings + ", faculties=" + faculties + "]";
    }

}
