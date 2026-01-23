package com.sparta.spartaapi.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trainers")
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainer_id")
    private Integer trainerID;

    @Column(name = "full_name", nullable = false, length = 40)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 40)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "specialty_lang", length = 30)
    private String specialtyLang;

    @Column(name = "fav_animal", length = 30)
    private String favAnimal;


    @OneToMany(mappedBy = "trainer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<>();

    public Trainer() {}

    public Trainer(Integer trainerID, String firstName, String lastName, String email, String phoneNumber, String specialtyLang, String favAnimal) {
        this.trainerID = trainerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.specialtyLang = specialtyLang;
        this.favAnimal = favAnimal;
    }

    public Integer getTrainerID() {
        return trainerID;
    }

    public void setTrainerID(Integer trainerID) {
        this.trainerID = trainerID;
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

    public String getSpecialtyLang() {
        return specialtyLang;
    }

    public void setSpecialtyLang(String specialtyLang) {
        this.specialtyLang = specialtyLang;
    }

    public String getFavAnimal() {
        return favAnimal;
    }

    public void setFavAnimal(String favAnimal) {
        this.favAnimal = favAnimal;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
