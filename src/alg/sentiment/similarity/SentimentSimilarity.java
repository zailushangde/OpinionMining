/**
 * this class calculate the similarity based on the sentiment words 
 * between the cases
 */
package alg.sentiment.similarity;

import java.util.Set;

import util.FeatureFunctions;
import alg.cases.Case;
import alg.cases.Product;

public class SentimentSimilarity {
	/**
	 * evaluate which product has better sentiment on the given feature
	 * @param featureName feature name
	 * @param c1 the first case
	 * @param c2 the second case
	 * @return positive indicates that candidate enjoys better sentiment 
	 * 		   than query for the given feature.
	 */
	public static Double better(String featureName, Case c1, Case c2){
		Product query = (Product) c1;
		Product candidate = (Product) c2;
		double sent_q = query.getSentEval().get(featureName);
		double sent_c = candidate.getSentEval().get(featureName);
		
		return (sent_c - sent_q) / 2.0;
	}
	
	/**
	 * compare overall sentiment on shared features between two cases
	 * @param c1 the first case
	 * @param c2 the second case
	 * @return sentiment
	 */
	public static Double B1(Case c1, Case c2){
		Product q = (Product) c1;
		Product c = (Product) c2;
		Set<String> commonFeatures = FeatureFunctions.getCommonFeatures(new Product[]{q, c});
		
		if(commonFeatures.size()==0){
			System.out.println("=============No common feature!============");
			return null;
		}
		
		double sum = 0;
		
		for(String f : commonFeatures){
			sum += better(f, q, c);
		}
		
		return sum / (commonFeatures.size()*1.0);
	}
	
	/**
	 * compare overall sentiment including residual features
	 * @param c1 the first case
	 * @param c2 the second case
	 * @return sentiment
	 */
	public static Double B2(Case c1, Case c2){
		Product q = (Product) c1;
		Product c = (Product) c2;
		// get the union of features of the two products
		Set<String> union = FeatureFunctions.getUnionFeatures(new Product[] {q, c});
		
		if(union.size()==0){
			System.out.println("=============No common feature!============");
			return null;
		}
		
		double sum = 0;
		
		for(String name : union){
			// use better metric for common features
			if(q.getFeatures().containsKey(name) &&
			   c.getFeatures().containsKey(name)){
				
				sum += better(name, q, c);
			// assume neutral for missing features	
			}else if(!c.getFeatures().containsKey(name)){
				// if candidate misses the feature
				sum += -q.getSentEval().get(name) / 2; 
			}else{
				// if query misses the feature
				sum += c.getSentEval().get(name) / 2;
			}
		}
		
		return sum / (union.size()+0.0);
	}
}
