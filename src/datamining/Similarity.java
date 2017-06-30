package datamining;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Scanner;
//import java.util.Map;
import java.util.Set;



public class Similarity {




	@SuppressWarnings("resource")
	public static void main(String args[]) throws Exception
	{
		ArrayList<Document> docsList=new ArrayList<Document>();
		SimilarityUtil su=new SimilarityUtil();
		Similarity s=new Similarity();
		docsList=s.readFile();
		Scanner sc=new Scanner(System.in);
		
		
		int queryIndex=0;
		try
		{

			
			System.out.println("Please enter the query document index:");
		    queryIndex=sc.nextInt();
		    
			if(queryIndex<=0)
			{
			  System.out.println("\nPlease enter a value greater than zero");
			  throw new IllegalArguementException();
			}
			
			if(queryIndex>docsList.size())
			{
			  System.out.println("\nThe documents vector size is:"+docsList.size()+"\nplease enter a value less than or equal to the list size");
			  throw new IllegalArguementException();
			}
			su.computeSimilarityOnCompleteDataSet(queryIndex, docsList);
			
		}
		catch(Exception e)
		{
			sc.close();
			System.exit(0);
		}
		

	}
	
	public ArrayList<Document> readFile() 
	{
		String filePath=new String("C:\\Mourya\\Study\\1st Sem\\CS584-Theory and Applications of Data Mining\\Assignments\\cs584_hw2\\cs584_hw2\\data.txt");
		File file=new File(filePath);
		
		ArrayList<Document> docsList=new ArrayList<Document>();
		docsList=delimitFile(file);
		return docsList;

	}
	
	public ArrayList<Document> delimitFile(File file)
	{
		
		ArrayList<Document> docsList=new ArrayList<Document>();
		try
		{
		BufferedReader br=new BufferedReader(new FileReader(file));
		String docLine=br.readLine();
		int index=1;

			while(docLine!=null)
			{
				   	Document d=readDocLine(docLine,index);
				   	boolean flag=docsList.add(d);
				   	if(!flag)
				   	{
				   		System.out.println("Not able to add the document");
				   	}

			   	index++;
			   	docLine=br.readLine();
			}
            
		br.close();
		}

		catch(Exception e)
		{
			
		}
		return docsList;

	}
	
//	public void experiment(String classVariable,ArrayList<Document> docslist)
//	{
//		ArrayList<Document> tenset=new ArrayList<Document>();
//		for(Document d:docslist)
//		{
//			if(d.getClassVariable().equals(classVariable))
//			{
//				tenset.add(d);
//				printDocument(d);
//			}
//			if(tenset.size()==10)
//			{
//				break;
//			}
//		}
//		
//	}
	
	public HashSet<String> getClassVariablesSet(ArrayList<Document> docsList)
	{
		HashSet<String> classVariablesSet=new HashSet<String>();
		for(Document d:docsList)
		{
		classVariablesSet.add(d.getClassVariable());
		}
		return classVariablesSet;
	}
	
	public Document readDocLine(String line,int index)
	{
		String[] documentLine=line.split("\\t");
		
		if(documentLine.length!=2)
		{
			System.out.println("Fatal Error!!!");
			System.exit(0);
		}
		
		return createDocObj(documentLine,index);
	}
	
	public Document createDocObj(String[] documentLine,int index)
	{
		Document doc=new Document();
		doc.documentIndex=index;
		doc.classVariable=documentLine[0];
		String[] termsList=documentLine[1].split("\\s+");
		LinkedHashSet<String> uniqueTerms=new LinkedHashSet<String>();
		LinkedHashMap<String,Integer> s=new LinkedHashMap<String,Integer>();
		
		for(int i=0;i<termsList.length;i++)
		{
			uniqueTerms.add(termsList[i]);
	        if(s.containsKey(termsList[i]))
	        {
	        	s.put(termsList[i],s.get(termsList[i])+1);
	        }
	        else
	        {
	        	s.put(termsList[i], 1);
	        }
		}
		
	    Set<String> actualTerms=s.keySet();
	    
	    if(!actualTerms.containsAll(uniqueTerms))
	    {
	    	System.out.println("Something went wrong!!!");
	    }
		
	    doc.termsInDocument=uniqueTerms;
	    doc.termFrequencyMapping=s;
	    return doc;
		
	}
	
	public void printDocument(Document d)
	{
		System.out.println("document index:"+d.documentIndex);
		System.out.println("class variable:"+d.classVariable);
		System.out.println("Doc term size:"+d.getTermFrequencyMapping().size());
		Iterator<String> i=d.termFrequencyMapping.keySet().iterator();
		while(i.hasNext())
		{
			String term=i.next();
			Integer frequency=d.termFrequencyMapping.get(term);
			System.out.println("Term:"+term+"\t"+"frequency:"+frequency);
			
		}
		
	}
}
