package com.rumakin.universityschedule.models;

public class Room {

    private int number;
    private int floor;
    private Building building;

    public Room(int number, int floor, Building building) {
        this.number = number;
        this.floor = floor;
        this.building = building;
    }

    public int getNumber() {
        return number;
    }

    public int getFloor() {
        return floor;
    }
    
    public Building getBuilding() {
        return building;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((building == null) ? 0 : building.hashCode());
        result = prime * result + floor;
        result = prime * result + number;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj ==null || getClass() != obj.getClass())
            return false;
        Room other = (Room) obj;
        if (building == null) {
            if (other.building != null)
                return false;
        } else if (!building.equals(other.building))
            return false;
        if (floor != other.floor)
            return false;
        if (number != other.number)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Room [number=" + number + ", floor=" + floor + ", building=" + building + "]";
    }

}
