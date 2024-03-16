package com.YusufFakhreddin.ICDTicketingSystem.entity;

import ch.qos.logback.core.net.SMTPAppenderBase;
import com.YusufFakhreddin.ICDTicketingSystem.enums.TicketPriority;
import com.YusufFakhreddin.ICDTicketingSystem.enums.TicketStatus;
import com.YusufFakhreddin.ICDTicketingSystem.enums.TicketType;
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

    @Column(name = "resolution")
    private String resolution;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Enumerated(EnumType.STRING)
    private TicketPriority priority;

    @Enumerated(EnumType.STRING)
    private TicketType type;

    private String date;
    private String time;

    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
@JsonManagedReference
    private User owner;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_team_id", nullable = false)
    @JsonBackReference
    private Team ownerTeam;

    @ManyToOne
    @JoinColumn(name = "assigned_user", referencedColumnName = "username")
    private User assignedUser;


    @ManyToOne
    @JoinColumn(name = "assigned_team_id", referencedColumnName = "id")
    private Team assignedTeam;

    // link to comment
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private List<Comment> comments;

    public void addComment(Comment comment) {
        comment.setTicket_id(this.id);
        this.comments.add(comment);
    }


}