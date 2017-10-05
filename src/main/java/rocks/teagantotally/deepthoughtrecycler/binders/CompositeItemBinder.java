package rocks.teagantotally.deepthoughtrecycler.binders;

import android.support.annotation.NonNull;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by tglenn on 3/21/17.
 */

public class CompositeItemBinder<ItemType>
          implements AbstractItemBinder<ItemType> {
    private final List<ConditionalItemBinder<ItemType>> itemBinders = new ArrayList<>();

    /**
     * Create a composite of item binders used for populating a recycler view
     *
     * @param itemBinders The item bindings that can be used to populate a recycler view
     */
    public CompositeItemBinder(@NonNull ConditionalItemBinder<ItemType>... itemBinders) {
        Objects.requireNonNull(itemBinders,
                               "Item binders cannot ben ull");
        if (itemBinders.length == 0) {
            return;
        }

        this.itemBinders.addAll(Lists.newArrayList(itemBinders));
    }

    /**
     * Add a conditional item binder to the composite
     *
     * @param itemBinder The item binding to be added
     */
    public void addItemBinder(@NonNull ConditionalItemBinder<ItemType> itemBinder) {
        Objects.requireNonNull(itemBinder,
                               "Item binder cannot be null");

        itemBinders.add(itemBinder);
    }

    /**
     * Returns the layout resource id for {@param model}
     *
     * @param model Model to retrieve layout resource id for
     * @return The id of the layout resource
     */
    @Override
    public int getLayoutResourceId(ItemType model) {
        for (ConditionalItemBinder<ItemType> dataBinder : itemBinders) {
            if (dataBinder.canBind(model)) {
                return dataBinder.getLayoutResourceId(model);
            }
        }

        throw new IllegalStateException("No mapping found for " + model.toString());
    }

    /**
     * Returns the binding variable id for {@param model}
     *
     * @param model Model to retrieve the binding variable id for
     * @return The id of the binding variable
     */
    @Override
    public int getBindingVariableId(ItemType model) {
        for (ConditionalItemBinder<ItemType> dataBinder : itemBinders) {
            if (dataBinder.canBind(model)) {
                return dataBinder.getBindingVariableId(model);
            }
        }

        throw new IllegalStateException("No mapping found for item " + model.toString());
    }
}
