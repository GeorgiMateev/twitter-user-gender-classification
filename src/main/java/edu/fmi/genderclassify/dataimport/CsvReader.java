package edu.fmi.genderclassify.dataimport;

import edu.fmi.genderclassify.entities.Observation;
import edu.fmi.genderclassify.entities.Tweet;
import edu.fmi.genderclassify.entities.TwitterProfile;
import edu.fmi.genderclassify.entities.TwitterUser;
import javafx.util.Pair;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Miroslav Kramolinski
 */
public class CsvReader {
    private enum Headers {
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

        Headers(String headerName) {
            this.headerName = headerName;
        }

        public String getHeaderName() {
            return headerName;
        }
    }

    private static String TRAIN_DATA_FILE = "src\\main\\resources\\train\\gender-classifier-DFE-791531.csv";

    public static List<Observation> readObservations() {
        List<Observation> observations = new ArrayList<>();
        Integer skipped = 0;

        try {
            List<List<String>> rows = readRows(Files.lines(Paths.get(TRAIN_DATA_FILE), StandardCharsets.ISO_8859_1).collect(Collectors.toList()));

            // First line holds the headers
            Map<String, Integer> headerMap = new HashMap<>();
            int column = 0;
            for(String header: rows.get(0)) {
                headerMap.put(header, column++);
            }

            for(int i = 1; i < rows.size(); i ++) {
                try {
                    String[] row = new String[rows.get(i).size()];
                    row = rows.get(i).toArray(row);

                    observations.add(parseLine(row, headerMap));
                } catch (Exception e) {
                    // skip parse and other errors
                    skipped ++;
                    System.out.println("Skipping line [" + rows.get(i) + "] due to parse exception: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("Observations read: " + observations.size() + ", observations skipped: " + skipped);
        return observations;
    }

    private static Observation parseLine(String[] line, Map<String, Integer> headerMap) {
        TwitterProfile profile = new TwitterProfile()
                .desciption(line[headerMap.get(Headers.PROFILE_DESCRIPTION.getHeaderName())])
                .sidebarColor(line[headerMap.get(Headers.SIDEBAR_COLOR.getHeaderName())])
                .linkColor(line[headerMap.get(Headers.LINK_COLOR.getHeaderName())])
                .profileImageLink(line[headerMap.get(Headers.PROFILE_IMAGE_LINK.getHeaderName())])
                .profileExists(getBoolean(line[headerMap.get(Headers.PROFILE_EXISTS.getHeaderName())]))
                .profileExistsConfidence(getDouble(line[headerMap.get(Headers.PROFILE_EXISTS_CONFIDENCE.getHeaderName())]))
                .created(getDate(line[headerMap.get(Headers.PROFILE_CREATION_DATE.getHeaderName())]))
                .genderGold(line[headerMap.get(Headers.GENDER_GOLDEN.getHeaderName())])
                .profileYnGolden(getBoolean(line[headerMap.get(Headers.PROFILE_YN_VALUE_GOLDEN.getHeaderName())]));

        String coordinates = line[headerMap.get(Headers.TWEET_COORDINATES.getHeaderName())];
        String lat = null;
        String lon = null;

        if(coordinates != null && !coordinates.isEmpty()) {
            lat = coordinates.split(", ")[0];
            lat = lat.substring(1);

            lon = coordinates.split(", ")[1];
            lon = lon.substring(0, lon.length() - 1);
        }

        return new Observation()
                .observationState(line[headerMap.get(Headers.UNIT_STATE.getHeaderName())])
                .user(new TwitterUser()
                        .id(Long.parseLong(line[headerMap.get(Headers.USER_ID.getHeaderName())]))
                        .username(line[headerMap.get(Headers.USERNAME.getHeaderName())])
                        .numberOfRetweets(getInteger(line[headerMap.get(Headers.RETWEET_COUNT.getHeaderName())]))
                        .numberOfFavorites(getInteger(line[headerMap.get(Headers.FAVORITES_NUMBER.getHeaderName())]))
                        .tweetCount(getInteger(line[headerMap.get(Headers.TWEETS_COUNT.getHeaderName())]))
                        .golden(getBoolean(line[headerMap.get(Headers.GOLDEN.getHeaderName())]))
                        .trustedJudgements(getInteger(line[headerMap.get(Headers.TRUSTED_JUDGEMENTS.getHeaderName())]))
                        .lastJudgement(getDate(line[headerMap.get(Headers.LAST_JUDGEMENT_TIME.getHeaderName())]))
                        .gender(line[headerMap.get(Headers.GENDER.getHeaderName())])
                        .genderConfidence(getDouble(line[headerMap.get(Headers.GENDER_CONFIDENCE.getHeaderName())]))
                        .timezone(line[headerMap.get(Headers.USER_TIMEZONE.getHeaderName())])
                        .profile(profile))
                .tweet(new Tweet()
                        .id(line[headerMap.get(Headers.TWEET_ID.getHeaderName())])
                        .text(line[headerMap.get(Headers.TWEET_TEXT.getHeaderName())])
                        .creationDate(getDate(line[headerMap.get(Headers.TWEET_CREATION_DATE.getHeaderName())]))
                        .coordinates(lat == null? null: new Pair<>(getDouble(lat), getDouble(lon)))
                        .location(line[headerMap.get(Headers.TWEET_LOCATION.getHeaderName())]));
    }

    private static Boolean getBoolean(String str) {
        if(str == null || str.isEmpty())
            return null;
        else if(str.equalsIgnoreCase("yes") || str.equalsIgnoreCase("true"))
            return true;
        else if(str.equalsIgnoreCase("no") || str.equalsIgnoreCase("false"))
            return false;

        return null;
    }

    private static Double getDouble(String str) {
        return str == null || str.isEmpty()? null: Double.parseDouble(str);
    }

    private static Integer getInteger(String str) {
        return str == null || str.isEmpty()? null: Integer.parseInt(str);
    }

    private static Date getDate(String str) {
        if(str == null || str.isEmpty())
            return null;

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        try {
            return df.parse(str);
        } catch(ParseException parseException) {
            return null;
        }
    }

    // Dissection and reconstruction of each line is required, as some tweets can contain commas or new lines
    // in their text and hence we can't just use the lines from the file and split them by the comma character
    private static List<List<String>> readRows(List<String> lines) throws IOException {
        List<List<String>> rows = new ArrayList<>();
        int columnsPerRow = Headers.values().length;


        List<String> row = new ArrayList<>();
        StringBuilder buff = new StringBuilder();

        boolean skipComma = false;
        for(String line: lines) {
            for(int i = 0; i < line.length(); i ++) {
                char c = line.charAt(i);
                if (c == '"') {
                    skipComma = !skipComma;
                } else if (c == ',') {
                    if (skipComma)
                        buff.append(c);
                    else {
                        row.add(buff.toString());
                        buff.setLength(0);
                    }
                } else {
                    buff.append(c);
                }
            }

            if(row.size() == columnsPerRow - 1) { // value is not to be continued on another line
                row.add(buff.toString());
                buff.setLength(0);
            } else {
                buff.append('\n');
            }

            if(row.size() == columnsPerRow) {
                rows.add(row);
                row = new ArrayList<>();
            }
        }

        return rows;
    }
}
