package com.sparta.spartaapi.webcontrollers;

import com.sparta.spartaapi.dtos.CourseDTO;
import com.sparta.spartaapi.services.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/courses")
public class CourseWebController {

    private final CourseService courseService;

    public CourseWebController(CourseService courseService) {
        this.courseService = courseService;
    }

    // LIST ALL COURSES
    @GetMapping
    public String listCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "courses/index";
    }

    // SHOW CREATE COURSE PAGE
    @GetMapping("/new")
    public String addCoursePage(Model model) {
        model.addAttribute("course", new CourseDTO());
        return "courses/new";
    }

    // VIEW SINGLE COURSE
    @GetMapping("/{id}")
    public String viewCourse(@PathVariable Integer id, Model model) {
        model.addAttribute("course", courseService.getCourseById(id));
        return "courses/view";
    }

    // CREATE COURSE
    @PostMapping("/new")
    public String addCourse(@ModelAttribute CourseDTO createdCourse) {
        courseService.createCourse(createdCourse);
        return "redirect:/courses";
    }

    // UPDATE COURSE
    @PostMapping("/{id}/update")
    public String updateCourse(
            @PathVariable Integer id,
            @ModelAttribute CourseDTO updatedCourse
    ) {
        courseService.updateCourse(id, updatedCourse);
        return "redirect:/courses";
    }

    // DELETE COURSE
    @PostMapping("/{id}")
    public String deleteCourse(@PathVariable Integer id) {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }
}
