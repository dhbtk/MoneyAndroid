package io.edanni.money.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import android.widget.Toast;
import fr.ganfra.materialspinner.MaterialSpinner;
import io.edanni.money.R;
import io.edanni.money.domain.entity.*;
import io.edanni.money.domain.repository.AccountRepository;
import io.edanni.money.domain.repository.StatementRepository;
import io.edanni.money.domain.repository.TagRepository;
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
public class NewCreditFragment extends Fragment
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
    @Bean
    RetrofitFactory retrofitFactory;

    TagRepository tagRepository;
    AccountRepository accountRepository;
    StatementRepository statementRepository;

    protected Statement statement = new Statement();

    /*-------------------------------------------------------------------
     *                           INITIALIZERS
     *-------------------------------------------------------------------*/


    public static NewCreditFragment newInstance( Statement statement )
    {
        NewCreditFragment fragment = new NewCreditFragment_();
        Bundle arguments = new Bundle();
        arguments.putSerializable( "statement", statement );
        fragment.setArguments( arguments );
        return fragment;
    }

    @AfterViews
    void getSpinnerData()
    {
        Statement receivedStatement = (Statement) getArguments().getSerializable( "statement" );
        if ( receivedStatement != null )
        {
            statement = receivedStatement;
        }
        tagRepository = retrofitFactory.createService( TagRepository.class );
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

    @Background
    void loadTags()
    {
        try
        {
            List<Tag> tags = tagRepository.getTags().execute().body();
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
    void setTags( List<Tag> tags )
    {
        List<NamedEntity> entities = new ArrayList<>();
        for ( Tag tag : tags )
        {
            entities.add( tag );
        }
        category.setAdapter( new NamedEntityListAdapter( getContext(), entities ) );
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
        if ( category.getSelectedItem() != null )
        {
            statement.name = ((Tag) category.getSelectedItem()).name;
            statement.tagId = ((Tag) category.getSelectedItem()).id;
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
                }
            }
            catch ( IOException e )
            {
                showCreationError();
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
                }
            }
            catch ( IOException e )
            {
                showCreationError();
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
