package com.example.imagesearch;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.MediaColumns;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
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

@SuppressWarnings("deprecation")
public class MainActivity extends Activity {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	protected static final int IMAGE_PICK = 200;
	private static final int SELECT_PICTURE = 1;
	private Uri imageUri;
	private File file;
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
	@SuppressWarnings("deprecation")
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
				
			public void OnActivityResult(int requestCode, int resultCode, Intent data) {



		        if (resultCode == RESULT_OK) {
		            Uri selectedImageUri = data.getData();
		           

		            }

		            
		        }

			
	private void PostFile(File filename){ 
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://www.tobedetermined.com/");

		    
		    try {

		    MultipartEntity mpEntity = new MultipartEntity();
		    ContentBody cbFile = new FileBody(filename, "image/jpeg");
		    mpEntity.addPart("userfile", cbFile);

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

				if (entity != null) {
				    InputStream instream = entity.getContent();
				        instream.close();
				}
		    }
			catch(Exception e) {
				
			    
		    } 
		    }
	
	
  /*  private void saveToFile(String message) throws Exception {
    
        String filePath = getFilesDir()+"";
        File file = new File(filePath + "/sdcard/DCIM/100MEDIA/Wardobe");
        FileOutputStream out = new FileOutputStream(file, true);
        Bitmap bmp = new Bitmap();
        out.write(message.getBytes());
        out.close();
        saveImage(filePath, "/sdcard/DCIM/100MEDIA/Wardobe/image.jpg", bmp);
        if(file != null) {
            saveImage(filePath, "sdcard/DCIM/100MEDIA/Wardrobe/image.jpg", bmp);
        } 

    }
    public void saveImage(String path, String dir, Bitmap image) {
        try{
            FileOutputStream fos = new FileOutputStream(path + dir);
            BufferedOutputStream stream = new BufferedOutputStream(fos);
            bmp.compress(CompressFormat.JPEG, 50, stream);
            stream.flush();
            stream.close(); 
        }
        catch(FileNotFoundException e) { 
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
	  */
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
