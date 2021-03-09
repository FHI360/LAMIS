package org.fhi360.lamis.mobile.lite.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
import org.fhi360.lamis.mobile.lite.Domains.Patient;
import org.fhi360.lamis.mobile.lite.Utils.Constant;

public class DeleteDAO {
    private Context context;
    private SQLiteOpenHelper lAMISLiteDb;

    public DeleteDAO(Context context) {
        this.context = context;
    }

    public void removeClinic(long clinicId1) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            db.delete(Constant.TABLE_CLINIC, Constant.patientId + " = ?", new String[]{String.valueOf(clinicId1)});
            db.close();
        } catch (Exception ignored) {

        }
    }

    public void removeHts(long htsId1) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            db.delete(Constant.TABLE_HTS, Constant.htsId + " = ?", new String[]{String.valueOf(htsId1)});
            Patient patient = new HtsDAO(context).findPatientHtsId(htsId1);
            if (patient != null) {
                db.delete(Constant.TABLE_PATIENT, Constant.patientId + " = ?", new String[]{String.valueOf(patient.getPatientId())});
                db.delete(Constant.TABLE_CLINIC, Constant.clinicId + " = ?", new String[]{String.valueOf(patient.getPatientId())});
                String query = "Select * FROM " + Constant.TABLE_INDEX_CONTACT + " WHERE " + Constant.htsId + " = " + htsId1;
                @SuppressLint("Recycle")
                Cursor cursor = db.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    do {
                        db.delete(Constant.TABLE_INDEX_CONTACT, Constant.htsId + " = ?", new String[]{String.valueOf(htsId1)});
                    } while (cursor.moveToNext());
                }
                db.close();
            } else {
                db.close();
            }
        } catch (Exception ignored) {

        }
    }


    public void removePatient(long patientIds, Long htsId) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            db.delete(Constant.TABLE_PATIENT, Constant.patientId + " = ?", new String[]{String.valueOf(patientIds)});
            db.delete(Constant.TABLE_CLINIC, Constant.clinicId + " = ?", new String[]{String.valueOf(patientIds)});
            db.delete(Constant.TABLE_BIOMETRIC, Constant.patientId + " = ?", new String[]{String.valueOf(htsId)});
            db.close();
        } catch (Exception ignored) {

        }

    }


    public void remove() {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            db.execSQL("DELETE FROM " + Constant.TABLE_FACILITY);
            db.execSQL("DELETE FROM " + Constant.TABLE_STATE);
            db.execSQL("DELETE FROM " + Constant.TABLE_LGA);
            db.execSQL("DELETE FROM " + Constant.TABLE_REGIMEN);
            db.close();
        } catch (Exception ignored) {

        }

    }

    public void removeBiometric(String uuid) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            db.delete(Constant.TABLE_BIOMETRIC, Constant.uuid + " = ?", new String[]{String.valueOf(uuid)});
            db.delete(Constant.TABLE_PATIENT, Constant.uuid + " = ?", new String[]{String.valueOf(uuid)});
            db.close();
        } catch (Exception ignored) {

        }

    }

    public void removeDuplicateBiometric(Long patientId) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            db.delete(Constant.TABLE_BIOMETRIC, Constant.patientId + " = ?", new String[]{String.valueOf(patientId)});
            db.close();
        } catch (Exception ignored) {

        }

    }


}
