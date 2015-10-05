/**
 * a product object
 */
package alg.cases;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Product implements Case{
	private String category; // store the name of the category
	private String productId; // store the ID of the product
	private String productName; // store the name of the product
	private Map<String, Feature> feature; // store the information about features

	private Map<String, Double> sent; // store the times of the sentiment word for each feature
	
	/**
	 * constructor - create a Product Object
	 * @param category - the category of the product belongs to 
	 * @param productId - the ID of the product
	 * @param productName - the name of the product
	 * @param feature - the information of the feature
	 */
	public Product(String category, String productId, String productName,
			Map<String, Feature> feature) {
		super();
		this.category = category;
		this.productId = productId;
		this.productName = productName;
		this.feature = feature;
	}

	/**
	 * constructor - create a object of product
	 */
	public Product() {
		super();
	}

	/**
	 * compute the sentiment value of every feature in this product
	 */
	private void evalSent()
	{
		this.sent = new HashMap<String, Double>();
		double pos, neg, neu;
		double result;
		for (String featureName: this.feature.keySet())
		{
			pos = this.feature.get(featureName).getPos();
			neg = this.feature.get(featureName).getNeg();
			neu = this.feature.get(featureName).getNeu();
			if(pos!=0 || neg!=0 || neu!=0)
			{
				result = (pos - neg) / (pos + neg + neu);
				this.sent.put(featureName, result);
			}
		}
	}
	
	/**
	 * @return the whole features' sentiment value
	 */
	public Map<String, Double> getSentEval()
	{
		if (this.sent == null)
			this.evalSent();
		return sent;
	}
	
	/**
	 * @return the category that this product belongs to 
	 */
	public String getCategory() {
		return category;
	}
	
	/**
	 * @param category set the category that this product belongs to 
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the product ID
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId set the product ID
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the product name of the product
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName set the product name
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	/**
	 * @return get the information of features
	 */
	public Map<String, Feature> getFeatures()
	{
		return feature;
	}

	/**
	 * @param name - the name of the feature which will be gotten
	 * @return the certain feature information
	 */
	public Feature getFeature(String name) {
		return feature.get(name);
	}

	/**
	 * @return the all feature for this product
	 */
	public Set<String> getAllFeatures() {
		return feature.keySet();
	}
	
	/**
	 * @return the number of the features that the product has
	 */
	public int getFeatureNum() {
		return feature.size();
	}
	
	/**
	 * @param featureName the name of a feature 
	 * @return if the product contains this feature, it will return TRUE
	 */
	public boolean containsFeature(String featureName)
	{
		return this.feature.containsKey(featureName)? true: false;
	}

	@Override
	public String toString() {
		return "Product [category=" + category + ", productId=" + productId
				+ ", productName=" + productName + ", feature=" + feature + "]";
	}

}
