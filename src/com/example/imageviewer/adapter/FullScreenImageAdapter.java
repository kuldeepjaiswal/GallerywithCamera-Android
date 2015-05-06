package com.example.imageviewer.adapter;



import com.example.imageviewer.R;
import com.example.imageviewer.helper.Utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;



import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class FullScreenImageAdapter extends PagerAdapter {

	private Activity _activity;
	private ArrayList<String> _imagePaths;
	private LayoutInflater inflater;
	private int imageWidth,imageHeight;

	// constructor
	public FullScreenImageAdapter(Activity activity,
			ArrayList<String> imagePaths,int width,int height) {
		this._activity = activity;
		this._imagePaths = imagePaths;
		this.imageWidth=width;
		this.imageHeight=height;
	}

	@Override
	public int getCount() {
		return this._imagePaths.size();
	}

	@Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }
	
	@Override
    public Object instantiateItem(ViewGroup container, int position) {
		ImageView imgDisplay;//TouchImageView imgDisplay;
        Button btnClose;
 
        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);
 
        imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgDisplay);
        btnClose = (Button) viewLayout.findViewById(R.id.btnClose);
        
        
        
        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        //Bitmap bitmap = BitmapFactory.decodeFile(_imagePaths.get(position), options);
        //imgDisplay.setImageBitmap(bitmap);
        
        
        try {

			File f = new File(_imagePaths.get(position));

			//first get the dimensions of the file and scale it
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			
			final int REQUIRED_WIDTH = imageWidth;
			final int REQUIRED_HIGHT = imageHeight;
			int scale = 1;
			while (o.outWidth / scale / 2 >= REQUIRED_WIDTH
					&& o.outHeight / scale / 2 >= REQUIRED_HIGHT)
				scale *= 2;

			//deflate each pixel to 8 bits and populate memory
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inPreferredConfig =Bitmap.Config.ARGB_8888;
			o2.inSampleSize = scale;
			Bitmap bitmap= BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
			 imgDisplay.setImageBitmap(bitmap);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        
        
        
        
        
        
        
        
        
        
        
        // close button click event
        btnClose.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				_activity.finish();
			}
		}); 

        ((ViewPager) container).addView(viewLayout);
 
        return viewLayout;
	}
	
	@Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
 
    }

}
