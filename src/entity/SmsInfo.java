package entity;

import android.graphics.Bitmap;

/**
 * class name��SmsInfo
 * class description��������Ϣʵ����
 * PS�� 
 * Date:2014-2-21
 * 
 * @version 1.00
 * @author YF
 */

/*	 _id��������ţ���100 ����
* ����thread_id���Ի�����ţ���100����ͬһ���ֻ��Ż����Ķ��ţ����������ͬ�� ����
* ����address�������˵�ַ�����ֻ��ţ���+8613811810000 ����
* ����person�������ˣ������������ͨѶ¼����Ϊ����������İ����Ϊnull ����
* ����date�����ڣ�long�ͣ���1256539465022�����Զ�������ʾ��ʽ�������� ����
* ����protocol��Э��0SMS_RPOTO���ţ�1MMS_PROTO���� ���� ����
* 	 read���Ƿ��Ķ�0δ����1�Ѷ� ����
* ����status������״̬-1���գ�0complete,64pending,128failed ����
* ����type����������1�ǽ��յ��ģ�2���ѷ��� ���� ����body�����ž������� ����
* ����service_center�����ŷ������ĺ����ţ���+8613800755500
*/
public class SmsInfo {
	/**
	 * ��������
	 */
	private String smsbody;
	/**
	 * ���Ͷ��ŵĵ绰����
	 */
	private String phoneNumber;
	/**
	 * ���Ͷ��ŵ����ں�ʱ��
	 */
	private String date;
	/**
	 * ���Ͷ����˵�id
	 */
	private String name;
	/**
	 * ��������1�ǽ��յ��ģ�2���ѷ���
	 */
	private String type;
	/**
	 * �Ƿ��Ķ�
	 */
	private String read;
	/**
	 * �Ự�еĶ�����
	 */
	private String dialogCount;
	/**
	 * �Ự��ͷ��
	 */
	private Bitmap photo;

	public String getSmsbody() {
		return smsbody;
	}

	public void setSmsbody(String smsbody) {
		this.smsbody = smsbody;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getRead() {
		return read;
	}

	public void setRead(String read) {
		this.read = read;
	}
	
	public String getDialogCount() {
		return dialogCount;
	}

	public void setDialogCount(String dialogCount) {
		this.dialogCount = dialogCount;
	}
	
	public Bitmap getPhoto() {
		return photo;
	}

	public void setPhoto(Bitmap photo) {
		this.photo = photo;
	}
}