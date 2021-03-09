package org.fhi360.lamis.mobile.lite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
import org.fhi360.lamis.mobile.lite.Domains.ViralLoad;
import org.fhi360.lamis.mobile.lite.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class ViralLoadDAO {
    private Context context;
    private SQLiteOpenHelper lAMISLiteDb;

    public ViralLoadDAO(Context context) {
        this.context = context;
    }


    public void save(ViralLoad viralLoad) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constant.recencyNumber, viralLoad.getRecencyNumber());
            values.put(Constant.sampleReferenceNumber, viralLoad.getSampleReferenceNumber());
            values.put(Constant.viralLoadResultClassification, viralLoad.getViralLoadResultClassification());
            values.put(Constant.dateSampleCollected, viralLoad.getDateSampleCollected());
            values.put(Constant.typeSample, viralLoad.getTypeSample());
            values.put(Constant.dateSampleTest, viralLoad.getDateSampleTest());
            values.put(Constant.dateTestDone, viralLoad.getDateTestDone());
            values.put(Constant.viralLoadResult, viralLoad.getViralLoadResult());
            values.put(Constant.finalResult, viralLoad.getFinalResult());
            db.insert(Constant.TABLE_VIRAL_LOAD, null, values);
            db.close();
        } catch (Exception e) {

        }

    }

    public void update(ViralLoad viralLoad) {
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.sampleReferenceNumber, viralLoad.getSampleReferenceNumber());
        values.put(Constant.viralLoadResultClassification, viralLoad.getViralLoadResultClassification());
        values.put(Constant.dateSampleCollected, viralLoad.getDateSampleCollected());
        values.put(Constant.typeSample, viralLoad.getTypeSample());
        values.put(Constant.dateTestDone, viralLoad.getDateTestDone());
        values.put(Constant.dateSampleTest, viralLoad.getDateSampleTest());
        values.put(Constant.viralLoadResult, viralLoad.getViralLoadResult());
        values.put(Constant.finalResult, viralLoad.getFinalResult());
        db.update(Constant.TABLE_VIRAL_LOAD, values, "viral_id = ?", new String[]{String.valueOf(viralLoad.getId())});
        db.close();


    }

    public List<ViralLoad> getViralLoad() {
        List<ViralLoad> loadArrayList = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + Constant.TABLE_VIRAL_LOAD + "ASC";
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    ViralLoad viralLoad = new ViralLoad();
                    viralLoad.setDateSampleCollected(cursor.getString(cursor.getColumnIndex(Constant.dateSampleCollected)));
                    viralLoad.setRecencyNumber(cursor.getString(cursor.getColumnIndex(Constant.recencyNumber)));
                    viralLoad.setTypeSample(cursor.getString(cursor.getColumnIndex(Constant.typeSample)));
                    viralLoad.setDateSampleTest(cursor.getString(cursor.getColumnIndex(Constant.dateSampleTest)));
                    viralLoad.setDateTestDone(cursor.getString(cursor.getColumnIndex(Constant.dateTestDone)));
                    viralLoad.setViralLoadResult(cursor.getString(cursor.getColumnIndex(Constant.viralLoadResult)));
                    viralLoad.setFinalResult(cursor.getString(cursor.getColumnIndex(Constant.finalResult)));
                    loadArrayList.add(viralLoad);
                } while (cursor.moveToNext());
            }
            db.close();
            cursor.close();
        } catch (Exception e) {

        }
        return loadArrayList;
    }

    public int getViralLoadByReference1(String recencyNumber) {
        int count = 0;
        String selectQuery = "SELECT * FROM viral_load where recency_number = '" + recencyNumber + "'";
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        count = cursor.getCount();
        db.close();
        cursor.close();
        return count;
    }

    public ViralLoad getViralLoadByReference(String recencyNumber) {
        String selectQuery = "SELECT * FROM viral_load where recency_number = '" + recencyNumber + "'";
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ViralLoad viralLoad = new ViralLoad();
        if (cursor.moveToFirst()) {
            viralLoad.setId(cursor.getLong(cursor.getColumnIndex(Constant.viralId)));
            // viralLoad.setSampleReferenceNumber(cursor.getString(cursor.getColumnIndex(Constant.labno)));
            viralLoad.setDateSampleCollected(cursor.getString(cursor.getColumnIndex(Constant.dateSampleCollected)));
            viralLoad.setRecencyNumber(cursor.getString(cursor.getColumnIndex(Constant.recencyNumber)));
            viralLoad.setTypeSample(cursor.getString(cursor.getColumnIndex(Constant.typeSample)));
            viralLoad.setDateSampleTest(cursor.getString(cursor.getColumnIndex(Constant.dateSampleTest)));
            viralLoad.setDateTestDone(cursor.getString(cursor.getColumnIndex(Constant.dateTestDone)));
            viralLoad.setViralLoadResult(cursor.getString(cursor.getColumnIndex(Constant.viralLoadResult)));
            viralLoad.setFinalResult(cursor.getString(cursor.getColumnIndex(Constant.finalResult)));
            db.close();
            cursor.close();
        }
        return viralLoad;
    }


}
