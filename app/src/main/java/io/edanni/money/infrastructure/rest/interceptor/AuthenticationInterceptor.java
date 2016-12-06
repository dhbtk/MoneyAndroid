package io.edanni.money.infrastructure.rest.interceptor;

import io.edanni.money.infrastructure.security.CredentialsStore;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * Created by eduardo on 06/12/16.
 */
@EBean(scope = EBean.Scope.Singleton)
public class AuthenticationInterceptor implements ClientHttpRequestInterceptor
{
    /**
     *
     */
    @Bean
    CredentialsStore store;

    @Override
    public ClientHttpResponse intercept( HttpRequest request, byte[] body, ClientHttpRequestExecution execution ) throws IOException
    {
        if ( hasAuthenticationData() )
        {
            HttpHeaders headers = request.getHeaders();
            HttpAuthentication auth = new HttpBasicAuthentication( store.getEmail(), store.getPassword() );
            headers.setAuthorization( auth );
        }
        return execution.execute( request, body );
    }

    private boolean hasAuthenticationData()
    {
        return store != null && store.hasAuthenticationData();
    }
}
