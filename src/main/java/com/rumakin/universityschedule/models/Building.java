package com.rumakin.universityschedule.models;

import java.util.*;

public class Building {

    private String name;
    private String address;
    private Set<Room> rooms;

    public Building(String name, String address) {
        this.name = name;
        this.address = address;
        this.rooms = new HashSet<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((rooms == null) ? 0 : rooms.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Building other = (Building) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (rooms == null) {
            if (other.rooms != null)
                return false;
        } else if (!rooms.equals(other.rooms))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Building [name=" + name + ", address=" + address + ", rooms=" + rooms + "]";
    }

}
