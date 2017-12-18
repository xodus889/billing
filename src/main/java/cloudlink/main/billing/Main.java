package cloudlink.main.billing;

import cloudlink.domin.bililng.Product;
import cloudlink.service.billing.HandleBilingData;

public class Main {

	public static void main(String[] args) {
		
		HandleBilingData handle = new HandleBilingData(Product.HEAD + Product.CLOUDFRONT + Product.TAIL);
		handle.readFileInsertData();
	}
}
