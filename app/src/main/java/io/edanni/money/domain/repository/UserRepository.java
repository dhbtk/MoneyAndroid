package io.edanni.money.domain.repository;

import io.edanni.money.domain.entity.User;
import io.edanni.money.infrastructure.rest.interceptor.AuthenticationInterceptor;
import org.androidannotations.rest.spring.annotations.Accept;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Repositório de usuários
 */
@Rest(rootUrl = "http://192.168.20.181:3000", interceptors = AuthenticationInterceptor.class, converters = MappingJackson2HttpMessageConverter.class)
@Accept(MediaType.APPLICATION_JSON)
public interface UserRepository
{
    @Get("/")
    User getUser();
}
