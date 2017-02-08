package com.gbrf;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.gbrf.dto.Product;
import com.gbrf.util.TagName;

public class JsonReader {

	public static List<Product> getHome(JSONObject jsonObject)
			throws JSONException {
		List<Product> products = new ArrayList<Product>();
		int count = 50;
		Product product;
		for (int i = 1; i <= count; i++) {
			if (jsonObject.has("" + i)) {
				product = new Product();
				product.setId(jsonObject.getJSONObject("" + i).getInt(
						TagName.KEY_ID));
				product.setName(jsonObject.getJSONObject("" + i).getString(
						TagName.KEY_NAME));
				product.setImageUrl(jsonObject.getJSONObject("" + i)
						.getString(TagName.KEY_IMAGE_URL));
				product.setPrice(jsonObject.getJSONObject(
						"" + i).getString(TagName.KEY_PRICE));
				products.add(product);
			}
		}

		return products;
	}
}
