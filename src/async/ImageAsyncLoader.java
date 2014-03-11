package async;

import java.util.HashMap;

import buffer.ContactBuffer;
import holder.ConversationHolder;
import service.ContactService;
import ustc.wth.circlecircle.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import entity.ContactInfo;


/**
 * �첽������ϵ�˵�ͷ��ͺ���
 * Date��2014-3-5
 * Author��������
 */
public class ImageAsyncLoader extends AsyncTask<Integer, Void, ContactInfo[]>{
		private ContactService cts;
		private ConversationHolder holder;
		private int position;
		private ContactInfo contact_info;
		private HashMap<String, ContactInfo> cm;
		Context ctx;
		private String phone_number;

		public ImageAsyncLoader(Context context, ConversationHolder holder, int position, ContactInfo contactinfo, ContactBuffer cb) {
			this.ctx=context;
			this.holder = holder;
			this.position = position;
			this.ctx = context;
			this.contact_info = contactinfo;
			this.cm = cb.getBuffer();
			cts = new ContactService((Activity) ctx);
		}

		@Override
		protected ContactInfo[] doInBackground(Integer... params) {
			ContactInfo[] ctis;
			String contactid;
			String al;
			String number;
			int id1;
				ctis = new ContactInfo[1];	//������СΪ1��ContactInfo[]
				//name = contact_info.getName();
				//��ȡid
				id1=contact_info.getId();
				al=contact_info.getName();
				contactid=Integer.toString(contact_info.getId());
				if(cm.get(contactid) == null){
					ctis[0] = cts.getContactById(Integer.parseInt(contactid),al);   //ͨ��ContactService����ȡContacInfo����
					al=ctis[0].getName();
					number=ctis[0].getPhone();
					cm.put(contactid, ctis[0]);        
					
				}else{
					ctis[0] = cm.get(contactid);
				}
				
				
			return ctis;
		}

		
		
		//�����������ʹ����doInBackground �õ��Ľ���������UI
		@Override
		protected void onPostExecute(ContactInfo[] result) {

			super.onPostExecute(result);
			if (position == holder.getPosition()) {
	
					if (result[0].getPhone() != null) {
						phone_number=result[0].getPhone().trim();
						//ΪImg��Ӽ����¼������ͷ����Խ���
						holder.getImg().setOnClickListener(new OnClickListener() {
				            @Override
				            public void onClick(View v) {
				            	
				            	if(phone_number != null && !phone_number.equals(""))
				            	{
				                    //����ϵͳ�Ĳ��ŷ���ʵ�ֵ绰������
				                    //��װһ������绰��intent�����ҽ��绰�����װ��һ��Uri������
				                    Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phone_number));
				                    ctx.startActivity(intent);//�ڲ���
				                }
				            	Toast.makeText(ctx, "���ڲ���", Toast.LENGTH_SHORT).show();
				            }
				        });
						
						
						holder.getNum().setText(result[0].getPhone());
						String sb ="";
						//holder.getNum().setText("123");
						if(result[0].getName()==null)
							{
								sb="";		
								holder.getName().setText("�ҵ���Ƭ");
							}
						else
						{
						sb = result[0].getName().substring(0, 1);//��ȡ���ֵĵ�һ���ַ�
						
						holder.getImg().setText(sb);
						
						holder.getImg().setBackgroundColor(ctx.getResources()
								.getColor(R.color.lightgray));
						}
					}
					if(result[0].getPhoto() != null){
						Drawable bd = new BitmapDrawable(ctx.getResources(),
								result[0].getPhoto());
						holder.getImg().setText(null);
						holder.getImg().setBackground(bd);
					}
					
			}
		}



}
