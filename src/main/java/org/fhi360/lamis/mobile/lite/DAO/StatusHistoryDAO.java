package org.fhi360.lamis.mobile.lite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
import org.fhi360.lamis.mobile.lite.Domains.StatusHistory;
import org.fhi360.lamis.mobile.lite.Utils.Constant;

public class StatusHistoryDAO {
    private Context context;
    private SQLiteOpenHelper lAMISLiteDb;
    public StatusHistoryDAO(Context context) {
        this.context = context;
    }




    public void saveStatusHistory(StatusHistory statusHistory) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constant.patientId, String.valueOf(statusHistory.getPatient().getPatientId()));
            values.put(Constant.facilityId, String.valueOf(statusHistory.getFacilityId()));
            values.put(Constant.currentStatus, statusHistory.getCurrentStatus());
            values.put(Constant.dateCurrentStatus, statusHistory.getDateCurrentStatus());
            values.put(Constant.reasonInterrupt, statusHistory.getReasonInterrupt());
            values.put(Constant.causeDeath, statusHistory.getCauseDeath());
            values.put(Constant.dateTracked, statusHistory.getDateTracked());
            values.put(Constant.outcome, statusHistory.getOutcome());
            values.put(Constant.agreedDate, statusHistory.getAgreedDate());
            db.insert(Constant.TABLE_STATUS_HISTORY, null, values);

            db.close();

        } catch (Exception e) {

        }

    }

}
