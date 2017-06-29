package edu.fmi.genderclassify.entities;

public class Observation {
    private String observationState;        // state of the observation; one of finalized (for contributor-judged) or golden (for gold standard observations)
    private TwitterUser user;               // the user of the observation
    private Tweet tweet;                    // the data of the tweet

    public Observation() {}

    public Observation observationState(String observationState) {
        this.observationState = observationState;
        return this;
    }

    public Observation user(TwitterUser user) {
        this.user = user;
        return this;
    }

    public Observation tweet(Tweet tweet) {
        this.tweet = tweet;
        return this;
    }

    public String getObservationState() {
        return observationState;
    }

    public TwitterUser getUser() {
        return user;
    }

    public Tweet getTweet() {
        return tweet;
    }
}
