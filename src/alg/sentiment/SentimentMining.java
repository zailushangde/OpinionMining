/**
 * a class to do the process of sentiment mining
 */
package alg.sentiment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.Pattern;
import util.Review;
import util.nlp.Parser;
import util.reader.DatasetReader;
import util.reader.FileReader;

public class SentimentMining {
	private final static String EOL = System.getProperty("line.separator");
	private final static String FEATURE = "FEATURE";
	private final static String POSITIVE = "positive";
	private final static String NEGATIVE = "negative";
	
	private Map<String, List<Pattern>> allPattern; // store the all of the pattern according different features
	private Map<List<String>, Boolean> pattern_times; // store the times (once or more than once) of a pattern appeared to define the valid pattern
	private Set<String> featurewords; // store the features of a category
	private Set<String> posWords; // store the positive sentiment words
	private Set<String> negWords; // store the negative sentiment words
	private Map<String, ArrayList<String>> sameFeature; // store the same meaning features
	private ArrayList<Review> reviews;
	/**
	 * constructor - create a ReadInPattern object
	 * @param fileReader - the object of the FileReader
	 * @param productFile - the path and filename of the file containing product category profiles
	 */
	public SentimentMining(final FileReader fileReader, final String productFile, final DatasetReader reader)
	{
System.out.print("Sentiment mining");
		featurewords = fileReader.getFeaturewords();
		posWords = fileReader.getPosWords();
		negWords = fileReader.getNegWords();
		sameFeature = fileReader.getCameraSameF();
		reviews = reader.getReviews(); // get all reviews and store in an ArrayList
		readSentences(productFile);
	}

	/**
	 * @return all of the patterns according the different features
	 */
	public Map<String, List<Pattern>> getAllPattern() {
		return allPattern;
	}
	
	/**
	 * @return the same meaning features for a product category
	 */
	public Map<String, ArrayList<String>> getSameFeature() {
		return sameFeature;
	}

	/**
	 * @param filename the path and filename of the file containing product category profiles
	 */
	private void readSentences(final String filename)
	{
		// initialize the variables
		allPattern = new HashMap<String, List<Pattern>>();
		pattern_times = new HashMap<List<String>, Boolean>();
		// read in and deal with the sentence from reviews
		readInSentence(filename);
		
		for (String single_prodeuct: allPattern.keySet())
		for (Pattern single_pattern: allPattern.get(single_prodeuct))
			if (single_pattern.getPattern() != null && pattern_times.containsKey(single_pattern.getPattern()) && pattern_times.get(single_pattern.getPattern()) == true)
				single_pattern.setTimes(2);
	}
	
	/**
	 * deal with the sentence from the reviews and generate the patterns
	 * @param filename the path and filename of the file containing product category profiles
	 */
	private void readInSentence(final String filename)
	{
		Parser parser = new Parser(); // create an instance of the Parser class
		// loop all of the reviews and handle the sentence one by one
		for (int out = 0; out < reviews.size(); out ++)
		{
if ((out + 1)%500 == 0)  System.out.print(".");
			String singleReview = reviews.get(out).getReviewText().replaceAll("<br />", EOL);
			String[] sentences = parser.getSentences(singleReview); // get the sentences
			String productId = reviews.get(out).getProductId();
			convertToPattern(parser, sentences, productId);
		}
System.out.println();
	}
	
	/**
	 * convert the sentence into pattern
	 * @param parser - an instance of the Parser class
	 * @param sentences - the sentences divided from a review
	 * @param productId - the id of a product
	 */
	private void convertToPattern(Parser parser, String[] sentences,
			String productId) {
		for (String sentence: sentences)
		{
			String[] tokens = parser.getSentenceTokens(sentence.toLowerCase()); // get the sentence tokens (words)
			String pos[] = parser.getPOSTags(tokens); // get the POS tag for each sentence token
			for(int i = 0; i < tokens.length; i++) // print the sentence tokens and corresponding chunk and POS tags
			{
				// situation 1: the pos tag of the feature is adj + nn and nn + nn
				// this can get the feature
				if (((i+1) < tokens.length) && ((pos[i].startsWith("JJ") && featurewords.contains(tokens[i] + " " + tokens[i+1]))
						|| (pos[i].startsWith("NN") && featurewords.contains(tokens[i] + " " + tokens[i+1]))))
				{
					// i - the position of the feature
					int times = 0;
					String feature = tokens[i] + " " + tokens[i+1];
					Map<Integer, Integer> position = new HashMap<Integer, Integer>();
					for(int j = 0; j < tokens.length; j++)
					{
						if ((j!=i && j!=(i+1)) && ((posWords.contains(tokens[j]) || negWords.contains(tokens[j]))))
						{
							// times - how many sentiment words in this sentence
							times ++;
							position.put(times, j);
						}
					}
					// have more than 2 sentiment words in a sentence
					if (position.size() > 1)
					{
						int min = 1000;
						int Wmin = 0; // Wmin is the minimal distance to the feature
						for (int inner = 1; inner <= position.size(); inner ++) 
						{
							if (Math.min(Math.abs(position.get(inner) - i), Math.abs(position.get(inner) - (i+1))) < min)
							{
								min = Math.min(Math.abs(position.get(inner) - i), Math.abs(position.get(inner) - (i+1)));
								Wmin = position.get(inner); // define the Wmin
							}
						}
						List<String> al = new ArrayList<String>(); // the words that occur between Wmin and the feature
						for(int k = Math.min(Wmin, i); k <= Math.max(Wmin, i+1); k++)
						{
							al.add(tokens[k] + " ");
						}
						// convert any words that occur between Wmin and the feature
						String[] tokens1 = parser.getSentenceTokens(al.toString().replace(",", ""));
						String pos1[] = parser.getPOSTags(tokens1);
						String chunks1[] = parser.getChunkTags(tokens1, pos1);
						List<String> pattern_list = new ArrayList<String>(); // the POS tags about the words
						for(int patt = 0; patt < pos1.length; patt ++)
						{
							if ((patt+1) < pos1.length && tokens1[patt].equalsIgnoreCase(tokens[i]) && tokens1[patt+1].equalsIgnoreCase(tokens[i+1]))
							{
								pattern_list.add(FEATURE);
								patt ++;
							}
							else if (chunks1[patt] != "O")
								pattern_list.add(pos1[patt]);
						}
						// update the patterns
						updatePattern(productId, tokens, pos, feature,
								Wmin, pattern_list, sentence);
					}
					// only one sentiment in this sentence
					else if (position.size() == 1)
					{
						int Wmin = position.get(1);
						List<String> al = new ArrayList<String>(); // the words that occur between Wmin and the feature
						for(int k = Math.min(Wmin, i); k <= Math.max(Wmin, i+1); k++)
						{
							al.add(tokens[k] + " ");
						}
						// convert any words that occur between Wmin and the feature
						String[] tokens1 = parser.getSentenceTokens(al.toString().replace("[", "").replace("]", "").replace(",", ""));
						String pos1[] = parser.getPOSTags(tokens1);
						String chunks1[] = parser.getChunkTags(tokens1, pos1);
						List<String> pattern_list = new ArrayList<String>(); // the POS tags about the words
						for(int patt = 0; patt < pos1.length; patt ++)
						{
							if ((patt+1) < pos1.length && tokens1[patt].equalsIgnoreCase(tokens[i]) && tokens1[patt+1].equalsIgnoreCase(tokens[i+1]))
							{
								pattern_list.add(FEATURE);
								patt ++;
							}
							else if (chunks1[patt] != "O")
								pattern_list.add(pos1[patt]);
						}
						// update the pattern
						updatePattern(productId, tokens, pos, feature,
								Wmin, pattern_list, sentence);
					}
					// do not contain the sentiment word is this sentence
					else if (position.size() == 0)
					{
						List<Pattern> pt_list = (allPattern.containsKey(productId) ? allPattern.get(productId) : new ArrayList<Pattern>());
						pt_list.add(new Pattern(feature, 0, "neutral", sentence));
						allPattern.put(productId, pt_list);
					}
					i ++; // skip the second word of the bi-gram features
				}
				// situation 2: the pos tag of the feature is nn
				else if (featurewords.contains(tokens[i]))
				{
//					if (productId.equalsIgnoreCase("B0073N1J48"))
//						System.out.println(tokens[i]);
					// i - the position of the feature
					int times = 0;
					String feature = tokens[i];
					Map<Integer, Integer> position = new HashMap<Integer, Integer>();
					for(int j = 0; j < tokens.length; j++)
					{
						if (j != i && (posWords.contains(tokens[j]) || negWords.contains(tokens[j])))
						{
							// times - how many sentiment words in this sentence
							times ++;
							position.put(times, j);
						}
					}
					// ** have more than 2 sentiment words in a sentence
					if (position.size() > 1)
					{
						int min = 1000;
						int Wmin = 0; // Wmin is the minimal distance to the feature
						for (int inner = 1; inner <= position.size(); inner ++) 
						{
							if (Math.abs(position.get(inner) - i) < min)
							{
								min = Math.abs(position.get(inner) - i);
								Wmin = position.get(inner);
							}
						}
						List<String> al = new ArrayList<String>(); // the words that occur between Wmin and the feature
						for(int k = Math.min(Wmin, i); k <= Math.max(Wmin, i); k++)
						{
							al.add(tokens[k] + " ");
						}
						// convert any words that occur between Wmin and the feature
						String[] tokens1 = parser.getSentenceTokens(al.toString().replace("[", "").replace("]", "").replace(",", ""));
						String pos1[] = parser.getPOSTags(tokens1);
						String chunks1[] = parser.getChunkTags(tokens1, pos1);
						List<String> pattern_list = new ArrayList<String>(); // the POS tags about the words
						for(int patt = 0; patt < pos1.length; patt ++)
						{
							if ((patt == 0 || patt == pos1.length-1) && tokens1[patt].equalsIgnoreCase(tokens[i]))
								pattern_list.add(FEATURE);
							else if (chunks1[patt] != "O")
								pattern_list.add(pos1[patt]);
						}
						// update the pattern
						updatePattern(productId, tokens, pos, feature,
								Wmin, pattern_list, sentence);
					}
					// ** only 1 sentiment word 
					else if (position.size() == 1)
					{
						int Wmin = position.get(1); // Wmin is the minimal distance to the feature
						List<String> al = new ArrayList<String>(); // the words that occur between Wmin and the feature
						for(int k = Math.min(Wmin, i); k <= Math.max(Wmin, i); k++)
						{
							al.add(tokens[k] + " ");
						}
						String[] tokens1 = parser.getSentenceTokens(al.toString().replace(",", ""));
						String pos1[] = parser.getPOSTags(tokens1);
						String chunks1[] = parser.getChunkTags(tokens1, pos1);
						List<String> pattern_list = new ArrayList<String>(); // the POS tags about the words
						for(int patt = 0; patt < pos1.length; patt ++)
						{
							if (tokens1[patt].equalsIgnoreCase(tokens[i]))
								pattern_list.add(FEATURE);
							else if (chunks1[patt] != "O")
								pattern_list.add(pos1[patt]);
						}
						// update the pattern
						updatePattern(productId, tokens, pos, feature,
								Wmin, pattern_list, sentence);
						
					}
					// ** have on sentiment word
//					else if (position.size() == 0)
					else
					{
						List<Pattern> pt_list = (allPattern.containsKey(productId) ? allPattern.get(productId) : new ArrayList<Pattern>());
						pt_list.add(new Pattern(feature, 0, "neutral", sentence));
						allPattern.put(productId, pt_list);
					}
				}
			}
		}
	}
	
	/**
	 * update the pattern
	 * @param productId - the ID of the handled product
	 * @param tokens - the single word about the review
	 * @param pos - the pos tags about the words that occur between Wmin and the feature
	 * @param feature - the name of the feature
	 * @param Wmin - the position of the Wmin
	 * @param pattern_list - the pos tags about the words that occur between Wmin and the feature
	 * @param sentence - a sentence to be handled in a review
	 */
	private void updatePattern(final String productId, final String[] tokens, final String[] pos,
			final String feature, final int Wmin, final List<String> pattern_list, final String sentence) {
		// pos or neg?
		String posORneg = POSITIVE; // the flag to tell the sentiment is this sentence is positive or not
		/**
		 * regardless the the pattern is valid or not, assign feature sentiment based on Wmin
		 * and subject to whether the sentence contains a negation term whinin a distance.
		 * The distance which I defined is 4
		 */
		for (int nega = Math.max(0, Wmin-4); nega < Math.min(Wmin+4, tokens.length-1); nega ++)
		{
			if (nega == Wmin)
				continue;
			// judge whether there is "only" followed by "not" ( which the pos tag is RB)
			else if (pos[nega].startsWith("RB") && !tokens[nega+1].equals("only") && posWords.contains(tokens[Wmin]))
				posORneg = NEGATIVE;
			else if (pos[nega].startsWith("RB") && tokens[nega+1].equals("only") && negWords.contains(tokens[Wmin]))
				posORneg = NEGATIVE;
			else if (nega == (Math.min(Wmin+4, tokens.length-1)-1) && negWords.contains(tokens[Wmin]))
				posORneg = NEGATIVE;
		}
		// define the pattern is valid or not
		if (!pattern_times.containsKey(pattern_list))
			pattern_times.put(pattern_list, false);
		else
			pattern_times.put(pattern_list, true);
		// store the Pattern Object into the map	
		List<Pattern> pt_list = (allPattern.containsKey(productId) ? allPattern.get(productId) : new ArrayList<Pattern>());
		pt_list.add(new Pattern(pattern_list, feature, 1, posORneg, sentence));
		allPattern.put(productId, pt_list);
	}
}
