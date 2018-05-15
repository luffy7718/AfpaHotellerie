package com.example.a77011_40_08.afpahotellerie.Interface;



import com.example.a77011_40_08.afpahotellerie.Models.Push;
import com.example.a77011_40_08.afpahotellerie.Models.User;
import com.example.a77011_40_08.afpahotellerie.Models.Users;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserInterface {


    @FormUrlEncoded
    @POST("/afpa_hotellerie/login.php")
    Call<Push> login(
            @Field("login") String login,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("/afpa_hotellerie/addUser.php")
    Call<User> account(
            @Field("name") String name,
            @Field("firstname") String firstname,
               @Field("login") String login,
             @Field("password") String password);
}
