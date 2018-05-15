package com.example.a77011_40_08.afpahotellerie.Activities;



import com.example.a77011_40_08.afpahotellerie.Interface.UserInterface;
import com.example.a77011_40_08.afpahotellerie.Utils.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApi {

    // http://square.github.io/retrofit/

    private static UserInterface userInterface;


    public static UserInterface getInterface() {
        if (userInterface == null) {
            synchronized (UserInterface.class) {
                getInstance();
            }
        }
        return userInterface;
    }

    private static void getInstance() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants._URL_WEBSERVICE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        userInterface = retrofit.create(UserInterface.class);


    }
}
