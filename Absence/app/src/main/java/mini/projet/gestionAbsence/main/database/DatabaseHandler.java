package mini.projet.gestionAbsence.main.database;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHandler   {
    Activity activity;
    private SQLiteDatabase database;

    public DatabaseHandler(Activity activity) {
        this.activity = activity;
        database = activity.openOrCreateDatabase("ASSIST", activity.MODE_PRIVATE, null);
        createTable();
    }

    public void createTable() {
        try {
            String qu = "CREATE TABLE IF NOT EXISTS STUDENT(name varchar(1000)," +
                    "cl varchar(100), " +
                    "regno varchar(100) primary key, contact varchar(100),roll integer);";
            database.execSQL(qu);
        } catch (Exception e) {
            Toast.makeText(activity, "Une erreur s'est produite pour créer une tablee", Toast.LENGTH_LONG).show();
        }
        try {
            String qu = "CREATE TABLE IF NOT EXISTS ATTENDANCE(datex date," +
                    "hour int, " +
                    "register varchar(100) ,isPresent boolean);";
            database.execSQL(qu);
        } catch (Exception e) {
            Toast.makeText(activity, "Une erreur s'est produite pour créer une table", Toast.LENGTH_LONG).show();
        }

        try {
            String qu = "CREATE TABLE IF NOT EXISTS NOTES(title varchar(100) not null," +
                    "body varchar(10000), cls varchar(1000), sub varchar(1000) ,datex TIMESTAMP default CURRENT_TIMESTAMP);";
            database.execSQL(qu);
        } catch (Exception e) {
            Toast.makeText(activity, "Une erreur s'est produite pour créer une table", Toast.LENGTH_LONG).show();
        }

        try {
            String qu = "CREATE TABLE IF NOT EXISTS SCHEDULE(cl varchar(100),subject varchar(1000)," +
                    "timex time, day_week varchar(100));";
            database.execSQL(qu);
        } catch (Exception e) {
            Toast.makeText(activity, "Une erreur s'est produite pour créer une table", Toast.LENGTH_LONG).show();
        }
    }

    public boolean execAction(String qu) {
        Log.i("DatabaseHandler", qu);
        try {
            database.execSQL(qu);
        } catch (Exception e) {
            Log.e("DatabaseHandler", qu);
            Toast.makeText(activity, "Une erreur s'est produite pour execAction", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public Cursor execQuery(String qu) {
        try {
            return database.rawQuery(qu, null);
        } catch (Exception e) {
            Log.e("DatabaseHandler", qu);
            Toast.makeText(activity," Une erreur s'est produite pour execAction",Toast.LENGTH_LONG).show();
        }
        return null;
    }
}
