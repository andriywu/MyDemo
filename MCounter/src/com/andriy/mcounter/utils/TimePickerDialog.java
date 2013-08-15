package com.andriy.mcounter.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.andriy.mcounter.R;

public class TimePickerDialog {
	EditText tvH, tvM, tvS;
	Button btnHA, btnHM, btnMA, btnMM, btnSA, btnSM, btnOk, btnCancel;
	private static ArrayList<Button> list;
	private Dialog dialog;
	int uiH = 0,
			uiM = 0,
			uiS = 0;
	DecimalFormat df;
	
	
	
	public TimePickerDialog(Context ctx) {
		super();
		// TODO Auto-generated constructor stub
		df = new DecimalFormat("00");
		list = new ArrayList<Button>();
		LayoutInflater layoutInflater = LayoutInflater.from(ctx);
		LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.dialog_timepicker	, null);
		tvH = (EditText) linearLayout.findViewById(R.id.et_h);
		tvM = (EditText) linearLayout.findViewById(R.id.et_m);
		tvS = (EditText) linearLayout.findViewById(R.id.et_s);
		tvH.setText(df.format(uiH));
		tvM.setText(df.format(uiM));
		tvS.setText(df.format(uiS));
		btnHA = (Button) linearLayout.findViewById(R.id.btn_add_h);
		btnHM = (Button) linearLayout.findViewById(R.id.btn_min_h);
		btnMA = (Button) linearLayout.findViewById(R.id.btn_add_m);
		btnMM = (Button) linearLayout.findViewById(R.id.btn_min_m);
		btnSA = (Button) linearLayout.findViewById(R.id.btn_add_s);
		btnSM = (Button) linearLayout.findViewById(R.id.btn_min_s);
		btnCancel = (Button) linearLayout.findViewById(R.id.btn_cancel);
		btnOk = (Button) linearLayout.findViewById(R.id.btn_ok);
		dialog = new Dialog(ctx,R.style.dialogStyle);
		dialog.setContentView(linearLayout);
		
		btnHA.setOnClickListener(onClickListener);
		btnHM.setOnClickListener(onClickListener);
		btnMA.setOnClickListener(onClickListener);
		btnMM.setOnClickListener(onClickListener);
		btnSA.setOnClickListener(onClickListener);
		btnSM.setOnClickListener(onClickListener);
		
		tvH.addTextChangedListener(textWatcherH);
		tvM.addTextChangedListener(textWatcherM);
		tvS.addTextChangedListener(textWatcherS);
	}
	
	TextWatcher textWatcherH = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			try {
				uiH = Integer.parseInt((s.toString()));
				if(uiH >60){
					uiH = 60;
					tvH.setText(60+"");
				}
			} catch (Exception e) {
				// TODO: handle exception
				uiH = 0;
			}
			
		}
	};
	
	TextWatcher textWatcherM = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			try {
				uiM = Integer.parseInt((s.toString()));
				if(uiM >59){
					uiM = 59;
					tvM.setText(59+"");
				}
			} catch (Exception e) {
				// TODO: handle exception
				uiM = 0;
			}
			
		}
	};
	
	TextWatcher textWatcherS = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			try {
				uiS = Integer.parseInt((s.toString()));
				if(uiS >59){
					uiS = 59;
					tvS.setText(59+"");
				}
			} catch (Exception e) {
				// TODO: handle exception
				uiS = 0;
			}
			
		}
	};
	
	OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == btnHA){
				if(uiH < 99){
					uiH++;
				}
			}
			else if(v == btnHM){
				if(uiH>0){
					uiH--;
				}else{
					uiH = 99;
				}
			}
			else if(v == btnMA){
				if(uiM < 59){
					uiM++;
				}
				if(uiM == 59){
					btnHA.performClick();
					uiM = 0;
				}
			}
			else if(v == btnMM){
				if(uiM>0){
					uiM--;
				}else{
					uiM = 59;
				}
			}
			else if(v == btnSA){
				if(uiS<59){
					uiS++;
				}
				if(uiS == 59){
					btnMA.performClick();
					uiS = 0;
				}
			}
			else if(v == btnSM){
				if(uiS>0){
					uiS--;
				}else{
					uiS = 59;
				}
			}
			tvH.setText(df.format(uiH));
			tvM.setText(df.format(uiM));
			tvS.setText(df.format(uiS));
		}
	};



	/**
	 * 弹出时间选择框
	 * @param ctx
	 * @return
	 */
	public List<Button> showPickTimeDialog(){
		list.clear();
		list.add(btnCancel);
		list.add(btnOk);
		try {
			dialog.show();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	public void resetTime(int h, int m, int s){
		uiH = h;
		uiM = m;
		uiS = s;
		tvH.setText(df.format(uiH));
		tvM.setText(df.format(uiM));
		tvS.setText(df.format(uiS));
		
	}
	
	public void dismiss(){
		this.dialog.dismiss();
	}
	
	/**
	 * 获取设置的时间
	 * 0-0-0
	 * @return
	 */
	public String getTime(){
		Log.w("","picktime:"+uiH+"-"+uiM+"-"+uiS);
		return uiH+"-"+uiM+"-"+uiS;
	}
}
