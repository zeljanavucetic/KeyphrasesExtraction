/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keyphraseextraction.NLP;

import edu.stanford.nlp.trees.Tree;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DM
 */
public class NounPhrases {
    
    public static List<String> GetNounPhrases(Tree parseTree, List allNounPhrases){
        
           List<String> allPhrases = new ArrayList<String>();
           String phrase = "";
           
           
           
                        // get noun phrases based on the annotations in the given tree
                        for (Tree subtree: parseTree){
                                
                            if(subtree.label().value().equals("NP")){
                                
                                    if (!subtree.firstChild().label().value().equals("NP")) {
                                    
                                    System.out.println("   Noun phrase - root : " + subtree);
                                     
                                       if(!(subtree.numChildren()<2) && !(subtree.numChildren()>4)) {
                                              //System.out.println(subtree.firstChild().toString());
                                              System.out.println("\n  Noun phrase - root , 1. condition  : " + subtree +"\n");
                                             
                                                   if(!(subtree.numChildren()==2 && subtree.firstChild().label().value().equalsIgnoreCase("DT" ) || subtree.firstChild().label().value().equalsIgnoreCase("PRP$"))){
                                                       
                                                       System.out.println("Noun phrase - root , 2. condition --> PASSED  : " + subtree);
                                                       System.out.println("Number of children: " + subtree.numChildren() +"\n");
                                                       
                                                        for (int j = 0;j<subtree.numChildren();j++){
                                                            
                                                            System.out.println("Clild: " + subtree.getChild(j).toString());
                                                            System.out.println("Clild - number of clildren: " + subtree.getChild(j).numChildren());
                                                            
                                                            if (subtree.getChild(j).value().equals("SBAR") || subtree.getChild(j).numChildren()>1 ){
                                                                phrase = "";
                                                                break;
                                                            }
                                                            else {
                                                            if (j == subtree.numChildren() - 1)
                                                                phrase = phrase + subtree.getChild(j).firstChild().toString();
                                                            else
                                                                phrase = phrase + subtree.getChild(j).firstChild().toString() + " ";
                                                            }
                                                         }
                                                        
                                                        if(!allNounPhrases.contains(phrase.toLowerCase()) && phrase!=""){
                                                        allPhrases.add(phrase.toLowerCase());
                                                        }
                                                        
                                                        System.out.println("\nAdded noun phrase: " + phrase.toLowerCase() + "\n");
                                                   }
                                                   
                                                phrase = "";
                                                }
                                        
                                    }
                                }
                            }// get noun phrases based on the annotations in the given tree
        
        
 System.out.println("****** The end of the sentence ******\n");
        return allPhrases;
    
    }
    
}
