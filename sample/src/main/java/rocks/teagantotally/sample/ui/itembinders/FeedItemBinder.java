package rocks.teagantotally.sample.ui.itembinders;

import rocks.teagantotally.deepthoughtrecycler.binders.CompositeItemBinder;

/**
 * Created by tglenn on 10/6/17.
 */

public class FeedItemBinder
          extends CompositeItemBinder<Object> {
    public FeedItemBinder() {
        super(new ColorItemBinder(),
              new NameItemBinder());
    }
}
