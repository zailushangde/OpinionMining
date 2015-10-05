/**
 * a class to generate visible table
 */
package alg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import alg.cases.Feature;
import alg.cases.Product;
import alg.cases.comparsion.CaseComparsion;
import alg.summary.Summary;

public class JTableCreateTable{

	/**
	 * generation the visible explanation table
	 * @param recommenderList the recommendation product list
	 * @param results store the better features and worse features for the recommendation list
	 */
	public void getExplanationTable(List<Product> recommenderList,
			Map<String, String[]> results) {
System.out.println("Create the explanation table for" + results.keySet().toString());
		Object[][] data = new Object[results.size()][3];
		String[] columnNames = {"Product Id", "Better Feature", "Worse Feature"};
		int i = 0;
		for (Product topP: recommenderList)
		{
			data[i][0] = topP.getProductId();
			data[i][1] = results.get(topP.getProductId())[0];
			data[i][2] = results.get(topP.getProductId())[1];
			i ++;
		}
		
		createTable("Explanation " + results.keySet().toString(), data, columnNames);
	}

	/**
	 * generate the comparison table
	 * @param comp the object of CaseComparison
	 */
	public void compareProducts(CaseComparsion comp) {

		Map<String, Map<String, Double>> results = comp.commonComp();
System.out.println("Create the comparison table for" + results.keySet().toString());
		List<String> sortedFeatures = new ArrayList<String>();
		sortedFeatures.add("Product Id");
		Object[][] data = new Object[results.size()][20];
		
		boolean flag = true;
		int i = 0;
		for (String productId: results.keySet())
		{
			data[i][0] = productId;
			int j = 1;
			for (String featureName: results.get(productId).keySet())
			{
				if (flag)
					sortedFeatures.add(featureName);
				data[i][j] = new Double(results.get(productId).get(sortedFeatures.get(j)));
				j ++;
			}
			flag = false;
			i ++;
		}
		String[] columnNames = new String[sortedFeatures.size()];
		for (i = 0; i < sortedFeatures.size(); i ++)
			columnNames[i] = sortedFeatures.get(i);
		
		createTable("Comparsion among " + results.keySet().toString(), data, columnNames);
	}

	/**
	 * generate the summary for one product
	 * @param summary the object of the summary
	 * @param idToSummary the product id to be summarized
	 */
	public void summaryOneProduct(Summary summary, String idToSummary) {
System.out.println("Create the summary table for " + idToSummary);
		for (String productID: summary.getCaseBase().getProductIdSet())
			if (productID.equalsIgnoreCase(idToSummary))
			{
				String[] columnNames = {"Feature", "Positive Sentiment(#)", "Negative Sentiment(#)", "Neutral Sentiment(#)", "Example Sentence"};
				Object[][] data = new Object[summary.getCaseBase().getProduct(productID).getFeatures().keySet().size()][5];
				int i = 0;
				for (String featureName: summary.getCaseBase().getProduct(productID).getFeatures().keySet())
				{
					Feature featureObject = summary.getCaseBase().getProduct(productID).getFeatures().get(featureName);
					data[i][0] = (i+1) + ". " + featureName;
					data[i][1] = new Integer(featureObject.getPos());
					data[i][2] = new Integer(featureObject.getNeg());
					data[i][3] = new Integer(featureObject.getNeu());
					data[i][4] = featureObject.getExampleSentence();
					i ++;
				}
			
				createTable("Summary for " + idToSummary, data, columnNames);
			}
				
	}
	
	/**
	 * generate the visible table
	 * @param tableTitle the title of the table
	 * @param data the data showed in this table
	 * @param columnNames the column names for this table
	 */
	private void createTable(final String tableTitle, final Object[][] data,
			final String[] columnNames) {
		JFrame frame= new JFrame();
		JTable table = new JTable(data, columnNames);
		
		JScrollPane contentPane = new JScrollPane();
		contentPane.setViewportView(table);
		
		frame.add(contentPane);
		frame.setTitle(tableTitle);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
