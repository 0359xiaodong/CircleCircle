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
	 * id
	 */
	private long id;
	/**
	 * �Ựid
	 */
	private long thread_id;
	/**
	 * �绰
	 */
	private String address;
	/**
	 * ����
	 */
	private String person;
	/**
	 * ��������
	 */
	private String protocol;
	/**
	 * ��������
	 */
	private String service_center;
	/**
	 * ���Ͷ��ŵ����ں�ʱ��
	 */
	private long date;
	/**
	 * ��������1�ǽ��յ��ģ�2���ѷ���
	 */
	private int type;
	/**
	 * �Ƿ��Ķ�
	 */
	private int read;
	/**
	 * ״̬
	 */
	private int status;
	/**
	 * ���ŷ�������
	 */
	private String body;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getThread_id() {
		return thread_id;
	}
	public void setThread_id(long thread_id) {
		this.thread_id = thread_id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getService_center() {
		return service_center;
	}
	public void setService_center(String service_center) {
		this.service_center = service_center;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

	
}