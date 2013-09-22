package com.example.imagesearch;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Camera;
import android.view.Menu;
import android.widget.FrameLayout;

public class CameraActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camera, menu);
		return true;
	}

}
