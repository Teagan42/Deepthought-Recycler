package rocks.teagantotally.sample.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by tglenn on 10/6/17.
 */

public class AnimatedLinearLayoutManager
          extends LinearLayoutManager {
    /**
     * Creates a vertical LinearLayoutManager
     *
     * @param context Current context, will be used to access resources.
     */
    public AnimatedLinearLayoutManager(Context context) {
        super(context);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return true;
    }
}
