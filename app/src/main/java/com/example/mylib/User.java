package com.example.mylib;

public class User {
    String fullName;
    String phoneNumber;
    String email;
    String password;
    Boolean libraryOwner;

    public User() {
    }

    User(String mail, String pw, String fN, String pN, boolean isOwner){
        this.email = mail;
        this.password = pw;
        this.fullName = fN;
        this.phoneNumber = pN;
        this.libraryOwner = isOwner;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getLibraryOwner() {
        return libraryOwner;
    }

    public void setLibraryOwner(Boolean libraryOwner) {
        this.libraryOwner = libraryOwner;
    }
}
