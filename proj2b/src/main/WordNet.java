package main;

import java.util.List;
import java.util.Set;

public class WordNet {
    private Graph graph;

    public WordNet(String synsetFile, String hyponymFile) {
        graph = new Graph(hyponymFile, synsetFile);
    }

    public Set<String> findHyponyms(List<String> words) {
        return graph.findHyponyms(words);
    }
}
