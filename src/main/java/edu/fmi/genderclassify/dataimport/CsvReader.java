package edu.fmi.genderclassify.dataimport;

import edu.fmi.genderclassify.entities.Observation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Miroslav Kramolinski
 */
public class CsvReader {
    private enum Headers {
        USER_ID("_unit_id"),
        GOLDEN("_golden"),
        UNIT_STATE("_unit_state"),
        TRUSTED_JUDGEMENT("_trusted_judgments"),
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

        private Headers(String headerName) {
            this.headerName = headerName;
        }

        public String getHeaderName() {
            return headerName;
        }
    }

    private static String TRAIN_DATA_FILE = "./gender-classifier-DFE-791531.csv";

    private static List<Observation> readObservations() {
        List<Observation> observations = new ArrayList<>();

        try {
            List<String> lines = Files.lines(Paths.get(TRAIN_DATA_FILE)).collect(Collectors.toList());

            // First line holds the headers
            Map<String, Integer> headerMap = new HashMap<>();
            int column = 0;
            for(String header: lines.get(0).split(",")) {
                headerMap.put(header, column++);
            }

            for(int i = 1; i < lines.size(); i ++) {
                observations.add(parseLine(lines.get(i), headerMap));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return observations;
    }

    private static Observation parseLine(String line, Map<String, Integer> headerMap) {
        // Construct the observation objects
        return null;
    }
}
