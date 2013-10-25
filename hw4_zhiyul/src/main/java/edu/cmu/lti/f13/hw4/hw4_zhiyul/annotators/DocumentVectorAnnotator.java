package edu.cmu.lti.f13.hw4.hw4_zhiyul.annotators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.cas.IntegerArray;
import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.jcas.tcas.Annotation;

import edu.cmu.lti.f13.hw4.hw4_zhiyul.typesystems.Token;
import edu.cmu.lti.f13.hw4.hw4_zhiyul.typesystems.Document;
import edu.cmu.lti.f13.hw4.hw4_zhiyul.utils.Utils;

public class DocumentVectorAnnotator extends JCasAnnotator_ImplBase {

	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {

		FSIterator<Annotation> iter = jcas.getAnnotationIndex().iterator();
		if (iter.isValid()) {
			iter.moveToNext();
			Document doc = (Document) iter.get();
			createTermFreqVector(jcas, doc);
		}

	}
	/**
	 * 
	 * @param jcas
	 * @param doc
	 */

	private void createTermFreqVector(JCas jcas, Document doc) {

		String docText = doc.getText();
//		Map<String, Integer> termFreqList=new HashMap<String, Integer>();
		ArrayList<Token> termFreqList=new ArrayList<Token>();
		//TO DO: construct a vector of tokens and update the tokenList in CAS
		String terms[]=docText.split(" ");
    int termPosition=doc.getBegin(); // save the current term's position
    for (String term : terms){
      while(!Character.isLetterOrDigit(term.charAt(term.length()-1))){
        term=term.substring(0, term.length()-1);
      } // discard the punctuation(s)
      
      // add the term to the Token annotation
      Token annotation=new Token(jcas);
      annotation.setBegin(termPosition);
      annotation.setEnd(termPosition+term.length());
      termPosition+=(term.length()+1); // assume there is only one space between tokens
      // not accurate, better to use a nlp tool, but stanford-nlp has a bug on my computer
      annotation.setText(term);
      annotation.setFrequency(1);
      
      boolean tokenExist=false;
      for(int i=0;i<termFreqList.size();i++){
        // not very efficient but corpus is small, and this is easy to implement.
        if(termFreqList.get(i).equals(annotation)){
          termFreqList.get(i).setFrequency(termFreqList.get(i).getFrequency()+1);
          tokenExist=true;
          break;
        }
//        if (termFreqList.get(i).getText().equals(term)) {
//          termFreqList.get(i).setFrequency(termFreqList.get(i).getFrequency()+1);
//        }
      }
      // add this token to the list if it's not added before
      if(!tokenExist){
        termFreqList.add(annotation);
      }
      
    }
    
    FSList fsList=Utils.fromCollectionToFSList(jcas, termFreqList);
    doc.setTokenList(fsList);

	}

}
