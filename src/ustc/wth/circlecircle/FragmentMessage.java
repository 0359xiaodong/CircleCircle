package ustc.wth.circlecircle;

import global.Uris;
import entity.SmsInfo;

import java.util.List;
import service.SmsService;
import android.support.v4.app.ListFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import entity.ThreadInfo;
import adapter.*;

public class FragmentMessage extends ListFragment {
	private List<ThreadInfo> threads;
	private SmsService sms;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_message, container, false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sms = new SmsService(this.getActivity());
		threads = sms.getSmsInfo();
		setListAdapter(new SmsListAdapter(this.getActivity(), threads));
		String unReadNum = sms.getUnreadNum();
		Toast.makeText(getActivity(), "����" + unReadNum + "������Ϣ��",
				Toast.LENGTH_LONG).show();
	}

	public void onListItemClick(ListView parent, View v, int position, long id) {
		// ����һ��Intent����
		Intent intent = new Intent();
		// ��Intent���������һ����ֵ��
		intent.putExtra("testIntent", "123");
		// ����Intent����Ҫ������Activity
		intent.setClass(getActivity(), MessageThreadActivity.class);
		// ͨ��Intent������������һ��Activity
		startActivity(intent);
	}

}