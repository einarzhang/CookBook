package com.cb.nb.zk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

public class WelcomeActivity extends Activity {

	private Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				try {
					if(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
						Toast.makeText(WelcomeActivity.this, "无法加载SD卡，请重新加载SD卡", Toast.LENGTH_LONG).show();
						WelcomeActivity.this.finish();
						return;
					}
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
					}
					Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
					startActivity(intent);
					WelcomeActivity.this.finish();
				} catch (Exception e) {
//					Log.e("zg", "fail to transfer data", e);
				}
				
			}
		}, 1000);
	}
	
//	private void transFile(File dbFile) throws IOException {
//		InputStream is = getResources().openRawResource(R.raw.dream);
//		FileOutputStream fos = new FileOutputStream(dbFile);
//		byte[] buffer = new byte[1024 * 8];
//		int readByte = 0;
//		while ((readByte = is.read(buffer)) > 0) {
//			fos.write(buffer, 0, readByte);
//		}
//		fos.close();
//		is.close();
//	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
