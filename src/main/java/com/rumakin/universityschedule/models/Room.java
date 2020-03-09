package com.rumakin.universityschedule.models;

public class Room {

    private int number;
    private int floor;

    public Room(int number, int floor) {
        this.number = number;
        this.floor = floor;
    }

    public int getNumber() {
        return number;
    }

    public int getFloor() {
        return floor;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + floor;
        result = prime * result + number;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Room other = (Room) obj;
        if (floor != other.floor)
            return false;
        if (number != other.number)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Room [number=" + number + ", floor=" + floor + "]";
    }

}
