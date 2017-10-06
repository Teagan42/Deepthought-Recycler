package rocks.teagantotally.sample.ui.viewmodels;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Random;

import rocks.teagantotally.deepthoughtrecycler.BindingRecyclerViewAdapter;
import rocks.teagantotally.deepthoughtrecycler.handlers.item.ClickHandler;
import rocks.teagantotally.deepthoughtrecycler.handlers.item.LongClickHandler;
import rocks.teagantotally.sample.data.Colors;
import rocks.teagantotally.sample.data.Names;
import rocks.teagantotally.sample.ui.AnimatedLinearLayoutManager;
import rocks.teagantotally.sample.ui.itembinders.FeedItemBinder;

/**
 * Created by tglenn on 10/5/17.
 */

public class FeedViewModel {
    private static String getStringValueOf(Object item) {
        if (item instanceof Integer) {
            return Integer.toHexString((Integer) item);
        } else {
            return String.valueOf(item);
        }
    }

    private Context context;
    private BindingRecyclerViewAdapter<Object> adapter;
    private RecyclerView.LayoutManager layoutManager;
    private View.OnClickListener addColorClickListener =
              new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      int index = adapter.getItemCount() > 0
                                  ? new Random().nextInt(adapter.getItemCount())
                                  : 0;
                      adapter.add(Colors.getRandomColor(),
                                  index);
                  }
              };
    private View.OnClickListener addNameClickListener =
              new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      int index = adapter.getItemCount() > 0
                                  ? new Random().nextInt(adapter.getItemCount())
                                  : 0;
                      adapter.add(Names.getRandomName(),
                                  index);
                  }
              };
    private ClickHandler<Object> itemClickHandler =
              new ClickHandler<Object>() {
                  @Override
                  public void onClick(Object item) {
                      String message = "You clicked on " + getStringValueOf(item);
                      new AlertDialog.Builder(context).setMessage(message)
                                                      .show();
                  }
              };
    private LongClickHandler<Object> itemLongClickHandler =
              new LongClickHandler<Object>() {
                  @Override
                  public void onLongClick(Object item) {
                      String message = "You long clicked on " + getStringValueOf(item);
                      new AlertDialog.Builder(context).setMessage(message)
                                                      .show();
                  }
              };

    public FeedViewModel(Context context) {
        this.context = context;
        adapter = new BindingRecyclerViewAdapter<>(new FeedItemBinder());
        layoutManager = new AnimatedLinearLayoutManager(context);
    }

    public BindingRecyclerViewAdapter<Object> getAdapter() {
        return adapter;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public View.OnClickListener getAddColorClickListener() {
        return addColorClickListener;
    }

    public View.OnClickListener getAddNameClickListener() {
        return addNameClickListener;
    }

    public ClickHandler<Object> getItemClickHandler() {
        return itemClickHandler;
    }

    public LongClickHandler<Object> getItemLongClickHandler() {
        return itemLongClickHandler;
    }
}
