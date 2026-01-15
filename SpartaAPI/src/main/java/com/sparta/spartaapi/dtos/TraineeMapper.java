package com.sparta.spartaapi.dtos;

import com.sparta.spartaapi.entities.Trainee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TraineeMapper {
    TraineeDTO toDto(Trainee trainee);
    Trainee toEntity(TraineeDTO traineeDTO);
}
