package mini.projet.gestionAbsence.main.planificateur;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


import assistant.genuinecoder.gestionAbsence.R;
import mini.projet.gestionAbsence.main.AppAcceuil;


public class Planificateur extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    ListView listView;
    ArrayAdapter adapter;
    ArrayList<String> subs;
    ArrayList<String> subx;
    ArrayList<String> times;
    Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planificateur);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_sch);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchIntent = new Intent(getBaseContext(), CreatePlanificateurActivity.class);
                startActivity(launchIntent);
            }
        });

        subs = new ArrayList<>();
        times = new ArrayList<>();
        subx = new ArrayList<>();
        listView = (ListView) findViewById(R.id.schedulerList);
        loadSchedules();
        listView.setOnItemLongClickListener(this);
    }

    private void loadSchedules() {
        subs.clear();
        times.clear();
        String qu = "SELECT * FROM SCHEDULE ORDER BY subject";
        Cursor cursor = AppAcceuil.handler.execQuery(qu);
        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(getBaseContext(), "Aucun horaire disponible", Toast.LENGTH_LONG).show();
        } else {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                subx.add(cursor.getString(1));
                subs.add(cursor.getString(1) + "\n" +"pour " + cursor.getString(0) + "\nà " + cursor.getString(2) + " : " + cursor.getString(3));
                times.add(cursor.getString(2));
                cursor.moveToNext();
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, subs);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle("Supprimer le calendrier?");
        alert.setMessage("Voulez-vous supprimer ce programme?");

        alert.setPositiveButton("oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String qu = "DELETE FROM SCHEDULE WHERE subject = '" + subx.get(position) + "' AND timex = '" + times.get(position) + "'";
                if (AppAcceuil.handler.execAction(qu)) {
                    Toast.makeText(getBaseContext(), "supprimer", Toast.LENGTH_LONG).show();
                    loadSchedules();
                } else {
                    Toast.makeText(getBaseContext(), "Échoué", Toast.LENGTH_LONG).show();
                    loadSchedules();
                }
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
        return true;
    }

    public void refresh(MenuItem item) {
        loadSchedules();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.planificateur_menu, menu);
        return true;
    }
}
