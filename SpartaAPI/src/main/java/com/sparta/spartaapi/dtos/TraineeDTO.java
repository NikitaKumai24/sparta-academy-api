package com.sparta.spartaapi.dtos;

public class TraineeDTO {


    private Integer traineeId;
    private Integer courseId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String specialityLang;


    public TraineeDTO() {
    }

    public TraineeDTO(Integer traineeId, Integer courseId,
                      String firstName, String lastName,
                      String email, String phoneNumber,
                      String specialityLang) {
        this.traineeId = traineeId;
        this.courseId = courseId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.specialityLang = specialityLang;
    }

    public Integer getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(Integer traineeId) {
        this.traineeId = traineeId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
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

    public String getEmail() { return email;}

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSpecialityLang() {
        return specialityLang;
    }

    public void setSpecialityLang(String specialityLang) {
        this.specialityLang = specialityLang;
    }




}

