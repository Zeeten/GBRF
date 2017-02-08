package com.gbrf.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.gbrf.JSONParser;

public class Test {
	
	public static void main(String[] args) {
		JSONParser jsonParser = new JSONParser();
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("uid", "13"));
		String url="http://theacademicworld.com/api/rest/order/purchases";
		String method="GET";
		JSONObject json = jsonParser.makeHttpRequest(url, method, param);
		System.out.println(json);
	}

}
