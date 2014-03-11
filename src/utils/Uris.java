package utils;


/**
 * class name��Uri
 * class description�����幫�õ�Uri��Դ
 * PS�� 
 * Date:2014-2-21
 * 
 * @version 1.00
 * @author YF
 */
public class Uris {
	/**
	 * ���ŻỰ
	 */
	public static final String CONVERSATION_URI = "content://mms-sms/conversations/";
	/**
	 * �򵥶��ŻỰ
	 */
	public static final String CONVERSATION_URI_ALL = "content://mms-sms/conversations?simple=true";
	/**
	 * ������лỰ��ϵ��
	 */
	public static final String CANONICAL_URI_ADDRESSES = "content://mms-sms/canonical-addresses";
	/**
	 * ����id��ȡ��ϵ��
	 */
	public static final String CANONICAL_URI_ADDRESS = "content://mms-sms/canonical-address/";
	/**
	 * ���еĶ���
	 */
	public static final String SMS_URI_ALL = "content://sms/";
	/**
	 * �ռ������
	 */
	public static final String SMS_URI_INBOX = "content://sms/inbox";
	/**
	 * ���������
	 */
	public static final String SMS_URI_SEND = "content://sms/sent";
	/**
	 * �ݸ������
	 */
	public static final String SMS_URI_DRAFT = "content://sms/draft";
	/**
	 * ������ϵ��
	 */
	public static final String Contacts_URI_ALL = "content://com.android.contacts/contacts"; 
	/**
	 * ������ϵ��
	 * #������ϵ��id
	 */
	public static final String Phone_CONTENT_URI = "content:// com.android.contacts/data/phones"; 
	/**
	 * �xȡ�M����Ϣ��̖�a
	 */
	public static final String Contacts_URI_SINGLE = "content://com.android.contacts/contacts/#/data"; 

	
	//��raw_contacts����ӡ�ɾ�������²�����
	public static final String Contacts_URI_RAW= "content://com.android.contacts/raw_contacts";
	
	//��data����ӡ�ɾ�������²�����
	public static final String Contacts_URI_DATA = "content://com.android.contacts/data";
	
	//����email��data���ѯ
	public static final String Contacts_URI_EMAIL = "content://com.android.contacts/data/emails/filter/";

	//���ݵ绰�����data���ѯ
	public static final String Contacts_URI_PHONE = "content://com.android.contacts/data/phones/filter/";


}
