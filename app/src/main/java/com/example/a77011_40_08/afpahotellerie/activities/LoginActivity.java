package com.example.a77011_40_08.afpahotellerie.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.a77011_40_08.afpahotellerie.interface_retrofit.SWInterface;
import com.example.a77011_40_08.afpahotellerie.models.Push;
import com.example.a77011_40_08.afpahotellerie.models.User;
import com.example.a77011_40_08.afpahotellerie.R;
import com.example.a77011_40_08.afpahotellerie.utils.Constants;
import com.example.a77011_40_08.afpahotellerie.utils.Functions;
import com.example.a77011_40_08.afpahotellerie.utils.Session;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Intent intent;
    ViewSwitcher vswLogin;
    TextView lblLoginCurrentAction;
    TextInputEditText txtLoginName;
    TextInputEditText txtLoginPwd;
    TextView lblLoginForgotten;
    TextView lblLoginRegister;
    Context context;
    int requestType = 0;
    SWInterface swInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        swInterface = RetrofitApi.getInterface();

        intent = new Intent();


        txtLoginName = findViewById(R.id.txtLogin_name);
        txtLoginPwd = findViewById(R.id.txtLogin_pwd);

        lblLoginForgotten = findViewById(R.id.lblLogin_forgotten);
        lblLoginForgotten.setPaintFlags(lblLoginForgotten.getPaintFlags() | Paint
                .UNDERLINE_TEXT_FLAG);

        Button btnLoginValidate = findViewById(R.id.btnLogin_validate);
        Button btnLoginBack = findViewById(R.id.btnLogin_back);
        vswLogin = findViewById(R.id.vswLogin);
        lblLoginCurrentAction = findViewById(R.id.lblLogin_currentAction);

        lblLoginForgotten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPasswordForgottenDialog();
            }
        });

        btnLoginValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = txtLoginName.getText().toString();
                String password = txtLoginPwd.getText().toString();

                if (!login.isEmpty() && !password.isEmpty()) {
                    vswLogin.showNext();
                    lblLoginCurrentAction.setText("Connexion ...");
                    callLogin(login, password);
                }
            }
        });

        btnLoginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();//finishing activity
            }
        });
    }

    private void showPasswordForgottenDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Mot de passe oublié");

        LayoutInflater inflater = LayoutInflater.from(context);
        View container = inflater.inflate(R.layout.dialog_forgotten_password, null);

        final TextView txtReminder = container.findViewById(R.id.txtReminder);

        alertDialog.setView(container);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Valider", new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                vswLogin.showNext();
                String login = txtReminder.getText().toString();
                if (login.isEmpty()) {
                    Toast.makeText(context, "Login manquant.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Fermer", new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void callLogin(String login, String password) {
        Log.e(Constants._TAG_LOG,"Login ...");
        Call<Push> call = swInterface.login(Functions.getAuth(), login, password,Functions.getMyIdDevice(context));

        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {
                if (response.isSuccessful()) {
                    Log.e(Constants._TAG_LOG, response.toString());
                    Push push = response.body();
                    if(push.getStatus()==1) {
                        Gson gson = new Gson();
                        User user = gson.fromJson(push.getData(), User.class);
                        Session.setMyUser(user);
                        Session.setConnectionChecked(true);
                        goToHome();
                        setResult(Constants._CODE_LOGIN, intent);

                        Log.e(Constants._TAG_LOG, "User " + user.getFirstname());

                        //textView.setText(push.getName());
                        Log.e("TAG ", "[status:" + push.getStatus() + ", type:" + push.getType()
                                + ", data:" + push.getData()
                                + "]");
                    } else
                    {
                        Log.e(Constants._TAG_LOG,"push.getdata = "+push.getData());

                    }
                    //Toast.makeText(context, "name= " + push.getName(), Toast.LENGTH_LONG)
                    // .show();
                } else {
                    //todo:gérer les code erreur de retour
                    Log.e(Constants._TAG_LOG, response.toString());
                }

            }

            @Override
            public void onFailure(Call<Push> call, Throwable t) {
                Log.e("error", "");

            }
        });
    }

    private void goToHome() {
        Intent intent = null;
        intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();

    }


}

