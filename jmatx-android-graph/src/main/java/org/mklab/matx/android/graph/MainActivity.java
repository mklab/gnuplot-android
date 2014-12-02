package org.mklab.matx.android.graph;

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
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class MainActivity extends Activity implements KeyboardListner,
		AddPredictionMessage {
	DemoView demoview = null;
	private Canvas _canvas = null;
	private Bitmap _bitmap = null;
	private int _screenHeight;
	private int _screenWidth;
	private int _textHeight;
	private int _textWidth;
	private int _x;
	private int _y;
	private LinearLayout mTerminalLayout;
	private LinearLayout mPlotLayout;
	private ViewSwitcher mSwitcher;
	public TextView mTextView;
	public TextView promptTextView;
	public ScrollView mScrollView;
	public EditText editText;
	private String textViewString = ""; //$NON-NLS-1$
	private String partialLine = ""; //$NON-NLS-1$
	private int _linetype;
	private int _linewidth;
	private String _justMode = "LEFT"; //$NON-NLS-1$
	private boolean _ready = false;
	private boolean _plotDataPresent = false;
	private String _plotData = ""; //$NON-NLS-1$
	private MainActivity _sessionParent = null;
	private static final int REQUEST_CODE_GRAPH = 2;
	private static final int RESULT_CODE_SUB_GRAPH = 101;
	private TermSession mTermSession;
	private boolean _isCalledIntent = false;
	private MyKeyboard myKeyboard;
	private PredictiveView predictionView;
	float fontSize = 20;

	List<EditText> editTextList = new ArrayList<EditText>();
	private int predictionStrCount;
	private int inputCount;
	static List<String> predictionVariableList = new ArrayList<String>();
	static List<String> predictionFunctionList = new ArrayList<String>();
	static List<String> predictionMaTXFunctionList = new ArrayList<String>();
	static List<String> variableNameList = new ArrayList<String>();
	private List<String> methodNameList = new ArrayList<String>();
	private int prefontsize = 15;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.mTerminalLayout = (LinearLayout) findViewById(R.id.terminalLayout);

		this.mPlotLayout = (LinearLayout) findViewById(R.id.plotLayout);

		this.mTextView = (TextView) findViewById(R.id.termWindow);

		this.mScrollView = (ScrollView) findViewById(R.id.scrollView);

		this.editText = (EditText) findViewById(R.id.edit_command);

		this.mSwitcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);

		this.promptTextView = (TextView) findViewById(R.id.prompt);

		predictionView = (PredictiveView) findViewById(R.id.CustomView);
		predictionView.setSize(0, 0);
		predictionView.setContext(this);
		predictionView.setfontsize(this.fontSize);

		methodNameLoader();
		// this.mCmdEditText.setOnKeyListener(new OnKeyListener() {
		// public boolean onKey(View view, int keyCode, KeyEvent event) {
		// if (event.getAction() == KeyEvent.ACTION_DOWN) {
		// if ((keyCode == KeyEvent.KEYCODE_ENTER)
		// && (MainActivity.this._ready == true)) {
		// String command = MainActivity.this.mCmdEditText
		// .getText().toString();
		//						MainActivity.this.mCmdEditText.setText(""); //$NON-NLS-1$
		//						MainActivity.this.mTermSession.write(command + "\n"); //$NON-NLS-1$
		// return true;
		// }
		// }
		// return false;
		// }
		// });

		this._sessionParent = this;

		ViewTreeObserver viewTreeObserver = this.mTerminalLayout
				.getViewTreeObserver();
		if (viewTreeObserver.isAlive()) {
			System.out.println("viewTree"); //$NON-NLS-1$
			viewTreeObserver
					.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
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

	private void init() {
		if (this.demoview == null) {
			this.demoview = new DemoView(this);
			this.demoview.setLayoutParams(new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.FILL_PARENT));
			this.mPlotLayout.addView(this.demoview);
			this.mPlotLayout.invalidate();
			this.mSwitcher.invalidate();
		}
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

	private class DemoView extends View {

		public DemoView(Context context) {
			super(context);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			canvas.drawBitmap(MainActivity.this._bitmap, 0, 0, null);
			// _canvas = canvas;

			// custom drawing code here
			// remember: y increases from top to bottom
			// x increases from left to right
			// int x = 0;
			// int y = 0;
			// Paint paint = new Paint();
			// paint.setStyle(Paint.Style.FILL);

			// make the entire canvas white
			// paint.setColor(Color.GRAY);
			// _canvas.drawPaint(paint);
			// another way to do this is to use:
			// canvas.drawColor(Color.WHITE);

			// draw a solid blue circle
			// paint.setColor(Color.BLUE);
			// canvas.drawCircle(20, 20, 15, paint);

			// draw blue circle with antialiasing turned on
			// paint.setAntiAlias(true);
			// paint.setColor(Color.BLUE);
			// canvas.drawCircle(60, 20, 15, paint);

			// compare the above circles once drawn
			// the fist circle has a jagged perimeter
			// the second circle has a smooth perimeter

			// draw a solid red rectangle
			// paint.setAntiAlias(false);
			// paint.setColor(Color.RED);

			// create and draw triangles
			// use a Path object to store the 3 line segments
			// use .offset to draw in many locations
			// note: this triangle is not centered at 0,0
			// paint.setStyle(Paint.Style.STROKE);
			// paint.setStrokeWidth(2);
			// paint.setColor(Color.RED);
			// path.moveTo(0, -10);
			// path.lineTo(5, 0);
			// path.lineTo(-5, 0);
			// path.close();
			// path.offset(10, 40);
			// canvas.drawPath(path, paint);
			// path.offset(50, 100);
			// canvas.drawPath(path, paint);
			// offset is cumlative
			// next draw displaces 50,100 from previous
			// path.offset(50, 100);
			// canvas.drawPath(path, paint);

			// draw some text using STROKE style
			// paint.setStyle(Paint.Style.STROKE);
			// paint.setStrokeWidth(1);
			// paint.setColor(Color.MAGENTA);
			// paint.setTextSize(30);
			// canvas.drawText("Style.STROKE", 75, 75, paint);

			// draw some text using FILL style
			// paint.setStyle(Paint.Style.FILL);
			// turn antialiasing on
			// paint.setAntiAlias(true);
			// paint.setTextSize(30);
			// canvas.drawText("Style.FILL", 75, 110, paint);

			// draw some rotated text
			// get text width and height
			// set desired drawing location
			// x = 75;
			// y = 185;
			// paint.setColor(Color.GRAY);
			// paint.setTextSize(25);
			// String str2rotate = "Rotated!";

			// draw bounding rect before rotating text
			// Rect rect = new Rect();
			// paint.getTextBounds(str2rotate, 0, str2rotate.length(), rect);
			// canvas.translate(x, y);
			// paint.setStyle(Paint.Style.FILL);
			// draw unrotated text
			// canvas.drawText("!Rotated", 0, 0, paint);
			// paint.setStyle(Paint.Style.STROKE);
			// canvas.drawRect(rect, paint);
			// undo the translate
			// canvas.translate(-x, -y);

			// rotate the canvas on center of the text to draw
			// canvas.rotate(-45, x + rect.exactCenterX(),
			// y + rect.exactCenterY());
			// draw the rotated text
			// paint.setStyle(Paint.Style.FILL);
			// canvas.drawText(str2rotate, x, y, paint);

			// undo the rotate
			// canvas.restore();
			// canvas.drawText("After canvas.restore()", 50, 250, paint);

			// draw a thick dashed line
			// DashPathEffect dashPath =
			// new DashPathEffect(new float[]{20,5}, 1);
			// paint.setPathEffect(dashPath);
			// paint.setStrokeWidth(8);
			// canvas.drawLine(0, 300 , 320, 300, paint);

			// move(0,0);
			// vector(100,100);
			/*
			 * int padding = 20; int width = getWidth() - 2*padding; int height
			 * = getHeight() - 2*padding; paint.setColor(Color.WHITE);
			 * _canvas.drawRect(padding, padding, padding+width, padding+height,
			 * paint);
			 * 
			 * if (!term.mCurves.isEmpty()) { NumberFormat exp =
			 * DecimalFormat.getInstance(); if (exp instanceof DecimalFormat) {
			 * ((DecimalFormat)exp).applyPattern("0.00E0"); } double xMin = 0;
			 * double xMax = 0; double yMin = 0; double yMax = 0; double xRange
			 * = 0; double yRange = 0; for (int curveLoop=0; curveLoop <
			 * term.mCurves.size(); curveLoop++) { for (int pointLoop = 0;
			 * pointLoop < term.mCurves.get(curveLoop).points.size();
			 * pointLoop++) { if ((curveLoop == 0) && (pointLoop == 0)) { xMin =
			 * term.mCurves.get(curveLoop).points.get(pointLoop).x; xMax =
			 * term.mCurves.get(curveLoop).points.get(pointLoop).x; yMin =
			 * term.mCurves.get(curveLoop).points.get(pointLoop).y; yMax =
			 * term.mCurves.get(curveLoop).points.get(pointLoop).y; } else { if
			 * (term.mCurves.get(curveLoop).points.get(pointLoop).x < xMin) {
			 * xMin = term.mCurves.get(curveLoop).points.get(pointLoop).x; } if
			 * (term.mCurves.get(curveLoop).points.get(pointLoop).x > xMax) {
			 * xMax = term.mCurves.get(curveLoop).points.get(pointLoop).x; } if
			 * (term.mCurves.get(curveLoop).points.get(pointLoop).y < yMin) {
			 * yMin = term.mCurves.get(curveLoop).points.get(pointLoop).y; } if
			 * (term.mCurves.get(curveLoop).points.get(pointLoop).y > yMax) {
			 * yMax = term.mCurves.get(curveLoop).points.get(pointLoop).y; } } }
			 * } xRange = xMax-xMin; yRange = yMax-yMin; for (int curveLoop=0;
			 * curveLoop < term.mCurves.size(); curveLoop++) { for (int
			 * pointLoop = 0; pointLoop <
			 * term.mCurves.get(curveLoop).points.size(); pointLoop++) { int
			 * xNorm = padding + (int)(width *
			 * (term.mCurves.get(curveLoop).points
			 * .get(pointLoop).x-xMin)/xRange); int yNorm = padding + height -
			 * (int)(height *
			 * (term.mCurves.get(curveLoop).points.get(pointLoop).
			 * y-yMin)/yRange); if (pointLoop == 0) { move(xNorm,yNorm); } else
			 * { vector(xNorm,yNorm); } } } paint.setColor(Color.BLACK);
			 * _canvas.drawText(exp.format(xMax), width - 10, height + padding +
			 * 10, paint); _canvas.drawText(exp.format(xMin), padding, height +
			 * padding + 10, paint); _canvas.drawText(exp.format(yMax), 0,
			 * padding + 10, paint); _canvas.drawText(exp.format(yMin), 0,
			 * height + padding, paint); }
			 */
		}
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
						init();
					} else if (termCommand[1].equals("graphics")) { //$NON-NLS-1$
						graphics();
					} else if (termCommand[1].equals("text")) { //$NON-NLS-1$
						this.demoview.invalidate();
						this.mSwitcher.showNext();
					}
				} else {
					this.textViewString = this.textViewString + lines[lineNum]
							+ "\n"; //$NON-NLS-1$
				}
			}
		}
		String textLines[] = this.textViewString.split("\\n"); //$NON-NLS-1$
		this.textViewString = ""; //$NON-NLS-1$
		int lineNum;
		int lineCount;
		for (lineNum = textLines.length - 1, lineCount = 0; (lineNum >= 0)
				&& (lineCount < 1000); lineNum--, lineCount++) {
			this.textViewString = textLines[lineNum]
					+ "\n" + this.textViewString; //$NON-NLS-1$
		}
		this.mTextView.setText(this.textViewString);
		scrollToBottom();
		// saveBitmapToSd(_bitmap);
	}

	public void sendBitmap() {
		if (this._isCalledIntent) {
			Intent intent_ret = new Intent();
			Bundle b = new Bundle();
			b.putParcelable("data", this._bitmap); //$NON-NLS-1$
			intent_ret.putExtra("ReturnData", b); //$NON-NLS-1$
			// intent_ret.setType("image/*");
			setResult(RESULT_OK, intent_ret);
			finish();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
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

	public void onPress(int primaryCode) {
		// TODO Auto-generated method stub

	}

	public void onRelease(int primaryCode) {
		// TODO Auto-generated method stub

	}

	public void onKey(int primaryCode, int[] keyCodes) {
		// TODO Auto-generated method stub

	}

	public void onText(CharSequence text) {
		// TODO Auto-generated method stub

	}

	public void swipeLeft() {
		// TODO Auto-generated method stub

	}

	public void swipeRight() {
		// TODO Auto-generated method stub

	}

	public void swipeDown() {
		// TODO Auto-generated method stub

	}

	public void swipeUp() {
		// TODO Auto-generated method stub

	}

	public void sendKeyDown(int key_code) {
		// TODO Auto-generated method stub
		System.out.println("KEY CODE " + key_code);

		if ((key_code == KeyEvent.KEYCODE_ENTER)
				&& (MainActivity.this._ready == true)) {
			String command = MainActivity.this.editText.getText().toString();
			MainActivity.this.editText.setText(""); //$NON-NLS-1$
			MainActivity.this.mTermSession.write(command + "\n"); //$NON-NLS-1$

		} else {
			CustomEditTextFunction.sendKeyCode(this.editTextList.get(0),
					key_code);
		}
	}

	public void sendInputText(String inputText) {
		// TODO Auto-generated method stub
		System.out.println("KEY INPUT " + inputText);

		Log.d("CONSOLE", "input text is " + inputText); //$NON-NLS-1$ //$NON-NLS-2$
		CustomEditTextFunction.insertText(this.editTextList.get(0), inputText);
		if (predictionView.getLastCursorPoint() + this.predictionStrCount != editText
				.getSelectionEnd()) {
			resetPrediction();
		}
		if (inputText.equals("")) { //$NON-NLS-1$
			resetPrediction();
			return;
		}

		predictionView.clear();
		this.inputCount = inputText.length();

		if (inputText.length() > 1 || this.symbols.indexOf(inputText) > 0
				|| editText.getText().toString().isEmpty()) {
			resetPrediction();
		} else {
			this.predictionStrCount += 1;
			System.out.println(this.predictionStrCount);
			String pre = editText
					.getText()
					.toString()
					.substring(
							predictionView.getLastCursorPoint(),
							this.predictionStrCount
									+ predictionView.getLastCursorPoint());
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
	 * 謖�螳壹＆繧後◆Edit繝�繧ｭ繧ｹ繝医′繧ｿ繝�繝√＆繧後※繧ゅく繝ｼ繝懊�ｼ繝峨′襍ｷ蜍輔＠縺ｪ縺�繧医≧縺ｫ縺励∪縺�
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
			// edit繝�繧ｭ繧ｹ繝医′繧ｿ繝�繝√＆繧後◆譎ゅ�ｮ謖吝虚
			editText.setOnTouchListener(new View.OnTouchListener() {
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

	public void addFunction(String function, int lastCursorPoint) {
		String frontStr = editText
				.getText()
				.toString()
				.substring(0,
						predictionView.getLastCursorPoint());
		String backStr = editText
				.getText()
				.toString()
				.substring(editText.getSelectionEnd(),
						editText.getText().toString().length());
		editText.setText(frontStr + function + "()" + backStr); //$NON-NLS-1$
		editText.setSelection(lastCursorPoint + function.length() + 1);
		resetPrediction();
	}

	public void addVariable(String var, int lastCursorPoint) {
		String frontStr = editText
				.getText()
				.toString()
				.substring(0,
						predictionView.getLastCursorPoint());
		String backStr = editText
				.getText()
				.toString()
				.substring(editText.getSelectionEnd(),
						editText.getText().toString().length());
		editText.setText(frontStr + var + backStr);
		editText.setSelection(lastCursorPoint + var.length());
		resetPrediction();
	}

	private void resetPrediction() {
		predictionView.setLastCursorPoint(editText.getSelectionStart());
		predictionView.clear();
		this.predictionStrCount = 0;
		prediction(""); //$NON-NLS-1$
	}

	public void cerateMessage(String message) {
		editText.setText(message);
		Editable result = editText.getText();
		editText.setSelection(result.toString().length());
		resetPrediction();
	}

	private void setPrediction() {
		System.out.println("setPrediction"); //$NON-NLS-1$
		predictionView.setUpdateFunctiuonList(predictionFunctionList);
		predictionView.setUpdateMaTXFunctionList(predictionMaTXFunctionList);
		predictionView.setUpdateVariableList(predictionVariableList);
		predictionView.setfontsize(mTextView.getTextSize() * 0.9f);
		predictionView.setCount(this.inputCount);
		// ビューを意図的に更新する
		promptTextView.setText(promptTextView.getText().toString());
	}


	private void prediction(String input) {
		System.out.println("prediction"); //$NON-NLS-1$
		predictionVariableList.clear();
		predictionFunctionList.clear();
		predictionMaTXFunctionList.clear();
		int predictionCount = 0;
		int left = 0;
		int top = 0;
		String lastMethod = ""; //$NON-NLS-1$
		// ウィンドウマネージャのインスタンス取得
		WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		// ディスプレイのインスタンス生成
		Display disp = wm.getDefaultDisplay();

		float widthSize = disp.getWidth();
		if (!input.equals("")) { //$NON-NLS-1$

			for (final String c : methodNameList) {
				if (c.startsWith(input)) {
					predictionFunctionList.add(c);
					int leftSize = c.length() * (int) this.prefontsize;
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
			predictionView
					.setSize((int) widthSize,
							(int) (mTextView.getTextSize()
									* (top + 2) * 1.1));
		} else {
			predictionView.setSize(0, 0);
		}
	}

	private void methodNameLoader() {
		methodNameList.add("sin");
		methodNameList.add("cos");
		methodNameList.add("tan");
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
