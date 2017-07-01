package edu.fmi.genderclassify.weka.features;

import edu.fmi.genderclassify.dataimport.ExtraFields;
import edu.fmi.genderclassify.dataimport.Fields;
import javafx.util.Pair;
import weka.core.Attribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This factory is used to supply a set of features, that would be then used in the feature vector
 */
public class FeatureSetFactory {
    public static List<Pair<String, Attribute>> getStandardFeatureSet(Map<String, Set<Object>> dataDomain) {
        BaseFeaturesFactory factory = new BaseFeaturesFactory(dataDomain);
        ExtraFeaturesFactory extraFactory = new ExtraFeaturesFactory(dataDomain);
        List<Pair<String, Attribute>> featureVector = new ArrayList<>();

        featureVector.add(new Pair(Fields.GOLDEN.name(), factory.getGolden()));
        featureVector.add(new Pair(Fields.UNIT_STATE.name(), factory.getUnitState()));
        featureVector.add(new Pair(Fields.TRUSTED_JUDGEMENTS.name(), factory.getTrustedJudgements()));
        featureVector.add(new Pair(Fields.LAST_JUDGEMENT_TIME.name(), factory.getLastJudgementTime()));
        featureVector.add(new Pair(Fields.GENDER_CONFIDENCE.name(), factory.getGenderConfidence()));
        featureVector.add(new Pair(Fields.PROFILE_EXISTS.name(), factory.getProfileExists()));
        featureVector.add(new Pair(Fields.PROFILE_EXISTS_CONFIDENCE.name(), factory.getProfileExistsConfidence()));
        featureVector.add(new Pair(Fields.PROFILE_CREATION_DATE.name(), factory.getProfileCreationDate()));
//        featureVector.add(new Pair(Fields.PROFILE_DESCRIPTION.name(), factory.getDescription()));
        featureVector.add(new Pair(Fields.FAVORITES_NUMBER.name(), factory.getFavoritesNumber()));
        featureVector.add(new Pair(Fields.GENDER_GOLDEN.name(), factory.getGenderGold()));
        featureVector.add(new Pair(Fields.LINK_COLOR.name(), factory.getLinkColor()));
        featureVector.add(new Pair(Fields.USERNAME.name(), factory.getUserName()));
        featureVector.add(new Pair(Fields.RETWEET_COUNT.name(), factory.getRetweetsCount()));
        featureVector.add(new Pair(Fields.SIDEBAR_COLOR.name(), factory.getSidebarColor()));
//        featureVector.add(new Pair(Fields.TWEET_TEXT.name(), factory.getText()));
        featureVector.add(new Pair(ExtraFields.TWEET_MALE_FEMALE_WORDS_SCORE.name(), extraFactory.getTweetMaleFemaleWordsScore()));
        featureVector.add(new Pair(ExtraFields.DESCRIPTION_MALE_FEMALE_WORDS_SCORE.name(), extraFactory.getDescriptionMaleFemaleWordsScore()));
        featureVector.add(new Pair(ExtraFields.TWEET_COORDINATES_LATITUDE.name(), extraFactory.getTweetLatitude()));
        featureVector.add(new Pair(ExtraFields.TWEET_COORDINATES_LONGITUDE.name(), extraFactory.getTweetLongitude()));
        featureVector.add(new Pair(Fields.TWEETS_COUNT.name(), factory.getTweetsCount()));
        featureVector.add(new Pair(Fields.TWEET_LOCATION.name(), factory.getTweetLocation()));
        featureVector.add(new Pair(Fields.USER_TIMEZONE.name(), factory.getUserTimezone()));

        // Class attribute should be last
        featureVector.add(new Pair(Fields.GENDER.name(), factory.getGender()));

        return featureVector;
    }
}
