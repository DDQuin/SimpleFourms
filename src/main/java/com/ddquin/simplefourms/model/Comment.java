package com.ddquin.simplefourms.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "comment is mandatory")
    @Lob
    private String content;

    @Column(updatable = false)
    private String username;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdOn;

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "thread_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Thread thread;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFormatTime() {
        String date = "";
        date += this.createdOn.getDayOfMonth() + " " + this.createdOn.getMonth().getDisplayName(TextStyle.SHORT, Locale.UK);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formatDateTime = this.createdOn.format(formatter);

        date += " " + formatDateTime;
        return date;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getUsername() {
        return username;
    }
    public String display() {
        return username + ": " + content;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
