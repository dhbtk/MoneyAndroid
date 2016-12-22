package io.edanni.money.ui.list.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import io.edanni.money.R;
import io.edanni.money.domain.entity.Credit;
import io.edanni.money.domain.entity.Statement;
import io.edanni.money.ui.activity.MainActivity;
import io.edanni.money.ui.fragment.DeleteStatementDialogFragment;
import io.edanni.money.ui.fragment.DeleteStatementDialogFragment_;
import io.edanni.money.ui.fragment.EditStatementFragment;
import io.edanni.money.ui.fragment.StatementListFragment;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by eduardo on 08/12/16.
 */
@EViewGroup(R.layout.statement_item)
public class StatementItemView extends LinearLayout implements PopupMenu.OnMenuItemClickListener
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
    @ViewById
    ImageButton edit;

    private Statement statement;

    public StatementItemView( Context context )
    {
        super( context );
        setLayoutParams( new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT ) );
    }

    public void bind( Statement statement )
    {
        this.statement = statement;
        if ( statement instanceof Credit )
        {
            source.setText( statement.account.name );
            source.setBackgroundResource( R.color.transferBackground );
            destination.setText( getContext().getString( R.string.spendings ) );
            destination.setBackgroundResource( R.color.creditBackground );
        }
        else
        {
            destination.setText( statement.account.name );
            destination.setBackgroundResource( R.color.debitBackground );
            source.setText( getContext().getString( R.string.incomes ) );
            source.setBackgroundResource( R.color.transferBackground );
        }
        name.setText( statement.name );
        value
            .setText( String.format( getContext().getString( R.string.currency_value ), new DecimalFormat( "#,##0.00" ).format( statement.value ) ) );
        date.setText( SimpleDateFormat.getDateInstance().format( statement.date.getTime() ) );
    }

    @Click
    void edit()
    {
        PopupMenu menu = new PopupMenu( getContext(), edit );
        menu.inflate( R.menu.statement_list );
        menu.setOnMenuItemClickListener( this );
        menu.show();
    }

    @Override
    public boolean onMenuItemClick( MenuItem item )
    {
        switch ( item.getItemId() )
        {
            case R.id.edit:
                EditStatementFragment fragment = EditStatementFragment.newInstance( statement );
                assert getActivity() != null;
                ((MainActivity) getActivity()).changeToFragment( fragment );
                return true;
            case R.id.delete:
                DeleteStatementDialogFragment dialog = new DeleteStatementDialogFragment_();
                dialog.setListener( new DeleteStatementDialogFragment.OnDeleteButtonClickedListener()
                {
                    @Override
                    public void onDeleteButtonClicked()
                    {
                        StatementListFragment fragment =
                            (StatementListFragment) ((MainActivity) getActivity()).getSupportFragmentManager().findFragmentByTag( "StatementListFragment_" );

                        fragment.deleteStatement( statement );
                    }
                } );
                dialog.show( ((MainActivity) getActivity()).getSupportFragmentManager(), null );
                return true;
        }
        return false;
    }

    private Activity getActivity()
    {
        Context context = getContext();
        while ( context instanceof ContextWrapper )
        {
            if ( context instanceof Activity )
            {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}
