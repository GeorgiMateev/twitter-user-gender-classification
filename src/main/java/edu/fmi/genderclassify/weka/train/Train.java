package edu.fmi.genderclassify.weka.train;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.PolyKernel;
import weka.classifiers.trees.J48;
import weka.core.Instances;

/**
 * Created by Miroslav Kramolinski
 *
 * A supplier of classifiers/models
 */
public class Train {
    private Instances instances;

    public Train(Instances instances) {
        this.instances = instances;
    }

    public Classifier getJ48Model() throws Exception {
        Classifier model = new J48();
        model.buildClassifier(instances);

        return model;
    }

    public Classifier getNaiveBayes() throws Exception {
        Classifier model = new NaiveBayes();
        model.buildClassifier(instances);

        return model;
    }

    public Classifier getSMO() throws Exception {
        Classifier model = new SMO();
        ((PolyKernel) ((SMO) model).getKernel()).setExponent(2);
        model.buildClassifier(instances);

        return model;
    }
}
