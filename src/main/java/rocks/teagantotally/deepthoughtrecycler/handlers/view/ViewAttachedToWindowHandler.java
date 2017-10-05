package rocks.teagantotally.deepthoughtrecycler.handlers.view;

import android.view.View;

/**
 * Created by tglenn on 10/2/17.
 */

public interface ViewAttachedToWindowHandler<ViewModelType> {
    /**
     * Handler for when a view is attached to the window
     *
     * @param view      The view that was attached to the window
     * @param viewModel The view model bound to the view
     */
    void onViewAttachedToWindow(View view,
                                ViewModelType viewModel);
}
