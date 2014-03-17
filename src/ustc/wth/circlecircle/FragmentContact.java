package ustc.wth.circlecircle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import entity.ContactInfo;
import service.ContactService;
import utils.CharacterParser;
import utils.ClearEditText;
import utils.PinyinComparator;
import utils.SideBar;
import utils.SideBar.OnTouchingLetterChangedListener;
import adapter.ContactListAdapter;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class FragmentContact extends ListFragment implements OnItemLongClickListener, OnItemClickListener {
	private List<ContactInfo> contact_infos;
	private ContactService contact;
	private ClearEditText mClearEditText;
	private ContactListAdapter adapter;
	private SideBar sideBar;
	private TextView dialog;
	private ListView lv;
	private PopupWindow pw;
	private ImageButton conadd_imgbut;
	/**
	 * ����ƴ��������ListView�����������
	 */
	private CharacterParser characterParser;
	private PinyinComparator pinyinComparator;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_contact, container, false);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initViews();
	}
	
	
	
	/**
	 *listfragment�еļ���¼�����Ҫ��onResume������ӣ�����ԭ��Ҳ��֪����Ϊʲô
	 *��ʵҲ�ã�onResume()��ʱ��᷽�� 
	 */
	 public void onResume()   
	    {  
	        super.onResume();  
	        
	       
	        characterParser = CharacterParser.getInstance();   //�ַ���ƴ����
			pinyinComparator = new PinyinComparator();
			sideBar = (SideBar) getActivity().findViewById(R.id.sidrbar);   //�Զ���Ĳ����
			dialog = (TextView) getActivity().findViewById(R.id.dialog);    //�������������ʾ������
			sideBar.setTextView(dialog);
			conadd_imgbut= (ImageButton) getActivity().findViewById(R.id.conadd_imgbut);
			getListView().setOnItemLongClickListener(this);
			
			//ImageButton��Ӽ����¼�
			MyButtonClickListener onclickListener = new MyButtonClickListener();   
	        conadd_imgbut.setOnClickListener(onclickListener);  
			
			//�������Ӽ����¼�
			sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
				@Override
				public void onTouchingLetterChanged(String s) {
					//����ĸ�״γ��ֵ�λ��
					int position = adapter.getPositionForSection(s.charAt(0));
					if(position != -1){
						lv.setSelection(position);
					}
					
				}
			});
			
	        
			//��ȡĬ��list����ӵ���¼�
			lv = getListView();   
			lv.setOnItemClickListener(this);
//			lv.setOnItemClickListener(new OnItemClickListener() {
//				@Override
//				public void onItemClick(AdapterView<?> parent, View view,
//						int position, long id) {
//					//����Ҫ����adapter.getItem(position)����ȡ��ǰposition����Ӧ�Ķ���
//					//Toast.makeText(getActivity(), ((ContactInfo)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
//					Intent intent=new Intent();
//					intent.setClass(getActivity(), ContactInfoActivity.class);
//					startActivity(intent);	
//				}
//			});
			
			
			contact = new ContactService(this.getActivity());
			contact_infos = contact.getContactInfo();
			Collections.sort(contact_infos, pinyinComparator);     //������Դ����ƴ����������
			adapter=new ContactListAdapter(this.getActivity(), contact_infos);
			setListAdapter(adapter);
			
			mClearEditText=(ClearEditText)getActivity().findViewById(R.id.filter_edit);   //�Զ���ClearEditText
			//Ϊeditatext�����Ӧ�¼�
			mClearEditText.addTextChangedListener(new TextWatcher(){

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
					//���ݸı���������������Դ
					filterData(s.toString());
					
				}
				
			});
	        
	    }  
	 
	 
	 
	 
//	 //����item��ת�鿴ҳ��
//	  class MyOnItemClickListener implements OnItemClickListener   
//	    {  
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// TODO Auto-generated method stub
//				//Toast.makeText(getActivity(), ((ContactInfo)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
//				Intent intent=new Intent();
//				intent.setClass(getActivity(), ContactInfoActivity.class);
//				startActivity(intent);	
//				
//			}  
//	    }  

	  //���������ϵ�˰�ť��ת���½���ϵ��ҳ��
	  class MyButtonClickListener implements OnClickListener   
	    {  
	        public void onClick(View v)   
	        {       	
	        	Intent intent=new Intent();
				intent.setClass(getActivity(), ContactAddActivity.class);
				startActivity(intent);	        	
	        }  
	    }  
	 

	 //��ʼ��ƴ����
	  private void initViews() {
		  
		characterParser = CharacterParser.getInstance();	
		pinyinComparator = new PinyinComparator();

	  }

	
	/**
	 * ����������е�ֵ���������ݲ�����ListView��Ŀǰ���԰���ƴ��������������������Ŀǰ��֧��ȫƴ
	 * DATE��2014-3-8
	 * Author��������
	 */
	private void filterData(String filterStr){
		//�½�һ��SortModel���͵�List
		List<ContactInfo> filterDateList = new ArrayList<ContactInfo>();

		//�ж�EditText���Ƿ�Ϊ��
		if(TextUtils.isEmpty(filterStr))
		{
			filterDateList = contact_infos;
		}
		else
		{
			filterDateList.clear();
			for(ContactInfo contact_info : contact_infos)
			{
				String name = contact_info.getName();
				String phone=contact_info.getPhone();
				if(name!=null)
				{
					//�����ַ���indexof��string�����ִ�string�ڸ������״γ��ֵ�λ�ã���0��ʼ��û�з���-1�������жϺͽ�ȡ�ַ�����

					//if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
				    if(name.indexOf(filterStr.toString()) != -1 ||  characterParser.getSelling(name).startsWith(filterStr.toString()))
				    {
						//filterDateList�е����ݸı�

						filterDateList.add(contact_info);
					}
				}
			}
		}
		
		// ����a-z��������
		//Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
		}
	
	//����Item��ת���鿴ҳ��
	public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
		
		ContactInfo ci = (ContactInfo)adapter.getItem(position);
		Bundle bundle = new Bundle();
		Intent intent=new Intent();
		intent.setClass(getActivity().getApplicationContext(),ContactInfoActivity.class);
		bundle.putSerializable("ContactInfo1", ci);
        intent.putExtras(bundle);
		startActivity(intent);

		 
	 }
	
	
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		LinearLayout pv = (LinearLayout) LayoutInflater.from(
				getActivity()).inflate(R.layout.pop_up_contact, null);

		pw = new PopupWindow(getActivity());
		pw.setContentView(pv);
		Drawable dw = getResources().getDrawable(R.drawable.qzone_bg_copy);
		pw.setBackgroundDrawable(dw);

		TextView conEdit = (TextView) pv.findViewById(R.id.contact_edit);
		TextView conDel = (TextView) pv.findViewById(R.id.contact_delete);
		ImageView ivLine = (ImageView) pv.findViewById(R.id.line);
		
		
		ContactInfo ci = contact_infos.get(arg2);


		// popwindow�ĳ��Ϳ�ģ�����Ҫ���õģ���Ȼ�޷���ʾ��
		pw.setWidth(400);
		pw.setHeight(150);

		pw.setOutsideTouchable(true);
		pw.setFocusable(true);

		View line = arg1;
        pw.showAsDropDown(line, line.getWidth()/2-200, -line.getHeight()-75);
        conEdit.setOnClickListener(new ConEditListener(ci.getId(), ci));
        conDel.setOnClickListener(new ConDelListener(ci.getId(), ci, arg2));
		return false;
	}


	
	class ConEditListener implements OnClickListener{
		
		private int id;
		private ContactInfo ci;
		ConEditListener(int id, ContactInfo ci){
			this.ci = ci;
			this.id = id;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			Bundle bundle = new Bundle();
			Intent intent=new Intent();
		    intent.setClass(getActivity().getApplicationContext(),ContactEditActivity.class);
		    bundle.putSerializable("ContactInfo1", ci);
            intent.putExtras(bundle);
            pw.dismiss();
            startActivity(intent);

		}
		
	}
	
	class ConDelListener implements OnClickListener{
		
		private int id;
		private ContactInfo ci;
		private int position;
		ConDelListener(int id, ContactInfo ci,int position){
			this.ci = ci;
			this.id = id;
			this.position=position;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
           
			pw.dismiss();
            
			delContact(ci.getName());
			
			contact_infos.remove(position);
			
			adapter.notifyDataSetChanged();
			
			getListView().invalidate();
		}
		
	}

	
	//ɾ����ϵ��
	private void delContact(String name) {
		ContentResolver resolver = this.getActivity().getContentResolver();

		Cursor cursor = resolver.query(Data.CONTENT_URI,new String[] { Data.RAW_CONTACT_ID },

		ContactsContract.Contacts.DISPLAY_NAME + "=?",new String[] { name }, null);

		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

		if (cursor.moveToFirst()) {
		do {
		long Id = cursor.getLong(cursor.getColumnIndex(Data.RAW_CONTACT_ID));

		ops.add(ContentProviderOperation.newDelete(
		ContentUris.withAppendedId(RawContacts.CONTENT_URI,Id)).build());
		try {
			resolver.applyBatch(ContactsContract.AUTHORITY, ops);
		} 
		catch (Exception e){}
		} while (cursor.moveToNext());
		cursor.close();
		}
		}
	
	
	
	}

