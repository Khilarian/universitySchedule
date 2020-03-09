package com.rumakin.universityschedule.models;

import java.time.*;

public class Office extends Room {

    private LocalTime openedFrom;
    private LocalTime openedTill;

    public Office(int number, int floor) {
        super(number, floor);
    }

    public Office(int number, int floor, LocalTime openedFrom, LocalTime openedTill) {
        super(number, floor);
        this.openedFrom = openedFrom;
        this.openedTill = openedTill;
    }

    public void setOpenedFrom(LocalTime openedFrom) {
        this.openedFrom = openedFrom;
    }

    public void setOpnedTill(LocalTime opnedTill) {
        this.openedTill = opnedTill;
    }

    public LocalTime getOpenedFrom() {
        return openedFrom;
    }

    public LocalTime getOpnedTill() {
        return openedTill;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((openedFrom == null) ? 0 : openedFrom.hashCode());
        result = prime * result + ((openedTill == null) ? 0 : openedTill.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj) || getClass() != obj.getClass())
            return false;
        Office other = (Office) obj;
        if (openedFrom == null) {
            if (other.openedFrom != null)
                return false;
        } else if (!openedFrom.equals(other.openedFrom))
            return false;
        if (openedTill == null) {
            if (other.openedTill != null)
                return false;
        } else if (!openedTill.equals(other.openedTill))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Office [number=" + getNumber() + ", floor=" + getFloor() + ", openedFrom=" + openedFrom + ", opnedTill="
                + openedTill + "]";
    }

}
