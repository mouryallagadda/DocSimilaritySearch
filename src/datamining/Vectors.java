package datamining;

public class Vectors {

	double vectorA[]=new double[10];
	double vectorB[]=new double[10];
	
	Document documentA=new Document();
	Document documentB=new Document();
	
	public double[] getVectorA() {
		return vectorA;
	}
	public void setVectorA(double[] vectorA) {
		this.vectorA = vectorA;
	}
	public double[] getVectorB() {
		return vectorB;
	}
	public void setVectorB(double[] vectorB) {
		this.vectorB = vectorB;
	}
	public Document getDocumentA() {
		return documentA;
	}
	public void setDocumentA(Document documentA) {
		this.documentA = documentA;
	}
	public Document getDocumentB() {
		return documentB;
	}
	public void setDocumentB(Document documentB) {
		this.documentB = documentB;
	}
	
	
}
