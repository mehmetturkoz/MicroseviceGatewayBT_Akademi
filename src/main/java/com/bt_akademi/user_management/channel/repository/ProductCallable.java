package com.bt_akademi.user_management.channel.repository;

import com.google.gson.JsonElement;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.concurrent.Callable;

// **** 15 ->ProductService

public interface ProductCallable // Product ile ilgili olan REST API nin çağırımı için
{

    @GET("api/product")
    Call<List<JsonElement>> getAll();

    @DELETE("api/product/delete/{productID}")
    Call<Void> deleteByID(@Path("productID") Integer productID);

    @POST("api/product")
    Call<JsonElement> save(@Body JsonElement product);
}
