package edu.fmi.genderclassify.weka.features;

import edu.fmi.genderclassify.dataimport.ExtraFields;
import weka.core.Attribute;

import java.util.Map;
import java.util.Set;

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
}
