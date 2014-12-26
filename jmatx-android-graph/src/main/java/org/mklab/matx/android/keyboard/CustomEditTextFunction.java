package org.mklab.matx.android.keyboard;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @author kawabata 　エディタテキストに様々な書き込みを行うクラスです
 */
public class CustomEditTextFunction {

	/**
	 * 入力部の任意の位置にメッセージを挿入し、カーソルを最後に移動します
	 * 
	 * @param editText
	 *            対象のEditText
	 * @param text
	 *            追加表示するメッセージ
	 */
	static public void insertText(EditText editText, String text) {
		switch (editText.getInputType()) {
		case InputType.TYPE_CLASS_NUMBER:
			if (!isNumber(text)) return;
			break;
		default:
			break;
		}
		int cursorPoint = editText.getSelectionStart();
		String frontStr = editText.getText().toString()
				.substring(0, cursorPoint);
		String backStr = editText.getText().toString()
				.substring(cursorPoint, editText.getText().toString().length());
		editText.setText(frontStr + text + backStr);
		editText.setSelection(cursorPoint + text.length());
		// 括弧入力があった場合、カーソルを左に移動させる
		if (text.toString().equals("{}") || text.toString().equals("[]") //$NON-NLS-1$ //$NON-NLS-2$
				|| text.toString().equals("()")
				|| text.toString().equals("\"\"")
				|| text.toString().equals("''")) { //$NON-NLS-1$
			moveLeftCursor(editText);
		}
	}

	private static boolean isNumber(String val) {
		String regex = "\\A[-]?[0-9]+\\z";
		Pattern p = Pattern.compile(regex);
		Matcher m1 = p.matcher(val);
		return m1.find();
	}

	/**
	 * 入力部の任意の位置にメッセージを挿入し、カーソルを最後に移動します
	 * 
	 * @param editText
	 *            対象のEditText
	 * @param keyCode
	 *            キーコード
	 */
	static public void sendKeyCode(EditText editText, int keyCode) {
		System.out.println("KEYCODE IS" + keyCode);
		switch (keyCode) {
		case KeyEvent.KEYCODE_ENTER:
			enterKeyFunction(editText);
			break;
		case KeyEvent.KEYCODE_DEL:
			deleteKeyFunction(editText);
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			break;
		case MyKeyboard.KEY_CODE_CURSOR_LEFT:
			moveLeftCursor(editText);
			break;
		case MyKeyboard.KEY_CODE_CURSOR_RIGHT:
			moveRightCursor(editText);
			break;
		case MyKeyboard.KEY_CODE_CURSOR_UP:
			moveUpCursor(editText);
			break;
		case MyKeyboard.KEY_CODE_CURSOR_DOWN:
			moveDownCursor(editText);
			break;
		case KeyEvent.KEYCODE_DPAD_UP:
			break;
		case KeyEvent.KEYCODE_SHIFT_LEFT:
			break;
		case KeyEvent.KEYCODE_SPACE:
			insertText(editText, " ");
			break;

		default:
			break;
		}
	}

	private static void enterKeyFunction(EditText editText) {
		insertText(editText, "\n");
	}

	private static void moveDownCursor(EditText editText) {
		// TODO Auto-generated method stub
		System.out.println("moveDownCursor"); //$NON-NLS-1$
		int cursorPoint = editText.getSelectionStart();
		String edit = editText.getText().toString();
		System.out.println("cusror = " + cursorPoint); //$NON-NLS-1$
		String beforeStr = edit.substring(0, cursorPoint);
		String afterStr = edit.substring(cursorPoint, edit.length());
		System.out.println("afterStr = " + afterStr); //$NON-NLS-1$
		String afterLine;
		String beforeLine;

		int nowLineCursorPoint;
		if (beforeStr.lastIndexOf("\n") >= 0)
			nowLineCursorPoint = cursorPoint - beforeStr.lastIndexOf("\n") - 1;
		else
			nowLineCursorPoint = cursorPoint;

		if (afterStr.indexOf("\n") >= 0) {
			System.out.println("atterStr indexOf : " + afterStr.indexOf("\n"));
			afterLine = afterStr.substring(afterStr.indexOf("\n") + 1,
					afterStr.length());
			System.out.println("afterline = " + afterLine); //$NON-NLS-1$
			if (afterLine.indexOf("\n") >= 0)
				afterLine = afterLine.substring(0, afterLine.indexOf("\n"));
			System.out.println("nowCursor : " + nowLineCursorPoint);
			System.out.println("afterline = " + afterLine); //$NON-NLS-1$

			int afterLineStart = afterStr.indexOf("\n") + 1;
			int afterLineEnd = afterLineStart + afterLine.length();
			if (afterLine.length() < nowLineCursorPoint)
				editText.setSelection(cursorPoint + afterLine.length()
						+ afterStr.indexOf("\n") + 1);
			else
				editText.setSelection(cursorPoint + nowLineCursorPoint
						+ afterStr.indexOf("\n") + 1);
		} else {
			return;
		}
	}

	private static void moveUpCursor(EditText editText) {
		// TODO Auto-generated method stub
		System.out.println("moveUpCursor"); //$NON-NLS-1$
		int cursorPoint = editText.getSelectionStart();
		String edit = editText.getText().toString();
		System.out.println("cusror = " + cursorPoint); //$NON-NLS-1$
		String beforeStr = edit.substring(0, cursorPoint);
		// String afterStr = edit.substring(cursorPoint,edit.length());
		System.out.println("bforeStr = " + beforeStr); //$NON-NLS-1$
		String beforeLine;
		if (beforeStr.lastIndexOf("\n") > 0) {
			beforeLine = edit.substring(0, beforeStr.lastIndexOf("\n"));
			int nowLineCursorPoint = cursorPoint - beforeStr.lastIndexOf("\n");
			System.out.println("nowCursor : " + nowLineCursorPoint);
			System.out.println("bforeline = " + beforeLine); //$NON-NLS-1$
			int beforeLineEnd = beforeStr.lastIndexOf("\n");
			int beforeLineStart = beforeLine.lastIndexOf("\n");
			System.out.println(beforeLineStart);
			if (beforeLineStart < 0) {
				beforeLineStart = 0;
				nowLineCursorPoint -= 1;
			}

			System.out.println(beforeLineStart);
			beforeLine = beforeLine.substring(beforeLineStart, beforeLineEnd);
			System.out.println("bforeline = " + beforeLine); //$NON-NLS-1$
			if (beforeLine.length() < nowLineCursorPoint)
				editText.setSelection(beforeStr.lastIndexOf("\n"));
			else
				editText.setSelection(beforeStr.lastIndexOf("\n")
						- (beforeLine.length() - nowLineCursorPoint));
		} else {
			return;
		}

	}

	private static void moveRightCursor(EditText editText) {
		System.out.println("moveRightCursor");
		int cursorPoint = editText.getSelectionStart();
		System.out.println("cusror = " + cursorPoint);
		if (cursorPoint == editText.length()) {
			return;
		}
		editText.setSelection(cursorPoint + 1);
	}

	private static void moveLeftCursor(EditText editText) {
		System.out.println("moveLeftCursor");
		int cursorPoint = editText.getSelectionStart();
		System.out.println("cusror = " + cursorPoint);
		if (cursorPoint == 0) {
			return;
		}
		editText.setSelection(cursorPoint - 1);
	}

	private static void deleteKeyFunction(EditText editText) {
		int cursorPoint = editText.getSelectionStart();
		if (cursorPoint == 0) {
			return;
		}
		String frontStr = editText.getText().toString()
				.substring(0, cursorPoint - 1);
		String backStr = editText.getText().toString()
				.substring(cursorPoint, editText.getText().toString().length());
		editText.setText(frontStr + backStr);
		editText.setSelection(cursorPoint - 1);
	}

}
