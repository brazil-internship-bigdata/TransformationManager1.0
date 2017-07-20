package tools;

import tools.exceptions.CancelledCommandException;

public interface MySupplier<T> {

	T get() throws CancelledCommandException;

}
