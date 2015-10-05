package util.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class FileReader {
	private Set<String> featurewords; // store the features of a certain product category
	private Set<String> posWords; // store the positive sentiment word
	private Set<String> negWords; // store the negative sentiment word
	private Map<String, ArrayList<String>> sameMeaningFeature; // store the same meaning features of a category into a map

	/**
	 * constructor - create a FileReader object
	 * @param FeatureFile - the path and filename of the file containing the feature words profile data
	 * @param PosWordFile - the path and filename of the file containing the positive sentiment word profile data
	 * @param NegWordFile - the path and filename of the file containing the negative sentiment word profile data
	 * @param SameFeatureFile - the path and filename of the file containing the same feature name profile data
	 */
	public FileReader(final String FeatureFile, final String PosWordFile, final String NegWordFile, final String SameFeatureFile) {
		super();
		System.out.println("Read in files...");
		this.featurewords = readDatabase(FeatureFile);
		this.posWords = readDatabase(PosWordFile);
		this.negWords = readDatabase(NegWordFile);
		this.sameMeaningFeature = readSameFeatures(SameFeatureFile);
	}

	/**
	 * @return the same feature names
	 */
	public Map<String, ArrayList<String>> getCameraSameF() {
		return sameMeaningFeature;
	}

	/**
	 * @return the product category features
	 */
	public Set<String> getFeaturewords() {
		return featurewords;
	}

	/**
	 * @return the positive sentiment words
	 */
	public Set<String> getPosWords() {
		return posWords;
	}

	/**
	 * @return the negative sentiment words
	 */
	public Set<String> getNegWords() {
		return negWords;
	}

	/**
	 * @param filename the path and filename of the file containing features or sentiment words profiles
	 * @return the category feature or sentiment words profiles
	 */
	private static Set<String> readDatabase(String filename) {
		Set<String> feature = new HashSet<String>();
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(new  FileInputStream(filename),"UTF-8"));
			String line;
			
			while ((line = br.readLine()) != null) 
			{
				StringTokenizer st = new StringTokenizer(line, "\t");
				String string = st.nextToken();
				if (!string.startsWith(";"))
					feature.add(string);
			}
			
			br.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		return feature;
	}
	
	/**
	 * @param filename the path and filename of the file containing same features profiles
	 * @return the same features of a certain product category
	 */
	private static Map<String, ArrayList<String>> readSameFeatures(String filename)
	{
		Map<String, ArrayList<String>> sameFeature = new HashMap<String, ArrayList<String>>();
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(new  FileInputStream(filename),"UTF-8"));
			String line;
			
			while ((line = br.readLine()) != null) 
			{
				ArrayList<String> features = new ArrayList<String>();
				for(String single: line.split(","))
					if (single!=null)
						features.add(single);
				sameFeature.put( features.get(0), features);
			}
			br.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		return sameFeature;
	}
}
