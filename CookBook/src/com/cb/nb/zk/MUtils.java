package com.cb.nb.zk;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MUtils {
	
	public static final String PREFIX_IMG = "http://www.yi18.net/";
	
	public static final int SORT1 = 1;	//最新时间
	public static final int SORT2 = 2;

	@SuppressWarnings("serial")
	public static ArrayList<HashMap<String, Object>> getChildClass(final int pid) {
		String url = "http://api.yi18.net/cook/cookclass";
		String result = HttpUtils.httpGet(url, new HashMap<String, Object>(){{
			put("id", pid);
		}});
		ArrayList<HashMap<String, Object>> dataMap = new  ArrayList<HashMap<String, Object>>();
		if(result != null) {
			try {
				JSONObject root = new JSONObject(result);
				if(root.getBoolean("success")) {
					JSONArray datas = root.getJSONArray("yi18");
					for(int i = 0, len = datas.length(); i < len; i++) {
						HashMap<String, Object> data = new HashMap<String, Object>();
						JSONObject obj = datas.getJSONObject(i);
						data.put("id", obj.getInt("id"));
						data.put("name", obj.getString("name"));
						dataMap.add(data);
					}
				}
			} catch (Exception e) {
			}
		}
		return dataMap;
	}
	
	
	@SuppressWarnings("serial")
	public static ArrayList<Cook> getCooks(final int classId, final int page, final int sortType) {
		String url = "http://api.yi18.net/cook/list";
		String result = HttpUtils.httpGet(url, new HashMap<String, Object>(){{
			put("id", classId);
			put("page", page);
			put("limit", 20);
			put("type", sortType == SORT2 ? "count" : "id");
		}});
		ArrayList<Cook> dataMap = new  ArrayList<Cook>();
		if(result != null) {
			try {
				JSONObject root = new JSONObject(result);
				if(root.getBoolean("success")) {
					JSONArray datas = root.getJSONArray("yi18");
					for(int i = 0, len = datas.length(); i < len; i++) {
						JSONObject obj = datas.getJSONObject(i);
						Cook c = new Cook();
						c.id = obj.optInt("id");
						c.name = obj.optString("name");
						c.count = obj.optInt("count");
						c.fcount = obj.optInt("fcount");
						c.rcount = obj.optInt("rcount");
						c.food = obj.optString("food");
						c.img = obj.optString("img");
						c.tag = obj.optString("tag");
						dataMap.add(c);
					}
				}
			} catch (Exception e) {
			}
		}
		return dataMap;
	}
	
	@SuppressWarnings({ "serial", "deprecation" })
	public static ArrayList<Cook> search(final String keyword, final int page) {
		String url = "http://api.yi18.net/cook/search";
		String result = HttpUtils.httpGet(url, new HashMap<String, Object>(){{
			put("keyword", URLEncoder.encode(keyword));
			put("page", page);
			put("limit", 20);
		}});
		ArrayList<Cook> dataMap = new  ArrayList<Cook>();
		if(result != null) {
			try {
				JSONObject root = new JSONObject(result);
				if(root.getBoolean("success")) {
					JSONArray datas = root.getJSONArray("yi18");
					for(int i = 0, len = datas.length(); i < len; i++) {
						JSONObject obj = datas.getJSONObject(i);
						Cook c = new Cook();
						c.id = obj.optInt("id");
						c.name = obj.optString("name");
						if(c.name != null) {
							c.name = c.name.replace("<font color=\"red\">", "").replace("</font>", "");
						}
						c.food = obj.optString("description");
						c.img = obj.optString("img");
						c.tag = obj.optString("keywords");
//						c.message = obj.optString("content");
						dataMap.add(c);
					}
				}
			} catch (Exception e) {
			}
		}
		return dataMap;
	}
	
	@SuppressWarnings("serial")
	public static Cook show(final int id) {
		String url = "http://api.yi18.net/cook/show";
		String result = HttpUtils.httpGet(url, new HashMap<String, Object>(){{
			put("id", id);
		}});
		if(result != null) {
			try {
				JSONObject root = new JSONObject(result);
				if(root.getBoolean("success")) {
					JSONObject obj = root.getJSONObject("yi18");
					Cook c = new Cook();
					c.id = obj.optInt("id");
					c.name = obj.optString("name");
					c.count = obj.optInt("count");
					c.fcount = obj.optInt("fcount");
					c.rcount = obj.optInt("rcount");
					c.food = obj.optString("food");
					c.img = obj.optString("img");
					c.tag = obj.optString("tag");
					c.message = obj.optString("message");
					return c;
				}
			} catch (Exception e) {
			}
		}
		return null;
	}
	
	/**
	 * 判断网络是否连接
	 * @param ctx
	 * @return
	 */
	public static boolean canAccessNetwork(Context ctx) {
		ConnectivityManager manager = (ConnectivityManager) ctx.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			return false;
		}
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable()) {
			return false;
		}
		return true;

	}
}
