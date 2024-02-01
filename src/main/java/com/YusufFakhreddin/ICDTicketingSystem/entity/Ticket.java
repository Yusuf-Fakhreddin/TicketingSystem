package com.YusufFakhreddin.ICDTicketingSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "title", nullable = false)
    @Size(min = 5,max=100, message = "title must be between 5 and 100 characters")
    private String title;
    @Column(name = "description", nullable = false)
    @Size(min = 5,max=1000, message = "description must be between 5 and 1000 characters")
    private String description;
    private String status;
    private String priority;
    private String type;
    private String date;
    private String time;

    private String owner;

    private String assigned_user;


    private String assigned_team;

    // link to comment
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private List<Comment> comments;

    public Ticket() {
    }

    public Ticket(String title, String description, String status, String priority, String type, String date, String time, String owner, String assigned_user, String assigned_team) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.type = type;
        this.date = date;
        this.time = time;
        this.owner = owner;
        this.assigned_user = assigned_user;
        this.assigned_team = assigned_team;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() { return status; }

    public String getPriority() {
        return priority;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getTime() { return time; }

    public String getId() { return id; }

    public void setTitle(String title) { this.title = title; }

    public void setDescription(String description) { this.description = description; }

    public void setStatus(String status) { this.status = status; }

    public void setPriority(String priority) { this.priority = priority; }

    public void setType(String type) { this.type = type; }

    public void setDate(String date) { this.date = date; }

    public void setTime(String time) { this.time = time; }

    public void setId(String id) { this.id = id; }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAssigned_user() {
        return assigned_user;
    }

    public void setAssigned_user(String assigned_user) {
        this.assigned_user = assigned_user;
    }

    public String getAssigned_team() {
        return assigned_team;
    }

    public void setAssigned_team(String assigned_team) {
        this.assigned_team = assigned_team;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    public void addComment(Comment comment) {
        comment.setTicket_id(this.id);
        this.comments.add(comment);
    }

    //    Define toString method
    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", owner='" + owner + '\'' +
                ", assigned_user='" + assigned_user + '\'' +
                ", assigned_team='" + assigned_team + '\'' +
                ", comments=" + comments +
                '}';
    }


}
