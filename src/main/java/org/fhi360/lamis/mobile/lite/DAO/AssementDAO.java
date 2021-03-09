package org.fhi360.lamis.mobile.lite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
import org.fhi360.lamis.mobile.lite.Domains.Assessment;
import org.fhi360.lamis.mobile.lite.Domains.AssessmentReturn;
import org.fhi360.lamis.mobile.lite.Domains.Facility;
import org.fhi360.lamis.mobile.lite.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class AssementDAO {
    private Context context;
    private SQLiteOpenHelper lAMISLiteDb;
    public AssementDAO(Context context) {
        this.context = context;
    }

    public long saveRiskAssessment(Assessment assessment) {
        long assessment_id = 0;
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constant.facilityId, assessment.getFacilityId());
            values.put(Constant.clientCode, assessment.getClientCode());
            values.put(Constant.dateVisit, assessment.getDateVisit());
            values.put(Constant.question1, assessment.getQuestion1());
            values.put(Constant.question2, assessment.getQuestion2());
            values.put(Constant.question3, assessment.getQuestion3());
            values.put(Constant.question4, assessment.getQuestion4());
            values.put(Constant.question5, assessment.getQuestion5());
            values.put(Constant.question6, assessment.getQuestion6());
            values.put(Constant.question7, assessment.getQuestion7());
            values.put(Constant.question8, assessment.getQuestion8());
            values.put(Constant.question9, assessment.getQuestion9());
            values.put(Constant.question10, assessment.getQuestion10());
            values.put(Constant.question11, assessment.getQuestion11());
            values.put(Constant.question12, assessment.getQuestion12());
            values.put(Constant.deviceId, assessment.getDeviceconfigId());
            values.put(Constant.sti1, assessment.getSti1());
            values.put(Constant.sti2, assessment.getSti2());
            values.put(Constant.sti3, assessment.getSti3());
            values.put(Constant.sti4, assessment.getSti4());
            values.put(Constant.sti5, assessment.getSti5());
            values.put(Constant.sti6, assessment.getSti6());
            values.put(Constant.sti7, assessment.getSti7());
            values.put(Constant.sti8, assessment.getSti8());
            assessment_id = db.insert(Constant.TABLE_ASSESSMENT, null, values);
            db.close();


        } catch (Exception e) {

        }
        return assessment_id;
    }



    public List<AssessmentReturn> getAssessment() {
        ArrayList<AssessmentReturn> assessments = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + Constant.TABLE_ASSESSMENT;
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    AssessmentReturn assessment = new AssessmentReturn();
                   // assessment.setAssessmentId(cursor.getLong(cursor.getColumnIndex(Constant.assessmentId)));
                    assessment.setClientCode(cursor.getString(cursor.getColumnIndex(Constant.clientCode)));
                    Facility facility = new Facility();
                    facility.setId(cursor.getLong(cursor.getColumnIndex(Constant.facilityId)));
                    assessment.setFacility(facility);
                    assessment.setDateVisit(cursor.getString(cursor.getColumnIndex(Constant.dateVisit)));
                    assessment.setQuestion1("Negative less than 6 Months");
                    assessment.setQuestion2(String.valueOf(cursor.getInt(cursor.getColumnIndex(Constant.question2))));
                    assessment.setQuestion3(cursor.getInt(cursor.getColumnIndex(Constant.question3)));
                    assessment.setQuestion4(cursor.getInt(cursor.getColumnIndex(Constant.question4)));
                    assessment.setQuestion5(cursor.getInt(cursor.getColumnIndex(Constant.question5)));
                    assessment.setQuestion6(cursor.getInt(cursor.getColumnIndex(Constant.question6)));
                    assessment.setQuestion7(cursor.getInt(cursor.getColumnIndex(Constant.question7)));
                    assessment.setQuestion8(cursor.getInt(cursor.getColumnIndex(Constant.question8)));
                    assessment.setQuestion9(cursor.getInt(cursor.getColumnIndex(Constant.question9)));
                    assessment.setQuestion10(cursor.getInt(cursor.getColumnIndex(Constant.question10)));
                    assessment.setQuestion11(cursor.getInt(cursor.getColumnIndex(Constant.question11)));
                    assessment.setQuestion12(cursor.getInt(cursor.getColumnIndex(Constant.question12)));
                    assessment.setDeviceconfigId(cursor.getLong(cursor.getColumnIndex(Constant.deviceId)));
                    assessment.setSti1(cursor.getInt(cursor.getColumnIndex(Constant.sti1)));
                    assessment.setSti2(cursor.getInt(cursor.getColumnIndex(Constant.sti2)));
                    assessment.setSti3(cursor.getInt(cursor.getColumnIndex(Constant.sti3)));
                    assessment.setSti4(cursor.getInt(cursor.getColumnIndex(Constant.sti4)));
                    assessment.setSti5(cursor.getInt(cursor.getColumnIndex(Constant.sti5)));
                    assessment.setSti6(cursor.getInt(cursor.getColumnIndex(Constant.sti6)));
                    assessment.setSti7(cursor.getInt(cursor.getColumnIndex(Constant.sti7)));
                    assessment.setSti8(cursor.getInt(cursor.getColumnIndex(Constant.sti8)));
                    assessments.add(assessment);
                } while ((cursor.moveToNext()));
            }

        } catch (Exception ignored) {

        }
        return assessments;
    }



}
