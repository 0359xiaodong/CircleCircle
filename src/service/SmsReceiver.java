package service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		// ��ʾ�Է��ɹ��յ�����
		boolean isMass = intent.getBooleanExtra("isMass", false);
		if(!isMass){
		Toast.makeText(context, "�Է����ճɹ�", Toast.LENGTH_LONG).show();
		}
	}
};
