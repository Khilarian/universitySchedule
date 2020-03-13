package com.rumakin.universityschedule.models;

public enum AcademicRank {
    DOCENT(1), PROFESSOR(2), CORRESPONDING_MEMBER_OF_THE_ACADEMY_OF_SCIENCES(3), FULL_MEMBER_OF_ACADEMY_OF_SCIENCE(4);

    private final int rankLevel;

    AcademicRank(int i) {
        this.rankLevel = i;
    }

    public int getRankLevel() {
        return rankLevel;
    }
}
