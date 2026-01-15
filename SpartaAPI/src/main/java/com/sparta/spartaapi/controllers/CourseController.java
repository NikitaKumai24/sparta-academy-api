package com.sparta.spartaapi.controllers;

import com.sparta.spartaapi.dtos.CourseDTO;
import com.sparta.spartaapi.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class CourseController {
    private final CourseService courseService;

    // GET all courses
    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Get single course by ID
    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses(){
        List<CourseDTO> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    // POST create new course
    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO) {
        CourseDTO createdCourse = courseService.createCourse(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    // PUT update existing course
    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable int id, @RequestBody CourseDTO courseDTO) {
        CourseDTO updatedCourse = courseService.updateCourse(id, courseDTO);
        return ResponseEntity.ok(updatedCourse);
    }

    // DELETE course
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int id) {
        boolean deleted = courseService.deleteCourse(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
