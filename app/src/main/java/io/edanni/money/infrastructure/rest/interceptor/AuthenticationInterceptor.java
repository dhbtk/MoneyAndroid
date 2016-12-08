package io.edanni.money.infrastructure.rest.interceptor;

import io.edanni.money.domain.entity.json.Login;
import io.edanni.money.domain.repository.UserRepository;
import io.edanni.money.infrastructure.rest.RetrofitFactory;
import io.edanni.money.infrastructure.security.CredentialsStore;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.Calendar;

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

    private String accessToken;
    private String client;
    private Calendar expiry = null;
    private String uid;

    @Override
    public Response intercept( Chain chain ) throws IOException
    {
        if ( expiry != null && Calendar.getInstance().compareTo( expiry ) > 0 )
        {
            refreshToken();
        }
        Response response;
        if ( hasAuthenticationData() )
        {
            Request.Builder requestBuilder = chain.request().newBuilder()
                                                  .header( "Authorization", "Bearer " + accessToken )
                                                  .header( "access-token", accessToken )
                                                  .header( "client", client )
                                                  .header( "uid", uid ).method( chain.request().method(), chain.request().body() );
            response = chain.proceed( requestBuilder.build() );
            // if we have authentication data and we got a 401, it means our token has expired.
            if ( response.code() == 401 )
            {
                throw new IOException( "Token expired" ); // FIXME
            }
        }
        else
        {
            response = chain.proceed( chain.request() );
        }
        accessToken = response.header( "access-token" );
        client = response.header( "client" );
        uid = response.header( "uid" );
        String expiry = response.header( "expiry" );
        if ( expiry != null )
        {
            this.expiry = Calendar.getInstance();
            this.expiry.setTimeInMillis( Long.valueOf( expiry ) * 1000 );
        }
        return response;
    }

    private boolean hasAuthenticationData()
    {
        return accessToken != null;
    }

    private void refreshToken() throws IOException
    {
        final AuthenticationInterceptor ref = this;
        UserRepository repo =
            new Retrofit.Builder().baseUrl( RetrofitFactory.BASE_URL ).addConverterFactory( JacksonConverterFactory.create() )
                                  .client( new OkHttpClient.Builder().addInterceptor( new Interceptor()
                                  {
                                      @Override
                                      public Response intercept( Chain chain ) throws IOException
                                      {
                                          Response response = chain.proceed( chain.request().newBuilder().addHeader( "Accept", "application/json" ).build() );

                                          accessToken = response.header( "access-token" );
                                          client = response.header( "client" );
                                          uid = response.header( "uid" );
                                          String expiry = response.header( "expiry" );
                                          if ( expiry != null )
                                          {
                                              ref.expiry = Calendar.getInstance();
                                              ref.expiry.setTimeInMillis( Long.valueOf( expiry ) * 1000 );
                                          }
                                          return response;
                                      }
                                  } ).build() ).build()
                                  .create( UserRepository.class );

        repo.signIn( new Login( store.getEmail(), store.getPassword() ) ).execute();
    }
}
