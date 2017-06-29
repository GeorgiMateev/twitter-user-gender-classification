package edu.fmi.genderclassify.dataimport;

public enum Fields {
    USER_ID("_unit_id"),
    GOLDEN("_golden"),
    UNIT_STATE("_unit_state"),
    TRUSTED_JUDGEMENTS("_trusted_judgments"),
    LAST_JUDGEMENT_TIME("_last_judgment_at"),
    GENDER("gender"),
    GENDER_CONFIDENCE("gender:confidence"),
    PROFILE_EXISTS("profile_yn"),
    PROFILE_EXISTS_CONFIDENCE("profile_yn:confidence"),
    PROFILE_CREATION_DATE("created"),
    PROFILE_DESCRIPTION("description"),
    FAVORITES_NUMBER("fav_number"),
    GENDER_GOLDEN("gender_gold"),
    LINK_COLOR("link_color"),
    USERNAME("name"),
    PROFILE_YN_VALUE_GOLDEN("profile_yn_gold"),
    PROFILE_IMAGE_LINK("profileimage"),
    RETWEET_COUNT("retweet_count"),
    SIDEBAR_COLOR("sidebar_color"),
    TWEET_TEXT("text"),
    TWEET_COORDINATES("tweet_coord"),
    TWEETS_COUNT("tweet_count"),
    TWEET_CREATION_DATE("tweet_created"),
    TWEET_ID("tweet_id"),
    TWEET_LOCATION("tweet_location"),
    USER_TIMEZONE("user_timezone");

    private String headerName;

    Fields(String headerName) {
        this.headerName = headerName;
    }

    public String getHeaderName() {
        return headerName;
    }
}
