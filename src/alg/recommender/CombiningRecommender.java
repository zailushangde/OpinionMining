package alg.recommender;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import util.ScoredThingDsc;
import alg.cases.Product;
import alg.cases.similarity.CaseSimilarity;
import alg.summary.Summary;

public class CombiningRecommender extends Recommender{

	/**
	 * constructor - creates a new CombiningRecommender object
	 * @param caseSimilarity - an object to compute case similarity
	 * @param summary - an object to store the products metadata
	 */
	public CombiningRecommender(CaseSimilarity casesimilarity, Summary summary) {
		super(casesimilarity, summary);
	}

	/**
	 * returns a ranked list of recommended case ids
	 * @param productId - the id of the target product
	 * @param summary - an object to store the products metadata
	 * @return the ranked list of recommended case ids
	 */
	public List<Product> getRecommendations(String productId, int k) {
		Product query = (Product) super.casebase.getProduct(productId);
		SortedSet<ScoredThingDsc> ss = new TreeSet<ScoredThingDsc>(); 
		for (String candidateId: super.casebase.getProductIdSet())
		{
			if(!candidateId.equalsIgnoreCase(productId))
			{
				Product candidate = (Product) super.casebase.getProduct(candidateId);
				Double score = super.casesimilarity.getSimilarity(query, candidate);
				if (score != null)
					ss.add(new ScoredThingDsc(score, candidate)); // add the score for the current recommendation candidate case to the set
			}
		}
		
		// sort the candidate recommendation cases by score (in descending order) and return as recommendations 
		ArrayList<Product> recommendations = new ArrayList<Product>();
		int topN = 0;
		for(Iterator<ScoredThingDsc> it = ss.iterator(); it.hasNext(); )
		{
			ScoredThingDsc st = it.next();
			recommendations.add((Product)st.thing);
			topN ++;
			if (topN == k)
				break;
		}
				
		return recommendations;
	}

}
