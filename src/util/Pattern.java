/**
 * a class to define an object about the pattern
 */
package util;

import java.util.List;

public class Pattern 
{
	private List<String> pattern; // store the patterns
	private String feature; // the name of the feature
	private int times; // store the times about the pattern appears
	private String posORneg; // store the pattern is positive or negative
	private String sentence; // store the example sentence 
	
	/**
	 * constructor - create a new object of the pattern
	 * @param feature - the name of the feature
	 * @param times - the times of the pattern which has appeared
	 * @param posORneg - the pattern is positive or negative
	 * @param sentence - the example sentence 
	 */ 
	public Pattern(String feature, int times, String posORneg, String sentence) {
		super();
		this.feature = feature;
		this.times = times;
		this.posORneg = posORneg;
		this.pattern = null;
		this.sentence = sentence;
	}

	/**
	 * @return the pattern
	 */
	public List<String> getPattern() {
		return pattern;
	}

	/**
	 * constructor - create a new pattern object
	 * @param pattern - the type of the pattern
	 * @param feature - the name of the feature
	 * @param times - the times of the appeared pattern
	 * @param posORneg - the pattern is positive or negative
	 * @param sentence - the example sentence
	 */
	public Pattern(List<String> pattern, String feature, int times,
			String posORneg, String sentence) {
		super();
		this.pattern = pattern;
		this.feature = feature;
		this.times = times;
		this.posORneg = posORneg;
		this.sentence = sentence;
	}

	/**
	 * @return the example sentence
	 */
	public String getSentence() {
		return sentence;
	}

	/**
	 * @param sentence the sentence to replace the old one 
	 */
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	/**
	 * @return the name of the feature
	 */
	public String getFeature() {
		return feature;
	}

	/**
	 * @param productId the new name of the feature
	 */
	public void setFeature(String productId) {
		feature = productId;
	}

	/**
	 * @return the times of the appeared pattern
	 */
	public int getTimes() {
		return times;
	}

	/**
	 * @param times the appear times of the pattern
	 */
	public void setTimes(int times) {
		this.times = times;
	}
	
	/**
	 * @return the pattern is positive or negative
	 */
	public String getPosORneg() {
		return posORneg;
	}

	public String toString() {
		return "Pattern [ProductId=" + feature + ", times=" + times
				+ ", posORneg=" + posORneg + "]";
	}
	
}
