package com.sparta.spartaapi.webcontrollers;

import com.sparta.spartaapi.dtos.CourseDTO;
import com.sparta.spartaapi.services.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseWebController {

    private final CourseService courseService;

    public CourseWebController(CourseService courseService) {
        this.courseService = courseService;
    }


    @GetMapping
    public String list(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "courses/index";
    }


    @GetMapping("/{id}")
    public String view(@PathVariable int id, Model model) {
        model.addAttribute("course", courseService.getCourseById(id));
        return "courses/view";
    }


    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("course", new CourseDTO());
        return "courses/new";
    }


    @PostMapping("/new")
    public String createSubmit(@ModelAttribute("course") CourseDTO courseDTO) {
        courseService.createCourse(courseDTO);
        return "redirect:/courses";
    }


    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable int id, Model model) {
        model.addAttribute("course", courseService.getCourseById(id));
        return "courses/edit";
    }


    @PostMapping("/{id}/edit")
    public String editSubmit(@PathVariable int id, @ModelAttribute("course") CourseDTO courseDTO) {
        courseService.updateCourse(id, courseDTO);
        return "redirect:/courses";
    }


    @PostMapping("/{id}/delete")
    public String delete(@PathVariable int id) {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }


    @GetMapping("/search")
    public String searchCourses(@RequestParam("query") String query, Model model) {

        List<CourseDTO> searchResults = courseService.filterByTitle_and_description(query, query);
        model.addAttribute("courses", searchResults);
        return "courses/index";
    }
}
