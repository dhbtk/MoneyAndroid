package io.edanni.money.ui.list.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import io.edanni.money.R;
import io.edanni.money.domain.entity.Credit;
import io.edanni.money.domain.entity.Statement;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;

/**
 * Created by eduardo on 08/12/16.
 */
@EViewGroup(R.layout.statement_item)
public class StatementItemView extends LinearLayout
{
    @ViewById
    TextView source;
    @ViewById
    TextView destination;
    @ViewById
    TextView name;
    @ViewById
    TextView value;
    @ViewById
    TextView date;

    public StatementItemView( Context context )
    {
        super( context );
    }

    public void bind( Statement statement )
    {
        if ( statement instanceof Credit )
        {
            source.setText( statement.account.name );
            destination.setText( getContext().getString( R.string.spendings) );
        }
        else
        {
            destination.setText( statement.account.name );
            source.setText( getContext().getString( R.string.incomes) );
        }
        name.setText( statement.name );
        value.setText( String.format( getContext().getString( R.string.currency_value), statement.value ) );
        date.setText( new SimpleDateFormat("dd-MM-yyyy").format( statement.date.getTime() ) );
    }
}
