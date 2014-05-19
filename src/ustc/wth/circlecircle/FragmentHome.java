package ustc.wth.circlecircle;

import java.util.ArrayList;
import java.util.List;

import entity.CallInfo;
import entity.ConversationInfo;
import service.CallService;
import ustc.wth.circlecircle.FragmentConversation.ConDelListener;
import ustc.wth.circlecircle.FragmentConversation.TelCallListener;
import utils.CharacterParser;
import utils.ClearEditText;
import utils.ConvNameFormat;
import utils.Uris;
import adapter.CallListAdapter;
import adapter.ConversationListAdapter;
import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;
import async.ConvNameAsyncLoader;

public class FragmentHome extends ListFragment implements
OnItemLongClickListener {
	private CallService cs;
	private List<CallInfo> callList;
	private CallListAdapter callListAdapter;
	PopupWindow pw;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home, container,
				false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		cs = new CallService(this.getActivity());
		callList = cs.getCallLog();
		callListAdapter = new CallListAdapter(this.getActivity(),
				callList);
		setListAdapter(callListAdapter);
		//getActivity().getContentResolver().registerContentObserver(CallLog.Calls.CONTENT_URI, true, new CallObserver(new Handler()));
		
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
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

				CallInfo callInfo = callList.get(arg2);
				tvCall.setVisibility(View.GONE);
				ivLine.setVisibility(View.GONE);


				// popwindow�ĳ��Ϳ�ģ�����Ҫ���õģ���Ȼ�޷���ʾ��
				pw.setWidth(400);
				pw.setHeight(150);

				pw.setOutsideTouchable(true);
				pw.setFocusable(true);

				View line = arg1;
				pw.showAsDropDown(line, line.getWidth() / 2 - 200,
						-line.getHeight() - 75);

				tvDel.setOnClickListener(new CallDelListener(callInfo));
		return false;
	}	
	
	class CallDelListener implements OnClickListener {
		private CallInfo ci;

		CallDelListener(CallInfo ci) {
			this.ci = ci;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			pw.dismiss();
			cs.deleteCall(ci.getPhone());
			callListAdapter.removeCall(ci);
			callListAdapter.notifyDataSetChanged();
			pw = null;
		}

	}
	
	private final class CallObserver extends ContentObserver{  
        public CallObserver(Handler handler) {  
            super(handler);  
        }  
        @Override  
        public void onChange(boolean selfChange) {  
        	callList = cs.getCallLog();
        	callListAdapter.updateListView(callList);
            }  
        } 
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getListView().setOnItemLongClickListener(this);
		ClearEditText mClearEditText = (ClearEditText) getActivity().findViewById(
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
		List<CallInfo> filterDateList = new ArrayList<CallInfo>();

		// �ж�EditText���Ƿ�Ϊ��
		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = callList;
		} else {
			filterDateList.clear();
			for (CallInfo callInfo : callList) {
				search = callInfo.getName() + callInfo.getPhone();

				if (search != null) {
					// �����ַ���indexof��string�����ִ�string�ڸ������״γ��ֵ�λ�ã���0��ʼ��û�з���-1�������жϺͽ�ȡ�ַ�����

					// if(name.indexOf(filterStr.toString()) != -1 ||
					// characterParser.getSelling(name).startsWith(filterStr.toString())){
					if (search.indexOf(filterStr.toString()) != -1
							|| characterParser.getSelling(search).startsWith(
									filterStr.toString())) {
						// filterDateList�е����ݸı�

						filterDateList.add(callInfo);
					}
				}
			}
		}

		callListAdapter.updateListView(filterDateList);
	}
	

}
	
