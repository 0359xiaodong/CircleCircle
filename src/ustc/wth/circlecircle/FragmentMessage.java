package ustc.wth.circlecircle;

import java.util.List;
import service.SmsService;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import entity.ThreadInfo;
import adapter.*;

public class FragmentMessage extends ListFragment implements
		OnItemLongClickListener {
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
		String unReadNum = sms.getUnreadNum(); // ��ȡ����Ϣ��Ŀ
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

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		PopupWindow pw;
		LinearLayout pv = (LinearLayout) LayoutInflater.from(
				getActivity()).inflate(R.layout.pop_up_menu, null);

		pw = new PopupWindow(getActivity());
		pw.setContentView(pv);
		Drawable dw = getResources().getDrawable(R.drawable.qzone_bg_copy);
		pw.setBackgroundDrawable(dw);

		TextView tvCall = (TextView) pv.findViewById(R.id.thread_call);
		TextView tvDel = (TextView) pv.findViewById(R.id.thread_delete);
		ImageView ivLine = (ImageView) pv.findViewById(R.id.line);
		
		ThreadInfo ti = threads.get(arg2);
		if(ti.getIsMass() == 1){
			tvCall.setVisibility(View.GONE);
			ivLine.setVisibility(View.GONE);
		}

		// popwindow�ĳ��Ϳ�ģ�����Ҫ���õģ���Ȼ�޷���ʾ��
		pw.setWidth(400);
		pw.setHeight(150);

		pw.setOutsideTouchable(true);
		pw.setFocusable(true);

		View line = arg1;
        pw.showAsDropDown(line, line.getWidth()/2-200, -line.getHeight()-75);
         
		return false;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getListView().setOnItemLongClickListener(this);
	}

}