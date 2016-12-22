package io.edanni.money.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import fr.ganfra.materialspinner.MaterialSpinner;
import io.edanni.money.R;
import io.edanni.money.domain.entity.*;
import io.edanni.money.domain.repository.AccountRepository;
import io.edanni.money.domain.repository.StatementRepository;
import io.edanni.money.domain.repository.CategoryRepository;
import io.edanni.money.infrastructure.rest.RetrofitFactory;
import io.edanni.money.ui.list.NamedEntityListAdapter;
import org.androidannotations.annotations.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eduardo on 08/12/16.
 */
@EFragment(R.layout.fragment_statement_new)
@OptionsMenu(R.menu.form)
public class NewStatementFragment extends Fragment
{
    /*-------------------------------------------------------------------
     *                            ATTRIBUTES
     *-------------------------------------------------------------------*/

    @ViewById
    MaterialSpinner account;
    @ViewById
    MaterialSpinner category;
    @ViewById
    TextView value;
    @ViewById
    Button b1;
    @ViewById
    Button b2;
    @ViewById
    Button b3;
    @ViewById
    Button b4;
    @ViewById
    Button b5;
    @ViewById
    Button b6;
    @ViewById
    Button b7;
    @ViewById
    Button b8;
    @ViewById
    Button b9;
    @ViewById
    Button b0;
    @ViewById
    ImageButton backspace;
    @ViewById
    ProgressBar progressBar;
    @OptionsMenuItem(R.id.menu_save)
    MenuItem save;
    @Bean
    RetrofitFactory retrofitFactory;

    CategoryRepository categoryRepository;
    AccountRepository accountRepository;
    StatementRepository statementRepository;

    protected Statement statement = new Statement();

    /*-------------------------------------------------------------------
     *                           INITIALIZERS
     *-------------------------------------------------------------------*/


    public static NewStatementFragment newInstance( Statement statement )
    {
        NewStatementFragment fragment = new NewStatementFragment_();
        Bundle arguments = new Bundle();
        arguments.putSerializable( "statement", statement );
        fragment.setArguments( arguments );
        return fragment;
    }

    @AfterViews
    void getSpinnerData()
    {
        lockForm();
        Statement receivedStatement = (Statement) getArguments().getSerializable( "statement" );
        if ( receivedStatement != null )
        {
            statement = receivedStatement;
            if ( statement instanceof Credit )
            {
                getActivity().setTitle( getString( R.string.new_spending) );
            }
            else
            {
                getActivity().setTitle( getString( R.string.new_income) );
            }
        }
        categoryRepository = retrofitFactory.createService( CategoryRepository.class );
        accountRepository = retrofitFactory.createService( AccountRepository.class );
        statementRepository = retrofitFactory.createService( StatementRepository.class );
        loadTags();
        loadAccounts();

        if ( statement.value == null )
        {
            statement.value = new BigDecimal( "0.00" );
        }
        updateValueDisplay();
    }

    @UiThread
    void lockForm()
    {
        progressBar.setVisibility( View.VISIBLE );
        category.setEnabled( false );
        account.setEnabled( false );
        b1.setEnabled( false );
        b2.setEnabled( false );
        b3.setEnabled( false );
        b4.setEnabled( false );
        b5.setEnabled( false );
        b6.setEnabled( false );
        b7.setEnabled( false );
        b8.setEnabled( false );
        b9.setEnabled( false );
        b0.setEnabled( false );
        backspace.setEnabled( false );
        save.setEnabled( false );
    }

    @UiThread
    void unlockForm()
    {
        if ( category.getAdapter() != null && account.getAdapter() != null )
        {
            category.setEnabled( true );
            account.setEnabled( true );
            b1.setEnabled( true );
            b2.setEnabled( true );
            b3.setEnabled( true );
            b4.setEnabled( true );
            b5.setEnabled( true );
            b6.setEnabled( true );
            b7.setEnabled( true );
            b8.setEnabled( true );
            b9.setEnabled( true );
            b0.setEnabled( true );
            backspace.setEnabled( true );
            save.setEnabled( true );
            progressBar.setVisibility( View.INVISIBLE );
        }
    }

    @Background
    void loadTags()
    {
        try
        {
            List<Category> tags = categoryRepository.getCategories().execute().body();
            setTags( tags );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    @Background
    void loadAccounts()
    {
        try
        {
            List<Account> accounts = accountRepository.getAccounts().execute().body();
            setAccounts( accounts );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
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
        category.setAdapter( new NamedEntityListAdapter( getContext(), entities ) );
        unlockForm();
    }

    @UiThread
    void setAccounts( List<Account> accounts )
    {
        List<NamedEntity> entities = new ArrayList<>();
        for ( Account account : accounts )
        {
            entities.add( account );
        }
        account.setAdapter( new NamedEntityListAdapter( getContext(), entities ) );
        unlockForm();
    }

    /*-------------------------------------------------------------------
     *                             BEHAVIORS
     *-------------------------------------------------------------------*/

    private void addDigit( int i )
    {
        assert i >= 0 && i < 10;
        statement.value = statement.value.multiply( new BigDecimal( "10" ) );
        statement.value = statement.value.add( new BigDecimal( "0.0" + i ) );
        updateValueDisplay();
    }

    private void removeDigit()
    {
        statement.value = statement.value.subtract( statement.value.remainder( new BigDecimal( "0.1" ) ) );
        statement.value = statement.value.divide( new BigDecimal( "10" ), RoundingMode.FLOOR );
        updateValueDisplay();
    }

    private void updateValueDisplay()
    {
        value.setText( new DecimalFormat( "#,##0.00" ).format( statement.value ) );
    }

    private boolean validateAndUpdateStatement()
    {
        if ( account.getSelectedItem() == null )
        {
            return false;
        }
        if ( statement.value.equals( new BigDecimal( "0.00" ) ) )
        {
            return false;
        }
        statement.accountId = ((Account) account.getSelectedItem()).id;
        if ( category.getSelectedItem() != null && category.getSelectedItem() instanceof Category )
        {
            statement.name = ((Category) category.getSelectedItem()).name;
            statement.categoryId = ((Category) category.getSelectedItem()).id;
        }
        return true;
    }

    /*-------------------------------------------------------------------
     *                             HANDLERS
     *-------------------------------------------------------------------*/

    @Click
    void backspace()
    {
        removeDigit();
    }

    @LongClick(R.id.backspace)
    void backspaceLongClick()
    {
        statement.value = new BigDecimal( "0.00" );
        updateValueDisplay();
    }

    @Click
    void b1()
    {
        addDigit( 1 );
    }

    @Click
    void b2()
    {
        addDigit( 2 );
    }

    @Click
    void b3()
    {
        addDigit( 3 );
    }

    @Click
    void b4()
    {
        addDigit( 4 );
    }

    @Click
    void b5()
    {
        addDigit( 5 );
    }

    @Click
    void b6()
    {
        addDigit( 6 );
    }

    @Click
    void b7()
    {
        addDigit( 7 );
    }

    @Click
    void b8()
    {
        addDigit( 8 );
    }

    @Click
    void b9()
    {
        addDigit( 9 );
    }

    @Click
    void b0()
    {
        addDigit( 0 );
    }

    @OptionsItem(R.id.menu_save)
    void save()
    {
        if ( validateAndUpdateStatement() )
        {
            lockForm();
            createStatement();
        }
        else
        {
            Toast.makeText( getContext(), "Por favor selecione uma conta e insira um valor.", Toast.LENGTH_LONG ).show();
        }
    }

    @Background
    void createStatement()
    {
        if ( statement instanceof Credit )
        {
            try
            {
                Credit credit = statementRepository.createCredit( (Credit) statement ).execute().body();
                if ( credit != null )
                {
                    showCreationSuccess( credit );
                }
                else
                {
                    showCreationError();
                    unlockForm();
                }
            }
            catch ( IOException e )
            {
                showCreationError();
                unlockForm();
            }
        }
        else
        {
            try
            {
                Debit debit = statementRepository.createDebit( (Debit) statement ).execute().body();
                if ( debit != null )
                {
                    showCreationSuccess( debit );
                }
                else
                {
                    showCreationError();
                    unlockForm();
                }
            }
            catch ( IOException e )
            {
                showCreationError();
                unlockForm();
            }
        }
    }

    @UiThread
    void showCreationSuccess( Statement statement )
    {
        String text = statement instanceof Credit ? "Despesa" : "Receita";
        Toast.makeText( getContext(), text + " criada com sucesso.", Toast.LENGTH_LONG ).show();
        getFragmentManager().popBackStackImmediate();
    }

    @UiThread
    void showCreationError()
    {
        Toast.makeText( getContext(), "Não foi possível salvar.", Toast.LENGTH_LONG ).show();
    }
}
