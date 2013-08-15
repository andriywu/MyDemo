package com.andriy.mcounter;

import java.util.List;

import com.andriy.mcounter.interfaces.CountFinishListener;
import com.andriy.mcounter.utils.MDialogUtil;
import com.andriy.mcounter.utils.TimePickerDialog;
import com.andriy.mcounter.view.CircleView;
import com.andriy.mcounter.view.ThreeCircleView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements CountFinishListener{
	
	//��������
	boolean isScreenAlarm = false;//��˸��Ļ����
	boolean isVibrate  = false;//�𶯱���
	boolean isSound = false;//��������
	boolean isAlarm = false;//�Ƿ񱨾�״̬
	
	final int changeTime = 500;//�������ʱ��
	
	private int timeCount = 0;//ʱ�����
	ThreeCircleView mView;
	boolean flag = false;//��ʼ/ֹͣ��־
	private TimePickerDialog timePickerDialog;
	Button btnStart;
	LinearLayout lyMain;
	private final int  MSG_RESET = 1;
	private final int MSG_FINISH = 2;
	private final int MSG_ALARM = 3;
	
	int colorCount = 0;//��ɫ����
	String[] colorStr = {//��ɫ����
			"#CE0000",
			"#019858",
			"#0000C6",
			"#00DB00",
			"#F9F900"
	};
	
	Handler handler = new Handler (){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_RESET:
				timePickerDialog = null;
				mView.stopCounter();
				mView.setTimeCount(timeCount);
				mView.resetCounter();
				break;
			case MSG_FINISH:
				isAlarm = true;
				isScreenAlarm = true;//������
				Toast.makeText(getApplicationContext(), "Finish!", Toast.LENGTH_SHORT).show();
				updateUI(false);
				{
					Message message = new Message();
					message.what = MSG_ALARM;
					handler.sendMessage(message);
				}
				break;
			case MSG_ALARM:
				
				if(isAlarm){
					//����ı�������������������־λ
					if(isScreenAlarm){
						if(colorCount < colorStr.length){
							lyMain.setBackgroundColor(new Color().parseColor(colorStr[colorCount]));
							colorCount++;
						}else{
							colorCount = 0;
							lyMain.setBackgroundColor(new Color().parseColor(colorStr[colorCount]));
							colorCount++;
						}
					}
					Message message = new Message();
					message.what = MSG_ALARM;
					handler.sendMessageDelayed(message, changeTime);
				}
				break;
			default:
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		timePickerDialog = new TimePickerDialog(this);
		mView = new ThreeCircleView(this);
		initUI();
//		mView = (ThreeCircleView) findViewById(R.id.view_circle);
//		mView.setRadius(150);
//		mView.startCounter();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mView.registerListener(this);
	}

	private void initUI() {
	// TODO Auto-generated method stub
		//��ȡ��Ļ�߿�
		setContentView(R.layout.activity_main);
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();//��Ļ���
		mView = (ThreeCircleView) findViewById(R.id.view_circle);
		btnStart = (Button) findViewById(R.id.btn_start);
		lyMain = (LinearLayout) findViewById(R.id.ly_bg);
		mLog(width+"");
//		mLog(width*2/3+"");
		mView.setRadius(width/3);
//		mView.startCounter();
		mView.excute();
		
	}
	
	/**
	 * ����UI
	 * @param flag true:��ʼ��ʱ false:ֹͣ��ʱ
	 */
	private void updateUI(boolean flag){
		if(flag){//��ʼ��ʱ
			btnStart.setText(R.string.main_stop);
			mView.startCounter();
		}else{//ֹͣ��ʱ
			btnStart.setText(R.string.main_start);
			mView.stopCounter();
		}
	}
	
	public  void btnStart(View v) {
		flag = !flag;
		updateUI(flag);
	}
	
	/**
	 * �������� �򿪳����б�
	 * @param v
	 */
	public void btnList(View v){
		timePickerDialog = new TimePickerDialog(this);
		List<Button> btns = timePickerDialog.showPickTimeDialog();
		btns.get(0).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				timePickerDialog.dismiss();
			}
		});
		btns.get(1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String timeStr = timePickerDialog.getTime();
				String[] time  = timeStr.split("-");
				int h = Integer.parseInt(time[0]);
				int m = Integer.parseInt(time[1]);
				int s = Integer.parseInt(time[2]);
				timeCount = h*3600 + m * 60 + s;
				Message msg = new Message();
				msg.what = MSG_RESET;
				handler.sendMessage(msg);
				timePickerDialog.dismiss();
			}
		});
	}
	
	
	//����ֹͣ����
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//����ֹͣ���� ������������δĬ��
		isAlarm = false;
		lyMain.setBackgroundResource(R.drawable.shap_background);
		return super.onTouchEvent(event);
	}
	
	/**
	 * @param v
	 */
	public void btnExit(View v){
//		MDialogUtil.getInstance(this).showDialogSingleBtn(this, R.string.main_exit, R.string.main_exit, R.string.main_exit);
//		new TimePickerDialog(this).showPickTimeDialog();
		System.exit(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void mLog(String log){
		
	}

	@Override
	public void alarm() {
		// TODO Auto-generated method stub
//		Toast.makeText(getApplicationContext(), "Finish!", Toast.LENGTH_SHORT).show();
		Message msg = new Message();
		msg.what = MSG_FINISH;
		handler.sendMessage(msg);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mView.unregisterListner();
	}

}
