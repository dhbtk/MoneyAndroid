package io.edanni.money.ui.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import io.edanni.money.R;
import io.edanni.money.domain.entity.Credit;
import io.edanni.money.domain.entity.Statement;
import io.edanni.money.domain.repository.StatementRepository;
import io.edanni.money.infrastructure.rest.Page;
import io.edanni.money.infrastructure.rest.RetrofitFactory;
import io.edanni.money.ui.list.StatementViewAdapter;
import io.edanni.money.ui.list.listener.EndlessScrollListener;
import org.androidannotations.annotations.*;
import org.androidannotations.api.BackgroundExecutor;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
@EFragment(R.layout.fragment_statement_list)
public class StatementListFragment extends Fragment
{
    @ViewById(R.id.list)
    RecyclerView statementList;
    @ViewById(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @ViewById
    FloatingActionButton fab;
    @ViewById
    ProgressBar progressBar;
    @Bean
    StatementViewAdapter statementViewAdapter;
    @Bean
    RetrofitFactory retrofitFactory;
    StatementRepository statementRepository;
    private EndlessScrollListener scrollListener;

    @AfterViews
    void afterViews()
    {
        getActivity().setTitle( getString( R.string.statements ) );
        statementRepository = retrofitFactory.createService( StatementRepository.class );
        statementList.setAdapter( statementViewAdapter );
        scrollListener = new EndlessScrollListener( (LinearLayoutManager) statementList.getLayoutManager() )
        {
            @Override
            public void onLoadMore( int page, int totalItemsCount, RecyclerView view )
            {
                getStatements( page );
            }
        };
        statementList.addOnScrollListener( scrollListener );
        swipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                getStatements();
            }
        } );
        getStatements();
    }

    @Background(id = "statements", serial = "statements")
    void getStatements()
    {
        try
        {
            startLoading();
            Page<Statement> statements = statementRepository.getStatements( "past", 1 ).execute().body();
            showStatements( statements.content );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    @UiThread
    void showStatements( List<Statement> statements )
    {
        ((StatementViewAdapter) statementList.getAdapter()).setItems( statements );
        swipeRefreshLayout.setRefreshing( false );
        scrollListener.resetState();
        stopLoading();
    }

    @Background(id = "statements", serial = "statements")
    void getStatements( int page )
    {
        try
        {
            startLoading();
            Page<Statement> statements = statementRepository.getStatements( "past", page ).execute().body();
            addStatements( statements.content );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
            stopLoading();
        }
    }

    @UiThread
    void addStatements( List<Statement> statements )
    {
        ((StatementViewAdapter) statementList.getAdapter()).getItems().addAll( statements );
        statementList.getAdapter().notifyDataSetChanged();
        stopLoading();
    }

    @UiThread
    void startLoading()
    {
        progressBar.setVisibility( View.VISIBLE );
    }

    @UiThread
    void stopLoading()
    {
        progressBar.setVisibility( View.INVISIBLE );
    }

    @Click
    void fab()
    {
        BackgroundExecutor.cancelAll( "statements", true );
        swipeRefreshLayout.setRefreshing( false );
        NewStatementDialogFragment dialog = new NewStatementDialogFragment_();
        dialog.setTargetFragment( this, 300 );
        dialog.show( getActivity().getSupportFragmentManager(), "fragment_statement_new_dialog" );
    }

    @Background
    void deleteCredit( Integer id )
    {
        startLoading();
        try
        {
            Response<Void> response = statementRepository.deleteCredit( id ).execute();
            showCreditDeleted();
            stopLoading();
            getStatements();
        }
        catch ( IOException e )
        {
            stopLoading();
        }
    }

    @Background
    void deleteDebit( Integer id )
    {
        startLoading();
        try
        {
            Response<Void> response = statementRepository.deleteDebit( id ).execute();
            showDebitDeleted();
            stopLoading();
            getStatements();
        }
        catch ( IOException e )
        {
            stopLoading();
        }
    }

    @UiThread
    void showCreditDeleted()
    {
        Toast.makeText( getActivity(), getString( R.string.spending_deleted), Toast.LENGTH_LONG ).show();
    }

    @UiThread
    void showDebitDeleted()
    {
        Toast.makeText( getActivity(), getString( R.string.income_deleted), Toast.LENGTH_LONG ).show();
    }

    public void deleteStatement( Statement statement )
    {
        if ( statement instanceof Credit )
        {
            deleteCredit( statement.id );
        }
        else
        {
            deleteDebit( statement.id );
        }
    }
}
