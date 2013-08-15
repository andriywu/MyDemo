package com.example.mydemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements SensorEventListener{
	
	//摇一摇功能
	private SensorManager mSensorManager = null;
	
	private final int  changeTime = 500;
	AlarmThread alarmThread = null;
	boolean isScreenAlarm = false;
	private final int MSG_SCREEN_ALARM = 001;
	int colorCount = 0;//颜色计数
	String[] colorStr = {//颜色数组
			"#FFFFFF",
			"#CE0000",
			"#019858",
			"#0000C6",
			"#00DB00",
			"#F9F900"
	};
	
	RelativeLayout rlMain;
	LinearLayout layout;
	AnimationDrawable drawable;
	
	private boolean isShock = false;
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_SCREEN_ALARM:
				if(colorCount < colorStr.length){
					rlMain.setBackgroundColor(new Color().parseColor(colorStr[colorCount]));
					colorCount++;
				}else{
					colorCount = 0;
					rlMain.setBackgroundColor(new Color().parseColor(colorStr[colorCount]));
					colorCount++;
				}
				Message msg1 = new Message();
				msg1.what = MSG_SCREEN_ALARM;
				handler.sendMessageDelayed(msg1, changeTime);
				break;
			default:
				break;
			}
		};
	};
	
	public void clickStart(View v){
		startAlarmAnim();
	}
	
	public void clickStop(View v){
		stopAlarmAnim();
	}
	
	public void clickShock(View v){
		isShock = !isShock;
		//根据标志位 刷新ui 实现功能
		if(isShock){
			
		}else{
			
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rlMain = (RelativeLayout) findViewById(R.id.bg_main);
//		layout = (LinearLayout) findViewById(R.id.layout);
//		drawable = (AnimationDrawable) layout.getBackground();
		//摇一摇
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//摇一摇
		if (mSensorManager != null) {// 注册监听器 
			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL); 
            // 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率 
        } 
	}
	
	/**
	 * 开启报警背景动画
	 */
	private void startAlarmAnim(){
		isScreenAlarm = true;
		colorCount = 0;
//		if(alarmThread == null){
//			alarmThread = new AlarmThread();
//		}
//		alarmThread.run();
		Message msg = new Message();
		msg.what = MSG_SCREEN_ALARM;
		handler.sendMessage(msg);
//		new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				while(isScreenAlarm){
////					handler.obtainMessage(MSG_SCREEN_ALARM);
//					Message msg = new Message();
//					msg.what = MSG_SCREEN_ALARM;
//					handler.sendMessage(msg);
//					try {
//						Thread.currentThread().sleep(changeTime);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//		}.run();
	}
	
	/**
	 * 关闭报警背景动画
	 */
	private void stopAlarmAnim(){
		isScreenAlarm = false;
		handler.removeMessages(MSG_SCREEN_ALARM);
		rlMain.setBackgroundColor(Color.WHITE);
	}
	
	class AlarmThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(isScreenAlarm){
				handler.obtainMessage(MSG_SCREEN_ALARM);
				try {
					Thread.currentThread().sleep(changeTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
//		drawable.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	
	//摇一摇
	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		 int sensorType = event.sensor.getType();
	        float[] values = event.values;
	        if (sensorType == Sensor.TYPE_ACCELEROMETER){
	            if (Math.abs(values[0]) > 14 || Math.abs(values[1]) > 14 || Math.abs(values[2]) > 14){
//	                mVibrator.vibrate(100);
//	                TextView tv1 = (TextView)findViewById(R.id.textView1);
//	                SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
//	                tv1.setText(f.format(new Date()) + "手机摇动了...");  
	            	Log.w("","----摇动----");
	            }
	        }
	}
	
	

}
