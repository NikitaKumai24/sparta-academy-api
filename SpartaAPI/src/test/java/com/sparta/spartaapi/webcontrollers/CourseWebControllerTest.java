package com.sparta.spartaapi.webcontrollers;

import com.sparta.spartaapi.dtos.CourseDTO;
import com.sparta.spartaapi.services.CourseService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseWebController.class)
@AutoConfigureMockMvc(addFilters = false)
class CourseWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

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

        mockMvc.perform(get("/courses/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("courses/view"))
                .andExpect(model().attributeExists("course"));
    }

    @Test
    void shouldReturnNewCoursePage() throws Exception {
        mockMvc.perform(get("/courses/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("courses/new"))
                .andExpect(model().attributeExists("course"));
    }

    @Test
    void shouldReturnEditCoursePage() throws Exception {
        CourseDTO course = new CourseDTO();
        course.setCourseId(1);
        course.setTitle("Java Bootcamp");

        when(courseService.getCourseById(1)).thenReturn(course);

        mockMvc.perform(get("/courses/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("courses/edit"))
                .andExpect(model().attributeExists("course"));
    }

    @Test
    void shouldUpdateCourseAndRedirect() throws Exception {
        mockMvc.perform(post("/courses/1/edit")
                        .param("title", "Updated Title")
                        .param("description", "Updated Desc")
                        .param("startDate", "2026-01-01")
                        .param("endDate", "2026-02-01")
                        .param("trainerId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courses"));

        verify(courseService).updateCourse(eq(1), any(CourseDTO.class));
    }


    @Test
    void shouldDeleteCourseAndRedirect() throws Exception {
        mockMvc.perform(post("/courses/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courses"));

        verify(courseService).deleteCourse(1);
    }


}