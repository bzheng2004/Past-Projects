package main;

import browser.NgordnetQueryHandler;
import ngrams.NGramMap;

public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {

        NGramMap nGramMap = new NGramMap(wordFile, countFile);

        WordNet wordNet = new WordNet(synsetFile, hyponymFile);

        return new HyponymsHandler(wordNet, nGramMap);
    }
}
