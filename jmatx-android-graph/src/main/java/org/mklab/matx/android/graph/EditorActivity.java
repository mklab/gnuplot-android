package org.mklab.matx.android.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.mklab.matx.android.customView.AddPredictionMessage;
import org.mklab.matx.android.customView.PredictiveView;
import org.mklab.matx.android.graph.session.TermSession;
import org.mklab.matx.android.keyboard.CustomEditTextFunction;
import org.mklab.matx.android.keyboard.KeyboardListner;
import org.mklab.matx.android.keyboard.MyKeyboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.Typeface;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

/**
 * @author kawabata 　グラフを表示するアクティビティです
 */
public class EditorActivity extends Activity implements KeyboardListner,
		AddPredictionMessage {

	private Canvas _canvas = null;
	private Bitmap _bitmap = null;
	int _screenHeight;
	int _screenWidth;
	private int _textHeight;
	private int _textWidth;
	private int _x;
	private int _y;

	private List<String> history = new ArrayList<>();
	private int counter;
	private int historyIndex;
	// グローバル変数
	Globals globals;
	private LinearLayout mTerminalLayout;
	private ViewSwitcher mSwitcher;
	ScrollView mScrollView;
	EditText editText;
	// VIewの問題から最初に改行を挟んでおく（要改善）
	private String consoleLineString = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n"; //$NON-NLS-1$
	private String partialLine = ""; //$NON-NLS-1$
	int _linetype;
	int _linewidth;
	String _justMode = "LEFT"; //$NON-NLS-1$
	boolean _ready = false;
	boolean _plotDataPresent = false;
	String _plotData = ""; //$NON-NLS-1$
	private EditorActivity _sessionParent = null;
	private static final String BITMAP_MARK = "bitmapMarkGnuPlotMobile"; //$NON-NLS-1$
	int GRAPTH_SIZE = 0;
	TermSession mTermSession;
	private boolean _isCalledIntent = false;
	private MyKeyboard myKeyboard;
	private PredictiveView predictionView;
	float fontSize = 25;

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
	private static final int ASSETS_RESULT_CODE = 4321;

	@Override
	@SuppressWarnings("null")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("onActivityResult"); //$NON-NLS-1$
		System.out.println(requestCode);
		if (requestCode == FILE_RESULT_CODE) {
			Uri fileUri = (data != null ? (Uri) data.getData() : null);
			if (fileUri != null) { // Everything went well => edit the file
				String filePath = fileUri.getPath();
				Toast.makeText(this, filePath, Toast.LENGTH_SHORT).show();
				Log.d("OIFILE Log", "filepath = " + filePath); //$NON-NLS-1$ //$NON-NLS-2$
				Log.d("OIFILE Log", "Succes load file !! " + filePath); //$NON-NLS-1$ //$NON-NLS-2$
				String command = "load " + "\"" + filePath + "\""; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				this.history.add(command);
				this.counter++;
				this.historyIndex = this.counter;
				EditorActivity.this.mTermSession.write(command + "\n"); //$NON-NLS-1$

			} else { // Error occurred
				Toast.makeText(this, "No file found.", Toast.LENGTH_LONG).show(); //$NON-NLS-1$
			}
		} else if (requestCode == ASSETS_RESULT_CODE && data != null) {
			EditorActivity.this.mTermSession.write("reset \n"); //$NON-NLS-1$
			String filePath = Environment.getExternalStorageDirectory()
					+ "/GnuplotMobile/sample.gnu"; //$NON-NLS-1$
			String command = "load " + "\"" + filePath + "\""; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			EditorActivity.this.mTermSession.write(command + "\n"); //$NON-NLS-1$
			EditorActivity.this.mTermSession.write("reset \n"); //$NON-NLS-1$
		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editor);
		this.editText = (EditText) findViewById(R.id.editerEditText);
		this.editText.setBackgroundColor(Color.BLACK);
		this.editText.setTextColor(Color.WHITE);
		this.editText.setTextSize(this.fontSize);
		this.editTextList.add(this.editText);
		this.editText.requestFocus();
		this.predictionView = (PredictiveView) findViewById(R.id.CustomView);
		this.predictionView.setSize(300, 300);
		this.predictionView.setContext(this);
		this.predictionView.setfontsize(this.fontSize*2);

		methodNameLoader();
		commandNameLoader();

		setKeyboard();
		onNewIntent(getIntent());

	}

	private void openFileManeger() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*"); //$NON-NLS-1$
		startActivityForResult(intent, FILE_RESULT_CODE);
	}

	private void callAssetsActivity() {
		Intent intent = new Intent(this, AssetsActivity.class);
		startActivityForResult(intent, ASSETS_RESULT_CODE);
	}

	private void setKeyboard() {
		// this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		// my keyboard set
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		this.myKeyboard = (MyKeyboard) findViewById(R.id.myKeyboard);
		this.myKeyboard.setKeyboardLisner(this);
		setEditText(this.editText);
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
			this.predictionView.setLastCursorPoint(this.editText
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
		if (this.predictionView.getLastCursorPoint() + this.predictionStrCount != this.editText
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
				|| this.editText.getText().toString().isEmpty()) {
			resetPrediction();
		} else {
			this.predictionStrCount += 1;
			System.out.println(this.predictionStrCount);
			String pre = this.editText
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
		String frontStr = this.editText.getText().toString()
				.substring(0, this.predictionView.getLastCursorPoint());
		String backStr = this.editText
				.getText()
				.toString()
				.substring(this.editText.getSelectionEnd(),
						this.editText.getText().toString().length());
		this.editText.setText(frontStr + function + "()" + backStr); //$NON-NLS-1$
		this.editText.setSelection(lastCursorPoint + function.length() + 1);
		resetPrediction();
	}

	@Override
	public void addVariable(String var, int lastCursorPoint) {
		String frontStr = this.editText.getText().toString()
				.substring(0, this.predictionView.getLastCursorPoint());
		String backStr = this.editText
				.getText()
				.toString()
				.substring(this.editText.getSelectionEnd(),
						this.editText.getText().toString().length());
		this.editText.setText(frontStr + var + backStr);
		this.editText.setSelection(lastCursorPoint + var.length());
		resetPrediction();
	}

	@Override
	public void addCommand(String command, int lastCursorPoint) {
		String frontStr = this.editText.getText().toString()
				.substring(0, this.predictionView.getLastCursorPoint());
		String backStr = this.editText
				.getText()
				.toString()
				.substring(this.editText.getSelectionEnd(),
						this.editText.getText().toString().length());
		this.editText.setText(frontStr + command + " " + backStr); //$NON-NLS-1$
		this.editText.setSelection(lastCursorPoint + command.length() + 1);
		resetPrediction();
	}

	private void resetPrediction() {
		System.out.println("reset  " + this.editText.getSelectionStart()); //$NON-NLS-1$
		this.predictionView.setLastCursorPoint(this.editText
				.getSelectionStart());
		this.predictionView.clear();
		this.predictionStrCount = 0;
		prediction(""); //$NON-NLS-1$
	}

	@Override
	public void cerateMessage(String message) {
		this.editText.setText(message);
		Editable result = this.editText.getText();
		this.editText.setSelection(result.toString().length());
		resetPrediction();
	}

	private void setPrediction() {
		System.out.println("setPrediction"); //$NON-NLS-1$
		this.predictionView.setUpdateFunctiuonList(this.predictionFunctionList);
		this.predictionView.setUpdateCommandList(this.predictionCommandList);
		this.predictionView.setUpdateVariableList(this.predictionVariableList);
		this.predictionView.setCount(this.inputCount);
		// ビューを意図的に更新する
		this.editText.setWidth(this.editText.getWidth());
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
					int leftSize = (int) (c.length() * this.editText
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
			this.predictionView.setSize((int) widthSize,
					(int) (this.editText.getTextSize() * (top + 2) * 1.1));
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
