package com.sparta.spartaapi.controllers;

import com.sparta.spartaapi.dtos.TraineeDTO;
import com.sparta.spartaapi.dtos.TrainerDTO;
import com.sparta.spartaapi.services.TraineeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainees")
public class TraineeController {
    private final TraineeService traineeService;

    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @Operation(summary = "Get all trainees", description = "Retrieve a list of all trainees")
    @GetMapping
    public ResponseEntity<List<TraineeDTO>> getAllTrainees() {
        List<TraineeDTO> trainees = traineeService.getAllTrainees();
        return ResponseEntity.ok(trainees);
    }

    @Operation(summary = "Get a trainee by ID", description = "Retrieve a trainee from the database using their unique ID")
    @GetMapping("/{id}")
    public ResponseEntity<TraineeDTO> getTraineeById(@PathVariable Integer id) {
        TraineeDTO traineeDTO = traineeService.getTraineeById(id);
        if (traineeDTO != null) {
            return ResponseEntity.ok(traineeDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Enrol a trainee in a course", description = "A trainee can assign themselves to a specific course")
    @PostMapping("/{traineeId}/courses/{courseId}/enrol")
    public ResponseEntity<TraineeDTO> enrolToCourse(@PathVariable Integer traineeId,
                                                    @PathVariable Integer courseId) {

        TraineeDTO updatedTrainee = traineeService.enrolTraineeToCourse(traineeId, courseId);
        return ResponseEntity.ok(updatedTrainee);
    }

    @Operation(summary = "Add a trainee", description = "Add a new trainee to the database")
    @PostMapping
    public ResponseEntity<TraineeDTO> addTrainee(@RequestBody TraineeDTO trainee) {
        TraineeDTO createdTrainee = traineeService.createTrainee(trainee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTrainee);
    }

    @Operation(summary = "Get trainer for a trainee", description = "Retrieve the trainer assigned to a trainee's course")
    @GetMapping("/{traineeId}/trainer")
    public ResponseEntity<TrainerDTO> getTrainerForTrainee(@PathVariable Integer traineeId) {
        TrainerDTO trainer = traineeService.getTrainerForTrainee(traineeId);
        return ResponseEntity.ok(trainer);
    }

    @Operation(summary = "Update a trainee", description = "Update the details of a trainee by using their unique ID ")
    @PutMapping("/{id}")
    public ResponseEntity<TraineeDTO> updateTrainee(@PathVariable Integer id, @RequestBody TraineeDTO traineeDTO) {
        try {
            TraineeDTO updatedTrainee = traineeService.updateTrainee(id, traineeDTO);
            return ResponseEntity.ok(updatedTrainee);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a trainee", description = "Delete a trainee from the database by using their unique ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainee(@PathVariable Integer id) {
        traineeService.deleteTrainee(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search trainees by first name", description = "Retrieve trainees whose first name contains the given value")
    @GetMapping("/search/firstname")
    public ResponseEntity<List<TraineeDTO>> searchByFirstName(@RequestParam String firstName) {
        return ResponseEntity.ok(traineeService.searchByFirstName(firstName));
    }

    @Operation(summary = "Search trainees by last name", description = "Retrieve trainees whose last name contains the given value")
    @GetMapping("/search/lastname")
    public ResponseEntity<List<TraineeDTO>> searchByLastName(@RequestParam String lastName) {
        return ResponseEntity.ok(traineeService.searchByLastName(lastName));
    }

    @Operation(summary = "Search trainees by specialty language", description = "Retrieve trainees by specialty programming language")
    @GetMapping("/search/specialty")
    public ResponseEntity<List<TraineeDTO>> searchBySpecialtyLang(@RequestParam String lang) {
        return ResponseEntity.ok(traineeService.searchBySpecialtyLang(lang));
    }

    @Operation(summary = "Get trainees by course", description = "Retrieve all trainees enrolled in a specific course using the course ID")
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<TraineeDTO>> getTraineesByCourse(@PathVariable Integer courseId) {
        return ResponseEntity.ok(traineeService.getTraineesByCourse(courseId));
    }
}



