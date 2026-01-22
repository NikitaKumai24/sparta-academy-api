package com.sparta.spartaapi.webcontrollers;

import com.sparta.spartaapi.dtos.CourseDTO;
import com.sparta.spartaapi.dtos.TrainerDTO;
import com.sparta.spartaapi.services.CourseService;
import com.sparta.spartaapi.services.TrainerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseWebController.class)
@AutoConfigureMockMvc(addFilters = false)
class CourseWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @MockBean
    private TrainerService trainerService;

    @Test
    void shouldReturnCoursesIndexPage() throws Exception {
        CourseDTO course = new CourseDTO();
        course.setCourseId(1);
        course.setTitle("Java Bootcamp");

        when(courseService.getAllCourses()).thenReturn(List.of(course));

        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(view().name("courses/index"))
                .andExpect(model().attributeExists("courses"));
    }

    @Test
    void shouldReturnCourseViewPage() throws Exception {
        CourseDTO course = new CourseDTO();
        course.setCourseId(1);
        course.setTitle("Java Bootcamp");

        when(courseService.getCourseById(1)).thenReturn(course);
        when(trainerService.getAllTrainers()).thenReturn(List.of()); // controller adds trainers

        mockMvc.perform(get("/courses/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("courses/view"))
                .andExpect(model().attributeExists("course"))
                .andExpect(model().attributeExists("trainers"));
    }

    @Test
    void shouldReturnNewCoursePage() throws Exception {
        when(trainerService.getAllTrainers()).thenReturn(List.of()); // controller adds trainers

        mockMvc.perform(get("/courses/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("courses/new"))
                .andExpect(model().attributeExists("course"))
                .andExpect(model().attributeExists("trainers"));
    }
}
