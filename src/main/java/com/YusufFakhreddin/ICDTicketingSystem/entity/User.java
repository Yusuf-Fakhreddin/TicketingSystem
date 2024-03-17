package com.YusufFakhreddin.ICDTicketingSystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
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
    @ToString.Exclude
    @JoinTable(
            name = "user_teams",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    private Set<Team> teams;

    @ToString.Exclude
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Ticket> tickets;

    @ToString.Exclude
    @OneToMany(mappedBy = "author")
    private List<Comment> comments;
    @ToString.Exclude
    @OneToMany(mappedBy = "assignedUser")
    private List<Ticket> assignedTickets;

}