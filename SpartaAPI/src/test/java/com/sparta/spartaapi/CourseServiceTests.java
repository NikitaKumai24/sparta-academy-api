package com.sparta.spartaapi.services;

import com.sparta.spartaapi.dtos.CourseDTO;
import com.sparta.spartaapi.entities.Course;
import com.sparta.spartaapi.repositories.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    private Course course;
    private CourseDTO courseDTO;

    @BeforeEach
    void setUp() {
        // Given - setup test data
        course = new Course();
        course.setCourseId(1);
        course.setTitle("Nish's SQL");
        course.setDescription("Learn APIs");
        course.setStartDate(LocalDate.of(2026, 2, 1));
        course.setEndDate(LocalDate.of(2026, 5, 1));

        courseDTO = new CourseDTO();
        course.setCourseId(1);
        courseDTO.setTitle("Nish's SQL");
        courseDTO.setDescription("Learn APIs");
        courseDTO.setStartDate(LocalDate.of(2026, 2, 1));
        courseDTO.setEndDate(LocalDate.of(2026, 5, 1));
    }

    // Test for getAllCourses method
    @DisplayName("JUnit test for getAllCourses method")
    @Test
    void givenCoursesList_whenGetAllCourses_thenReturnCoursesDTOList() {
        // Given
        Course course2 = new Course();
        course2.setTitle("Python Bootcamp");
        course2.setDescription("Learn Python");
        course2.setStartDate(LocalDate.of(2026, 3, 1));
        course2.setEndDate(LocalDate.of(2026, 6, 1));

        given(courseRepository.findAll()).willReturn(Arrays.asList(course, course2));

        // When
        List<CourseDTO> coursesList = courseService.getAllCourses();

        // Then
        assertThat(coursesList).isNotNull();
        assertThat(coursesList.size()).isEqualTo(2);
        assertThat(coursesList.get(0).getTitle()).isEqualTo("Nish's SQL");
        assertThat(coursesList.get(1).getTitle()).isEqualTo("Python Bootcamp");
    }

    @DisplayName("JUnit test for getAllCourses method (empty list)")
    @Test
    void givenEmptyCoursesList_whenGetAllCourses_thenReturnEmptyList() {
        // Given
        given(courseRepository.findAll()).willReturn(Collections.emptyList());

        // When
        List<CourseDTO> coursesList = courseService.getAllCourses();

        // Then
        assertThat(coursesList).isEmpty();
        assertThat(coursesList.size()).isEqualTo(0);
    }

    // Test for getCourseById method
    @DisplayName("JUnit test for getCourseById method (success)")
    @Test
    void givenCourseId_whenGetCourseById_thenReturnCourseDTO() {
        // Given
        given(courseRepository.findById(1)).willReturn(Optional.of(course));

        // When
        CourseDTO foundCourse = courseService.getCourseById(1);

        // Then
        assertThat(foundCourse).isNotNull();
        assertThat(foundCourse.getCourseId()).isEqualTo(1);
        assertThat(foundCourse.getTitle()).isEqualTo("Nish's SQL");
        verify(courseRepository, times(1)).findById(1);
    }

    @DisplayName("JUnit test for getCourseById method (not found)")
    @Test
    void givenNonExistingCourseId_whenGetCourseById_thenThrowException() {
        // Given
        given(courseRepository.findById(999)).willReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            courseService.getCourseById(999);
        });

        verify(courseRepository, times(1)).findById(999);
    }

    // Test for createCourse method
    @DisplayName("JUnit test for createCourse method")
    @Test
    void givenCourseDTO_whenCreateCourse_thenReturnSavedCourseDTO() {
        // Given
        given(courseRepository.save(any(Course.class))).willReturn(course);

        // When
        CourseDTO savedCourse = courseService.createCourse(courseDTO);

        // Then
        assertThat(savedCourse).isNotNull();
        assertThat(savedCourse.getTitle()).isEqualTo("Nish's SQL");
        assertThat(savedCourse.getDescription()).isEqualTo("Learn APIs");
        verify(courseRepository, times(1)).save(any(Course.class));
    }



    @DisplayName("JUnit test for updateCourse method (not found)")
    @Test
    void givenNonExistingCourseId_whenUpdateCourse_thenThrowException() {
        // Given
        given(courseRepository.findById(999)).willReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            courseService.updateCourse(999, courseDTO);
        });

        verify(courseRepository, times(1)).findById(999);
        verify(courseRepository, never()).save(any(Course.class));
    }

    // Test for deleteCourse method
    @DisplayName("JUnit test for deleteCourse method (success)")
    @Test
    void givenCourseId_whenDeleteCourse_thenReturnTrue() {
        // Given
        int courseId = 1;
        given(courseRepository.existsById(courseId)).willReturn(true);
        willDoNothing().given(courseRepository).deleteById(courseId);

        // When
        boolean isDeleted = courseService.deleteCourse(courseId);

        // Then
        assertThat(isDeleted).isTrue();
        verify(courseRepository, times(1)).existsById(courseId);
        verify(courseRepository, times(1)).deleteById(courseId);
    }

    @DisplayName("JUnit test for deleteCourse method (not found)")
    @Test
    void givenNonExistingCourseId_whenDeleteCourse_thenReturnFalse() {
        // Given
        int courseId = 999;
        given(courseRepository.existsById(courseId)).willReturn(false);

        // When
        boolean isDeleted = courseService.deleteCourse(courseId);

        // Then
        assertThat(isDeleted).isFalse();
        verify(courseRepository, times(1)).existsById(courseId);
        verify(courseRepository, never()).deleteById(courseId);
    }
}
