package com.sparta.spartaapi.webcontrollers;

import com.sparta.spartaapi.dtos.TrainerDTO;
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

        when(trainerService.getAllTrainers())
                .thenReturn(List.of(trainer));

        mockMvc.perform(get("/trainers"))
                .andExpect(status().isOk())
                .andExpect(view().name("trainers/index"))
                .andExpect(model().attributeExists("trainers"));
    }

    @Test
    void shouldReturnTrainerViewPage() throws Exception {
        TrainerDTO trainer = new TrainerDTO();
        trainer.setTrainerId(1);

        when(trainerService.getTrainerById(1))
                .thenReturn(trainer);

        mockMvc.perform(get("/trainers/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trainers/view"))
                .andExpect(model().attributeExists("trainer"));
    }
}
