package org.mklab.matx.android.graph;

import java.util.ArrayList;
import java.util.List;




import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author kawabata
 *
 */
public class GridActivity extends Activity {

	Globals globals;
	Boolean[] isSelectBmp;
	private Button saveButton;

	@SuppressWarnings("boxing")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid);
		// グローバル変数を取得
		this.globals = (Globals) this.getApplication();
		GridView gridview = (GridView)findViewById(R.id.gridview);
		this.saveButton = (Button) findViewById(R.id.saveButton);
		this.isSelectBmp = new Boolean[this.globals.bitmaps.size()];
		for (int i = 0; i < this.isSelectBmp.length; i++) {
			this.isSelectBmp[i] = false;
		}
		ImageAdapter imageAdapter = new ImageAdapter(this);
		imageAdapter.setBitmap(this.globals.bitmaps);
		gridview.setAdapter(imageAdapter);
		gridview.setNumColumns(2);

		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (view instanceof ImageView) {
					ImageView imageView = (ImageView) view;
					System.out.println(" SELECT IMAGE " + position);
					System.out.println(" select? "
							+ GridActivity.this.isSelectBmp[position]);
					if (GridActivity.this.isSelectBmp[position]) {
						GridActivity.this.isSelectBmp[position] = false;
						imageView.setColorFilter(null); 
					} else {
						GridActivity.this.isSelectBmp[position] = true;
						imageView.setColorFilter(Color.argb(120, 0, 0, 255));//Alpha100 Black Filter
					}
					System.out.println(" select? "
							+ GridActivity.this.isSelectBmp[position]);

				}
				// Toast.makeText(GridActivity.this, "" + position,
				// Toast.LENGTH_SHORT).show();
			}
		});
		
		this.saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GridActivity.this.globals.saveBitmap(GridActivity.this.isSelectBmp);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grid, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
