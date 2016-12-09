package io.edanni.money.domain.repository;

import io.edanni.money.domain.entity.Tag;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

/**
 * Created by eduardo on 09/12/16.
 */
public interface TagRepository
{
    @GET("/tags")
    Call<List<Tag>> getTags();
}
