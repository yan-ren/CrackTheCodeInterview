/*
 * Click `Run` to execute the snippet below!
 */

import java.io.*;
import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class TextProcessor {
    public static void main(String[] args) {
        System.out.println(new TextProcessor().getWords(SAMPLE_TEXT));

        TextProcessor s = new TextProcessor();
        // List<String> test = new ArrayList<>();
        // test.add("the");
        // test.add("dog");
        // test.add("ate");
        // test.add("the");
        // test.add("cat);
        s.analyze(s.getWords(SAMPLE_TEXT));
        System.out.println(s.map.get("alice"));
        System.out.println(s.getNextWord("alice", 0.2f));
        System.out.println(s.getNextWord("alice", 0.4f));
        System.out.println(s.getNextWord("alice", 0.6f));
        System.out.println(s.getNextWord("alice", 0.8f));
    }

    Map<String, Map<String, Integer>> map;

    //
    Map<String, Map<String, float[]>> probability = new HashMap<>();

    public static final String SAMPLE_TEXT = "Alice was beginning to get very tired of sitting by her sister on the\n"
            + "bank, and of having nothing to do: once or twice she had peeped into the\n"
            + "book her sister was reading, but it had no pictures or conversations in\n"
            + "it, 'and what is the use of a book,' thought Alice 'without pictures or\n" + "conversations?'\n" + "\n"
            + "So she was considering in her own mind (as well as she could, for the\n"
            + "hot day made her feel very sleepy and stupid), whether the pleasure\n"
            + "of making a daisy-chain would be worth the trouble of getting up and\n"
            + "picking the daisies, when suddenly a White Rabbit with pink eyes ran\n" + "close by her.\n" + "\n"
            + "There was nothing so VERY remarkable in that; nor did Alice think it so\n"
            + "VERY much out of the way to hear the Rabbit say to itself, 'Oh dear!\n"
            + "Oh dear! I shall be late!' (when she thought it over afterwards, it\n"
            + "occurred to her that she ought to have wondered at this, but at the time\n"
            + "it all seemed quite natural); but when the Rabbit actually TOOK A WATCH\n"
            + "OUT OF ITS WAISTCOAT-POCKET, and looked at it, and then hurried on,\n"
            + "Alice started to her feet, for it flashed across her mind that she had\n"
            + "never before seen a rabbit with either a waistcoat-pocket, or a watch\n"
            + "to take out of it, and burning with curiosity, she ran across the field\n"
            + "after it, and fortunately was just in time to see it pop down a large\n"
            + "rabbit-hole under the hedge.\n";

    /**
     * Sanitize and return a list of words we can work with - remove whitespaces -
     * remove punctuation - rabbit hole :) - rabbit-hole :) - rabbithole :/ - lower
     * case all words
     */
    List<String> getWords(String corpus) {
        String lowercase = corpus.toLowerCase();
        String result = lowercase.replaceAll("[^a-zA-Z]", " ");
        String[] elements = result.split(" ");
        List<String> output = new ArrayList<>();
        for (String s : elements) {
            if (!s.isEmpty()) {
                output.add(s);
            }

        }
        return output;
    }

    /**
     * Given a list of words, build a data structure that represents for each word:
     * what words follow it, and how many times they occur (or their probability).
     * For example, given the list of words: [the, dog, ate, the, cat] Your data
     * structure should contain the following information: the: (dog, 1), (cat, 1)
     * dog: (ate, 1) ate: (the, 1) cat: ?
     */
    void analyze(List<String> words) {
        // key: the -> {dog: 1},
        map = new HashMap<>();
        for (int i = 0; i < words.size() - 1; i++) {
            if (map.containsKey(words.get(i))) {
                String key = words.get(i);
                if (map.get(key).containsKey(words.get(i + 1))) {
                    map.get(key).put(words.get(i + 1), map.get(key).get(words.get(i + 1)) + 1);
                } else {
                    map.get(key).put(words.get(i + 1), 1);
                }
            } else {
                map.put(words.get(i), new HashMap<>());
                map.get(words.get(i)).put(words.get(i + 1), 1);
            }
        }
    }

    /**
     * Given a word and a probability, return the next word according to that
     * probability. For example, given the following data structure: the: (dog, 2),
     * (cat, 1) // "dog" follows "the" 66%, "cat" follows "the" 33% dog: (ate, 1)
     * ate: (the, 1) cat: ?
     *
     * getNextWord("the", 0.4) => "dog" // 0<p<0.66 -> "dog" | 0.33<p<1 === 66%
     * getNextWord("the", 0.6) => "dog" getNextWord("the", 0.7) => "cat" // 0.66<p<1
     * -> "cat" | 0<p<0.33 === 33%
     *
     * Another example, let's say the possible next words for "is" are "that":0.8
     * and "this":0.2, then given word="is" and 0<=p<0.2 return "this", and given
     * 0.2<=p<1, return "that".
     */
    String getNextWord(String word, float p) {
        if (probability.containsKey(word)) {
            Map<String, float[]> prob = probability.get(word);
            for (Map.Entry<String, float[]> entry : prob.entrySet()) {
                if (p < entry.getValue()[1] && p > entry.getValue()[0]) {
                    return entry.getKey();
                }
            }
        } else {
            probability.put(word, new HashMap<String, float[]>());
            Map<String, float[]> prob = probability.get(word);
            Map<String, Integer> values = map.get(word);
            int total = 0;
            for (Map.Entry<String, Integer> entry : values.entrySet()) {
                total += entry.getValue();
            }
            float previousProb = 0.0f;
            for (Map.Entry<String, Integer> entry : values.entrySet()) {
                float keyProb = (float) entry.getValue() / total;
                prob.put(entry.getKey(), new float[] { previousProb, previousProb + keyProb });
                previousProb += keyProb;
            }
            for (Map.Entry<String, float[]> entry : prob.entrySet()) {
                if (p < entry.getValue()[1] && p > entry.getValue()[0]) {
                    return entry.getKey();
                }
            }
        }

        return null;
    }

    /**
     * Generate a sentence of 'numberOfWords' words where each word was chosen with
     * probability 'p'
     */
    String buildSentence(String firstWord, int numberOfWords, float p) {
        return null;
    }

}
