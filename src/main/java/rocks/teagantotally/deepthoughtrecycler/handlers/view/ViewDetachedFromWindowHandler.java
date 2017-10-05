package rocks.teagantotally.deepthoughtrecycler.handlers.view;

import android.view.View;

/**
 * Created by tglenn on 10/2/17.
 */

public interface ViewDetachedFromWindowHandler<ViewModelType> {
    /**
     * Handler for when a view is detached from the window
     *
     * @param view      The view that was detached
     * @param viewModel The view model bound to the view
     */
    void onViewDetachedFromWindow(View view,
                                  ViewModelType viewModel);
}
