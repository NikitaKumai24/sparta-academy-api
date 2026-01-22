package com.sparta.spartaapi.controllers;

import com.sparta.spartaapi.dtos.CourseDTO;
import com.sparta.spartaapi.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(summary = "Get course by ID", description = "Retrieve a course from its unique ID")
    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable int id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @Operation(summary = "Create a new course", description = "Create a new course with title, description, start date and end date")
    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO) {
        CourseDTO createdCourse = courseService.createCourse(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @Operation(summary = "Update a course", description = "Update an existing course using its unique ID")
    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable int id, @RequestBody CourseDTO courseDTO) {
        return ResponseEntity.ok(courseService.updateCourse(id, courseDTO));
    }

    @Operation(summary = "Delete a course", description = "Delete a course using its unique ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int id) {
        boolean deleted = courseService.deleteCourse(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Search courses by title", description = "Retrieve courses whose titles contain the provided term")
    @GetMapping("/search")
    public ResponseEntity<List<CourseDTO>> searchCourses(@RequestParam String title) {
        return ResponseEntity.ok(courseService.searchCoursesByTitle(title));
    }

    @Operation(summary = "Get course by exact title", description = "Retrieve a single course that exactly matches the provided title")
    @GetMapping("/title")
    public ResponseEntity<CourseDTO> getCourseByExactTitle(@RequestParam String title) {
        return ResponseEntity.ok(courseService.getCourseByExactTitle(title));
    }

    @Operation(summary = "Get courses starting after a date", description = "Retrieve all courses that start after the specified date")
    @GetMapping("/start-date-after")
    public ResponseEntity<List<CourseDTO>> getCoursesStartingAfter(@RequestParam String date) {
        return ResponseEntity.ok(courseService.getCoursesStartingAfter(LocalDate.parse(date)));
    }
}
