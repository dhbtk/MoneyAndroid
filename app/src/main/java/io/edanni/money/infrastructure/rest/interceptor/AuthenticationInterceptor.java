package io.edanni.money.infrastructure.rest.interceptor;

import io.edanni.money.infrastructure.security.CredentialsStore;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.IOException;

/**
 * Created by eduardo on 06/12/16.
 */
@EBean(scope = EBean.Scope.Singleton)
public class AuthenticationInterceptor implements Interceptor
{
    /**
     *
     */
    @Bean
    CredentialsStore store;

    @Override
    public Response intercept( Chain chain ) throws IOException
    {
        Response response;
        if ( hasAuthenticationData() )
        {
            Request.Builder requestBuilder = chain.request().newBuilder()
                                                  .header( "Authorization", Credentials.basic( store.getEmail(), store.getPassword() ) )
                                                  .method( chain.request().method(), chain.request().body() );
            response = chain.proceed( requestBuilder.build() );
            // if we have authentication data and we got a 401, it means our token has expired.
            if ( response.code() == 401 )
            {
                throw new IOException( "Login invalid" ); // FIXME
            }
        }
        else
        {
            response = chain.proceed( chain.request() );
        }
        return response;
    }

    private boolean hasAuthenticationData()
    {
        return store.hasAuthenticationData();
    }
}
