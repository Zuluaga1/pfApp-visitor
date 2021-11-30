package com.example.visitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
        setContentView(R.layout.activity_login);
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
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";

                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;
                            getPosts(username,password);
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void getPosts(String username, String password){
        Boolean app;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://3.141.193.87:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build(); //creando instancia retrofit
        ProductoApi productoApi = retrofit.create(ProductoApi.class);
        Call<List<User>> call = productoApi.getUsersPost(new User(username,password));
        call.enqueue(new Callback<List<User>>(){
            String placa;
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
               List<User> postsList = response.body();
               for(User user: postsList){
                   String content = "";
                   content += "pro_nombre:"+ user.getPro_nombre() + "\n";
                   content += "pro_placa:"+ user.getPro_placa() + "\n";
                   placa = user.getPro_placa();
                   Log.e("Conexión a la API: ",content);



               }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("placa",placa);
                startActivity(intent);
                finish();


            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("error: ","Conexión a la API fallida");
                Toast.makeText(getApplicationContext(),"incorrecto", Toast.LENGTH_SHORT).show();
            }
        });
    }
}