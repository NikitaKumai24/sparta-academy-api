package com.sparta.spartaapi.webcontrollers;

import com.sparta.spartaapi.dtos.TraineeDTO;
import com.sparta.spartaapi.entities.Trainee;
import com.sparta.spartaapi.services.TraineeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/trainees")
public class TraineeWebController {
    private final TraineeService traineeService;

    public TraineeWebController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @GetMapping
    public String listTrainees(Model model) {
        model.addAttribute("trainees", traineeService.getAllTrainees());
        return "trainees/list";
    }

    @GetMapping("/{id}")
    public String viewTrainee(@PathVariable Integer id, Model model) {
        model.addAttribute("trainee", traineeService.getTraineeById(id));
        return "trainees/view";
    }

    @GetMapping("/new")
    public String addTraineePage(Model model) {
        model.addAttribute("trainee", new Trainee());
        return "trainees/new";
    }

    @PostMapping("/new")
    public String addTrainee(@ModelAttribute TraineeDTO createdTrainee) {
        traineeService.createTrainee(createdTrainee);
        return "redirect:/trainees";
    }

    @PostMapping("/{id}/update")
    public String updateTrainee(@PathVariable Integer id, @ModelAttribute TraineeDTO updatedTrainee) {
        traineeService.updateTrainee(id, updatedTrainee);
        return "redirect:/trainees";
    }

    @PostMapping("/{id}")
    public String deleteTrainee(@PathVariable Integer id) {
        traineeService.deleteTrainee(id);
        return "redirect:/trainees";
    }
}
