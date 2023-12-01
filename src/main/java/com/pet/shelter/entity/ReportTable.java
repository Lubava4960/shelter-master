package com.pet.shelter.entity;

import jakarta.persistence.*;

import java.net.URL;
import java.time.LocalDateTime;

@Entity
@Table(name="report_table")
public class ReportTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "moon_cat",nullable = false)
    private String message;
    @Column(name="chat_id")
    private long chatId;
    @Column(name="image")
    private byte[] image;


    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public byte[] getImage() {

        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
