package com.sparta.spartaapi.webcontrollers;

import com.sparta.spartaapi.dtos.TrainerDTO;
import com.sparta.spartaapi.services.TrainerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/trainers")
public class TrainerWebController {

    private final TrainerService trainerService;

    public TrainerWebController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping
    public String listTrainers(Model model) {
        model.addAttribute("trainers", trainerService.getAllTrainers());
        return "trainers/list";
    }

    @GetMapping("/{id}")
    public String viewTrainer(@PathVariable Integer id, Model model) {
        model.addAttribute("trainer", trainerService.getTrainerById(id));
        return "trainers/details";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("trainer", new TrainerDTO());
        return "trainers/form";
    }

    @PostMapping
    public String createTrainer(@ModelAttribute("trainer") TrainerDTO trainerDTO) {
        trainerService.saveTrainer(trainerDTO);
        return "redirect:/api/trainers";
    }

    @PostMapping("/{id}/delete")
    public String deleteTrainer(@PathVariable Integer id) {
        trainerService.deleteTrainer(id);
        return "redirect:/api/trainers";
    }
}
