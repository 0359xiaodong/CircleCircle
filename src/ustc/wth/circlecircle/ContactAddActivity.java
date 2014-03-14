package ustc.wth.circlecircle;

import java.util.ArrayList;










import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ContactAddActivity extends Activity {
	private ImageButton imgbutton;
	private EditText edittext_name,edittext_phone,edittext_address,edittext_email,edittext_im;
	String TAG="abc";
	private TextView textview1,textview2,textview3;
	private TextView textview_phone,textview_email,textview_address,textview_im;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);
        
        //��ȡedittext
        edittext_name=(EditText)findViewById(R.id.edittext_name);
        edittext_phone=(EditText)findViewById(R.id.edittext_phone);
        edittext_email=(EditText)findViewById(R.id.edittext_email);
        edittext_address=(EditText)findViewById(R.id.edittext_address);
        edittext_im=(EditText)findViewById(R.id.edittext_im);
        
        imgbutton=(ImageButton)findViewById(R.id.confinish_imgbut);
        
        //����Զ��� 
        MyButtonClickListener clickListener = new MyButtonClickListener();   
        
        imgbutton.setOnClickListener(clickListener); 
        
 
        


    }
	
	  class MyButtonClickListener implements OnClickListener   
	    {  
	        public void onClick(View v)   
	        {  
	        	try{
	        		ContactAdd();	
	        		Toast.makeText(ContactAddActivity.this,
		    				"��ӳɹ� ", Toast.LENGTH_SHORT)
		    				.show();
	        		ContactAddActivity.this.finish();  //��ӳɹ���رյ�ǰactivity����������һ����  
	        	}
	        	catch (Exception e){
	        		Toast.makeText(ContactAddActivity.this,
		    				"���ʧ�� ", Toast.LENGTH_SHORT)
		    				.show();
	        	}

	        }  
	    }  
	  
	  
	  public void ContactAdd()throws Exception{
		  Uri uri=Uri.parse("content://com.android.contacts/raw_contacts");
		  ContentResolver resolver=this.getContentResolver();
		  ArrayList<ContentProviderOperation> operations=new ArrayList<ContentProviderOperation>();
		  ContentProviderOperation opi=ContentProviderOperation.newInsert(uri).withValue("account_name", null).build();
		  //�������������ӹȸ���˺�,����еĻ�,���԰󶨹ȸ���˺�
		  operations.add(opi);
		  uri=Uri.parse("content://com.android.contacts/data");
		  ContentProviderOperation op2=ContentProviderOperation.newInsert(uri)
		  .withValueBackReference("raw_contact_id", 0)//ʹ�ü�����0��������Ӧ�Ĳ�������,ִ�к󷵻صļ�¼id,��Ϊ���ֵ
		  //ʹ������Ϊ0�Ķ���,ִ�в����,���ص�idֵ��Ϊ���ֵ
		  .withValue("mimetype", "vnd.android.cursor.item/name")
		  .withValue("data2", edittext_name.getText().toString())
		  .build();
		  operations.add(op2);
		   
		  ContentProviderOperation op3=ContentProviderOperation.newInsert(uri)
		  .withValueBackReference("raw_contact_id", 0)//ʹ�ü�����0��������Ӧ�Ĳ�������,ִ�к󷵻صļ�¼id,��Ϊ���ֵ
		  //ʹ������Ϊ0�Ķ���,ִ�в����,���ص�idֵ��Ϊ���ֵ
		  .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
		  .withValue("data1", edittext_phone.getText().toString())
		  .withValue("data2", "2")
		  .build();
		  operations.add(op3);
		  
		  //��������
		  ContentProviderOperation op4=ContentProviderOperation.newInsert(uri)
		  .withValueBackReference("raw_contact_id", 0)//ʹ�ü�����0��������Ӧ�Ĳ�������,ִ�к󷵻صļ�¼id,��Ϊ���ֵ
		  //ʹ������Ϊ0�Ķ���,ִ�в����,���ص�idֵ��Ϊ���ֵ
		  .withValue("mimetype", "vnd.android.cursor.item/email_v2")
		  .withValue("data1", edittext_email.getText().toString())
		  .withValue("data2", "1")
		  .build();
		  operations.add(op4);
		  
		  //����QQ
		  ContentProviderOperation op5=ContentProviderOperation.newInsert(uri)
		  .withValueBackReference("raw_contact_id", 0)//ʹ�ü�����0��������Ӧ�Ĳ�������,ִ�к󷵻صļ�¼id,��Ϊ���ֵ
		  //ʹ������Ϊ0�Ķ���,ִ�в����,���ص�idֵ��Ϊ���ֵ
		  .withValue("mimetype", "vnd.android.cursor.item/im")
		  .withValue("data1", edittext_im.getText().toString())
		  .withValue("data2", "4")
		  .withValue("data5","4")
		  .build();
		  operations.add(op5);

		//���˵�ַ
		  ContentProviderOperation op6=ContentProviderOperation.newInsert(uri)
		  .withValueBackReference("raw_contact_id", 0)//ʹ�ü�����0��������Ӧ�Ĳ�������,ִ�к󷵻صļ�¼id,��Ϊ���ֵ
		  //ʹ������Ϊ0�Ķ���,ִ�в����,���ص�idֵ��Ϊ���ֵ
		  .withValue("mimetype", "vnd.android.cursor.item/postal-address_v2")
		  .withValue("data1", edittext_address.getText().toString())
		  .withValue("data2", "1")
		  .withValue("data4", edittext_address.getText().toString())
		  .build();
		  operations.add(op6);
		  
		  
		  
		  resolver.applyBatch("com.android.contacts", operations);

		  }


	


}
