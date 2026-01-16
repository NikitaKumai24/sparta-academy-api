package com.sparta.spartaapi.repositories;


import com.sparta.spartaapi.entities.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

    List<Trainer> findByFirstNameContainingIgnoreCase(String firstName);

    List<Trainer> findByLastNameContainingIgnoreCase(String lastName);

    List<Trainer> findBySpecialtyLangContainingIgnoreCase(String specialtyLang);
}

