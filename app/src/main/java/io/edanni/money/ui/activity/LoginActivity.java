package io.edanni.money.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import io.edanni.money.R;
import io.edanni.money.domain.entity.User;
import io.edanni.money.domain.repository.UserRepository;
import io.edanni.money.infrastructure.security.CredentialsStore;
import org.androidannotations.annotations.*;
import org.androidannotations.rest.spring.annotations.RestService;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity
{
    @ViewById(R.id.btn_login)
    Button loginButton;
    @ViewById(R.id.input_email)
    EditText emailInput;
    @ViewById(R.id.input_password)
    EditText passwordInput;
    @Bean
    CredentialsStore store;
    @RestService
    UserRepository userRepository;

    @AfterViews
    void checkForLoginData()
    {
        if ( store != null && store.hasAuthenticationData() )
        {
            login();
        }
    }

    @Click(R.id.btn_login)
    void validateLogin()
    {
        if ( emailInput.getText().length() != 0 && passwordInput.getText().length() != 0 )
        {
            store.setEmail( emailInput.getText().toString() );
            store.setPassword( passwordInput.getText().toString() );
            login();
        }
        else
        {
            Toast.makeText( this, getString( R.string.please_input_email_and_password), Toast.LENGTH_SHORT ).show();
        }
    }

    private void login()
    {
        final LoginActivity activity = this;
        new AsyncTask<Void, Void, Boolean>()
        {
            @Override
            protected Boolean doInBackground( Void... voids )
            {
                User user = userRepository.getUser();
                return user != null;
            }

            @Override
            protected void onPostExecute( Boolean result )
            {
                if ( result )
                {
                    startActivity( new Intent( activity, MainActivity_.class ) );
                    finish();
                }
                else
                {
                    Toast.makeText( activity, getString( R.string.please_input_email_and_password ), Toast.LENGTH_SHORT ).show();
                }
            }
        }.execute();

    }
}
