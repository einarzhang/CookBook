package com.cb.nb.zk;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainGridAdapter extends BaseAdapter {
	
	private List<Item> items;
	private Context ctx;
	
	public MainGridAdapter(List<Item> items, Context ctx) {
		this.items = items;
		this.ctx = ctx;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int arg0) {
		return items.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		if(view == null) {
			view = LayoutInflater.from(ctx).inflate(R.layout.item, null);
		}
		final Item item = items.get(position);
		ImageView icon = (ImageView) view.findViewById(R.id.item_icon);
		icon.setImageResource(item.icon);
		TextView text = (TextView) view.findViewById(R.id.item_text);
		text.setText(ctx.getString(item.text));
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("id", position+1);
				intent.putExtra("title", ctx.getString(item.text));
				intent.setClass(ctx, MListActivity.class);
				ctx.startActivity(intent);
			}
		});
		return view;
	}
	
	static class Item {
		
		public Item() {
			
		}
		
		public Item(int bgColor, int icon, int text) {
			super();
			this.bgColor = bgColor;
			this.icon = icon;
			this.text = text;
		}
		
		public int bgColor;
		public int icon;
		public int text;
	}

}
