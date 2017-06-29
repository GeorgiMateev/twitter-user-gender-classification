package edu.fmi.genderclassify.weka.train;

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
    public static Instances getStandardInstances(List<Pair<String, Attribute>> attributesList, List<Observation> observations) {
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
                if(attributePair.getValue().isNumeric())
                    instance.setValue(attributePair.getValue(), ValueExtractor.getDoubleValue(attributePair.getKey(), observations, i));
                else if(attributePair.getValue().isNominal())
                    instance.setValue(attributePair.getValue(), ValueExtractor.getStringValue(attributePair.getKey(), observations, i));
                else
                    throw new IllegalArgumentException("Numeric or nominal attribute expected");
            }

            trainingSet.add(instance);
        }

        trainingSet.setClassIndex(attributes.size() - 1);

        return trainingSet;
    }
}
