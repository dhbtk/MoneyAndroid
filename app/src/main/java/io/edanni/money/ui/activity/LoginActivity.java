package io.edanni.money.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import io.edanni.money.R;
import io.edanni.money.domain.entity.json.Login;
import io.edanni.money.domain.entity.json.UserWrapper;
import io.edanni.money.domain.repository.UserRepository;
import io.edanni.money.infrastructure.rest.RetrofitFactory;
import io.edanni.money.infrastructure.security.CredentialsStore;
import org.androidannotations.annotations.*;
import retrofit2.Response;

import java.io.IOException;

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
    @Bean
    RetrofitFactory retrofitFactory;
    UserRepository userRepository;

    @AfterViews
    void checkForLoginData()
    {
        userRepository = retrofitFactory.createService( UserRepository.class );
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

    @Background
    void login()
    {
        Login login = new Login();
        login.email = store.getEmail();
        login.password = store.getPassword();
        try
        {
            Response<UserWrapper> response = userRepository.signIn(login).execute();
            switchToMain();
        }
        catch ( IOException e )
        {
            showLoginError();
        }
    }

    @UiThread
    void switchToMain()
    {
        startActivity( new Intent( this, MainActivity_.class ) );
        finish();
    }

    @UiThread
    void showLoginError()
    {
        Toast.makeText( this, getString( R.string.please_input_email_and_password ), Toast.LENGTH_SHORT ).show();
    }
}
