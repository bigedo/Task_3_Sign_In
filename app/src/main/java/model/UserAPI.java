package model;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import model.User;

/**
 * Created by bigedo on 5/18/2016.
 */
public interface UserAPI {
    @GET("/users")
    Call<Users> getUsers();

    @GET("/users/{id}")
    Call<User> getUser(@Path("id") int user_id);

    @PUT("/users")
    Call<User> updateUser(@Path("id") String user_id, @Body User user);

    @POST("/users")
    Call<User> saveUser(@Body String user, String email, String password);

    @DELETE("/users")
    Call<User> deleteUser(@Path("id") String user_id);
}
