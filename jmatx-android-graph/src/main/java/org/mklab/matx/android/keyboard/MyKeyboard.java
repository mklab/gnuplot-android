package org.mklab.matx.android.keyboard;

import org.mklab.matx.android.graph.R;

import android.app.Instrumentation;
import android.content.Context;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author kawabata ソフトウェアキーボードのビューです
 */
public class MyKeyboard extends KeyboardView implements
		KeyboardView.OnKeyboardActionListener {
	private Context parent = null;
	static final int CHANGE_KEY_CODE_DEFAULT = -121;
	static final int CHANGE_KEY_CODE_FUNCTION = -144;
	static final int CHANGE_KEY_CODE_DEFAULT_SHIFT = -169;
	static final int CHANGE_KEY_CODE_TENKEY = -196;
	static final int KEY_CODE_SPACE = 32;
	public static final int KEY_CODE_CURSOR_LEFT = -1;
	public static final int KEY_CODE_CURSOR_RIGHT = -2;
	public static final int KEY_CODE_CURSOR_UP = -3;
	public static final int KEY_CODE_CURSOR_DOWN = -4;
	private Keyboard deafaultKeyboard;
	private Keyboard deafaultShiftKeyboard;
	private Keyboard functionKeyboard;
	private Keyboard tenKeyboard;
	private boolean shiftLock;
	private KeyboardListner listner = null;

	/**
	 * @param context
	 * @param attrs
	 */
	public MyKeyboard(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.parent = context;
		Log.d("KEYBOARD", "const1"); //$NON-NLS-1$ //$NON-NLS-2$
		init();
	}

	/**
	 * コンストラクタですです
	 * 
	 * @param context
	 */
	public MyKeyboard(Context context) {
		super(context, null);
		this.parent = context;
		Log.d("MYKEYBOARD", "const2"); //$NON-NLS-1$ //$NON-NLS-2$
		init();
	}

	/**
	 * @param l
	 *            リスナーをセットします
	 */
	public void setKeyboardLisner(KeyboardListner l) {
		this.listner = l;
	}

	private Keyboard myKeyboard = null;

	/**
	 * キーボードの情報を初期化します
	 */
	public void init() {
		Log.d("KEYBOARD", "init"); //$NON-NLS-1$ //$NON-NLS-2$
		this.deafaultKeyboard = new Keyboard(this.parent, R.xml.qwerty);
		this.deafaultShiftKeyboard = new Keyboard(this.parent,
				R.xml.qwerty_shift);
		this.functionKeyboard = new Keyboard(this.parent, R.xml.function);
		this.tenKeyboard = new Keyboard(this.parent, R.xml.symbols);
		setOnKeyboardActionListener(this);
		setEnabled(true);
		setPreviewEnabled(true);
		setVisibility(View.VISIBLE);
		setFocusable(false);
		setFocusableInTouchMode(false);

		setBackgroundResource(R.drawable.keyboard_backcolor);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, 0);
		lp.gravity = Gravity.BOTTOM;
		setLayoutParams(lp);
		makeKeyboardView();
	}

	/**
	 * デフォルトのキーボードをセットします
	 */
	public void makeKeyboardView() {
		this.myKeyboard = new Keyboard(this.parent, R.xml.qwerty);
		setKeyboard(this.myKeyboard);
	}

	class Param {
		int param1;
		String param2;
	}

	Param param = new Param();

	class Result {
		int result1;
		String result2;
	}

	public void onKey(final int primaryCode, int[] keyCodes) {
		Log.d("MYKEYBOARD", "onKey : " + primaryCode); //$NON-NLS-1$ //$NON-NLS-2$

		switch (primaryCode) {
		case CHANGE_KEY_CODE_DEFAULT:
			if (getKeyboard().equals(this.deafaultShiftKeyboard)
					&& !getshiftLock()) {
				sihftLock();
			} else {
				init();
				sihftLock();
				setKeyboard(this.deafaultKeyboard);
			}
			break;
		case CHANGE_KEY_CODE_DEFAULT_SHIFT:
			init();
			shiftUnLock();
			setKeyboard(this.deafaultShiftKeyboard);
			break;
		case CHANGE_KEY_CODE_FUNCTION:
			init();
			sihftLock();
			setKeyboard(this.functionKeyboard);
			break;
		case CHANGE_KEY_CODE_TENKEY:
			init();
			sihftLock();
			setKeyboard(this.tenKeyboard);
			break;
		case KeyEvent.KEYCODE_SHIFT_LEFT:
			System.out.println("keySHIFTCHANGE :" + this.isShifted());
			if (this.isShifted()) {
				this.setShifted(false);
			} else {
				this.setShifted(true);
			}
			System.out.println("keySHIFTCHANGE :" + this.isShifted());
			break;
		default:
			this.listner.sendKeyDown(primaryCode);

			break;
		}
	}

	private void sihftLock() {
		this.shiftLock = true;
	}

	private void shiftUnLock() {
		this.shiftLock = false;
	}

	public boolean getshiftLock() {
		return this.shiftLock;
	}

	public void onPress(int k) {
		Log.d("MYKEYBOARD", "onPress"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void onRelease(int k) {
		Log.d("MYKEYBOARD", "onRelease"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void onText(final CharSequence s) {
		Log.d("MYKEYBOARD", "onText : " + s.toString()); //$NON-NLS-1$ //$NON-NLS-2$
		this.listner.sendInputText(s.toString());
		if (getKeyboard().equals(this.deafaultShiftKeyboard) && !getshiftLock()) {
			init();
			sihftLock();
			setKeyboard(this.deafaultKeyboard);
		}
	}

	@Override
	public void swipeUp() {
		this.listner.swipeUp();
	}

	@Override
	public void swipeDown() {
		this.listner.swipeDown();
	}

	@Override
	public void swipeLeft() {
		this.listner.swipeLeft();
	}

	@Override
	public void swipeRight() {
		this.listner.swipeRight();
	}

}