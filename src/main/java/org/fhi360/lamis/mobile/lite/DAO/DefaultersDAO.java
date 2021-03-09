package org.fhi360.lamis.mobile.lite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
import org.fhi360.lamis.mobile.lite.Domains.Defaulter;
import org.fhi360.lamis.mobile.lite.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class DefaultersDAO {
    private Context context;
    private SQLiteOpenHelper lAMISLiteDb;
    public DefaultersDAO(Context context) {
        this.context = context;
    }


    public void saveDefaulters(Defaulter defaulter) {
        try {
            String selectQuery = "SELECT  * FROM " + Constant.TABLE_DEFAULTER + " WHERE patient_id = " + defaulter.getPatient_id();
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            ContentValues values = new ContentValues();
            values.put(Constant.patientId, String.valueOf(defaulter.getPatient_id()));
            values.put(Constant.facilityId, String.valueOf(defaulter.getFacility_id()));
            values.put(Constant.surname, defaulter.getSurname());
            values.put(Constant.othername, defaulter.getOther_names());
            values.put(Constant.address, defaulter.getAddress());
            values.put(Constant.phone, defaulter.getPhone());
            values.put(Constant.phoneKin, defaulter.getPhone_kin());
            values.put(Constant.addressKin, defaulter.getAddress_kin());
            values.put(Constant.nextKin, defaulter.getNext_kin());
            values.put(Constant.currentStatus, defaulter.getCurrent_status());
            values.put(Constant.dateCurrentStatus, defaulter.getDate_current_status());
            values.put(Constant.dateNextClinic, defaulter.getDate_next_clinic());
            values.put(Constant.dateNextRefill, defaulter.getDate_next_refill());
            values.put(Constant.hospitalNum, defaulter.getHospital_num());
            if (cursor.getCount() > 0) {
                db.update(Constant.TABLE_DEFAULTER, values, "patient_id= ?", new String[]{String.valueOf(defaulter.getPatient_id())});
            } else {
                db.insert(Constant.TABLE_DEFAULTER, null, values);
            }
            cursor.close();
            db.close();

        } catch (Exception ignored) {

        }
    }


    public List<Defaulter> getDefaulters() {
        ArrayList<Defaulter> defaulterArrayList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + Constant.TABLE_DEFAULTER;
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Defaulter defaulter = new Defaulter();
                    defaulter.setDefaulterId(cursor.getLong(cursor.getColumnIndex(Constant.defaulterId)));
                    defaulter.setPatient_id(cursor.getLong(cursor.getColumnIndex(Constant.patientId)));
                    defaulter.setHospital_num(cursor.getString(cursor.getColumnIndex(Constant.hospitalNum)));
                    defaulter.setFacility_id(cursor.getLong(cursor.getColumnIndex(Constant.facilityId)));
                    defaulter.setSurname(cursor.getString(cursor.getColumnIndex(Constant.surname)));
                    defaulter.setOther_names(cursor.getString(cursor.getColumnIndex(Constant.otherName)));
                    defaulter.setAddress(cursor.getString(cursor.getColumnIndex(Constant.address)));
                    defaulter.setPhone(cursor.getString(cursor.getColumnIndex(Constant.phone)));
                    defaulter.setPhone_kin(cursor.getString(cursor.getColumnIndex(Constant.phoneKin)));
                    defaulter.setAddress_kin(cursor.getString(cursor.getColumnIndex(Constant.addressKin)));
                    defaulter.setNext_kin(cursor.getString(cursor.getColumnIndex(Constant.nextKin)));
                    defaulter.setCurrent_status(cursor.getString(cursor.getColumnIndex(Constant.currentStatus)));
                    defaulter.setDate_next_clinic(cursor.getString(cursor.getColumnIndex(Constant.dateNextClinic)));
                    defaulter.setDate_next_refill(cursor.getString(cursor.getColumnIndex(Constant.dateNextRefill)));
                    defaulter.setDate_current_status(cursor.getString(cursor.getColumnIndex(Constant.dateCurrentStatus)));
                    defaulterArrayList.add(defaulter);
                } while ((cursor.moveToNext()));
            }
        } catch (Exception ignored) {

        }
        return defaulterArrayList;
    }


}
