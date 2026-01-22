package com.sparta.spartaapi.webcontrollers;

import com.sparta.spartaapi.dtos.CourseDTO;
import com.sparta.spartaapi.services.CourseService;
import com.sparta.spartaapi.services.TrainerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/courses")
public class CourseWebController {

    private final CourseService courseService;
    private final TrainerService trainerService;

    public CourseWebController(CourseService courseService, TrainerService trainerService) {
        this.courseService = courseService;
        this.trainerService = trainerService;
    }

    @GetMapping
    public String listCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "courses/index";
    }

    @GetMapping("/new")
    public String showCreateCoursePage(Model model) {
        model.addAttribute("course", new CourseDTO());
        model.addAttribute("trainers", trainerService.getAllTrainers());
        return "courses/new";
    }

    @PostMapping("/new")
    public String createCourse(@ModelAttribute("course") CourseDTO createdCourse) {
        courseService.createCourse(createdCourse);
        return "redirect:/courses";
    }

    @GetMapping("/{id}")
    public String viewCourse(@PathVariable Integer id, Model model) {
        model.addAttribute("course", courseService.getCourseById(id));
        model.addAttribute("trainers", trainerService.getAllTrainers());
        return "courses/view";
    }

    @PostMapping("/{id}/update")
    public String updateCourse(@PathVariable Integer id,
                               @ModelAttribute("course") CourseDTO updatedCourse) {

        // ensure ID is set (helps if your form doesn't bind it)
        updatedCourse.setCourseId(id);

        courseService.updateCourse(id, updatedCourse);
        return "redirect:/courses";
    }

    @PostMapping("/{id}")
    public String deleteCourse(@PathVariable Integer id) {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }
}
