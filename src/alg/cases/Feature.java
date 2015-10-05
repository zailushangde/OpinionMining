/**
 * a feature object 
 */
package alg.cases;

public class Feature {
	private int pos; // to store the numbers of positive reviews
	private int neg; // to store the numbers of negative reviews
	private int neu; // to store the numbers of neutral reviews
	private String pos_sen; // store the example of positive sentence of a feature
	private String neg_sen; // store the example of negative sentence of a feature
	private String neu_sen; // store the example of neutral sentence of a feature
	
	/**
	 * constructor - create a Feature Object
	 * @param pos - the number of positive reviews
	 * @param neg - the number of negative reviews
	 * @param neu - the number of neutral reviews
	 * @param pos_sen - the example of positive sentence of a feature
	 * @param neg_sen - the example of negative sentence of a feature
	 * @param neu_sen - the example of neutral sentence of a feature
	 */
	public Feature(int pos, int neg, int neu, String pos_sen, String neg_sen,
			String neu_sen) {
		super();
		this.pos = pos;
		this.neg = neg;
		this.neu = neu;
		this.pos_sen = pos_sen;
		this.neg_sen = neg_sen;
		this.neu_sen = neu_sen;
	}
	
	/**
	 * constructor - create a Feature Object
	 */
	public Feature() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the example of positive sentence of a feature
	 */
	public String getPos_sen() {
		return pos_sen;
	}

	/**
	 * @param pos_sen the example of positive sentence of a feature
	 */
	public void setPos_sen(String pos_sen) {
		this.pos_sen = pos_sen;
	}

	/**
	 * @return the example of negative sentence of a feature
	 */
	public String getNeg_sen() {
		return neg_sen;
	}

	/**
	 * @param neg_sen the example of negative sentence of a feature
	 */
	public void setNeg_sen(String neg_sen) {
		this.neg_sen = neg_sen;
	}

	/**
	 * @return the example of neutral sentence of a feature
	 */
	public String getNeu_sen() {
		return neu_sen;
	}

	/**
	 * @param neu_sen the example of neutral of a feature
	 */
	public void setNeu_sen(String neu_sen) {
		this.neu_sen = neu_sen;
	}

	/**
	 * @return the number of positive reviews
	 */
	public int getPos() {
		return pos;
	}

	/**
	 * plus the number of positive reviews
	 */
	public void AddPos() {
		pos ++;
	}
	
	/**
	 * @return the number of negative reviews
	 */
	public int getNeg() {
		return neg;
	}

	/**
	 * plus the number of negative reviews
	 */
	public void AddNeg() {
		neg ++;
	}

	/**
	 * @return the number of neutral reviews
	 */
	public int getNeu() {
		return neu;
	}

	/**
	 * plus the number of neutral reviews
	 */
	public void AddNeu() {
		neu ++;
	}
	
	/**
	 * @return a example sentence
	 */
	public String getExampleSentence()
	{
		if (this.pos_sen != null)
			return "Postive: " + this.pos_sen;
		else if (this.neg_sen != null)
			return "Negative: " + this.neg_sen;
		else
			return "Neutral: " + this.neu_sen;
	}
	/**
	 * @return the popularity for the feature
	 */
	public double getPop() {
		return this.neg + this.neu + this.pos;
	}
	
	public String toString() {
		return "[pos=" + pos + ", neg=" + neg + ", neu=" + neu
				;
	}
}
