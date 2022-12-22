package com.example.mylib;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable {
    private String about;
    private String author;
    private boolean available;
    private List<String> genres;
    private String title;
    private String imagePath;
    private Timestamp borrowedUntil;

    public Timestamp getBorrowedUntil() {
        return borrowedUntil;
    }

    public void setBorrowedUntil(Timestamp borrowedUntil) {
        this.borrowedUntil = borrowedUntil;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
