package edu.fmi.genderclassify.entities;

import javafx.util.Pair;

import java.util.Date;

/**
 * Created by Miroslav Kramolinski
 */
public class Tweet {
    private String id;                              // tweet ID
    private String text;                            // the text content of the tweet

    private Date creationDate;                      // tweet's creation date

    private Pair<Double, Double> coordinates;       // the coordinates of the location of the tweet
    private String location;                        // the name of the location of the tweet

    public Tweet() {}

    public Tweet id(String id) {
        this.id = id;
        return this;
    }

    public Tweet text(String text) {
        this.text = text;
        return this;
    }

    public Tweet creationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public Tweet coordinates(Pair<Double, Double> coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public Tweet location(String location) {
        this.location = location;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Pair<Double, Double> getCoordinates() {
        return coordinates;
    }

    public String getLocation() {
        return location;
    }
}
