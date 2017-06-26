package edu.fmi.genderclassify.weka.train;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Instances;

import java.util.Map;
import java.util.Set;

/**
 * Created by Miroslav Kramolinski
 */
public class Train {
    private Map<String, Set<Object>> dataDomain;

    public Train(Map<String, Set<Object>> dataDomain) {
        this.dataDomain = dataDomain;
    }

    public Classifier getJ48Model(Instances instances) throws Exception {
        Classifier model = new J48();
        model.buildClassifier(instances);

        return model;
    }

    public Classifier getNaiveBayes(Instances instances) throws Exception {
        Classifier model = new NaiveBayes();
        model.buildClassifier(instances);

        return model;
    }
}
