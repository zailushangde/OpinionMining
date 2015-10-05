/**
 * An interface to compute the similarity between cases
 */

package alg.cases.similarity;

import alg.cases.Case;

public interface CaseSimilarity {

	/**
	 * computes the similarity between two cases
	 * @param c1 - the first case
	 * @param c2 - the second case
	 * @return the similarity between case c1 and case c2
	 */
	public Double getSimilarity(final Case case1, final Case case2);
}
