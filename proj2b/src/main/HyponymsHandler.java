package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wordNet;
    private NGramMap nGramMap;

    public HyponymsHandler(WordNet wn, NGramMap ngm) {
        this.wordNet = wn;
        this.nGramMap = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();

        if (words.isEmpty()) {
            return " ";
        }

        Set<String> hyponyms = new HashSet<>();

        List<String> firstWordList = new ArrayList<>();
        firstWordList.add(words.get(0));
        hyponyms = (wordNet.findHyponyms(firstWordList));

        for (int i = 1; i < words.size(); i++) {
            List<String> nextWordList = new ArrayList<>();
            nextWordList.add(words.get(i));
            Set<String> nextWordHyponyms = wordNet.findHyponyms(nextWordList);
            Set<String> tempS = new HashSet<>();
            for (String hyponym : hyponyms) {
                if (nextWordHyponyms.contains(hyponym)) {
                    tempS.add(hyponym);
                }
            }
            hyponyms = tempS;
        }

        List<String> sortedHyponyms = new ArrayList<>(hyponyms);
        Collections.sort(sortedHyponyms);

        if (k == 0) {
            return sortedHyponyms.toString();
        } else {
            Map<String, Double> countMap = new HashMap<>();

            for (String hyponym : sortedHyponyms) {
                TimeSeries ts = nGramMap.countHistory(hyponym, startYear, endYear);
                if (ts.size() != 0) {
                    double totalCount = 0;
                    for (double count : ts.values()) {
                        totalCount += count;
                    }
                    countMap.put(hyponym, totalCount);
                }
            }

            if (countMap.size() <= k) {
                List<String> sortedCountHyponyms = new ArrayList<>(countMap.keySet());
                Collections.sort(sortedCountHyponyms);
                return sortedCountHyponyms.toString();
            } else {
                Set<String> topHyponyms = new HashSet<>();
                for (int i = 0; i < k; i++) {
                    String maxWord = getMaxFrequencyWord(countMap);
                    topHyponyms.add(maxWord);
                    countMap.remove(maxWord);
                }

                List<String> sortedTopHyponyms = new ArrayList<>(topHyponyms);
                Collections.sort(sortedTopHyponyms);
                return sortedTopHyponyms.toString();
            }
        }
    }

    private String getMaxFrequencyWord(Map<String, Double> countMap) {
        String maxWord = "";
        double maxCount = -1;

        for (Map.Entry<String, Double> entry : countMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxWord = entry.getKey();
            }
        }
        return maxWord;
    }
}
