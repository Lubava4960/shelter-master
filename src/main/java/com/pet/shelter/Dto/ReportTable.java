package com.pet.shelter.Dto;

import java.util.Objects;

public class ReportTable {
    private long id;
    private String message;
    private long chatId;
    private  String image ;


    public ReportTable() {
        this.id = id;
        this.chatId = chatId;
        this.image=image;

    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public long getChatId() {
        return chatId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMessage(String moodCats) {
        this.message =message;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportTable that = (ReportTable) o;
        return id == that.id && chatId == that.chatId && Objects.equals(message, that.message) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, chatId);
    }
}
