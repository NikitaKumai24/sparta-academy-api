package com.sparta.spartaapi.repositories;

import com.sparta.spartaapi.entities.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Integer> {

    Optional<Trainee> findByName(String name);

    List<Trainee> findByNameContainingIgnoreCase(String name);

    List<Trainee> findByCourse(String course);


}
