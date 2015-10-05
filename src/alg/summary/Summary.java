/**
 * this class to summary the information of the products
 */
package alg.summary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Pattern;
import alg.casebase.CameraBase;
import alg.casebase.CaseBase;
import alg.casebase.PrinterBase;
import alg.cases.Feature;
import alg.cases.Product;
import alg.sentiment.SentimentMining;

public class Summary {
	private CaseBase casebase; // to store the CaseBase object
	private Map<String, List<Pattern>> patterns; // store all of the patterns from reviews
	private Map<String, ArrayList<String>> sameFeatures; // store the same meaning features
	
	/**
	 * Constructor - create the summary object
	 * @param pattern - the object about the ReadInPattern
	 * @param category - the product category to summary
	 */
	public Summary(final SentimentMining pattern, final String category) {
		super();
System.out.println("Start to summary...");		
		this.patterns = pattern.getAllPattern();
		this.sameFeatures = pattern.getSameFeature();
		if (category.equalsIgnoreCase("printer"))
			this.casebase = new PrinterBase();
		else
			this.casebase = new CameraBase();
		summarize(category);
	}

	/**
	 * summarize all of the products in the category
	 * @param category the category to be summarized
	 */
	private void summarize(final String category)
	{
		// if this database does not contain the this category, the code will output the error and will be stoped
		if (!category.equalsIgnoreCase("printer") && !category.equalsIgnoreCase("digital camera"))
		{
			System.out.println("Wrong category: " + category);
			System.exit(1);
		}

		for (String productId: patterns.keySet())
		{
			Product product = null;
			Map<String, Feature> features = new HashMap<String, Feature>();
			for (Pattern pattern: patterns.get(productId))
			{
				String featureName = pattern.getFeature();
				String sentence = pattern.getSentence();
				
				featureName = dealSameFeatures(featureName); // deal with the same meaning features
						
				if (!features.containsKey(featureName))
				{
					features.put(featureName, new Feature(0, 0, 0, null, null, null));
					updateFeature(features, pattern, featureName, sentence);
				}
				else
					updateFeature(features, pattern, featureName, sentence);
			}
			product = new Product(category, productId, null, features);
			casebase.add(product);
		}
	}

	private void updateFeature(Map<String, Feature> features, Pattern pattern,
			String featureName, String sentence) {
		if (pattern.getPosORneg().equals("neutral"))
		{
			features.get(featureName).AddNeu();
			if (features.get(featureName).getNeu_sen() == null)
				features.get(featureName).setNeu_sen(sentence);
		}
		else if (pattern.getTimes() == 1)
		{
			features.get(featureName).AddNeu();
			if (features.get(featureName).getNeu_sen() == null)
				features.get(featureName).setNeu_sen(sentence);
		}
			
		else if (pattern.getPosORneg().equals("positive"))
		{
			features.get(featureName).AddPos();
			if (features.get(featureName).getPos_sen() == null)
				features.get(featureName).setPos_sen(sentence);
		}
			
		else
		{
			features.get(featureName).AddNeg();
			if (features.get(featureName).getNeg_sen() == null)
				features.get(featureName).setNeg_sen(sentence);
		}
	}

	/**
	 * @param featureName the name of the feature to be dealed with 
	 * @return if there are other same meaning features, it will return a same feature name
	 */
	private String dealSameFeatures(String featureName) {
		for (String fea: sameFeatures.keySet())
			if (sameFeatures.get(fea).contains(featureName))
			{
				featureName = fea;
				break;
			}
		return featureName;
	}
	
	/**
	 * @return the object of CaseBase
	 */
	public CaseBase getCaseBase()
	{
		return casebase;
	}
	
}
