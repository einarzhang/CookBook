package com.cb.nb.zk;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MListActivity extends Activity implements OnClickListener, OnItemClickListener, Runnable {
	
	private ImageView unconnect;
	private ListView mlist;
	Handler handler;
	ArrayList<HashMap<String, Object>> mdata;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		findViewById(R.id.list_return).setOnClickListener(this);
		mlist = (ListView) findViewById(R.id.mlist);
		mlist.setOnItemClickListener(this);
		unconnect = (ImageView) findViewById(R.id.list_unconnect);
		unconnect.setOnClickListener(this);
		((TextView)findViewById(R.id.list_title)).setText(getIntent().getStringExtra("title"));
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if(mdata == null) {
					change(true);
				} else {
					change(false);
					mlist.setAdapter(new SimpleAdapter(MListActivity.this, mdata, R.layout.list_item, new String[]{"name"}, new int[]{R.id.list_item_text}));
				}
			}
		};
		new Thread(this).start();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void onItemClick(AdapterView<?> av, View v, int position, long arg3) {
		HashMap<String, Object> item = (HashMap<String, Object>)av.getItemAtPosition(position);
		Intent intent = new Intent();
		intent.putExtra("id", (Integer)item.get("id"));
		intent.putExtra("title", (String)item.get("name"));
		intent.setClass(this, CListActivity.class);
		startActivity(intent);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.list_return:
			MListActivity.this.finish();
			break;
		case R.id.list_unconnect:
			new Thread(this).start();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void run() {
		if(MUtils.canAccessNetwork(MListActivity.this)) {
			mdata = MUtils.getChildClass(getIntent().getIntExtra("id", 1));
		} 
		handler.sendEmptyMessage(1);
	}
	
	void change(boolean flag) {
		if(flag) {
			mlist.setVisibility(View.GONE);
			unconnect.setVisibility(View.VISIBLE);
		} else {
			mlist.setVisibility(View.VISIBLE);
			unconnect.setVisibility(View.GONE);
		}
	}
}
