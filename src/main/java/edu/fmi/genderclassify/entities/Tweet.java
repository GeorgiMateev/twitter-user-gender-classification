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
}
