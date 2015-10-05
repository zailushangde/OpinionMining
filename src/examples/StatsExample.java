/**
 * Example showing how to compute the median and interquartile range of an array of values
 * 
 * Michael O'Mahony
 * 02/04/2014
 */

package examples;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;

public class StatsExample {
	public static void main(String[] args)
	{
		double[] values = {102, 104, 105, 107, 108, 109, 110, 112, 115, 116, 118};
//		double[] values = {3, 1, 2, 5, 4};
		
		Percentile percentile = new Percentile();
		double median = percentile.evaluate(values, 50);
		double IQR = percentile.evaluate(values, 75) - percentile.evaluate(values, 25);
		System.out.println("median: " + median);
		System.out.println("IQR: " + IQR);
		for (int p = 25; p <= 75; p+=25)
			System.out.println(p + "th percentile:  " + percentile.evaluate(values, p));
	}
}
