package fileManaging;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;


public class MyListView<E extends Item> extends JPanel{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6278407390981154945L;
	
	private ArrayList<E> listData; //TODO delete this comment : This map joins the list Items inside a given List and the elements of the listView
//	private ArrayList<MyItemView> listView; 
	
	
	//TODO modify this
	public MyListView(List<E> list) {
		super();
		init(list);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	private void init(List<E> list) {
		listData = (ArrayList<E>) list;
		for(E e : list) {
			MyItemView itemView = new MyItemView(this, e);
			//listView.add(itemView);
			this.add(itemView);
		}
	}
	
	public void add (E e) {
		MyItemView itemView = new MyItemView(this, e);
		listData.add(e);
//		listView.add(itemView);
		this.add(itemView);
		this.revalidate();
		this.repaint();
	}

	
	/**
	 * removes the FileSelectedPane from the scrollBar and removes the element from the list.
	 * @param child the pane that was deleted
	 */
	public void remove(MyItemView child) {
		super.remove(child);
		listData.remove(child.getItem());
		this.repaint();
	}
	

	/*
	  
	 

	
	public MyListView(List<E> list) {
		this.list = list;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		init();
	}
	
	
	public void add(File f) {
		super.add(new FileSelectedPane(this, f));
		fileList.add(f);
		this.revalidate();
		this.repaint();
	}
*/

}
