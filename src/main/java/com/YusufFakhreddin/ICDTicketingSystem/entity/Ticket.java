package com.YusufFakhreddin.ICDTicketingSystem.entity;

import ch.qos.logback.core.net.SMTPAppenderBase;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tickets")
@NoArgsConstructor
@Data
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

    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
@JsonManagedReference
    private User owner;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "owner_team_id", nullable = false)
    @JsonBackReference
    private Team ownerTeam;
    private String assigned_user;


    private String assigned_team;

    // link to comment
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private List<Comment> comments;

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