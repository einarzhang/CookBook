package com.cb.nb.zk;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class CListActivity extends Activity implements OnClickListener, OnItemClickListener, Runnable {
	
	int lastVisibleIndex = 0;	//滚动的最后可见条目
	int page = 1;	//当前分页页码
	int cId;		//分类ID
	String keyword;	//搜索关键词
	CListAdapter mAdapter;	
	List<Cook> cList;
	ListView mlistView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clist);
		findViewById(R.id.clist_return).setOnClickListener(this);
		mlistView = (ListView) findViewById(R.id.clist);
		mlistView.setOnItemClickListener(this);
		mlistView.setOnScrollListener(scrollListener);
		cId = getIntent().getIntExtra("id", 0);
		if(cId == 0) {
			//食谱搜索
			keyword = getIntent().getStringExtra("keyword");
			((TextView)findViewById(R.id.clist_title)).setText(keyword);
		} else {
			//分类进入
			((TextView)findViewById(R.id.clist_title)).setText(getIntent().getStringExtra("title"));
		}
		cList = new ArrayList<Cook>();
		new Thread(this).start();
	}
	
	@Override
	public void onItemClick(AdapterView<?> av, View arg1, int arg2, long arg3) {
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.clist_return:
			CListActivity.this.finish();
			break;

		default:
			break;
		}
	}
	
	@Override
	public void run() {
		loadData();
		handler.sendEmptyMessage(0);
	}
	
	public void loadData() {
		List<Cook> results;
		if(cId == 0) {
			results = MUtils.search(keyword, page++);
		} else {
			results = MUtils.getCooks(cId, page++, MUtils.SORT1);
		}
		//获取成功，则更新数据列表
		cList.addAll(results);
	}
	
	Handler handler = new Handler(new Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			//更新列表界面
			if(mAdapter == null) {
				mAdapter = new CListAdapter(CListActivity.this, cList);
				mlistView.setAdapter(mAdapter);
			} else {
				mAdapter.notifyDataSetChanged();
			}
			return false;
		}
	});
	
	OnScrollListener scrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			int itemsLastIndex = mAdapter.getCount() - 1; // 数据集最后一项的索引
			if ((scrollState == SCROLL_STATE_TOUCH_SCROLL || scrollState == SCROLL_STATE_IDLE)
					&& lastVisibleIndex == itemsLastIndex) {
				//当滚动最后一项时，加载新的一页数据
				new Thread(CListActivity.this).start();
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			//滚动过程中更新最后可见索引
			lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;
		}
	};
}
