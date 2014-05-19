package entity;

import java.io.Serializable;

import android.graphics.Bitmap;


public class ContactInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ��ϵ�˵�����
	 */
	private String name;
	/**
	 * �绰
	 */
	private String phone;
	/**
	 * id
	 */
	private int id;
	
	/**
	 * ����id
	 */
	private String groupid;
	
	
	private ContactInfo cti;
	
	private String sortLetters;  //��ʾ����ƴ��������ĸ
	
	private String sortKey;
	/**
	 * ͷ��
	 */
	private Bitmap photo;
	
	
	public ContactInfo getCti() {
		return cti;
	}

	public void setCti(ContactInfo cti) {
		this.cti = cti;
	}
	
	
	public String getSortKey() {
		return sortKey;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}
	
	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Bitmap getPhoto() {
		return photo;
	}

	public void setPhoto(Bitmap photo) {
		this.photo = photo;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public void setId(int id){
		this.id = id;	
	}
	
	public int getId(){
		return id;
	}
	
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

}
