package org.mklab.matx.android.graph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.mklab.matx.android.customView.AddPredictionMessage;
import org.mklab.matx.android.customView.PredictiveView;
import org.mklab.matx.android.graph.session.TermSession;
import org.mklab.matx.android.keyboard.CustomEditTextFunction;
import org.mklab.matx.android.keyboard.KeyboardListner;
import org.mklab.matx.android.keyboard.MyKeyboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

/**
 * @author kawabata 　グラフを表示するアクティビティです
 */
public class EditorActivity extends Activity implements KeyboardListner,
		AddPredictionMessage {

	int _screenHeight;
	int _screenWidth;
	// グローバル変数
	Globals globals;
	private ViewSwitcher mSwitcher;
	ScrollView mScrollView;
	static EditText editText;
	int _linetype;
	int _linewidth;
	String _justMode = "LEFT"; //$NON-NLS-1$
	boolean _ready = false;
	boolean _plotDataPresent = false;
	String _plotData = ""; //$NON-NLS-1$
	int GRAPTH_SIZE = 0;
	TermSession mTermSession;
	MyKeyboard myKeyboard;
	private PredictiveView predictionView;
	float fontSize = 25;
	private String filePath = Environment.getExternalStorageDirectory()
			+ "/GnuplotMobile/"; //$NON-NLS-1$

	List<EditText> editTextList = new ArrayList<>();
	private int predictionStrCount;

	private int inputCount;
	private List<String> predictionVariableList = new ArrayList<>();
	private List<String> predictionFunctionList = new ArrayList<>();
	private List<String> predictionCommandList = new ArrayList<>();
	private List<String> commandList = new ArrayList<>();
	private List<String> methodNameList = new ArrayList<>();
	private int prefontsize = 15;
	protected int mTouchX;
	protected int mTouchY;
	private static final int FILE_RESULT_CODE = 54321;
	static final int CONTEXT_MENU1_ID = 0;
	static final int CONTEXT_MENU2_ID = 1;
	private static final int MENU_LOAD_KEY = 1;
	private static final int MENU_SAVE_KEY = 2;
	private static final int MENU_CONSOLE_KEY = 3;
	private TextView textView;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("onActivityResult"); //$NON-NLS-1$
		System.out.println(requestCode);
		if (requestCode == FILE_RESULT_CODE) {
			Uri fileUri = (data != null ? (Uri) data.getData() : null);
			if (fileUri != null) { // Everything went well => edit the file
				this.filePath = fileUri.getPath();
				Toast.makeText(this, this.filePath, Toast.LENGTH_SHORT).show();
				Log.d("OIFILE Log", "filepath = " + this.filePath); //$NON-NLS-1$ //$NON-NLS-2$
				Log.d("OIFILE Log", "Succes load file !! " + this.filePath); //$NON-NLS-1$ //$NON-NLS-2$
				loadScroiptFile(this.filePath);
				this.textView.setText(this.filePath);
			} else { // Error occurred
				Toast.makeText(this, "No file found.", Toast.LENGTH_LONG).show(); //$NON-NLS-1$
			}
		}
	}

	// 書き込みメソッド
	/**
	 * @param filePath
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static void saveText(String filePath) throws IOException {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
		file.getParentFile().mkdir();

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file, true);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8"); //$NON-NLS-1$
			BufferedWriter bw = new BufferedWriter(osw);
			String str = editText.getText().toString();
			System.out.println(filePath);
			System.out.println("WRITE * \n" + str); //$NON-NLS-1$
			bw.write(str);
			bw.flush();
			bw.close();
		} catch (Exception e) {
		}

	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editor);
		EditorActivity.editText = (EditText) findViewById(R.id.editerEditText);
		EditorActivity.editText.setBackgroundColor(Color.BLACK);
		EditorActivity.editText.setTextColor(Color.WHITE);
		EditorActivity.editText.setTextSize(this.fontSize);
		this.editTextList.add(EditorActivity.editText);
		EditorActivity.editText.requestFocus();
		this.predictionView = (PredictiveView) findViewById(R.id.CustomView);
		this.predictionView.setSize(300, 300);
		this.predictionView.setContext(this);
		this.predictionView.setfontsize(this.fontSize * 2);
		this.textView = (TextView) findViewById(R.id.fileNameText);
		this.textView.setText(this.filePath);
		methodNameLoader();
		commandNameLoader();

		setKeyboard();
		onNewIntent(getIntent());

	}

	// オプションメニューが最初に呼び出される時に1度だけ呼び出されます
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, MENU_LOAD_KEY, 0, "Load Script File").setShowAsAction( //$NON-NLS-1$
				MenuItem.SHOW_AS_ACTION_NEVER);
		menu.add(0, MENU_SAVE_KEY, 0, "Save Script File").setShowAsAction( //$NON-NLS-1$
				MenuItem.SHOW_AS_ACTION_NEVER);
		menu.add(0, MENU_CONSOLE_KEY, 0, "Move Console").setShowAsAction( //$NON-NLS-1$
				MenuItem.SHOW_AS_ACTION_NEVER);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_LOAD_KEY:
			openFileManeger();
			return true;
		case MENU_SAVE_KEY:
			writeScriptFile();
			return true;
		case MENU_CONSOLE_KEY:
			callConsoleActivity();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	private void openFileManeger() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*"); //$NON-NLS-1$
		startActivityForResult(intent, FILE_RESULT_CODE);
	}

	private void writeScriptFile() {
		// TODO Auto-generated method stub
		// テキスト入力を受け付けるビューを作成します。
		final EditText editView = new EditText(this);
		editView.setText(this.filePath);
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setTitle("Please input save file path") //$NON-NLS-1$
				// setViewにてビューを設定します。
				.setView(editView)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() { //$NON-NLS-1$
							@Override
							public void onClick(DialogInterface dialog,
									int whichButton) {
								try {
									saveText(editView.getText().toString());
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}).setNegativeButton("Cancel", //$NON-NLS-1$
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int whichButton) {
							}
						}).show();
	}

	private void callConsoleActivity() {
		finish();
		// Intent intent = new Intent(this, MainActivity.class);
		// startActivityForResult(intent, ASSETS_RESULT_CODE);
	}

	public void loadScroiptFile(String filePath) {
		try {
			EditorActivity.editText.setText(readText(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 読み込みメソッド
	/**
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String readText(String path) throws IOException {
		@SuppressWarnings("resource")
		FileInputStream in = new FileInputStream(new File(path));
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuffer sb = new StringBuffer();
		String sbs;
		while ((sbs = reader.readLine()) != null) {
			sb.append(sbs);
			sb.append("\n"); //$NON-NLS-1$
		}
		in.close();
		System.out.println(sb.toString());
		return sb.toString();
	}

	private void setKeyboard() {
		// this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		// my keyboard set
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		this.myKeyboard = (MyKeyboard) findViewById(R.id.myKeyboard);
		this.myKeyboard.setKeyboardLisner(this);
		setEditText(EditorActivity.editText);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		resetPrediction();
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (this._plotDataPresent) {
				if (this.mSwitcher.getDisplayedChild() != 1) {
					this.mSwitcher.showPrevious();
					return true;
				} else {
					return super.onKeyDown(keyCode, event);
				}
			} else if (this.mSwitcher.getDisplayedChild() != 0) {
				this.mSwitcher.showPrevious();
				return true;
			} else {
				return super.onKeyDown(keyCode, event);
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onPress(int primaryCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRelease(int primaryCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onKey(int primaryCode, int[] keyCodes) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onText(CharSequence text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void swipeLeft() {
		// TODO Auto-generated method stub

	}

	@Override
	public void swipeRight() {
		// TODO Auto-generated method stub

	}

	@Override
	public void swipeDown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void swipeUp() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		System.out.println("Keyevent is " + event); //$NON-NLS-1$
		System.out.println("Keyevent is " + event.getKeyCode()); //$NON-NLS-1$

		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (hideMyKeyboard() != 0) {
				System.out.println("no!!"); //$NON-NLS-1$
				return true;
			}
			System.out.println("back!!"); //$NON-NLS-1$
		}
		return super.dispatchKeyEvent(event);
	}

	private int hideMyKeyboard() {
		int visibility = this.myKeyboard.getVisibility();
		switch (visibility) {
		case View.GONE:
			return 0;
		case View.VISIBLE:
			this.myKeyboard.setVisibility(View.GONE);
			return 1;
		default:
			break;
		}
		return 0;
	}

	@Override
	public void sendKeyDown(int key_code) {
		// Log.d("CONSOLE_FRAGMENT", "onOnKeyDown " + key_code);
		if (getFocusIsEnabledEditText() != null) {
			resetPrediction();
			CustomEditTextFunction.sendKeyCode(getFocusIsEnabledEditText(),
					key_code);
		}
		// if mouse move
		if (key_code < 0) {
			this.predictionView.setLastCursorPoint(EditorActivity.editText
					.getSelectionEnd());
		}
	}

	private EditText getFocusIsEnabledEditText() {
		for (EditText editText : this.editTextList) {
			System.out.println("focus is " + editText.hasFocus()); //$NON-NLS-1$
			if (editText.findFocus() != null)
				return editText;
		}
		return null;
	}

	@Override
	public void sendInputText(String inputText) {
		if (this.predictionView.getLastCursorPoint() + this.predictionStrCount != EditorActivity.editText
				.getSelectionEnd()) {
			resetPrediction();
		}
		if (inputText.equals("")) { //$NON-NLS-1$
			resetPrediction();
			return;
		}

		this.predictionView.clear();
		this.inputCount = inputText.length();
		Log.d("EDITOR", "input text is " + inputText); //$NON-NLS-1$ //$NON-NLS-2$
		if (getFocusIsEnabledEditText() != null)
			CustomEditTextFunction.insertText(getFocusIsEnabledEditText(),
					inputText);
		if (inputText.length() > 1 || this.symbols.indexOf(inputText) > 0
				|| EditorActivity.editText.getText().toString().isEmpty()) {
			resetPrediction();
		} else {
			this.predictionStrCount += 1;
			System.out.println(this.predictionStrCount);
			String pre = EditorActivity.editText
					.getText()
					.toString()
					.substring(
							this.predictionView.getLastCursorPoint(),
							this.predictionStrCount
									+ this.predictionView.getLastCursorPoint());
			System.out.println(pre);
			prediction(pre);
		}
		setPrediction();
	}

	private void setEditText(EditText editText) {
		this.editTextList.add(editText);
		hideKeyboard(this.editTextList, this);
	}

	/**
	 * デフォルトのキーボードを隠します
	 * 
	 * @param editTexts
	 * @param activity
	 */
	public void hideKeyboard(List<EditText> editTexts, Activity activity) {
		System.out.println("hideKeyboard"); //$NON-NLS-1$
		InputMethodManager inputMethodManager = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		for (final EditText editText : editTexts) {
			inputMethodManager.hideSoftInputFromWindow(
					editText.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
			editText.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					v.onTouchEvent(event);
					InputMethodManager imm = (InputMethodManager) v
							.getContext().getSystemService(
									Context.INPUT_METHOD_SERVICE);
					Log.d("KEYBOARD", "VISIBLE"); //$NON-NLS-1$ //$NON-NLS-2$
					if (imm != null) {
						imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
					}
					EditorActivity.this.myKeyboard.setVisibility(View.VISIBLE);
					if (!editText.hasFocus()) {
						switch (editText.getInputType()) {
						case InputType.TYPE_CLASS_NUMBER:
							EditorActivity.this.myKeyboard
									.setKeyboard(new Keyboard(
											EditorActivity.this, R.xml.symbols));
							break;
						case InputType.TYPE_CLASS_TEXT:
							EditorActivity.this.myKeyboard
									.setKeyboard(new Keyboard(
											EditorActivity.this, R.xml.qwerty));
							break;
						default:
							EditorActivity.this.myKeyboard
									.setKeyboard(new Keyboard(
											EditorActivity.this, R.xml.qwerty));

							break;
						}
					}
					return true;
				}
			});
		}
	}

	@Override
	public void addFunction(String function, int lastCursorPoint) {
		String frontStr = EditorActivity.editText.getText().toString()
				.substring(0, this.predictionView.getLastCursorPoint());
		String backStr = EditorActivity.editText
				.getText()
				.toString()
				.substring(EditorActivity.editText.getSelectionEnd(),
						EditorActivity.editText.getText().toString().length());
		EditorActivity.editText.setText(frontStr + function + "()" + backStr); //$NON-NLS-1$
		EditorActivity.editText.setSelection(lastCursorPoint
				+ function.length() + 1);
		resetPrediction();
	}

	@Override
	public void addVariable(String var, int lastCursorPoint) {
		String frontStr = EditorActivity.editText.getText().toString()
				.substring(0, this.predictionView.getLastCursorPoint());
		String backStr = EditorActivity.editText
				.getText()
				.toString()
				.substring(EditorActivity.editText.getSelectionEnd(),
						EditorActivity.editText.getText().toString().length());
		EditorActivity.editText.setText(frontStr + var + backStr);
		EditorActivity.editText.setSelection(lastCursorPoint + var.length());
		resetPrediction();
	}

	@Override
	public void addCommand(String command, int lastCursorPoint) {
		String frontStr = EditorActivity.editText.getText().toString()
				.substring(0, this.predictionView.getLastCursorPoint());
		String backStr = EditorActivity.editText
				.getText()
				.toString()
				.substring(EditorActivity.editText.getSelectionEnd(),
						EditorActivity.editText.getText().toString().length());
		EditorActivity.editText.setText(frontStr + command + " " + backStr); //$NON-NLS-1$
		EditorActivity.editText.setSelection(lastCursorPoint + command.length()
				+ 1);
		resetPrediction();
	}

	private void resetPrediction() {
		System.out
				.println("reset  " + EditorActivity.editText.getSelectionStart()); //$NON-NLS-1$
		this.predictionView.setLastCursorPoint(EditorActivity.editText
				.getSelectionStart());
		this.predictionView.clear();
		this.predictionStrCount = 0;
		prediction(""); //$NON-NLS-1$
	}

	@Override
	public void cerateMessage(String message) {
		EditorActivity.editText.setText(message);
		Editable result = EditorActivity.editText.getText();
		EditorActivity.editText.setSelection(result.toString().length());
		resetPrediction();
	}

	private void setPrediction() {
		System.out.println("setPrediction"); //$NON-NLS-1$
		this.predictionView.setUpdateFunctiuonList(this.predictionFunctionList);
		this.predictionView.setUpdateCommandList(this.predictionCommandList);
		this.predictionView.setUpdateVariableList(this.predictionVariableList);
		this.predictionView.setCount(this.inputCount);
		// ビューを意図的に更新する
		EditorActivity.editText.setWidth(EditorActivity.editText.getWidth());
	}

	private void prediction(String input) {
		System.out.println("prediction"); //$NON-NLS-1$
		this.predictionVariableList.clear();
		this.predictionFunctionList.clear();
		this.predictionCommandList.clear();
		int predictionCount = 0;
		int left = 0;
		int top = 0;
		// ウィンドウマネージャのインスタンス取得
		WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		// ディスプレイのインスタンス生成
		Display disp = wm.getDefaultDisplay();

		float widthSize = disp.getWidth();

		if (!input.equals("")) { //$NON-NLS-1$
			for (final String c : this.commandList) {
				if (c.startsWith(input)) {
					this.predictionCommandList.add(c);
					int leftSize = (int) (c.length() * EditorActivity.editText
							.getTextSize());
					// 二段目に移動させる
					if ((left + leftSize) > widthSize) {
						top += 1;
						left = 0;
					}
					left += leftSize;
					predictionCount += 1;
				}
			}
			for (final String c : this.methodNameList) {
				if (c.startsWith(input)) {
					this.predictionFunctionList.add(c);
					int leftSize = c.length() * this.prefontsize;
					// 二段目に移動させる
					if ((left + leftSize) > widthSize) {
						top += 1;
						left = 0;
					}
					left += leftSize;
					predictionCount += 1;
				}
			}
		}
		if (predictionCount > 0) {
			this.predictionView
					.setSize((int) widthSize,
							(int) (EditorActivity.editText.getTextSize()
									* (top + 2) * 1.1));
		} else {
			this.predictionView.setSize(0, 0);
		}
	}

	private void methodNameLoader() {
		AssetManager as = getResources().getAssets();
		InputStream st = null;
		try {
			st = as.open("functions.csv"); //$NON-NLS-1$
			byte[] buffer = new byte[st.available()];
			while ((st.read(buffer)) != -1) {
			}
			String s = new String(buffer);// この中にテキストの内容が入る
			String[] temp = s.split("\n"); //$NON-NLS-1$
			for (int i = 0; i < temp.length; i++) {

				String[] temp2 = temp[i].split(","); //$NON-NLS-1$
				for (int j = 0; j < temp2.length; j++) {
					this.methodNameList.add(temp2[j]);
				}

			}

		} catch (IOException e) {
		} finally {
			try {
				st.close();
			} catch (IOException e2) {
			}
		}
		System.out.println("load " + this.methodNameList); //$NON-NLS-1$
		resetPrediction();
	}

	private void commandNameLoader() {
		AssetManager as = getResources().getAssets();
		InputStream st = null;
		try {
			st = as.open("commands.csv"); //$NON-NLS-1$
			byte[] buffer = new byte[st.available()];
			while ((st.read(buffer)) != -1) {
			}
			String s = new String(buffer);// この中にテキストの内容が入る
			String[] temp = s.split("\n"); //$NON-NLS-1$
			for (int i = 0; i < temp.length; i++) {

				String[] temp2 = temp[i].split(","); //$NON-NLS-1$
				for (int j = 0; j < temp2.length; j++) {
					this.commandList.add(temp2[j]);
				}

			}

		} catch (IOException e) {
		} finally {
			try {
				st.close();
			} catch (IOException e2) {
			}
		}
		System.out.println("load " + this.methodNameList); //$NON-NLS-1$
		resetPrediction();

	}

	private List<String> symbols = new ArrayList<String>(38) {
		{
			add(" "); //$NON-NLS-1$
			add("\""); //$NON-NLS-1$
			add("!"); //$NON-NLS-1$
			add("#"); //$NON-NLS-1$
			add("$"); //$NON-NLS-1$
			add("%"); //$NON-NLS-1$
			add("&"); //$NON-NLS-1$
			add("'"); //$NON-NLS-1$
			add("("); //$NON-NLS-1$
			add(")"); //$NON-NLS-1$
			add("="); //$NON-NLS-1$
			add("~"); //$NON-NLS-1$
			add("^"); //$NON-NLS-1$
			add("+"); //$NON-NLS-1$
			add("-"); //$NON-NLS-1$
			add("|"); //$NON-NLS-1$
			add("\\"); //$NON-NLS-1$
			add("{"); //$NON-NLS-1$
			add("}"); //$NON-NLS-1$
			add("["); //$NON-NLS-1$
			add("]"); //$NON-NLS-1$
			add("*"); //$NON-NLS-1$
			add("/"); //$NON-NLS-1$
			add(";"); //$NON-NLS-1$
			add(":"); //$NON-NLS-1$
			add("."); //$NON-NLS-1$
			add(","); //$NON-NLS-1$
			add("?"); //$NON-NLS-1$
			add("1"); //$NON-NLS-1$
			add("2"); //$NON-NLS-1$
			add("3"); //$NON-NLS-1$
			add("4"); //$NON-NLS-1$
			add("5"); //$NON-NLS-1$
			add("6"); //$NON-NLS-1$
			add("7"); //$NON-NLS-1$
			add("8"); //$NON-NLS-1$
			add("9"); //$NON-NLS-1$
			add("0"); //$NON-NLS-1$
		}
	};

}
