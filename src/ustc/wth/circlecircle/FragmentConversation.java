package ustc.wth.circlecircle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import service.SmsService;
import utils.CharacterParser;
import utils.ClearEditText;
import utils.ConvNameFormat;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import async.ConvNameAsyncLoader;
import entity.ContactInfo;
import entity.ConversationInfo;
import adapter.*;

public class FragmentConversation extends ListFragment implements
		OnItemLongClickListener {
	private List<ConversationInfo> conversations;
	private SmsService sms;
	private PopupWindow pw;
	private ClearEditText mClearEditText;
	private ConversationListAdapter convListAdapter;
	private ImageButton add_sms;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_conversation, container,
				false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sms = new SmsService(this.getActivity());
		conversations = sms.getSmsInfo();
		convListAdapter = new ConversationListAdapter(this.getActivity(),
				conversations);
		new ConvNameAsyncLoader(getActivity(), convListAdapter, conversations)
				.execute();
		setListAdapter(convListAdapter);
		String unReadNum = sms.getUnreadNum(); // ��ȡ����Ϣ��Ŀ
		Toast.makeText(getActivity(), "����" + unReadNum + "������Ϣ��",
				Toast.LENGTH_LONG).show();
		
	}

	public void onListItemClick(ListView parent, View v, int position, long id) {
		ConversationInfo conv = conversations.get(position);
		TextView tv = (TextView) v.findViewById(R.id.name);
		Bundle b = new Bundle();
		// ����һ��Intent����
		Intent intent = new Intent();
		if(!conv.getIsMass()){
			intent.putExtra("contactName", conv.getCti().getName());
			intent.putExtra("phone", conv.getCti().getPhone());
			b.putParcelable("photo", conv.getCti()
					.getPhoto());
		}else{
			b.putParcelableArray("contactInfos", conv.getCtis());
		}
		
		intent.putExtra("id", conv.getId());
		intent.putExtra("isMass", conv.getIsMass());
		intent.putExtra("name", tv.getText());
		intent.putExtras(b);
		intent.setClass(getActivity(), ConversationActivity.class);
		// ͨ��Intent������������һ��Activity
		startActivity(intent);
		sms.markUnread(conv.getId());
		for(int i=0;i<conversations.size();i++){
			if(conversations.get(i).getId() == conv.getId()){
				conversations.get(i).setRead(1);
			}
		}
		convListAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		LinearLayout pv = (LinearLayout) LayoutInflater.from(getActivity())
				.inflate(R.layout.pop_up_conversation, null);

		pw = new PopupWindow(getActivity());
		pw.setContentView(pv);
		Drawable dw = getResources().getDrawable(R.drawable.qzone_bg_copy);
		pw.setBackgroundDrawable(dw);

		TextView tvCall = (TextView) pv.findViewById(R.id.conversation_call);
		TextView tvDel = (TextView) pv.findViewById(R.id.conversation_delete);
		ImageView ivLine = (ImageView) pv.findViewById(R.id.line);

		ConversationInfo ci = conversations.get(arg2);
		if (ci.getIsMass()) {
			tvCall.setVisibility(View.GONE);
			ivLine.setVisibility(View.GONE);
		} else {
			tvCall.setOnClickListener(new TelCallListener(ci.getCti()
					.getPhone()));
		}

		// popwindow�ĳ��Ϳ�ģ�����Ҫ���õģ���Ȼ�޷���ʾ��
		pw.setWidth(400);
		pw.setHeight(150);

		pw.setOutsideTouchable(true);
		pw.setFocusable(true);

		View line = arg1;
		pw.showAsDropDown(line, line.getWidth() / 2 - 200,
				-line.getHeight() - 75);

		tvDel.setOnClickListener(new ConDelListener(ci.getId(), ci));
		return false;
	}

	class TelCallListener implements OnClickListener {

		private String phone;

		TelCallListener(String phone) {
			this.phone = phone;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			pw.dismiss();
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ phone));
			startActivity(intent);
			pw = null;
		}

	}

	class ConDelListener implements OnClickListener {

		private long id;
		private ConversationInfo ci;

		ConDelListener(long id, ConversationInfo ci) {
			this.ci = ci;
			this.id = id;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			pw.dismiss();
			sms.deleteConversation(id);
			convListAdapter.removeConversation(ci);
			convListAdapter.notifyDataSetChanged();
			pw = null;
		}

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		add_sms = (ImageButton) getActivity().findViewById(R.id.add_sms);
		add_sms.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), SmsAddActivity.class);
				// ͨ��Intent������������һ��Activity
				startActivity(intent);
			}
			
		});
		getListView().setOnItemLongClickListener(this);
		mClearEditText = (ClearEditText) getActivity().findViewById(
				R.id.message_filter); // �Զ���ClearEditText
		// Ϊeditatext�����Ӧ�¼�
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				// ���ݸı���������������Դ
				filterData(s.toString());

			}

		});

	}

	/**
	 * ����������е�ֵ���������ݲ�����ListView��Ŀǰ���԰���ƴ��������������������Ŀǰ��֧��ȫƴ
	 */
	private void filterData(String filterStr) {
		String search = null;
		CharacterParser characterParser = CharacterParser.getInstance();
		// �½�һ��SortModel���͵�List
		List<ConversationInfo> filterDateList = new ArrayList<ConversationInfo>();

		// �ж�EditText���Ƿ�Ϊ��
		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = conversations;
		} else {
			filterDateList.clear();
			for (ConversationInfo ConvInfo : conversations) {
				if (!ConvInfo.getIsMass()) {
					search = ConvInfo.getCti().getName();
					if (search == null) {
						search += ConvInfo.getCti().getPhone();
					}
				} else {
					search = ConvNameFormat.ConvNameFormat(ConvInfo.getCtis());
				}

				if (search != null) {
					// �����ַ���indexof��string�����ִ�string�ڸ������״γ��ֵ�λ�ã���0��ʼ��û�з���-1�������жϺͽ�ȡ�ַ�����

					// if(name.indexOf(filterStr.toString()) != -1 ||
					// characterParser.getSelling(name).startsWith(filterStr.toString())){
					if (search.indexOf(filterStr.toString()) != -1
							|| characterParser.getSelling(search).startsWith(
									filterStr.toString())) {
						// filterDateList�е����ݸı�

						filterDateList.add(ConvInfo);
					}
				}
			}
		}

		convListAdapter.updateListView(filterDateList);
	}

}