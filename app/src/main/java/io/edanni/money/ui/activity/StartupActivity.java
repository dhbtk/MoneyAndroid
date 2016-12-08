package io.edanni.money.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import io.edanni.money.R;
import io.edanni.money.domain.entity.json.Login;
import io.edanni.money.domain.entity.json.UserWrapper;
import io.edanni.money.domain.repository.UserRepository;
import io.edanni.money.infrastructure.rest.RetrofitFactory;
import io.edanni.money.infrastructure.security.CredentialsStore;
import org.androidannotations.annotations.*;

import java.io.IOException;

@EActivity(R.layout.activity_startup)
public class StartupActivity extends AppCompatActivity
{
    @Bean
    CredentialsStore store;
    @Bean
    RetrofitFactory retrofitFactory;
    UserRepository userRepository;

    @AfterViews
    void checkLogin()
    {
        userRepository = retrofitFactory.createService( UserRepository.class );
        if ( store != null && store.hasAuthenticationData() )
        {
            login();
        }
        else
        {
            startActivity( new Intent( this, LoginActivity_.class ) );
            finish();
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
            UserWrapper response = userRepository.signIn(login).execute().body();
            if ( response != null )
            {
                switchToMain();
            }
            else
            {
                showLoginError();
            }
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
        startActivity( new Intent( this, LoginActivity_.class ) );
        finish();
    }
}
