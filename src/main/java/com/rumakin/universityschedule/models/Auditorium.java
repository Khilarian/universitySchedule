package com.rumakin.universityschedule.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Auditorium {

    private int id;
    private int number;
    private int capacity;
    private Building building;

    @Autowired
    public Auditorium(int number, int capacity, Building building) {
        this.number = number;
        this.capacity = capacity;
        this.building = building;
    }

    @Autowired
    public Auditorium(int id, int number, int capacity, Building building) {
        this.id = id;
        this.number = number;
        this.capacity = capacity;
        this.building = building;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public int getCapacity() {
        return capacity;
    }

    public Building getBuilding() {
        return building;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((building == null) ? 0 : building.hashCode());
        result = prime * result + capacity;
        result = prime * result + id;
        result = prime * result + number;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Auditorium other = (Auditorium) obj;
        if (building == null) {
            if (other.building != null)
                return false;
        } else if (!building.equals(other.building))
            return false;
        if (capacity != other.capacity)
            return false;
        if (id != other.id)
            return false;
        return number == other.number;
    }

    @Override
    public String toString() {
        return "Auditorium [id=" + id + ", number=" + number + ", capacity=" + capacity + ", building=" + building
                + "]";
    }

}
