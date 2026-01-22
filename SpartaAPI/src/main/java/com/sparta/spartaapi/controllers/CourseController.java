package com.sparta.spartaapi.controllers;

import com.sparta.spartaapi.dtos.CourseDTO;
import com.sparta.spartaapi.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    // GET all courses
    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Get single course by ID
    @Operation(summary = "Get course by ID", description = "Retrieve a course from its unique ID")
    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable int id) {
        CourseDTO course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }


    // POST create new course
    @Operation(summary = "Create a new course", description = "Create a new course with title, description, start date and end date")
    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO) {
        CourseDTO createdCourse = courseService.createCourse(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    // PUT update existing course
    @Operation(summary = "Update a course", description = "Update an existing course using its unique ID")
    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable int id, @RequestBody CourseDTO courseDTO) {
        CourseDTO updatedCourse = courseService.updateCourse(id, courseDTO);
        return ResponseEntity.ok(updatedCourse);
    }

    // DELETE course
    @Operation(summary = "Delete a course", description = "Delete a course using its unique ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int id) {
        boolean deleted = courseService.deleteCourse(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // GET search courses by title (custom repository method)
    @Operation(summary = "Search courses by title", description = "Retrieve courses whose titles contain the provided term")
    @GetMapping("/search")
    public ResponseEntity<List<CourseDTO>> searchCourses(@RequestParam String title) {
        List<CourseDTO> results = courseService.searchCoursesByTitle(title);
        return ResponseEntity.ok(results);
    }

    @Operation(summary = "Get course by exact title", description = "Retrieve a single course that exactly matches the provided title")
    @GetMapping("/title")
    public ResponseEntity<CourseDTO> getCourseByExactTitle(@RequestParam String title) {
        return ResponseEntity.ok(courseService.getCourseByExactTitle(title));
    }

    @Operation(summary = "Get courses starting after a date", description = "Retrieve all courses that start after the specified date")
    @GetMapping("/start-date-after")
    public ResponseEntity<List<CourseDTO>> getCoursesStartingAfter(@RequestParam String date) {
        return ResponseEntity.ok(
                courseService.getCoursesStartingAfter(LocalDate.parse(date)));
    }

}
