package io.edanni.money.infrastructure.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by eduardo on 08/12/16.
 */
public class ViewWrapper<V extends View> extends RecyclerView.ViewHolder
{
    private V view;

    public ViewWrapper( V itemView )
    {
        super( itemView );
        view = itemView;
    }

    public V getView()
    {
        return view;
    }
}
