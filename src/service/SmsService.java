package service;

import java.util.ArrayList;
import java.util.List;
import entity.SmsInfo;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

/**
 * class name��SmsService
 * class description����ȡ�ֻ��еĸ��ֶ�����Ϣ
 * PS�� ��ҪȨ�� <uses-permission android:name="android.permission.READ_SMS" />
 * Date:2014-2-21<BR>
 * 
 * @version 1.00
 * @author YF
 */
public class SmsService {
	private Activity activity;
	private Uri uri;
	private String[] projection = new String[] { "_id", "address", "person",
			"body", "date", "type" };
	private List<SmsInfo> infos;

	public SmsService(Activity activity, Uri uri) {
		infos = new ArrayList<SmsInfo>();
		this.activity = activity;
		this.uri = uri;
	}

	/**
	 * Role:��ȡ���ŵĸ�����Ϣ
	 * Date:2014-2-21
	 * 
	 */
	public List<SmsInfo> getSmsInfo() {
		
		ContentResolver resolver = activity.getContentResolver();
		//ʹ��hack���distinct��ѯ��Ҳ���Լ���дproviderʵ��
		Cursor cursor = resolver.query(uri, 
	            new String[]{"DISTINCT address", "body", "person", "date", "type"}, //DISTINCT
	            "address IS NOT NULL) GROUP BY (address", //GROUP BY
	            null, null);
		int nameColumn = cursor.getColumnIndex("person");
		int phoneNumberColumn = cursor.getColumnIndex("address");
		int smsbodyColumn = cursor.getColumnIndex("body");
		int dateColumn = cursor.getColumnIndex("date");
		int typeColumn = cursor.getColumnIndex("type");
		if (cursor != null) {
			while (cursor.moveToNext()) {
				SmsInfo smsinfo = new SmsInfo();
				smsinfo.setName(cursor.getString(nameColumn));
				smsinfo.setDate(cursor.getString(dateColumn));
				smsinfo.setPhoneNumber(cursor.getString(phoneNumberColumn));
				smsinfo.setSmsbody(cursor.getString(smsbodyColumn));
				smsinfo.setType(cursor.getString(typeColumn));
				infos.add(smsinfo);
			}
			cursor.close();
		}
		return infos;
	}
}