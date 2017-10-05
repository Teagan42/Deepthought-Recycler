package rocks.teagantotally.deepthoughtrecycler.handlers.item;

/**
 * Created by tglenn on 3/22/17.
 */

public interface ClickHandler<ItemType> {
    /**
     * Handle on click event for {@param item}
     *
     * @param item
     */
    void onClick(ItemType item);
}
