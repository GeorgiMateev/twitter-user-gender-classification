package edu.fmi.genderclassify.utils;

import edu.fmi.genderclassify.entities.Observation;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Miroslav Kramolinski
 */
public class FeatureExtractorUtils {
    /**
     * Returns the PMI score of the given word found in gender-specific text compared
     * to the text for all genders (i.e. the co-occurrence of the word with the
     * gender of the user). Useful to calculate the score of the likelihood of finding
     * this word in text written by user of this gender
     *
     * @param word the word that the PMI score is calculated for
     * @param genderTf the term frequency of each word found in texts of users of this gender
     * @param allTf the term frequency of each word found in all the input text
     * @param condProb the probability of the user being of the particular gender
     * @return
     */
    public static Double getPMI(String word, Map<String, Integer> genderTf, Map<String, Integer> allTf, Double condProb) {
        if(!genderTf.containsKey(word))
            return 0d;

        Double wordCountInExcerpt = new Double(genderTf.values().stream().reduce((v1, v2) -> v1 + v2).get());
        Double wordCountInAllText = new Double(genderTf.values().stream().reduce((v1, v2) -> v1 + v2).get());

        return (new Double(genderTf.get(word)) / wordCountInExcerpt) /
                ((new Double(allTf.get(word)) / wordCountInAllText) * condProb);
    }

    /**
     * Returns a concatenation of the string/text taken using the given @extractor
     * for every observation that matches the @filter
     *
     * @param observations the list of observations that would be tested
     * @param filter the condition used for testing the observations
     * @param extractor the data that would be extracted from the matching conditions
     * @return
     */
    public static String getTextForSubject(
            List<Observation> observations,
            Predicate<Observation> filter,
            Function<Observation, String> extractor) {
        String allTextForGender = "";
        for(Observation observation: observations) {
            if(filter.test(observation))
                allTextForGender += " " + extractor.apply(observation);
        }

        return allTextForGender;
    }
}
