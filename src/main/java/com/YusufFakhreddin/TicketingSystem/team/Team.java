package com.YusufFakhreddin.TicketingSystem.team;

import com.YusufFakhreddin.TicketingSystem.user.User;
import com.YusufFakhreddin.TicketingSystem.team.enums.TeamName;
import com.YusufFakhreddin.TicketingSystem.ticket.Ticket;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "teams")
@Data
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private TeamName name;

    @ManyToMany(mappedBy = "teams")
    private Set<User> users;
    

    @OneToMany(mappedBy = "ownerTeam")
    @JsonManagedReference
    private Set<Ticket> tickets;

    @OneToMany(mappedBy = "assignedTeam")
    private List<Ticket> assignedTickets;

}