package org.mklab.matx.android.graph;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

/**
 * @author kawabata グリッドビューのたのアダプタです
 */
public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private List<Bitmap> bitmaps;

	/**
	 * コンストラクタです
	 * 
	 * @param c
	 */
	public ImageAdapter(Context c) {
		this.mContext = c;
	}

	@Override
	public int getCount() {
		return this.bitmaps.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	// Adapterから参照される新しいImageViewを作成
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) { // インスタンスが生成されていない場合、作成
			imageView = new ImageView(this.mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
		} else {
			imageView = (ImageView) convertView;
		}
		imageView.setImageBitmap(this.bitmaps.get(position));
		return imageView;
	}


	/**
	 * @param bitmaps
	 * GridViewに表示するbitmapのListをセットします
	 */
	public void setBitmap(List<Bitmap> bitmaps) {
		// TODO Auto-generated method stub
		this.bitmaps = bitmaps;
		System.out.println("GETBITMAP!! NUM= " + this.bitmaps.size());
	}
}
