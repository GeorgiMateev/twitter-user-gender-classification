package edu.fmi.genderclassify.entities;

import java.util.Date;

/**
 * Created by Miroslav Kramolinski
 */
public class TwitterUser {
    private Long id;                            // a unique id for user
    private String username;                    // the user's name

    private Integer numberOfRetweets;           // number of times the user has retweeted (or possibly, been retweeted)
    private Integer numberOfFavorites;          // number of tweets the user has favorited
    private Integer tweetCount;                 // number of tweets that the user has posted

    private Boolean golden;                     // whether the user was included in the gold standard for the model; TRUE or FALSE
    private Integer trustedJudgements;           // number of trusted judgments; always 3 for non-golden, and what may be a unique id for gold standard observations
    private Date lastJudgement;                 // date and time of last contributor judgment; blank for gold standard observations
    private String gender;                      // one of male, female, or brand (for non-human profiles)
    private Double genderConfidence;            // a float representing confidence in the provided gender

    private String timezone;                    // the timezone of the user

    private TwitterProfile profile;             // the profile settings of the user

    public TwitterUser() {}

    public TwitterUser id(Long id) {
        this.id = id;
        return this;
    }

    public TwitterUser username(String username) {
        this.username = username;
        return this;
    }

    public TwitterUser numberOfRetweets(Integer numberOfRetweets) {
        this.numberOfRetweets = numberOfRetweets;
        return this;
    }

    public TwitterUser numberOfFavorites(Integer numberOfFavorites) {
        this.numberOfFavorites = numberOfFavorites;
        return this;
    }

    public TwitterUser tweetCount(Integer tweetCount) {
        this.tweetCount = tweetCount;
        return this;
    }

    public TwitterUser golden(Boolean golden) {
        this.golden = golden;
        return this;
    }

    public TwitterUser trustedJudgements(Integer trustedJudgements) {
        this.trustedJudgements = trustedJudgements;
        return this;
    }

    public TwitterUser lastJudgement(Date lastJudgement) {
        this.lastJudgement = lastJudgement;
        return this;
    }

    public TwitterUser gender(String gender) {
        this.gender = gender;
        return this;
    }

    public TwitterUser genderConfidence(Double genderConfidence) {
        this.genderConfidence = genderConfidence;
        return this;
    }

    public TwitterUser timezone(String timezone) {
        this.timezone = timezone;
        return this;
    }

    public TwitterUser profile(TwitterProfile profile) {
        this.profile = profile;
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Integer getNumberOfRetweets() {
        return numberOfRetweets;
    }

    public Integer getNumberOfFavorites() {
        return numberOfFavorites;
    }

    public Integer getTweetCount() {
        return tweetCount;
    }

    public Boolean getGolden() {
        return golden;
    }

    public Integer getTrustedJudgements() {
        return trustedJudgements;
    }

    public Date getLastJudgement() {
        return lastJudgement;
    }

    public String getGender() {
        return gender;
    }

    public Double getGenderConfidence() {
        return genderConfidence;
    }

    public String getTimezone() {
        return timezone;
    }

    public TwitterProfile getProfile() {
        return profile;
    }
}
