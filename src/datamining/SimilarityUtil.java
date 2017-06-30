package datamining;

import java.util.ArrayList;
import java.util.HashSet;

public class SimilarityUtil {

	
	
	public void computeSimilarityOnCompleteDataSet(int queryIndex,ArrayList<Document> docsList)
	{
		Document queryDocument=docsList.get(queryIndex-1);
		ArrayList<Document> comparableList;
		ArrayList<Vectors> vectorsList;
		Vectors bestMatchInCosineSimilarity,bestMatchInEuclideanDistance;
		comparableList=fetchDocsListOfSameClassLabelOnCompleteDataSet(queryDocument, docsList);
		vectorsList=vectorsListToBeComputed(queryDocument,comparableList);
		
		bestMatchInCosineSimilarity=findTheBestMatchInCosineSimilarity(vectorsList);
		bestMatchInEuclideanDistance=findTheBestMatchInEuclideanDistance(vectorsList);
		
		printVectors(bestMatchInCosineSimilarity,true);
		printVectors(bestMatchInEuclideanDistance,false);
	}
	
	public void computeSimilarityForDocumentPair(int indexA,int indexB,ArrayList<Document> docsList)
	{
		Document A=docsList.get(indexA-1);
		Document B=docsList.get(indexB-1);
		if(!A.getClassVariable().equals(B.getClassVariable()))
		{
			System.out.println("Oops.The given documents class labels do not match!");
			System.exit(0);
		}
		HashSet<String> termsInBothDoc=processTerms(A, B);
		Vectors v=generateVectors(termsInBothDoc, A, B);
		computeCosineSimilarity(v);
		computeEuclideanDistance(v);
		printVectors(v,true);
		printVectors(v,false);
	}
	
	
	public ArrayList<Document> fetchDocsListOfSameClassLabelOnCompleteDataSet(Document queryDocument,ArrayList<Document> docsList)
	{
		//ArrayList<Document> listToBeCompared=new ArrayList<Document>();
		
//		for(Document d:docsList)
//		{
//			if(queryDocument.getDocumentIndex()!=d.getDocumentIndex())
//			{
//				if(queryDocument.getClassVariable().equals(d.getClassVariable()))
//				{
//					listToBeCompared.add(d);
//				}
//			}
//		}
		docsList.remove(queryDocument.getDocumentIndex()-1);
		return docsList;
	}
	
	public ArrayList<Vectors> vectorsListToBeComputed(Document queryDocument,ArrayList<Document> comparableList)
	{
		HashSet<String> termsInBothDoc=new HashSet<String>();
		Vectors v=new Vectors();
		ArrayList<Vectors> vectorsList=new ArrayList<Vectors>();
		for(Document d:comparableList)
		{
			termsInBothDoc=processTerms(queryDocument,d);
			v=generateVectors(termsInBothDoc,queryDocument,d);
			vectorsList.add(v);
		}
		return vectorsList;
	}
	
	public HashSet<String> processTerms(Document queryDocument,Document docToCompare)
	{
		HashSet<String> termsInBothDoc=new HashSet<String>();
		termsInBothDoc.addAll(getTerms(queryDocument));
		termsInBothDoc.addAll(getTerms(docToCompare));
        return termsInBothDoc;
	}
	
	public HashSet<String> getTerms(Document d)
	{
       HashSet<String> terms=new HashSet<String>();
		
		for(String term:d.getTermFrequencyMapping().keySet())
		{
			terms.add(term);
		}
		return terms;
	}
	
	public Vectors generateVectors(HashSet<String> termsInBothDoc,Document queryDocument,Document docToCompare)
	{
		double vectorA[]=new double[queryDocument.getTermsInDocument().size()+docToCompare.getTermsInDocument().size()];
		double vectorB[]=new double[queryDocument.getTermsInDocument().size()+docToCompare.getTermsInDocument().size()];
		int i=0;
		for(String s:termsInBothDoc)
		{
			
		   Integer frequencyInA=(queryDocument.getTermFrequencyMapping().containsKey(s))?queryDocument.getTermFrequencyMapping().get(s):0;
		   Integer frequencyInB=(docToCompare.getTermFrequencyMapping().containsKey(s))?docToCompare.getTermFrequencyMapping().get(s):0;
		   vectorA[i]=(frequencyInA!=0 && frequencyInA!=null)?(double)frequencyInA:0.00;
		   vectorB[i]=(frequencyInB!=0 && frequencyInB!=null)?(double)frequencyInB:0.00;
		   i++;
		}
		
		Vectors v=new Vectors();
		v.setVectorA(vectorA);
		v.setVectorB(vectorB);
		validateVectors(v);
		
		v.setDocumentA(queryDocument);
		v.setDocumentB(docToCompare);
		
		return v;
	}
	
	public void validateVectors(Vectors v)
	{
		double vectorA[]=new double[10];
		double vectorB[]=new double[10];
		
		vectorA=v.getVectorA();
		vectorB=v.getVectorB();
		
		if(vectorA.length!=vectorB.length)
		{
			System.out.println("Something Seriously Went Wrong");
		}
	}
	public Vectors findTheBestMatchInCosineSimilarity(ArrayList<Vectors> vectorsList)
	{
		Vectors bestMatch=new Vectors();
		double cosineSimilarity=0.0;
		double bestCosineSimilarity=0.0;
        
		for(Vectors v:vectorsList)
		{
		    cosineSimilarity=computeCosineSimilarity(v);
		    if(cosineSimilarity>bestCosineSimilarity)
		    {
		    	bestCosineSimilarity=cosineSimilarity;
		    	bestMatch=v;
		    }
		}
		return bestMatch;
	}
	
	public Vectors findTheBestMatchInEuclideanDistance(ArrayList<Vectors> vectorsList)
	{
		Vectors bestMatch=new Vectors();
		double euclideanDistance=0.0;
		double bestEuclideanDistance=Double.MAX_VALUE;
        
		for(Vectors v:vectorsList)
		{
			euclideanDistance=computeEuclideanDistance(v);
		    if(euclideanDistance<bestEuclideanDistance)
		    {
		    	bestEuclideanDistance=euclideanDistance;
		    	bestMatch=v;
		    }
		}
		return bestMatch;
	}
	public double computeCosineSimilarity(Vectors v)
	{
		double vectorA[]=new double[10];
		double vectorB[]=new double[10];
		
		vectorA=v.getVectorA();
		vectorB=v.getVectorB();
		
		double dotProduct = 0.0;
		double sumOfSquareOfVectorA=0.0;
		double sumOfSquareOfVectorB=0.0;
        double magnitudeVectorA = 0.0;
        double magnitudeVectorB = 0.0;
        double productOfMagnitudes=0.0;
        double cosineSimilarity = 0.0;
        
        for (int i = 0; i < vectorA.length; i++) 
        {
            dotProduct += vectorA[i] * vectorB[i];  
            sumOfSquareOfVectorA += vectorA[i] * vectorA[i];
            sumOfSquareOfVectorB += vectorB[i] * vectorB[i];
        }

        magnitudeVectorA = Math.sqrt(sumOfSquareOfVectorA);
        magnitudeVectorB = Math.sqrt(sumOfSquareOfVectorB);
        productOfMagnitudes=magnitudeVectorA*magnitudeVectorB;

       cosineSimilarity =(productOfMagnitudes!=0.0)? dotProduct / productOfMagnitudes:0.0;
       return cosineSimilarity;
	}
	
	public double computeEuclideanDistance(Vectors v)
	{
		double vectorA[]=new double[10];
		double vectorB[]=new double[10];
		
		vectorA=v.getVectorA();
		vectorB=v.getVectorB();
		
		double difference = 0.0;
		double squareOfDifference=0.0;
		double sumOfSquareOfDifference=0.0;
        double euclideanDistance = 0.0;
        
        for (int i = 0; i < vectorA.length; i++) 
        {
            difference = vectorB[i]-vectorA[i];  
            squareOfDifference = difference*difference;
            sumOfSquareOfDifference += squareOfDifference; 
        }
        euclideanDistance=Math.sqrt(sumOfSquareOfDifference);
        return euclideanDistance;
	}
	
	public void printVectors(Vectors v,boolean flag)
	{
		Document queryDocument= v.getDocumentA();
		Document bestNeighbouringDocument=v.getDocumentB();
		
		if(flag)
		{
			double cosineSimilarity=computeCosineSimilarity(v);
			System.out.println();
			System.out.println("Query Document Index:"+queryDocument.getDocumentIndex());
			System.out.println("Nearest Neighbour Index:"+bestNeighbouringDocument.getDocumentIndex());
			System.out.println("Cosine Similarity:"+cosineSimilarity);
			System.out.println("Query Class Label:"+queryDocument.getClassVariable());
			System.out.println("Neighbour Class Label:"+bestNeighbouringDocument.getClassVariable());
			if(queryDocument.getClassVariable().equals(bestNeighbouringDocument.getClassVariable()))
			{
				System.out.println("Class Labels Matched");
			}
			else
			{
				System.out.println("Class Labels did not match!");
			}
			
		}
		else
		{
			double euclideanDistance=computeEuclideanDistance(v);
			System.out.println();
			System.out.println("Query Document Index:"+queryDocument.getDocumentIndex());
			System.out.println("Nearest Neighbour Index:"+bestNeighbouringDocument.getDocumentIndex());
			System.out.println("Euclidean Distance:"+euclideanDistance);
			System.out.println("Query Class Label:"+queryDocument.getClassVariable());
			System.out.println("Neighbour Class Label:"+bestNeighbouringDocument.getClassVariable());
			if(queryDocument.getClassVariable().equals(bestNeighbouringDocument.getClassVariable()))
			{
				System.out.println("Class Labels Matched");
			}
			else
			{
				System.out.println("Class Labels did not match!");
			}
		}
		
	}
}
