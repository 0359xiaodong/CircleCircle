package ustc.wth.circlecircle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import android.util.Log;
import android.view.Window; 
import entity.ContactInfo;
import service.ContactService;
import utils.CharacterParser;
import utils.ClearEditText;
import utils.PinyinComparator;
import utils.SideBar;
import utils.SideBar.OnTouchingLetterChangedListener;
import adapter.ContactListAdapter;
import adapter.GroupAdapter;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.Groups;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.CommonDataKinds.GroupMembership;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class FragmentContact extends ListFragment implements OnItemLongClickListener, OnItemClickListener {
	private static final int OPTIONS_MENU_ITEM_ADD = Menu.FIRST;
	private static final int OPTIONS_MENU_ITEM_GROUPS = Menu.FIRST + 1;
	private List<ContactInfo> contact_infos;
	private HashMap<String, String> contactgroup;
	private ContactService contact;

	private ClearEditText mClearEditText;
	private ContactListAdapter adapter;
	private GroupAdapter groupAdapter;
	private SideBar sideBar;
	private TextView dialog;
	private ListView lv;
	private PopupWindow pw;
	private ImageButton conadd_imgbut;
	private PopupWindow popupWindow;
	private Context content;
	private ListView lv_group;  
	private String staticgroupid="999";
    private View view;  
    private static FragmentContact instance;
	private TextView tvtitle;  
	/**
	 * ����ƴ��������ListView�����������
	 */
	private CharacterParser characterParser;
	private PinyinComparator pinyinComparator;
	private List<String> groupinfos;
	
	private Activity group_activity;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_contact, container, false);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		contactgroup=new HashMap<String,String>();
		initViews();	
	}
	
	/**
	 *listfragment�еļ���¼�����Ҫ��onResume������ӣ�����ԭ��Ҳ��֪����Ϊʲô
	 *��ʵҲ�ã�onResume()��ʱ��᷽�� 
	 */
	 public void onResume()   
	    {  
	        super.onResume();  
	        
	        tvtitle = (TextView) getActivity().findViewById(R.id.tvtitle); 
	        
	        tvtitle.setOnClickListener(new View.OnClickListener() {  
	        	  
	            @Override  
	            public void onClick(View v) {  
	                showWindow(v);  
	            }  
	        });  
	        
	        characterParser = CharacterParser.getInstance();   //�ַ���ƴ����
			pinyinComparator = new PinyinComparator();
			sideBar = (SideBar) getActivity().findViewById(R.id.sidrbar);   //�Զ���Ĳ����
			dialog = (TextView) getActivity().findViewById(R.id.dialog);    //�������������ʾ������
			sideBar.setTextView(dialog);
			conadd_imgbut= (ImageButton) getActivity().findViewById(R.id.conadd_imgbut);
			getListView().setOnItemLongClickListener(this);
			registerForContextMenu(getListView());
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
			
			lv = getListView();    //��ȡĬ��list����ӵ���¼�
			lv.setOnItemClickListener(this);
			content =this.getActivity();
			group_activity=this.getActivity();
			contact = new ContactService(this.getActivity());
			contact_infos = contact.getContactInfo();              //����contactservice��ʼ������
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
					filterData(s.toString(),staticgroupid);				
				}				
			});     
	    }   

	 //�����Ĳ˵�
	 @Override 
     public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) { 
         // TODO Auto-generated method stub 
         super.onCreateOptionsMenu(menu, inflater); 
         menu.add(0, OPTIONS_MENU_ITEM_ADD, 0, "�����ϵ��");  
	     menu.add(0, OPTIONS_MENU_ITEM_GROUPS , 0, "�������");  
     } 
       
	 public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case OPTIONS_MENU_ITEM_ADD:
//				final Intent intent = new Intent(Intent.ACTION_INSERT,
//						Contacts.CONTENT_URI);
//				startActivity(intent);
				return true;
			case OPTIONS_MENU_ITEM_GROUPS:// ��ת����ʾ���з���ҳ��
				final Intent groupIntent = new Intent(getActivity().getApplicationContext(), GroupInfoActivity.class);
				startActivity(groupIntent);
			default:
				return super.onContextItemSelected(item);
			}
		}

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
	private void filterData(String filterStr,String groupid){
		//�½�һ��SortModel���͵�List
		List<ContactInfo> filterDateList = new ArrayList<ContactInfo>();

		//�ж�EditText���Ƿ�Ϊ��
		if(groupid.equals("999"))
		{		
			if(TextUtils.isEmpty(filterStr))
			{
				filterDateList.clear();
				filterDateList = contact_infos;
			}
			else
			{
				filterDateList.clear();
				for(ContactInfo contact_info : contact_infos)
				{
					String name = contact_info.getName();
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
		}
		else
		{
			filterDateList.clear();
			for(ContactInfo contact_info : contact_infos)
			{
				String name = contact_info.getName();
				String phone=contact_info.getPhone();
				String groupid_info=contact_info.getGroupid();
				if(name!=null&&groupid_info.equals(groupid))
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
	
	//������listview����Ϣ
	private void filtergroupData(String groupid)
	{
		List<ContactInfo> filtergroupList = new ArrayList<ContactInfo>();
		if(groupid.equals("999"))
		{
			filtergroupList.clear();
			filtergroupList = contact_infos;
			Log.d("gorupitems",Integer.toString(filtergroupList.size()));
		}
		else
		{
			filtergroupList.clear();
			for(ContactInfo contact_info : contact_infos)
			{
				String name = contact_info.getName();
				String phone=contact_info.getPhone();
				String group_id=contact_info.getGroupid();
				if(group_id.equals(groupid))
				{
					filtergroupList.add(contact_info);
				}
			}
		}	
		adapter.updateListView(filtergroupList);
		Log.d("adapter", Integer.toString(adapter.getCount()));
		
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
			delContact(getActivity(),ci.getName());
			contact_infos.remove(position);
			adapter.notifyDataSetChanged();
			getListView().invalidate();
		}	
	}
	
	
	private void showWindow(View parent) {  
		  
        if (popupWindow == null) {  
            LayoutInflater layoutInflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
            WindowManager mWm = (WindowManager)this.getActivity().getSystemService(Context.WINDOW_SERVICE);   
            view = layoutInflater.inflate(R.layout.group_list, null);  
  
            String[] RAW_PROJECTION = new String[] { ContactsContract.Groups._ID, ContactsContract.Groups.TITLE, };  
            String RAW_CONTACTS_WHERE = ContactsContract.Groups.DELETED + " = ? ";  
            Cursor groupcursor = this.getActivity().getContentResolver().query(ContactsContract.Groups.CONTENT_URI, RAW_PROJECTION,  
                    RAW_CONTACTS_WHERE, new String[] { "" + 0 }, null);  
            
            groupinfos = new ArrayList<String>();
            
            if (groupcursor != null) {
    			while (groupcursor.moveToNext()) {
    					//���ù�ϣmap��������ϵ�˷��鼰��Ӧ�����
    					String title = groupcursor.getString(groupcursor.getColumnIndex("title"));
    					String id=groupcursor.getString(groupcursor.getColumnIndex("_id"));
    					groupinfos.add(title);
    					contactgroup.put(title,id);
    				}
    			}
    			groupcursor.close();
    			groupinfos.add("δ����");
    			contactgroup.put("δ����","999");
            lv_group = (ListView) view.findViewById(R.id.lvGroup);  
            groupAdapter = new GroupAdapter(this.getActivity(), groupinfos);  
            lv_group.setAdapter(groupAdapter);  
                
            // ����һ��PopuWidow����  
            popupWindow = new PopupWindow(view, 350, 550);  
        }  
         
        popupWindow.setFocusable(true);  // ʹ��ۼ�
        popupWindow.setOutsideTouchable(true);  // ����������������ʧ  
  
        // �����Ϊ�˵��������Back��Ҳ��ʹ����ʧ�����Ҳ�����Ӱ����ı���  
        popupWindow.setBackgroundDrawable(new BitmapDrawable());  
        WindowManager windowManager = (WindowManager) this.getActivity().getSystemService(Context.WINDOW_SERVICE);  
        
        // ��ʾ��λ��Ϊ:��Ļ�Ŀ�ȵ�һ��-PopupWindow�ĸ߶ȵ�һ��  
        //        int xPos = windowManager.getDefaultDisplay().getWidth() / 2  
       
        //                - popupWindow.getWidth() / 2;
        int xPos =0;
        Log.i("coder", "xPos:" + xPos);  
        popupWindow.showAsDropDown(parent, xPos, 0);  
        lv_group.setOnItemClickListener(new OnItemClickListener() {  
            @Override  
            
            
            public void onItemClick(AdapterView<?> parent, View view,  
                    int position, long id) {  
            	
            	//��ȡ��ǰItem�д洢��ֵ������hashmap��ȡ��ǰ��Ӧvalue
            	String ss=(String)groupAdapter.getItem(position);
            	String ssid=contactgroup.get(ss);
            	tvtitle.setText(ss);
            	//����ssid=999��ΪĬ��δ����
            	if(ssid.equals("999")){               
            		staticgroupid=ssid;
            		filtergroupData("999");
            	}
            	else
            	{
            		staticgroupid=ssid;
    				filtergroupData(ssid);
            	}
				
            	if (popupWindow != null) {  
                    popupWindow.dismiss();  
                }  
            }  
        });  
    }  
	
	public static FragmentContact getInstance() {
        // TODO Auto-generated method stub
        return instance;
	}
	
	private void delContact(Context context, String name) {

		Cursor cursor = getActivity().getContentResolver().query(Data.CONTENT_URI,new String[] { Data.RAW_CONTACT_ID },

		ContactsContract.Contacts.DISPLAY_NAME + "=?",new String[] { name }, null);

		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		ArrayList<ContentProviderOperation> ops1 = new ArrayList<ContentProviderOperation>();
		if (cursor.moveToFirst()) {
		do {
		long Id = cursor.getLong(cursor.getColumnIndex(Data.RAW_CONTACT_ID));
		ops.add(ContentProviderOperation.newDelete(
		ContentUris.withAppendedId(RawContacts.CONTENT_URI,Id)).build());
		ops1.add(ContentProviderOperation.newDelete(
				ContentUris.withAppendedId(Data.CONTENT_URI,Id)).build());
		try {
		getActivity().getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
		getActivity().getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops1);
		} 
		catch (Exception e){}
		} while (cursor.moveToNext());
		
		cursor.close();

		
		}
		}
	//CR.delete(ContactsContract.RawContacts.CONTENT_URI,ContactsContract.RawContacts_id + "=" + delRawId);
	
	
	ArrayList<ContentProviderOperation> ops =
	          new ArrayList<ContentProviderOperation>();
	
	}

