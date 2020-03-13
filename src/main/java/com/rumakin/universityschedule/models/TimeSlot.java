package com.rumakin.universityschedule.models;

public enum TimeSlot {
    FIRST(1), SECOND(2), THIRD(3), FOURTH(4), FIFTH(5), SIXTH(6), SEVENTH(7), EIGHTH(8);

    private final int index;

    TimeSlot(int i) {
        this.index = i;
    }

    public int getIndex() {
        return index;
    }
}
