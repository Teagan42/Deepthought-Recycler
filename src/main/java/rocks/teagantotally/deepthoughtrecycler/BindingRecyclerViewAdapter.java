package rocks.teagantotally.deepthoughtrecycler;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rocks.teagantotally.deepthoughtrecycler.binders.AbstractItemBinder;
import rocks.teagantotally.deepthoughtrecycler.handlers.item.ClickHandler;
import rocks.teagantotally.deepthoughtrecycler.handlers.item.DoubleTapHandler;
import rocks.teagantotally.deepthoughtrecycler.handlers.item.LongClickHandler;
import rocks.teagantotally.deepthoughtrecycler.handlers.view.ViewAttachedToWindowHandler;
import rocks.teagantotally.deepthoughtrecycler.handlers.view.ViewDetachedFromWindowHandler;
import rocks.teagantotally.deepthoughtrecycler.handlers.view.ViewRecycledHandler;
import rocks.teagantotally.deepthoughtrecycler.listeners.AttachedToWindowListener;
import rocks.teagantotally.deepthoughtrecycler.listeners.DetachedFromWindowListener;
import rocks.teagantotally.deepthoughtrecycler.listeners.RecycledListener;

/**
 * Created by tglenn on 3/21/17.
 */

public class BindingRecyclerViewAdapter<ItemType>
          extends RecyclerView.Adapter<BindingRecyclerViewAdapter.ViewHolder>
          implements View.OnClickListener,
                     View.OnLongClickListener {
    public static final int ITEM_MODEL = -124;
    private static final String TAG = "BindingRecyclerViewAdap";
    private final AbstractItemBinder<ItemType> itemBinder;

    //region Item Event Handlers
    private ClickHandler<ItemType> clickHandler;
    private LongClickHandler<ItemType> longClickHandler;
    private DoubleTapHandler<ItemType> doubleTapHandler;
    //endregion

    //region View Event Handlers
    private ViewAttachedToWindowHandler<ItemType> viewAttachedToWindowHandler;
    private ViewDetachedFromWindowHandler<ItemType> viewDetachedFromWindowHandler;
    private ViewRecycledHandler<ItemType> viewRecycledHandler;
    //endregion

    private LayoutInflater inflater;
    private List<ItemType> items = new ArrayList<>();
    private WeakReferenceOnListChangedCallback<ItemType> onListChangedCallback;

    /**
     * Instantiate a new binding recycler view adapter
     *
     * @param itemBinder The layout binder for items in the adapter
     */
    public BindingRecyclerViewAdapter(AbstractItemBinder<ItemType> itemBinder) {
        this.itemBinder = itemBinder;
        this.onListChangedCallback = new WeakReferenceOnListChangedCallback(this);
    }

    /**
     * Return the collection of items in the recycler view
     *
     * @return
     */
    public List<ItemType> getItems() {
        return items;
    }

    /**
     * Set the handler for clicking on an item in the recycler view
     *
     * @param clickHandler Event handler
     */
    public void setClickHandler(ClickHandler<ItemType> clickHandler) {
        this.clickHandler = clickHandler;
    }

    /**
     * Set the handler for long clicking on an item in the recycler view
     *
     * @param clickHandler Event handler
     */
    public void setLongClickHandler(LongClickHandler<ItemType> clickHandler) {
        this.longClickHandler = clickHandler;
    }

    /**
     * Set the handler for double tapping on an item in the recycler view
     *
     * @param doubleTapHandler Event handler
     */
    public void setDoubleTapHandler(DoubleTapHandler<ItemType> doubleTapHandler) {
        this.doubleTapHandler = doubleTapHandler;
    }

    /**
     * Set the handler for when a view is attached to the window
     *
     * @param handler Event handler
     */
    public void setViewAttachedToWindowHandler(ViewAttachedToWindowHandler<ItemType> handler) {
        this.viewAttachedToWindowHandler = handler;
    }

    /**
     * Set the handler for when the view is detached from the window
     *
     * @param handler Event handler
     */
    public void setViewDetachedFromWindowHandler(ViewDetachedFromWindowHandler<ItemType> handler) {
        this.viewDetachedFromWindowHandler = handler;
    }

    /**
     * Set the handler for when a view is recycled
     *
     * @param handler Event handler
     */
    public void setViewRecycledHandler(ViewRecycledHandler<ItemType> handler) {
        this.viewRecycledHandler = handler;
    }

    /**
     * Called when RecyclerView needs a new {@link RecyclerView.ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(RecyclerView.ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param layoutId The layotut resource id
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(RecyclerView.ViewHolder, int)
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int layoutId) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        ViewDataBinding binding = DataBindingUtil.inflate(inflater,
                                                          layoutId,
                                                          parent,
                                                          false);
        return new ViewHolder(binding);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link RecyclerView.ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link android.widget.ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link RecyclerView.ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(RecyclerView.ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder,
                                 int position) {
        final ItemType item = items.get(position);
        final ViewDataBinding binding = holder.binding;

        binding.setVariable(itemBinder.getBindingVariableId(item),
                            item);
        binding.getRoot()
               .setTag(ITEM_MODEL,
                       item);
        binding.getRoot()
               .setOnClickListener(this);
        binding.getRoot()
               .setOnLongClickListener(this);
        binding.executePendingBindings();
    }

    public void add(ItemType item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void add(ItemType item,
                    int index) {
        items.add(index,
                  item);
        notifyItemInserted(index);
    }

    public void addAll(Collection<ItemType> items) {
        int index = items.size() - 1;
        this.items.addAll(items);
        notifyItemRangeInserted(index,
                                items.size());
    }

    public void remove(ItemType item) {
        int index = items.indexOf(item);
        if (index < 0) {
            return;
        }

        items.remove(index);
        notifyItemRemoved(index);
    }

    public void remove(int index) {
        items.remove(index);
        notifyItemRemoved(index);
    }

    public void removeAll(Collection<ItemType> items) {
        for (ItemType item : items) {
            remove(item);
        }
    }

    public void updateItem(int index,
                           ItemType newItem) {
        this.items.remove(index);
        this.items.add(index,
                       newItem);
        notifyItemChanged(index);
    }

    public void moveItem(int fromIndex,
                         int toIndex) {
        ItemType item = this.items.remove(fromIndex);
        this.items.add(toIndex,
                       item);
        notifyItemMoved(fromIndex,
                        toIndex);
    }

    /**
     * Return the view type of the item at <code>position</code> for the purposes
     * of view recycling.
     * <p>
     * <p>The default implementation of this method returns 0, making the assumption of
     * a single view type for the adapter. Unlike ListView adapters, types need not
     * be contiguous. Consider using id resources to uniquely identify item view types.
     *
     * @param position position to query
     * @return integer value identifying the type of the view needed to represent the item at
     * <code>position</code>. Type codes need not be contiguous.
     */
    @Override
    public int getItemViewType(int position) {
        return itemBinder.getLayoutResourceId(items.get(position));
    }


    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return items == null
               ? 0
               : items.size();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (clickHandler != null) {
            ItemType item = (ItemType) v.getTag(ITEM_MODEL);
            clickHandler.onClick(item);
        }
    }

    /**
     * Called when a view has been long clicked.
     *
     * @param v The view that was long clicked.
     */
    @Override
    public boolean onLongClick(View v) {
        if (longClickHandler != null) {
            ItemType item = (ItemType) v.getTag(ITEM_MODEL);
            longClickHandler.onLongClick(item);
            return true;
        }

        return false;
    }

    /**
     * Called when a view created by this adapter has been attached to a window.
     * <p>
     * <p>This can be used as a reasonable signal that the view is about to be seen
     * by the user. If the adapter previously freed any resources in
     * {@link #onViewDetachedFromWindow(RecyclerView.ViewHolder) onDetachedFromWindow}
     * those resources should be restored here.</p>
     *
     * @param viewHolder Holder of the view being attached
     */
    @Override
    public void onViewAttachedToWindow(ViewHolder viewHolder) {
        ItemType item = (ItemType) viewHolder.binding.getRoot()
                                                     .getTag(ITEM_MODEL);
        if (item instanceof AttachedToWindowListener) {
            ((AttachedToWindowListener) item).onAttachedToWindow();
        }
        if (viewAttachedToWindowHandler != null) {
            viewAttachedToWindowHandler.onViewAttachedToWindow(viewHolder.binding.getRoot(),
                                                               item);
        }

        super.onViewAttachedToWindow(viewHolder);
    }

    /**
     * Called when a view created by this adapter has been detached from its window.
     * <p>
     * <p>Becoming detached from the window is not necessarily a permanent condition;
     * the consumer of an Adapter's views may choose to cache views offscreen while they
     * are not visible, attaching and detaching them as appropriate.</p>
     *
     * @param viewHolder Holder of the view being detached
     */
    @Override
    public void onViewDetachedFromWindow(ViewHolder viewHolder) {
        ItemType item = (ItemType) viewHolder.binding.getRoot()
                                                     .getTag(ITEM_MODEL);
        if (item instanceof DetachedFromWindowListener) {
            ((DetachedFromWindowListener) item).onDetachedFromWindow();
        }
        if (viewDetachedFromWindowHandler != null) {
            viewDetachedFromWindowHandler.onViewDetachedFromWindow(viewHolder.binding.getRoot(),
                                                                   item);
        }

        super.onViewDetachedFromWindow(viewHolder);
    }

    /**
     * This method is called whenever the view in the ViewHolder is recycled.
     * <p>
     * RecyclerView calls this method right before clearing ViewHolder's internal data and
     * sending it to RecycledViewPool. This way, if ViewHolder was holding valid information
     * before being recycled, you can call {@link RecyclerView.ViewHolder#getAdapterPosition()} to get
     * its adapter position.
     *
     * @param viewHolder The ViewHolder containing the view that was recycled
     */
    @Override
    public void onViewRecycled(ViewHolder viewHolder) {
        ItemType item = (ItemType) viewHolder.binding.getRoot()
                                                     .getTag(ITEM_MODEL);
        if (item instanceof RecycledListener) {
            ((RecycledListener) item).onRecycled();
        }
        if (viewRecycledHandler != null) {
            viewRecycledHandler.onViewRecycled(viewHolder.binding.getRoot(),
                                               item);
        }

        super.onViewRecycled(viewHolder);
    }

    static class ViewHolder
              extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private static class WeakReferenceOnListChangedCallback<ItemType>
              extends ObservableList.OnListChangedCallback {

        private final WeakReference<BindingRecyclerViewAdapter<ItemType>> adapterReference;

        WeakReferenceOnListChangedCallback(BindingRecyclerViewAdapter<ItemType>
                                                     bindingRecyclerViewAdapter) {
            this.adapterReference = new WeakReference<>(bindingRecyclerViewAdapter);
        }

        @Override
        public void onChanged(ObservableList sender) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeChanged(ObservableList sender,
                                       int positionStart,
                                       int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeChanged(positionStart,
                                               itemCount);
            }
        }

        @Override
        public void onItemRangeInserted(ObservableList sender,
                                        int positionStart,
                                        int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeInserted(positionStart,
                                                itemCount);
            }
        }

        @Override
        public void onItemRangeMoved(ObservableList sender,
                                     int fromPosition,
                                     int toPosition,
                                     int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemMoved(fromPosition,
                                        toPosition);
            }
        }

        @Override
        public void onItemRangeRemoved(ObservableList sender,
                                       int positionStart,
                                       int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeRemoved(positionStart,
                                               itemCount);
            }
        }
    }
}
