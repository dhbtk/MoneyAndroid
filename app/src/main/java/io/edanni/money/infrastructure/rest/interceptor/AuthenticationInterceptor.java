package io.edanni.money.infrastructure.rest.interceptor;

import io.edanni.money.infrastructure.security.CredentialsStore;
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

    private String accessToken;
    private String client;
    private String uid;

//    public ClientHttpResponse intercept( HttpRequest request, byte[] body, ClientHttpRequestExecution execution ) throws IOException
//    {
//        if ( hasAuthenticationData() )
//        {
//            HttpHeaders headers = request.getHeaders();
//            headers.set( "Authorization", "Bearer " + accessToken );
//            headers.set( "access-token", accessToken );
//            headers.set( "client", client );
//            headers.set( "uid", uid );
//        }
//        ClientHttpResponse response = execution.execute( request, body );
//        accessToken = response.getHeaders().getFirst( "access-token" );
//        client = response.getHeaders().getFirst( "client" );
//        uid = response.getHeaders().getFirst( "uid" );
//        return response;
//    }

    private boolean hasAuthenticationData()
    {
        return accessToken != null;
    }

    @Override
    public Response intercept( Chain chain ) throws IOException
    {
        Response response = null;
        if ( hasAuthenticationData() )
        {
            Request.Builder requestBuilder = chain.request().newBuilder()
                                                  .header( "Authorization", "Bearer " + accessToken )
                                                  .header( "access-token", accessToken )
                                                  .header( "client", client )
                                                  .header( "uid", uid ).method( chain.request().method(), chain.request().body() );
            response = chain.proceed( requestBuilder.build() );
        }
        else
        {
            response = chain.proceed( chain.request() );
        }
        accessToken = response.header( "access-token" );
        client = response.header( "client" );
        uid = response.header( "uid" );
        return response;
    }
}
