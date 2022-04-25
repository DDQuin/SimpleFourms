package com.ddquin.simplefourms.controller;


import com.ddquin.simplefourms.exceptions.NoAccessException;
import com.ddquin.simplefourms.model.Comment;
import com.ddquin.simplefourms.model.Thread;
import com.ddquin.simplefourms.repository.CommentRepository;
import com.ddquin.simplefourms.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        comment.setUsername(authentication.getName());
        commentRepository.save(comment);
        return "redirect:/view_thread/" + id;
    }

    @GetMapping("/deletecomment/{id}")
    public String deleteComment(@PathVariable("id") long id, Model model) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid comment id: " + id));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        if (!comment.getUsername().equals(user)) {
            throw new NoAccessException();
        }
        long threadID = comment.getThread().getId();
        commentRepository.delete(comment);
        return "redirect:/view_thread/" + threadID;
    }


}
