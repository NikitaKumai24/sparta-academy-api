package com.sparta.spartaapi.controllers;

import com.sparta.spartaapi.dtos.TrainerDTO;
import com.sparta.spartaapi.services.TrainerService;
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

    @GetMapping
    public ResponseEntity<List<TrainerDTO>> getAllTrainers() {
        return ResponseEntity.ok(trainerService.getAllTrainers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainerDTO> getTrainerById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(trainerService.getTrainerById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TrainerDTO> createTrainer(@RequestBody TrainerDTO dto) {
        return ResponseEntity.status(201).body(trainerService.saveTrainer(dto));
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable Integer id) {
        try {
            trainerService.deleteTrainer(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}


