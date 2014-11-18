package org.mklab.matx.android.keyboard;

import android.inputmethodservice.KeyboardView;

/**
 * @author kawabata
 * キーボードのリスナーインターフェースです
 */
public interface KeyboardListner extends KeyboardView.OnKeyboardActionListener{
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
