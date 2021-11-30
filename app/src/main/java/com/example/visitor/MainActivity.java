package com.example.visitor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button btgenerate;
    ImageView image;
    private int mInterval = 5000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    Bundle datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btgenerate = findViewById(R.id.bt_qr);
        image = findViewById(R.id.ima_qr);
        datos = getIntent().getExtras();
        String placa = datos.getString("placa");

        btgenerate.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               Date d = new Date();

               String text= d+","+placa;
               MultiFormatWriter writer = new MultiFormatWriter();

               try {
                   BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE,350,350);
                   BarcodeEncoder encoder = new BarcodeEncoder();
                   Bitmap bitmap = encoder.createBitmap(matrix);
                   image.setImageBitmap(bitmap);
                   InputMethodManager manager = (InputMethodManager) getSystemService(
                           Context.INPUT_METHOD_SERVICE
                   );

               } catch (WriterException e) {
                   e.printStackTrace();
               }
               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       btgenerate.performClick();
                   }
               }, 5000);
           }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea salir de la Aplicación?")
                    .setPositiveButton("si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent); //para mantenerla activada
                            //finish();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }
}