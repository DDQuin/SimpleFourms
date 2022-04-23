package com.ddquin.simplefourms.controller;


import com.ddquin.simplefourms.model.Section;
import com.ddquin.simplefourms.model.Thread;
import com.ddquin.simplefourms.repository.SectionRepository;
import com.ddquin.simplefourms.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class SectionController {

    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private ThreadRepository threadRepository;

    @GetMapping("/index")
    public String showSections(Model model) {
        model.addAttribute("sections", sectionRepository.findAll());

        return "index";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {

        return "login";
    }

    @GetMapping("/")
    public String showSectionsNormal(Model model) {
        return "redirect:/index";
    }

    @GetMapping("/edit_section/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Section Id:" + id));

        model.addAttribute("section", section);
        return "update-section";
    }

    @GetMapping("/createsection")
    public String showCreateSectionForm(Model model, Section section) {
        return "add-section";
    }

    @PostMapping("/addsection")
    public String addSection(@Valid Section section, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-section";
        }
        sectionRepository.save(section);
        return "redirect:/index";
    }

    @PostMapping("/update_section/{id}")
    public String updateSection(@PathVariable("id") long id, @Valid Section section,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            section.setId(id);
            return "update-section";
        }
        // Save old section num threads before updating
        Section oldSection = sectionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Section Id:" + id));
        section.setNumThreads(oldSection.getNumThreads());
        sectionRepository.save(section);
        return "redirect:/index";
    }

    @GetMapping("/delete_section/{id}")
    public String deleteSection(@PathVariable("id") long id, Model model) {

        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Section Id:" + id));
        sectionRepository.delete(section);
        return "redirect:/index";
    }
}
