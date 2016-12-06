package io.edanni.money.infrastructure.security;

import android.content.Context;
import android.content.SharedPreferences;
import org.androidannotations.annotations.EBean;

/**
 * Created by eduardo on 06/12/16.
 */
@EBean(scope = EBean.Scope.Singleton)
public class CredentialsStore
{
    private final SharedPreferences preferences;
    /**
     *
     */
    private static final String PREFERENCES = "io.edanni.money.preferences";

    public CredentialsStore( Context context )
    {
        this.preferences = context.getSharedPreferences( PREFERENCES, Context.MODE_PRIVATE );
    }

    public boolean hasAuthenticationData()
    {
        return getEmail() != null && getPassword() != null;
    }

    public void setEmail( String email )
    {
        this.preferences.edit().putString( "email", email ).apply();
    }

    public void setPassword( String password )
    {
        this.preferences.edit().putString( "password", password ).apply();
    }

    public String getEmail()
    {
        return this.preferences.getString( "email", null );
    }

    public String getPassword()
    {
        return this.preferences.getString( "password", null );
    }
}
