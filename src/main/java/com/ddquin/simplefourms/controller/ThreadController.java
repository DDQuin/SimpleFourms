package com.ddquin.simplefourms.controller;

import com.ddquin.simplefourms.model.Comment;
import com.ddquin.simplefourms.model.Section;
import com.ddquin.simplefourms.model.Thread;
import com.ddquin.simplefourms.repository.CommentRepository;
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
import java.util.List;

@Controller
public class ThreadController {

    @Autowired
    private ThreadRepository threadRepository;
    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/show_threads/{section_id}")
    public String showThreads(Model model, @PathVariable long section_id) {
        List<Thread> threadList = threadRepository.findBySectionId(section_id);
        Section section = sectionRepository.findById(section_id).orElseThrow(() -> new IllegalArgumentException("Invalid Section Id:" + section_id));
        model.addAttribute("threads", threadList);
        model.addAttribute("section", section);
        return "show-threads";
    }

    @GetMapping("/view_thread/{id}")
    public String showThread(@PathVariable("id") long id, Model model, Comment comment) {
        Thread thread = threadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Thread Id:" + id));

        model.addAttribute("thread", thread);
        model.addAttribute("section", thread.getSection());
        List<Comment> commentList = commentRepository.findByThreadId(id);
        model.addAttribute("comments", commentList);
        return "view-thread";
    }


    @GetMapping("/edit_thread/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Thread thread = threadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Thread Id:" + id));

        model.addAttribute("thread", thread);
        return "update-thread";
    }

    @GetMapping("/createthread/{section_id}")
    public String showCreateThreadForm(@PathVariable("section_id") long section_id, Model model, Thread thread) {
        Section section = sectionRepository.findById(section_id).orElseThrow(() -> new IllegalArgumentException("Invalid section Id:" + section_id));
        model.addAttribute("section", section);
        return "add-thread";
    }

    @PostMapping("/addthread/{section_id}")
    public String addThread(@Valid Thread thread, BindingResult result, Model model, @PathVariable long section_id) {
        if (result.hasErrors()) {
            return "redirect:/show_threads/" + section_id ;
        }
        Section section = sectionRepository.findById(section_id).orElseThrow(() -> new IllegalArgumentException("Invalid section Id:" + section_id));
        thread.setSection(section);
        section.setNumThreads(section.getNumThreads() + 1);
        sectionRepository.save(section);
        threadRepository.save(thread);
        return "redirect:/show_threads/" + section_id ;
    }

    @PostMapping("/update_thread/{id}")
    public String updateThread(@PathVariable("id") long id, @Valid Thread thread,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            thread.setId(id);
            return "update-thread";
        }
        long sectionID = threadRepository.getById(id).getSection().getId();
        threadRepository.save(thread);
        return "redirect:/show_threads/" + sectionID;
    }

    @GetMapping("/delete_thread/{id}")
    public String deleteThread(@PathVariable("id") long id, Model model) {

        Thread thread = threadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Thread Id:" + id));
        long sectionID = thread.getSection().getId();
        thread.getSection().setNumThreads(thread.getSection().getNumThreads() - 1);
        sectionRepository.save(thread.getSection());
        threadRepository.delete(thread);
        return "redirect:/show_threads/" + sectionID;
    }




    // additional CRUD methods
}
