package mini.projet.gestionAbsence.main.planificateur;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

import assistant.genuinecoder.gestionAbsence.R;
import mini.projet.gestionAbsence.main.AppAcceuil;


public class CreatePlanificateurActivity extends AppCompatActivity {

    Spinner classSelect, daySelect;
    ArrayAdapter adapterSpinner, days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planificateur_create);

        classSelect = (Spinner) findViewById(R.id.classSelector);
        daySelect = (Spinner) findViewById(R.id.daySelector);

        adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, AppAcceuil.divisions);
        assert classSelect != null;
        classSelect.setAdapter(adapterSpinner);

        ArrayList<String> weekdays = new ArrayList<>();
        weekdays.add("Lundi");
        weekdays.add("Mardi");
        weekdays.add("Mercredi");
        weekdays.add("Jeudi");
        weekdays.add("Vendredi");
        weekdays.add("Samedi");
        weekdays.add("Dimanche");
        days = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, weekdays);
        assert classSelect != null;
        daySelect.setAdapter(days);

        Button btn = (Button) findViewById(R.id.saveBUTTON_SCHEDULE);
        assert btn != null;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSchedule(v);
            }
        });

    }


    private void saveSchedule(View v) {
        String daySelected = daySelect.getSelectedItem().toString();
        String classSelected = classSelect.getSelectedItem().toString();
        EditText editText = (EditText) findViewById(R.id.subjectName);
        String subject = editText.getText().toString();
        if (subject.length() < 2) {
            Toast.makeText(getBaseContext(), "Entrez un nom de sujet valide", Toast.LENGTH_SHORT).show();
            return;
        }
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        int hour = timePicker.getCurrentHour();
        int min = timePicker.getCurrentMinute();

        String sql = "INSERT INTO SCHEDULE VALUES('" + classSelected + "'," +
                "'" + subject + "'," +
                "'" + hour + ":" + min + "'," +
                "'" + daySelected + "');";
        Log.d("Planificateur", sql);
        if (AppAcceuil.handler.execAction(sql)) {
            Toast.makeText(getBaseContext(), "Planification terminée", Toast.LENGTH_LONG).show();
            this.finish();
        } else
            Toast.makeText(getBaseContext(), "Échec de la planification", Toast.LENGTH_LONG).show();

    }
}
