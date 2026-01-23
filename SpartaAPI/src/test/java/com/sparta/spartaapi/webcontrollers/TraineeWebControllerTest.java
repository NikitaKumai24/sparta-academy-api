package com.sparta.spartaapi.webcontrollers;

import com.sparta.spartaapi.dtos.TraineeDTO;
import com.sparta.spartaapi.services.CourseService;
import com.sparta.spartaapi.services.TraineeService;
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

@WebMvcTest(TraineeWebController.class)
@AutoConfigureMockMvc(addFilters = false)
class TraineeWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TraineeService traineeService;

    @MockBean
    private CourseService courseService;

    @Test
    void shouldReturnTraineesIndexPage() throws Exception {
        TraineeDTO trainee = new TraineeDTO();
        trainee.setTraineeId(1);

        when(traineeService.getAllTrainees()).thenReturn(List.of(trainee));

        mockMvc.perform(get("/trainees"))
                .andExpect(status().isOk())
                .andExpect(view().name("trainees/index"))
                .andExpect(model().attributeExists("trainees"));
    }

    @Test
    void shouldReturnTraineeViewPage() throws Exception {
        TraineeDTO trainee = new TraineeDTO();
        trainee.setTraineeId(1);

        when(traineeService.getTraineeById(1)).thenReturn(trainee);
        when(courseService.getAllCourses()).thenReturn(List.of());

        mockMvc.perform(get("/trainees/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trainees/view"))
                .andExpect(model().attributeExists("trainee"))
                .andExpect(model().attributeExists("courses"));
    }

    @Test
    void shouldReturnNewTraineePage() throws Exception {
        when(courseService.getAllCourses()).thenReturn(List.of());

        mockMvc.perform(get("/trainees/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("trainees/new"))
                .andExpect(model().attributeExists("trainee"))
                .andExpect(model().attributeExists("courses"));
    }


    @Test
    void shouldSearchTraineesAndReturnIndexPage() throws Exception {
        TraineeDTO trainee = new TraineeDTO();
        trainee.setTraineeId(1);

        when(traineeService.filterByName_and_specialty("rupe", "rupe"))
                .thenReturn(List.of(trainee));

        mockMvc.perform(get("/trainees/search")
                        .param("query", "rupe"))
                .andExpect(status().isOk())
                .andExpect(view().name("trainees/index"))
                .andExpect(model().attributeExists("trainees"));

        verify(traineeService).filterByName_and_specialty("rupe", "rupe");
    }




    @Test
    void shouldCreateTraineeAndRedirect() throws Exception {
        mockMvc.perform(post("/trainees/new")
                        .param("name", "Rupert Buffalo")
                        .param("speciality", "Python")
                        .param("email", "rupe@sparta.com")
                        .param("phoneNumber", "07034864011")
                        .param("courseId", "3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trainees"));

        verify(traineeService).createTrainee(any(TraineeDTO.class));
    }


    @Test
    void shouldReturnTraineeViewPage_ForEditing() throws Exception {
        TraineeDTO trainee = new TraineeDTO();
        trainee.setTraineeId(1);

        when(traineeService.getTraineeById(1)).thenReturn(trainee);
        when(courseService.getAllCourses()).thenReturn(List.of());

        mockMvc.perform(get("/trainees/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trainees/view"))
                .andExpect(model().attributeExists("trainee"))
                .andExpect(model().attributeExists("courses"));
    }



    @Test
    void shouldUpdateTraineeAndRedirect() throws Exception {
        mockMvc.perform(post("/trainees/1/update")
                        .param("firstName", "Updated")
                        .param("lastName", "Name")
                        .param("email", "updated@sparta.com")
                        .param("phoneNumber", "07000000000")
                        .param("specialityLang", "Python")
                        .param("courseId", "3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trainees"));

        verify(traineeService).updateTrainee(eq(1), any(TraineeDTO.class));
    }


    @Test
    void shouldDeleteTraineeAndRedirect() throws Exception {
        mockMvc.perform(post("/trainees/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trainees"));

        verify(traineeService).deleteTrainee(1);
    }

}
