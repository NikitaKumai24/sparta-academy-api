package com.sparta.spartaapi.services;

import com.sparta.spartaapi.dtos.CourseDTO;
import com.sparta.spartaapi.entities.Course;
import com.sparta.spartaapi.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    //CRUD Operations

    //Get all courses
    public List<CourseDTO> getAllCourses(){
        List<Course> courses = this.courseRepository.findAll();
        return courses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    //Get a single course id
    public CourseDTO getCourseById(int id){
        Course course = this.courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        return convertToDTO(course);
    }



    //create course
    public CourseDTO createCourse(CourseDTO courseDTO){
        Course course = convertToEntity(courseDTO);
        Course savedCourse = this.courseRepository.save(course);
        return convertToDTO(savedCourse);
    }


    //update course
    public CourseDTO updateCourse(int id, CourseDTO courseDTO){
        Optional<Course> existingCourse = this.courseRepository.findById(id);

        if(existingCourse.isEmpty()){
            return null;
        }


        Course courseToUpdate = existingCourse.get();
        courseToUpdate.setTitle(courseDTO.getTitle());

        Course updatedCourse = this.courseRepository.save(courseToUpdate);
        return convertToDTO(updatedCourse);
    }

    //delete course
    public boolean deleteCourse(int id){
        if(this.courseRepository.existsById(id)){
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

    private CourseDTO convertToDTO(Course course){
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


    private Course convertToEntity(CourseDTO courseDTO){
        Course course = new Course();
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setStartDate(courseDTO.getStartDate());
        course.setEndDate(courseDTO.getEndDate());
        return course;
    }






}
