package rocks.teagantotally.deepthoughtrecycler.handlers.view;

import android.view.View;

/**
 * Created by tglenn on 3/22/17.
 */

public interface ViewRecycledHandler<ViewModelType> {
    /**
     * Handle when a view is recycled
     *
     * @param view      The view that is recycled
     * @param viewModel The view model bound to the view
     */
    void onViewRecycled(View view,
                        ViewModelType viewModel);
}
