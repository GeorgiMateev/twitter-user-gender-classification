package edu.fmi.genderclassify.utils;

import edu.fmi.genderclassify.dataimport.ExtraFields;
import edu.fmi.genderclassify.dataimport.Fields;
import edu.fmi.genderclassify.entities.Observation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Contains the specific logic for extracting the value from each available field in the system
 */
public class ValueExtractor {
    private static Map<String, List<Double>> fieldsValuesCache = new HashMap<>(); // spare some stream operations

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

        return null;
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
}
