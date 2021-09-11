package com.example.visitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visitor.interfaces.ProductoApi;
import com.example.visitor.models.User;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUP extends AppCompatActivity {
    TextInputEditText textInputEditTextFullname, textInputEditTextUsername, textInputEditTextPassword, textInputEditTextPlaca;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;
    private Spinner spinner1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_u_p);
        textInputEditTextFullname = findViewById(R.id.fullname);
        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextPassword = findViewById(R.id.password);
        textInputEditTextPlaca = findViewById(R.id.placa);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.loginText);
        progressBar = findViewById(R.id.progress);
        spinner1 = (Spinner)findViewById(R.id.spinner);
        String [] opciones = {"visitante", "trabajador", "contratista"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, opciones);
        spinner1.setAdapter(adapter);

        textViewLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();
            }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String fullname, username, password, placa;
                fullname = String.valueOf(textInputEditTextFullname.getText());
                username = String.valueOf(textInputEditTextUsername.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
                placa = String.valueOf(textInputEditTextPlaca.getText());

                if(!fullname.equals("") && !username.equals("") && !password.equals("") && !placa.equals("")){
                progressBar.setVisibility(View.VISIBLE);
                //Start ProgressBar first (Set visibility VISIBLE)
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[4];
                        field[0] = "fullname";
                        field[1] = "username";
                        field[2] = "password";
                        field[3] = "placa";

                        //Creating array for data
                        String[] data = new String[4];
                        data[0] = fullname;
                        data[1] = username;
                        data[2] = password;
                        data[3] = placa;
                        String rol = spinner1.getSelectedItem().toString();
                        getPosts(fullname, username, password, placa,rol );

                        /*
                        PutData putData = new PutData("http://192.168.1.85/LoginRegister/signup.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                progressBar.setVisibility(View.GONE);

                                String result = putData.getResult();
                                if(result.equals("Sign Up Success")){
                                    Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), login.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();

                                }
                                //End ProgressBar (Set visibility to GONE)
                                //Log.i("PutData", result);
                            }
                        }

                         */
                        //End Write and Read data with URL
                    }
                });
            }
                else {
                    Toast.makeText(getApplicationContext(),"Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        

    }
    private void getPosts(String username, String password, String fullname, String placa, String rol){
        Boolean app;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.84:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build(); //creando instancia retrofit
        ProductoApi productoApi = retrofit.create(ProductoApi.class);
        Call<List<User>> call = productoApi.createUser(new User(username,password,fullname,placa,rol));
        call.enqueue(new Callback<List<User>>(){
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> postsList = response.body();
                for(User user: postsList){
                    String content = "";
                    content += "pro_nombre:"+ user.getPro_nombre() + "\n";
                    content += "pro_placa:"+ user.getPro_placa() + "\n";
                    Log.e("APi encontrada: ",content);
                }
                Intent intent = new Intent(getApplicationContext(), login.class);
                //intent.putExtra("",username);
                startActivity(intent);
                finish();

                //Log.e("Usuario:",response.body());
                //Log.e("User:", String.valueOf(response));

            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("hola: ","no API");
                Toast.makeText(getApplicationContext(),"incorrecto", Toast.LENGTH_SHORT).show();
            }
        });
    }
}