package com.rumakin.universityschedule.dto;

import io.swagger.annotations.ApiModelProperty;

public class StudentDto {

    @ApiModelProperty(notes = "Id of the student")
    private int personId;
    @ApiModelProperty(notes = "First name of the student")
    private String firstName;
    @ApiModelProperty(notes = "Last name of the student")
    private String lastName;
    @ApiModelProperty(notes = "Email of the student")
    private String email;
    @ApiModelProperty(notes = "Phone of the student")
    private String phone;
    @ApiModelProperty(notes = "Id of the group")
    private int groupId;
    @ApiModelProperty(notes = "Name of the group")
    private String groupName;

    public StudentDto() {
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

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "StudentDto [personId=" + personId + ", firstName=" + firstName + ", lastName=" + lastName + ", groupId="
                + groupId + ", groupName=" + groupName + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + groupId;
        result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + personId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        StudentDto other = (StudentDto) obj;
        if (firstName == null) {
            if (other.firstName != null) return false;
        } else if (!firstName.equals(other.firstName)) return false;
        if (groupId != other.groupId) return false;
        if (groupName == null) {
            if (other.groupName != null) return false;
        } else if (!groupName.equals(other.groupName)) return false;
        if (lastName == null) {
            if (other.lastName != null) return false;
        } else if (!lastName.equals(other.lastName)) return false;
        return personId == other.personId;
    }

}
