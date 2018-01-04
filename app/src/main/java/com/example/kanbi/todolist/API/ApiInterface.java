package com.example.kanbi.todolist.API;

import com.example.kanbi.todolist.ToDoModel;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by kanbi on 30/12/2017.
 */

public interface ApiInterface {
///id?sort=desc
    @GET("2a00ea83/notes?sort_by=id.desc")
    Call<List<ToDoModel>> getResults();

    @POST("2a00ea83/notes")
    Call<ToDoModel> createTodo(@Body ToDoModel todo);

    @DELETE("2a00ea83/notes/{id}")
    Call<ToDoModel> deleteTask(@Path("id") long id);

    @PUT("2a00ea83/notes/{id}")
    Call<ToDoModel> updateTask(@Path("id") long id, @Body ToDoModel todo);

}
