package io.edanni.money.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import fr.ganfra.materialspinner.MaterialSpinner;
import io.edanni.money.R;
import io.edanni.money.domain.entity.Category;
import io.edanni.money.domain.entity.Credit;
import io.edanni.money.domain.entity.NamedEntity;
import io.edanni.money.domain.entity.Statement;
import io.edanni.money.domain.repository.StatementRepository;
import io.edanni.money.domain.repository.CategoryRepository;
import io.edanni.money.infrastructure.rest.RetrofitFactory;
import io.edanni.money.ui.list.NamedEntityListAdapter;
import org.androidannotations.annotations.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eduardo on 09/12/16.
 */
@EFragment(R.layout.fragment_statement_form)
@OptionsMenu(R.menu.form)
public class EditStatementFragment extends Fragment
{
    @ViewById
    EditText name;
    @ViewById
    EditText value;
    @ViewById
    EditText date;
    @ViewById
    MaterialSpinner category;
    @ViewById
    ProgressBar progressBar;
    @OptionsMenuItem(R.id.menu_save)
    MenuItem save;
    @Bean
    RetrofitFactory retrofitFactory;
    private Statement statement;
    private NamedEntityListAdapter categoryAdapter;
    private StatementRepository statementRepository;
    private CategoryRepository categoryRepository;

    public static EditStatementFragment newInstance( Statement statement )
    {
        EditStatementFragment fragment = new EditStatementFragment_();
        Bundle arguments = new Bundle();
        arguments.putSerializable( "statement", statement );
        fragment.setArguments( arguments );
        return fragment;
    }

    @AfterViews
    void startup()
    {
        statementRepository = retrofitFactory.createService( StatementRepository.class );
        categoryRepository = retrofitFactory.createService( CategoryRepository.class );
        lockForm();
        loadStatement( (Statement) getArguments().getSerializable( "statement" ) );
        loadTags();
    }

    @Background
    void loadStatement( Statement statement )
    {
        if ( statement instanceof Credit )
        {
            try
            {
                this.statement = statementRepository.getCredit( statement.id ).execute().body();
                unlockForm();
            }
            catch ( IOException e )
            {
                Toast.makeText( getActivity(), e.getMessage(), Toast.LENGTH_LONG ).show();
            }
        }
        else
        {
            try
            {
                this.statement = statementRepository.getDebit( statement.id ).execute().body();
                unlockForm();
            }
            catch ( IOException e )
            {
                Toast.makeText( getActivity(), e.getMessage(), Toast.LENGTH_LONG ).show();
            }
        }
        unlockForm();
    }

    @Background
    void loadTags()
    {
        try
        {
            setTags( this.categoryRepository.getCategories().execute().body() );
        }
        catch ( IOException e )
        {
            Toast.makeText( getActivity(), e.getMessage(), Toast.LENGTH_LONG ).show();
        }
    }

    @UiThread
    void setTags( List<Category> tags )
    {
        List<NamedEntity> entities = new ArrayList<>();
        for ( Category category : tags )
        {
            entities.add( category );
        }
        categoryAdapter = new NamedEntityListAdapter( getContext(), entities );
        category.setAdapter( categoryAdapter );
        unlockForm();
    }

    @UiThread
    void lockForm()
    {
        progressBar.setVisibility( View.VISIBLE );
        name.setEnabled( false );
        value.setEnabled( false );
        date.setEnabled( false );
        category.setEnabled( false );
        save.setEnabled( false );
    }

    @UiThread
    void unlockForm()
    {
        if ( statement != null && categoryAdapter != null )
        {
            bindValues();
            progressBar.setVisibility( View.INVISIBLE );
            name.setEnabled( true );
            value.setEnabled( true );
            date.setEnabled( true );
            category.setEnabled( true );
            save.setEnabled( true );
        }
    }

    void bindValues()
    {
        name.setText( statement.name );
        value.setText( statement.value.toString() );
        date.setText( SimpleDateFormat.getDateInstance().format( statement.date.getTime() ) );
        category.setSelection( categoryAdapter.getPositionById( statement.categoryId ) + 1 );
    }
}
