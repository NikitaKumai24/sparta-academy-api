package com.sparta.spartaapi.services;

import com.sparta.spartaapi.dtos.CourseDTO;
import com.sparta.spartaapi.entities.Course;
import com.sparta.spartaapi.entities.Trainer;
import com.sparta.spartaapi.repositories.CourseRepository;
import com.sparta.spartaapi.repositories.TrainerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final TrainerRepository trainerRepository;

    public CourseService(CourseRepository courseRepository, TrainerRepository trainerRepository) {
        this.courseRepository = courseRepository;
        this.trainerRepository = trainerRepository;
    }


    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }


    public CourseDTO getCourseById(int id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Course not found with id: " + id));
        return convertToDTO(course);
    }


    public CourseDTO createCourse(CourseDTO courseDTO) {
        if (courseDTO.getTrainerId() == null) {
            throw new IllegalArgumentException("trainerId is required");
        }

        Trainer trainer = trainerRepository.findById(courseDTO.getTrainerId())
                .orElseThrow(() -> new NoSuchElementException("Trainer not found with id: " + courseDTO.getTrainerId()));

        Course course = new Course();
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setStartDate(courseDTO.getStartDate());
        course.setEndDate(courseDTO.getEndDate());
        course.setTrainer(trainer);

        Course saved = courseRepository.save(course);
        return convertToDTO(saved);
    }


    public CourseDTO updateCourse(int id, CourseDTO courseDTO) {
        Course courseToUpdate = courseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Course not found with id: " + id));

        courseToUpdate.setTitle(courseDTO.getTitle());
        courseToUpdate.setDescription(courseDTO.getDescription());
        courseToUpdate.setStartDate(courseDTO.getStartDate());
        courseToUpdate.setEndDate(courseDTO.getEndDate());

        if (courseDTO.getTrainerId() != null) {
            Trainer trainer = trainerRepository.findById(courseDTO.getTrainerId())
                    .orElseThrow(() -> new NoSuchElementException("Trainer not found with id: " + courseDTO.getTrainerId()));
            courseToUpdate.setTrainer(trainer);
        }

        Course updated = courseRepository.save(courseToUpdate);
        return convertToDTO(updated);
    }


    public boolean deleteCourse(int id) {
        if (!courseRepository.existsById(id)) return false;

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Course not found with id: " + id));

        course.getTrainees().forEach(t -> t.setCourse(null));
        courseRepository.save(course);

        courseRepository.deleteById(id);
        return true;
    }


    public List<CourseDTO> searchCoursesByTitle(String title) {
        return courseRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public CourseDTO getCourseByExactTitle(String title) {
        Course course = courseRepository.findByTitle(title)
                .orElseThrow(() -> new NoSuchElementException("Course not found with title: " + title));
        return convertToDTO(course);
    }

    public List<CourseDTO> getCoursesStartingAfter(LocalDate date) {
        return courseRepository.findByStartDateAfter(date)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }


    private CourseDTO convertToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setCourseId(course.getCourseId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setStartDate(course.getStartDate());
        dto.setEndDate(course.getEndDate());

        if (course.getTrainer() != null) {
            dto.setTrainerId(course.getTrainer().getTrainerID());
        }
        return dto;
    }
}
