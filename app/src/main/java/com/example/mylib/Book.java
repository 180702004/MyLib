package com.example.mylib;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable {
    private String about;
    private String author;
    private boolean available;
    private String genre;
    private String bookTitle;
    private String imagePath;
    private Timestamp borrowedUntil;

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genres) {
        this.genre = genre;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
