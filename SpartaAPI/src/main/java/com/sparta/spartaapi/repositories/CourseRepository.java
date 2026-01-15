package com.sparta.spartaapi.repositories;

import com.sparta.spartaapi.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    Optional<Course> findByName(String name);

    List<Course> findByNameContainingIgnoreCase(String name);

    List<Course> findByStartDateAfter(LocalDate startDate);


}
