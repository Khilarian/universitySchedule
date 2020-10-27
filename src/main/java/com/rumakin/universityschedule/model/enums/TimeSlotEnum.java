package com.rumakin.universityschedule.model.enums;

public enum TimeSlotEnum {
    FIRST(1), SECOND(2), THIRD(3), FOURTH(4), FIFTH(5), SIXTH(6), SEVENTH(7), EIGHTH(8);

    private int number;

    TimeSlotEnum(int i) {
        this.number = i;
    }
    
    public int getNumber() {
        return this.number;
    }

}
