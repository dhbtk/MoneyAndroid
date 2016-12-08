package io.edanni.money.domain.repository;

import io.edanni.money.domain.entity.Credit;
import io.edanni.money.domain.entity.Statement;
import io.edanni.money.infrastructure.rest.Page;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by eduardo on 07/12/16.
 */
public interface StatementRepository
{
    @GET("/statements")
    Call<Page<Statement>> getStatements( @Query("period") String period, @Query("page") Integer page );

    @GET("/credits/{id}/edit")
    Call<Credit> getCredit( @Path("id") Integer id );
}
