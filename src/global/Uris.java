package global;


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
}
