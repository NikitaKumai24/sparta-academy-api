package com.sparta.spartaapi.services;

import com.sparta.spartaapi.dtos.TraineeDTO;
import com.sparta.spartaapi.dtos.TraineeMapper;
import com.sparta.spartaapi.entities.Course;
import com.sparta.spartaapi.entities.Trainee;
import com.sparta.spartaapi.repositories.CourseRepository;
import com.sparta.spartaapi.repositories.TraineeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TraineeService {
    private final TraineeRepository traineeRepository;
    private final TraineeMapper traineeMapper;
    private final CourseRepository courseRepository;

    @Autowired
    public TraineeService(TraineeRepository traineeRepository,
                          TraineeMapper traineeMapper,
                          CourseRepository courseRepository) {
        if (traineeRepository == null || traineeMapper == null || courseRepository == null) {
            throw new IllegalArgumentException("Repository and mapper cannot be null");
        }
        this.traineeRepository = traineeRepository;
        this.traineeMapper = traineeMapper;
        this.courseRepository = courseRepository;
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
                .orElseThrow(() -> new NoSuchElementException("Trainee not found with id: " + id));
    }

    public TraineeDTO createTrainee(TraineeDTO traineeDTO) {
        Trainee trainee = traineeMapper.toEntity(traineeDTO);

        // Link trainee to course if courseId is provided
        if (traineeDTO.getCourseId() != null) {
            Course course = courseRepository.findById(traineeDTO.getCourseId())
                    .orElseThrow(() -> new NoSuchElementException(
                            "Course not found with id: " + traineeDTO.getCourseId()));
            trainee.setCourse(course);
        }

        Trainee savedTrainee = traineeRepository.save(trainee);
        return traineeMapper.toDto(savedTrainee);
    }

    public TraineeDTO updateTrainee(Integer id, TraineeDTO traineeDTO) {
        if (!traineeRepository.existsById(id)) {
            throw new NoSuchElementException("Trainee not found with id: " + id);
        }

        Trainee trainee = traineeMapper.toEntity(traineeDTO);
        trainee.setTraineeID(id);

        // Link trainee to course if courseId is provided
        if (traineeDTO.getCourseId() != null) {
            Course course = courseRepository.findById(traineeDTO.getCourseId())
                    .orElseThrow(() -> new NoSuchElementException(
                            "Course not found with id: " + traineeDTO.getCourseId()));
            trainee.setCourse(course);
        }

        Trainee updatedTrainee = traineeRepository.save(trainee);
        return traineeMapper.toDto(updatedTrainee);
    }

    public void deleteTrainee(Integer id) {
        if (!traineeRepository.existsById(id)) {
            throw new NoSuchElementException("Trainee not found with id: " + id);
        }
        traineeRepository.deleteById(id);
    }
}
