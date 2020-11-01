package mini.projet.gestionAbsence.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.GridView;

import java.util.ArrayList;

import assistant.genuinecoder.gestionAbsence.R;
import mini.projet.gestionAbsence.main.components.GridAdapter;
import mini.projet.gestionAbsence.main.database.DatabaseHandler;

public class AppAcceuil extends AppCompatActivity {

    public static ArrayList<String> divisions;
    public static DatabaseHandler handler;
    public static Activity activity;
    ArrayList<String> basicFields;
    GridAdapter adapter;
    GridView gridView;

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            super.onCreateOptionsMenu(menu);
            MenuInflater inflater = getMenuInflater();
            return true;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.base_layout);
            basicFields = new ArrayList<>();
            handler = new DatabaseHandler(this);
            activity = this;

            getSupportActionBar().show();
            divisions = new ArrayList<>();
            divisions.add("S1 Master(SIM)");
            divisions.add("S2 Master(SIM)");
            divisions.add("S3 Master(SIM)");
            divisions.add("S4 Master(SIM)");
            gridView = (GridView) findViewById(R.id.grid);
            basicFields.add("Présence");
            basicFields.add("Planificateur");
            basicFields.add("Profil");

            adapter = new GridAdapter(this, basicFields);
            gridView.setAdapter(adapter);
        }


    }

