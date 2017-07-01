package edu.fmi.genderclassify.dataimport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

public class MaleFemaleWordsReader {

    private static String DATA_FILE = "src\\main\\resources\\fisherMF.txt";
    private static Map<String, Double> scores;

    public static Map<String, Double> readMaleFemaleWordsScore() {
        try {
            if (scores != null) return scores;

            scores = Files.lines(Paths.get(DATA_FILE))
                    .collect(Collectors.toMap(
                            line -> line.split(" ")[0],
                            line -> Double.parseDouble(line.split(" ")[7])
                    ));

            return scores;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load the male/female words score dataset.");
        }
    }
}
