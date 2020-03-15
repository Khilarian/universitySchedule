package com.rumakin.universityschedule.models;

public class Room {

    private int id;
    private int number;
    private Building building;

    public Room(int number, Building building) {
        this.number = number;
        this.building = building;
    }

    public Room(int id, int number, Building building) {
        this.id = id;
        this.number = number;
        this.building = building;
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public Building getBuilding() {
        return building;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((building == null) ? 0 : building.hashCode());
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
        Room other = (Room) obj;
        if (building == null) {
            if (other.building != null)
                return false;
        } else if (!building.equals(other.building))
            return false;
        if (id != other.id)
            return false;
        if (number != other.number)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Room [id=" + id + ", number=" + number + ", building=" + building + "]";
    }

}
