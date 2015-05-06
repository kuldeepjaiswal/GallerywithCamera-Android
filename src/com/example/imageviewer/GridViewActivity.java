package com.example.imageviewer;


import com.example.imageviewer.adapter.GridViewImageAdapter;
import com.example.imageviewer.helper.AppConstant;
import com.example.imageviewer.helper.Utils;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;

//activity to start on application load
public class GridViewActivity extends Activity {

	private Utils utils;
	//paths to all the images are stored here
	private ArrayList<String> imagePaths = new ArrayList<String>();
	private GridViewImageAdapter adapter;
	private GridView gridView;
	private int columnWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid_view);

		gridView = (GridView) findViewById(R.id.grid_view);

		utils = new Utils(this);

		//launch camera activity on clicking the camera button
		Button camerabutton= (Button)findViewById(R.id.camera_button);
	    camerabutton.setOnClickListener(new OnClickListener() 
	    {   public void onClick(View v) 
	        {   
	            Intent intent = new Intent(GridViewActivity.this, CameraActivity.class);
	                startActivity(intent);      
	                //finish();
	        }
	    });
	    
		// Initilizing Grid View
		InitilizeGridLayout();

		// loading all image paths from SD card
		imagePaths = utils.getFilePaths();

		// Gridview adapter
		adapter = new GridViewImageAdapter(GridViewActivity.this, imagePaths,
				columnWidth);

		// setting grid view adapter
		gridView.setAdapter(adapter);
	}

	private void InitilizeGridLayout() {
		Resources r = getResources();
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				AppConstant.GRID_PADDING, r.getDisplayMetrics());

		//getting the dimensions of each preview in list view
		columnWidth = (int) ((utils.getScreenWidth() - ((AppConstant.NUM_OF_COLUMNS + 1) * padding)) / AppConstant.NUM_OF_COLUMNS);

		//setting the grid view options
		gridView.setNumColumns(AppConstant.NUM_OF_COLUMNS);
		gridView.setColumnWidth(columnWidth);
		gridView.setStretchMode(GridView.NO_STRETCH);
		gridView.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);
		gridView.setHorizontalSpacing((int) padding);
		gridView.setVerticalSpacing((int) padding);
	}

}
