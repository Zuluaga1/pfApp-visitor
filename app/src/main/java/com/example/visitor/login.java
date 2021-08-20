package com.example.visitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visitor.interfaces.ProductoApi;
import com.example.visitor.models.User;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login extends AppCompatActivity {
    TextInputEditText  textInputEditTextUsername, textInputEditTextPassword;
    Button buttonLogin;
    TextView textViewSignUp;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
          //      WindowManager.LayoutParams.FLAG_SECURE); bloquear ss
        setContentView(R.layout.activity_login);
         final String esteban;
        new Peticion().execute();

        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progress);
        textViewSignUp = findViewById(R.id.signUpText);

        textViewSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), SignUP.class);
                startActivity(intent);
                finish();
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String  username, password;
                username = String.valueOf(textInputEditTextUsername.getText());
                password = String.valueOf(textInputEditTextPassword.getText());

                if(!username.equals("") && !password.equals("") ){
                    //progressBar.setVisibility(View.VISIBLE);
                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";

                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;


                            //PutData putData = new PutData("http://192.168.1.85/LoginRegister/login.php", "POST", field, data);
                            //PutData putData = new PutData("http://192.168.1.85:5000/crear", "GET",field,data);
                            //if (putData.startPut()) {

                               // if (putData.onComplete()) {
                                    //progressBar.setVisibility(View.GONE);
                                   // String result = putData.getResult();
                                    //if(result.equals("Login Success")){
                                  //      Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.putExtra("usuario",username);
                                        startActivity(intent);
                                        finish();
                                    //}
                                    //else{
                                      //  Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
                                    //}
                                    //End ProgressBar (Set visibility to GONE)
                                    //Log.i("PutData", result);
                                }
                            //}
                            //End Write and Read data with URL
                       // }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public class Peticion extends AsyncTask<Void, Void, Call<List<User>>> {
        //String  username, password;


        @Override
        protected Call<List<User>> doInBackground(Void... voids) {

            final  String url = "http://192.168.1.85:5000/";

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            ProductoApi service = retrofit.create(ProductoApi.class);
            Call<User> call = service.findUserPost("a");
            call.enqueue(new Callback<User>(){
                @Override
                public void onResponse(Call<User>call,Response<User>response){
                    log.e("User:", response.body)
                }
            })
            /*
            Call<List<User>> response = service.getUsersPost();

            try {
                for (User user : response.execute().body())

                    Log.e("Respuesta:   ",user.getPro_nombre()+ " "+user.getPro_placa());
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            return null;
        }
    }
    /*
    private void find(String codigo){
        Retrofit retrofit =new Retrofit.Builder().baseUrl("http://192.168.1.85:5000/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ProductoApi productoApi = retrofit.create(ProductoApi.class);
        Call<User> call = productoApi.find(codigo);
        call.enqueue(new Callback<User>(){
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try{
                    if(response.isSuccessful()){
                        User u = response.body();


                    }

                }catch(Exception ex){
                    Toast.makeText(getApplicationContext(),ex.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }

        });
    }*/
}