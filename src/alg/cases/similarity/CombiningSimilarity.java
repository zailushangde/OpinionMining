/**
 * A class to compute the case similarity between objects of type Product
 * Uses the cosine similarity + sentiment metric
 */
package alg.cases.similarity;

import alg.cases.Case;
import alg.sentiment.similarity.SentimentSimilarity;

public class CombiningSimilarity implements CaseSimilarity{

	private double w; // the value to assign the weight about similarity and sentiment
	private CosineSimilarity cossim; // the object of the cosin similarity
	
	/**
	 * constructor - create a new combiningSimilarity object
	 * @param w - the value to assign the weight about similarity and sentiment
	 */
	public CombiningSimilarity(double w) {
		super();
		this.w = w;
		cossim = new CosineSimilarity();
	}

	/**
	 * computes the similarity between two cases
	 * @param c1 - the first case
	 * @param c2 - the second case
	 * @return the similarity between case c1 and case c2
	 */
	public Double getSimilarity(Case c1, Case c2) {
		
		Double cosineSimilarity = cossim.getSimilarity(c1, c2);
		Double sentimentSimilarity = SentimentSimilarity.B2(c1, c2);
		if (cosineSimilarity != null && sentimentSimilarity != null)
		{
			double value = (1 - w) * cosineSimilarity * 1.0 + w * (sentimentSimilarity + 1) / 2.0; 
//			System.out.println("(" + c1.getProductId() + "  " + c2.getProductId() + ")" + " value " + value +  " Cosine Similarity is " + cosineSimilarity + ", and Sentiment Similarity is " + sentimentSimilarity);
			return value;
		}
			
		else
			return null;
	}

}
