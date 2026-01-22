package com.sparta.spartaapi.webcontrollers;

import com.sparta.spartaapi.dtos.TraineeDTO;
import com.sparta.spartaapi.entities.Trainee;
import com.sparta.spartaapi.services.CourseService;
import com.sparta.spartaapi.services.TraineeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/trainees")
public class TraineeWebController {
    private final TraineeService traineeService;
    private final CourseService courseService;

    public TraineeWebController(TraineeService traineeService, CourseService courseService) {
        this.traineeService = traineeService;
        this.courseService = courseService;
    }

    @GetMapping
    public String listTrainees(Model model) {
        model.addAttribute("trainees", traineeService.getAllTrainees());
        return "trainees/index";
    }

    @GetMapping("/new")
    public String addTraineePage(Model model) {
        model.addAttribute("trainee", new TraineeDTO());
        model.addAttribute("courses", courseService.getAllCourses());
        return "trainees/new";
    }

    @GetMapping("/{id}")
    public String viewTrainee(@PathVariable Integer id, Model model) {
        model.addAttribute("trainee", traineeService.getTraineeById(id));
        model.addAttribute("courses", courseService.getAllCourses());
        return "trainees/view";
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
