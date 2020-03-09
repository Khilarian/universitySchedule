package com.rumakin.universityschedule.models;

public class Auditorium extends Room {

    private int capacity;

    public Auditorium(int number, int floor) {
        super(number, floor);
    }

    public Auditorium(int number, int floor, int capacity) {
        super(number, floor);
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
        if (capacity != other.capacity)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Auditorium [number=" + getNumber() + ", floor=" + getFloor() + ", capacity=" + capacity + "]";
    }

}
