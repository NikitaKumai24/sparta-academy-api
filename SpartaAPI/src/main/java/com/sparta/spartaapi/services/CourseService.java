package com.sparta.spartaapi.services;

import com.sparta.spartaapi.dtos.CourseDTO;
import com.sparta.spartaapi.entities.Course;
import com.sparta.spartaapi.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sparta.spartaapi.entities.Trainer;
import com.sparta.spartaapi.repositories.TrainerRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CourseService {



    private final CourseRepository courseRepository;
    private final TrainerRepository trainerRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, TrainerRepository trainerRepository) {
        this.courseRepository = courseRepository;
        this.trainerRepository = trainerRepository;
    }

    //CRUD Operations

    //Get all courses
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = this.courseRepository.findAll();
        return courses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    //Get a single course id
    public CourseDTO getCourseById(int id) {
        Course course = this.courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        return convertToDTO(course);
    }


    //create course
    public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = convertToEntity(courseDTO);

        if (courseDTO.getTrainerId() != null) {
            Trainer trainer = trainerRepository.findById(courseDTO.getTrainerId())
                    .orElseThrow(() -> new NoSuchElementException("Trainer not found with id: " + courseDTO.getTrainerId()));
            course.setTrainer(trainer);
        } else {
            throw new IllegalArgumentException("Trainer is required for a course");
        }

        Course savedCourse = courseRepository.save(course);
        return convertToDTO(savedCourse);
    }



    //update course
    public CourseDTO updateCourse(int id, CourseDTO courseDTO) {
        Course courseToUpdate = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));

        courseToUpdate.setTitle(courseDTO.getTitle());
        courseToUpdate.setDescription(courseDTO.getDescription());
        courseToUpdate.setStartDate(courseDTO.getStartDate());
        courseToUpdate.setEndDate(courseDTO.getEndDate());

        if (courseDTO.getTrainerId() != null) {
            Trainer trainer = trainerRepository.findById(courseDTO.getTrainerId())
                    .orElseThrow(() -> new NoSuchElementException("Trainer not found with id: " + courseDTO.getTrainerId()));
            courseToUpdate.setTrainer(trainer);
        }

        Course updatedCourse = courseRepository.save(courseToUpdate);
        return convertToDTO(updatedCourse);
    }


    //delete course
    public boolean deleteCourse(int id) {
        if (this.courseRepository.existsById(id)) {
            this.courseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //search by title
    public List<CourseDTO> searchCoursesByTitle(String title) {
        return courseRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }


    //Mapper methods

    private CourseDTO convertToDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCourseId(course.getCourseId());
        courseDTO.setTitle(course.getTitle());
        courseDTO.setDescription(course.getDescription());
        courseDTO.setStartDate(course.getStartDate());
        courseDTO.setEndDate(course.getEndDate());

        if (course.getTrainer() != null) {
            courseDTO.setTrainerId(course.getTrainer().getTrainerID());
        }

        return courseDTO;
    }


    private Course convertToEntity(CourseDTO courseDTO) {
        Course course = new Course();
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setStartDate(courseDTO.getStartDate());
        course.setEndDate(courseDTO.getEndDate());
        return course;
    }

    public CourseDTO getCourseByExactTitle(String title) {
        Course course = courseRepository.findByTitle(title).orElseThrow(() -> new NoSuchElementException("Course not found with title: " + title));
        return convertToDTO(course);
    }

    public List<CourseDTO> getCoursesStartingAfter(LocalDate date) {
        return courseRepository.findByStartDateAfter(date).stream().map(this::convertToDTO).toList();
    }
}
