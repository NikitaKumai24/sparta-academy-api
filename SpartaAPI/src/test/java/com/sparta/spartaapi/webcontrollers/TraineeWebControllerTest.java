package com.sparta.spartaapi.webcontrollers;

import com.sparta.spartaapi.dtos.TraineeDTO;
import com.sparta.spartaapi.services.CourseService;
import com.sparta.spartaapi.services.TraineeService;
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

        when(traineeService.getAllTrainees())
                .thenReturn(List.of(trainee));

        mockMvc.perform(get("/trainees"))
                .andExpect(status().isOk())
                .andExpect(view().name("trainees/index"))
                .andExpect(model().attributeExists("trainees"));
    }

    @Test
    void shouldReturnTraineeViewPage() throws Exception {
        TraineeDTO trainee = new TraineeDTO();
        trainee.setTraineeId(1);

        when(traineeService.getTraineeById(1))
                .thenReturn(trainee);
        when(courseService.getAllCourses())
                .thenReturn(List.of());

        mockMvc.perform(get("/trainees/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trainees/view"))
                .andExpect(model().attributeExists("trainee"))
                .andExpect(model().attributeExists("courses"));
    }


    @Test
    void shouldReturnNewTraineePage() throws Exception {
        when(courseService.getAllCourses())
                .thenReturn(List.of());

        mockMvc.perform(get("/trainees/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("trainees/new"))
                .andExpect(model().attributeExists("trainee"))
                .andExpect(model().attributeExists("courses"));
    }



}
