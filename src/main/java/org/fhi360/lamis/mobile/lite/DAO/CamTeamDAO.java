package org.fhi360.lamis.mobile.lite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
import org.fhi360.lamis.mobile.lite.Domains.CamTeam;
import org.fhi360.lamis.mobile.lite.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class CamTeamDAO {
    private Context context;
    private SQLiteOpenHelper lAMISLiteDb;

    public CamTeamDAO(Context context) {
        this.context = context;
    }

    public void save(CamTeam camTeam) {
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        System.out.println("CAMTEAM "+camTeam.getCamteam());
        values.put(Constant.facilityId, camTeam.getFacilityId());
        values.put(Constant.camteam, camTeam.getCamteam());
        values.put(Constant.camCode, camTeam.getCamCode());
        db.insert(Constant.TABLE_CAM_TEAM, null, values);
        db.close();
    }

    public List<CamTeam> getCamTeam() {
        List<CamTeam> camTeams = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + Constant.TABLE_CAM_TEAM + "";
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    CamTeam camTeam = new CamTeam();
                    camTeam.setFacilityId(cursor.getLong(cursor.getColumnIndex(Constant.facilityId)));
                    camTeam.setCamCode(cursor.getString(cursor.getColumnIndex(Constant.camCode)));
                    camTeam.setCamteam(cursor.getString(cursor.getColumnIndex(Constant.camteam)));
                    camTeams.add(camTeam);
                } while (cursor.moveToNext());
            }
            db.close();
            cursor.close();
        } catch (Exception ignored) {

        }
        return camTeams;
    }

}
