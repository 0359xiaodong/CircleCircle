package entity;

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
public class Contactinfo {
	/**
	 * �绰����
	 */
	private String phoneNumber;

	/**
	 * ��ϵ������
	 */
	private String name;
	
	/*
	 * ����ؼ���
	 */
	private String sortKey; 




	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSortKey() {  
        return sortKey;  
    }  
  
    public void setSortKey(String sortKey) {  
        this.sortKey = sortKey;  
    } 


}