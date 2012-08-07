package com.delesio.json;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyHttpClient {

	private static final String url = "http://www.totallyscored.com/json";

	public static void main(String[] args) throws Exception {

		MyHttpClient client = new MyHttpClient();
		parseData(client.getData());
	}

	private StringBuffer getData() {
		// create a singular HttpClient object
		HttpClient client = new HttpClient();

		// Buffer for the response
		StringBuffer sb = new StringBuffer();

		// establish a connection within 5 seconds
		client.getHttpConnectionManager().getParams()
				.setConnectionTimeout(5000);

		HttpMethod method = new GetMethod(url);
		method.setFollowRedirects(true);

		// execute the method
		InputStream responseBody = null;
		try {
			client.executeMethod(method);
			responseBody = method.getResponseBodyAsStream();
		} catch (HttpException he) {
			System.err.println("Http error connecting to '" + url + "'");
			System.err.println(he.getMessage());
			System.exit(-4);
		} catch (IOException ioe) {
			System.err.println("Unable to connect to '" + url + "'");
			System.exit(-3);
		}

		// write out the request headers
		System.out.println("*** Request ***");
		System.out.println("Request Path: " + method.getPath());
		System.out.println("Request Query: " + method.getQueryString());
		Header[] requestHeaders = method.getRequestHeaders();
		for (int i = 0; i < requestHeaders.length; i++) {
			System.out.print(requestHeaders[i]);
		}

		// write out the response headers
		System.out.println("*** Response ***");
		System.out.println("Status Line: " + method.getStatusLine());
		Header[] responseHeaders = method.getResponseHeaders();
		for (int i = 0; i < responseHeaders.length; i++) {
			System.out.print(responseHeaders[i]);
		}

		// write out the response body
		System.out.println("*** Response Body ***");

		try {
			int c;
			while ((c = responseBody.read()) != -1) {
				if (!Character.isWhitespace(c))
					sb.append((char) c);
			} 
		} catch (IOException ioe) {
			System.err.print(ioe);
		}

		// clean up the connection resources
		method.releaseConnection();

		return sb;
	}

	private static void parseData(StringBuffer sb) throws ParseException {
		try
		{
		JSONArray jsArray = new JSONArray(sb.toString());
		JSONObject jsObject;
		for (int i = 0; i < jsArray.length(); i++) {
			jsObject = jsArray.getJSONObject(i);
			if(jsObject.getString("sport").equals("MajorLeagueBaseball"))
				System.err.println(jsObject);
		}
		}
		catch (JSONException exception)
		{
			exception.printStackTrace();
		}
	}
}
