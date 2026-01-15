package com.sparta.spartaapi;

import com.sparta.spartaapi.dtos.TrainerDTO;
import com.sparta.spartaapi.dtos.TrainerMapper;
import com.sparta.spartaapi.entities.Trainer;
import com.sparta.spartaapi.repositories.TrainerRepository;
import com.sparta.spartaapi.services.TrainerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TrainerServiceTests {

    private final TrainerRepository mockRepository = Mockito.mock(TrainerRepository.class);
    private final TrainerMapper mockMapper = Mockito.mock(TrainerMapper.class);
    private final TrainerService sut = new TrainerService(mockRepository, mockMapper);

    @Test
    @DisplayName("Get all Trainers")
    public void getAllTrainersTest() {
        Trainer trainer1 = new Trainer();
        trainer1.setTrainerID(1);

        Trainer trainer2 = new Trainer();
        trainer2.setTrainerID(2);

        List<Trainer> trainers = new ArrayList<>();
        trainers.add(trainer1);
        trainers.add(trainer2);

        TrainerDTO dto1 = new TrainerDTO();
        dto1.setTrainerId(1);

        TrainerDTO dto2 = new TrainerDTO();
        dto2.setTrainerId(2);

        Mockito.when(mockRepository.findAll()).thenReturn(trainers);
        Mockito.when(mockMapper.toDTO(trainer1)).thenReturn(dto1);
        Mockito.when(mockMapper.toDTO(trainer2)).thenReturn(dto2);

        List<TrainerDTO> result = sut.getAllTrainers();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(1, result.get(0).getTrainerId());
        Assertions.assertEquals(2, result.get(1).getTrainerId());

    }

    @Test
    @DisplayName("Get Trainer by ID - Happy Path")
    void getTrainerByIdHappyPath() {
        Trainer trainer = new Trainer();
        trainer.setTrainerID(1);

        TrainerDTO dto = new TrainerDTO();
        dto.setTrainerId(1);

        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(trainer));
        Mockito.when(mockMapper.toDTO(trainer)).thenReturn(dto);

        TrainerDTO result = sut.getTrainerById(1);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getTrainerId());
    }

    @Test
    @DisplayName("Get Trainer by ID - Not Found")
    void getTrainerByIdNotFound() {
        Mockito.when(mockRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class,
                () -> sut.getTrainerById(99)
        );
    }

    @Test
    @DisplayName("Save Trainer - Happy Path")
    void saveTrainerTest() {
        TrainerDTO dto = new TrainerDTO();
        dto.setTrainerId(1);

        Trainer entity = new Trainer();
        entity.setTrainerID(1);

        Mockito.when(mockMapper.toEntity(dto)).thenReturn(entity);
        Mockito.when(mockRepository.save(entity)).thenReturn(entity);
        Mockito.when(mockMapper.toDTO(entity)).thenReturn(dto);

        TrainerDTO result = sut.saveTrainer(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getTrainerId());
        Mockito.verify(mockRepository).save(entity);
    }

    @Test
    @DisplayName("Update Trainer - Happy Path")
    void updateTrainerTest() {
        TrainerDTO dto = new TrainerDTO();
        dto.setTrainerId(1);

        Trainer entity = new Trainer();
        entity.setTrainerID(1);

        Mockito.when(mockRepository.existsById(1)).thenReturn(true);
        Mockito.when(mockMapper.toEntity(dto)).thenReturn(entity);
        Mockito.when(mockRepository.save(entity)).thenReturn(entity);
        Mockito.when(mockMapper.toDTO(entity)).thenReturn(dto);


        TrainerDTO result = sut.updateTrainer(dto);

        Assertions.assertEquals(1, result.getTrainerId());
    }

    @Test
    @DisplayName("Update Trainer - Not Found")
    void updateTrainerNotFound() {
        TrainerDTO dto = new TrainerDTO();
        dto.setTrainerId(100);

        Mockito.when(mockRepository.existsById(100)).thenReturn(false);

        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> sut.updateTrainer(dto)
        );
    }

    @Test
    @DisplayName("Delete Trainer -Happy Path")
    void deleteTrainerTest() {
        Mockito.when(mockRepository.existsById(1)).thenReturn(true);

        sut.deleteTrainer(1);

        Mockito.verify(mockRepository).deleteById(1);
    }

    @Test
    @DisplayName("Delete Trainer - Not Found")
    void deleteTrainerNotFound() {
        Mockito.when(mockRepository.existsById(100)).thenReturn(false);

        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> sut.deleteTrainer(100)
        );
    }
}


