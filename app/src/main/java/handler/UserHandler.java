package handler;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import layout.SignInFragment;
import model.User;
import model.UserAPI;
import model.Users;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static xdroid.toaster.Toaster.toast;
import static xdroid.toaster.Toaster.toastLong;
import android.content.SharedPreferences;

/**
 * Created by bigedo on 5/18/2016.
 */
public class UserHandler {

    Gson gson;
    Retrofit retrofit;
    UserAPI userApi;

    String message;

    public String getMessage() {
        return message;
    }

    public UserHandler() {
        this.gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://private-3dc94b-simplecrud1.apiary-mock.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.userApi = retrofit.create(UserAPI.class);
    }

    public void getAllUserData(final String email) {
        final String e = email;
        Call<Users>
        call = userApi.getUsers();

        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Response<Users> response, Retrofit retrofit) {
                String result = "masih kosong";
                int status = response.code();
                for (Users.UserItem user : response.body().getUsers()) {
                    /*(e.equals(user.getEmail())){
                        toast("found it");
                    }*/
                    result = "Id = " + String.valueOf(user.getId()) +
                            System.getProperty("line.separator") +
                            "Email = " + user.getEmail() +
                            System.getProperty("line.separator") +
                            "Password = " + user.getPassword() +
                            System.getProperty("line.separator") +
                            "Token Auth = " + user.getToken_auth() +
                            System.getProperty("line.separator") +
                            "Created at = " + user.getCreated_at() +
                            System.getProperty("line.separator") +
                            "Updated at = " + user.getUpdated_at() +
                            System.getProperty("line.separator") +
                            System.getProperty("line.separator");
                    toast(result);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                toast("error");
            }
        });
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int status;
    public int signIn(int id, final String password, final Context context) {
        Call<User> call;
        call = userApi.getUser(id);
        status = 0;

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                int stat = response.code();
                if(password.equals(response.body().getPassword())){
                    toast("login sukses");
                    SharedPreferences set_shared_preference = context.getSharedPreferences("authentication", context.MODE_PRIVATE);
                    SharedPreferences.Editor sp_editor = set_shared_preference.edit();
                    sp_editor.putString("token_authentication", response.body().getToken_auth());
                    sp_editor.commit();
                }
                else{
                    toast("login gagal");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                message = String.valueOf(t);
                toast(t.toString());
            }
        });
        return status;
    }

    public void register (String user, String email, String password){
        Call<User> call;
        call = userApi.saveUser(user, email, password);
        status = 0;

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                int stat = response.code();
                /*if(password.equals(response.body().getPassword())){
                    toast("login sukses");
                    /*SharedPreferences set_shared_preference = context.getSharedPreferences("authentication", context.MODE_PRIVATE);
                    SharedPreferences.Editor sp_editor = set_shared_preference.edit();
                    sp_editor.putString("token_authentication", response.body().getToken_auth());
                    sp_editor.commit();
                }
                else{
                    toast("login gagal");
                }*/
            }

            @Override
            public void onFailure(Throwable t) {
                message = String.valueOf(t);
                toast(t.toString());
            }
        });
    }
}
