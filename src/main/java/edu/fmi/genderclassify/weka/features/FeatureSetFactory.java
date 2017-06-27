package edu.fmi.genderclassify.weka.features;

import edu.fmi.genderclassify.dataimport.Fields;
import javafx.util.Pair;
import weka.core.Attribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Miroslav Kramolinski
 *
 * This factory is used to supply a set of features, that would be then used in the feature vector
 */
public class FeatureSetFactory {
    public static List<Pair<String, Attribute>> getStandardFeatureSet(Map<String, Set<Object>> dataDomain) {
        BaseFeatureFactory factory = new BaseFeatureFactory(dataDomain);
        List<Pair<String, Attribute>> featureVector = new ArrayList<>();

        featureVector.add(new Pair(Fields.USER_ID.name(), factory.getUserId()));
        featureVector.add(new Pair(Fields.GOLDEN.name(), factory.getGolden()));
        featureVector.add(new Pair(Fields.UNIT_STATE.name(), factory.getUnitState()));
        featureVector.add(new Pair(Fields.TRUSTED_JUDGEMENTS.name(), factory.getTrustedJudgements()));
        featureVector.add(new Pair(Fields.LAST_JUDGEMENT_TIME.name(), factory.getLastJudgementTime()));
        featureVector.add(new Pair(Fields.GENDER_CONFIDENCE.name(), factory.getGenderConfidence()));
        featureVector.add(new Pair(Fields.PROFILE_EXISTS.name(), factory.getProfileExists()));
        featureVector.add(new Pair(Fields.PROFILE_EXISTS_CONFIDENCE.name(), factory.getProfileExistsConfidence()));
        featureVector.add(new Pair(Fields.PROFILE_CREATION_DATE.name(), factory.getProfileCreationDate()));
        featureVector.add(new Pair(Fields.PROFILE_DESCRIPTION.name(), factory.getDescription()));
        featureVector.add(new Pair(Fields.FAVORITES_NUMBER.name(), factory.getFavoritesNumber()));
        featureVector.add(new Pair(Fields.GENDER_GOLDEN.name(), factory.getGenderGold()));
        featureVector.add(new Pair(Fields.LINK_COLOR.name(), factory.getLinkColor()));
        featureVector.add(new Pair(Fields.USERNAME.name(), factory.getUserName()));
        featureVector.add(new Pair(Fields.PROFILE_YN_VALUE_GOLDEN.name(), factory.getProfileYnGolden()));
        featureVector.add(new Pair(Fields.PROFILE_IMAGE_LINK.name(), factory.getProfileImageLink()));
        featureVector.add(new Pair(Fields.RETWEET_COUNT.name(), factory.getRetweetsCount()));
        featureVector.add(new Pair(Fields.SIDEBAR_COLOR.name(), factory.getSidebarColor()));
        featureVector.add(new Pair(Fields.TWEET_TEXT.name(), factory.getText()));
        featureVector.add(new Pair(Fields.TWEET_COORDINATES.name() + "_LATITUDE", factory.getTweetLatitude()));
        featureVector.add(new Pair(Fields.TWEET_COORDINATES.name() + "_LONGITUDE", factory.getTweetLongitude()));
        featureVector.add(new Pair(Fields.TWEETS_COUNT.name(), factory.getTweetsCount()));
        featureVector.add(new Pair(Fields.TWEET_CREATION_DATE.name(), factory.getTweetCreationDate()));
        featureVector.add(new Pair(Fields.TWEET_ID.name(), factory.getTweetId()));
        featureVector.add(new Pair(Fields.TWEET_LOCATION.name(), factory.getTweetLocation()));
        featureVector.add(new Pair(Fields.USER_TIMEZONE.name(), factory.getUserTimezone()));

        // Class attribute should be last
        featureVector.add(new Pair(Fields.GENDER.name(), factory.getGender()));

        return featureVector;
    }
}
