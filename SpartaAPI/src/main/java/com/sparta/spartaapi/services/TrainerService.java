package com.sparta.spartaapi.services;

import com.sparta.spartaapi.dtos.TraineeDTO;
import com.sparta.spartaapi.dtos.TrainerDTO;
import com.sparta.spartaapi.dtos.TrainerMapper;
import com.sparta.spartaapi.entities.Trainee;
import com.sparta.spartaapi.entities.Trainer;
import com.sparta.spartaapi.repositories.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;

    public TrainerService(TrainerRepository trainerRepository,
                          TrainerMapper trainerMapper) {

        if (trainerRepository == null || trainerMapper == null) {
            throw new IllegalArgumentException("Repository and Mapper cannot be null");
        }
        this.trainerRepository = trainerRepository;
        this.trainerMapper = trainerMapper;
    }


    public List<TrainerDTO> getAllTrainers() {
        return trainerRepository.findAll()
                .stream()
                .map(trainerMapper::toDTO)
                .toList();
    }

    public TrainerDTO getTrainerById(Integer id) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Trainer not found"));
        return trainerMapper.toDTO(trainer);
    }

    public TrainerDTO saveTrainer(TrainerDTO trainerDTO) {
        Trainer entity = trainerMapper.toEntity(trainerDTO);
        Trainer saved = trainerRepository.save(entity);
        return trainerMapper.toDTO(saved);
    }

    public TrainerDTO updateTrainer(TrainerDTO trainerDTO) {
        Integer id = trainerDTO.getTrainerId();

        if (!trainerRepository.existsById(id)) {
            throw new NoSuchElementException("Trainer with ID " + id + " does not exist.");
        }

        Trainer entity = trainerMapper.toEntity(trainerDTO);
        Trainer saved = trainerRepository.save(entity);
        return trainerMapper.toDTO(saved);
    }

    public void deleteTrainer(Integer id) {
        if (!trainerRepository.existsById(id)) {
            throw new NoSuchElementException("Trainer not found");
        }
        trainerRepository.deleteById(id);
    }

    public List<TrainerDTO> searchByFirstName(String firstName) {
        return trainerRepository.findByFirstNameContainingIgnoreCase(firstName).stream().map(trainerMapper::toDTO).toList();
    }

    public List<TrainerDTO> searchByLastName(String lastName) {
        return trainerRepository.findByLastNameContainingIgnoreCase(lastName).stream().map(trainerMapper::toDTO).toList();
    }

    public List<TrainerDTO> searchBySpecialtyLang(String lang) {
        return trainerRepository.findBySpecialtyLangContainingIgnoreCase(lang).stream().map(trainerMapper::toDTO).toList();
    }

    public List<TrainerDTO> filterByName_and_specialty(String name, String specialty){
        List<Trainer> filteredTrainers = trainerRepository.findByFirstNameContainingIgnoreCaseOrSpecialtyLangContainingIgnoreCase(name, specialty);
        return filteredTrainers.stream()
                .map(trainerMapper::toDTO)
                .collect(Collectors.toList());
    }
}
