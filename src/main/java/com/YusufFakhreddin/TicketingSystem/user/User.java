package com.YusufFakhreddin.TicketingSystem.user;

import com.YusufFakhreddin.TicketingSystem.comment.Comment;
import com.YusufFakhreddin.TicketingSystem.team.Team;
import com.YusufFakhreddin.TicketingSystem.ticket.Ticket;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean active;

//    one to many relationship with team entity (one user can be in one teams) joined by username and team_id
    @ManyToMany
    @JoinTable(
            name = "user_teams",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    private Set<Team> teams;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "author")
    private List<Comment> comments;
    @OneToMany(mappedBy = "assignedUser")
    private List<Ticket> assignedTickets;

}