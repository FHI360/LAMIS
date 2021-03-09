package org.fhi360.lamis.mobile.lite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
import org.fhi360.lamis.mobile.lite.Domains.State;
import org.fhi360.lamis.mobile.lite.Utils.Constant;

import java.util.ArrayList;

public class StateDAO {
    private Context context;
    private SQLiteOpenHelper lAMISLiteDb;
    public StateDAO(Context context) {
        this.context = context;
    }

    public void saveState(State state) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constant.stateId, String.valueOf(state.getId()));
            values.put(Constant.name, state.getName());
            db.insert(Constant.TABLE_STATE, null, values);
            db.close();
        } catch (Exception e) {

        }

    }


    public ArrayList<State> getStates() {
        ArrayList<State> listState = new ArrayList<State>();
        try {
            String selectQuery = "SELECT  * FROM " + Constant.TABLE_STATE;
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    State state = new State();
                    state.setName(cursor.getString(cursor.getColumnIndex(Constant.name)));
                    state.setId(cursor.getLong(cursor.getColumnIndex(Constant.stateId)));
                    listState.add(state);
                } while (cursor.moveToNext());
            }
            db.close();
            cursor.close();
        } catch (Exception e) {

        }
        return listState;
    }



}
