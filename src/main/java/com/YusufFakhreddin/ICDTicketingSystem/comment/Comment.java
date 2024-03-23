package com.YusufFakhreddin.ICDTicketingSystem.comment;

import com.YusufFakhreddin.ICDTicketingSystem.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;
    private String ticket_id;
    private String comment;

    private LocalDate date;
    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "author_username", nullable = false)
    private User author;


    public void setTicket_id(String id) {
        this.ticket_id = id;
    }
}