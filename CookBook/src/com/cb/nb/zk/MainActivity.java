package com.cb.nb.zk;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.cb.nb.zk.MainGridAdapter.Item;

public class MainActivity extends Activity implements OnClickListener {
	
	private GridView grid;
	private ImageView clearButton, searchButton;
	private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        clearButton = (ImageView) findViewById(R.id.main_clear_btn);
        clearButton.setOnClickListener(this);
        searchButton = (ImageView) findViewById(R.id.main_search_btn);
        searchButton.setOnClickListener(this);
        input = (EditText) findViewById(R.id.main_input);
        grid = (GridView) findViewById(R.id.main_grid);
        initGrid();
    }

    private void initGrid() {
    	List<Item> items = new ArrayList<Item>();
    	items.add(new Item(R.color.item1, R.drawable.item1, R.string.item1));
    	items.add(new Item(R.color.item2, R.drawable.item2, R.string.item2));
    	items.add(new Item(R.color.item3, R.drawable.item3, R.string.item3));
    	items.add(new Item(R.color.item4, R.drawable.item4, R.string.item4));
    	items.add(new Item(R.color.item5, R.drawable.item5, R.string.item5));
    	items.add(new Item(R.color.item6, R.drawable.item6, R.string.item6));
    	items.add(new Item(R.color.item7, R.drawable.item7, R.string.item7));
    	items.add(new Item(R.color.item8, R.drawable.item8, R.string.item8));
    	items.add(new Item(R.color.item9, R.drawable.item9, R.string.item9));
    	items.add(new Item(R.color.item9, R.drawable.item10, R.string.item10));
    	items.add(new Item(R.color.item9, R.drawable.item11, R.string.item11));
    	items.add(new Item(R.color.item9, R.drawable.item12, R.string.item12));
    	items.add(new Item(R.color.item9, R.drawable.item13, R.string.item13));
    	items.add(new Item(R.color.item9, R.drawable.item14, R.string.item14));
    	grid.setAdapter(new MainGridAdapter(items, this));
    }
    
    @Override
    public void onClick(View v) {
    	switch (v.getId()) {
		case R.id.main_clear_btn:
			input.setText("");
			break;
		case R.id.main_search_btn:
			String searchStr = input.getText().toString();
			if(searchStr == null || searchStr.trim().equals("")) {
				Toast.makeText(this, "请输入要搜索的菜谱!", Toast.LENGTH_SHORT).show();
			} else {
				Intent intent = new Intent();
				intent.putExtra("keyword", searchStr);
				intent.setClass(this, CListActivity.class);
				startActivity(intent);
			}
			break;

		default:
			break;
		}
    }
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!isExit) {
				isExit = true;
				Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
				tExit = new Timer();
				tExit.schedule(new TimerTask() {
					@Override
					public void run() {
						isExit = false;
						tExit.cancel();
					}
				}, 3000);
			} else {
				finish();
				android.os.Process.killProcess(android.os.Process.myPid());
				System.gc();
			}
		} else {
		}
		return false;
	}

	private boolean isExit = false;
	Timer tExit;
    
}
