package org.fhi360.lamis.mobile.lite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
import org.fhi360.lamis.mobile.lite.Domains.Regimens;
import org.fhi360.lamis.mobile.lite.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class RegimensDAO {
    private Context context;
    private SQLiteOpenHelper lAMISLiteDb;

    public RegimensDAO(Context context) {
        this.context = context;
    }


    public void save(Regimens regimen2) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Constant.regimen_id, regimen2.getRegimenId());
            contentValues.put(Constant.regimen, regimen2.getRegimen());
            contentValues.put(Constant.regimentype_id, regimen2.getRegimentypeId());
            contentValues.put(Constant.regimentype, regimen2.getRegimentype());
            db.insert(Constant.TABLE_REGIMEN, null, contentValues);
            db.close();
        } catch (SQLiteException exception) {
            exception.printStackTrace();
        }

    }

    public List<Regimens> getRegimentType() {
        List<Regimens> regimentypes = new ArrayList<>();
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.query(true, Constant.TABLE_REGIMEN,
                    new String[]{"regimentype_id", "regimentype"},
                    "(regimentype_id >= ? AND regimentype_id <= ?)", new String[]{"1", "4"}, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    Regimens regimens = new Regimens();
                    String regimenIds = cursor.getString(cursor.getColumnIndex(Constant.regimentype_id));
                    regimens.setRegimentypeId(regimenIds);
                    regimens.setRegimentype(cursor.getString(cursor.getColumnIndex(Constant.regimentype)));
                    regimentypes.add(regimens);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (SQLiteException exception) {

        }
        return regimentypes;
    }


    public ArrayList<Regimens> getByRegimen() {
        ArrayList<Regimens> regimentypes = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + Constant.TABLE_REGIMEN;
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Regimens regimens = new Regimens();
                    regimens.setRegimen(cursor.getString(cursor.getColumnIndex(Constant.regimen)));
                    regimentypes.add(regimens);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {

        }
        return regimentypes;
    }


    public ArrayList<Regimens> getByRegimen(String regimenTypeId1) {
        ArrayList<Regimens> regimentypes = new ArrayList<>();
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.query(true, Constant.TABLE_REGIMEN,
                    new String[]{"regimen"}, "regimentype_id = ? ", new String[]{regimenTypeId1}, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    Regimens regimens = new Regimens();
                    regimens.setRegimen(cursor.getString(cursor.getColumnIndex(Constant.regimen)));
                    regimentypes.add(regimens);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {

        }
        return regimentypes;
    }


    public int getByRegimenName(String regimen) {
        int regimenId = 0;
        System.out.println("regimen "+regimen);
        try {
            String query = "SELECT  * FROM " + Constant.TABLE_REGIMEN + " WHERE regimen = ?;";
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, new String[]{regimen});
            if (cursor.getCount() > 0) {
                regimenId = cursor.getInt(cursor.getColumnIndex(Constant.regimen_id));

            }
            cursor.close();
            db.close();
        } catch (Exception ignored) {

        }
        return regimenId;
    }


}
