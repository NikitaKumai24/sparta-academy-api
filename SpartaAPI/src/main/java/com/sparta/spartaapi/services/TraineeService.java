package com.sparta.spartaapi.services;

import com.sparta.spartaapi.dtos.TraineeDTO;
import com.sparta.spartaapi.dtos.TraineeMapper;
import com.sparta.spartaapi.entities.Trainee;
import com.sparta.spartaapi.repositories.TraineeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeService {
    private final TraineeRepository traineeRepository;
    private final TraineeMapper traineeMapper;

    @Autowired
    public TraineeService(TraineeRepository traineeRepository, TraineeMapper traineeMapper) {
        if (traineeRepository == null || traineeMapper == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.traineeRepository = traineeRepository;
        this.traineeMapper = traineeMapper;
    }

    public List<TraineeDTO> getAllTrainees() {
        return traineeRepository.findAll()
                .stream()
                .map(traineeMapper::toDto)
                .toList();
    }

    public TraineeDTO getTraineeById(Integer id) {
        return traineeRepository.findById(id)
                .map(traineeMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee not found with id: " + id));
    }

    public TraineeDTO createTrainee(TraineeDTO traineeDTO) {
        Trainee trainee = traineeMapper.toEntity(traineeDTO);
        Trainee savedTrainee = traineeRepository.save(trainee);
        return traineeMapper.toDto(savedTrainee);
    }

    public TraineeDTO updateTrainee(Integer id, TraineeDTO traineeDTO) {
        if (!traineeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Trainee not found with id: " + id);
        }

        Trainee trainee = traineeMapper.toEntity(traineeDTO);
        trainee.setTraineeID(id);

        Trainee updatedTrainee = traineeRepository.save(trainee);
        return traineeMapper.toDto(updatedTrainee);
    }

    public void deleteTrainee(Integer id) {
        if (!traineeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Trainee not found with id: " + id);
        }
        traineeRepository.deleteById(id);
    }
}
