package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap map;

    public HistoryTextHandler(NGramMap map) {
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        StringBuilder result = new StringBuilder();
        for (String w : q.words()) {
            TimeSeries h = map.weightHistory(w, q.startYear(), q.endYear());
            result.append(w).append(": ").append(h.toString()).append("\n");
        }
        return result.toString();
    }
}
