package br.com.dup.services.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Joerverson ... email: joerverson.santos@gmail.com on 14/01/16.
 */
public class DBCore extends SQLiteOpenHelper {
        private static final String NAME_DB = "gowifi";
        private static final int VERSION = 3;

        public DBCore(Context context) {
            super(context, NAME_DB, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table hotpots(_id integer primary key autoincrement, ssid varchar(255) not null, password varchar(255) not null,latitude double NOT NULL, longitude double NOT NULL)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table hotpots");
            onCreate(db);
        }

}
