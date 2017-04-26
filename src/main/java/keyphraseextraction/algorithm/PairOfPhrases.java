/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keyphraseextraction.algorithm;

/**
 *
 * @author DM
 */
public class PairOfPhrases {
    
    //First phrase
    private String firstPhrase;
    //Second phrase
    private String secondPhrase;
    //the number of repetitions ( firstPhrase and secondPhrase ) in the same window
    private int edgeValue;

    public PairOfPhrases(String firstPhrase, String secondPhrase, int edgeValue){
    this.firstPhrase=firstPhrase;
    this.secondPhrase=secondPhrase;
    this.edgeValue=edgeValue;
    }

    /**
     * @return the firstPhrase
     */
    public String getFirstPhrase() {
        return firstPhrase;
    }

    /**
     * @param firstPhrase the firstPhrase to set
     */
    public void setFirstPhrase(String firstPhrase) {
        this.firstPhrase = firstPhrase;
    }

    /**
     * @return the secondPhrase
     */
    public String getSecondPhrase() {
        return secondPhrase;
    }

    /**
     * @param secondPhrase the secondPhrase to set
     */
    public void setSecondPhrase(String secondPhrase) {
        this.secondPhrase = secondPhrase;
    }

    /**
     * @return the edgeValue
     */
    public int getEdgeValue() {
        return edgeValue;
    }

    /**
     * @param edgeValue the edgeValue to set
     */
    public void setEdgeValue(int edgeValue) {
        this.edgeValue = edgeValue;
    }
    
    public String toString (){
    
        String pair = "first phrase: " + firstPhrase + " ************ second phrase: " + secondPhrase + " ************ edge value: " + edgeValue;
        return pair;
                }

    
    
}
