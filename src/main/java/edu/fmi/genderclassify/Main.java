package edu.fmi.genderclassify;

import edu.fmi.genderclassify.dataimport.CsvReader;
import edu.fmi.genderclassify.entities.Observation;
import edu.fmi.genderclassify.weka.Classification;
import javafx.util.Pair;

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
        Classification.runJ48(dataDomain, observations);
        Classification.runNaiveBayes(dataDomain, observations);
//        Classification.runSMO(dataDomain, observations);
    }
}
