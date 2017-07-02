package edu.fmi.genderclassify.weka.features;

import edu.fmi.genderclassify.dataimport.ExtraFields;
import edu.fmi.genderclassify.dataimport.Fields;
import weka.core.Attribute;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A factory for creating Attribute instances for extra (custom) features
 */
public class ExtraFeaturesFactory {
    private Map<String, Set<Object>> dataDomain;

    public ExtraFeaturesFactory(Map<String, Set<Object>> dataDomain) {
        this.dataDomain = dataDomain;
    }

    public Attribute getTweetLatitude() {
        return new Attribute(ExtraFields.TWEET_COORDINATES_LATITUDE.name());
    }

    public Attribute getTweetLongitude() {
        return new Attribute(ExtraFields.TWEET_COORDINATES_LONGITUDE.name());
    }

    public Attribute getTweetMaleFemaleWordsScore() {
        return new Attribute(ExtraFields.TWEET_MALE_FEMALE_WORDS_SCORE.name());
    }

    public Attribute getDescriptionMaleFemaleWordsScore() {
        return new Attribute(ExtraFields.DESCRIPTION_MALE_FEMALE_WORDS_SCORE.name());
    }

    public Attribute getTweetTextPMIMale() {
        return new Attribute(ExtraFields.TWEET_TEXT_PMI_MALE.name());
    }

    public Attribute getTweetTextPMIFemale() {
        return new Attribute(ExtraFields.TWEET_TEXT_PMI_FEMALE.name());
    }

    public Attribute getTweetTextPMIBrand() {
        return new Attribute(ExtraFields.TWEET_TEXT_PMI_BRAND.name());
    }

    public Attribute getTweetTextPMIUnknown() {
        return new Attribute(ExtraFields.TWEET_TEXT_PMI_UNKNOWN.name());
    }

    public Attribute getTweetTextGenderPrediction() {
        return new Attribute(
                ExtraFields.TWEET_TEXT_GENDER_PREDICT.name(),
                new ArrayList<>(dataDomain.get(Fields.GENDER.name()).stream().map(obj -> (String) obj).collect(Collectors.toList())));
    }

    public Attribute getUserDescriptionPMIMale() {
        return new Attribute(ExtraFields.USER_DESC_PMI_MALE.name());
    }

    public Attribute getUserDescriptionPMIFemale() {
        return new Attribute(ExtraFields.USER_DESC_PMI_FEMALE.name());
    }

    public Attribute getUserDescriptionPMIBrand() {
        return new Attribute(ExtraFields.USER_DESC_PMI_BRAND.name());
    }

    public Attribute getUserDescriptionPMIUnknown() {
        return new Attribute(ExtraFields.USER_DESC_PMI_UNKNOWN.name());
    }

    public Attribute getUserDescriptionGenderPrediction() {
        return new Attribute(
                ExtraFields.USER_DESC_GENDER_PREDICT.name(),
                new ArrayList<>(dataDomain.get(Fields.GENDER.name()).stream().map(obj -> (String) obj).collect(Collectors.toList())));
    }

    public Attribute getFirstNameFromUserNameBasedGenderPrediction() {
        return new Attribute(
                ExtraFields.FIRST_NAME_FROM_USERNAME_BASED_GENDER_PREDICT.name(),
                new ArrayList<>(dataDomain.get(Fields.GENDER.name()).stream().map(obj -> (String) obj).collect(Collectors.toList())));
    }
}
