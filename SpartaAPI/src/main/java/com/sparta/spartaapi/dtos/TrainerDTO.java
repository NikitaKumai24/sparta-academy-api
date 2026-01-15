package com.sparta.spartaapi.dtos;

public class TrainerDTO {


    private Integer trainerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String specialityLang;
    private String favAnimal;


    public TrainerDTO() {
    }


    public TrainerDTO (Integer trainerId, String firstName, String lastName,
                       String email, String phoneNumber,
                       String specialityLang, String favAnimal) {
        this.trainerId = trainerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.specialityLang = specialityLang;
        this.favAnimal = favAnimal;
    }

    public Integer TrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
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

    public String getEmail() {
        return email;
    }

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

    public String getFavAnimal() {
        return favAnimal;
    }

    public void setFavAnimal(String favAnimal) {
        this.favAnimal = favAnimal;
    }

}

