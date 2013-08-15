package com.andriy.mcounter.utils;

import java.util.ArrayList;
import java.util.List;

import com.andriy.mcounter.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MDialogUtil {
	private static MDialogUtil dialogUtil = null;
	private static ArrayList<Button> list;
	private Dialog dialog;

	/**
	 * 私有化构造方法
	 */
	private MDialogUtil(){
		list = new ArrayList<Button>();
	}
	
	/**
	 * 获取单例
	 * @param ctx
	 * @return
	 */
	public static MDialogUtil getInstance(Context ctx){
		if(dialogUtil == null){
			dialogUtil = new MDialogUtil();
		}
		return dialogUtil;
	}
	
	public Button showDialogSingleBtn(Context ctx, int title, int content, int btn){
		LayoutInflater layoutInflater = LayoutInflater.from(ctx);
		LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.dialog_one_btn	, null);
		TextView tvTitle = (TextView) linearLayout.findViewById(R.id.tv_dialog_title);
		TextView tvContent = (TextView) linearLayout.findViewById(R.id.tv_dialog_content);
		Button button = (Button) linearLayout.findViewById(R.id.btn_single);
		tvTitle.setText(title);
		tvContent.setText(content);
		button.setText(btn);
		dialog = new Dialog(ctx,R.style.dialogStyle);
		dialog.setContentView(linearLayout);
		try {
			dialog.show();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return button;
	}
	
}
