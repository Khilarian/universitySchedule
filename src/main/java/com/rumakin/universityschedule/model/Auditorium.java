package com.rumakin.universityschedule.model;

import javax.persistence.*;

@Entity
@Table
public class Auditorium implements ModelEntity {

    @Id
    @Column(name = "auditorium_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auditorium_seq")
    @SequenceGenerator(name = "auditorium_seq", sequenceName = "auditorium_auditorium_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "auditorium_number")
    private int number;

    @Column(name = "auditorium_capacity")
    private int capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private Building building;

    public Auditorium() {
    }

    public Auditorium(int number, int capacity, Building building) {
        this.number = number;
        this.capacity = capacity;
        this.building = building;
    }

    public Auditorium(int id, int number, int capacity, Building building) {
        this.id = id;
        this.number = number;
        this.capacity = capacity;
        this.building = building;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    @Override
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
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Auditorium other = (Auditorium) obj;
        if (building == null) {
            if (other.building != null) return false;
        } else if (!building.equals(other.building)) return false;
        if (capacity != other.capacity) return false;
        if (id != other.id) return false;
        return number == other.number;
    }

    @Override
    public String toString() {
        return "Auditorium [id=" + id + ", number=" + number + ", capacity=" + capacity + ", building=" + building
                + "]";
    }

}
