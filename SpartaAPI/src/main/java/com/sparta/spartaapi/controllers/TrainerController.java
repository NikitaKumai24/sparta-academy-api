package com.sparta.spartaapi.controllers;

import com.sparta.spartaapi.dtos.TrainerDTO;
import com.sparta.spartaapi.services.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Operation(summary = "Get all trainers", description = "Retrieve a list of all trainers")
    @GetMapping
    public ResponseEntity<List<TrainerDTO>> getAllTrainers() {
        return ResponseEntity.ok(trainerService.getAllTrainers());
    }

    @Operation(summary = "Get trainer by ID", description = "Retrieve a trainer from its unique ID")
    @GetMapping("/{id}")
    public ResponseEntity<TrainerDTO> getTrainerById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(trainerService.getTrainerById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Create trainer", description = "Add a new trainer to the system")
    @PostMapping
    public ResponseEntity<TrainerDTO> createTrainer(@RequestBody TrainerDTO dto) {
        return ResponseEntity.status(201).body(trainerService.saveTrainer(dto));
    }

    @Operation(summary = "Update trainer", description = "Update an existing trainer using its unique ID")
    @PutMapping("/{id}")
    public ResponseEntity<TrainerDTO> updateTrainer(
            @PathVariable Integer id,
            @RequestBody TrainerDTO dto) {

        try {
            dto.setTrainerId(id);
            return ResponseEntity.ok(trainerService.updateTrainer(dto));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete trainer", description = "Delete a trainer from the system using its unique ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable Integer id) {
        try {
            trainerService.deleteTrainer(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Search trainers by first name", description = "Retrieve trainers whose first name contains the given value")
    @GetMapping("/search/firstname")
    public ResponseEntity<List<TrainerDTO>> searchByFirstName(@RequestParam String firstName) {
        return ResponseEntity.ok(trainerService.searchByFirstName(firstName));
    }

    @Operation(summary = "Search trainers by last name", description = "Retrieve trainers whose last name contains the given value")
    @GetMapping("/search/lastname")
    public ResponseEntity<List<TrainerDTO>> searchByLastName(@RequestParam String lastName) {
        return ResponseEntity.ok(trainerService.searchByLastName(lastName));
    }

    @Operation(summary = "Search trainers by specialty language", description = "Retrieve trainers by specialty programming language")
    @GetMapping("/search/specialty")
    public ResponseEntity<List<TrainerDTO>> searchBySpecialtyLang(@RequestParam String lang) {
        return ResponseEntity.ok(trainerService.searchBySpecialtyLang(lang));
    }
}



