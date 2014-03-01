package ustc.wth.circlecircle;

import java.util.List;

import entity.ContactInfo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

/**
 * 联系人列表�?配器�?
 * 
 * @author guolin
 */
public class ContactAdapter extends ArrayAdapter<ContactInfo> {

	/**
	 * ��Ҫ��Ⱦ��item�����ļ�
	 */
	private int resource;

	/**
	 *  ��ĸ����鹤�� 
	 */
	private SectionIndexer mIndexer;

	public ContactAdapter(Context context, int textViewResourceId, List<ContactInfo> objects) {
		super(context, textViewResourceId, objects);
		resource = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ContactInfo contact = getItem(position);
		LinearLayout layout = null;
		if (convertView == null) {
			layout = (LinearLayout) LayoutInflater.from(getContext()).inflate(resource, null);
		} else {
			layout = (LinearLayout) convertView;
		}
		TextView name = (TextView) layout.findViewById(R.id.contact_name);
		TextView num = (TextView) layout.findViewById(R.id.contact_num);
		//LinearLayout sortKeyLayout = (LinearLayout) layout.findViewById(R.id.sort_key_layout);
		//TextView sortKey = (TextView) layout.findViewById(R.id.sort_key);
		name.setText(contact.getName());
		num.setText(contact.getPhone());;
		int section = mIndexer.getSectionForPosition(position);
		
//		if (position == mIndexer.getPositionForSection(section)) {
//			sortKey.setText(contact.getSortKey());
//			sortKeyLayout.setVisibility(View.VISIBLE);
//		} else {
//			sortKeyLayout.setVisibility(View.GONE);
//		}
		return layout;
	}

	/**
	 * 给当前�?配器传入�?��分组工具�?
	 * 
	 * @param indexer
	 */
	public void setIndexer(SectionIndexer indexer) {
		mIndexer = indexer;
	}
	

	

}
