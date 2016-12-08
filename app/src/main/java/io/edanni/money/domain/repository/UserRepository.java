package io.edanni.money.domain.repository;

import io.edanni.money.domain.entity.json.Login;
import io.edanni.money.domain.entity.json.UserWrapper;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Repositório de usuários
 */
public interface UserRepository
{
    @POST("/api/v1/auth/sign_in")
    Call<UserWrapper> signIn( @Body Login login );
}
