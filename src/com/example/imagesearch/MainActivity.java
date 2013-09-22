package com.example.imagesearch;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.service.textservice.SpellCheckerService.Session;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	protected static final int IMAGE_PICK = 200;
	private static final int SELECT_PICTURE = 1;
	private Uri imageUri;
	private int buttonOneOrTwo = 1;
    private String selectedImagePath;
    String filemanagerstring;
    String logo,imagePath,Logo;
    ImageView img,img1;
    int column_index;
    Cursor cursor;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final Button camera = (Button) findViewById(R.id.button1);
		final Button gallery = (Button) findViewById(R.id.button2);

		camera.setOnClickListener(cameraListener);
		gallery.setOnClickListener(galleryListener);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(buttonOneOrTwo == 1) {
			if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
				if(resultCode == RESULT_OK) {
					// TO-DO networking with server
					Intent temp = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, data.getData());
					startActivity(temp);
				}
			}
		}
		else {
			if (resultCode == Activity.RESULT_OK) {
		        if (requestCode == SELECT_PICTURE) {
		            Uri selectedImageUri = data.getData();

		            //OI FILE Manager
		            filemanagerstring = selectedImageUri.getPath();
		           //MEDIA GALLERY
		           selectedImagePath = getPath(selectedImageUri);
		           img.setImageURI(selectedImageUri);

		           imagePath.getBytes();
		           TextView txt = (TextView)findViewById(R.id.button2);
		           txt.setText(imagePath.toString());
		           Bitmap bm = BitmapFactory.decodeFile(imagePath);

		           img1.setImageBitmap(bm);
		        }

		    }
		}
	}
	
	//UPDATED!
	public String getPath(Uri uri) {
	String[] projection = { MediaColumns.DATA };
	Cursor cursor = managedQuery(uri, projection, null, null, null);
	column_index = cursor
	        .getColumnIndexOrThrow(MediaColumns.DATA);
	cursor.moveToFirst();
	 imagePath = cursor.getString(column_index);

	return cursor.getString(column_index);
	}
	

	// Camera Button Click
	private OnClickListener cameraListener = new OnClickListener() {
		public void onClick(View v) {
			buttonOneOrTwo = 1;
			String fileName = "image-search.jpg";
			ContentValues values = new ContentValues();
			values.put(MediaStore.Images.Media.TITLE, fileName);
			values.put(MediaStore.Images.Media.DESCRIPTION,"Image capture by camera");
			imageUri = getContentResolver().insert(
			        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
			startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	    }
	};
	
	// Gallery Button Click
	private OnClickListener galleryListener = new OnClickListener() {
		public void onClick(View v) {
			buttonOneOrTwo = 2;
			ContentValues values = new ContentValues();
			imageUri = getContentResolver().insert(
			        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			startActivityForResult(intent, IMAGE_PICK);
			
	    }
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
