/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keyphraseextraction.graph;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Zana
 */
public class Sorter {
    
    	// sort map of scored noun phrases by score
	public static TreeMap<String, Integer> sortPhrasesByScore(
			TreeMap<String, Integer> mapOfPhrases) {

		ScoreComparator vc = new ScoreComparator(mapOfPhrases);
		TreeMap<String, Integer> sortedMapOfPhrases = new TreeMap<String, Integer>(vc);
		sortedMapOfPhrases.putAll(mapOfPhrases);

		return sortedMapOfPhrases;
	}

	// return required number of noun phrases
	public static TreeMap<String, Integer> getSortedPhrases(TreeMap<String, Integer> scoredPhrases, int number) {
		int i = 0;
		TreeMap<String, Integer> requiredPhrases = new TreeMap<String, Integer>();
		for (Map.Entry<String, Integer> entry : scoredPhrases.entrySet()) {
			if (i >= number)
				break;
                        else{
			requiredPhrases.put(entry.getKey(), entry.getValue());
			i++;
                        }
		}
		return sortPhrasesByScore(requiredPhrases);
	}
    
}
