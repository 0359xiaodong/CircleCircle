package ustc.wth.circlecircle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;





import entity.ContactInfo;
import service.ContactService;
import ustc.wth.circlecircle.SideBar.OnTouchingLetterChangedListener;
import adapter.ContactListAdapter;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class FragmentContact extends ListFragment {
	private List<ContactInfo> contact_infos;
	private ContactService contact;
	private ClearEditText mClearEditText;
	private EditText editatext;
	private ContactListAdapter adapter;
	private TextView textview;
	private SideBar sideBar;
	private TextView dialog;
	private ListView lv;
	
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
	        
			lv = getListView();   //��ȡĬ��list����ӵ���¼�
			MyOnItemClickListener clickListener = new MyOnItemClickListener();   
			
			//lv.setOnItemLongClickListener(listener);
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					//����Ҫ����adapter.getItem(position)����ȡ��ǰposition����Ӧ�Ķ���
					Toast.makeText(getActivity(), ((ContactInfo)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
				}
			});
				
	        mClearEditText=(ClearEditText)getActivity().findViewById(R.id.filter_edit);   //�Զ���ClearEditText

			contact = new ContactService(this.getActivity());
			contact_infos = contact.getContactInfo();
			
			Collections.sort(contact_infos, pinyinComparator);     //������Դ����ƴ����������
			adapter=new ContactListAdapter(this.getActivity(), contact_infos);
			setListAdapter(adapter);
			
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
	        
//	        contact = new ContactService(this.getActivity());
//	        
//			contact_infos = contact.getContactInfo();
//			
//			setListAdapter(new ContactListAdapter(this.getActivity(), contact_infos));
//	     
//	        button = (Button) getActivity().findViewById(R.id.button32);  
//	        
//	        button.setText("����");
//
//	        MyButtonClickListener clickListener = new MyButtonClickListener();   
//	        
//	        button.setOnClickListener(clickListener);  

	    }  
	 
	  class MyOnItemClickListener implements OnItemClickListener   
	    {  
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), ((ContactInfo)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
			}  
	    }  

	 
	 
	 
	
	 //��ʼ��ƴ����
	private void initViews() {
		characterParser = CharacterParser.getInstance();
		
		pinyinComparator = new PinyinComparator();

	}
	
	
	/**
	 * ΪListView�������
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String [] date){
		List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<date.length; i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			//����ת����ƴ��
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			
			mSortList.add(sortModel);
		}
		return mSortList;
		
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

	
	
	
	}

