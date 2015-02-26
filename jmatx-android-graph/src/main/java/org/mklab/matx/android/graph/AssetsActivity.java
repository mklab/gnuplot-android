package org.mklab.matx.android.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 
 * assetsの中身を表示し、選択したファイルをストレージに保存するアクティビティです
 * 
 * @author kawabata
 *
 */
public class AssetsActivity extends Activity {

	ListView listView;
	String correntPath = "sample"; //$NON-NLS-1$
	private AssetManager assetManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assets);
		this.assetManager = getResources().getAssets();
		this.listView = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_1,
				loadAssetsFolder(this.correntPath));
		this.listView.setAdapter(adapter);

		// リスト項目がクリックされた時の処理
		this.listView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String item = (String) AssetsActivity.this.listView
								.getItemAtPosition(position);
						String nextFile = AssetsActivity.this.correntPath
								+ File.separator + item;
						String[] nextFileList = loadAssetsFolder(nextFile);
						if (nextFileList.length > 0) {
							AssetsActivity.this.correntPath = nextFile;
							AssetsActivity.this.listView
									.setAdapter(new ArrayAdapter<>(
											AssetsActivity.this,
											android.R.layout.simple_list_item_1,
											nextFileList));
						} else {
							copyAssetsFiles(
									AssetsActivity.this.correntPath,
									item,
									new File(Environment
											.getExternalStorageDirectory()
											+ File.separator + "GnuplotMobile")); //$NON-NLS-1$
						}
					}
				});
	}

	String[] loadAssetsFolder(String folderName) {
		String[] files = null;
		ArrayList<String> fileList = new ArrayList<>();
		try {
			files = this.assetManager.list(folderName);
			fileList = new ArrayList<>(Arrays.asList(files));
			Collections.sort(fileList);
			Collections.sort(fileList, new NomComparator());
			files = (String[]) fileList.toArray(new String[fileList.size()]);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return files;
	}

	public class NomComparator implements Comparator<String> {

		// 比較メソッド（データクラスを比較して-1, 0, 1を返すように記述する）
		public int compare(String a, String b) {
			String[] aList = a.split(".");
			String[] bList = b.split(".");
			System.out.println("A :" + a);
			System.out.println("aList" + aList.length);

			System.out.println(a.lastIndexOf("."));
			if (a.lastIndexOf(".") > 0) {
				System.out.println();
			}

			if (a.lastIndexOf(".") > 0 && b.lastIndexOf(".") > 0) {
				@SuppressWarnings("boxing")
				int no1 = Integer.valueOf((String) a.subSequence(
						a.indexOf(".") + 1, a.lastIndexOf(".")));
				@SuppressWarnings("boxing")
				int no2 = Integer.valueOf((String) b.subSequence(
						a.indexOf(".") + 1, b.lastIndexOf(".")));
				System.out.println("NO1:no2" + no1 + " : " + no2);
				if (no1 > no2) {
					return 1;

				} else if (no1 == no2) {
					return 0;

				} else {
					return -1;

				}

			}
			return 0;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (this.correntPath.lastIndexOf(File.separator) > 0) { // NON-NLS-1$
				this.correntPath = this.correntPath.substring(0,
						this.correntPath.lastIndexOf(File.separator));
				this.listView.setAdapter(new ArrayAdapter<>(
						AssetsActivity.this,
						android.R.layout.simple_list_item_1,
						loadAssetsFolder(this.correntPath)));
			} else {
				finish();
			}
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}

	@SuppressWarnings("resource")
	String loadAssetsFile(String filePath) {
		InputStream is;
		BufferedReader br = null;
		String text = ""; //$NON-NLS-1$
		try {
			is = this.assetManager.open(filePath);
			br = new BufferedReader(new InputStreamReader(is));
			String str;
			while ((str = br.readLine()) != null) {
				text += str + "\n"; //$NON-NLS-1$
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}

	@SuppressWarnings("resource")
	void copyAssetsFiles(final String parentPath, final String filename,
			File folder) {
		if (!folder.exists()) {
			folder.mkdirs();
		}
		File file = new File(folder, "sample.gnu");
		if (file.exists()) {
			file.delete();
		}
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int length = 0;
			InputStream inputStream = getAssets().open(
					parentPath + File.separator + filename);
			while ((length = inputStream.read(buffer)) >= 0) {
				fileOutputStream.write(buffer, 0, length);
			}
			fileOutputStream.close();
			inputStream.close();

			finishActivity();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void finishActivity() {
		// 返すデータ(Intent&Bundle)の作成
		Intent data = new Intent();
		Bundle bundle = new Bundle();
		// bundle.putString("key.StringData", "送り返す文字列");
		data.putExtras(bundle);
		setResult(RESULT_OK, data);
		finish();
	}

}
