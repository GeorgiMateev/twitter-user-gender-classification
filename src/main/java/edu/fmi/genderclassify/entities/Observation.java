package edu.fmi.genderclassify.entities;

/**
 * Created by Miroslav Kramolinski
 */
public class Observation {
    private String observationState;        // state of the observation; one of finalized (for contributor-judged) or golden (for gold standard observations)
    private TwitterUser user;               // the user of the observation
    private Tweet tweet;                    // the data of the tweet
}
