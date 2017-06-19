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
    private Integer trustedJudgement;           // number of trusted judgments; always 3 for non-golden, and what may be a unique id for gold standard observations
    private Date lastJudgement;                 // date and time of last contributor judgment; blank for gold standard observations
    private String gender;                      // one of male, female, or brand (for non-human profiles)
    private Double genderConfidence;            // a float representing confidence in the provided gender

    private String timezone;                    // the timezone of the user

    private TwitterProfile profile;             // the profile settings of the user
}
