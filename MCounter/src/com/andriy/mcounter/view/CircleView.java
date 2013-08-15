package com.andriy.mcounter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.view.View;

/**
 * @author AndriyWu
 *
 */
public class CircleView extends View {

	Paint mPaint,//中间的线
			mCountPaint;//外围用来计时的线
	Canvas mCanvas;
	private int radius = 100,//半径
						circleWidth = 20;//外圈厚度
	int angle = 360;
	private final int UPDATE_VIEW = 0;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case UPDATE_VIEW:
				invalidate();
				break;

			default:
				break;
			}
		};
	};
	
	public CircleView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		//初始化中间圆的画笔
		mPaint = new Paint();
		mPaint.setColor(Color.BLUE);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(10);
		mPaint.setAntiAlias(true);
		//初始化外部圆的画笔
		mCountPaint = new Paint();
		mCountPaint.setColor(Color.RED);
		mCountPaint.setStyle(Style.STROKE);
		mCountPaint.setStrokeWidth(circleWidth);
		mCountPaint.setAntiAlias(true);
		
		mCanvas = new Canvas();
	}
	
	/**
	 * 设置半径
	 * @param radius
	 */
	public void setRadius(int radius){
		this.radius = radius;
	}
	
	/**
	 * 设置外圈半径
	 * @param circleWidth
	 */
	public void setCircleWidth(int circleWidth){
		this.circleWidth = circleWidth;
	}
	
	/**
	 * 设置中间线条的颜色
	 * @param color
	 */
	public void setMiddleColor(String colorStr){
		
	}
	
	/**
	 * 设置外圈颜色
	 * @param colorStr
	 */
	public void setOutsideColor(String colorStr){
		
	}
	
	/**
	 * 设置时间， 0~60
	 * @param count
	 */
	public void setCount(int count){
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		int width = getWidth();
		int height = getHeight();
		mPaint.setColor(Color.BLUE);
		mPaint.setStrokeWidth(10);
		canvas.drawCircle(width/2, height/2, radius, mPaint);
		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(circleWidth);
		canvas.drawArc(new RectF(width/2-radius, height/2-radius, width/2+radius, height/2+radius), 0, angle, false, mPaint);
	}
	
	public void runAnimation(){
		mThread.start();
	}
	
	Thread mThread = new Thread(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			while(true){
				try {
					sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				angle = angle -10;
				Message msg   = new Message();
				msg.what = UPDATE_VIEW;
				handler.sendMessage(msg);
			}
		}
		
	};

}
