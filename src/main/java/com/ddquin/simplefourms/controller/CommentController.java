package com.ddquin.simplefourms.controller;


import com.ddquin.simplefourms.model.Comment;
import com.ddquin.simplefourms.model.Thread;
import com.ddquin.simplefourms.repository.CommentRepository;
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
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ThreadRepository threadRepository;



    @PostMapping("/createcomment/{id}")
    public String addComment(@PathVariable("id") long id, @Valid Comment comment, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/view_thread/" + id;
        }
        Thread thread = threadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Thread Id:" + id));
        comment.setThread(thread);
        commentRepository.save(comment);
        return "redirect:/view_thread/" + id;
    }
}
