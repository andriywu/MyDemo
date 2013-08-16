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
	//ҡһҡ����
	private SensorManager mSensorManager = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//ҡһҡ
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//ҡһҡ
		if (mSensorManager != null) {// ע������� 
			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL); 
            // ��һ��������Listener���ڶ������������ô��������ͣ�����������ֵ��ȡ��������Ϣ��Ƶ�� 
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

	//ҡһҡ
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
//	                tv1.setText(f.format(new Date()) + "�ֻ�ҡ����...");  
	            	Log.w("","----ҡ��----");
	            	//��⵽��ҡһҡ������������ָ��ȥ�������һ����Ϣ��������������
	            }
	        }
	}

}
