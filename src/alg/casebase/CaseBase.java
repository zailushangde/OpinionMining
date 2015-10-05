/**
 * This class stores case objects
 * 
 */
package alg.casebase;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import alg.cases.Case;
import alg.cases.Product;

public class CaseBase {
	protected Map<String, Case> casebase; // store the object of casebase

	/**
	 * constructor create a new casebase object
	 */
	public CaseBase() {
		super();
		casebase = new HashMap<String, Case>();
		// TODO Auto-generated constructor stub
	}

	/**
	 * judge whether the object contains a product or not
	 * @param productId - the ID of the product
	 * @return - true or false depends on whether the object contains the product
	 */
	public boolean contains(String productId)
	{
		if (this.casebase.containsKey(productId))
			return true;
		else
			return false;
	}

	/**
	 * @param product a object of product
	 */
	public void add(Case product)
	{
		if(!this.casebase.containsKey(product.getProductId()))
			this.casebase.put(product.getProductId(), product);
	}
	
	/**
	 * @param product a object of product
	 */
	public void delete(Case product)
	{
		if(this.casebase.containsKey(product.getProductId()))
			this.casebase.remove(product.getProductId());
	}
	
	/**
	 * @param productId the ID of a certain product
	 */
	public void delete(String productId)
	{
		if(this.casebase.containsKey(productId))
			this.casebase.remove(productId);
	}
	
	/**
	 * @param productId - the ID of the product
	 * @return - null or the wanted product
	 */
	public Case getProduct(String productId)
	{
		if(this.casebase.containsKey(productId))
			return this.casebase.get(productId);
		else
			return null;
	}
	
	/**
	 * @return all the product ID into a set
	 */
	public Set<String> getProductIdSet()
	{
		return casebase.keySet();
	}
	
}
