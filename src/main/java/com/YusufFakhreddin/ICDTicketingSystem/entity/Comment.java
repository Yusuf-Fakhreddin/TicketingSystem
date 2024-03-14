package com.YusufFakhreddin.ICDTicketingSystem.entity;

import jakarta.persistence.*;
import lombok.*;
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

    private String date;
    private String time;



//    constructor to initialize date and time with current date and time
    public Comment(String ticket_id, String comment) {
        this.ticket_id = ticket_id;
        this.comment = comment;
        this.date= java.time.LocalDate.now().toString();
        this.time= java.time.LocalTime.now().toString();
    }


    //    Define toString method
    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", ticket_id='" + ticket_id + '\'' +
                ", comment='" + comment + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public void setTicket_id(String id) {
        this.ticket_id = id;
    }
}