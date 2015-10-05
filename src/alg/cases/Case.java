/**
 * An interface to define common behaviour for case objects
 * 
 */
package alg.cases;

import java.util.Map;

public interface Case {

	
	public String getProductId();  // return the product Id

	public Map<String, Feature> getFeatures(); // return the all features for a product
}
