package edu.cmu.lti.f13.hw4.hw4_zhiyul.casconsumers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;
import org.apache.uima.util.ProcessTrace;

import edu.cmu.lti.f13.hw4.hw4_zhiyul.typesystems.Document;
import edu.cmu.lti.f13.hw4.hw4_zhiyul.typesystems.Token;
import edu.cmu.lti.f13.hw4.hw4_zhiyul.utils.Utils;


public class RetrievalEvaluator extends CasConsumer_ImplBase {

	/** query id number **/
	public ArrayList<Integer> qIdList;

	/** query and text relevant values **/
	public ArrayList<Integer> relList;
	
	/** list of term frequency lists **/
	public ArrayList<Map<String, Integer>> termMapList;
	
	/** save the documents' text for output **/
	public ArrayList<String> docTexts;

  // save the stopwords list
  Set<String> stopwordsSet;
  
  // stemmer for terms
  Stemmer stemmer=new Stemmer();

		
	public void initialize() throws ResourceInitializationException {

		qIdList = new ArrayList<Integer>();

		relList = new ArrayList<Integer>();
		
		termMapList=new ArrayList<Map<String,Integer>>();
		
		docTexts=new ArrayList<String>();
		
		stopwordsSet=new HashSet<String>();

		// process stopword set
		Scanner scan=null;
    try {
      scan = new Scanner(new File("src/main/resources/stopwords_lucene.txt"));
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    String line=null;
    // skip the first three lines
    for (int i = 0; i < 3; i++) {
      line=scan.nextLine();
    }
    
    do {
      line=scan.nextLine();
//      System.out.println(line+"!");
      if (!stopwordsSet.contains(line)) {
        stopwordsSet.add(line);
      }
    } while (scan.hasNext());
	}

	/**
	 * TODO :: 1. construct the global word dictionary 
	 *         2. keep the word frequency for each sentence
	 */
	@Override
	public void processCas(CAS aCas) throws ResourceProcessException {

		JCas jcas;
		try {
			jcas =aCas.getJCas();
		} catch (CASException e) {
			throw new ResourceProcessException(e);
		}

		FSIterator it = jcas.getAnnotationIndex(Document.type).iterator();
//		FSIndex documentIndex=jcas.getAnnotationIndex(Document.type);
//		Iterator it=documentIndex.iterator();
	
		
    
		while (it.hasNext()) {
			Document doc = (Document) it.next();

			//Make sure that your previous annotators have populated this in CAS
			FSList fsTokenList = doc.getTokenList();
			ArrayList<Token> tokenList=Utils.fromFSListToCollection(fsTokenList, Token.class);
			Map<String, Integer> termFreqMap=new HashMap<String, Integer>();
			for(Token token:tokenList){
			  String term=token.getText();
			  // stem the term
        stemmer.add(term.toCharArray(), term.length());
        stemmer.stem();
        term=stemmer.toString();
        
//			  termFreqMap.put(term, token.getFrequency());
        // only process terms that are not in the stopwords list
        if (!stopwordsSet.contains(token.getText())) {
          termFreqMap.put(term, token.getFrequency());
        }
			}

			qIdList.add(doc.getQueryID());
			relList.add(doc.getRelevanceValue());
			termMapList.add(termFreqMap);
			docTexts.add(doc.getText());
			

		}

	}

	/**
	 * TODO 1. Compute Cosine Similarity and rank the retrieved sentences
	 *      2. Compute the MRR metric
	 */
	@Override
	public void collectionProcessComplete(ProcessTrace arg0)
			throws ResourceProcessException, IOException {

		super.collectionProcessComplete(arg0);
		
		// qIds checks whether we already processed a given query using the query id.
		Set<Integer> qIds=new HashSet<Integer>();
		
	  Map<String, Integer> queryVector=new HashMap<String, Integer>();
	  Map<String, Integer> docVector=new HashMap<String, Integer>();
	  // docScores contains the scores for the queries
	  ArrayList<Double> docScores = new ArrayList<Double>();
	  
    // relScore records the cosine similarity score for the current RELAVENT document
    double relScore=0;
    // relIndex records the index of the current RELAVENT document in the qIDList.
    int relIndex=0;
    
    // ranks contains the ranks for the relevant documents, will use to calculate mrr
    ArrayList<Integer> ranks=new ArrayList<Integer>();
    
	  for (int j = 0; j < qIdList.size(); j++) {
      //Make sure that your previous annotators have populated this in CAS
      
	    if (!qIds.contains(qIdList.get(j))) {
        qIds.add(qIdList.get(j));
        if (docScores.size()>0) {
          // first caclulate reciprocal rank for the previous query
          Collections.sort(docScores, Collections.reverseOrder());
          
          for (int i = 0; i < docScores.size(); i++) {
            if (relScore==docScores.get(i)) {
              System.out.print("Score: "+docScores.get(i));
              System.out.print("\trank="+String.valueOf(i+1));
              ranks.add(i+1);
              System.out.print("\trel="+relList.get(relIndex));
              System.out.print(" qid="+qIdList.get(relIndex));
              System.out.println(" "+docTexts.get(relIndex));
              break;
            }
          }
        }
        
        // reset ArrayList docScores 
        docScores=new ArrayList<Double>();
        queryVector=termMapList.get(j);
      }
      else{
        docVector=termMapList.get(j);
        double score=computeCosineSimilarity(queryVector, docVector);
        if (relList.get(j)==1) {
          relIndex=j;
          relScore=score;
        }
        docScores.add(score);
      }
    }
	  
    // calculate reciprocal rank for the previous query
    Collections.sort(docScores, Collections.reverseOrder());
    for (int i = 0; i < docScores.size(); i++) {
      if (relScore==docScores.get(i)) {
        System.out.print("Score: "+docScores.get(i));
        System.out.print("\trank="+String.valueOf(i+1));
        ranks.add(i+1);
        System.out.print("\trel="+relList.get(relIndex));
        System.out.print(" qid="+qIdList.get(relIndex));
        System.out.println(" "+docTexts.get(relIndex));
        break;
      }
    }
		// TODO :: compute the cosine similarity measure
    
    
		
		// TODO :: compute the rank of retrieved sentences
		
		
		
		// TODO :: compute the metric:: mean reciprocal rank
		double metric_mrr = compute_mrr(ranks);
		System.out.println(" (MRR) Mean Reciprocal Rank ::" + metric_mrr);
	}

	/**
	 * 
	 * @return cosine_similarity
	 */
	private double computeCosineSimilarity(Map<String, Integer> queryVector,
			Map<String, Integer> docVector) {
	  double sum=0;
	  double queryNorm=0;
	  double docNorm=0;
		double cosine_similarity=0.0;

		// TODO :: compute cosine similarity between two sentences
		for(String term:queryVector.keySet()){
		  queryNorm+=queryVector.get(term)*queryVector.get(term);
		  if(docVector.containsKey(term)){
		    sum+=docVector.get(term)*queryVector.get(term);
		  }
		}
		queryNorm=Math.sqrt(queryNorm);
		
		for(String term:docVector.keySet()){
		  docNorm+=docVector.get(term)*docVector.get(term);
		}
		docNorm=Math.sqrt(docNorm);
		
		cosine_similarity=sum/(queryNorm*docNorm);
		return cosine_similarity;
	}

	/**
	 * 
	 * @return mrr
	 */
	private double compute_mrr(ArrayList<Integer> ranks) {
		double metric_mrr=0.0;
		double sum=0;
		// TODO :: compute Mean Reciprocal Rank (MRR) of the text collection
		for(int rank:ranks){
		  sum+=1.0/rank;
		}
		metric_mrr=sum/ranks.size();
		return metric_mrr;
	}

}
