package entity;

import android.graphics.Bitmap;

public class ContactInfo {
	/**
	 * ��ϵ�˵�����
	 */
	private String name;
	/**
	 * �绰
	 */
	private String phone;
	/**
	 * ͷ��
	 */
	private Bitmap photo;
	
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

}
