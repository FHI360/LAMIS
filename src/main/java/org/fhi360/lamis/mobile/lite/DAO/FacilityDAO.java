package org.fhi360.lamis.mobile.lite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
import org.fhi360.lamis.mobile.lite.Domains.Facility;
import org.fhi360.lamis.mobile.lite.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class FacilityDAO {
    private Context context;
    private SQLiteOpenHelper lAMISLiteDb;
    public FacilityDAO(Context context) {
        this.context = context;
    }


    public ArrayList getFacilityByLgaId(long lgaIds) {

        ArrayList listFacility = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + Constant.TABLE_FACILITY + " WHERE  lga_id=" + lgaIds + " ORDER BY facility_name ASC";
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Facility facility = new Facility();
                    facility.setFacilityid(cursor.getLong(cursor.getColumnIndex(Constant.facilityId)));
                    facility.setName(cursor.getString(cursor.getColumnIndex(Constant.facilityName)));
                    facility.setLgaid(cursor.getLong(cursor.getColumnIndex(Constant.lgaId)));
                    facility.setStateid(cursor.getLong(cursor.getColumnIndex(Constant.stateId)));
                    listFacility.add(facility);
                } while (cursor.moveToNext());
            }
            db.close();
            cursor.close();
            // return user list
        } catch (Exception e) {

        }
        return listFacility;
    }


    public Facility getFacilityById(long id) {
        ArrayList listFacility = new ArrayList<>();
        Facility facility = new Facility();
        try {
            String selectQuery = "SELECT * FROM " + Constant.TABLE_FACILITY + " WHERE  facility_id=" + id + " ORDER BY facility_name ASC";
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                facility.setFacilityid(cursor.getLong(cursor.getColumnIndex(Constant.facilityId)));
                facility.setName(cursor.getString(cursor.getColumnIndex(Constant.facilityName)));
                facility.setLgaid(cursor.getLong(cursor.getColumnIndex(Constant.lgaId)));
                facility.setStateid(cursor.getLong(cursor.getColumnIndex(Constant.stateId)));
            }
            db.close();
            cursor.close();
        } catch (Exception e) {

        }
        return facility;
    }


    public ArrayList getFacility() {
        ArrayList listFacility = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + Constant.TABLE_FACILITY + " ORDER BY facility_name ASC";
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Facility facility = new Facility();
                    facility.setFacilityid(cursor.getLong(cursor.getColumnIndex(Constant.facilityId)));
                    facility.setName(cursor.getString(cursor.getColumnIndex(Constant.facilityName)));
                    facility.setLgaid(cursor.getLong(cursor.getColumnIndex(Constant.lgaId)));
                    facility.setStateid(cursor.getLong(cursor.getColumnIndex(Constant.stateId)));
                    listFacility.add(facility);
                } while (cursor.moveToNext());
            }
            db.close();
            cursor.close();
        } catch (Exception e) {

        }
        return listFacility;
    }

    public void saveFacility(Facility facility) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constant.facilityId, String.valueOf(facility.getFacilityid()));
            values.put(Constant.deviceId, String.valueOf(facility.getDeviceconfigid()));
            values.put(Constant.facilityName, facility.getName());
            values.put(Constant.stateId, String.valueOf(facility.getStateid()));
            values.put(Constant.lgaId, String.valueOf(facility.getLgaid()));
            db.insert(Constant.TABLE_FACILITY, null, values);
            db.close();
        } catch (Exception e) {

        }

    }



    public List<Facility> getFacility1() {
        List<Facility> listFacility = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + Constant.TABLE_FACILITY + " ORDER BY facility_name ASC";
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Facility facility = new Facility();
                    facility.setId(cursor.getLong(cursor.getColumnIndex(Constant.facilityId)));
                    facility.setName(cursor.getString(cursor.getColumnIndex(Constant.facilityName)));
                    facility.setLgaid(cursor.getLong(cursor.getColumnIndex(Constant.lgaId)));
                    facility.setStateid(cursor.getLong(cursor.getColumnIndex(Constant.stateId)));
                    listFacility.add(facility);
                } while (cursor.moveToNext());
            }
            db.close();
            cursor.close();
        } catch (Exception ignored) {

        }
        return listFacility;
    }

}
