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



    public Comment() {
    }

//    constructor to initialize date and time with current date and time
    public Comment(String ticket_id, String comment) {
        this.ticket_id = ticket_id;
        this.comment = comment;
        this.date= java.time.LocalDate.now().toString();
        this.time= java.time.LocalTime.now().toString();
    }

    public Comment(String ticket_id, String comment, String date, String time) {
        this.ticket_id = ticket_id;
        this.comment = comment;
        this.date = date;
        this.time = time;
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

//    ticket_id getters and setters
    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }
    public String getTicket_id() {
        return ticket_id;
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

}
