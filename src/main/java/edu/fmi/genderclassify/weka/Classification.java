package edu.fmi.genderclassify.weka;

import edu.fmi.genderclassify.entities.Observation;
import edu.fmi.genderclassify.weka.test.Test;
import edu.fmi.genderclassify.weka.train.Train;
import weka.classifiers.Classifier;
import weka.core.Instances;

import java.util.List;

/**
 * A place where we define methods for the different classifiers and variations
 * Each method should include the:
 *      - Shaping / retrieving of the appropriate feature vectors / instances
 *      - Running the training of the chosen model with the given train set
 *      - Running the testing of the chosen model with the given tets set
 */
public class Classification {
    public static void runJ48(List<Observation> observations, Instances instances) {
        System.out.println("#####################  J48  #####################");

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

    public static void runNaiveBayes(List<Observation> observations, Instances instances)  {
        System.out.println("#####################  NAIVE BAYES  #####################");

        /*
         * TRAIN
         */
        Classifier model = null;

        int split = (int) (instances.size() * 0.8);
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

    public static void runSMO(List<Observation> observations, Instances instances) {
        System.out.println("#####################  SMO (SVM)  #####################");

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

    public static void runIBk(List<Observation> observations, Instances instances) {
        System.out.println("#####################  IBk (kNN)  #####################");

        /*
         * TRAIN
         */
        Classifier model = null;

        int split = (int) (instances.size() * 0.7);
        Instances trainSet = new Instances(instances, 0, split);
        Train train = new Train(trainSet);

        try {
            model = train.getIBk();
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*
         * TEST
         */
        Instances testSet = new Instances(instances, split, instances.size() - split);
        Test test = new Test(trainSet, testSet);
        test.testModel(model);

        System.out.println("#####################  END OF IBk (kNN)  #####################");
        System.out.print("\n\n\n");
    }
}
