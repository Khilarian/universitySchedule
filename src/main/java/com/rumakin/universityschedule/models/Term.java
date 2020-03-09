package com.rumakin.universityschedule.models;

public enum Term {
    FIRST(1), SECOND(2), THIRD(3), FOURTH(4), FIFTH(5), SIXTH(6), SEVENTH(7), EIGHTH(8), NINTH(9), TENTH(10),
    ELEVENTH(11), TWELFTH(12);

    public final int index;

    Term(int i) {
        this.index = i;
    }

    public int getIndex() {
        return index;
    }
}
