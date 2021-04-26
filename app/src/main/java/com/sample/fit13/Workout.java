package com.sample.fit13;


//Object to store workout information
public class Workout {

    private String title;
    private String date;
    private String duration;
    private String description;

    Workout(){

    }

    Workout (String title, String date, String duration, String description){
        this.title=title;
        this.date=date;
        this.duration=duration;
        this.description=description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}

