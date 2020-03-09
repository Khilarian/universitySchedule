package com.rumakin.universityschedule.models;

public enum AcademicDegree {
    BACHELOR(1), SPECIALIST(2), MASTER(3), CANDIDAT_OF_SCIENCES(4), DOCTOR_OF_SIENCE(5);

    public final int gradeLevel;
    
    AcademicDegree(int i){
        this.gradeLevel = i;
    }
    
    public int getGradeLevel() {
        return gradeLevel;
    }
}
