/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keyphraseextraction.graph;

import keyphraseextraction.algorithm.PairOfPhrases;
import edu.uci.ics.jung.algorithms.scoring.DegreeScorer;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author DM
 */
public class NounphrasesGraph {
    
    public static TreeMap<String, Integer> createGraphOfNounPhrases(List <PairOfPhrases> pairsOfPhrases) {
    
    Graph<NewNode, NewLink> nounPhrasesGraph = new UndirectedSparseGraph<NewNode, NewLink>();
    
    for(PairOfPhrases pair : pairsOfPhrases){
    NewNode first = new NewNode(pair.getFirstPhrase());
    NewNode second = new NewNode(pair.getSecondPhrase());
    NewLink link = new NewLink(first + " - " + second, pair.getEdgeValue());
    nounPhrasesGraph.addEdge(link, first, second);
    
    }
    
    System.out.println("The graph = " + nounPhrasesGraph.toString());
    
    	Collection<NewNode> nodes = new LinkedList<NewNode>();

		nodes = nounPhrasesGraph.getVertices();

		DegreeScorer<NewNode> degreeScorer = new DegreeScorer<NewNode>(nounPhrasesGraph);
		int score = 0;
		TreeMap<String, Integer> scoredPhrases = new TreeMap<String, Integer>();
		for (NewNode node : nodes) {

			score = degreeScorer.getVertexScore(node);
			scoredPhrases.put(node.getName(), score);
		}
               

		return scoredPhrases;
    }
    
}
