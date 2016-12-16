package io.edanni.money.infrastructure.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.edanni.money.infrastructure.rest.interceptor.AuthenticationInterceptor;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

/**
 * Created by eduardo on 08/12/16.
 */
@EBean(scope = EBean.Scope.Singleton)
public class RetrofitFactory
{
//    public static final String BASE_URL = "https://money.edanni.io";
    public static final String BASE_URL = "http://192.168.20.181:3000";

    @Bean
    AuthenticationInterceptor authenticationInterceptor;

    private ObjectMapper getObjectMapper()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
        objectMapper.setPropertyNamingStrategy( PropertyNamingStrategy.SNAKE_CASE );
        return objectMapper;
    }

    private OkHttpClient getClient()
    {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor( authenticationInterceptor );
        httpClient.addInterceptor( new Interceptor()
        {
            @Override
            public Response intercept( Chain chain ) throws IOException
            {
                return chain.proceed( chain.request().newBuilder().addHeader( "Accept", "application/json" ).build() );
            }
        } );

        return httpClient.build();
    }

    /**
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T createService( Class<T> clazz )
    {
        Retrofit retrofit =
            new Retrofit.Builder().baseUrl( BASE_URL )
                                  .addConverterFactory( JacksonConverterFactory.create( getObjectMapper() ) )
                                  .client( getClient() )
                                  .build();
        return retrofit.create( clazz );
    }
}
