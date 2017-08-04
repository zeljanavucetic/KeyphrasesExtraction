/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keyphraseextraction.NLP;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Zana
 */
public class Annotator {
    public static List<CoreMap> AnnotateText (String text){
    
                Properties properties;
		properties = new Properties();
		properties.put("annotators", "tokenize, ssplit, pos, lemma");
                
		StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);
	
                List<CoreLabel> tokens = new LinkedList<CoreLabel>();
                
//                //read text from file
//                String text;
//                text = TextReader.getTextFromFile(fileName);
                
		// create an empty Annotation just with the given text
		Annotation document = new Annotation(text);
                
		// run all Annotators on this text
		pipeline.annotate(document);
                
                //List of all sentences in the text
                List<CoreMap> allSentences = document.get(CoreAnnotations.SentencesAnnotation.class); 
        
                return allSentences;
    }
}
