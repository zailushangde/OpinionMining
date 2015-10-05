/**
 * a class to provide the useful methods about the features among the products
 */
package util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import alg.cases.Product;

public class FeatureFunctions {

	/**
	 * compute the same features among some products
	 * @param products the product objects to be computed
	 * @return the same features among the given products
	 */
	public static Set<String> getCommonFeatures(Product[] products){
		Set<String> common = new HashSet<String>();
		Map<String, Integer> total = new HashMap<String, Integer>();
		for (int i = 0; i < products.length; i ++)
			for (String featureName: products[i].getAllFeatures())
			{
				if (!total.containsKey(featureName))
					total.put(featureName, 1);
				else if (total.containsKey(featureName))
					total.put(featureName, total.get(featureName) + 1);
			}
		for (String st: total.keySet())
			if (total.get(st) >= products.length)
				common.add(st);
		return common;		
	}
	
	/**
	 * compute the union features for the given products
	 * @param products the given products
	 * @return the union features
	 */
	public static Set<String> getUnionFeatures(Product[] products){
		Set<String> union = new HashSet<String>();
		for (int i = 0; i < products.length; i ++)
			for (String featureName: products[i].getAllFeatures())
				union.add(featureName);
		return union;
	}
}
