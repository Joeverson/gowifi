package br.com.dup.services.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.dup.imwifi.Hotpots;

/**
 * Created by joerverson ... email: joerverson.santos@gmail.com on 14/01/16.
 */
public class DB {

    private SQLiteDatabase db;
    private String table = "hotpots";

    public DB(Context context){
        DBCore dbCore = new DBCore(context);
        this.db = dbCore.getWritableDatabase(); // pega a instacia do db
    }


    /**
     * this method is add new register in database.
     *
     * param string, string, double, double.
     * return boolean.
     * */
    public boolean setData(String ssid, String password, double latitude, double longitude){
        ContentValues args  = new ContentValues();
        args.put("ssid", ssid);
        args.put("password", password);
        args.put("latitude", latitude);
        args.put("longitude", longitude);

        //para atualizar ou criar se não existir
        if(db.insert(this.table, null, args) == -1)
            return false;
        return true;
    }

    /**
     * just show the counts the number of columns of tables
     *
     * param null
     * return int
     * */
    public int getCountLines(){
        String[] cols = new String[]{"ssid", "password","_id"};
        Cursor pointer = db.query(this.table, cols, null, null,null, null, null);
        return pointer.getCount();
    }

    /**
     * the method is for get all infos or date same and show for who need him.
     *
     * param null
     * return list - a list with hotpots that a infos are organized.
     * */
    public List getData(){
        List<Hotpots> list = new ArrayList<Hotpots>();
        Cursor cursor = this.db.query(this.table, new String[] { "ssid", "password", "latitude","longitude" },null, null, null, null, "ssid desc");

        if (cursor.moveToFirst()) {
            do {
                //adicionando os hotsposts que veio do banco de dados para um lista e passar para qualquer um consumir
                list.add(new Hotpots(cursor.getString(0),cursor.getString(1),Double.parseDouble(cursor.getString(2)), Double.parseDouble(cursor.getString(3))));
            } while (cursor.moveToNext());
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        return list;
    }



}