package com.sparta.spartaapi.dtos;

import com.sparta.spartaapi.entities.Trainer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainerMapper {
    TrainerDTO toDTO(Trainer trainer);
    Trainer toEntity(TrainerDTO trainerDTO);
}