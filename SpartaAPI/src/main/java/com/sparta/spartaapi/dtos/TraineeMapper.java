package com.sparta.spartaapi.dtos;

import com.sparta.spartaapi.entities.Trainee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TraineeMapper {

    @Mapping(source = "traineeID", target = "traineeId")
    @Mapping(source = "specialtyLang", target = "specialityLang")
    @Mapping(source = "course.courseId", target = "courseId")
    TraineeDTO toDto(Trainee trainee);

    @Mapping(source = "traineeId", target = "traineeID")
    @Mapping(source = "specialityLang", target = "specialtyLang")
    @Mapping(target = "course", ignore = true)
    Trainee toEntity(TraineeDTO traineeDTO);
}
