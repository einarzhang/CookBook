package com.cb.nb.zk;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class HttpUtils {

	/**
	 * 获取指定URL请求的结果信息，以字符串方式返回请求内容
	 * @param url 请求地址
	 * @param param 请求所需参数
	 * @return
	 */
	public static String httpGet(String url, HashMap<String, Object> param) {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
		HttpConnectionParams.setSoTimeout(httpParams, 5000);
		HttpClient mHttpClient = new DefaultHttpClient(httpParams);
		String mUrl = url;
		if(param != null && param.size() > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append(url);
			sb.append("?");
			for(Entry<String, Object> m : param.entrySet()) {
				sb.append(m.getKey());
				sb.append("=");
				sb.append(m.getValue());
				sb.append("&");
			}
			mUrl = sb.substring(0, sb.length()-1);
		}
		HttpGet httpRequest = new HttpGet(mUrl);
		try {
			HttpResponse httpResponse = mHttpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				String str = EntityUtils.toString(httpResponse.getEntity());
				return str;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpRequest.abort();
		}
		return null;
	}
}
