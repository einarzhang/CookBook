package com.cb.nb.zk;

import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class CListAdapter extends BaseAdapter {
	
	private CListActivity activity;
	
	private List<Cook> dataList;
	
	public CListAdapter(CListActivity activity, List<Cook> dataList) {
		this.activity = activity;
		this.dataList = dataList;
		//初始化Android-Universal-Image-Loader框架
		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(activity));
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(activity).inflate(R.layout.clist_item, null);
		final Cook c = dataList.get(position);
		((TextView)convertView.findViewById(R.id.clist_item_title)).setText(c.name);
		((TextView)convertView.findViewById(R.id.clist_item_detail)).setText(c.tag);
		//设置图片显示格式(我们可以设置圆角、缓存等一些列配置)
		DisplayImageOptions options = new DisplayImageOptions.Builder()  
	        .showImageOnLoading(R.drawable.loading)  	//加载中显示的正在加载中的图片
	        .showImageOnFail(R.drawable.loading)  		//为了方便，加载失败也显示加载中的图片
	        .cacheInMemory(true)  					//在内存中缓存图片
	        .cacheOnDisk(true)  					
	        .bitmapConfig(Bitmap.Config.RGB_565)
	        .imageScaleType(ImageScaleType.EXACTLY)		//设置图片以如何的编码方式显示
	        .build(); 
		//异步加载图片，并渲染到指定的控件上
		ImageLoader.getInstance().displayImage(MUtils.PREFIX_IMG + c.img, (ImageView)convertView.findViewById(R.id.clist_item_icon), options);
		//Item点击后进入食谱详情界面
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(activity, DetailActivity.class);
				intent.putExtra("id", c.id);
				intent.putExtra("title", c.name);
				activity.startActivity(intent);
			}
		});
		return convertView;
	}

	//为Adapter增加新的食谱数据，当分页加载时，用于更新分页信息
	public void add(List<Cook> newData) {
		dataList.addAll(newData);
	}
}
