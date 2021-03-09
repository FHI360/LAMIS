package org.fhi360.lamis.mobile.lite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
import org.fhi360.lamis.mobile.lite.Domains.Hts;
import org.fhi360.lamis.mobile.lite.Domains.Hts2;
import org.fhi360.lamis.mobile.lite.Domains.IndexContact;
import org.fhi360.lamis.mobile.lite.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class IndexContactDAO {
    private Context context;
    private SQLiteOpenHelper lAMISLiteDb;

    public IndexContactDAO(Context context) {
        this.context = context;
    }

    public List<IndexContact> getIndexContact() {
        ArrayList<IndexContact> indexContacts = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + Constant.TABLE_INDEX_CONTACT;
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {

            if (cursor.moveToFirst()) {
                do {
                    IndexContact indexContact = new IndexContact();
                    Hts2 hts2 = new Hts2();
                    hts2.setHtsId(cursor.getLong(cursor.getColumnIndex(Constant.htsId)));
                    indexContact.setHts(hts2);
                    Hts hts = this.getHtsId(cursor.getLong(cursor.getColumnIndex(Constant.htsId)));
                    indexContact.setClientCode(hts.getClientCode());
                    indexContact.setFacilityId(cursor.getLong(cursor.getColumnIndex(Constant.facilityId)));
                    indexContact.setContactType(cursor.getString(cursor.getColumnIndex(Constant.contactType)));
                    indexContact.setDeviceconfigId(cursor.getLong(cursor.getColumnIndex(Constant.deviceId)));
                    indexContact.setIndexContactCode(cursor.getString(cursor.getColumnIndex(Constant.indexContactCode)));
                    indexContact.setClientCode(cursor.getString(cursor.getColumnIndex(Constant.clientCode)));
                    indexContact.setSurname(cursor.getString(cursor.getColumnIndex(Constant.surname)));
                    indexContact.setOtherNames(cursor.getString(cursor.getColumnIndex(Constant.otherNames)));
                    indexContact.setAge(cursor.getInt(cursor.getColumnIndex(Constant.age)));
                    indexContact.setGender(cursor.getString(cursor.getColumnIndex(Constant.gender)));
                    indexContact.setAddress(cursor.getString(cursor.getColumnIndex(Constant.address)));
                    indexContact.setPhone(cursor.getString(cursor.getColumnIndex(Constant.phone)));
                    indexContact.setRelationship(cursor.getString(cursor.getColumnIndex(Constant.relation)));
                    indexContact.setGbv(cursor.getString(cursor.getColumnIndex(Constant.gbv)));
                    indexContact.setDurationPartner(cursor.getInt(cursor.getColumnIndex(Constant.durationPartner)));
                    indexContact.setPhoneTracking(cursor.getString(cursor.getColumnIndex(Constant.phoneTracking)));
                    indexContact.setHomeTracking(cursor.getString(cursor.getColumnIndex(Constant.visitTracking)));
                    indexContact.setOutcome(cursor.getString(cursor.getColumnIndex(Constant.outcome)));
                    indexContact.setHivStatus(cursor.getString(cursor.getColumnIndex(Constant.hivStatus)));
                    indexContact.setDateHivTest(cursor.getString(cursor.getColumnIndex(Constant.dateHivTest)));
                    indexContact.setLinkCare(cursor.getString(cursor.getColumnIndex(Constant.linkCare)));
                    indexContact.setPartnerNotification(cursor.getString(cursor.getColumnIndex(Constant.partnerNotification)));
                    indexContact.setModeNotification(cursor.getString(cursor.getColumnIndex(Constant.modeNotification)));
                    indexContact.setServiceProvided(cursor.getString(cursor.getColumnIndex(Constant.serviceProvided)));
                    indexContacts.add(indexContact);
                } while ((cursor.moveToNext()));
            }

        } catch (Exception e) {

        }
        return indexContacts;
    }

    public Hts getHtsId(long htsIds) {
        String selectQuery = "SELECT  * FROM " + Constant.TABLE_HTS + " WHERE  " + Constant.htsId + "=" + htsIds;
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Hts hts = new Hts();
        try {
            if (cursor.moveToFirst()) {
                hts.setHtsId(cursor.getLong(cursor.getColumnIndex(Constant.htsId)));
            }
            db.close();
            cursor.close();
        } catch (Exception e) {

        }
        return hts;
    }


    public ArrayList<IndexContact> getIndexContact1(long htsId1) {
        ArrayList<IndexContact> indexContacts = new ArrayList<>();
        try {
            String selectQuery = "SELECT  * FROM " + Constant.TABLE_INDEX_CONTACT + " WHERE " + Constant.htsId + "=" + htsId1;
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    IndexContact indexContact = new IndexContact();
                    Hts2 hts2 = new Hts2();
                    hts2.setHtsId(cursor.getLong(cursor.getColumnIndex(Constant.htsId)));
                    indexContact.setHts(hts2);
                    indexContact.setFacilityId(cursor.getLong(cursor.getColumnIndex(Constant.facilityId)));
                    indexContact.setContactType(cursor.getString(cursor.getColumnIndex(Constant.contactType)));
                    indexContact.setDeviceconfigId(cursor.getLong(cursor.getColumnIndex(Constant.deviceId)));
                    indexContact.setIndexContactCode(cursor.getString(cursor.getColumnIndex(Constant.indexContactCode)));
                    indexContact.setClientCode(cursor.getString(cursor.getColumnIndex(Constant.clientCode)));
                    indexContact.setSurname(cursor.getString(cursor.getColumnIndex(Constant.surname)));
                    indexContact.setOtherNames(cursor.getString(cursor.getColumnIndex(Constant.otherNames)));
                    indexContact.setAge(cursor.getInt(cursor.getColumnIndex(Constant.age)));
                    indexContact.setGender(cursor.getString(cursor.getColumnIndex(Constant.gender)));
                    indexContact.setAddress(cursor.getString(cursor.getColumnIndex(Constant.address)));
                    indexContact.setPhone(cursor.getString(cursor.getColumnIndex(Constant.phone)));
                    indexContact.setRelationship(cursor.getString(cursor.getColumnIndex(Constant.relation)));
                    indexContact.setGbv(cursor.getString(cursor.getColumnIndex(Constant.gbv)));
                    indexContact.setDurationPartner(cursor.getInt(cursor.getColumnIndex(Constant.durationPartner)));
                    indexContact.setPhoneTracking(cursor.getString(cursor.getColumnIndex(Constant.phoneTracking)));
                    indexContact.setHomeTracking(cursor.getString(cursor.getColumnIndex(Constant.visitTracking)));
                    indexContact.setOutcome(cursor.getString(cursor.getColumnIndex(Constant.outcome)));
                    indexContact.setHivStatus(cursor.getString(cursor.getColumnIndex(Constant.hivStatus)));
                    indexContact.setDateHivTest(cursor.getString(cursor.getColumnIndex(Constant.dateHivTest)));
                    indexContact.setLinkCare(cursor.getString(cursor.getColumnIndex(Constant.linkCare)));
                    indexContact.setPartnerNotification(cursor.getString(cursor.getColumnIndex(Constant.partnerNotification)));
                    indexContact.setModeNotification(cursor.getString(cursor.getColumnIndex(Constant.modeNotification)));
                    indexContact.setServiceProvided(cursor.getString(cursor.getColumnIndex(Constant.serviceProvided)));
                    indexContacts.add(indexContact);
                } while ((cursor.moveToNext()));

            }
        } catch (Exception e) {
            Log.v("EXCEPTION ", e.getMessage());
        }
        return indexContacts;
    }

    public long insertIndexContact(IndexContact indexContact) {
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        long id = 0;
        try {
            values.put(Constant.htsId, indexContact.getHts().getHtsId());
            values.put(Constant.deviceId, indexContact.getDeviceconfigId());
            values.put(Constant.facilityId, indexContact.getFacilityId());
            values.put(Constant.indexContactCode, indexContact.getIndexContactCode());
            values.put(Constant.clientCode, indexContact.getClientCode());
            values.put(Constant.contactType, indexContact.getContactType());
            values.put(Constant.surname, indexContact.getSurname());
            values.put(Constant.otherNames, indexContact.getOtherNames());
            values.put(Constant.age, indexContact.getAge());
            values.put(Constant.gender, indexContact.getGender());
            values.put(Constant.address, indexContact.getAddress());
            values.put(Constant.phone, indexContact.getPhone());
            values.put(Constant.relation, indexContact.getRelationship());
            values.put(Constant.gbv, indexContact.getGbv());
            values.put(Constant.durationPartner, indexContact.getDurationPartner());
            values.put(Constant.phoneTracking, indexContact.getPhoneTracking());
            values.put(Constant.outcome, indexContact.getOutcome());//month/day
            values.put(Constant.dateHivTest, indexContact.getDateHivTest());
            values.put(Constant.hivStatus, indexContact.getHivStatus());
            values.put(Constant.linkCare, indexContact.getLinkCare());
            values.put(Constant.partnerNotification, indexContact.getPartnerNotification());
            values.put(Constant.modeNotification, indexContact.getModeNotification());
            values.put(Constant.serviceProvided, indexContact.getServiceProvided());
            id = db.insert(Constant.TABLE_INDEX_CONTACT, null, values);
            db.close();
        } catch (Exception e) {

        }
        return id;
    }

    public int countPages1(long htsIds) {
        int count = 0;
        try {
            String query = "Select * FROM " + Constant.TABLE_INDEX_CONTACT + " WHERE " + Constant.htsId + " = " + htsIds;
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            count = cursor.getCount();
        } catch (Exception e) {

        }
        return count;
    }


    public int countPages(long htsIds) {
        int count = 0;
        try {
            String query = "Select * FROM " + Constant.TABLE_INDEX_CONTACT + " WHERE " + Constant.htsId + " = " + htsIds;
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    count++;
                } while (cursor.moveToNext());

            }
            db.close();
            cursor.close();
        } catch (Exception e) {

        }
        return count;
    }
}
