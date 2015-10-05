/**
 * a class to explain the reasons why the system recommends those products
 */
package util.explanation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.FeatureFunctions;
import alg.casebase.CaseBase;
import alg.cases.Product;
import alg.summary.Summary;

public class Explanation {

	private CaseBase casebase; // store the object of the casebase
	
	/**
	 * constructor - create a new explanation object
	 * @param summary - all of the products' information is stored in this object
	 */
	public Explanation(Summary summary) {
		super();
		casebase = summary.getCaseBase();
	}
	
	/**
	 * @param recommendations the recommendations for a given product
	 * @param queryId the id of the given product
	 * @return the explanation for the recommendations
	 */
	public Map<String, String[]> getExplanations(List<Product> recommendations, String queryId)
	{
		Product query = (Product) casebase.getProduct(queryId);
		
		Map<String, String[]> result = new HashMap<String, String[]>();
		Set<String> common;
		for (Product candidate: recommendations)
		{	
			StringBuilder better = new StringBuilder();
			StringBuilder worse = new StringBuilder();
			common = FeatureFunctions.getCommonFeatures(new Product[] {candidate, query});
//			System.out.println(candidate.getProductId() + " " + common);
			for (String feature: common)
			{
				Double score = (query.getSentEval().get(feature) - candidate.getSentEval().get(feature)) / query.getSentEval().get(feature);
				if (score > 0.5)
					better.append(feature + ", ");
				else if (score < -0.3)
					worse.append(feature + ", ");
			}
			result.put(candidate.getProductId(), new String[] {better.toString(), worse.toString()});
		}
		return result;
	}
}
