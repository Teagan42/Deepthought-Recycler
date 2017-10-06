package rocks.teagantotally.deepthoughtrecycler.binders;

/**
 * Created by tglenn on 3/21/17.
 */

public class ItemBinder<ItemType>
          implements AbstractItemBinder<ItemType> {
    private final int bindingVariableId;
    private final int layoutId;

    /**
     * Create a layout to binding variable map
     *
     * @param bindingVariableId The variable identifier to bind to
     * @param layoutId          The layout identifier to inflate
     */
    public ItemBinder(int bindingVariableId,
                      int layoutId) {
        this.bindingVariableId = bindingVariableId;
        this.layoutId = layoutId;
    }

    /**
     * Returns the layout resource id for {@param model}
     *
     * @param model Model to retrieve layout resource id for
     * @return The id of the layout resource
     */
    @Override
    public int getLayoutResourceId(ItemType model) {
        return layoutId;
    }

    /**
     * Returns the binding variable id for {@param model}
     *
     * @param model Model to retrieve the binding variable id for
     * @return The id of the binding variable
     */
    @Override
    public int getBindingVariableId(ItemType model) {
        return bindingVariableId;
    }
}
