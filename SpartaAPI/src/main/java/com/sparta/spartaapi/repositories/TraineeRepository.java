package com.sparta.spartaapi.repositories;

import com.sparta.spartaapi.entities.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Integer> {

    List<Trainee> findByFirstNameContainingIgnoreCase(String firstName);

    List<Trainee> findByLastNameContainingIgnoreCase(String lastName);

    List<Trainee> findBySpecialtyLangContainingIgnoreCase(String specialtyLang);

    List<Trainee> findByCourse_CourseId(Integer courseId);

    List<Trainee> findByFirstNameContainingIgnoreCaseOrSpecialtyLangContainingIgnoreCase(String name, String specialty);
}
