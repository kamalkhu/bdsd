package myapps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
 
public class TFIDFCalculator {

    public static HashMap<String, Double> computeTF(String articleText) {
        String[] words = articleText.split("\\s+");
        int totalWordCount = words.length;
        HashMap<String, Double> tfMap = new HashMap<String, Double>();
        for (String word : words) {
            double wordFreq = wordCounter(words, word);
            double tfScore = wordFreq / totalWordCount;
            tfMap.put(word, tfScore);
        }
        return tfMap;
    }
 
    public static double wordCounter(String[] allWords, String targetWord) {
        double count = 0.0;
        for (String word : allWords) {
            if (word.equals(targetWord)) {
                count += 1;
            }
        }
        return count;
    }
 
    public static HashMap<String, Double> computeIDF(String[] previousArticlesText, String curArticleText) {
        HashMap<String, Double> idfScores = new HashMap<String, Double>();
        ArrayList<String> allArticles = new ArrayList<String>();
 
        allArticles.add(curArticleText);
        for (String text : previousArticlesText) {
            allArticles.add(text);
        }
 
        // Number of times a word is found in ALL articles
        HashMap<String, Integer> wordCounter = new HashMap<String, Integer>();
        for (String text : allArticles) {
            Set<String> words = new HashSet<String>(Arrays.asList(text.split("\\s+")));
            for (String word : words) {
                if (wordCounter.containsKey(word)) {
                    wordCounter.put(word, wordCounter.get(word) + 1);
                } else {
                    wordCounter.put(word, 1);
                }
            }
        }
 
        for (Map.Entry<String, Integer> word : wordCounter.entrySet()) {
            double score = Math.log10(allArticles.size() / (double) word.getValue());
            idfScores.put(word.getKey(), score);
        }
 
        return idfScores;
    }
 
    public static HashMap<String, Double> computeTFIDF(HashMap<String, Double> tfScores,
            HashMap<String, Double> idfScores) {
        HashMap<String, Double> tfIdfScores = new HashMap<String, Double>();
        for (Map.Entry<String, Double> tfScore : tfScores.entrySet()) {
            tfIdfScores.put(tfScore.getKey(), tfScore.getValue() * idfScores.get(tfScore.getKey()));
        }
        return tfIdfScores;
    }
}
