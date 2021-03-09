package org.fhi360.lamis.mobile.lite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
import org.fhi360.lamis.mobile.lite.Domains.Geolocation;
import org.fhi360.lamis.mobile.lite.Domains.GetNameId;
import org.fhi360.lamis.mobile.lite.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class GeoLocationDAO {
    private Context context;
    private SQLiteOpenHelper lAMISLiteDb;

    public GeoLocationDAO(Context context) {
        this.context = context;
    }


    public void saveGeoLocation(String testSetting, String name, String longitude, String latitude) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Constant.testingSetting, testSetting);
            contentValues.put(Constant.name, name);
            contentValues.put(Constant.longitude, Float.valueOf(longitude));
            contentValues.put(Constant.latitude, Float.valueOf(latitude));
            db.insert(Constant.TABLE_GEO_LOCATION, null, contentValues);
            db.close();
        } catch (SQLiteException exception) {
            exception.printStackTrace();
        }

    }

    public List<GetNameId> getGeolocationName1(String testinSetting) {
        List<GetNameId> name = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM geolocation where testing_setting = '"+testinSetting+"'";
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    GetNameId setNameId = new GetNameId();
                    setNameId.setTestingSetting(cursor.getString(cursor.getColumnIndex(Constant.testingSetting)));
                    setNameId.setId(cursor.getLong(cursor.getColumnIndex(Constant.id)));
                    setNameId.setName(cursor.getString(cursor.getColumnIndex(Constant.name)));
                    name.add(setNameId);
                } while (cursor.moveToNext());
            }
            db.close();
            cursor.close();
        } catch (Exception ignored) {

        }
        return name;
    }

    public List<GetNameId> getGeolocationName() {
        List<GetNameId> name = new ArrayList<>();
        try {
            String selectQuery = "SELECT  * FROM " + Constant.TABLE_GEO_LOCATION;
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    GetNameId setNameId = new GetNameId();
                    setNameId.setTestingSetting(cursor.getString(cursor.getColumnIndex(Constant.testingSetting)));
                    setNameId.setId(cursor.getLong(cursor.getColumnIndex(Constant.id)));
                    setNameId.setName(cursor.getString(cursor.getColumnIndex(Constant.name)));
                    name.add(setNameId);
                } while (cursor.moveToNext());
            }
            db.close();
            cursor.close();
        } catch (Exception ignored) {

        }
        return name;
    }


    public List<Geolocation> getLongitudeAndLatitude(Long id) {
        List<Geolocation> geolocations = new ArrayList<>();
        try {
            String selectQuery = "SELECT  * FROM " + Constant.TABLE_GEO_LOCATION + " WHERE id = " + id;
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Geolocation geolocation = new Geolocation();
                    geolocation.setLongitude(cursor.getFloat(cursor.getColumnIndex(Constant.longitude)));
                    geolocation.setLatitude(cursor.getFloat(cursor.getColumnIndex(Constant.latitude)));
                    geolocation.setName(cursor.getString(cursor.getColumnIndex(Constant.name)));
                    geolocations.add(geolocation);
                } while (cursor.moveToNext());
            }
            db.close();
            cursor.close();
        } catch (Exception ignored) {

        }
        return geolocations;
    }


}
