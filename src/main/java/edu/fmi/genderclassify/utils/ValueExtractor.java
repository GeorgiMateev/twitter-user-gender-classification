package edu.fmi.genderclassify.utils;

import edu.fmi.genderclassify.dataimport.ExtraFields;
import edu.fmi.genderclassify.dataimport.Fields;
import edu.fmi.genderclassify.dataimport.MaleFemaleWordsReader;
import edu.fmi.genderclassify.entities.Observation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Contains the specific logic for extracting the value from each available field in the system
 */
public class ValueExtractor {
    private static Map<String, List<Double>> fieldsValuesCache = new HashMap<>(); // spare some stream operations
    private static Map<String, Map<String, Integer>> termFreqCache = new HashMap<>(); // cache of the tf for each gender
    private static Map<String, Double> probs = new HashMap<>(); // cache of the probability of the user being of given gender
    private static Map<String, Map<String, Double>> pmiCache = new HashMap<>(); // cache the found PMI values per gender

    public static Double getDoubleValue(String fieldName, List<Observation> observations, int observationId) {
        Observation observation = observations.get(observationId);

        if(fieldName.equalsIgnoreCase(Fields.TRUSTED_JUDGEMENTS.name())) {
            Double value = Conversion.getDoubleValue(observation.getUser().getTrustedJudgements());

            if(value.doubleValue() == 3d)
                return 0d;
            else {
                if(!fieldsValuesCache.containsKey(fieldName))
                    fieldsValuesCache.put(
                            fieldName,
                            observations
                                    .stream()
                                    .map(obs -> new Double(obs.getUser().getTrustedJudgements()))
                                    .collect(Collectors.toList()));

                return Conversion.getMinMaxNormalizedValue(
                        value,
                        fieldsValuesCache.get(fieldName));
            }
        }

        if(fieldName.equalsIgnoreCase(Fields.GENDER_CONFIDENCE.name()))
            return Conversion.getDoubleValue(observation.getUser().getGenderConfidence());

        if(fieldName.equalsIgnoreCase(Fields.PROFILE_EXISTS_CONFIDENCE.name()))
            return Conversion.getDoubleValue(observation.getUser().getProfile().getProfileExistsConfidence());

        if(fieldName.equalsIgnoreCase(Fields.FAVORITES_NUMBER.name())) {
            return Conversion.getDoubleValue(observation.getUser().getNumberOfFavorites());

            // Attempts at normalizing this value resulted in failure

            // step normalization
//            if(!fieldsValuesCache.containsKey(fieldName))
//                fieldsValuesCache.put(
//                    fieldName,
//                        observations
//                            .stream()
//                            .map(obs -> new Double(obs.getUser().getNumberOfFavorites()))
//                            .collect(Collectors.toList()));
//
//            return Conversion.getNormalizedToStepsValue(
//                Conversion.getDoubleValue(observation.getUser().getNumberOfFavorites()),
//                fieldsValuesCache.get(fieldName),
//                10);

            // min-max normalization
//            if(!fieldsValuesCache.containsKey(fieldName))
//                fieldsValuesCache.put(
//                    fieldName,
//                        observations
//                                .stream()
//                                .map(obs -> new Double(obs.getUser().getNumberOfFavorites()))
//                                .collect(Collectors.toList()));
//
//            return Conversion.getMinMaxNormalizedValue(
//                    Conversion.getDoubleValue(observation.getUser().getNumberOfFavorites()),
//                    fieldsValuesCache.get(fieldName));
        }

        if(fieldName.equalsIgnoreCase(Fields.RETWEET_COUNT.name()))
            return Conversion.getDoubleValue(observation.getUser().getNumberOfRetweets());

        if(fieldName.equalsIgnoreCase(ExtraFields.TWEET_COORDINATES_LATITUDE.name())) {
            if(observation.getTweet().getCoordinates() != null)
                return Conversion.getDoubleValue(observation.getTweet().getCoordinates().getKey());
            else
                return 0d;
        }

        if(fieldName.equalsIgnoreCase(ExtraFields.TWEET_COORDINATES_LONGITUDE.name())) {
            if(observation.getTweet().getCoordinates() != null)
                return Conversion.getDoubleValue(observation.getTweet().getCoordinates().getValue());
            else
                return 0d;
        }

        if(fieldName.equalsIgnoreCase(Fields.TWEETS_COUNT.name())) {
            if(!fieldsValuesCache.containsKey(fieldName))
                fieldsValuesCache.put(
                    fieldName,
                    observations
                        .stream()
                        .map(obs -> new Double(obs.getUser().getTweetCount()))
                        .collect(Collectors.toList()));

//            return Conversion.getDoubleValue(observation.getUser().getTweetCount());
            return Conversion.getNormalizedToStepsValue(
                Conversion.getDoubleValue(observation.getUser().getTweetCount()),
                fieldsValuesCache.get(fieldName),
                2000);
        }

        if(fieldName.equalsIgnoreCase(ExtraFields.TWEET_MALE_FEMALE_WORDS_SCORE.name()))
            return getMaleFemaleWordsScore(observation.getTweet().getText());

        if(fieldName.equalsIgnoreCase(ExtraFields.DESCRIPTION_MALE_FEMALE_WORDS_SCORE.name()))
            return getMaleFemaleWordsScore(observation.getUser().getProfile().getDescription());

        return null;
    }

    private static double getMaleFemaleWordsScore(String text) {
        Map<String, Double> scores = MaleFemaleWordsReader.readMaleFemaleWordsScore();
        String[] tokens = text.split(" ");
        double score = 0;
        for (String token: tokens) {
            if (scores.containsKey(token.toLowerCase())) {
                score += scores.get(token.toLowerCase());
            }
        }
        return score;
    }

    public static String getStringValue(String fieldName, List<Observation> observations, int observationId) {
        Observation observation = observations.get(observationId);

        if(fieldName.equalsIgnoreCase(Fields.GOLDEN.name()))
            return Conversion.getValueAsStr(observation.getUser().getGolden());

        if(fieldName.equalsIgnoreCase(Fields.UNIT_STATE.name()))
            return Conversion.getValueAsStr(observation.getObservationState());

        if(fieldName.equalsIgnoreCase(Fields.LAST_JUDGEMENT_TIME.name()))
            return Conversion.getValueAsStr(observation.getUser().getLastJudgement());

        if(fieldName.equalsIgnoreCase(Fields.GENDER.name()))
            return Conversion.getValueAsStr(observation.getUser().getGender());

        if(fieldName.equalsIgnoreCase(Fields.PROFILE_EXISTS.name()))
            return Conversion.getValueAsStr(observation.getUser().getProfile().getProfileExists());

        if(fieldName.equalsIgnoreCase(Fields.PROFILE_CREATION_DATE.name()))
            return Conversion.getValueAsStr(observation.getUser().getProfile().getCreated());

        if(fieldName.equalsIgnoreCase(Fields.PROFILE_DESCRIPTION.name()))
            return observation.getUser().getProfile().getDescription();

        if(fieldName.equalsIgnoreCase(Fields.GENDER_GOLDEN.name()))
            return Conversion.getValueAsStr(observation.getUser().getProfile().getGenderGold());

        if(fieldName.equalsIgnoreCase(Fields.LINK_COLOR.name()))
            return Conversion.getHexColorAsNominal(observation.getUser().getProfile().getLinkColor());

        if(fieldName.equalsIgnoreCase(Fields.USERNAME.name()))
            return observation.getUser().getUsername();

        if(fieldName.equalsIgnoreCase(Fields.SIDEBAR_COLOR.name()))
            return Conversion.getHexColorAsNominal(observation.getUser().getProfile().getSidebarColor());

        if(fieldName.equalsIgnoreCase(Fields.TWEET_TEXT.name()))
            return observation.getTweet().getText();

        if(fieldName.equalsIgnoreCase(Fields.TWEET_LOCATION.name()))
            return Conversion.getValueAsStr(observation.getTweet().getLocation());

        if(fieldName.equalsIgnoreCase(Fields.USER_TIMEZONE.name()))
            return Conversion.getValueAsStr(observation.getUser().getTimezone());

        return null;
    }

    public static String getGenderPredictionBasedOnPMI(String fieldName, List<Observation> observations, int observationId) {
        if(fieldName.equalsIgnoreCase(ExtraFields.TWEET_TEXT_GENDER_PREDICT.name())) {
            Double pmiMale = getPMIValue(ExtraFields.TWEET_TEXT_PMI_MALE.name(), observations, observationId);
            Double pmiFemale = getPMIValue(ExtraFields.TWEET_TEXT_PMI_FEMALE.name(), observations, observationId);
            Double pmiBrand = getPMIValue(ExtraFields.TWEET_TEXT_PMI_BRAND.name(), observations, observationId);
            Double pmiUnknown = getPMIValue(ExtraFields.TWEET_TEXT_PMI_UNKNOWN.name(), observations, observationId);

            return getBestScoringGender(pmiMale, pmiFemale,pmiBrand, pmiUnknown);
        } else if(fieldName.equalsIgnoreCase(ExtraFields.USER_DESC_GENDER_PREDICT.name())) {
            Double pmiMale = getPMIValue(ExtraFields.USER_DESC_PMI_MALE.name(), observations, observationId);
            Double pmiFemale = getPMIValue(ExtraFields.USER_DESC_PMI_FEMALE.name(), observations, observationId);
            Double pmiBrand = getPMIValue(ExtraFields.USER_DESC_PMI_BRAND.name(), observations, observationId);
            Double pmiUnknown = getPMIValue(ExtraFields.USER_DESC_PMI_UNKNOWN.name(), observations, observationId);

            return getBestScoringGender(pmiMale, pmiFemale,pmiBrand, pmiUnknown);
        } else {
            throw new IllegalArgumentException("Unable to predict gender from: " + fieldName);
        }
    }

    private static String getBestScoringGender(Double pmiMale, Double pmiFemale, Double pmiBrand, Double pmiUnknown) {
        if(pmiMale >= pmiFemale && pmiMale >= pmiBrand && pmiMale >= pmiUnknown)
            return "male";
        else if(pmiFemale >= pmiMale && pmiFemale >= pmiBrand && pmiFemale >= pmiUnknown)
            return "female";
        else if(pmiBrand >= pmiMale && pmiBrand >= pmiFemale && pmiBrand >= pmiUnknown)
            return "brand";
        else
            return "unknown";
    }

    public static Double getPMIValue(String fieldName, List<Observation> observations, int observationId) {
        if(fieldName.equalsIgnoreCase(ExtraFields.TWEET_TEXT_PMI_MALE.name()))
            return getPMIOfTextByGender(observations, observationId, obs -> obs.getTweet().getText(), "male");

        if(fieldName.equalsIgnoreCase(ExtraFields.TWEET_TEXT_PMI_FEMALE.name()))
            return getPMIOfTextByGender(observations, observationId, obs -> obs.getTweet().getText(), "female");

        if(fieldName.equalsIgnoreCase(ExtraFields.TWEET_TEXT_PMI_BRAND.name()))
            return getPMIOfTextByGender(observations, observationId, obs -> obs.getTweet().getText(), "brand");

        if(fieldName.equalsIgnoreCase(ExtraFields.TWEET_TEXT_PMI_UNKNOWN.name()))
            return getPMIOfTextByGender(observations, observationId, obs -> obs.getTweet().getText(), "unknown");

        if(fieldName.equalsIgnoreCase(ExtraFields.USER_DESC_PMI_MALE.name()))
            return getPMIOfTextByGender(observations, observationId, obs -> obs.getUser().getProfile().getDescription(), "male");

        if(fieldName.equalsIgnoreCase(ExtraFields.USER_DESC_PMI_FEMALE.name()))
            return getPMIOfTextByGender(observations, observationId, obs -> obs.getUser().getProfile().getDescription(), "female");

        if(fieldName.equalsIgnoreCase(ExtraFields.USER_DESC_PMI_BRAND.name()))
            return getPMIOfTextByGender(observations, observationId, obs -> obs.getUser().getProfile().getDescription(), "brand");

        if(fieldName.equalsIgnoreCase(ExtraFields.USER_DESC_PMI_UNKNOWN.name()))
            return getPMIOfTextByGender(observations, observationId, obs -> obs.getUser().getProfile().getDescription(), "unknown");

        return null;
    }

    private static Double getPMIOfTextByGender(
            List<Observation> observations,
            int observationId,
            Function<Observation, String> extractor,
            String gender) {

        if(!termFreqCache.containsKey(gender)) {
            termFreqCache.put(
                    gender,
                    calculateTf(FeatureExtractorUtils.getTextForSubject(
                        observations,
                        obs -> obs.getUser().getGender().equalsIgnoreCase(gender),
                        extractor)));
        }

        if(!termFreqCache.containsKey("all")) {
            termFreqCache.put(
                    "all",
                    calculateTf(FeatureExtractorUtils.getTextForSubject(observations, obs -> true, extractor)));
        }

        if(!probs.containsKey(gender))
            probs.put(
                gender,
                new Double(observations.stream().filter(obs -> obs.getUser().getGender().equalsIgnoreCase(gender)).count()) / new Double(observations.size()));

        return Arrays.stream(extractor.apply(observations.get(observationId))
                .split(" "))
                .map(word -> {
                    if(!pmiCache.containsKey(gender))
                        pmiCache.put(gender, new HashMap<>());

                    if(!pmiCache.get(gender).containsKey(word))
                        pmiCache.get(gender).put(word, FeatureExtractorUtils.getPMI(word, termFreqCache.get(gender), termFreqCache.get("all"), probs.get(gender)));

                    return pmiCache.get(gender).get(word);
                })
                .reduce((x, y) -> x + y)
                .get();
    }

    private static Map<String, Integer> calculateTf(String text) {
        Map<String, Integer> tf = new HashMap<>();
        for(String word: text.split(" "))
            if(!tf.containsKey(word))
                tf.put(word, 1);
            else
                tf.put(word, tf.get(word) + 1);

        return tf;
    }
}
