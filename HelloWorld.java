package my.database.imp;//�����Ķ�����Ҫ������Ŀ������һ������Ŀ��+��ϵͳ��+����ģ����

/*
 * ��Ľ���˵��  			 eg.��ʾ����
 * ������   				 eg.�𿡷�
 * ����ʱ��				 eg.2013.11.06
 * */
public class HelloWorld {//��������ĸ��д

	/*
	 * @param hello   hello��Ա�����Ľ���
	 * 
	 * @param world   world��Ա�����Ľ���
	 */

	private String hello;//������Сд
	private int world;

	
	/*
	 * �����Ľ��ͣ�����     (һЩ�����Եķ����ر���Ҫ����)
	 * @param id     id�Ľ���
	 * @return       ����ֵ�Ľ��ͣ�����У�
	 * */
	public int getInfor(int id) {//����������ĸСд
		
		if (id == 0) {//����ʲô����£���Դ����ű������
			this.world = this.world + 2;//�������ر��ǵȺ��������ʹ�ÿո�
		} else {
			this.world--;
		}
		return this.world;
	}
	


}
