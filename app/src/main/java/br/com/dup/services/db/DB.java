package br.com.dup.services.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.dup.services.wifimanager.WifiControl;
import br.edu.ifpb.dup.imwifi.Hotpots;

/**
 * Created by joerverson ... email: joerverson.santos@gmail.com on 14/01/16.
 */
public class DB {

    private SQLiteDatabase dbw;
    private SQLiteDatabase dbr;
    private String table = "hotpots";

    public DB(Context context){
        DBCore dbCore = new DBCore(context);
        this.dbw = dbCore.getWritableDatabase(); // pega a instacia do db
        this.dbr = dbCore.getReadableDatabase(); // pega a instacia do db
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
        if(dbw.insert(this.table, null, args) == -1)
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
        String[] cols = new String[]{"_id"};
        Cursor pointer = dbr.query(this.table, cols, null, null,null, null, null);
        return (pointer.getCount());
    }

    /**
     * the method is for get all infos or date same and show for who need him.
     *
     * param null
     * return list - a list with hotpots that a infos are organized.
     * */
    public List getData(){
        List<Hotpots> list = new ArrayList<Hotpots>();
        Cursor cursor = this.dbr.query(this.table, new String[] { "ssid", "password", "latitude","longitude" },null, null, null, null, "ssid desc");

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

    public Boolean nearNetwork(Double latitude, Double longitude){

        // fazendo um raio de +- 10 metros (usando uma logica bem idiota)
        Double latitudeM = latitude + 00.0005000;
        Double longitudeM = longitude + 00.0005000;

        Cursor cursor = this.dbr.rawQuery("select * from ? where (latitude between ? and ?) and (longitude between ? and ?)", new String [] {table, String.valueOf(latitude), String.valueOf(latitudeM), String.valueOf(longitude), String.valueOf(longitudeM)});

        if(cursor == null)
            return false;

        if(cursor.moveToFirst())
            return true;

        return false;
    }

    public Boolean exist(String in){
        Cursor cursor = this.dbr.rawQuery("select * from ? where ssid=?"+in, new String[] { table, in });
        Boolean rResult = true; //variavel de retorno .. para n ficar sheiod e return no code.

        if(cursor == null){
            Log.e(WifiControl.APP_TAG, "Error no cursor, não foi retornado nada no banco:: line 87");
            return true;
        }


        if (cursor.moveToFirst()) {
            rResult = false;
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        return rResult;
    }


}
