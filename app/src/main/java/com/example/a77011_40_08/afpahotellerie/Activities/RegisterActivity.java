package com.example.a77011_40_08.afpahotellerie.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.a77011_40_08.afpahotellerie.Interface.UserInterface;
import com.example.a77011_40_08.afpahotellerie.Models.Push;
import com.example.a77011_40_08.afpahotellerie.Models.User;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.Utils.Constants;
import com.example.a77011_40_08.afpahotellerie.Utils.Session;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText txtRegisterName, txtRegisterFirstName, txtRegisterLogin, txtRegisterpwd;
    ViewSwitcher vswRegister;
    Context context;
    Button btnRegisterValidate;
    Intent intent;
    String name;
    String firstname;
    String login;
    String password;
    UserInterface userInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_create);
        context = this;
        userInterface = RetrofitApi.getInterface();
        intent = new Intent();
        txtRegisterName = findViewById(R.id.txtRegister_name);
        txtRegisterFirstName = findViewById(R.id.txtRegister_firstName);
        txtRegisterLogin = findViewById(R.id.txtRegister_login);
        txtRegisterpwd = findViewById(R.id.txtRegister_pwd);
        btnRegisterValidate = findViewById(R.id.btnRegister_validate);
        vswRegister = findViewById(R.id.vswRegister);

        btnRegisterValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 name = txtRegisterName.getText().toString();
                 firstname = txtRegisterFirstName.getText().toString();
                login = txtRegisterLogin.getText().toString();
                 password = txtRegisterpwd.getText().toString();

                if(!name.isEmpty() && !firstname.isEmpty() && !login.isEmpty() && !password.isEmpty()){
                    vswRegister.showNext();
                  /* AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "addUser.php", new AsyncCallWS.OnCallBackAsyncTask() {
                        @Override
                        public void onResultCallBack(String result) {
                            onResultGet(result);
                        }
                    });*/
                   // asyncCallWS.addParam("name",name);
                   // asyncCallWS.addParam("firstname",firstname);
                   // asyncCallWS.addParam("login",login);
                   // asyncCallWS.addParam("password",password);
                  //  asyncCallWS.execute();
                }

            }
        });


    }
    private void callAccount(String login, String password) {
        try {


            Call<User> call = userInterface.account(name,firstname,login, password);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {

                        goToLogin();
                        setResult(Constants._CODE_LOGIN, intent);

                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("error", "");

                }
            });


        } catch (Exception e) {
            Log.e("Tag", e.toString());
        }
    }
    private void goToLogin() {
        Intent intent = null;
        intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();

    }

    public void onResultGet(String result) {
        Log.e("[DEBUG]",result);
        int idUser = 0;
        try{
            idUser = Integer.parseInt(result);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

        String msg;
        switch (idUser){
            case 0:
                Toast.makeText(context,"Erreur, veuillez recommencer.", Toast.LENGTH_LONG).show();
                vswRegister.showPrevious();
                break;
            case -1:
                Toast.makeText(context,"Login déjà existant.", Toast.LENGTH_LONG).show();
                vswRegister.showPrevious();
                break;
            default:
                Toast.makeText(context,"Votre compte a bien été créé.", Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }
}
