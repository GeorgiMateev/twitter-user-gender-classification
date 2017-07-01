package edu.fmi.genderclassify.weka.train;

import edu.fmi.genderclassify.dataimport.ExtraFields;
import edu.fmi.genderclassify.dataimport.Fields;
import edu.fmi.genderclassify.entities.Observation;
import edu.fmi.genderclassify.utils.ValueExtractor;
import javafx.util.Pair;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Supplies feature vectors that are already filled with data and ready for action
 */
public class FeatureVectorsFactory {
    public static Instances constructInstances(List<Pair<String, Attribute>> attributesList, List<Observation> observations) {
        List<Pair<String, Attribute>> attributes = attributesList
                .stream()
                .filter(pair -> !pair.getValue().isString())
                .collect(Collectors.toList());

        Instances trainingSet = new Instances(
                "Standard set",
                (ArrayList<Attribute>) attributes
                        .stream()
                        .map(pair -> pair.getValue())
                        .collect(Collectors.toList()),
                observations.size());

        for(int i = 0; i < observations.size(); i ++) {
            Instance instance = new DenseInstance(attributes.size());

            for(Pair<String, Attribute> attributePair : attributes) {
                updateInstance(attributePair, instance, observations, i);
            }

            trainingSet.add(instance);
        }

        trainingSet.setClassIndex(attributes.size() - 1);

        return trainingSet;
    }

    private static void updateInstance(
            Pair<String, Attribute> attributePair,
            Instance instance,
            List<Observation> observations,
            int currentObservation) {
        if(Fields.contains(attributePair.getKey())) {
            if(attributePair.getValue().isNumeric())
                instance.setValue(attributePair.getValue(), ValueExtractor.getDoubleValue(attributePair.getKey(), observations, currentObservation));
            else if(attributePair.getValue().isNominal())
                instance.setValue(attributePair.getValue(), ValueExtractor.getStringValue(attributePair.getKey(), observations, currentObservation));
            else
                throw new IllegalArgumentException("Numeric or nominal attribute expected");
        } else if(ExtraFields.contains(attributePair.getKey())) {
            if(attributePair.getKey().equalsIgnoreCase(ExtraFields.TWEET_COORDINATES_LATITUDE.name())
                    || attributePair.getKey().equalsIgnoreCase(ExtraFields.TWEET_COORDINATES_LONGITUDE.name())
                    || attributePair.getKey().equalsIgnoreCase(ExtraFields.TWEET_MALE_FEMALE_WORDS_SCORE.name())
                    || attributePair.getKey().equalsIgnoreCase(ExtraFields.DESCRIPTION_MALE_FEMALE_WORDS_SCORE.name()))
                instance.setValue(attributePair.getValue(), ValueExtractor.getDoubleValue(attributePair.getKey(), observations, currentObservation));
            else if(attributePair.getKey().equalsIgnoreCase(ExtraFields.TWEET_TEXT_PMI_MALE.name())
                    || attributePair.getKey().equalsIgnoreCase(ExtraFields.TWEET_TEXT_PMI_FEMALE.name())
                    || attributePair.getKey().equalsIgnoreCase(ExtraFields.TWEET_TEXT_PMI_BRAND.name())
                    || attributePair.getKey().equalsIgnoreCase(ExtraFields.TWEET_TEXT_PMI_UNKNOWN.name())
                    || attributePair.getKey().equalsIgnoreCase(ExtraFields.USER_DESC_PMI_MALE.name())
                    || attributePair.getKey().equalsIgnoreCase(ExtraFields.USER_DESC_PMI_FEMALE.name())
                    || attributePair.getKey().equalsIgnoreCase(ExtraFields.USER_DESC_PMI_BRAND.name())
                    || attributePair.getKey().equalsIgnoreCase(ExtraFields.USER_DESC_PMI_UNKNOWN.name()))
                instance.setValue(attributePair.getValue(), ValueExtractor.getPMIValue(attributePair.getKey(), observations, currentObservation));
            else if(attributePair.getKey().equalsIgnoreCase(ExtraFields.TWEET_TEXT_GENDER_PREDICT.name())
                    || attributePair.getKey().equalsIgnoreCase(ExtraFields.USER_DESC_GENDER_PREDICT.name()))
                instance.setValue(attributePair.getValue(), ValueExtractor.getGenderPredictionBasedOnPMI(attributePair.getKey(), observations, currentObservation));
            else
                throw new IllegalArgumentException("No valid way to update instance for field: " + attributePair.getKey());
        } else {
            throw new IllegalArgumentException("Unknown field: " + attributePair.getKey());
        }
    }
}
