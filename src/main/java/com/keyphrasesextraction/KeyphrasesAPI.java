/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keyphrasesextraction;

/**
 *
 * @author Zana
 */
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import java.util.TreeMap;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.*;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static keyphraseextraction.NLP.Parser.GetInputForAlgorithm;
import keyphraseextraction.algorithm.InputForAlgorithm;
import keyphraseextraction.algorithm.PairOfPhrases;
import static keyphraseextraction.algorithm.SlidingWindowAlgorithm.GetPairsOfPhrases;
import static keyphraseextraction.graph.NounphrasesGraph.createGraphOfNounPhrases;
import static keyphraseextraction.graph.Sorter.getSortedPhrases;

@Path("/hello")
public class KeyphrasesAPI {
    
//    @GET
//    @Path("/{param}")
//    public Response getMsg(@PathParam("param") String message) {
//        String output = "Hello " + message + "!";
//        return Response.status(200).entity(output).build();
//    } 
    
    	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getKeyphrases(
			@QueryParam("text") String text) throws IOException {

		//String text = TextInput.getStringFromInputStream(fileInputStream);
		String json = "";
		int status = 0;
          
          InputForAlgorithm input = null;
          List<PairOfPhrases> pairsOfPhrases = null;
          TreeMap<String, Integer> scoredPhrases = null;
          TreeMap<String, Integer> sortedPhrases = null;
          
         // get all noun phrases in the input text based on NPL tags
         //read text from file: ScientificWork.txt , NewsArticle1, NewsArticle2
         input = GetInputForAlgorithm(text);
         
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
		
//			KeyphrasesParser keyphrasesParser = new KeyphrasesParser();
			Gson gson = new Gson();
			json = gson.toJson(sortedPhrases);
			status = 1;
		
		
		if (status==1) {
			return Response.status(200).entity(json).build();
		}
		else {
			return Response.status(400).build();
		}
	}
        }
    

