package tools;


public interface MySupplier<T> {

	T get() throws CancelledCommandException;

}
