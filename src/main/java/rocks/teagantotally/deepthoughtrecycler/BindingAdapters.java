package rocks.teagantotally.deepthoughtrecycler;

import android.databinding.BindingAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

import java.util.Collection;

import rocks.teagantotally.deepthoughtrecycler.binders.AbstractItemBinder;
import rocks.teagantotally.deepthoughtrecycler.handlers.item.ClickHandler;
import rocks.teagantotally.deepthoughtrecycler.handlers.item.DoubleTapHandler;
import rocks.teagantotally.deepthoughtrecycler.handlers.item.LongClickHandler;
import rocks.teagantotally.deepthoughtrecycler.handlers.view.ViewAttachedToWindowHandler;
import rocks.teagantotally.deepthoughtrecycler.handlers.view.ViewDetachedFromWindowHandler;
import rocks.teagantotally.deepthoughtrecycler.handlers.view.ViewRecycledHandler;


/**
 * Created by tglenn on 3/22/17.
 */

public abstract class BindingAdapters {
    private static final int KEY_ITEM_BINDER = -123;
    private static final int KEY_CLICK_HANDLER = -124;
    private static final int KEY_LONG_CLICK_HANDLER = -125;
    private static final int KEY_DOUBLE_TAP_HANDLER = -126;
    private static final int KEY_VIEW_ATTACHED_HANDLER = -127;
    private static final int KEY_VIEW_DETACHED_HANDLER = -128;
    private static final int KEY_VIEW_RECYCLED_HANDLER = -129;
    private static final int KEY_ITEMS = -130;

    /**
     * Bind a collection of {@param items} to a {@param recyclerView} view
     *
     * @param recyclerView Recycler view to bind items to
     * @param items        The items to bind
     * @param <ItemType>   The type of item being bound
     */
    @BindingAdapter("items")
    public static <ItemType> void setItems(RecyclerView recyclerView,
                                           Collection<ItemType> items) {
        BindingRecyclerViewAdapter<ItemType> adapter =
                  (BindingRecyclerViewAdapter<ItemType>) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.addAll(items);
        } else {
            recyclerView.setTag(KEY_ITEMS,
                                items);
        }
    }

    /**
     * Bind an adapter to a {@param recyclerView} view
     *
     * @param recyclerView Recycler view to bind adapter to
     * @param adapter      The adapter for the recycler
     * @param <ItemType>   The type of item being bound
     */
    @BindingAdapter("adapter")
    public static <ItemType> void setAdapter(RecyclerView recyclerView,
                                             BindingRecyclerViewAdapter<ItemType> adapter) {
        recyclerView.setAdapter(adapter);
        attachHandlers(recyclerView);
    }

    /**
     * Bind an {@itemViewMapper} to a recycler view, this tells it which layout to inflate and
     * which binding variable to bind to
     *
     * @param recyclerView The recycler view being bound
     * @param itemBinder   The mapping of layout and binding variable ids
     */
    @BindingAdapter("itemBinder")
    public static <ItemType> void setItemViewBinder(RecyclerView recyclerView,
                                                    AbstractItemBinder<ItemType> itemBinder) {
        Collection<ItemType> items =
                  (Collection<ItemType>) recyclerView.getTag(KEY_ITEMS);
        BindingRecyclerViewAdapter<ItemType> adapter =
                  (BindingRecyclerViewAdapter) recyclerView.getAdapter();

        if (adapter != null) {
            attachHandlers(recyclerView);
            return;
        }

        adapter = new BindingRecyclerViewAdapter<>(itemBinder);

        if (items != null) {
            adapter.addAll(items);
        }

        setAdapter(recyclerView,
                   adapter);
    }

    /**
     * Bind event {@param handler} for clicking on an item
     *
     * @param recyclerView Recycler view to bind to
     * @param handler      The click handler
     * @param <ItemType>   The type of item in the recycler view
     */
    @BindingAdapter("itemClickHandler")
    public static <ItemType> void setHandler(RecyclerView recyclerView,
                                             ClickHandler<ItemType> handler) {
        BindingRecyclerViewAdapter<ItemType> adapter =
                  (BindingRecyclerViewAdapter<ItemType>) recyclerView.getAdapter();

        if (adapter != null) {
            adapter.setClickHandler(handler);
        } else {
            // No adapter defined yet, store in view tag for later
            recyclerView.setTag(KEY_CLICK_HANDLER,
                                handler);
        }
    }

    /**
     * Bind event {@param handler} for long clicking on an item
     *
     * @param recyclerView Recycler view to bind to
     * @param handler      The long click handler
     * @param <ItemType>   The type of item in the recycler view
     */
    @BindingAdapter("itemLongClickHandler")
    public static <ItemType> void setHandler(RecyclerView recyclerView,
                                             LongClickHandler<ItemType> handler) {
        BindingRecyclerViewAdapter<ItemType> adapter =
                  (BindingRecyclerViewAdapter<ItemType>) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setLongClickHandler(handler);
        } else {
            // No adapter defined yet, store in view tag for later
            recyclerView.setTag(KEY_LONG_CLICK_HANDLER,
                                handler);
        }
    }

    @BindingAdapter("itemDoubleTapHandler")
    public static <ItemType> void setHandler(RecyclerView recyclerView,
                                             DoubleTapHandler<ItemType> handler) {
        BindingRecyclerViewAdapter<ItemType> adapter =
                  (BindingRecyclerViewAdapter<ItemType>) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setDoubleTapHandler(handler);
        } else {
            // No adapter defined yet, store in view tag for later
            recyclerView.setTag(KEY_DOUBLE_TAP_HANDLER,
                                handler);
        }
    }

    /**
     * Bind event {@param handler} for when a a view is attached to the window
     *
     * @param recyclerView Recycler view to bind to
     * @param handler      The event handler
     * @param <ItemType>   The type of item in the recycler view
     */
    @BindingAdapter("viewAttachedHandler")
    public static <ItemType> void setViewAttachedHandler(RecyclerView recyclerView,
                                                         ViewAttachedToWindowHandler<ItemType> handler) {
        BindingRecyclerViewAdapter<ItemType> adapter =
                  (BindingRecyclerViewAdapter<ItemType>) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setViewAttachedToWindowHandler(handler);
        } else {
            recyclerView.setTag(KEY_VIEW_ATTACHED_HANDLER,
                                handler);
        }
    }

    /**
     * Bind event {@param handler} for when a view is detached from the window
     *
     * @param recyclerView Recycler view to bind to
     * @param handler      The event handler
     * @param <ItemType>   The type of item in the recycler view
     */
    @BindingAdapter("viewDetachedHandler")
    public static <ItemType> void setViewDetachedHandler(RecyclerView recyclerView,
                                                         ViewDetachedFromWindowHandler<ItemType> handler) {
        BindingRecyclerViewAdapter<ItemType> adapter =
                  (BindingRecyclerViewAdapter<ItemType>) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setViewDetachedFromWindowHandler(handler);
        } else {
            recyclerView.setTag(KEY_VIEW_DETACHED_HANDLER,
                                handler);
        }
    }

    /**
     * Bind event {@param handler} for when a view is recycled
     *
     * @param recyclerView Recycler view to bind to
     * @param handler      The event handler
     * @param <ItemType>   The type of item in the recycler view
     */
    @BindingAdapter("viewRecycled")
    public static <ItemType> void setViewRecycledHandler(RecyclerView recyclerView,
                                                         ViewRecycledHandler<ItemType> handler) {
        BindingRecyclerViewAdapter<ItemType> adapter =
                  (BindingRecyclerViewAdapter<ItemType>) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setViewRecycledHandler(handler);
        } else {
            recyclerView.setTag(KEY_VIEW_RECYCLED_HANDLER,
                                handler);
        }
    }

    /**
     * Bind the {@param layoutManger} to the recycler view
     *
     * @param recyclerView  The recycler view to bind to
     * @param layoutManager The layout manager instance
     */
    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView,
                                        RecyclerView.LayoutManager layoutManager) {
        // Do not rebind a layout manager - throws exception
        if (recyclerView.getLayoutManager() == null
            || !recyclerView.getLayoutManager()
                            .equals(layoutManager)) {
            recyclerView.setLayoutManager(layoutManager);
        }
    }

    private static <ItemType> void attachHandlers(RecyclerView recyclerView) {
        ClickHandler<ItemType> clickHandler =
                  (ClickHandler<ItemType>) recyclerView.getTag(KEY_CLICK_HANDLER);
        LongClickHandler<ItemType> longClickHandler =
                  (LongClickHandler<ItemType>) recyclerView.getTag(KEY_LONG_CLICK_HANDLER);
        DoubleTapHandler<ItemType> doubleTapHandler =
                  (DoubleTapHandler<ItemType>) recyclerView.getTag(KEY_DOUBLE_TAP_HANDLER);
        ViewAttachedToWindowHandler<ItemType> viewAttachedToWindowHandler =
                  (ViewAttachedToWindowHandler<ItemType>) recyclerView.getTag(KEY_VIEW_ATTACHED_HANDLER);
        ViewDetachedFromWindowHandler<ItemType> viewDetachedFromWindowHandler =
                  (ViewDetachedFromWindowHandler<ItemType>) recyclerView.getTag(KEY_VIEW_DETACHED_HANDLER);
        ViewRecycledHandler<ItemType> viewRecycledHandler =
                  (ViewRecycledHandler<ItemType>) recyclerView.getTag(KEY_VIEW_RECYCLED_HANDLER);
        BindingRecyclerViewAdapter<ItemType> adapter =
                  (BindingRecyclerViewAdapter<ItemType>) recyclerView.getAdapter();

        if (clickHandler != null) {
            adapter.setClickHandler(clickHandler);
        }
        if (longClickHandler != null) {
            adapter.setLongClickHandler(longClickHandler);
        }
        if (doubleTapHandler != null) {
            adapter.setDoubleTapHandler(doubleTapHandler);
        }
        if (viewAttachedToWindowHandler != null) {
            adapter.setViewAttachedToWindowHandler(viewAttachedToWindowHandler);
        }
        if (viewDetachedFromWindowHandler != null) {
            adapter.setViewDetachedFromWindowHandler(viewDetachedFromWindowHandler);
        }
        if (viewRecycledHandler != null) {
            adapter.setViewRecycledHandler(viewRecycledHandler);
        }
    }
}
