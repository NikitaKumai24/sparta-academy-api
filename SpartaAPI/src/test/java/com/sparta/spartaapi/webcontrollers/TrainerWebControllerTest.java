package com.sparta.spartaapi.webcontrollers;

import com.sparta.spartaapi.dtos.TrainerDTO;
import com.sparta.spartaapi.services.TrainerService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TrainerWebController.class)
@AutoConfigureMockMvc(addFilters = false)
class TrainerWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainerService trainerService;

    @Test
    void shouldReturnTrainersIndexPage() throws Exception {
        TrainerDTO trainer = new TrainerDTO();
        trainer.setTrainerId(1);

        when(trainerService.getAllTrainers()).thenReturn(List.of(trainer));

        mockMvc.perform(get("/trainers"))
                .andExpect(status().isOk())
                .andExpect(view().name("trainers/index"))
                .andExpect(model().attributeExists("trainers"));
    }

    @Test
    void shouldReturnTrainerViewPage() throws Exception {
        TrainerDTO trainer = new TrainerDTO();
        trainer.setTrainerId(1);

        when(trainerService.getTrainerById(1)).thenReturn(trainer);

        mockMvc.perform(get("/trainers/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trainers/view"))
                .andExpect(model().attributeExists("trainer"));
    }

    @Test
    void shouldReturnNewTrainerPage() throws Exception {
        mockMvc.perform(get("/trainers/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("trainers/new"))
                .andExpect(model().attributeExists("trainer"));
    }

    @Test
    void shouldUpdateTrainerAndRedirect() throws Exception {
        mockMvc.perform(post("/trainers/1/update")
                        .param("trainerId", "999") // will be overwritten by controller
                        .param("firstName", "Updated")
                        .param("lastName", "Trainer")
                        .param("email", "updated@sparta.com")
                        .param("specialty", "Java"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trainers"));

        ArgumentCaptor<TrainerDTO> captor = ArgumentCaptor.forClass(TrainerDTO.class);
        verify(trainerService).updateTrainer(captor.capture());

        assertThat(captor.getValue().getTrainerId()).isEqualTo(1);
    }

    @Test
    void shouldDeleteTrainerAndRedirect() throws Exception {
        mockMvc.perform(post("/trainers/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trainers"));

        verify(trainerService).deleteTrainer(1);
    }

    //  Search trainers
    @Test
    void shouldSearchTrainersAndReturnIndexPage() throws Exception {
        TrainerDTO trainer = new TrainerDTO();
        trainer.setTrainerId(1);

        // Change this to match your real service method name:
        when(trainerService.filterByName_and_specialty("java", "java"))
                .thenReturn(List.of(trainer));

        mockMvc.perform(get("/trainers/search").param("query", "java"))
                .andExpect(status().isOk())
                .andExpect(view().name("trainers/index"))
                .andExpect(model().attributeExists("trainers"));

        verify(trainerService).filterByName_and_specialty("java", "java");
    }
}
