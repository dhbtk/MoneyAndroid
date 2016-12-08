package io.edanni.money.ui.fragment;

import android.content.Context;
import android.view.ViewGroup;
import io.edanni.money.domain.entity.Statement;
import io.edanni.money.infrastructure.recyclerview.RecyclerViewAdapterBase;
import io.edanni.money.infrastructure.recyclerview.ViewWrapper;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class StatementViewAdapter extends RecyclerViewAdapterBase<Statement, StatementItemView>
{
    @RootContext
    Context context;

    @Override
    protected StatementItemView onCreateItemView( ViewGroup parent, int viewType )
    {
        return StatementItemView_.build( context );
    }

    @Override
    public void onBindViewHolder( ViewWrapper<StatementItemView> holder, int position )
    {
        StatementItemView view = holder.getView();
        Statement statement = items.get( position );
        view.bind( statement );
    }

    public List<Statement> getItems()
    {
        return items;
    }

    public void setItems( List<Statement> statements )
    {
        this.items = statements;
        notifyDataSetChanged();
    }
}
