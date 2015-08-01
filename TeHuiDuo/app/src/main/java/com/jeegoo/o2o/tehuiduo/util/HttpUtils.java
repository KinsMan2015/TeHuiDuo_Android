package com.jeegoo.o2o.tehuiduo.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpUtils {
	public static final String VERBOSE_TAG = "verbose";
	public static final String DEBUG_TAG = "debug";
	public static final String INFO_TAG = "info";
	public static final String WARN_TAG = "warn";
	public static final String ERROR_TAG = "error";

	public static final String BASE_URL = "http://192.168.1.149:8080/XintongSmartCloud/";


	public static HttpGet getHttpGet(String url) {
		HttpGet request = new HttpGet(url);
		return request;
	}


	public static HttpPost getHttpPost(String url) {
		HttpPost request = new HttpPost(url);
		return request;
	}


	public static HttpResponse getHttpResponse(HttpGet request)
			throws ClientProtocolException, IOException {
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}


	public static HttpResponse getHttpResponse(HttpPost request)
			throws ClientProtocolException, IOException {
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}


	public static String queryStringForGet(String url) {
		HttpGet request = HttpUtils.getHttpGet(url);
		String result = null;
		try {
			HttpResponse response = HttpUtils.getHttpResponse(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "�����쳣";
			Log.e(ERROR_TAG, "�����쳣");
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "�����쳣";
			Log.e(ERROR_TAG, "�����쳣");
			return result;
		}
		return null;
	}


	public static String queryStringForPost(String url) {
		HttpPost request = HttpUtils.getHttpPost(url);
		String result = null;
		try {
			HttpResponse response = HttpUtils.getHttpResponse(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
				Log.i(VERBOSE_TAG, "ͨ��url��post���󣬷�����������" + result);
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "�����쳣";
			Log.e(ERROR_TAG, "�����쳣");
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "�����쳣";
			Log.e(ERROR_TAG, "�����쳣");
			return result;
		}

		return null;
	}


	public static String queryStringForPost(HttpPost request) {
		String result = null;
		try {
			HttpResponse response = HttpUtils.getHttpResponse(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "�����쳣";
			Log.e(ERROR_TAG, "�����쳣");
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "�����쳣";
			Log.e(ERROR_TAG, "�����쳣");
			return result;
		}
		return null;
	}
}
