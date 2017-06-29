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

public class CsvReader {
    private static String TRAIN_DATA_FILE = "src\\main\\resources\\train\\gender-classifier-DFE-791531.csv";

    private Map<String, Set<Object>> dataDomain;

    public CsvReader() {
        dataDomain = new HashMap<>();
    }

    public Pair<List<Observation>, Map<String, Set<Object>>> readObservations() {
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

        System.out.println("Observations read: " + observations.size() + ", observations skipped: " + skipped);
        return new Pair(observations, dataDomain);
    }

    private Observation parseLine(String[] line, Map<String, Integer> headerMap) {
        TwitterProfile profile = new TwitterProfile()
                .desciption(extractString(line, headerMap, Fields.PROFILE_DESCRIPTION))
                .sidebarColor(extractString(line, headerMap, Fields.SIDEBAR_COLOR))
                .linkColor(extractString(line, headerMap, Fields.LINK_COLOR))
                .profileImageLink(extractString(line, headerMap, Fields.PROFILE_IMAGE_LINK))
                .profileExists(extractBoolean(line, headerMap, Fields.PROFILE_EXISTS))
                .profileExistsConfidence(extractDouble(line, headerMap, Fields.PROFILE_EXISTS_CONFIDENCE))
                .created(extractDate(line, headerMap, Fields.PROFILE_CREATION_DATE))
                .genderGold(extractString(line, headerMap, Fields.GENDER_GOLDEN))
                .profileYnGolden(extractBoolean(line, headerMap, Fields.PROFILE_YN_VALUE_GOLDEN));

        return new Observation()
                .observationState(extractString(line, headerMap, Fields.UNIT_STATE))
                .user(new TwitterUser()
                        .id(extractLong(line, headerMap, Fields.USER_ID))
                        .username(extractString(line, headerMap, Fields.USERNAME))
                        .numberOfRetweets(extractInteger(line, headerMap, Fields.RETWEET_COUNT))
                        .numberOfFavorites(extractInteger(line, headerMap, Fields.FAVORITES_NUMBER))
                        .tweetCount(extractInteger(line, headerMap, Fields.TWEETS_COUNT))
                        .golden(extractBoolean(line, headerMap, Fields.GOLDEN))
                        .trustedJudgements(extractInteger(line, headerMap, Fields.TRUSTED_JUDGEMENTS))
                        .lastJudgement(extractDate(line, headerMap, Fields.LAST_JUDGEMENT_TIME))
                        .gender(extractString(line, headerMap, Fields.GENDER))
                        .genderConfidence(extractDouble(line, headerMap, Fields.GENDER_CONFIDENCE))
                        .timezone(extractString(line, headerMap, Fields.USER_TIMEZONE))
                        .profile(profile))
                .tweet(new Tweet()
                        .id(extractString(line, headerMap, Fields.TWEET_ID))
                        .text(extractString(line, headerMap, Fields.TWEET_TEXT))
                        .creationDate(extractDate(line, headerMap, Fields.TWEET_CREATION_DATE))
                        .coordinates(extractCoordinates(line, headerMap, Fields.TWEET_COORDINATES))
                        .location(extractString(line, headerMap, Fields.TWEET_LOCATION)));
    }

    private String extractString(String[] line, Map<String, Integer> headerMap, Fields field) {
        String value = line[headerMap.get(field.getHeaderName())];
        addToDomain(field, value);

        return value;
    }

    private Long extractLong(String[] line, Map<String, Integer> headerMap, Fields field) {
        Long value = Long.parseLong(line[headerMap.get(field.getHeaderName())]);
        addToDomain(field, value);

        return value;
    }

    private Boolean extractBoolean(String[] line, Map<String, Integer> headerMap, Fields field) {
        Boolean value = getBoolean(line[headerMap.get(field.getHeaderName())]);
        addToDomain(field, value);

        return value;
    }

    private Integer extractInteger(String[] line, Map<String, Integer> headerMap, Fields field) {
        Integer value = getInteger(line[headerMap.get(field.getHeaderName())]);
        addToDomain(field, value);

        return value;
    }

    private Double extractDouble(String[] line, Map<String, Integer> headerMap, Fields field) {
        Double value = getDouble(line[headerMap.get(field.getHeaderName())]);
        addToDomain(field, value);

        return value;
    }

    private Date extractDate(String[] line, Map<String, Integer> headerMap, Fields field) {
        Date value = getDate(line[headerMap.get(field.getHeaderName())]);
        addToDomain(field, value);

        return value;
    }

    private Pair<Double, Double> extractCoordinates(String[] line, Map<String, Integer> headerMap, Fields field) {
        String coordinates = line[headerMap.get(field.getHeaderName())];
        String lat = null;
        String lon = null;

        if(coordinates != null && !coordinates.isEmpty()) {
            lat = coordinates.split(", ")[0];
            lat = lat.substring(1);

            lon = coordinates.split(", ")[1];
            lon = lon.substring(0, lon.length() - 1);
        }

        Pair<Double, Double> value = lat == null? null: new Pair<>(getDouble(lat), getDouble(lon));
        addToDomain(field, value);

        return value;
    }

    private void addToDomain(Fields field, Object value) {
        if(dataDomain.get(field.name()) == null)
            dataDomain.put(field.name(), new HashSet<>());

        dataDomain.get(field.name()).add(value);
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
        int columnsPerRow = Fields.values().length;


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
