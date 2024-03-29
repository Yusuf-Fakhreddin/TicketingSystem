package com.YusufFakhreddin.TicketingSystem.ticket;

import com.YusufFakhreddin.TicketingSystem.attachment.Attachment;
import com.YusufFakhreddin.TicketingSystem.comment.Comment;
import com.YusufFakhreddin.TicketingSystem.team.Team;
import com.YusufFakhreddin.TicketingSystem.user.User;
import com.YusufFakhreddin.TicketingSystem.ticket.enums.TicketPriority;
import com.YusufFakhreddin.TicketingSystem.ticket.enums.TicketStatus;
import com.YusufFakhreddin.TicketingSystem.ticket.enums.TicketType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tickets")
@NoArgsConstructor
@Getter
@Setter
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
    private TicketStatus status= TicketStatus.QUEUED;

    @Enumerated(EnumType.STRING)
    private TicketPriority priority;

    @Enumerated(EnumType.STRING)
    private TicketType type;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }


    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
@JsonManagedReference
    private User owner;


    @ManyToOne(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_team_id", nullable = false)
    @JsonBackReference
    private Team ownerTeam;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "assigned_user", referencedColumnName = "username")
    private User assignedUser;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "assigned_team_id", referencedColumnName = "id")
    private Team assignedTeam;

    // link to comment
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private List<Comment> comments;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ticket_id")
    private List<Attachment> attachments= new ArrayList<>();;

    public void addComment(Comment comment) {
        comment.setTicket_id(this.id);
        this.comments.add(comment);
    }


}