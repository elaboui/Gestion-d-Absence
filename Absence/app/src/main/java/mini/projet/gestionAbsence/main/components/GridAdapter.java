package mini.projet.gestionAbsence.main.components;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import assistant.genuinecoder.gestionAbsence.R;
import mini.projet.gestionAbsence.main.AppAcceuil;
import mini.projet.gestionAbsence.main.presence.PresenceActivity;
import mini.projet.gestionAbsence.main.profile.ProfileActivity;
import mini.projet.gestionAbsence.main.planificateur.Planificateur;

public class GridAdapter extends BaseAdapter {
    public static Activity activity;
    ArrayList names;

    public GridAdapter(Activity activity, ArrayList names) {
        this.activity = activity;
        this.names = names;
    }


    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(activity);
            v = vi.inflate(R.layout.grid_layout, null);
        }
        TextView textView = (TextView) v.findViewById(R.id.namePlacer);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageHolder);
        if (names.get(position).toString().equals("Présence")) {
            imageView.setImageResource(R.drawable.ic_attendance);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = activity.getFragmentManager();
                    createRequest request = new createRequest();
                    request.show(fm, "Select");
                }
            });

        } else if (names.get(position).toString().equals("Planificateur")) {
            imageView.setImageResource(R.drawable.ic_schedule);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent launchinIntent = new Intent(activity, Planificateur.class);
                    activity.startActivity(launchinIntent);
                }
            });

        } else if (names.get(position).toString().equals("Profil")) {
            imageView.setImageResource(R.drawable.ic_profil);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent launchinIntent = new Intent(activity, ProfileActivity.class);
                    activity.startActivity(launchinIntent);
                }
            });

        }

        textView.setText(names.get(position).toString());
        return v;
    }

    public static class createRequest extends DialogFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View v = inflater.inflate(R.layout.pick_period, null);
            final DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);
            final EditText hour = (EditText) v.findViewById(R.id.periodID);
            final Spinner spn = (Spinner) v.findViewById(R.id.spinnerSubject);

            String qu = "SELECT DISTINCT sub FROM NOTES";
            ArrayList<String> subs = new ArrayList<>();
            subs.add("Non précisé");
            subs.add("cours");subs.add("Tp1");subs.add("Tp2");subs.add("Tp3");subs.add("Tp4");subs.add("Tp5");
            subs.add("Atelier 1");subs.add("Atelier 2");
            subs.add("cours devloppement mobile"); subs.add("cours virtualisation"); subs.add("cours sécurité");
            subs.add("cours e-commerce");subs.add("cours managment");subs.add("cours Hybride");
            subs.add("cours recherche scientifique");
            Cursor cr = AppAcceuil.handler.execQuery(qu);
            if (cr != null) {
                cr.moveToFirst();
                while (!cr.isAfterLast()) {
                    subs.add(cr.getString(0));
                    Log.d("GridAdapter.class", "En cache " + cr.getString(0));
                    cr.moveToNext();
                }
            } else
                Log.d("GridAdapter.class", "Pas de SUBS" + cr.getString(0));

            ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, subs);
            assert spn != null;
            spn.setAdapter(adapterSpinner);
            builder.setView(v)
                    // Add action buttons
                    .setPositiveButton("Entrer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            int day = datePicker.getDayOfMonth();
                            int month = datePicker.getMonth() + 1;
                            int year = datePicker.getYear();
                            String date = year + "-" + month + "-" + day;
                            String subject = spn.getSelectedItem().toString();
                            String qx = "SELECT title FROM NOTES where sub = '" + subject + "'";
                            Cursor cr = AppAcceuil.handler.execQuery(qx);
                            String subnames = "";
                            if (cr != null) {
                                cr.moveToFirst();
                                while (!cr.isAfterLast()) {
                                    subnames += (cr.getString(0)) + "\n";
                                    cr.moveToNext();
                                }
                            }


                            Cursor cursor = AppAcceuil.handler.execQuery("SELECT * FROM ATTENDANCE WHERE datex = '" +
                                    date + "' AND hour = " + hour.getText() + ";");
                            if (cursor == null || cursor.getCount() == 0) {
                                Intent launchinIntent = new Intent(AppAcceuil.activity, PresenceActivity.class);
                                launchinIntent.putExtra("DATE", date);
                                launchinIntent.putExtra("PERIOD", hour.getText().toString());
                                AppAcceuil.activity.startActivity(launchinIntent);
                            } else {
                                Toast.makeText(getActivity(), "Période déjà ajoutée", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            return builder.create();
        }
    }
}
