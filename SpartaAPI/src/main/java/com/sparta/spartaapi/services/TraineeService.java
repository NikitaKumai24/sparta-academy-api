package com.sparta.spartaapi.services;

import com.sparta.spartaapi.dtos.TraineeDTO;
import com.sparta.spartaapi.dtos.TraineeMapper;
import com.sparta.spartaapi.dtos.TrainerDTO;
import com.sparta.spartaapi.dtos.TrainerMapper;
import com.sparta.spartaapi.entities.Course;
import com.sparta.spartaapi.entities.Trainee;
import com.sparta.spartaapi.entities.Trainer;
import com.sparta.spartaapi.repositories.CourseRepository;
import com.sparta.spartaapi.repositories.TraineeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TraineeService {
    private final TraineeRepository traineeRepository;
    private final TraineeMapper traineeMapper;
    private final CourseRepository courseRepository;
    private final TrainerMapper trainerMapper;

    @Autowired
    public TraineeService(TraineeRepository traineeRepository, TraineeMapper traineeMapper,
                          CourseRepository courseRepository, TrainerMapper trainerMapper) {
        if (traineeRepository == null || traineeMapper == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.traineeRepository = traineeRepository;
        this.traineeMapper = traineeMapper;
        this.courseRepository = courseRepository;
        this.trainerMapper = trainerMapper;
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

    public TraineeDTO enrolTraineeToCourse(Integer traineeId, Integer courseId) {
        Trainee trainee = traineeRepository.findById(traineeId)
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found with id: " + traineeId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + courseId));

        trainee.setCourse(course);

        Trainee updatedTrainee = traineeRepository.save(trainee);
        return traineeMapper.toDto(updatedTrainee);
    }

    public TraineeDTO createTrainee(TraineeDTO traineeDTO) {
        Trainee trainee = traineeMapper.toEntity(traineeDTO);


        if (traineeDTO.getCourseId() != null) {
            Course course = courseRepository.findById(traineeDTO.getCourseId())
                    .orElseThrow(() -> new NoSuchElementException(
                            "Course not found with id: " + traineeDTO.getCourseId()));
            trainee.setCourse(course);
        }

        Trainee savedTrainee = traineeRepository.save(trainee);
        return traineeMapper.toDto(savedTrainee);
    }

    public TrainerDTO getTrainerForTrainee(Integer traineeId) {
        Trainee trainee = traineeRepository.findById(traineeId)
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found with id: " + traineeId));

        if (trainee.getCourse() == null) {
            throw new IllegalArgumentException("Trainee is not enrolled in any course");
        }

        Trainer trainer = trainee.getCourse().getTrainer();

        if (trainer == null) {
            throw new IllegalArgumentException("Trainer cannot be found for this course");
        }

        return trainerMapper.toDTO(trainer);
    }

    public TraineeDTO updateTrainee(Integer id, TraineeDTO dto) {

        Trainee trainee = traineeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Trainee not found"));

        trainee.setFirstName(dto.getFirstName());
        trainee.setLastName(dto.getLastName());
        trainee.setEmail(dto.getEmail());
        trainee.setPhoneNumber(dto.getPhoneNumber());
        trainee.setSpecialtyLang(dto.getSpecialityLang());

        if (dto.getCourseId() != null) {
            Course course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new NoSuchElementException("Course not found"));
            trainee.setCourse(course);
        }

        return traineeMapper.toDto(traineeRepository.save(trainee));
    }

    public void deleteTrainee(Integer id) {
        if (!traineeRepository.existsById(id)) {
            throw new NoSuchElementException("Trainee not found with id: " + id);
        }
        traineeRepository.deleteById(id);
    }

    public List<TraineeDTO> searchByFirstName(String firstName){
        List<Trainee> filteredTrainees = traineeRepository.findByFirstNameContainingIgnoreCase(firstName);
        return filteredTrainees.stream()
                .map(traineeMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TraineeDTO> filterByName_and_specialty(String name, String specialty){
        List<Trainee> filteredTodos = traineeRepository.findByFirstNameContainingIgnoreCaseOrSpecialtyLangContainingIgnoreCase(name, specialty);
        return filteredTodos.stream()
                .map(traineeMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TraineeDTO> searchByLastName(String lastName) {
        return traineeRepository.findByLastNameContainingIgnoreCase(lastName).stream().map(traineeMapper::toDto).toList();
    }

    public List<TraineeDTO> searchBySpecialtyLang(String lang) {
        return traineeRepository.findBySpecialtyLangContainingIgnoreCase(lang).stream().map(traineeMapper::toDto).toList();
    }

    public List<TraineeDTO> getTraineesByCourse(Integer courseId) {
        return traineeRepository.findByCourse_CourseId(courseId).stream().map(traineeMapper::toDto).toList();
    }
}
