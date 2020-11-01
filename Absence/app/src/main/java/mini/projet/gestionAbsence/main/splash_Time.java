package mini.projet.gestionAbsence.main;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import assistant.genuinecoder.gestionAbsence.R;

public class splash_Time extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_time);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(splash_Time.this, AppAcceuil.class);
                splash_Time.this.startActivity(mainIntent);
                splash_Time.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}