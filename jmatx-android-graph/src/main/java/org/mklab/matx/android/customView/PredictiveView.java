package org.mklab.matx.android.customView;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author kawabata 予測されたメッソドの一覧を表示するビューです
 */
public class PredictiveView extends View {
	private Paint paint = new Paint();
	private int lastCursorPoint;
	private int width = 0;
	private int height = 0;
	private float moveX = 0;
	private float moveY = 0;
	private List<String> predictionFunctionList = new ArrayList<String>();
	private List<String> predictionCommandList = new ArrayList<String>();
	private String predictionFunction = ""; //$NON-NLS-1$
	private String predictionVariable = ""; //$NON-NLS-1$
	private String predictionCommand = ""; //$NON-NLS-1$
	private int count;
	private int textSize;
	private float boxHeight;
	private float boxWidth;
	private List<String> predictionVariabelList = new ArrayList<String>();
	private AddPredictionMessage lisner;

	/**
	 * コンストラクタです
	 * 
	 * @param context
	 */
	public PredictiveView(Context context) {
		super(context);
	}

	/**
	 * コンストラクタです
	 * 
	 * @param context
	 * @param attrs
	 */
	public PredictiveView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		System.out.println("onMeasure");
		// Viewの描画サイズを指定する
		setMeasuredDimension(this.width, this.height);
	}

	/**
	 * Viewのサイズをセットします
	 * 
	 * @param width
	 * @param height
	 */
	public void setSize(int width, int height) {
		System.out.println("setSize");
		System.out.println(width   + " " + height);
		this.width = width;
		this.height = height;
		setMeasuredDimension(this.width, this.height);
	}

	/**
	 * contextをセットします
	 * 
	 * @param Object
	 * 
	 */
	public void setContext(AddPredictionMessage Object) {
		this.lisner = Object;
	}

	/**
	 * 予測された関数名をセットします
	 * 
	 * @param predictionList
	 */
	public void setUpdateFunctiuonList(List<String> predictionList) {
		this.predictionFunctionList = predictionList;
	}

	/**
	 * 予測された関数名をセットします
	 * 
	 * @param predictionList
	 */
	public void setUpdateCommandList(List<String> predictionList) {
		this.predictionCommandList = predictionList;
	}

	/**
	 * 予測された変数をセットします
	 * 
	 * @param predictionList
	 * 
	 */
	public void setUpdateVariableList(List<String> predictionList) {
		// TODO Auto-generated method stub
		this.predictionVariabelList = predictionList;
	}

	/**
	 * 入力中の文字数をセットします
	 * 
	 * @param count
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * 文字サイズをセットします
	 * 
	 * @param size
	 */
	public void setfontsize(float size) {
		this.textSize = (int) size;
		this.boxHeight = this.textSize * 1.2f;
		this.boxWidth = this.textSize * 1.2f;
	}

	/**
	 * 画面をクリアして新たに描画します。
	 */
	public void clear() {
		System.out.println("clear");
		this.paint.clearShadowLayer();
		this.moveX = -1;
		this.moveY = -1;
		invalidate();
	}

	/**
	 * @param point
	 */
	public void setLastCursorPoint(int point) {
		this.lastCursorPoint = point;
	}

	/**
	 * @return
	 */
	public int getLastCursorPoint() {
		return this.lastCursorPoint;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		System.out.println("onCanvas");
		canvas.drawColor(0, Mode.CLEAR);
		canvas.drawColor(Color.BLACK);

		float left = 0.0f;
		float top = 0.0f;
		this.paint.setStrokeWidth(2);

		for (final String c : this.predictionVariabelList) {
			float leftSize = c.length() * this.boxWidth;
			// 二段目に移動させる
			if ((left + leftSize) > this.width) {
				left = 0;
				top += this.boxHeight;
			}

			// 枠内がタッチされているかを判定する
			if (this.moveX > left && this.moveX < (left + leftSize)
					&& this.moveY > top && this.moveY < (top + this.boxHeight)) {
				this.paint.setColor(Color.GRAY);
				this.paint.setStyle(Style.FILL);
				this.predictionVariable = c;
				this.predictionFunction = ""; //$NON-NLS-1$
				this.predictionCommand = ""; //$NON-NLS-1$
			} else {
				this.paint.setColor(Color.GRAY);
				this.paint.setStyle(Style.STROKE);
			}
			// 短径を描画
			canvas.drawRect(left, top, left + leftSize, top + this.boxHeight,
					this.paint);

			// Textの描画
			this.paint.setColor(Color.GREEN);
			this.paint.setStyle(Style.FILL);
			this.paint.setTextSize(this.textSize);
			canvas.drawText(c, left + leftSize / 5, top + this.boxHeight
					- this.boxHeight / 5, this.paint);
			left = left + leftSize;
		}

		for (final String c : this.predictionCommandList) {
			int leftSize = c.length() * this.textSize;
			// 二段目に移動させる
			if ((left + leftSize) > this.width) {
				left = 0;
				top += this.boxHeight;
			}

			// 枠内がタッチされているかを判定する
			if (this.moveX > left && this.moveX < (left + leftSize)
					&& this.moveY > top && this.moveY < (top + this.boxHeight)) {
				this.paint.setColor(Color.GRAY);
				this.paint.setStyle(Style.FILL);
				this.predictionVariable = ""; //$NON-NLS-1$
				this.predictionFunction = ""; //$NON-NLS-1$
				this.predictionCommand = c;
			} else {
				this.paint.setColor(Color.GRAY);
				this.paint.setStyle(Style.STROKE);
			}
			// 短径を描画
			canvas.drawRect(left, top, left + leftSize, top + this.boxHeight,
					this.paint);

			// Textの描画
			this.paint.setColor(Color.WHITE);
			this.paint.setStyle(Style.FILL);
			this.paint.setTextSize(this.textSize);
			canvas.drawText(c, left + leftSize / 3, top + this.boxHeight
					- this.boxHeight / 5, this.paint);
			left = left + leftSize;
		}

		for (final String c : this.predictionFunctionList) {
			int leftSize = c.length() * this.textSize;
			// 二段目に移動させる
			if ((left + leftSize) > this.width) {
				left = 0;
				top += this.boxHeight;
			}

			// 枠内がタッチされているかを判定する
			if (this.moveX > left && this.moveX < (left + leftSize)
					&& this.moveY > top && this.moveY < (top + this.boxHeight)) {
				this.paint.setColor(Color.GRAY);
				this.paint.setStyle(Style.FILL);
				this.predictionVariable = ""; //$NON-NLS-1$
				this.predictionFunction = c;
				this.predictionCommand = ""; //$NON-NLS-1$

			} else {
				this.paint.setColor(Color.GRAY);
				this.paint.setStyle(Style.STROKE);
				// this.predictionFunction="";
			}
			// 短径を描画
			canvas.drawRect(left, top, left + leftSize, top + this.boxHeight,
					this.paint);

			// Textの描画
			this.paint.setColor(Color.CYAN);
			this.paint.setStyle(Style.FILL);
			this.paint.setTextSize(this.textSize);
			canvas.drawText(c, left + leftSize / 3, top + this.boxHeight
					- this.boxHeight / 5, this.paint);
			left = left + leftSize;
		}

		left = 0;
		top = 0;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		this.moveX = event.getX();
		this.moveY = event.getY();
		invalidate();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_UP:

			if (!this.predictionFunction.equals("")) { //$NON-NLS-1$
				this.lisner.addFunction(this.predictionFunction,
						this.getLastCursorPoint());
			}

			if (!this.predictionCommand.equals("")) { //$NON-NLS-1$
				this.lisner.addCommand(this.predictionCommand,
						this.getLastCursorPoint());
			}
			if (!this.predictionVariable.equals("")) { //$NON-NLS-1$
				this.lisner.addVariable(this.predictionVariable,
						this.getLastCursorPoint());
			}
			this.predictionFunction = ""; //$NON-NLS-1$
			this.predictionVariable = ""; //$NON-NLS-1$
			this.predictionCommand = ""; //$NON-NLS-1$
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		default:
			break;
		}
		return true;
	}

}
