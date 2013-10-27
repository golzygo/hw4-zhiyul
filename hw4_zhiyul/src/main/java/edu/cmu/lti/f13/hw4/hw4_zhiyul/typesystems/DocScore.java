

/* First created by JCasGen Fri Oct 25 15:22:38 EDT 2013 */
package edu.cmu.lti.f13.hw4.hw4_zhiyul.typesystems;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Assign a score for the corresponding document.
 * Updated by JCasGen Fri Oct 25 15:22:38 EDT 2013
 * XML source: /Users/golzygo/git/hw4-zhiyul/hw4_zhiyul/src/main/resources/descriptors/typesystems/VectorSpaceTypes.xml
 * @generated */
public class DocScore extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DocScore.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected DocScore() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public DocScore(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public DocScore(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public DocScore(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: score

  /** getter for score - gets 
   * @generated */
  public double getScore() {
    if (DocScore_Type.featOkTst && ((DocScore_Type)jcasType).casFeat_score == null)
      jcasType.jcas.throwFeatMissing("score", "edu.cmu.lti.f13.hw4.hw4_zhiyul.typesystems.DocScore");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((DocScore_Type)jcasType).casFeatCode_score);}
    
  /** setter for score - sets  
   * @generated */
  public void setScore(double v) {
    if (DocScore_Type.featOkTst && ((DocScore_Type)jcasType).casFeat_score == null)
      jcasType.jcas.throwFeatMissing("score", "edu.cmu.lti.f13.hw4.hw4_zhiyul.typesystems.DocScore");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((DocScore_Type)jcasType).casFeatCode_score, v);}    
   
    
  //*--------------*
  //* Feature: document

  /** getter for document - gets corresponding document
   * @generated */
  public Document getDocument() {
    if (DocScore_Type.featOkTst && ((DocScore_Type)jcasType).casFeat_document == null)
      jcasType.jcas.throwFeatMissing("document", "edu.cmu.lti.f13.hw4.hw4_zhiyul.typesystems.DocScore");
    return (Document)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((DocScore_Type)jcasType).casFeatCode_document)));}
    
  /** setter for document - sets corresponding document 
   * @generated */
  public void setDocument(Document v) {
    if (DocScore_Type.featOkTst && ((DocScore_Type)jcasType).casFeat_document == null)
      jcasType.jcas.throwFeatMissing("document", "edu.cmu.lti.f13.hw4.hw4_zhiyul.typesystems.DocScore");
    jcasType.ll_cas.ll_setRefValue(addr, ((DocScore_Type)jcasType).casFeatCode_document, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    