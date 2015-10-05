/**
 * An abstract class to define a case-based recommender
 */
package alg.recommender;

import java.util.List;

import alg.casebase.CaseBase;
import alg.cases.Product;
import alg.cases.similarity.CaseSimilarity;
import alg.summary.Summary;

public abstract class Recommender {

	protected CaseBase casebase; // store the casebase object
	protected CaseSimilarity casesimilarity; // the object of the casesimilarity
	/**
	 * constructor - creates a new Recommender object
	 * @param caseSimilarity - an object to compute case similarity
	 * @param summary - an object to store information about the product
	 */
	public Recommender(CaseSimilarity casesimilarity, Summary summary) {
		super();
		casebase = summary.getCaseBase();
		this.casesimilarity = casesimilarity;
	}
	
	/**
	 * returns a ranked list of recommended case ids
	 * @param productID - the ids of the products
	 * @param k - the top-k product to recommender
	 * @return the topN recommender products
	 */
	public abstract List<Product> getRecommendations(String productID, int k);
	
}
