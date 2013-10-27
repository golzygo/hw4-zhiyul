
/* First created by JCasGen Fri Oct 25 15:22:38 EDT 2013 */
package edu.cmu.lti.f13.hw4.hw4_zhiyul.typesystems;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** Assign a score for the corresponding document.
 * Updated by JCasGen Fri Oct 25 15:22:38 EDT 2013
 * @generated */
public class DocScore_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (DocScore_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = DocScore_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new DocScore(addr, DocScore_Type.this);
  			   DocScore_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new DocScore(addr, DocScore_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = DocScore.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.lti.f13.hw4.hw4_zhiyul.typesystems.DocScore");
 
  /** @generated */
  final Feature casFeat_score;
  /** @generated */
  final int     casFeatCode_score;
  /** @generated */ 
  public double getScore(int addr) {
        if (featOkTst && casFeat_score == null)
      jcas.throwFeatMissing("score", "edu.cmu.lti.f13.hw4.hw4_zhiyul.typesystems.DocScore");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_score);
  }
  /** @generated */    
  public void setScore(int addr, double v) {
        if (featOkTst && casFeat_score == null)
      jcas.throwFeatMissing("score", "edu.cmu.lti.f13.hw4.hw4_zhiyul.typesystems.DocScore");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_score, v);}
    
  
 
  /** @generated */
  final Feature casFeat_document;
  /** @generated */
  final int     casFeatCode_document;
  /** @generated */ 
  public int getDocument(int addr) {
        if (featOkTst && casFeat_document == null)
      jcas.throwFeatMissing("document", "edu.cmu.lti.f13.hw4.hw4_zhiyul.typesystems.DocScore");
    return ll_cas.ll_getRefValue(addr, casFeatCode_document);
  }
  /** @generated */    
  public void setDocument(int addr, int v) {
        if (featOkTst && casFeat_document == null)
      jcas.throwFeatMissing("document", "edu.cmu.lti.f13.hw4.hw4_zhiyul.typesystems.DocScore");
    ll_cas.ll_setRefValue(addr, casFeatCode_document, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public DocScore_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_score = jcas.getRequiredFeatureDE(casType, "score", "uima.cas.Double", featOkTst);
    casFeatCode_score  = (null == casFeat_score) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_score).getCode();

 
    casFeat_document = jcas.getRequiredFeatureDE(casType, "document", "edu.cmu.lti.f13.hw4.hw4_zhiyul.typesystems.Document", featOkTst);
    casFeatCode_document  = (null == casFeat_document) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_document).getCode();

  }
}



    