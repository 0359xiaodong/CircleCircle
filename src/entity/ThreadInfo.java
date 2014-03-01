package entity;

import android.graphics.Bitmap;

public class ThreadInfo {
	/**
	 * �Ựid
	 */
	private int id;
	/**
	 * ���һ����������
	 */
	private long date;
	/**
	 * �Ự������
	 */
	private int messageCount;
	/**
	 * �Ự��id��ָ��canonical��
	 */
	private String recipientId;
	/**
	 * ���һ������
	 */
	private String snippet;
	/**
	 * �����ַ���
	 */
	private int snippetCs;
	/**
	 * �Ƿ��Ѷ����Ѷ�Ϊ1��δ��Ϊ0
	 */
	private int read;
	/**
	 * �Ự����,Type of the thread, either Threads.COMMON_THREAD or Threads.BROADCAST_THREAD
	 */
	private int type;
	/**
	 * �Ƿ�����д���Ϊ1��û��Ϊ0
	 */
	private int error;
	/**
	 * �Ƿ��и�����û��Ϊ1����Ϊ0
	 */
	private int hasAttachment;
	/**
	 * �Ƿ�ΪȺ������Ϊ1����Ϊ0
	 */
	private int isMass;
	/**
	 * ������ϵ�˶���
	 */
	private ContactInfo cti;
	/**
	 * ����Ⱥ����ϵ�˶���
	 */
	private ContactInfo[] ctis;
	
	public ThreadInfo(){
		isMass = 0;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public int getMessageCount() {
		return messageCount;
	}
	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}
	public String getRecipientId() {
		return recipientId;
	}
	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}
	public String getSnippet() {
		return snippet;
	}
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	public int getSnippetCs() {
		return snippetCs;
	}
	public void setSnippetCs(int snippetCs) {
		this.snippetCs = snippetCs;
	}
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public int getHasAttachment() {
		return hasAttachment;
	}
	public void setHasAttachment(int hasAttachment) {
		this.hasAttachment = hasAttachment;
	}
	public ContactInfo getCti() {
		return cti;
	}

	public void setCti(ContactInfo cti) {
		this.cti = cti;
	}

	public ContactInfo[] getCtis() {
		return ctis;
	}

	public void setCtis(ContactInfo[] ctis) {
		this.ctis = ctis;
	}
	public int getIsMass() {
		return isMass;
	}

	public void setIsMass(int isMass) {
		this.isMass = isMass;
	}
	

}
