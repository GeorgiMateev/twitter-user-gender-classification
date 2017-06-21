package edu.fmi.genderclassify;

import edu.fmi.genderclassify.dataimport.CsvReader;

/**
 * Created by Miroslav Kramolinski
 */
public class Main {
    public static void main(String[] args) {
        CsvReader.readObservations();
    }
}
