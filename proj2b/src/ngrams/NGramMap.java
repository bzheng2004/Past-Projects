package ngrams;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.algs4.In;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    private Map<String, TimeSeries> wordsI;
    private TimeSeries total;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        wordsI = new HashMap<>();
        total = new TimeSeries();

        In file = new In(wordsFilename);
        while (file.hasNextLine()) {
            String[] split = file.readLine().split("\t");
            String word = split[0];
            int year = Integer.parseInt(split[1]);
            double appearance = Double.parseDouble(split[2]);
            TimeSeries ts = wordsI.get(word);
            if (ts == null) {
                ts = new TimeSeries();
            }
            ts.put(year, appearance);
            wordsI.put(word, ts);
        }
        In countF = new In(countsFilename);
        while (countF.hasNextLine()) {
            String[] split = countF.readLine().split(",");
            int year = Integer.parseInt(split[0]);
            double appearance = Double.parseDouble(split[1]);
            total.put(year, appearance);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (!wordsI.containsKey(word)) {
            return new TimeSeries();
        }
        return new TimeSeries(wordsI.get(word), startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if (!wordsI.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries base = wordsI.get(word);
        TimeSeries copy = new TimeSeries();
        for (int y : base.years()) {
            copy.put(y, base.get(y));
        }
        return copy;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        TimeSeries copy = new TimeSeries();
        for (int y : total.years()) {
            copy.put(y, total.get(y));
        }
        return copy;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries countW = countHistory(word, startYear, endYear);
        if (countW.isEmpty()) {
            return new TimeSeries();
        }
        TimeSeries totalW = totalCountHistory();
        TimeSeries relativeFre = new TimeSeries();
        for (int y = startYear; y <= endYear; y++) {
            Double counts = countW.get(y);
            Double totals = totalW.get(y);
            if (totals != null && totals > 0 && counts != null) {
                double relativeF = counts / totals;
                relativeFre.put(y, relativeF);
            }
        }
        return relativeFre;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries countW = countHistory(word);
        if (countW.isEmpty()) {
            return new TimeSeries();
        }
        TimeSeries totalW = totalCountHistory();
        TimeSeries relativeFre = new TimeSeries();
        for (int y: countW.years()) {
            double counts = countW.get(y);
            double totals = totalW.get(y);
            if (totals > 0) {
                double relativeF  = counts / totals;
                relativeFre.put(y, relativeF);
            }
        }
        return relativeFre;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries summedFreqs = new TimeSeries();
        for (String w: words) {
            TimeSeries weightH = weightHistory(w, startYear, endYear);
            for (int y = startYear; y <= endYear; y++) {
                Double freq = weightH.get(y);
                if (freq != null && freq > 0) {
                    double sum = 0;
                    if (summedFreqs.get(y) != null) {
                        sum = summedFreqs.get(y);
                    }
                    summedFreqs.put(y, sum + freq);
                }
            }
        }
        return summedFreqs;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries summedFreqs = new TimeSeries();
        for (String w: words) {
            TimeSeries weightH = weightHistory(w);
            for (int y : weightH.years()) {
                Double freq = weightH.get(y);
                if (freq != null && freq > 0) {
                    double sum = 0;
                    if (summedFreqs.get(y) != null) {
                        sum = summedFreqs.get(y);
                    }
                    summedFreqs.put(y, sum + freq);
                }
            }
        }
        return summedFreqs;
    }
}
