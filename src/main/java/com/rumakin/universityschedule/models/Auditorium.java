package com.rumakin.universityschedule.models;

public class Auditorium extends Room {

    private int capacity;

    public Auditorium(int number, Building building) {
        super(number, building);
    }

    public Auditorium(int id, int number, Building building) {
        super(id, number, building);
    }

    public Auditorium(int number, Building building, int capacity) {
        super(number, building);
        this.capacity = capacity;
    }

    public Auditorium(int id, int number, Building building, int capacity) {
        super(id, number, building);
        this.capacity = capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + capacity;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Auditorium other = (Auditorium) obj;
        return capacity == other.capacity;
    }

    @Override
    public String toString() {
        return "Auditorium [id=" + getId() + ", snumber=" + getNumber() + ", building="
                + getBuilding() + ", capacity=" + capacity + "]";
    }

}
