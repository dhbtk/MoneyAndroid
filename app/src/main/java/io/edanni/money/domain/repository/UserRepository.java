package io.edanni.money.domain.repository;

import io.edanni.money.domain.entity.User;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Repositório de usuários
 */
public interface UserRepository
{
    @GET("/users/edit")
    Call<User> signIn();
}
