package com.example.yagnikbhatt.routeplanner.Data;

/**
 * Created by hemaladani on 1/23/18.
 */

public class Task {
    long date;
    String description;
public Task(){

}
    public Task(long date, String description, String location, int priority, int id, boolean isCompleted, double latitude, double longitude) {
        this.date = date;
        this.description = description;
        this.location = location;
        this.priority = priority;
        this.id = id;
        this.isCompleted = isCompleted;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    String location;
    int priority,id;
    boolean isCompleted;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    double latitude,longitude;

    public int getId(){
        return id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

}
