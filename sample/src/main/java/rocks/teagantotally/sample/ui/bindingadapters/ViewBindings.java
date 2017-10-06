package rocks.teagantotally.sample.ui.bindingadapters;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Created by tglenn on 10/6/17.
 */

public abstract class ViewBindings {
    @BindingAdapter("backgroundColor")
    public static void setBackgroundColor(View view,
                                          int color) {
        view.setBackgroundColor(color);
    }
}
