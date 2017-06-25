package edu.fmi.genderclassify;

import edu.fmi.genderclassify.dataimport.CsvReader;
import edu.fmi.genderclassify.entities.Observation;
import edu.fmi.genderclassify.weka.features.FeatureSetFactory;
import edu.fmi.genderclassify.weka.train.StandardFeaturesInstances;
import edu.fmi.genderclassify.weka.train.Train;
import javafx.util.Pair;
import weka.classifiers.Classifier;
import weka.core.Instances;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Miroslav Kramolinski
 */
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
         * TRAIN
         */
        Instances instances = StandardFeaturesInstances.getStandardFeaturesInstances(FeatureSetFactory.getStandardFeatureSet(dataDomain), observations);
        Train train = new Train(dataDomain);
        try {
            Classifier model = train.getJ48Model(instances);
        } catch (Exception e) {
            e.printStackTrace();
        }



        /*
         * TEST
         */
    }
}
