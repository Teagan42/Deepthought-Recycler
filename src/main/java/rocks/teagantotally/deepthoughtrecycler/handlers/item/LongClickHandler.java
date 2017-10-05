package rocks.teagantotally.deepthoughtrecycler.handlers.item;

/**
 * Created by tglenn on 3/22/17.
 */

public interface LongClickHandler<T> {
    /**
     * Handle on long click event for {@param item}
     *
     * @param item
     */
    void onLongClick(T item);
}
