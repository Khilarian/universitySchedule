package com.rumakin.universityschedule.dto;

import io.swagger.annotations.ApiModelProperty;

public class TeacherDto {

    @ApiModelProperty(notes = "Id of the teacher")
    private int personId;
    @ApiModelProperty(notes = "First name of the teacher")
    private String firstName;
    @ApiModelProperty(notes = "Last name of thr teacher")
    private String lastName;
    @ApiModelProperty(notes = "Id of the faculty")
    private int facultyId;
    @ApiModelProperty(notes = "Name of the faculty")
    private String facultyName;

    public TeacherDto() {
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Override
    public String toString() {
        return "TeacherDto [personId=" + personId + ", firstName=" + firstName + ", lastName=" + lastName
                + ", facultyId=" + facultyId + ", facultyName=" + facultyName + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + facultyId;
        result = prime * result + ((facultyName == null) ? 0 : facultyName.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + personId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TeacherDto other = (TeacherDto) obj;
        if (facultyId != other.facultyId) return false;
        if (facultyName == null) {
            if (other.facultyName != null) return false;
        } else if (!facultyName.equals(other.facultyName)) return false;
        if (firstName == null) {
            if (other.firstName != null) return false;
        } else if (!firstName.equals(other.firstName)) return false;
        if (lastName == null) {
            if (other.lastName != null) return false;
        } else if (!lastName.equals(other.lastName)) return false;
        return personId == other.personId;
    }

}
