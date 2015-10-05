package examples;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import util.Review;
import util.nlp.Parser;
import util.reader.DatasetReader;
import util.reader.FileReader;

public class test {
	public final static String EOL = System.getProperty("line.separator");
	
	public static void main(String[] args)
	{
		String FeatureFile = "feature sets" + File.separator + "Printer Features.txt";
		String PosWordFile = "sentiment lexicon" + File.separator + "positive-words.txt";
		String NegWordFile = "sentiment lexicon" + File.separator + "negative-words.txt";
		String cameraSameF = "same features" + File.separator + "printer same feature.csv";
		String category = "printer";
		int id = 2569;
		FileReader file = new FileReader(FeatureFile, PosWordFile, NegWordFile, cameraSameF);
		Set<String> f = file.getFeaturewords();
		String filename = "Printer.txt"; // set the dataset filename
		DatasetReader reader = new DatasetReader(filename); // create an instance of the DatasetReader class
		ArrayList<Review> reviews = reader.getReviews(); // get all reviews and store in an ArrayList
		
//		System.out.println("\nthe 3rd review:\n" + reviews.get(id).toString()); 	// print the 3rd review
//		// - note the <br /> tag in the review text...
//		// - treat two (or more) consecutive <br /> tags as a new paragraph
//		reader.getReviews().get(2).getPosVotes();
//		// tip - how to replace all <br /> tags with line separators: 
//		System.out.println("\nreview text with \"<br />\" tags:\n" + reviews.get(id).getReviewText());		
		System.out.println("\nreview text with \"<br />\" tags replaced by line separators:\n" + reviews.get(id).getReviewText().replaceAll("<br />", EOL));
		String str = reviews.get(id).getReviewText().replaceAll("<br />", EOL);
		Parser parser = new Parser(); // create an instance of the Parser class
		String[] sentences = parser.getSentences(str); // get the sentences
		System.out.println("Token\t\tChunk Tag\tPOS Tag");
		for(String sentence: sentences) // iterate over each sentence
		{
			String[] tokens = parser.getSentenceTokens(sentence.toLowerCase()); // get the sentence tokens (words)
			String pos[] = parser.getPOSTags(tokens); // get the POS tag for each sentence token
			String chunks[] = parser.getChunkTags(tokens, pos); // get the chunk tags for the sentence
//			for (int j = 0; j < tokens.length; j ++)
//				if (f.contains(tokens[j]))
//					System.out.println(tokens[j]);
			
			for(int i = 0; i < tokens.length; i++) // print the sentence tokens and corresponding chunk and POS tags
				System.out.println(tokens[i] + "\t\t" + chunks[i] + "\t\t" + pos[i]);
			System.out.println("\n+++++\n");			
		}
	}
}
