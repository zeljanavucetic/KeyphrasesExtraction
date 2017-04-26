/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keyphraseextraction.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author DM
 */
public class SlidingWindowAlgorithm {
    
    public static List<PairOfPhrases> GetPairsOfPhrases (InputForAlgorithm input){
        
        int windowSize=input.getMedian();
        List<String> allWords = input.getAllWords();
        List<String> allPhrases = input.getNounPhrases();
        
         // list where every pair of phrases, with weight between them, are placed
          List<PairOfPhrases> pairsOfPhrases = new ArrayList<PairOfPhrases>();
                           
                           //list which serve for sliding window
                           List<String> window = new LinkedList<String>();
                           //part of window, before founded noun phrase (all words that have been found  before certain phrase in the window )
                           String partOfWindow = null;
                           //number of all words that have been found before the phrase in the window (used for moving window through the text)
                           int overWordsCount = 0;
                           //for saving position of the last added word from allWords list to the window
                           int position = 0;
                           
                           //for checking -  does the window contain certain phrase
                           Boolean contain;
                          
                           
                           // create initial window with size of windowSize
                           for (int j = 0; j < windowSize; j++) {
			          window.add(allWords.get(j));
                                  //save position of the last added word from the list of all words in text
                                  position = j;
		             }
                           
                           System.out.println("\n\n\n****** Sliding window ******\n\n");
                           
                           //iterate through the list of noun phrases (two for loops)
                           for(int j = 0; j < allPhrases.size(); j++){
                          
                               contain = window.toString().replace(",", "").replace("[", "").replace("]", "").contains(allPhrases.get(j));
                            
                            System.out.println("\nNounphrase: " +  allPhrases.get(j));
                            System.out.println("Window for searching pairs: " +  window.toString() );  



                         //if window doesn't contain the phrase AND window length isn't smaller then windowSize (end of the text did not come)
                         //clear widow and initiate with new words from allWords list
                            if(!contain && !(window.size() < windowSize) ){
                                System.out.println("Current window doesn't include the phrase, replace with new one. ");
                               //as long as the window does not include the phrase AND end of the text did not come, slide window through the text 
                               while (contain == false && !(position >= allWords.size())) {
                                window.clear();
                                for (int z = 0; z < windowSize; z++ ) {
                                 //save position of the last added word from the list   
                                 position++;
                                   if(position < allWords.size()){
			           window.add(allWords.get(position));
                                   }
                                   else {
                                       break;
                                    }
                                }
                                System.out.println("Does new window contain the phrase? --> " +  window.toString()); 
                                contain = window.toString().replace(",", "").replace("[", "").replace("]", "").contains(allPhrases.get(j));
                                
                               }
                               System.out.println("New window for searching pairs: " +  window.toString() );
                           }//if window doesn't contain certain noun phrase
                            
                           PairOfPhrases pairphrases = null;
                           int exist = 0;
                           
                           //get pairs of noun phrases
                           for (int s = 0; s < allPhrases.size(); s++) {
                               exist = 0;
                               if(s != j){
					if (window.toString().replace(",", "").replace("[", "").replace("]", "").contains(allPhrases.get(s))) {
                                                
                                             // if list of pairs empty add first pair
                                             if(pairsOfPhrases.size() == 0){
                                                        pairphrases = new PairOfPhrases(allPhrases.get(j), allPhrases.get(s), 1);
							pairsOfPhrases.add(pairphrases);
                                                        System.out.println ("Pair: " + pairphrases.toString());
                                             }
                                             else {
                                                for (PairOfPhrases pairOfPhrases : pairsOfPhrases){
                                                // if pair of phrases exists increment edge value
                                                if ((pairOfPhrases.getFirstPhrase() == allPhrases.get(j) && pairOfPhrases.getSecondPhrase() == allPhrases.get(s)) || (pairOfPhrases.getFirstPhrase() == allPhrases.get(s) && pairOfPhrases.getSecondPhrase() == allPhrases.get(j))) {
						      pairOfPhrases.setEdgeValue(pairOfPhrases.getEdgeValue() + 1);
                                                      exist = 1;
                                                      System.out.println ("Pair: " + pairOfPhrases.toString());
                                                      break;
						  }
                                                }
                                                 // if pair of phrases doesn't exist, add new pair
                                                 if(exist == 0) {
                                                 pairphrases = new PairOfPhrases(allPhrases.get(j), allPhrases.get(s), 1);
						 pairsOfPhrases.add(pairphrases);
                                                  System.out.println ("Pair: " + pairphrases.toString());
                                                 }
                                             }
                                        }
                               }
                           }//get pairs of noun phrases
                          
                        if(!contain) partOfWindow = "";
                            else   
                             {
                                partOfWindow = window.toString().
                                                  replace(",", "").
                                                        substring(0, window.toString().replace(",", "").indexOf(allPhrases.get(j))).replace("[", "").replace("]", "") 
                                    + allPhrases.get(j);
                               }
                          //System.out.println("Part of window, before certain noun phrase: " + partOfWindow);
                          
                           //number of all words in partOfWindow string
                           overWordsCount = partOfWindow.split("\\s+").length;
                           
                           //move window forward for overWordsCount
                           if (overWordsCount == windowSize){ //if the phrase is at the end of window
                              window.clear();
                            for (int z = 0; z < windowSize; z++) {
                                if(position<allWords.size()){
			          window.add(allWords.get(position));
                                  position++;
                                }
                                else break;
		             }
                            }
                            else 
                              if(overWordsCount !=1 && !(window.size() < windowSize)){
                                for(int k = 0; k < overWordsCount; k++ ){
                                window.remove(0);
                                
                                  if(position < allWords.size()){
                                   
                                   window.add(allWords.get(position));
                                   position++;
                                 }
                                }
                               }//moves window forward for overWordsCount
                           
                            System.out.println("\nWindow for the next noun phrase: " +  window.toString() + "\n");
                       
                           }//iterate through the list of noun phrases (two for loops)
                           
                           System.out.println("****** Sliding window ******\n\n");
                           return pairsOfPhrases;
                    }
}
