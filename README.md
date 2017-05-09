# keyphraseExtraction
Get the most relevant keyphrases in the input text.

# About the Project

The main idea of this project is to develop a program for extracting the most relevant keyphrases from natural language texts that differ in length and type (blog post, news article, scientific work, etc..).
The whole solution is mostly based on the method published in the research paper "Keyword and Keyphrase Extraction Using Centrality Measures on Collocation Networks", by S. Lahiri, S. R. Choudhury, C. Caragea (2014). This paper can be downloaded from this [link](http://arxiv.org/pdf/1401.6571v1).
The code in this project rely on existing software libraries in the field of Natural Language Processing (NLP).

The main challenge was to optimize the Sliding window algorithm for successful extraction of keyphrases from texts that vary in length and/or type.

Extraction of keyphrases requires the following steps:
- splitting the input text into sentences and sentences into words
- determining the lemma of a word based on its intended meaning
- identifying noun phrases in the grammatical structure of a sentence by annotating lemmas with Part of Speech (POS) tags
- creating a keyphrase graph: 1. Nodes are the identified noun phrases.</br>
                  2. Edges are established between two nodes if the corresponding noun phrases occur in vicinity, that is, in a specific 'window' in the input text (The number of common occurrences of a pair of noun phrases in the text represents the weight of the edge that connects them).
- scoring the nodes of the graph using one of the centrality measures (in this case, using Degree centrality measure)
- returning given number of keyphrases sorted by their score

# Implementation

Libraries used in the project are:

[Stanford CoreNLP](http://nlp.stanford.edu/) library is used for NPL tasks, more precisely, to parse the input text. Stanford CoreNLP provides a set of natural language analysis tools. It can give the base forms of words, their parts of speech, whether they are names of companies, people, etc.; normalize dates, times, and numeric quantities; mark up the structure of sentences in terms of phrases and word dependencies; indicate which noun phrases refer to the same entities; indicate sentiment; extract particular or open-class relations between entity mentions; get quotes people said, etc.</br> 
[JUNG](http://jung.sourceforge.net/) is used for for creating graphs and scoring edges based on different SNA centrality measures.

The process of parsing the input text starts by running all annotators on the text using [annotate(Annotation document)](https://stanfordnlp.github.io/CoreNLP/api.html) method from CoreNLP package.</br>
After that, the text is divided into sentences and for each sentence, tokenizer divides sentence into a sequence of tokens.
Tokenization of text provides basic text units - tokens - including dots, commas, words.. </br>
List of all words in the input text is created, from tokens, for initializing 'window' (that is the span of words where pairs of noun phrases will be searched for). 
List of all tokens in the input text is created, for finding keyphrases. </br>
Both lists do not include lemmas that are less than 3 characters long and brackets presented in form of "-lrb-,-rrb-,-lsb-,-rsb-,-lcb-,-rcb-" (if condition).

[LexicalizedParser](https://nlp.stanford.edu/nlp/javadoc/javanlp/edu/stanford/nlp/parser/lexparser/LexicalizedParser.html) class provides parser for natural language text, which is used for parsing the list of tokens. A natural language parser works out the grammatical structure of sentences, for instance, which groups of words go together (as "phrases") and which words are the subject or object of a verb. 
After a sentence (list of tokens) is parsed, nodes that do not have tag NP (Noun Phrase) are ignored. Then, the remaining (NP) nodes are filtered; for isntance, all noun phrases that contain less than two or more than four words are ignored. At the end, list of the most relevant keyphrases is created.

The Sliding window algorithm makes a single pass through the entire document to determine how many times each pair of phrases in the phrase list occurs within a specified window size. Window size is defined as the median sentence length in the input document.

A graph is constructed for the input text as follows: nodes represent unique noun phrases, and edges link together noun phrases that co-occur within a specific window. Every edge between a pair of noun phrases is weighted with their co-occurrence frequency. At the end, nodes are sorted based on degree centrality measure using [DegreeScorer](http://jung.sourceforge.net/doc/api/edu/uci/ics/jung/algorithms/scoring/DegreeScorer.html) class from the JUNG library.

# Sliding window algorithm

A straightforward algorithm for the construction of a noun phrase collocation network would scan the whole document to determine how many times each pair of phrases in the phrase list occurs within a specified window size. However, this algorithm requires O(m2) passes (for m phrases) over the document text, making it computationally very expensive.</br>
Instead of that, this project uses a sliding window algorithm.

Generally speaking, a sliding window is a sub-list that runs over an underlying collection. In this case, the underlying collection is a collection of keyphrases, like [a b c d e f g h], where every character represents one keyphrase. A sliding window of size 3 would run over it like: 

 ``` 
    [a b c]
      [b c d]
        [c d e]
          [d e f]
            [e f g]
              [f g h]  
```
              
In this project, window size ws is the median of sentences length.
At the beginning, the window is initialized with the first ws words in the list of all words; it is searched for pairs of the first keyphrase in the keyphrases list. If the window does not contain the keyphrase, it is moved forward for the window size. Otherwise, that is, if the window contains the keyphrase, it is examined for the presence of other keyphrases, so that keyphrase pairs can be found. If a pair already exists, its edge weight is incremented.</br>
Next step is to make a new window by removing the words that precede the found keyphrase and adding the same number of new words. This new window serves for finding pairs for the next keyphrase in the keyphrases list.

# Analysis

In order to analyse how much the program is efficient in extracting the most relevant keyphrases from natural language texts,
several types of texts were used. The input texts for testing are: news article, scientific work, blog post.

Based on the results of analysis, general conclusion is that the program is more efficient for longer texts. The length of text is crucial. The program was more successful in extracting keyphrases from texts like scientific work or news article then from blog post. The reason is that the texts like blog post are mostly shorter and have just one sentence. If text have more then three sentences approximately, it is very easy to get the main topic of the input text based on the most relevant keyphrases.

Scientific work 
---------------

In file ScientificWork.txt is a text with this content: 
*“Keyword and keyphrase extraction is an
important problem in natural language
processing, with applications ranging
from summarization to semantic search
to document clustering. Graph-based approaches
to keyword and keyphrase extraction
avoid the problem of acquiring a
large in-domain training corpus by applying
variants of PageRank algorithm on a
network of words. Although graph-based
approaches are knowledge-lean and easily
adoptable in online systems, it remains
largely open whether they can benefit from
centrality measures other than PageRank.
In this paper, we experiment with an array
of centrality measures on word and
noun phrase collocation networks, and analyze
their performance on four benchmark
datasets. Not only are there centrality
measures that perform as well as or better
than PageRank, but they are much simpler
(e.g., degree, strength, and neighborhood
size). Furthermore, centrality-based
methods give results that are competitive
with and, in some cases, better than two
strong unsupervised baselines.”*

After the text passed through the code of program, the following keyphrases are extracted sorted by their score.

| Keyphrases                        | Score |
|-----------------------------------|-------|
| graph-based approaches            | 7     |
| summarization semantic search     | 5     |
| natural language                  | 5     |
| large in-domain training corpus   | 5     |
| keyphrase extraction              | 5     |         
| centrality measures               | 5     |
| pagerank algorithm network words  | 4     |
| important problem                 | 4     |           
| not only                          | 3     |
| adoptable online systems          | 3     |        
| four benchmark datasets           | 2     |
| centrality-based methods          | 2     |



# Acknowledgements

This project has been developed as a project assignment for the course Social Network Analysis at the Faculty of Organizational Sciences, University of Belgrade.
