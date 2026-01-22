package com.sparta.spartaapi.webcontrollers;

import com.sparta.spartaapi.dtos.TrainerDTO;
import com.sparta.spartaapi.services.TrainerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/trainers")
public class TrainerWebController {

    private final TrainerService trainerService;

    public TrainerWebController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping
    public String listTrainers(Model model) {
        model.addAttribute("trainers", trainerService.getAllTrainers());
        return "trainers/index";
    }

    @GetMapping("/new")
    public String addTrainerPage(Model model) {
        model.addAttribute("trainer", new TrainerDTO());
        return "trainers/new";
    }

    @GetMapping("/{id}")
    public String viewTrainer(@PathVariable Integer id, Model model) {
        model.addAttribute("trainer", trainerService.getTrainerById(id));
        return "trainers/view";
    }

    @PostMapping("/new")
    public String addTrainer(@ModelAttribute TrainerDTO trainerDTO) {
        trainerService.saveTrainer(trainerDTO);
        return "redirect:/trainers";
    }

    @PostMapping("/{id}/update")
    public String updateTrainer(
            @PathVariable Integer id,
            @ModelAttribute TrainerDTO trainerDTO) {

        trainerDTO.setTrainerId(id);
        trainerService.updateTrainer(trainerDTO);
        return "redirect:/trainers";
    }

    @PostMapping("/{id}")
    public String deleteTrainer(@PathVariable Integer id) {
        trainerService.deleteTrainer(id);
        return "redirect:/trainers";
    }
}
