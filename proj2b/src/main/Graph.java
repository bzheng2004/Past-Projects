package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {
    private Map<Integer, Set<Integer>> hypoList;
    private Map<Integer, List<String>> idMap;
    private Map<String, Set<Integer>> wordId;

    public Graph(String hyponymFile, String synsetFile) {
        hypoList = new HashMap<>();
        idMap = new HashMap<>();
        wordId = new HashMap<>();

        loadSyn(synsetFile);
        loadHyp(hyponymFile);
    }

    private void loadSyn(String synsetFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(synsetFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int synsetId = Integer.parseInt(parts[0]);
                String[] words = parts[1].split(" ");

                List<String> wordList = Arrays.asList(words);
                idMap.put(synsetId, wordList);

                // Map each word to its corresponding synset ID
                for (String word : words) {
                    wordId.putIfAbsent(word, new HashSet<>());
                    wordId.get(word).add(synsetId);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private void loadHyp(String hyponymFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(hyponymFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int synsetId = Integer.parseInt(parts[0]);

                hypoList.putIfAbsent(synsetId, new HashSet<>());

                for (int i = 1; i < parts.length; i++) {
                    int hyponymId = Integer.parseInt(parts[i]);
                    hypoList.get(synsetId).add(hyponymId);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public Set<String> findHyponyms(List<String> words) {
        Set<Integer> synsetIds = new HashSet<>();

        for (String word : words) {
            if (wordId.containsKey(word)) {
                synsetIds.addAll(wordId.get(word));
            }
        }

        Set<Integer> allHyponyms = new HashSet<>();
        for (int id : synsetIds) {
            allHyponyms.add(id);
            findAllHyponyms(id, allHyponyms);
        }

        Set<String> hyponymWords = new HashSet<>();
        for (int id : allHyponyms) {
            List<String> wordsList = idMap.get(id);
            if (wordsList != null) {
                hyponymWords.addAll(wordsList);
            }
        }

        return hyponymWords;
    }

    private void findAllHyponyms(int synsetId, Set<Integer> allHyponyms) {
        if (!hypoList.containsKey(synsetId)) {
            return;
        }

        for (int hyponymId : hypoList.get(synsetId)) {
            if (!allHyponyms.contains(hyponymId)) {
                allHyponyms.add(hyponymId);
                findAllHyponyms(hyponymId, allHyponyms);
            }
        }
    }

}
