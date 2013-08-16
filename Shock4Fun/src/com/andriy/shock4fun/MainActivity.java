package com.andriy.shock4fun;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity implements SensorEventListener{
	//摇一摇功能
	private SensorManager mSensorManager = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
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
	            	//检测到“摇一摇”动作，发送指令去服务端那一条信息，调用语音服务
	            }
	        }
	}

}
