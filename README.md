# keyphraseExtraction
Get the most relevant keyphrases in the input text.

# About the Project

The main idea of this project is to extract the most relevant keyphrases from different texts in length and type (blog post, news article, scientific work etc..).
The whole solution is mostly based on the research - Keyword and Keyphrase Extraction Using Centrality Measures on Collocation Networks, published by S. Lahiri, S. R. Choudhury, C. Caragea (2014). This research can be downloaded from this [link](http://arxiv.org/pdf/1401.6571v1).
The code in this project is based on the research results in the field of Natural Language Processing (NLP).

The main challenge for me was to optimize Sliding window algorithm for successful extraction of keyphrases from diffrent texts in length and type.

Extraction of keyphrases require following steps:
- splitting text into sentences and sentences into words
- determining the lemma of a word based on its intended meaning
- identifying noun phrases in the grammatical structure of sentence by annotating lemmas with Part of Speech (POS) tags
- creating graph: 1. Nodes are identified noun phrases.
                  2. Edges are links between two nodes if corresponding noun phrases occur in a specific 'window' formed of words in input text (The number of common occurrences of the two noun phrases in text represent weight of edge).
- scoring the graph using one of the centrality measures (in this case, using Degree centrality measure)
- returning given number of keyphrases sorted by their score

# Implementation

Libraries used in project are:

[Stanford CoreNLP](http://nlp.stanford.edu/) library is used for NPL tasks (to parse the input text). Stanford CoreNLP provides a set of natural language analysis tools. It can give the base forms of words, their parts of speech, whether they are names of companies, people, etc., normalize dates, times, and numeric quantities, mark up the structure of sentences in terms of phrases and word dependencies, indicate which noun phrases refer to the same entities, indicate sentiment, extract particular or open-class relations between entity mentions, get quotes people said, etc.</br> 
[JUNG](http://jung.sourceforge.net/) is used for for creating graphs and scoring edges based on different SNA centrality measures.

The process of parsing the input text starts by running all annotators on the text using [annotate(Annotation document)](https://stanfordnlp.github.io/CoreNLP/api.html) method from CoreNLP package.</br>
After that, text is divided into sentences and for each sentence, tokenizer divides sentence into a sequence of tokens.
Tokenization of text provides basic text units - tokens - including dots, commas, words.. </br>
List of all words in the input text is created, from tokens, for initializing 'window'. 
List of all tokens in the input text is created, for finding keyphrases. </br>
Both lists don't include lemmas that are less than 3 characters long and brackets presented in form of "-lrb-,-rrb-,-lsb-,-rsb-,-lcb-,-rcb-" (if condition).

[LexicalizedParser](https://nlp.stanford.edu/nlp/javadoc/javanlp/edu/stanford/nlp/parser/lexparser/LexicalizedParser.html) class provides parser for natural language text, which is used for parsing the list of tokens. A natural language parser works out the grammatical structure of sentences, for instance, which groups of words go together (as "phrases") and which words are the subject or object of a verb. 
After the sentence (list of tokens) is parsed, all nodes that don't have tag NP (Noun Phrase) are ignored. Also, all noun phrases that contain less than two and more than four words are ignored. At the end, list of the most relevant keyphrases is created.

Sliding window algorithm did a single pass through the entire document to determine how many times each pair of phrases in the phrase list occurs within a specified window size. Window size represent the median sentence length of a document.

Graph is constructed for text as follows: nodes represent unique noun phrases, and edges link together noun phrases that occur within a specific window of each other. Every edge between pair of noun phrases is weighted with their co-occurrence frequency. While merging edges, if pair of phrases exists in list of pairs edge weights are incremented . At the end, nodes are sorted based on degree centrality measure using [DegreeScorer](http://jung.sourceforge.net/doc/api/edu/uci/ics/jung/algorithms/scoring/DegreeScorer.html) class from the JUNG library.

# Acknowledgements

This project has been developed as a project assignment for the subject Social network analysis at the Faculty of Organizational Sciences, University of Belgrade.
