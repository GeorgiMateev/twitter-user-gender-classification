package edu.fmi.genderclassify.weka;

import edu.fmi.genderclassify.entities.Observation;
import edu.fmi.genderclassify.weka.features.FeatureSetFactory;
import edu.fmi.genderclassify.weka.test.Test;
import edu.fmi.genderclassify.weka.train.FeatureVectorsFactory;
import edu.fmi.genderclassify.weka.train.Train;
import weka.classifiers.Classifier;
import weka.core.Instances;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Miroslav Kramolinski
 *
 * A place where we define methods for the different classifiers and variations
 * Each method should include the:
 *      - Shaping / retrieving of the appropriate feature vectors / instances
 *      - Running the training of the chosen model with the given train set
 *      - Running the testing of the chosen model with the given tets set
 */
public class Classification {
    public static void runJ48(Map<String, Set<Object>> dataDomain, List<Observation> observations) {
        System.out.println("#####################  J48  #####################");
        Instances instances = FeatureVectorsFactory.getStandardInstances(
                FeatureSetFactory.getStandardFeatureSet(dataDomain),
                observations);

        /*
         * TRAIN
         */
        Classifier model = null;

        int split = (int) (instances.size() * 0.7);
        Instances trainSet = new Instances(instances, 0, split);
        Train train = new Train(trainSet);

        try {
            model = train.getJ48Model();
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*
         * TEST
         */
        Instances testSet = new Instances(instances, split, instances.size() - split);
        Test test = new Test(trainSet, testSet);
        test.testModel(model);

        System.out.println("#####################  END OF J48  #####################");
        System.out.print("\n\n\n");
    }

    public static void runNaiveBayes(Map<String, Set<Object>> dataDomain, List<Observation> observations) {
        System.out.println("#####################  NAIVE BAYES  #####################");

        Instances instances = FeatureVectorsFactory.getStandardInstances(
                FeatureSetFactory.getStandardFeatureSet(dataDomain),
                observations);

        /*
         * TRAIN
         */
        Classifier model = null;

        int split = (int) (instances.size() * 0.7);
        Instances trainSet = new Instances(instances, 0, split);
        Train train = new Train(trainSet);

        try {
            model = train.getNaiveBayes();
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*
         * TEST
         */
        Instances testSet = new Instances(instances, split, instances.size() - split);
        Test test = new Test(trainSet, testSet);
        test.testModel(model);

        System.out.println("#####################  END OF NAIVE BAYES  #####################");
        System.out.print("\n\n\n");
    }

    public static void runSMO(Map<String, Set<Object>> dataDomain, List<Observation> observations) {
        System.out.println("#####################  SMO (SVM)  #####################");

        Instances instances = FeatureVectorsFactory.getStandardInstances(
                FeatureSetFactory.getStandardFeatureSet(dataDomain),
                observations);

        /*
         * TRAIN
         */
        Classifier model = null;

        int split = (int) (instances.size() * 0.7);
        Instances trainSet = new Instances(instances, 0, split);
        Train train = new Train(trainSet);

        try {
            model = train.getSMO();
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*
         * TEST
         */
        Instances testSet = new Instances(instances, split, instances.size() - split);
        Test test = new Test(trainSet, testSet);
        test.testModel(model);

        System.out.println("#####################  END OF SMO (SVM)  #####################");
        System.out.print("\n\n\n");
    }
}
