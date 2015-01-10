package org.mklab.matx.android.graph;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore.MediaColumns;
import android.provider.MediaStore.Images.Media;
import android.util.Log;

/**
 * @author kawabata グローバル変数を定義するクラスです
 */
public class Globals extends Application {
	public List<Bitmap> bitmaps = new ArrayList<>();
	private String startTime;

	public void setApplictionStartTime() {
		final Calendar calendar = Calendar.getInstance();

		final int year = calendar.get(Calendar.YEAR);
		final int month = calendar.get(Calendar.MONTH);
		final int day = calendar.get(Calendar.DAY_OF_MONTH);
		final int hour = calendar.get(Calendar.HOUR_OF_DAY);
		final int minute = calendar.get(Calendar.MINUTE);
		final int second = calendar.get(Calendar.SECOND);
		final int ms = calendar.get(Calendar.MILLISECOND);

		this.startTime = year + "" + (month + 1) + "" + day + ""  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				+ hour + "" + minute + "" + second; //$NON-NLS-1$ //$NON-NLS-2$
	}

	@SuppressWarnings("boxing")
	public void saveBitmap(Boolean[] isSelect) {
		for (int i = 0; i < isSelect.length; i++) {
			if (isSelect[i]) {
				createFolderSaveImage(bitmaps.get(i), i);
			}
		}
	}

	public void createFolderSaveImage(Bitmap imageToSave, int i) {
		createFolderSaveImage(imageToSave, this.startTime + "_" + (i + 1)); //$NON-NLS-1$
	}

	// 新規フォルダを作成し、画像ファイルを保存する
	public void createFolderSaveImage(Bitmap imageToSave, String fileName) {
		// 新しいフォルダへのパス
		String folderPath = Environment.getExternalStorageDirectory()
				+ "/GnuplotMobile/"; //$NON-NLS-1$
		File folder = new File(folderPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		// NewFolderに保存する画像のパス
		File file = new File(folder, fileName);
		if (file.exists()) {
			file.delete();
		}

		try {
			FileOutputStream out = new FileOutputStream(file);
			imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// これをしないと、新規フォルダは端末をシャットダウンするまで更新されない
			showFolder(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ContentProviderに新しいイメージファイルが作られたことを通知する
	@SuppressWarnings("boxing")
	private void showFolder(File path) throws Exception {
		try {
			ContentValues values = new ContentValues();
			ContentResolver contentResolver = getApplicationContext()
					.getContentResolver();
			values.put(MediaColumns.MIME_TYPE, "image/jpeg"); //$NON-NLS-1$
			values.put(MediaColumns.DATE_MODIFIED,
					System.currentTimeMillis() / 1000);
			values.put(MediaColumns.SIZE, path.length());
			values.put(MediaColumns.TITLE, path.getName());
			values.put(MediaColumns.DATA, path.getPath());
			contentResolver.insert(Media.EXTERNAL_CONTENT_URI, values);
		} catch (Exception e) {
			throw e;
		}
	}

}