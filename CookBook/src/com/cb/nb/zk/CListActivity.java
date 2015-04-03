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
	
	int lastVisibleIndex = 0;	//���������ɼ���Ŀ
	int page = 1;	//��ǰ��ҳҳ��
	int cId;		//����ID
	String keyword;	//�����ؼ���
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
			//ʳ������
			keyword = getIntent().getStringExtra("keyword");
			((TextView)findViewById(R.id.clist_title)).setText(keyword);
		} else {
			//�������
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
		//��ȡ�ɹ�������������б�
		cList.addAll(results);
	}
	
	Handler handler = new Handler(new Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			//�����б����
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
			int itemsLastIndex = mAdapter.getCount() - 1; // ���ݼ����һ�������
			if ((scrollState == SCROLL_STATE_TOUCH_SCROLL || scrollState == SCROLL_STATE_IDLE)
					&& lastVisibleIndex == itemsLastIndex) {
				//���������һ��ʱ�������µ�һҳ����
				new Thread(CListActivity.this).start();
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			//���������и������ɼ�����
			lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;
		}
	};
}
