/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keyphraseextraction.algorithm;

import java.util.List;

/**
 *
 * @author DM
 */

//input for sliding window algorithm
public class InputForAlgorithm {
    private List<String> nounPhrases;
    private int median;
    private List<String> allWords;

    
    public InputForAlgorithm (List<String> nounPhrases, int median, List<String> allWords){
    this.nounPhrases=nounPhrases;
    this.median=median;
    this.allWords=allWords;
    }

    /**
     * @return the nounPhrases
     */
    public List<String> getNounPhrases() {
        return nounPhrases;
    }

    /**
     * @param nounPhrases the nounPhrases to set
     */
    public void setNounPhrases(List<String> nounPhrases) {
        this.nounPhrases = nounPhrases;
    }

    /**
     * @return the median
     */
    public int getMedian() {
        return median;
    }

    /**
     * @param median the median to set
     */
    public void setMedian(int median) {
        this.median = median;
    }

    /**
     * @return the allWords
     */
    public List<String> getAllWords() {
        return allWords;
    }

    /**
     * @param allWords the allWords to set
     */
    public void setAllWords(List<String> allWords) {
        this.allWords = allWords;
    }
  
}
