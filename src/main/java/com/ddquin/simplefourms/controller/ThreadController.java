package com.ddquin.simplefourms.controller;

import com.ddquin.simplefourms.model.Thread;
import com.ddquin.simplefourms.repository.ThreadRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ThreadController {

    private final ThreadRepository threadRepository;

    public ThreadController(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }


    @GetMapping("/index")
    public String showThreads(Model model) {
        model.addAttribute("threads", threadRepository.findAll());
        return "index";
    }


    @GetMapping("/edit_thread/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Thread thread = threadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Thread Id:" + id));

        model.addAttribute("thread", thread);
        return "update-thread";
    }

    @GetMapping("/createthread")
    public String showCreateThreadForm(Thread thread) {
        return "add-thread";
    }

    @PostMapping("/addthread")
    public String addThread(@Valid Thread thread, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-thread";
        }

        threadRepository.save(thread);
        return "redirect:/index";
    }

    @PostMapping("/update_thread/{id}")
    public String updateThread(@PathVariable("id") long id, @Valid Thread thread,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            thread.setId(id);
            return "update-thread";
        }

        threadRepository.save(thread);
        return "redirect:/index";
    }

    @GetMapping("/delete_thread/{id}")
    public String deleteThread(@PathVariable("id") long id, Model model) {
        Thread thread = threadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Thread Id:" + id));
        threadRepository.delete(thread);
        return "redirect:/index";
    }




    // additional CRUD methods
}
