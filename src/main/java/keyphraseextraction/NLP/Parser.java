/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keyphraseextraction.NLP;

import keyphraseextraction.algorithm.InputForAlgorithm;
import static keyphraseextraction.NLP.Annotator.AnnotateText;
import static keyphraseextraction.NLP.NounPhrases.GetNounPhrases;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
/**
 *
 * @author DM
 */
public class Parser {
    
    public static int GetMedianLength (int [] countWordsArray){
         int median;
        //median sentence length, for the window
                 Arrays.sort(countWordsArray);
                 if (countWordsArray.length % 2 == 0)
                 median = (countWordsArray[countWordsArray.length/2] + countWordsArray[countWordsArray.length/2 - 1])/2;
                 else
                 median = countWordsArray[countWordsArray.length/2];
                 
                 return median;
    }
    
    public static InputForAlgorithm GetInputForAlgorithm (String fileName){
        
                List<CoreLabel> tokens = new LinkedList<CoreLabel>();
                
                String lexModel = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
		LexicalizedParser lp = LexicalizedParser
				.loadModel(lexModel);
                
		// for window
		List<String> allWords = new LinkedList<String>();
                
                //List of all sentences in the text
                List<CoreMap> allSentences = AnnotateText(fileName);
                
                List<Tree> phraseTree =new ArrayList<Tree>();
                
                //List of noun phrases in one sentence
                List<String> nounPhrases = new ArrayList<String>();
                
                //List of noun phrases in whole text
                List<String> allNounPhrases = new ArrayList<String>();
                
                //number of words in sentence
                int countWords = 0;
                
                //array of countWords for each sentence
                int[] countWordsArray = new int[allSentences.size()];
                
                // median sentence length for window size
                int median;
                
                System.out.println("Number of sentences: "+ allSentences.size() +"\n"); 
                
                //for adding number of words in one sentence to the array of numbers of words for each sentence 
                int index = 0;
                
                //for each sentence in text 
                for (CoreMap sentence : allSentences) {
                            
                          //System.out.println("Sentence: "+ i); 
                          
                          //get each token in sentence and add to the list of tokens
			  for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                              
				//putting token into his basic form
				String tokenString = token.get(CoreAnnotations.LemmaAnnotation.class)
						.toLowerCase();
                              
                                String word = token.word().toLowerCase();
                                
                                //numberOfWords++;
                                countWords++;
				
				// longer than 2 characters
				if (word.length() > 2 && !word.matches("-lrb-|-rrb-|-lsb-|-rsb-|-rcb-|-lcb-")) {
					allWords.add(word);
				} 
                                
				if (tokenString.length() > 2 && !word.matches("-lrb-|-rrb-|-lsb-|-rsb-|-rcb-|-lcb-")) {
					tokens.add(token);
                                        //System.out.println("Token: "  + token);
				}
                            }

			Tree parseTree = lp.apply(tokens);
                        //parseTree.pennPrint();
                        
                      System.out.println("\n\n****** NEW SENTENCE --> get noun phrases based on the annotations in the given tree ******\n");
                      // get noun phrases in sentence with the help of annotations in the parseTree
                      nounPhrases = GetNounPhrases(parseTree, allNounPhrases);
                      
                      //all noun phrases in the given text
                      for (String phrase : nounPhrases) {
				allNounPhrases.add(phrase);
			}
                      
                        tokens.clear();
                        //i++;
                       countWordsArray[index]=countWords;
                       countWords = 0;
                       index++;
                       }
                 
                 median = GetMedianLength(countWordsArray);
                 
    //input for sliding window algorithm             
    InputForAlgorithm input = new InputForAlgorithm (allNounPhrases, median, allWords); 
    
    return input;
    }
    
}
