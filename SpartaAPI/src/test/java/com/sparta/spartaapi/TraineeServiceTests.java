package com.sparta.spartaapi;

import com.sparta.spartaapi.dtos.TraineeDTO;
import com.sparta.spartaapi.dtos.TraineeMapper;
import com.sparta.spartaapi.dtos.TrainerMapper;
import com.sparta.spartaapi.entities.Trainee;
import com.sparta.spartaapi.repositories.CourseRepository;
import com.sparta.spartaapi.repositories.TraineeRepository;
import com.sparta.spartaapi.services.TraineeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TraineeServiceTests {

    private final TraineeRepository mockRepository = Mockito.mock(TraineeRepository.class);
    private final TraineeMapper mockMapper = Mockito.mock(TraineeMapper.class);
    private final CourseRepository mockCourseRepository = Mockito.mock(CourseRepository.class);
    private final TrainerMapper mockTrainerMapper = Mockito.mock(TrainerMapper.class);
    private final TraineeService sut = new TraineeService(mockRepository, mockMapper, mockCourseRepository, mockTrainerMapper);

    @Test
    @DisplayName("Get all Trainees")
    public void getAllTraineesTest() {
        Trainee trainee1 = new Trainee();
        trainee1.setTraineeID(1);

        Trainee trainee2 = new Trainee();
        trainee2.setTraineeID(2);

        List<Trainee> trainees = new ArrayList<>();
        trainees.add(trainee1);
        trainees.add(trainee2);

        TraineeDTO dto1 = new TraineeDTO();
        dto1.setTraineeId(1);

        TraineeDTO dto2 = new TraineeDTO();
        dto2.setTraineeId(2);

        Mockito.when(mockRepository.findAll()).thenReturn(trainees);
        Mockito.when(mockMapper.toDto(trainee1)).thenReturn(dto1);
        Mockito.when(mockMapper.toDto(trainee2)).thenReturn(dto2);
        List<TraineeDTO> result = sut.getAllTrainees();
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(1, result.get(0).getTraineeId());
        Assertions.assertEquals(2, result.get(1).getTraineeId());
    }

    @Test
    @DisplayName("Get Trainee by ID")
    void getTraineeById() {
        Trainee trainee = new Trainee();
        trainee.setTraineeID(1);

        TraineeDTO dto = new TraineeDTO();
        dto.setTraineeId(1);

        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(trainee));
        Mockito.when(mockMapper.toDto(trainee)).thenReturn(dto);
        TraineeDTO result = sut.getTraineeById(1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getTraineeId());
    }

    @Test
    @DisplayName("Get Trainee by ID - Sad Path")
    void getTraineeByIdNotFound() {
        Mockito.when(mockRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class,
                () -> sut.getTraineeById(99)
        );
    }

    @Test
    @DisplayName("Create Trainee")
    void createTraineeTest() {
        TraineeDTO dto = new TraineeDTO();
        dto.setTraineeId(1);

        Trainee entity = new Trainee();
        entity.setTraineeID(1);

        Mockito.when(mockMapper.toEntity(dto)).thenReturn(entity);
        Mockito.when(mockRepository.save(entity)).thenReturn(entity);
        Mockito.when(mockMapper.toDto(entity)).thenReturn(dto);
        TraineeDTO result = sut.createTrainee(dto);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getTraineeId());
        Mockito.verify(mockRepository).save(entity);
    }

    @Test
    @DisplayName("Update Trainee ")
    void updateTraineeTest() {
        TraineeDTO dto = new TraineeDTO();
        dto.setTraineeId(1);

        Trainee entity = new Trainee();
        entity.setTraineeID(1);

        Mockito.when(mockRepository.existsById(1)).thenReturn(true);
        Mockito.when(mockMapper.toEntity(dto)).thenReturn(entity);
        Mockito.when(mockRepository.save(Mockito.any(Trainee.class))).thenReturn(entity);
        Mockito.when(mockMapper.toDto(entity)).thenReturn(dto);
        TraineeDTO result = sut.updateTrainee(1, dto);
        Assertions.assertEquals(1, result.getTraineeId());
    }

    @Test
    @DisplayName("Update Trainee - Sad Path")
    void updateTraineeNotFound() {
        TraineeDTO dto = new TraineeDTO();
        dto.setTraineeId(100);
        Mockito.when(mockRepository.existsById(100)).thenReturn(false);
        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> sut.updateTrainee(100, dto)
        );
    }

    @Test
    @DisplayName("Delete Trainee")
    void deleteTraineeTest() {
        Mockito.when(mockRepository.existsById(1)).thenReturn(true);
        sut.deleteTrainee(1);
        Mockito.verify(mockRepository).deleteById(1);
    }

}
