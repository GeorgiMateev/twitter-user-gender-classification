package edu.fmi.genderclassify.weka;

import edu.fmi.genderclassify.weka.test.Test;
import edu.fmi.genderclassify.weka.train.Train;
import weka.classifiers.Classifier;
import weka.core.Instances;

/**
 * A place where we define methods for the different classifiers and variations
 * Each method should include the:
 *      - Shaping / retrieving of the appropriate feature vectors / instances
 *      - Running the training of the chosen model with the given train set
 *      - Running the testing of the chosen model with the given tets set
 */
public class Classification {
    public static void runJ48(Instances instances) {
        int split = (int) (instances.size() * 0.7);
        Instances trainSet = new Instances(instances, 0, split);
        Instances testSet = new Instances(instances, split, instances.size() - split);
        runJ48(trainSet, testSet);
    }

    public static void runJ48(Instances trainSet, Instances testSet) {
        System.out.println("#####################  J48  #####################");

        Classifier model = null;
        Train train = new Train(trainSet);

        /*
         * TRAIN
         */
        try {
            model = train.getJ48Model();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * TEST
         */
        Test test = new Test(trainSet, testSet);
        test.testModel(model);

        System.out.println("#####################  END OF J48  #####################");
        System.out.print("\n\n\n");
    }

    public static void runNaiveBayes(Instances instances) {
        int split = (int) (instances.size() * 0.7);
        Instances trainSet = new Instances(instances, 0, split);
        Instances testSet = new Instances(instances, split, instances.size() - split);
        runNaiveBayes(trainSet, testSet);
    }

    public static void runNaiveBayes(Instances trainSet, Instances testSet) {
        System.out.println("#####################  NAIVE BAYES  #####################");

        Classifier model = null;
        Train train = new Train(trainSet);

        /*
         * TRAIN
         */
        try {
            model = train.getNaiveBayes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * TEST
         */
        Test test = new Test(trainSet, testSet);
        test.testModel(model);

        System.out.println("#####################  END OF NAIVE BAYES  #####################");
        System.out.print("\n\n\n");
    }

    public static void runSMO(Instances instances) {
        int split = (int) (instances.size() * 0.7);
        Instances trainSet = new Instances(instances, 0, split);
        Instances testSet = new Instances(instances, split, instances.size() - split);
        runSMO(trainSet, testSet);
    }

    public static void runSMO(Instances trainSet, Instances testSet) {
        System.out.println("#####################  SMO (SVM)  #####################");

        Classifier model = null;
        Train train = new Train(trainSet);

        /*
         * TRAIN
         */
        try {
            model = train.getSMO();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * TEST
         */
        Test test = new Test(trainSet, testSet);
        test.testModel(model);

        System.out.println("#####################  END OF SMO (SVM)  #####################");
        System.out.print("\n\n\n");
    }

    public static void runIBk(Instances instances) {
        int split = (int) (instances.size() * 0.7);
        Instances trainSet = new Instances(instances, 0, split);
        Instances testSet = new Instances(instances, split, instances.size() - split);
        runIBk(trainSet, testSet);
    }

    public static void runIBk(Instances trainSet, Instances testSet) {
        System.out.println("#####################  IBk (kNN)  #####################");

        Classifier model = null;
        Train train = new Train(trainSet);

        /*
         * TRAIN
         */
        try {
            model = train.getIBk();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * TEST
         */
        Test test = new Test(trainSet, testSet);
        test.testModel(model);

        System.out.println("#####################  END OF IBk (kNN)  #####################");
        System.out.print("\n\n\n");
    }
}
