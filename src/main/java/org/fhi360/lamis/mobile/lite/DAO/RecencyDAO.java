package org.fhi360.lamis.mobile.lite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
import org.fhi360.lamis.mobile.lite.Domains.Hts2;
import org.fhi360.lamis.mobile.lite.Domains.Recency;
import org.fhi360.lamis.mobile.lite.Domains.Recency2;
import org.fhi360.lamis.mobile.lite.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class RecencyDAO {
    private Context context;
    private SQLiteOpenHelper lAMISLiteDb;

    public RecencyDAO(Context context) {
        this.context = context;
    }

    public void save(Recency recency) {
        try {

            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constant.recencyNumber, recency.getRecencyNumber());
            values.put(Constant.htsId, recency.getHts().getHtsId());
            values.put(Constant.testName, recency.getTestName());
            values.put(Constant.testDate, recency.getTestDate());
            values.put(Constant.controlLine, recency.getControlLine());
            values.put(Constant.verificationLine, recency.getVerificationLine());
            values.put(Constant.longTimeLine, recency.getLongTimeLine());
            values.put(Constant.recencyInterpretation, recency.getRecencyInterpretation());
            db.insert(Constant.TABLE_RECENCY, null, values);
            db.close();
        } catch (Exception e) {

        }

    }

    public Recency findHtsId(long htsIds) {
        String query = "Select * FROM " + Constant.TABLE_RECENCY + " WHERE " + Constant.htsId + " = " + htsIds;
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        Recency recency = new Recency();
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                recency.setRecencyNumber(cursor.getString(cursor.getColumnIndex(Constant.recencyNumber)));
            }

            db.close();
            cursor.close();
        } catch (Exception e) {

        }

        return recency;
    }

    public void update(Recency recency) {
        try {

            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constant.recencyNumber, recency.getRecencyNumber());
            values.put(Constant.htsId, recency.getHts().getHtsId());
            values.put(Constant.testDate, recency.getTestDate());
            values.put(Constant.testName, recency.getTestName());
            values.put(Constant.controlLine, recency.getControlLine());
            values.put(Constant.verificationLine, recency.getVerificationLine());
            values.put(Constant.longTimeLine, recency.getLongTimeLine());
            values.put(Constant.recencyInterpretation, recency.getRecencyInterpretation());
            db.update(Constant.TABLE_RECENCY, values, "recency_id = ?", new String[]{String.valueOf(recency.getId())});
            db.close();
        } catch (Exception e) {

        }
    }

    public int checkIfRecencyExist(Long htsId) {
        int count = 0;
        try {
            String query = "SELECT  * FROM " + Constant.TABLE_RECENCY + " WHERE hts_id = " + htsId;
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            count = cursor.getCount();
            db.close();
            cursor.close();
        } catch (Exception e) {

        }
        return count;
    }


    public Recency getByHtsId(Long htsId) {
        String selectQuery = "SELECT  * FROM " + Constant.TABLE_RECENCY + " WHERE hts_id = " + htsId;
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Recency recency = new Recency();
        if (cursor.moveToFirst()) {
            Hts2 hts = new Hts2();
            hts.setHtsId(cursor.getLong(cursor.getColumnIndex(Constant.htsId)));
            recency.setHts(hts);
            recency.setId(cursor.getLong(cursor.getColumnIndex(Constant.recencyId)));
            recency.setRecencyNumber(cursor.getString(cursor.getColumnIndex(Constant.recencyNumber)));
            recency.setTestName(cursor.getString(cursor.getColumnIndex(Constant.testName)));
            recency.setTestDate(cursor.getString(cursor.getColumnIndex(Constant.testDate)));
            recency.setControlLine(cursor.getLong(cursor.getColumnIndex(Constant.controlLine)));
            recency.setVerificationLine(cursor.getLong(cursor.getColumnIndex(Constant.verificationLine)));
            recency.setLongTimeLine(cursor.getLong(cursor.getColumnIndex(Constant.longTimeLine)));
            recency.setRecencyInterpretation(cursor.getString(cursor.getColumnIndex(Constant.recencyInterpretation)));
            db.close();
            cursor.close();
        }

        return recency;
    }

    public List<Recency2> sync() {
        List<Recency2> recencyList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + Constant.TABLE_RECENCY;
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Recency2 recency = new Recency2();
                recency.setHtsId(cursor.getLong(cursor.getColumnIndex(Constant.htsId)));
                recency.setRecencyNumber(cursor.getString(cursor.getColumnIndex(Constant.recencyNumber)));
                recency.setTestName(cursor.getString(cursor.getColumnIndex(Constant.testName)));
                recency.setTestDate(cursor.getString(cursor.getColumnIndex(Constant.testDate)));
                recency.setControlLine(cursor.getLong(cursor.getColumnIndex(Constant.controlLine)));
                recency.setVerificationLine(cursor.getLong(cursor.getColumnIndex(Constant.verificationLine)));
                recency.setLongTimeLine(cursor.getLong(cursor.getColumnIndex(Constant.longTimeLine)));
                recency.setRecencyInterpretation(cursor.getString(cursor.getColumnIndex(Constant.recencyInterpretation)));
                recencyList.add(recency);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return recencyList;
    }
}
