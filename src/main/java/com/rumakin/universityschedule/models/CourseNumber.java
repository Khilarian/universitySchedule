package com.rumakin.universityschedule.models;

public enum CourseNumber {
    FIRST(1), SECOND(2), THIRD(3), FOURTH(4), FIFTH(5), SIXTH(6);

    private final int index;

    CourseNumber(int i) {
        this.index = i;
    }

    public int getIndex() {
        return index;
    }
    
}
