package rocks.teagantotally.deepthoughtrecycler.binders;

/**
 * Created by tglenn on 3/21/17.
 */

public abstract class ConditionalItemBinder<ItemType>
          extends ItemBinder<ItemType> {
    /**
     * Create a conditional layout to binding variable mapping
     *
     * @param bindingVariableId The variable identifier to bind to
     * @param layoutId          The layout identifier to inflate
     */
    public ConditionalItemBinder(int bindingVariableId,
                                 int layoutId) {
        super(bindingVariableId,
              layoutId);
    }

    /**
     * Whether {@param item} can be bound using this item binder
     *
     * @param item The item to check for binding
     * @return Whether this binder can handle this item
     */
    public abstract boolean canBind(ItemType item);
}
