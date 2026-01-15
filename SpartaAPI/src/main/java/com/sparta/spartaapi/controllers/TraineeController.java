package com.sparta.spartaapi.controllers;

import com.sparta.spartaapi.dtos.TraineeDTO;
import com.sparta.spartaapi.services.TraineeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainees")
public class TraineeController {
    private final TraineeService traineeService;

    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @GetMapping
    public ResponseEntity<List<TraineeDTO>> getAllTrainees() {
        List<TraineeDTO> trainees = traineeService.getAllTrainees();
        return ResponseEntity.ok(trainees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TraineeDTO> getTraineeById(@PathVariable Integer id) {
        TraineeDTO traineeDTO = traineeService.getTraineeById(id);
        if (traineeDTO != null) {
            return ResponseEntity.ok(traineeDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TraineeDTO> addTrainee(@RequestBody TraineeDTO trainee) {
        TraineeDTO createdTrainee = traineeService.createTrainee(trainee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTrainee);
    }
}
