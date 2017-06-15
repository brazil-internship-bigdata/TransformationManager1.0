package dataManaging;

import tools.CancelledCommandException;

public interface Factory<T extends Item> {
	public T create(MyListView<T> listView);

	public T create();

	public T createWithGUI() throws CancelledCommandException;
}
