package com.YusufFakhreddin.ICDTicketingSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.ArrayList;

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

    //    add ownership to ticket
    private String owner;
    //    add assigned to ticket

    private String assignedUser;

    //    add Assigned Team
    private String assignedTeam;



    public Ticket(String title, String description, String status, String priority, String type, String date, String time, String id, String owner, String assignedUser, String assignedTeam) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.type = type;
        this.date = date;
        this.time = time;
        this.id = id;
        this.owner = owner;
        this.assignedUser = assignedUser;
        this.assignedTeam = assignedTeam;

    }

    public Ticket() {
        this.title = "";
        this.description = "";
        this.status = "";
        this.priority = "";
        this.type = "";
        this.date = "";
        this.time = "";
        this.id = "";
        this.owner = "";
        this.assignedUser = "";
        this.assignedTeam = "";

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
        return owner;}

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAssignedUser() {
        return assignedUser;}

    public void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
    }

    public String getAssignedTeam() {
        return assignedTeam;}

    public void setAssignedTeam(String assignedTeam) {
        this.assignedTeam = assignedTeam;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", assignedUser='" + assignedUser + '\'' +
                ", assignedTeam='" + assignedTeam + '\'' +
                '}';
    }


}
