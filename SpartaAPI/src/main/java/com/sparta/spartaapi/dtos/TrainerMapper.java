package com.sparta.spartaapi.dtos;

import com.sparta.spartaapi.entities.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrainerMapper {

    @Mapping(source = "trainerID", target = "trainerId")
    @Mapping(source = "specialtyLang", target = "specialityLang")
    TrainerDTO toDTO(Trainer trainer);

    @Mapping(source = "trainerId", target = "trainerID")
    @Mapping(source = "specialityLang", target = "specialtyLang")
    Trainer toEntity(TrainerDTO trainerDTO);
}
