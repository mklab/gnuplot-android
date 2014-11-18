package org.mklab.matx.android.keyboard;

import android.inputmethodservice.KeyboardView;

/**
 * @author kawabata
 * キーボードのリスナーインターフェースです
 */
public interface KeyboardListner extends KeyboardView.OnKeyboardActionListener{
	/**
	 * バックスペースが押された時の挙動です。
	 */
	public void handleBackspace();
	/**
	 * Enterが押された時の挙動です。
	 */
	public void handleEnter();
	/**
	 * Spaceが押された時の挙動です。
	 */
	public void handleSpace();
	/**
	 * edittextのカーソルを左に動かします。
	 */
	public void moveLeftCursor();
	/**
	 * edittextのカーソルを右に動かします。
	 */
	public void moveRightCursor();
	/**
	 * keyDownコードを送信します
	 * 
	 * @param key_code
	 */
	public void sendKeyDown(int key_code);
	
	/**
	 * 入力するテキストを送信します
	 * 
	 * @param inputText
	 */
	public void sendInputText(String inputText);
}
