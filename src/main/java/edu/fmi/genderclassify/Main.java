package edu.fmi.genderclassify;

import edu.fmi.genderclassify.dataimport.CsvReader;
import edu.fmi.genderclassify.entities.Observation;
import edu.fmi.genderclassify.weka.features.FeatureSetFactory;
import edu.fmi.genderclassify.weka.train.StandardFeaturesInstances;
import edu.fmi.genderclassify.weka.train.Train;
import javafx.util.Pair;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.DenseInstance;
import weka.core.Instance;
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



        Instances instances = StandardFeaturesInstances.getStandardFeaturesInstances(FeatureSetFactory.getStandardFeatureSet(dataDomain), observations);
        /*
         * TRAIN
         */
        Train train = new Train(dataDomain);
        Classifier model = null;

        int split = (int) (instances.size() * 0.7);
        Instances trainSet = new Instances(instances, 0, split);

        try {
            trainSet.deleteStringAttributes();
            model = train.getJ48Model(trainSet);
//            model = train.getNaiveBayes(trainSet);
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*
         * TEST
         */
        Instances testSet = new Instances(instances, split, instances.size() - split);
        Evaluation eTest = null;
        try {
            eTest = new Evaluation(trainSet); // training set
            eTest.evaluateModel(model, testSet); // testing set

            // Output statistics
            System.out.println(eTest.toSummaryString());

            // Get the confusion matrix ??
            double[][] cmMatrix = eTest.confusionMatrix();

            // Another test case, where we adapt one instance to the training set
            Instance iUse = new DenseInstance(instances.numAttributes());

            // Specify that the instance belong to the training set
            // in order to inherit from the set description
            iUse.setDataset(instances);

            // Get the likelihood of each classes
            // fDistribution[0] is the probability of being “positive”
            // fDistribution[1] is the probability of being “negative”
            double[] fDistribution = model.distributionForInstance(iUse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
