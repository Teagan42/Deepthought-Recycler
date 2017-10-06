package rocks.teagantotally.sample.ui.itembinders;

import rocks.teagantotally.deepthoughtrecycler.binders.ConditionalItemBinder;
import rocks.teagantotally.sample.BR;
import rocks.teagantotally.sample.R;

/**
 * Created by tglenn on 10/6/17.
 */

public class NameItemBinder
          extends ConditionalItemBinder<Object> {

    public NameItemBinder() {
        super(BR.name,
              R.layout.item_name);
    }

    /**
     * Whether {@param item} can be bound using this item binder
     *
     * @param item The item to check for binding
     * @return Whether this binder can handle this item
     */
    @Override
    public boolean canBind(Object item) {
        return item instanceof String;
    }
}
