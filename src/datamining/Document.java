package datamining;

import java.util.LinkedHashSet;
import java.util.LinkedHashMap;

public class Document {

	
	int documentIndex;
	String classVariable;
	LinkedHashSet<String> termsInDocument;
	LinkedHashMap<String,Integer> termFrequencyMapping;
	public int getDocumentIndex() {
		return documentIndex;
	}
	public void setDocumentIndex(int documentIndex) {
		this.documentIndex = documentIndex;
	}
	public String getClassVariable() {
		return classVariable;
	}
	public void setClassVariable(String classVariable) {
		this.classVariable = classVariable;
	}
	public LinkedHashSet<String> getTermsInDocument() {
		return termsInDocument;
	}
	public void setTermsInDocument(LinkedHashSet<String> termsInDocument) {
		this.termsInDocument = termsInDocument;
	}
	public LinkedHashMap<String, Integer> getTermFrequencyMapping() {
		return termFrequencyMapping;
	}
	public void setTermFrequencyMapping(LinkedHashMap<String, Integer> termFrequencyMapping) {
		this.termFrequencyMapping = termFrequencyMapping;
	}
		
}
