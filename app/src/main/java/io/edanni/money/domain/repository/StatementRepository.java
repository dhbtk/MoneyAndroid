package io.edanni.money.domain.repository;

import io.edanni.money.domain.entity.Credit;
import io.edanni.money.domain.entity.Debit;
import io.edanni.money.domain.entity.Statement;
import io.edanni.money.infrastructure.rest.Page;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by eduardo on 07/12/16.
 */
public interface StatementRepository
{
    @GET("/statements")
    Call<Page<Statement>> getStatements( @Query("period") String period, @Query("page") Integer page );

    @GET("/credits/{id}/edit")
    Call<Credit> getCredit( @Path("id") Integer id );

    @POST("/credits")
    Call<Credit> createCredit( @Body Credit credit );

    @POST("/debits")
    Call<Debit> createDebit( @Body Debit debit );
}
