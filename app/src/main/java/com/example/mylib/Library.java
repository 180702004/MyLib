package com.example.mylib;

import java.io.Serializable;

public class Library implements Serializable {
    private String id;
    private String fullName;
    private String about;
    private String contactNumber;
    private double longitude;
    private double latitude;
    private String membershipNeeded;
    private String studyingAreaIncluded;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMembershipNeeded() {
        return membershipNeeded;
    }

    public void setMembershipNeeded(String membershipNeeded) {
        this.membershipNeeded = membershipNeeded;
    }

    public String getStudyingAreaIncluded() {
        return studyingAreaIncluded;
    }

    public void setStudyingAreaIncluded(String studyingAreaIncluded) {
        this.studyingAreaIncluded = studyingAreaIncluded;
    }

    private String workingHours;
    private String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }
}
