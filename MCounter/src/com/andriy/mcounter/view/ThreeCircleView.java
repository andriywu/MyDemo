package com.andriy.mcounter.view;

import com.andriy.mcounter.interfaces.CountFinishListener;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;

public class ThreeCircleView extends View {
	CountFinishListener mCountFinishListener = null;
	boolean threadFlag = false;
	
	public ThreeCircleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		mPaint = new Paint();
		mPaint.setStyle(Style.STROKE);
		mPaint.setAntiAlias(true);
		calculate();
		getCounts();
	}

	/**
	 * 注册监听器
	 * @param listener
	 */
	public void registerListener(CountFinishListener listener){
		this.mCountFinishListener = listener;
	}
	
	public void unregisterListner(){
		this.mCountFinishListener = null;
	}
	
	public ThreeCircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mPaint = new Paint();
		mPaint.setStyle(Style.STROKE);
		mPaint.setAntiAlias(true);
		calculate();
		getCounts();
	}

	//画笔、画布
	int width, height;
	int timeCount = 0;//时间参数，单位：秒
	Paint mPaint;
	float circleWidth = 10;
	int cricleColor = Color.WHITE;
	float radius = 150;//总半径，与秒圈半径相同
	
	//小时圈属性
	float hRadius;
	float hWidth = 20;
	int hColor = Color.GREEN;
	int hCount;
	
	//分钟圈属性
	float mRadius;
	float mWidth = 20;
	int mColor = Color.YELLOW;
	int mCount;
	
	//秒圈属性
	float sWidth = 20;
	int sColor = Color.RED;
	int sCount;
	
	//文字属性
	int textColor = Color.WHITE;
	private final int UPDATE_VIEW = 0;
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == UPDATE_VIEW ){
				getCounts();
				invalidate();
			}
		};
	};
	
	
	public ThreeCircleView(Context context) {
		super(context);
		mPaint = new Paint();
		mPaint.setStyle(Style.STROKE);
		mPaint.setAntiAlias(true);
		calculate();
		getCounts();
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
//		Log.w("three_circle","radius:"+radius+",mRadius:"+mRadius+",hRadius:"+hRadius);
		width = getWidth()/2;
		height = getHeight()/2;
		Log.w("three_circle","w:"+width+",h:"+height);
		mPaint.setColor(cricleColor);
		mPaint.setStrokeWidth(circleWidth);
		canvas.drawCircle(width, height, hRadius, mPaint);
		canvas.drawCircle(width, height, mRadius, mPaint);
		canvas.drawCircle(width, height, radius, mPaint);
		mPaint.setColor(hColor);
		mPaint.setStrokeWidth(hWidth);
		canvas.drawArc(new RectF(width-hRadius, height-hRadius, width+hRadius, height+hRadius), 0, hCount*6, false, mPaint);
		mPaint.setColor(mColor);
		mPaint.setStrokeWidth(mWidth);
		canvas.drawArc(new RectF(width-mRadius, height-mRadius, width+mRadius, height+mRadius), 0, mCount*6, false, mPaint);
		mPaint.setColor(sColor);
		mPaint.setStrokeWidth(sWidth);
		canvas.drawArc(new RectF(width-radius, height-radius, width+radius, height+radius), 0, sCount*6, false, mPaint);
//		mPaint.setColor(textColor);
//		Paint paint = new Paint();
//		paint.setAntiAlias(true);
		mPaint.setColor(textColor);
		mPaint.setTextSize(70);
		mPaint.setStrokeWidth(1);
		FontMetrics fontMetrics = mPaint.getFontMetrics();
//		String text = hCount+":"+mCount+":"+sCount;
		String text = String.format("%02d", hCount)+":"+String.format("%02d", mCount)+":"+String.format("%02d", sCount);
		float y  = height + (fontMetrics.bottom - fontMetrics.top)/4;
		mPaint.setTextAlign(Paint.Align.CENTER);
		canvas.drawText(text, width, y, mPaint);
		//hCount+":"+mCount+":"+sCount
	}
	
	/**
	 * 计算绘图属性
	 */
	private void calculate() {
		// TODO Auto-generated method stub
		mRadius = radius - sWidth/2 - mWidth/2;
		hRadius = mRadius - mWidth/2 - hWidth/2;
//		Log.w("three_circle","radius:"+radius+",mRadius:"+mRadius+",hRadius:"+hRadius);
	}
	
	/**
	 * 计算时间
	 */
	private void getCounts(){
		hCount = timeCount/3600;
		sCount = timeCount%60;
		mCount = timeCount%3600/60;
		Log.w("three_circle","h:"+hCount+",m:"+mCount+",s:"+sCount);
	}
	
	public void excute(){
		if(mThread.getState() == Thread.State.WAITING){
			mThread.notify();
		}else{
			mThread.start();
		}
	}
	
	/**
	 * 开启计时
	 */
	public void startCounter(){
//		if(mThread.getState() == Thread.State.WAITING){
//			mThread.notify();
//		}else{
//			mThread.start();
//		}
		threadFlag = true;
		
	}
	
	/**
	 * 停止计时
	 */
	public void stopCounter(){
//			mThread.stop();
		threadFlag = false;
	}
	
	/**
	 * 重置计时
	 */
	public void resetCounter(){
		calculate();
		getCounts();
		 invalidate();
//		Message msg = new Message();
//		msg.what = UPDATE_VIEW;
//		handler.sendMessage(msg);
	}
	
	Thread mThread = new Thread(){
		public void run() {
			while(true){
				if (threadFlag) {
					try {
						Thread.currentThread().sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(timeCount == 0){
						if(mCountFinishListener != null){
							mCountFinishListener.alarm();
							stopCounter();
						}
					}else{
						Message msg = new Message();
						msg.what = UPDATE_VIEW;
						handler.sendMessage(msg);
						timeCount--;
					}
				}
			}
		
		};
	};
	
	
	public float getCircleWidth() {
		return circleWidth;
	}

	public void setCircleWidth(float circleWidth) {
		this.circleWidth = circleWidth;
	}

	public int getCricleColor() {
		return cricleColor;
	}

	public void setCricleColor(int cricleColor) {
		this.cricleColor = cricleColor;
	}

	public float gethWidth() {
		return hWidth;
	}

	public void sethWidth(float hWidth) {
		this.hWidth = hWidth;
	}

	public int gethColor() {
		return hColor;
	}

	public void sethColor(int hColor) {
		this.hColor = hColor;
	}

	public float getmWidth() {
		return mWidth;
	}

	public void setmWidth(float mWidth) {
		this.mWidth = mWidth;
	}

	public int getmColor() {
		return mColor;
	}

	public void setmColor(int mColor) {
		this.mColor = mColor;
	}

	public float getsWidth() {
		return sWidth;
	}

	public void setsWidth(float sWidth) {
		this.sWidth = sWidth;
	}

	public int getsColor() {
		return sColor;
	}

	public void setsColor(int sColor) {
		this.sColor = sColor;
	}
	
	public int getTimeCount() {
		return timeCount;
	}

	public void setTimeCount(int timeCount) {
		this.timeCount = timeCount;
	}
	
	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
		//设置了半径，顺便重新设置view的大小
		ViewGroup.LayoutParams layoutParams = getLayoutParams();
		layoutParams.width = (int) (2*radius+20);
		layoutParams.height = (int) (2*radius+20);
		setLayoutParams(layoutParams);
		resetCounter();
	}

}
