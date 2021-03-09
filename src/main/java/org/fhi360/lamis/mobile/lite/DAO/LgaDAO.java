package org.fhi360.lamis.mobile.lite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
import org.fhi360.lamis.mobile.lite.Domains.Lga;
import org.fhi360.lamis.mobile.lite.Utils.Constant;

import java.util.ArrayList;

public class LgaDAO {
    private Context context;
    private SQLiteOpenHelper lAMISLiteDb;

    public LgaDAO(Context context) {
        this.context = context;
    }


    public ArrayList<Lga> getLga() {
        ArrayList<Lga> listLga = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + Constant.TABLE_LGA;
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Lga lga = new Lga();
                    lga.setId(cursor.getLong(cursor.getColumnIndex(Constant.lgaId)));
                    lga.setName(cursor.getString(cursor.getColumnIndex(Constant.name)));
                    listLga.add(lga);
                } while ((cursor.moveToNext()));
            }
            cursor.close();
            db.close();
        } catch (Exception e) {

        }
        return listLga;
    }


    public ArrayList<Lga> getLgaByStateId(long stateId) {
        ArrayList<Lga> listLga = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + Constant.TABLE_LGA + " WHERE  state_id=" + stateId + " ORDER BY name ASC";
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Lga lga = new Lga();
                    lga.setId(cursor.getLong(cursor.getColumnIndex(Constant.lgaId)));
                    lga.setName(cursor.getString(cursor.getColumnIndex(Constant.name)));
                    listLga.add(lga);

                } while ((cursor.moveToNext()));
            }
            cursor.close();
            db.close();
        } catch (Exception e) {

        }
        return listLga;
    }


    public void saveLga(Lga lga) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constant.lgaId, String.valueOf(lga.getId()));
            values.put(Constant.name, lga.getName());
            values.put(Constant.stateId, lga.getState().getId());
            db.insert(Constant.TABLE_LGA, null, values);
            db.close();
        } catch (Exception e) {

        }
    }


}
