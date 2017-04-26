/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keyphraseextraction.start;

import keyphraseextraction.algorithm.InputForAlgorithm;
import keyphraseextraction.algorithm.PairOfPhrases;
import static keyphraseextraction.NLP.Parser.GetInputForAlgorithm;
import static keyphraseextraction.graph.NounphrasesGraph.createGraphOfNounPhrases;
import static keyphraseextraction.graph.Sorter.getSortedPhrases;
import static keyphraseextraction.algorithm.SlidingWindowAlgorithm.GetPairsOfPhrases;
import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author DM
 */
public class Main {
   public static void main(String[] args) {
        System.out.println("****** MAIN ******\n\n\n");
       
          InputForAlgorithm input = null;
          List<PairOfPhrases> pairsOfPhrases = null;
          TreeMap<String, Integer> scoredPhrases = null;
          TreeMap<String, Integer> sortedPhrases = null;
        
         // get all noun phrases in the input text based on NPL tags
         //read text from file: ScientificWork.txt , NewsArticle1, NewsArticle2
         input = GetInputForAlgorithm("ScientificWork.txt");
        
        System.out.println("\n\nAll keyphrases: \n");
                 for (String phrase : input.getNounPhrases()) {
				System.out.println(phrase);
			}
        
        //find all relatonships between noun phrases
        pairsOfPhrases = GetPairsOfPhrases (input);
        
        //create graph 
        scoredPhrases = createGraphOfNounPhrases(pairsOfPhrases);
        
        //get sorted keyphrases by their score
        sortedPhrases = getSortedPhrases(scoredPhrases, 20);
                
        System.out.println("\nSorted keyphrases by score: \n");
        System.out.println(sortedPhrases);
                
        System.out.println("\n\n\n****** MAIN ******");
                        
    }
   
}
   
