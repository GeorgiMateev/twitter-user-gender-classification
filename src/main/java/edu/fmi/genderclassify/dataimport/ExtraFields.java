package edu.fmi.genderclassify.dataimport;

import java.util.Arrays;

public enum ExtraFields {
    TWEET_COORDINATES_LATITUDE,
    TWEET_COORDINATES_LONGITUDE,

    TWEET_MALE_FEMALE_WORDS_SCORE,
    DESCRIPTION_MALE_FEMALE_WORDS_SCORE,

    TWEET_TEXT_PMI_MALE,
    TWEET_TEXT_PMI_FEMALE,
    TWEET_TEXT_PMI_BRAND,
    TWEET_TEXT_PMI_UNKNOWN,
    TWEET_TEXT_GENDER_PREDICT,

    USER_DESC_PMI_MALE,
    USER_DESC_PMI_FEMALE,
    USER_DESC_PMI_BRAND,
    USER_DESC_PMI_UNKNOWN,
    USER_DESC_GENDER_PREDICT;

    public static boolean contains(String field) {
        return Arrays.stream(values()).filter(value -> value.name().equalsIgnoreCase(field)).count() == 1;
    }
}
