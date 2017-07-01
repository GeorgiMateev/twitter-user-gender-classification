package edu.fmi.genderclassify;

import edu.fmi.genderclassify.dataimport.CsvReader;
import edu.fmi.genderclassify.entities.Observation;
import edu.fmi.genderclassify.weka.Classification;
import edu.fmi.genderclassify.weka.features.FeatureSetFactory;
import edu.fmi.genderclassify.weka.train.FeatureVectorsFactory;
import javafx.util.Pair;
import weka.core.Instances;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        /*
         * READ DATA
         */
        CsvReader reader = new CsvReader();
        Pair<List<Observation>, Map<String, Set<Object>>> data = reader.readObservations();
        Map<String, Set<Object>> dataDomain = data.getValue();
        List<Observation> observations = data.getKey();

        /*
         * Test classifiers
         */
        Instances standardInstances = FeatureVectorsFactory.constructInstances(
                FeatureSetFactory.getStandardFeatureSet(dataDomain),
                observations);

        Instances extendedInstances = FeatureVectorsFactory.constructInstances(
                FeatureSetFactory.getExtendedFeatureSet(dataDomain),
                observations);

        Classification.runJ48(observations, standardInstances);
        Classification.runNaiveBayes(observations, standardInstances);
        Classification.runSMO(observations, standardInstances);
        Classification.runIBk(observations, standardInstances);
    }
}
