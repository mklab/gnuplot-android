package org.mklab.matx.android.graph;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * @author kawabata グリッドビューのたのアダプタです
 */
public class ImageAdapter extends BaseAdapter {
	private Context mContext;

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
		return this.mThumbIds.length;
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
	@SuppressWarnings("boxing")
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

		imageView.setImageResource(this.mThumbIds[position]);
		return imageView;
	}

	// references to our images
	private Integer[] mThumbIds = { R.drawable.icon, R.drawable.matx3,
			R.drawable.test };
}
