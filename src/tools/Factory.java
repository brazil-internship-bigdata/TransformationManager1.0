package tools;

import fileManaging.DBconnection;
import fileManaging.Item;
import fileManaging.MyListView;

public interface Factory<T extends Item> {
	public T create(MyListView<T> listView);

	public T create();

	T createWithGUI();
}
