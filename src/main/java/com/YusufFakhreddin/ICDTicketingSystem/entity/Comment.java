package com.YusufFakhreddin.ICDTicketingSystem.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;
    private String ticket_id;
    private String comment;
    private String date;
    private String time;

    // link to ticket
    @ManyToOne
    @JoinColumn(name = "ticket_id", insertable = false, updatable = false)
    private Ticket ticket;

    public Comment() {
    }

    public Comment(String ticket_id, String comment, String date, String time) {
        this.ticket_id = ticket_id;
        this.comment = comment;
        this.date = date;
        this.time = time;
    }

    public String getTicket_id() {
        return ticket_id;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
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
                ", ticket=" + ticket +
                '}';
    }

}
