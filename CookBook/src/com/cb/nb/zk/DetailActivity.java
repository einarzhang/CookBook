package com.cb.nb.zk;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends Activity implements OnClickListener, Runnable {

	private TextView titleView, contentView, foodView, nameView;
	private ImageView imgView;
	private int id;
	private Cook cook;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		titleView = (TextView) findViewById(R.id.detail_title);
		contentView = (TextView) findViewById(R.id.detail_text);
		foodView = (TextView) findViewById(R.id.detail_food);
		nameView = (TextView) findViewById(R.id.detail_name);
		imgView = (ImageView) findViewById(R.id.detail_img);
		findViewById(R.id.detail_return).setOnClickListener(this);
		initData();
	}
	
	private void initData() {
		titleView.setText(getIntent().getStringExtra("title"));
		id = getIntent().getIntExtra("id", 1);
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		cook = MUtils.show(id);
		handler.sendEmptyMessage(0);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.detail_return:
			DetailActivity.this.finish();
			break;

		default:
			break;
		}
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			//异步显示图片
			ImageLoader.getInstance().displayImage(MUtils.PREFIX_IMG + cook.img, imgView);
			nameView.setText(cook.name);
			foodView.setText(cook.food);
			//内容以Html的方式展示
			contentView.setText(Html.fromHtml(cook.message));
		}
	};
}
