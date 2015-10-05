package alg.casebase;

import alg.cases.Product;

public class CameraBase extends CaseBase{

	public void add(Product product)
	{
		if (!product.getCategory().equalsIgnoreCase("Digital Camera"))
			System.out.println("Warn: This product " + product.getProductId() + " doesn't belong to digital camera.");
		else
			super.add(product);
	}
}
