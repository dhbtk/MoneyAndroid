package io.edanni.money.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import io.edanni.money.R;
import io.edanni.money.domain.entity.Statement;
import io.edanni.money.domain.repository.StatementRepository;
import io.edanni.money.infrastructure.rest.Page;
import io.edanni.money.infrastructure.rest.RetrofitFactory;
import io.edanni.money.ui.fragment.dummy.DummyContent;
import io.edanni.money.ui.fragment.dummy.DummyContent.DummyItem;
import org.androidannotations.annotations.*;

import java.io.IOException;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
@EFragment(R.layout.fragment_statement_list)
public class StatementFragment extends Fragment
{
    @ViewById(R.id.statement_list)
    RecyclerView statementList;
    @Bean
    RetrofitFactory retrofitFactory;
    StatementRepository statementRepository;

    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StatementFragment()
    {
    }

    @AfterViews
    void afterViews()
    {
        statementRepository = retrofitFactory.createService( StatementRepository.class );
        statementList.setAdapter( new StatementViewAdapter( DummyContent.ITEMS, null ) );
        getStatements();
    }

    @Background
    void getStatements()
    {
        try
        {
            Statement statement = statementRepository.getCredit( 186 ).execute().body();
            showStatements( null );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        try
        {
            Page<Statement> statements = statementRepository.getStatements( "past", 1 ).execute().body();
            showStatements( null );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }

        showStatements( null );
    }

    @UiThread
    void showStatements(List<Statement> statements)
    {
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onListFragmentInteraction( DummyItem item );
    }
}
