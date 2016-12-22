package io.edanni.money.domain.repository;

import io.edanni.money.domain.entity.Category;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

/**
 * Created by eduardo on 09/12/16.
 */
public interface CategoryRepository
{
    @GET("/categories")
    Call<List<Category>> getCategories();
}
