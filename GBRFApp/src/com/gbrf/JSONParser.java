package com.gbrf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.gbrf.util.FileUtils;

public class JSONParser {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	private HttpURLConnection httpConnection;

	InputStream inputStream = null;
	BufferedReader reader = null;

	public JSONParser() {
	}

	public JSONObject makeHttpRequest(String url, String method, List params) {

		// Making HTTP request
		try {

			// check for request method
			if (method == "POST") {
				// request method is POST
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(params));

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();

			} else if (method == "GET") {
				// request method is GET
				DefaultHttpClient httpClient = new DefaultHttpClient();
				String paramString = URLEncodedUtils.format(params, "utf-8");
				url += "?" + paramString;
				HttpGet httpGet = new HttpGet(url);

				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json.substring(json.indexOf("{"),
					json.lastIndexOf("}") + 1));
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;

	}

	private void openHttpUrlConnection(String urlString) throws IOException {
		Log.d("urlstring in parser", urlString + "");
		URL url = new URL(urlString);
		URLConnection connection = url.openConnection();

		httpConnection = (HttpURLConnection) connection;
		httpConnection.setConnectTimeout(30000);
		httpConnection.setRequestMethod("GET");

		httpConnection.connect();
	}

	private void openHttpClient(String urlString) throws IOException {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpParams httpParams = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 30000);

		HttpGet httpGet = new HttpGet(urlString);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-type", "application/json");
		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		inputStream = httpEntity.getContent();

		reader = new BufferedReader(
				new InputStreamReader(inputStream, "UTF-8"), 8);
	}

	// using HttpClient for <= Froyo
	public JSONObject getJSONHttpClient(String url)
			throws ClientProtocolException, IOException, JSONException {
		JSONObject jsonObject = null;
		try {
			openHttpClient(url);

			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}

			json = sb.toString();

			Log.d("json", json);
			jsonObject = new JSONObject(json);

		} finally {
			FileUtils.close(inputStream);
			FileUtils.close(reader);
		}
		return jsonObject;
	}

	// using HttpURLConnection for > Froyo
	public JSONObject getJSONHttpURLConnection(String urlString)
			throws IOException, JSONException {

		BufferedReader reader = null;
		StringBuffer output = new StringBuffer("");
		InputStream stream = null;
		JSONObject jsonObject = null;
		try {

			openHttpUrlConnection(urlString);

			if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				stream = httpConnection.getInputStream();

				reader = new BufferedReader(new InputStreamReader(stream,
						"UTF-8"), 8);
				String line = "";
				while ((line = reader.readLine()) != null)
					output.append(line + "\n");
				json = output.toString();
				jsonObject = new JSONObject(json);
			}

		} finally {
			FileUtils.close(stream);
			FileUtils.close(reader);
		}
		return jsonObject;
	}

	public JSONObject getJSONFromUrl(String url) {
		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}
		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}
		// return JSON String
		return jObj;
	}
}
