/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keyphraseextraction.graph;

import java.util.Comparator;
import java.util.Map;

/**
 *
 * @author Zana
 */
public class ScoreComparator implements Comparator<String> {
	 
    Map<String, Integer> map;
 
    public ScoreComparator(Map<String, Integer> base) {
        this.map = base;
    }
 
    public int compare(String a, String b) {
        if (map.get(a) >= map.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys 
    }
}
