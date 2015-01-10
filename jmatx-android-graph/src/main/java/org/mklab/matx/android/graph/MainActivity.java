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
import android.content.ContentResolver;
import android.content.ContentValues;
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
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.MediaColumns;
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
import android.widget.ViewSwitcher;

/**
 * @author kawabata 　グラフを表示するアクティビティです
 */
public class MainActivity extends Activity implements KeyboardListner,
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
	//グローバル変数
    Globals globals;
	private LinearLayout mTerminalLayout;
	private ViewSwitcher mSwitcher;
	TextView mTextView;
	TextView promptTextView;
	ScrollView mScrollView;
	EditText editText;
	// VIewの問題から最初に開業を挟んでおく（要改善）
	private String consoleLineString = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n"; //$NON-NLS-1$
	private String partialLine = ""; //$NON-NLS-1$
	int _linetype;
	int _linewidth;
	String _justMode = "LEFT"; //$NON-NLS-1$
	boolean _ready = false;
	boolean _plotDataPresent = false;
	String _plotData = ""; //$NON-NLS-1$
	private MainActivity _sessionParent = null;
	private static final String BITMAP_MARK = "bitmapMarkGnuPlotMobile"; //$NON-NLS-1$
	int GRAPTH_SIZE = 0;
	TermSession mTermSession;
	private boolean _isCalledIntent = false;
	private MyKeyboard myKeyboard;
	private PredictiveView predictionView;
	float fontSize = 20;

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

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		System.out.println("onConfigurationChanged"); //$NON-NLS-1$
		// setContentView(R.layout.main);
		// my keyboard set
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		this.myKeyboard.init();
		this.myKeyboard.setKeyboardLisner(this);
		setGrapthSize();
		this.editText.requestFocus();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		setGrapthSize();
	}

	private void setGrapthSize() {
		LinearLayout layout = (LinearLayout) findViewById(R.id.terminalLayout);
		if (layout.getHeight() > layout.getWidth()) {
			this.GRAPTH_SIZE = layout.getWidth();
		} else {
			this.GRAPTH_SIZE = layout.getHeight();
		}
	}

	static final int CONTEXT_MENU1_ID = 0;
	static final int CONTEXT_MENU2_ID = 1;

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		super.onCreateContextMenu(menu, v, menuInfo);

		// コンテキストメニューの設定
		menu.setHeaderTitle("メニュータイトル");
		// Menu.add(int groupId, int itemId, int order, CharSequence title)
		menu.add(0, CONTEXT_MENU1_ID, 0, "メニュー1");
		menu.add(0, CONTEXT_MENU2_ID, 0, "メニュー2");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();

		switch (item.getItemId()) {
		case CONTEXT_MENU1_ID:
			  Intent intent = new Intent(this, GridActivity.class);
			  intent.setAction(Intent.ACTION_VIEW);
			  startActivity(intent);

			return true;
		case CONTEXT_MENU2_ID:
			// TODO:メニュー押下時の操作
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("onCreate"); //$NON-NLS-1$
		setContentView(R.layout.main);
		//グローバル変数を取得
        this.globals = (Globals) this.getApplication();
        this.globals.setApplictionStartTime();
		this.mTerminalLayout = (LinearLayout) findViewById(R.id.terminalLayout);

		this.mTextView = (TextView) findViewById(R.id.termWindow);
		this.mTextView.setTextIsSelectable(true);
		registerForContextMenu(this.mTextView);
		// mTextView.setOnTouchListener(new View.OnTouchListener() {
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// mTouchX = (int) event.getX();
		// mTouchY = (int) event.getY();
		// return false;
		// }
		// });
		this.mTextView
				.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

					@Override
					public boolean onPrepareActionMode(ActionMode mode,
							Menu menu) {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public void onDestroyActionMode(ActionMode mode) {
						// TODO Auto-generated method stub

					}

					@Override
					public boolean onCreateActionMode(ActionMode mode, Menu menu) {
						menu.removeItem(android.R.id.paste);
						menu.removeItem(android.R.id.cut);
						menu.removeItem(android.R.id.copy);
						// 順にペースト、カット、コピーのメニューアイテムを削除
						MenuItem saveItem = menu.add(Menu.NONE, 123, Menu.NONE,
								"Mark Text"); //$NON-NLS-1$
						// メニュー追加
						saveItem.setIcon(R.drawable.matx3);
						// アイコン設定
						return true;
					}

					@SuppressWarnings("boxing")
					@Override
					public boolean onActionItemClicked(ActionMode mode,
							MenuItem item) {
						String text = MainActivity.this.mTextView.getText()
								.toString();

						// 選択位置のはじめと終わりを取得
						int start = 0, end = 0;
						if (MainActivity.this.mTextView.isFocused()) {
							start = MainActivity.this.mTextView
									.getSelectionStart();
							end = MainActivity.this.mTextView.getSelectionEnd();
						}
						switch (item.getItemId()) {
						case 123:
							if (start < end) {
								System.out.println("TARGET : "
										+ text.substring(start, end));
								String beforeText = text
										.substring(0, start + 1);
								String targetText = text.substring(start, end);
								int beforeLineCount = 0;
								int targetLineCount = 0;
								int s = 0;
								String line = "\n";
								String[] beforeLines = beforeText.split(line);
								String[] targetLines = targetText.split(line);
								String[] consoleLines = MainActivity.this.consoleLineString
										.split(line);
								for (String st : beforeLines) {
									beforeLineCount++;
								}
								if (beforeLineCount > 0)
									beforeLineCount--;
								for (String st : targetLines) {
									targetLineCount++;
								}

								System.out.println("BEFORE LINE COUNT = "
										+ beforeLineCount);
								System.out.println("TARGET LINE COUNT = "
										+ targetLineCount);
								String selectText = MainActivity.this.consoleLineString
										.substring(start, end);
								System.out.println("SELECT TEXT = ");

								for (int i = 0; i < targetLineCount; i++) {
									if (consoleLines[i + beforeLineCount]
											.indexOf(BITMAP_MARK) != -1) {
										String[] bitmapMarks = consoleLines[i
												+ beforeLineCount].split(" ");
										System.out.println("S.O! Crue!! "
												+ bitmapMarks[1]);
										MainActivity.this.globals.createFolderSaveImage(
												MainActivity.this.globals.bitmaps
														.get(Integer
																.valueOf(bitmapMarks[1])),
												"TESTTEST" + bitmapMarks[1]);
									}
									System.out
											.println(i
													+ "  line  "
													+ consoleLines[i
															+ beforeLineCount]);
								}
							}

							return true;
						default:
							break;
						}

						return false;
					}
				});

		this.mScrollView = (ScrollView) findViewById(R.id.scrollView);

		this.editText = (EditText) findViewById(R.id.edit_command);
		this.editText.requestFocus();

		this.mSwitcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);

		this.promptTextView = (TextView) findViewById(R.id.prompt);

		this.predictionView = (PredictiveView) findViewById(R.id.CustomView);
		this.predictionView.setSize(300, 300);
		this.predictionView.setContext(this);
		this.predictionView.setfontsize(this.fontSize);

		methodNameLoader();
		commandNameLoader();

		this._sessionParent = this;

		ViewTreeObserver viewTreeObserver = this.mTerminalLayout
				.getViewTreeObserver();
		if (viewTreeObserver.isAlive()) {
			System.out.println("viewTree"); //$NON-NLS-1$
			viewTreeObserver
					.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
						@Override
						public void onGlobalLayout() {
							MainActivity.this.mTerminalLayout
									.getViewTreeObserver()
									.removeGlobalOnLayoutListener(this);
							MainActivity.this._screenHeight = MainActivity.this.mTerminalLayout
									.getHeight();
							MainActivity.this._screenWidth = MainActivity.this.mTerminalLayout
									.getWidth();
							if (MainActivity.this._screenWidth < MainActivity.this._screenHeight) {
								MainActivity.this._screenHeight = MainActivity.this._screenWidth;
							} else {
								MainActivity.this._screenWidth = MainActivity.this._screenHeight;
							}
							// Converts 14 dip into its equivalent px
							Resources r = getResources();
							MainActivity.this._textHeight = (int) TypedValue
									.applyDimension(
											TypedValue.COMPLEX_UNIT_DIP, 14,
											r.getDisplayMetrics());
							Paint paint = new Paint();
							paint.setStyle(Paint.Style.STROKE);
							paint.setTextSize(MainActivity.this._textHeight);
							paint.setTypeface(Typeface.MONOSPACE);
							MainActivity.this._textWidth = (int) paint
									.measureText("A"); //$NON-NLS-1$
							System.out.println("Create BitMap"); //$NON-NLS-1$
							MainActivity.this._bitmap = Bitmap.createBitmap(
									MainActivity.this._screenWidth,
									MainActivity.this._screenHeight,
									Bitmap.Config.ARGB_8888);
							System.out.println(MainActivity.this._bitmap);
							MainActivity.this._canvas = new Canvas(
									MainActivity.this._bitmap);

							MainActivity.this.mTermSession = new TermSession(
									MainActivity.this._sessionParent);
							MainActivity.this.mTermSession.updateSize(1024,
									1024);

							if (MainActivity.this._canvas != null) {
								Log.d("GRAPH IMAGE", MainActivity.this._canvas.toString()); //$NON-NLS-1$
							} else {
								Log.d("GRAPH IMAGE", "CANVAS NULL"); //$NON-NLS-1$ //$NON-NLS-2$
							}

						}

					});
		}
		setKeyboard();
		onNewIntent(getIntent());

	}


	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		this._ready = false;
		this._plotData = intent.getStringExtra("plotData"); //$NON-NLS-1$

		this._plotDataPresent = false;
		if (this._plotData != null && this._plotData.length() > 0) {
			this._isCalledIntent = true;
			// mPlotLayout.setVisibility(View.INVISIBLE);
			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			String fileName = "tempPlotData.csv"; //$NON-NLS-1$
			OutputStreamWriter out;
			try {
				out = new OutputStreamWriter(openFileOutput(fileName,
						Context.MODE_WORLD_READABLE
								| Context.MODE_WORLD_WRITEABLE));
				this._plotData = this._plotData.replaceAll(";", "\n"); //$NON-NLS-1$ //$NON-NLS-2$
				out.write(this._plotData);
				out.flush();
				out.close();
				this._plotDataPresent = true;
			} catch (Exception e) {
			}
		}

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

	public void scrollToBottom() {
		this.mScrollView.post(new Runnable() {
			@Override
			public void run() {
				MainActivity.this.mScrollView.smoothScrollTo(0,
						MainActivity.this.mTextView.getBottom());
				if (MainActivity.this._ready == false
						&& MainActivity.this.partialLine.startsWith("gnuplot>")) { // prompt //$NON-NLS-1$
					// is
					// up
					MainActivity.this._ready = true;
					String fileName = "startup.p"; //$NON-NLS-1$
					OutputStreamWriter out;
					try {
						out = new OutputStreamWriter(openFileOutput(fileName,
								Context.MODE_WORLD_READABLE
										| Context.MODE_WORLD_WRITEABLE));
						out.write("set term android size " //$NON-NLS-1$
								+ Integer
										.toString(MainActivity.this._screenWidth)
								+ "," //$NON-NLS-1$
								+ Integer
										.toString(MainActivity.this._screenHeight)
								+ " charsize " + Integer.toString(MainActivity.this._textWidth) //$NON-NLS-1$
								+ "," + Integer.toString(MainActivity.this._textHeight) //$NON-NLS-1$
								+ " ticsize " + Integer.toString(MainActivity.this._textWidth) //$NON-NLS-1$
								+ "," + Integer.toString(MainActivity.this._textWidth) + "\n"); //$NON-NLS-1$ //$NON-NLS-2$

						if (MainActivity.this._plotDataPresent == true) {
							// if (false) {

							MainActivity.this._plotData = MainActivity.this._plotData
									.replaceAll(";", "\n"); //$NON-NLS-1$ //$NON-NLS-2$
							String lines[] = MainActivity.this._plotData
									.split("\\n"); //$NON-NLS-1$
							String lines2[] = lines[0].split(","); //$NON-NLS-1$
							int lineCount = (lines2.length + 1) / 2;
							out.write("set datafile separator \",\" \n"); //$NON-NLS-1$
							out.write("set nokey \n"); //$NON-NLS-1$
							String command = "plot \"" + getFilesDir() //$NON-NLS-1$
									+ "/tempPlotData.csv\" using "; //$NON-NLS-1$
							for (int lineNum = 0; lineNum < lineCount; lineNum++) {
								if (lineNum != 0) {
									command = command + ", "; //$NON-NLS-1$
								}
								command = command
										+ Integer.toString(lineNum * 2 + 1)
										+ ":" //$NON-NLS-1$
										+ Integer.toString(lineNum * 2 + 2);
							}
							command = command + " with lines \n"; //$NON-NLS-1$
							out.write(command);

						}
						out.flush();
						out.close();
						MainActivity.this.mTermSession
								.write("load \"" + getFilesDir() //$NON-NLS-1$
										+ "/startup.p\" \n"); //$NON-NLS-1$
					} catch (Exception e) {
						;
					}
				}
			}
		});
	}

	private void graphics() {
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);

		// make the entire canvas white
		paint.setColor(Color.WHITE);
		Log.d("GRAPH IMAGE", "PAINT"); //$NON-NLS-1$ //$NON-NLS-2$
		this._canvas.drawPaint(paint);
	}

	private void linewidth(int width) {
		this._linewidth = width;
	}

	private void linetype(int type) {
		this._linetype = type;
	}

	private void justify_text(String mode) {
		this._justMode = mode;
	}

	private void move(int x, int y) {
		this._x = x;
		this._y = y;
	}

	private void vector(int x, int y) {
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(this._linewidth);

		if (this._linetype < 0) {
			paint.setColor(Color.BLACK);
		} else if ((this._linetype % 9) == 0) {
			paint.setColor(Color.RED);
		} else if ((this._linetype % 9) == 1) {
			paint.setColor(Color.GREEN);
		} else if ((this._linetype % 9) == 2) {
			paint.setColor(Color.BLUE);
		} else if ((this._linetype % 9) == 3) {
			paint.setColor(Color.MAGENTA);
		} else if ((this._linetype % 9) == 4) {
			paint.setColor(Color.CYAN);
		} else if ((this._linetype % 9) == 5) {
			paint.setColor(Color.YELLOW);
		} else if ((this._linetype % 9) == 6) {
			paint.setColor(Color.BLACK);
		} else if ((this._linetype % 9) == 7) {
			paint.setARGB(1, 139, 69, 19);
		} else if ((this._linetype % 9) == 8) {
			paint.setColor(Color.LTGRAY);
		} else {
			paint.setColor(Color.BLACK);
		}
		Log.d("GRAPH IMAGE", "DRAWLINE"); //$NON-NLS-1$ //$NON-NLS-2$
		this._canvas.drawLine(this._x, this._screenHeight - this._y, x,
				this._screenHeight - y, paint);
		this._x = x;
		this._y = y;
	}

	private void text(int x, int y, String text) {
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(this._linewidth);
		paint.setColor(Color.BLACK);
		paint.setTextSize(this._textHeight);
		paint.setTypeface(Typeface.MONOSPACE);
		if (this._justMode.equals("RIGHT")) { //$NON-NLS-1$
			paint.setTextAlign(Align.RIGHT);
		} else if (this._justMode.equals("CENTRE")) { //$NON-NLS-1$
			paint.setTextAlign(Align.CENTER);
		} else {
			paint.setTextAlign(Align.LEFT);
		}
		Log.d("GRAPH IMAGE", "DRAW TEXT"); //$NON-NLS-1$ //$NON-NLS-2$
		this._canvas.drawText(text, x, this._screenHeight - y, paint);
	}

	public void processString(String newTermOut) {

		String modifiedTermOut = this.partialLine + newTermOut;
		int incompleteLine;
		String lines[] = modifiedTermOut.split("\\r?\\n"); //$NON-NLS-1$

		if ((lines.length > 0) && (modifiedTermOut.length() > 0)) {
			if ((modifiedTermOut.charAt(modifiedTermOut.length() - 1) == '\n')
					|| (modifiedTermOut.charAt(modifiedTermOut.length() - 1) == '\r')) {
				incompleteLine = 0;
				this.partialLine = ""; //$NON-NLS-1$
			} else {
				incompleteLine = 1;
				this.partialLine = lines[lines.length - 1];
			}
			for (int lineNum = 0; lineNum < lines.length - incompleteLine; lineNum++) {
				System.out.println(lines[lineNum]);
				if (lines[lineNum].startsWith("ANDROIDTERM")) { //$NON-NLS-1$
					lines[lineNum] = lines[lineNum].replaceAll("\\r|\\n", ""); //$NON-NLS-1$ //$NON-NLS-2$
					String termCommand[] = lines[lineNum].split(","); //$NON-NLS-1$
					if (termCommand[1].equals("move")) { //$NON-NLS-1$
						try {
							move(Integer.parseInt(termCommand[2]),
									Integer.parseInt(termCommand[3]));
						} catch (NumberFormatException ex) {
							// Toast.makeText(getBaseContext(), "why am I here",
							// Toast.LENGTH_LONG).show();
						}
					} else if (termCommand[1].equals("vector")) { //$NON-NLS-1$
						try {
							vector(Integer.parseInt(termCommand[2]),
									Integer.parseInt(termCommand[3]));
						} catch (NumberFormatException ex) {
							// Toast.makeText(getBaseContext(), "why am I here",
							// Toast.LENGTH_LONG).show();
						}
					} else if (termCommand[1].equals("put_text")) { //$NON-NLS-1$
						text(Integer.parseInt(termCommand[2]),
								Integer.parseInt(termCommand[3]),
								termCommand[4]);
					} else if (termCommand[1].equals("linetype")) { //$NON-NLS-1$
						linetype(Integer.parseInt(termCommand[2]));
					} else if (termCommand[1].equals("linewidth")) { //$NON-NLS-1$
						linewidth(Integer.parseInt(termCommand[2]));
					} else if (termCommand[1].equals("justify_text")) { //$NON-NLS-1$
						justify_text(termCommand[2]);
					} else if (termCommand[1].equals("init")) { //$NON-NLS-1$

					} else if (termCommand[1].equals("graphics")) { //$NON-NLS-1$
						graphics();
					} else if (termCommand[1].equals("text")) { //$NON-NLS-1$
						System.out.println("DemoView invalidate"); //$NON-NLS-1$
						// String filePath = saveBmp().getPath();
						addTexiview(Bitmap.createBitmap(this._bitmap));
						// this.demoview.invalidate();
						// this.mSwitcher.showNext();
					}
				} else {
					this.consoleLineString = this.consoleLineString
							+ lines[lineNum] + "\n"; //$NON-NLS-1$
				}
			}
		}
		String textLines[] = this.consoleLineString.split("\\n"); //$NON-NLS-1$
		this.consoleLineString = ""; //$NON-NLS-1$
		int lineNum;
		int lineCount;
		for (lineNum = textLines.length - 1, lineCount = 0; (lineNum >= 0)
				&& (lineCount < 1000); lineNum--, lineCount++) {
			this.consoleLineString = textLines[lineNum]
					+ "\n" + this.consoleLineString; //$NON-NLS-1$
		}
		// this.mTextView.setText(this.textViewString);
		addTexiview(null);
		scrollToBottom();
		// saveBitmapToSd(_bitmap);
	}

	/**
	 * インテント呼び出しがあった場合、ファイルパスをインテントに乗せて呼び出し元のアプリケーションに送ります
	 */
	public void sendBitmap() {
		// if (this._isCalledIntent) {
		// Intent intent_ret = new Intent();
		// Bundle b = new Bundle();
		//			b.putParcelable("data", this._bitmap); //$NON-NLS-1$
		//			intent_ret.putExtra("ReturnData", b); //$NON-NLS-1$
		// // intent_ret.setType("image/*");
		// setResult(RESULT_OK, intent_ret);
		// finish();
		// }
		saveBmp().getPath();

		if (this._isCalledIntent) {
			Intent intent_ret = new Intent();
			Bundle b = new Bundle();
			// saveBmp();
			//			b.putParcelable("data", saveBmp()); //$NON-NLS-1$
			b.putString("data", saveBmp().getPath()); //$NON-NLS-1$
			intent_ret.putExtra("ReturnData", b); //$NON-NLS-1$
			// intent_ret.setType("image/*");
			setResult(RESULT_OK, intent_ret);
			finish();
		}
	}

	private File saveBmp() {
		Calendar cal = Calendar.getInstance();
		int HH = cal.get(Calendar.HOUR_OF_DAY);
		int mm = cal.get(Calendar.MINUTE);
		int ss = cal.get(Calendar.SECOND);
		int SSS = cal.get(Calendar.MILLISECOND);
		File file = new File(getExternalCacheDir(), HH
				+ "" + mm + "" + ss + "" + SSS + ".png"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		try {
			// 出力ファイルを準備
			FileOutputStream fos = new FileOutputStream(file);
			// PNG形式で出力
			this._bitmap.compress(CompressFormat.PNG, 100, fos);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("output file = " + file.getPath()); //$NON-NLS-1$
		return file;
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
		// TODO Auto-generated method stub
		System.out.println("KEY CODE " + key_code); //$NON-NLS-1$
		resetPrediction();
		if ((key_code == KeyEvent.KEYCODE_ENTER)
				&& (MainActivity.this._ready == true)) {
			String command = MainActivity.this.editText.getText().toString();
			this.history.add(command);
			this.counter++;
			this.historyIndex = this.counter;
			MainActivity.this.editText.setText(""); //$NON-NLS-1$
			MainActivity.this.mTermSession.write(command + "\n"); //$NON-NLS-1$
		} else if (key_code == MyKeyboard.KEY_CODE_CURSOR_UP) {
			upHistory();
		} else if (key_code == MyKeyboard.KEY_CODE_CURSOR_DOWN) {
			downHistory();
		} else {
			CustomEditTextFunction.sendKeyCode(this.editTextList.get(0),
					key_code);
		}
	}

	@Override
	public void sendInputText(String inputText) {
		// TODO Auto-generated method stub
		System.out.println("KEY INPUT " + inputText); //$NON-NLS-1$
		this.historyIndex = this.counter;
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
		Log.d("CONSOLE", "input text is " + inputText); //$NON-NLS-1$ //$NON-NLS-2$
		if (!inputText.equals("\n")) { //$NON-NLS-1$
			CustomEditTextFunction.insertText(this.editTextList.get(0),
					inputText);
		}
		if (inputText.length() > 1 || this.symbols.indexOf(inputText) > 0
				|| this.editText.getText().toString().isEmpty()) {
			resetPrediction();
		} else {
			this.predictionStrCount += 1;
			System.out.println(this.predictionStrCount);
			System.out.println(this.editText.getText().toString() + "   " //$NON-NLS-1$
					+ this.predictionView.getLastCursorPoint() + "  " //$NON-NLS-1$
					+ this.predictionStrCount);
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
					MainActivity.this.myKeyboard.setVisibility(View.VISIBLE);
					if (!editText.hasFocus()) {
						switch (editText.getInputType()) {
						case InputType.TYPE_CLASS_NUMBER:
							MainActivity.this.myKeyboard
									.setKeyboard(new Keyboard(
											MainActivity.this, R.xml.symbols));
							break;
						case InputType.TYPE_CLASS_TEXT:
							MainActivity.this.myKeyboard
									.setKeyboard(new Keyboard(
											MainActivity.this, R.xml.qwerty));
							break;
						default:
							MainActivity.this.myKeyboard
									.setKeyboard(new Keyboard(
											MainActivity.this, R.xml.qwerty));

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
		this.predictionView.setfontsize(this.mTextView.getTextSize() * 0.9f);
		this.predictionView.setCount(this.inputCount);
		// ビューを意図的に更新する
		this.promptTextView.setText(this.promptTextView.getText().toString());
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
					(int) (this.mTextView.getTextSize() * (top + 2) * 1.1));
		} else {
			this.predictionView.setSize(0, 0);
		}
		System.out.println(predictionCount);
		System.out.println((int) widthSize + "  " //$NON-NLS-1$
				+ (int) (this.mTextView.getTextSize() * (top + 2) * 1.1));
		System.out.println(this.predictionFunctionList);
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

	

	private void addTexiview(Bitmap bitmap) {
		// Bitmap bmp = bitmap;
		if (this.globals.bitmaps.size() == 0 && bitmap == null) {
			this.mTextView.setText(this.consoleLineString);
			return;
		}
		ImageGetter imageGetter = new ImageGetter() {
			@Override
			@SuppressWarnings({ "deprecation", "boxing" })
			public Drawable getDrawable(String source) {
				String[] sources = source.split(" "); //$NON-NLS-1$
				Drawable d = new BitmapDrawable(
						MainActivity.this.globals.bitmaps.get(Integer
								.valueOf(sources[1])));
				d.setBounds(0, 0, 0 + MainActivity.this.GRAPTH_SIZE,
						0 + MainActivity.this.GRAPTH_SIZE);
				return d;
			}
		};

		if (bitmap != null) {
			this.globals.bitmaps.add(bitmap);
			String bitmapNum = String.valueOf(this.globals.bitmaps.size() - 1);
			this.consoleLineString = this.consoleLineString + BITMAP_MARK + " " //$NON-NLS-1$
					+ bitmapNum + "\n"; //$NON-NLS-1$
		}

		String imageStr = ""; //$NON-NLS-1$
		String textLines[] = this.consoleLineString.split("\\n"); //$NON-NLS-1$
		for (String line : textLines) {
			if (line.indexOf(BITMAP_MARK) != -1) {
				imageStr = imageStr + "<img src='" + line + "'/>" //$NON-NLS-1$ //$NON-NLS-2$ 
						+ "<BR>"; //$NON-NLS-1$
			} else {
				imageStr = imageStr + line + "<BR>"; //$NON-NLS-1$
			}
		}
		CharSequence htmlstr = Html.fromHtml(imageStr, imageGetter, null);
		this.mTextView.setText(htmlstr);
	}

	/**
	 * ヒストリー表示（過去）
	 */
	private void upHistory() {
		if (this.history.size() > 0 && this.historyIndex > 0) {
			this.historyIndex--;
			this.editText.setText(this.history.get(this.historyIndex));
			this.editText.setSelection(this.history.get(this.historyIndex)
					.length());
		}
	}

	/**
	 * ヒストリー表示（未来）
	 */
	private void downHistory() {
		if (this.history.size() > 0 && this.historyIndex < this.counter - 1) {
			this.historyIndex++;
			this.editText.setText(this.history.get(this.historyIndex));
			this.editText.setSelection(this.history.get(this.historyIndex)
					.length());
		}
	}

}
