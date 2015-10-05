/**
 * A class to compute the case similarity between objects of type Product
 * Uses the cosine similarity metric
 */
package alg.cases.similarity;

import java.util.Set;

import util.FeatureFunctions;
import alg.cases.Case;
import alg.cases.Product;

public class CosineSimilarity implements CaseSimilarity{

	/**
	 * constructor - creates a new CosineSimilarity object
	 */
	public CosineSimilarity() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * computes the similarity between two cases
	 * @param c1 - the first case
	 * @param c2 - the second case
	 * @return the similarity between case c1 and case c2
	 */
	public Double getSimilarity(final Case c1, final Case c2)
	{
		Product p1 = (Product) c1;
		Product p2 = (Product) c2;
		if (!p1.getCategory().equalsIgnoreCase(p2.getCategory()))
		{
			System.out.println("Error: they are not the same category");
			System.exit(1);
		}
		return cosine(p1, p2);
	}

	/**
	 * computes the similarity between two products
	 * @param p1 - the first product
	 * @param p2 - the second product
	 * @return - the similarity based on cosine similarity
	 */
	public static Double cosine(Product p1, Product p2){
		
		// get the union feature between p1 and p2
		Set<String> unionFeatures = FeatureFunctions.getUnionFeatures(new Product[]{p1, p2});
		
		if(unionFeatures.size()==0){
			System.out.println("=============No common feature!============");
			return null;
		}
		
		double above = 0;
		double below_p1 = 0;
		double below_p2 = 0;
		
		for(String feature : unionFeatures){
			if (p1.containsFeature(feature) && p2.containsFeature(feature))
			{
				above += p1.getFeature(feature).getPop() * p2.getFeature(feature).getPop();
				below_p1 += Math.pow(p1.getFeature(feature).getPop(), 2);
				below_p2 += Math.pow(p2.getFeature(feature).getPop(), 2);
			}
			else if (p1.containsFeature(feature) && !p2.containsFeature(feature))
				below_p1 += Math.pow(p1.getFeature(feature).getPop(), 2);
			else if (!p1.containsFeature(feature) && p2.containsFeature(feature))
				below_p2 += Math.pow(p2.getFeature(feature).getPop(), 2);
		}
		// compute the cosine simiarity 
		return above / (Math.sqrt(below_p1) * Math.sqrt(below_p2));
	}
}
