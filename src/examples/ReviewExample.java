/**
 * An example class to read in a review dataset
 * 
 * Michael O'Mahony
 * 20/02/2013
 */

package examples;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import util.Review;
import util.reader.DatasetReader;

public class ReviewExample 
{
	public final static String EOL = System.getProperty("line.separator");
	
	public static void main(String[] args)
	{
//		String filename = "Digital Camera.txt"; // set the dataset filename
		String filename = "Printer.txt"; // set the dataset filename
		DatasetReader reader = new DatasetReader(filename); // create an instance of the DatasetReader class
		ArrayList<Review> reviews = reader.getReviews(); // get all reviews and store in an ArrayList
		for (int i = 0; i < reviews.size(); i ++)
			if (reviews.get(i).getProductId().equalsIgnoreCase("B0073N1J48"))
			{
				System.out.println(i);
//		System.out.println("total # reviews: " + reviews.size()); // print the number of reviews in the ArrayList
//		System.out.println("total # products: " + reader.getProductIds().size());
		
		System.out.println("\nthe 3rd review:\n" + reviews.get(i).toString()); 	// print the 3rd review
																				// - note the <br /> tag in the review text...
																				// - treat two (or more) consecutive <br /> tags as a new paragraph
		reader.getReviews().get(2).getPosVotes();
		// tip - how to replace all <br /> tags with line separators: 
		System.out.println("\nreview text with \"<br />\" tags:\n" + reviews.get(i).getReviewText());		
		System.out.println("\nreview text with \"<br />\" tags replaced by line separators:\n" + reviews.get(i).getReviewText().replaceAll("<br />", EOL).toLowerCase());
		
		}
	}
}
