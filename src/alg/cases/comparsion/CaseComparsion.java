/**
 * a class to compare the cases
 */
package alg.cases.comparsion;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import util.FeatureFunctions;
import alg.casebase.CaseBase;
import alg.cases.Product;
import alg.summary.Summary;

public class CaseComparsion extends Comparsion{

	private static final int MIN = 2; // the minimum number of cases to compare
	private static final int MAX = 5; // the maximum number of cases to compare
	
	private Product[] products; // store the products to be compared
	private CaseBase casebase; // store the object of the casebase
	private Set<String> common; // store the common features of the given products
	
	/**
	 * constructor - to ceate a new casecomparsion object
	 * @param category - the category of the product 
	 * @param ids - the product ids
	 * @param summary - the object of the summary, which stores the information of the summary
	 */
	public CaseComparsion(String category, String[] ids, Summary summary) {
		super();
		casebase = summary.getCaseBase();
		if (ids.length < MIN || ids.length > MAX)
			System.out.println("Error: the number of products to be campared is invalid.");
		this.products = new Product[ids.length];
		for (int i = 0; i < products.length; i ++)
			products[i] = (Product) casebase.getProduct(ids[i]);
		common = FeatureFunctions.getCommonFeatures(products);
	}
	
	/**
	 * the usual method to compare the given products based on the common features
	 */
	public Map<String, Map<String, Double>> commonComp()
	{		
		Map<String, Map<String, Double>> out = new HashMap<String, Map<String,Double>>();
		for (Product product: products)
		{
			Map<String, Double> score = new HashMap<String, Double>();
			for (String featureName: common)
				score.put(featureName, product.getSentEval().get(featureName));
			out.put(product.getProductId(), score);
		}
		return out;
//		System.out.print("\t\t");
//		for (String st: common)
//			System.out.print(st + "\t\t");
//		System.out.println();
//		for (String id: out.keySet())
//		{
//			System.out.print(id + "\t\t");
//			for (String feature: out.get(id).keySet())
//			{
//				System.out.print(out.get(id).get(feature) + "\t\t");
//			}
//			System.out.println();
//		}
			
//		System.out.println("ProductId" + "\t\t" + "Feature");	
	}
}
