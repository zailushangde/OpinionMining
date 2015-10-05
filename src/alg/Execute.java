package alg;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import alg.cases.Feature;
import alg.cases.Product;
import alg.cases.comparsion.CaseComparsion;
import alg.cases.comparsion.Comparsion;
import alg.cases.similarity.CaseSimilarity;
import alg.cases.similarity.CombiningSimilarity;
import alg.cases.similarity.CosineSimilarity;
import alg.recommender.CombiningRecommender;
import alg.recommender.Recommender;
import alg.sentiment.SentimentMining;
import alg.summary.Summary;
import util.explanation.Explanation;
import util.reader.DatasetReader;
import util.reader.FileReader;

public class Execute{

	public static void main(String[] args) {
		// this class is to create the visible table
		JTableCreateTable tableCreater = new JTableCreateTable();
		/** must select one categoryName, and only can select one 
		 * the idToSummary should belong to the corresponding categoryName**/
		String categoryName = "Printer"; String idToSummary = "B003E1S2K2";
//		String categoryName = "Digital Camera"; String idToSummary = "B005IHAIHA";
		
		// the process of initializing the dataset and sentiment mining
		SentimentMining sentMining = initDataset(categoryName);
		
		/** summarizing **/
		// the process of summarizing for all products
		Summary summary = new Summary(sentMining, categoryName);
		// select one product to output, and the selected product Id can be typed in directory
		tableCreater.summaryOneProduct(summary, idToSummary);
				
		/** comparing, the maximum number to compare is 5**/
//		// the example product Ids for PRINTER
		String[] ids = {"B001R4BTIA", "B003E1S2K2", "B004QM8J8S"};
//		// the example product Ids for DIGITAL CAMERA
//		String[] ids = {"B005GMRVZO", "B005IHAIHA", "B004HW73OS"};
//		// the process of comparsion
		CaseComparsion comp = new CaseComparsion(categoryName, ids , summary);
		// create and show the table
		tableCreater.compareProducts(comp);
//		
		/** recommendation **/
//		CaseSimilarity casesimilarity = new CosineSimilarity(); // the cosine similarity
        // you can change the weight W, and right now is 0.2
		CaseSimilarity casesimilarity = new CombiningSimilarity(0.2); // the cosine and sentiment similarity
		Recommender recommender = new CombiningRecommender(casesimilarity, summary);
		List<Product> recommenderList = recommender.getRecommendations("B000CO9ZMI", 5);
		/** explanation **/
		Explanation explanation = new Explanation(summary);
		Map<String, String[]> results = explanation.getExplanations(recommenderList, "B000CO9ZMI");

		// create and show the table
		tableCreater.getExplanationTable(recommenderList, results);
			
	}

	/**
	 * initialize the dataset
	 * @param categoryName the product category
	 * @return the sentimentMining object
	 */
	private static SentimentMining initDataset(String categoryName) {
		// the path for POSTIVE and NEGATIVE words
		String posWordFile = "sentiment lexicon" + File.separator + "positive-words.txt";
		String negWordFile = "sentiment lexicon" + File.separator + "negative-words.txt";
		
		String fileName = categoryName + ".txt";
//		String category = categoryName;
		String featureFile = "feature sets" + File.separator + categoryName + " Features.txt";
		String sameMeaningWords = "same features" + File.separator + categoryName + " same feature.csv";

		DatasetReader reader = new DatasetReader(fileName); // create an instance of the DatasetReader class
		FileReader readFiles = new FileReader(featureFile, posWordFile, negWordFile, sameMeaningWords);
		
		SentimentMining sentMining = new SentimentMining(readFiles, fileName, reader);
		
		return sentMining;
	}
}