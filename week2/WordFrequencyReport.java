package week2;

import java.util.HashMap;
import java.util.Map;

public class WordFrequencyReport {

    static void printFilteredWordFrequency(String feedback) {

        String cleaned = feedback
                .toLowerCase()
                .replace(".", "")
                .replace(",", "");

        String[] words = cleaned.split("\\s+");

        String[] stopWords = {
            "the", "was", "and", "a",
            "is", "of", "in"
        };

        HashMap<String, Integer> frequency =
            new HashMap<>();

        for (String word : words) {

            boolean isStopWord = false;

            for (String stop : stopWords) {

                if (word.equals(stop)) {
                    isStopWord = true;
                    break;
                }
            }

            if (!isStopWord) {

                frequency.put(
                    word,
                    frequency.getOrDefault(word, 0) + 1
                );
            }
        }

        // Print words in descending frequency.
        while (!frequency.isEmpty()) {

            String highestWord = null;
            int highestCount = -1;

            for (Map.Entry<String, Integer> entry
                    : frequency.entrySet()) {

                if (entry.getValue() > highestCount) {
                    highestCount = entry.getValue();
                    highestWord = entry.getKey();
                }
            }

            System.out.println(
                highestWord + ": " + highestCount
            );

            frequency.remove(highestWord);
        }
    }

    public static void main(String[] args) {

        printFilteredWordFrequency(
            "The mentor was great, the session was great and clear."
        );
    }
}