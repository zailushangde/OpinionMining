package alg.casebase;

import java.util.Map;

import alg.cases.Product;

public class PrinterBase extends CaseBase{

	public void add(Product product)
	{
		if (!product.getCategory().equalsIgnoreCase("Printer"))
			System.out.println("Warn: This product " + product.getProductId() + " doesn't belong to printer.");
		else
			super.add(product);
	}
}
