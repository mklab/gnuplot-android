package org.mklab.matx.android.graph;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.graphics.Bitmap;

/**
 * @author kawabata
 * グローバル変数を定義するクラスです
 */
public class Globals extends Application {
	public List<Bitmap> bitmaps = new ArrayList<>();
}