package com.komputerkit.kasirlaundry;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.komputerkit.kasirlaundry.Utilitas.Config;
import com.komputerkit.kasirlaundry.Utilitas.Database;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ActivitySplash extends AppCompatActivity {

    boolean isFirst = true ;
    Database db ;
    Utilitas utilitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        db = new Database(this) ;
        utilitas = new Utilitas(this) ;

        setSplash();
    }

    private void setSplash() {
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                isFirst = utilitas.getSession("isFirst",true) ;
                if (isFirst){
                    utilitas.setSession("isFirst",false);
                    createTable();
                    startActivity(new Intent(ActivitySplash.this,ActivityIntro.class));
                } else {
                    Intent i = new Intent(ActivitySplash.this, ActivityUtama.class);
                    startActivity(i);
                }

                // close this activity
                finish();
            }
        }, 5000);
    }

    public void createTable(){

        InputStream is = null;
        OutputStream os = null ;
        try {
            is = getAssets().open(Config.getAppName());
            os = new FileOutputStream(getDatabasePath(Config.getAppName()));

            byte[] buffer = new byte[1024];
            while (is.read(buffer) > 0) {
                os.write(buffer);
            }

            os.flush();
            os.close();
            is.close();
        } catch (IOException e) {
            utilitas.toast("Failed Set Application");
        }

    }
}
