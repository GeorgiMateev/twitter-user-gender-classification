package edu.fmi.genderclassify.weka.features;

import edu.fmi.genderclassify.dataimport.Fields;
import edu.fmi.genderclassify.utils.Conversion;
import javafx.util.Pair;
import org.w3c.dom.Attr;
import weka.core.Attribute;

import javax.smartcardio.ATR;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Miroslav Kramolinski
 */
public class BaseFeatureFactory {
    private Map<String, Set<Object>> dataDomain;

    public BaseFeatureFactory(Map<String, Set<Object>> dataDomain) {
        this.dataDomain = dataDomain;
    }

    public Attribute getUserId() {
        return new Attribute(Fields.USER_ID.name());
    }

    public Attribute getGolden() {
        return getNominalAttribute(Fields.GOLDEN);
    }

    public Attribute getUnitState() {
        return getNominalAttribute(Fields.UNIT_STATE);
    }

    public Attribute getTrustedJudgements() {
        return new Attribute(Fields.TRUSTED_JUDGEMENTS.name());
    }

    public Attribute getLastJudgementTime() {
        return getDateAttribute(Fields.LAST_JUDGEMENT_TIME);
    }

    public Attribute getGender() {
        return getNominalAttribute(Fields.GENDER);
    }

    public Attribute getGenderConfidence() {
        return new Attribute(Fields.GENDER_CONFIDENCE.name());
    }

    public Attribute getProfileExists() {
        return getNominalAttribute(Fields.PROFILE_EXISTS);
    }

    public Attribute getProfileExistsConfidence() {
        return new Attribute(Fields.PROFILE_EXISTS_CONFIDENCE.name());
    }

    public Attribute getProfileCreationDate() {
        return getDateAttribute(Fields.PROFILE_CREATION_DATE);
    }

    public Attribute getDescription() {
        return new Attribute(Fields.PROFILE_DESCRIPTION.name());
    }

    public Attribute getFavoritesNumber() {
        return new Attribute(Fields.FAVORITES_NUMBER.name());
    }

    public Attribute getGenderGold() {
        return getNominalAttribute(Fields.GENDER_GOLDEN);
    }

    public Attribute getLinkColor() {
        return new Attribute(Fields.LINK_COLOR.name());
    }

    public Attribute getUserName() {
        return new Attribute(Fields.USERNAME.name());
    }

    public Attribute getProfileYnGolden() {
        return getNominalAttribute(Fields.PROFILE_YN_VALUE_GOLDEN);
    }

    public Attribute getProfileImageLink() {
        return new Attribute(Fields.PROFILE_IMAGE_LINK.name());
    }

    public Attribute getRetweetsCount() {
        return new Attribute(Fields.RETWEET_COUNT.name());
    }

    public Attribute getSidebarColor() {
        return new Attribute(Fields.SIDEBAR_COLOR.name());
    }

    public Attribute getText() {
        return new Attribute(Fields.TWEET_TEXT.name());
    }

    public Attribute getTweetLatitude() {
        return new Attribute(Fields.TWEET_COORDINATES.name() + "_LATITUDE");
    }

    public Attribute getTweetLongitude() {
        return new Attribute(Fields.TWEET_COORDINATES.name() + "_LONGITUDE");
    }

    public Attribute getTweetsCount() {
        return new Attribute(Fields.TWEETS_COUNT.name());
    }

    public Attribute getTweetCreationDate() {
        return getDateAttribute(Fields.TWEET_CREATION_DATE);
    }

    public Attribute getTweetId() {
        return new Attribute(Fields.TWEET_ID.name());
    }

    public Attribute getTweetLocation() {
        return new Attribute(Fields.TWEET_LOCATION.name());
    }

    public Attribute getUserTimezone() {
        return new Attribute(Fields.USER_TIMEZONE.name());
    }

    private Attribute getDateAttribute(Fields field) {
        Set<String> dates = new HashSet<>();
        for(Object obj: dataDomain.get(field.name()))
            dates.add(Conversion.getValueAsStr(obj));

        Attribute att = new Attribute(field.name());
        for(String element: dates) {
            att.addStringValue(element);
        }

        return att;
    }

    private Attribute getNominalAttribute(Fields field) {
        Attribute att = new Attribute(field.name());
        for(Object obj: dataDomain.get(field.name()))
            att.addStringValue(Conversion.getValueAsStr(obj));

        return att;
    }
}
