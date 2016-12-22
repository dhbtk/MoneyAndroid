package io.edanni.money.ui.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import io.edanni.money.domain.entity.NamedEntity;
import io.edanni.money.ui.list.view.NamedEntityListItemView;

import java.util.List;

/**
 * Created by eduardo on 09/12/16.
 */
public class NamedEntityListAdapter extends BaseAdapter
{
    private List<NamedEntity> items;
    private Context context;

    public NamedEntityListAdapter( Context context )
    {
        this.context = context;
    }

    public NamedEntityListAdapter( Context context, List<NamedEntity> items )
    {
        this( context );
        setItems( items );
    }

    @Override
    public int getCount()
    {
        return items.size();
    }

    @Override
    public NamedEntity getItem( int i )
    {
        return items.get( i );
    }

    @Override
    public long getItemId( int i )
    {
        return i;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        NamedEntityListItemView view;
        if ( convertView == null )
        {
            view = new NamedEntityListItemView( context );
        }
        else
        {
            view = (NamedEntityListItemView) convertView;
        }
        view.bind( getItem( position ) );
        return view;
    }

    @Override
    public View getDropDownView( int position, View convertView, ViewGroup parent )
    {
        return getView( position, convertView, parent );
    }

    public void setItems( List<NamedEntity> items )
    {
        this.items = items;
        notifyDataSetChanged();
    }

    public List<NamedEntity> getItems()
    {
        return this.items;
    }

    public int getPositionById( int id )
    {
        for(int i = 0; i < items.size(); i++)
        {
            if ( items.get( i ).id == id )
            {
                return i;
            }
        }
        return -1;
    }
}
