package io.edanni.money.ui.list.view;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import io.edanni.money.R;
import io.edanni.money.domain.entity.Credit;
import io.edanni.money.domain.entity.Statement;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.DecimalFormat;
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
        setLayoutParams( new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT ) );
    }

    public void bind( Statement statement )
    {
        if ( statement instanceof Credit )
        {
            source.setText( statement.account.name );
            source.setBackgroundResource( R.color.transferBackground );
            destination.setText( getContext().getString( R.string.spendings) );
            destination.setBackgroundResource( R.color.creditBackground );
        }
        else
        {
            destination.setText( statement.account.name );
            destination.setBackgroundResource( R.color.debitBackground );
            source.setText( getContext().getString( R.string.incomes) );
            source.setBackgroundResource( R.color.transferBackground );
        }
        name.setText( statement.name );
        value.setText( String.format( getContext().getString( R.string.currency_value), new DecimalFormat( "#,##0.00" ).format( statement.value ) ) );
        date.setText( SimpleDateFormat.getDateInstance().format( statement.date.getTime() ) );
    }
}
