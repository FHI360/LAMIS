package org.fhi360.lamis.mobile.lite.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
import org.fhi360.lamis.mobile.lite.Domains.Hts;
import org.fhi360.lamis.mobile.lite.Domains.Hts2;
import org.fhi360.lamis.mobile.lite.Domains.IndexHelper;
import org.fhi360.lamis.mobile.lite.Domains.Patient;
import org.fhi360.lamis.mobile.lite.Utils.Constant;
import org.fhi360.lamis.mobile.lite.Utils.SettingConfig;

import java.util.ArrayList;
import java.util.List;

public class HtsDAO {
    private Context context;
    private SQLiteOpenHelper lAMISLiteDb;

    public HtsDAO(Context context) {
        this.context = context;
    }

    public ArrayList<Hts> getAllContact() {
        ArrayList<Hts> htsList = new ArrayList<>();
        try {
            String selectQuery = "SELECT  * FROM " + Constant.TABLE_HTS + " ORDER BY " + Constant.surname;
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Hts hts1 = new Hts();
                    hts1.setSurname(cursor.getString(cursor.getColumnIndex(Constant.surname)));
                    hts1.setOtherNames(cursor.getString(cursor.getColumnIndex(Constant.otherNames)));
                    hts1.setFacilityName(cursor.getString(cursor.getColumnIndex(Constant.facilityName)));
                    hts1.setAddress(cursor.getString(cursor.getColumnIndex(Constant.address)));
                    hts1.setAge(cursor.getInt(cursor.getColumnIndex(Constant.age)));
                    hts1.setDateVisit(cursor.getString(cursor.getColumnIndex(Constant.dateVisit)));
                    hts1.setPhone(cursor.getString(cursor.getColumnIndex(Constant.phone)));
                    hts1.setGender(cursor.getString(cursor.getColumnIndex(Constant.gender)));
                    htsList.add(hts1);
                } while (cursor.moveToNext());
            }
            db.close();
            cursor.close();
        } catch (Exception e) {

        }
        return htsList;
    }


    public long addTestResult(Hts hts) {
        long id = 0;
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constant.stateId, hts.getStateId());
            values.put(Constant.lgaId, hts.getLgaId());
            values.put(Constant.facilityId, hts.getFacilityId());
            values.put(Constant.facilityName, hts.getFacilityName());
            values.put(Constant.camCode, hts.getCamCode());
            values.put(Constant.dateVisit, hts.getDateVisit());
            values.put(Constant.clientCode, hts.getClientCode());
            values.put(Constant.referredFrom, hts.getReferredFrom());
            values.put(Constant.testingSetting, hts.getTestingSetting());
            values.put(Constant.surname, hts.getSurname());
            values.put(Constant.otherNames, hts.getOtherNames());
            values.put(Constant.dateBirth, hts.getDateBirth());
            values.put(Constant.age, hts.getAge());
            values.put(Constant.ageUnit, hts.getAgeUnit());//month/day
            values.put(Constant.ageUnit, hts.getAgeUnit());//month/day
            values.put(Constant.assessmentId, hts.getAssessmentId());
            values.put(Constant.deviceId, hts.getDeviceconfigId());
            values.put(Constant.address, hts.getAddress());
            values.put(Constant.phone, hts.getPhone());
            values.put(Constant.gender, hts.getGender());
            values.put(Constant.firstTimeVisit, hts.getFirstTimeVisit());
            values.put(Constant.state, hts.getState());
            values.put(Constant.lga, hts.getLga());
            values.put(Constant.maritalStatus, hts.getMaritalStatus());
            values.put(Constant.numChildren, hts.getNumChildren());
            values.put(Constant.numWives, hts.getNumWives());
            values.put(Constant.typeCounseling, hts.getTypeCounseling());
            values.put(Constant.indexClient, hts.getIndexClient());
            values.put(Constant.typeIndex, hts.getTypeIndex());
            values.put(Constant.indexClientCode, hts.getIndexClientCode());
            values.put(Constant.knowledgeAssessment1, hts.getKnowledgeAssessment1());
            values.put(Constant.knowledgeAssessment2, hts.getKnowledgeAssessment2());
            values.put(Constant.knowledgeAssessment3, hts.getKnowledgeAssessment3());
            values.put(Constant.knowledgeAssessment4, hts.getKnowledgeAssessment4());
            values.put(Constant.knowledgeAssessment5, hts.getKnowledgeAssessment5());
            values.put(Constant.knowledgeAssessment6, hts.getKnowledgeAssessment6());
            values.put(Constant.knowledgeAssessment7, hts.getKnowledgeAssessment7());
            values.put(Constant.riskAssessment1, hts.getRiskAssessment1());
            values.put(Constant.riskAssessment2, hts.getRiskAssessment2());
            values.put(Constant.riskAssessment3, hts.getRiskAssessment3());
            values.put(Constant.riskAssessment4, hts.getRiskAssessment4());
            values.put(Constant.riskAssessment5, hts.getRiskAssessment5());
            values.put(Constant.riskAssessment6, hts.getRiskAssessment6());
            values.put(Constant.tbScreening1, hts.getTbScreening1());
            values.put(Constant.tbScreening2, hts.getTbScreening2());
            values.put(Constant.tbScreening3, hts.getTbScreening3());
            values.put(Constant.tbScreening4, hts.getTbScreening4());
            values.put(Constant.stiScreening1, hts.getStiScreening1());
            values.put(Constant.stiScreening2, hts.getStiScreening2());
            values.put(Constant.stiScreening3, hts.getStiScreening3());
            values.put(Constant.stiScreening4, hts.getStiScreening4());
            values.put(Constant.stiScreening5, hts.getStiScreening5());
            values.put(Constant.hivTestResult, hts.getHivTestResult());
            values.put(Constant.testedHiv, hts.getTestedHiv());
            values.put(Constant.dateStarted, hts.getDateStarted());
            values.put(Constant.dateRegistration, hts.getDateRegistration());
            values.put(Constant.postTest1, hts.getPostTest1());
            values.put(Constant.postTest2, hts.getPostTest2());
            values.put(Constant.postTest3, hts.getPostTest3());
            values.put(Constant.postTest4, hts.getPostTest4());
            values.put(Constant.postTest5, hts.getPostTest5());
            values.put(Constant.postTest6, hts.getPostTest6());
            values.put(Constant.postTest7, hts.getPostTest7());
            values.put(Constant.postTest8, hts.getPostTest8());
            values.put(Constant.postTest9, hts.getPostTest9());
            values.put(Constant.postTest10, hts.getPostTest10());
            values.put(Constant.postTest11, hts.getPostTest11());
            values.put(Constant.postTest12, hts.getPostTest12());
            values.put(Constant.postTest13, hts.getPostTest13());
            values.put(Constant.postTest14, hts.getPostTest14());
            values.put(Constant.syphilisTestResult, hts.getSyphilisTestResult());
            values.put(Constant.hepatitisbTestResult, hts.getHepatitisbTestResult());
            values.put(Constant.hepatitiscTestResult, hts.getHepatitiscTestResult());
            values.put(Constant.note, hts.getNote());
            values.put(Constant.artReferred, hts.getArtReferred());
            values.put(Constant.tbReferred, hts.getTbReferred());
            values.put(Constant.stiReferred, hts.getStiReferred());
            values.put(Constant.uploaded, hts.getUploaded());
            values.put(Constant.timeUploaded, hts.getTimeUploaded() + "");
            values.put(Constant.latitude, hts.getLatitude() + "");
            values.put(Constant.longitude, hts.getLongitude() + "");
            values.put(Constant.uploaded, 0);
            values.put(Constant.nextKinName, hts.getNextKinName());
            values.put(Constant.nextKinPhone, hts.getNextKinPhone());
            values.put(Constant.nextKinAddress, hts.getNextKinAddress());
            values.put(Constant.nextKinDescriptiveAddress, hts.getNextKinDescriptiveAddress());
            values.put(Constant.nextKinStreet, hts.getNextKinStreet());
            values.put(Constant.nextKinCity, hts.getNextKinCity());
            values.put(Constant.nextKinLandmark, hts.getNextKinLandmark());
            id = db.insert(Constant.TABLE_HTS, null, values);
            db.close();
        } catch (Exception e) {

        }

        return id;
    }


    public List<Hts> syncData() {
        List<Hts> htsList = new ArrayList<>();
        try {
            String selectQuery = "SELECT  * FROM " + Constant.TABLE_HTS;
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Hts hts = new Hts();
                    hts.setHtsId(cursor.getLong(cursor.getColumnIndex(Constant.htsId)));
                    hts.setAssessmentId(cursor.getLong(cursor.getColumnIndex(Constant.assessmentId)));
                    hts.setDeviceconfigId(cursor.getLong(cursor.getColumnIndex(Constant.deviceId)));
                    hts.setStateId(cursor.getLong(cursor.getColumnIndex(Constant.stateId)));
                    hts.setHospitalNum(cursor.getString(cursor.getColumnIndex(Constant.hospitalNum)));
                    hts.setLgaId(cursor.getLong(cursor.getColumnIndex(Constant.lgaId)));
                    hts.setFacilityId(cursor.getLong(cursor.getColumnIndex(Constant.facilityId)));
                    hts.setFacilityName(cursor.getString(cursor.getColumnIndex(Constant.facilityName)));
                    hts.setCamCode(cursor.getString(cursor.getColumnIndex(Constant.camCode)));
                    hts.setDateVisit(cursor.getString(cursor.getColumnIndex(Constant.dateVisit)));
                    hts.setClientCode(cursor.getString(cursor.getColumnIndex(Constant.clientCode)));
                    hts.setReferredFrom(cursor.getString(cursor.getColumnIndex(Constant.referredFrom)));
                    hts.setTestingSetting(cursor.getString(cursor.getColumnIndex(Constant.testingSetting)));
                    hts.setSurname(cursor.getString(cursor.getColumnIndex(Constant.surname)));
                    hts.setOtherNames(cursor.getString(cursor.getColumnIndex(Constant.otherNames)));
                    hts.setDateBirth(cursor.getString(cursor.getColumnIndex(Constant.dateBirth)));
                    hts.setAge(cursor.getInt(cursor.getColumnIndex(Constant.age)));
                    hts.setAgeUnit(cursor.getString(cursor.getColumnIndex(Constant.ageUnit)));
                    hts.setPhone(cursor.getString(cursor.getColumnIndex(Constant.phone)));
                    hts.setAddress(cursor.getString(cursor.getColumnIndex(Constant.address)));
                    hts.setGender(cursor.getString(cursor.getColumnIndex(Constant.gender)));
                    hts.setFirstTimeVisit(cursor.getString(cursor.getColumnIndex(Constant.firstTimeVisit)));
                    hts.setHospitalNum(cursor.getString(cursor.getColumnIndex(Constant.hospitalNum)));
                    hts.setState(cursor.getString(cursor.getColumnIndex(Constant.state)));
                    hts.setLga(cursor.getString(cursor.getColumnIndex(Constant.lga)));
                    hts.setMaritalStatus(cursor.getString(cursor.getColumnIndex(Constant.maritalStatus)));
                    hts.setNumChildren(cursor.getInt(cursor.getColumnIndex(Constant.numChildren)));
                    hts.setNumWives(cursor.getInt(cursor.getColumnIndex(Constant.numWives)));
                    hts.setTypeCounseling(cursor.getString(cursor.getColumnIndex(Constant.typeCounseling)));
                    hts.setIndexClient(cursor.getString(cursor.getColumnIndex(Constant.indexClient)));
                    hts.setNotificationCounseling(cursor.getString(cursor.getColumnIndex(Constant.notificationCounseling)));
                    hts.setPartnerNotification(cursor.getString(cursor.getColumnIndex(Constant.agreeNotification)));
                    hts.setNumberPartner(cursor.getInt(cursor.getColumnIndex(Constant.numberPartner)));
                    hts.setTypeIndex(cursor.getString(cursor.getColumnIndex(Constant.typeIndex)));
                    hts.setIndexClientCode(cursor.getString(cursor.getColumnIndex(Constant.indexClientCode)));
                    hts.setKnowledgeAssessment1(cursor.getInt(cursor.getColumnIndex(Constant.knowledgeAssessment1)));
                    hts.setKnowledgeAssessment2(cursor.getInt(cursor.getColumnIndex(Constant.knowledgeAssessment2)));
                    hts.setKnowledgeAssessment3(cursor.getInt(cursor.getColumnIndex(Constant.knowledgeAssessment3)));
                    hts.setKnowledgeAssessment4(cursor.getInt(cursor.getColumnIndex(Constant.knowledgeAssessment4)));
                    hts.setKnowledgeAssessment5(cursor.getInt(cursor.getColumnIndex(Constant.knowledgeAssessment5)));
                    hts.setKnowledgeAssessment6(cursor.getInt(cursor.getColumnIndex(Constant.knowledgeAssessment6)));
                    hts.setKnowledgeAssessment7(cursor.getInt(cursor.getColumnIndex(Constant.knowledgeAssessment7)));
                    hts.setRiskAssessment1(cursor.getInt(cursor.getColumnIndex(Constant.riskAssessment1)));
                    hts.setRiskAssessment2(cursor.getInt(cursor.getColumnIndex(Constant.riskAssessment2)));
                    hts.setRiskAssessment3(cursor.getInt(cursor.getColumnIndex(Constant.riskAssessment3)));
                    hts.setRiskAssessment4(cursor.getInt(cursor.getColumnIndex(Constant.riskAssessment4)));
                    hts.setRiskAssessment5(cursor.getInt(cursor.getColumnIndex(Constant.riskAssessment5)));
                    hts.setRiskAssessment6(cursor.getInt(cursor.getColumnIndex(Constant.riskAssessment6)));
                    hts.setTbScreening1(cursor.getInt(cursor.getColumnIndex(Constant.tbScreening1)));
                    hts.setTbScreening2(cursor.getInt(cursor.getColumnIndex(Constant.tbScreening2)));
                    hts.setTbScreening3(cursor.getInt(cursor.getColumnIndex(Constant.tbScreening3)));
                    hts.setTbScreening4(cursor.getInt(cursor.getColumnIndex(Constant.tbScreening4)));
                    hts.setStiScreening1(cursor.getInt(cursor.getColumnIndex(Constant.stiScreening1)));
                    hts.setStiScreening2(cursor.getInt(cursor.getColumnIndex(Constant.stiScreening2)));
                    hts.setStiScreening3(cursor.getInt(cursor.getColumnIndex(Constant.stiScreening3)));
                    hts.setStiScreening4(cursor.getInt(cursor.getColumnIndex(Constant.stiScreening4)));
                    hts.setStiScreening5(cursor.getInt(cursor.getColumnIndex(Constant.stiScreening5)));
                    hts.setHivTestResult(cursor.getString(cursor.getColumnIndex(Constant.hivTestResult)));
                    hts.setLongitude(cursor.getFloat(cursor.getColumnIndex(Constant.longitude)));
                    hts.setLatitude(cursor.getFloat(cursor.getColumnIndex(Constant.latitude)));
                    hts.setTestedHiv(cursor.getString(cursor.getColumnIndex(Constant.testedHiv)));
                    hts.setPostTest1(cursor.getInt(cursor.getColumnIndex(Constant.postTest1)));
                    hts.setPostTest2(cursor.getInt(cursor.getColumnIndex(Constant.postTest2)));
                    hts.setPostTest3(cursor.getInt(cursor.getColumnIndex(Constant.postTest3)));
                    hts.setPostTest4(cursor.getInt(cursor.getColumnIndex(Constant.postTest4)));
                    hts.setPostTest5(cursor.getInt(cursor.getColumnIndex(Constant.postTest5)));
                    hts.setPostTest6(cursor.getInt(cursor.getColumnIndex(Constant.postTest6)));
                    hts.setPostTest7(cursor.getInt(cursor.getColumnIndex(Constant.postTest7)));
                    hts.setPostTest8(cursor.getInt(cursor.getColumnIndex(Constant.postTest8)));
                    hts.setPostTest9(cursor.getInt(cursor.getColumnIndex(Constant.postTest9)));
                    hts.setPostTest10(cursor.getInt(cursor.getColumnIndex(Constant.postTest10)));
                    hts.setPostTest11(cursor.getInt(cursor.getColumnIndex(Constant.postTest11)));
                    hts.setPostTest12(cursor.getInt(cursor.getColumnIndex(Constant.postTest12)));
                    hts.setPostTest13(cursor.getInt(cursor.getColumnIndex(Constant.postTest13)));
                    hts.setPostTest14(cursor.getInt(cursor.getColumnIndex(Constant.postTest14)));
                    hts.setSyphilisTestResult(cursor.getString(cursor.getColumnIndex(Constant.syphilisTestResult)));
                    hts.setHepatitisbTestResult(cursor.getString(cursor.getColumnIndex(Constant.hepatitisbTestResult)));
                    hts.setHepatitiscTestResult(cursor.getString(cursor.getColumnIndex(Constant.hepatitiscTestResult)));
                    hts.setNote(cursor.getString(cursor.getColumnIndex(Constant.note)));
                    hts.setStiReferred(cursor.getString(cursor.getColumnIndex(Constant.stiReferred)));
                    hts.setArtReferred(cursor.getString(cursor.getColumnIndex(Constant.artReferred)));
                    hts.setUploaded(cursor.getInt(cursor.getColumnIndex(Constant.uploaded)));
                    hts.setDateRegistration(cursor.getString(cursor.getColumnIndex(Constant.dateRegistration)));
                    hts.setDateStarted(cursor.getString(cursor.getColumnIndex(Constant.dateStarted)));
                    hts.setNextKinName(hts.getNextKinName());
                    hts.setNextKinPhone(hts.getNextKinPhone());
                    hts.setNextKinAddress(hts.getNextKinAddress());
                    hts.setCamCode(hts.getCamCode());
                    hts.setNextKinDescriptiveAddress(hts.getNextKinDescriptiveAddress());
                    hts.setNextKinStreet(hts.getNextKinStreet());
                    hts.setNextKinCity(hts.getNextKinCity());
                    hts.setNextKinLandmark(hts.getNextKinLandmark());
                    htsList.add(hts);
                } while ((cursor.moveToNext()));
            }
            db.close();
            cursor.close();
        } catch (Exception e) {

        }
        return htsList;
    }


    public Hts getData(long htsIds) {
        String selectQuery = "SELECT  * FROM " + Constant.TABLE_HTS + " WHERE  " + Constant.htsId + "=" + htsIds;
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Hts hts = new Hts();
        try {
            if (cursor.moveToFirst()) {
                hts.setHtsId(cursor.getLong(cursor.getColumnIndex(Constant.htsId)));
                hts.setDeviceconfigId(cursor.getLong(cursor.getColumnIndex(Constant.deviceId)));
                hts.setStateId(cursor.getLong(cursor.getColumnIndex(Constant.stateId)));
                hts.setHospitalNum(cursor.getString(cursor.getColumnIndex(Constant.hospitalNum)));
                hts.setLgaId(cursor.getLong(cursor.getColumnIndex(Constant.lgaId)));
                hts.setCamCode(cursor.getString(cursor.getColumnIndex(Constant.camCode)));
                hts.setFacilityId(cursor.getLong(cursor.getColumnIndex(Constant.facilityId)));
                hts.setFacilityName(cursor.getString(cursor.getColumnIndex(Constant.facilityName)));
                hts.setDateVisit(cursor.getString(cursor.getColumnIndex(Constant.dateVisit)));
                hts.setClientCode(cursor.getString(cursor.getColumnIndex(Constant.clientCode)));
                hts.setReferredFrom(cursor.getString(cursor.getColumnIndex(Constant.referredFrom)));
                hts.setTestingSetting(cursor.getString(cursor.getColumnIndex(Constant.testingSetting)));
                hts.setSurname(cursor.getString(cursor.getColumnIndex(Constant.surname)));
                hts.setOtherNames(cursor.getString(cursor.getColumnIndex(Constant.otherNames)));
                hts.setDateBirth(cursor.getString(cursor.getColumnIndex(Constant.dateBirth)));
                hts.setAge(cursor.getInt(cursor.getColumnIndex(Constant.age)));
                hts.setAgeUnit(cursor.getString(cursor.getColumnIndex(Constant.ageUnit)));
                hts.setPhone(cursor.getString(cursor.getColumnIndex(Constant.phone)));
                hts.setAddress(cursor.getString(cursor.getColumnIndex(Constant.address)));
                hts.setGender(cursor.getString(cursor.getColumnIndex(Constant.gender)));
                hts.setFirstTimeVisit(cursor.getString(cursor.getColumnIndex(Constant.firstTimeVisit)));
                hts.setHospitalNum(cursor.getString(cursor.getColumnIndex(Constant.hospitalNum)));
                hts.setState(cursor.getString(cursor.getColumnIndex(Constant.state)));
                hts.setLga(cursor.getString(cursor.getColumnIndex(Constant.lga)));
                hts.setMaritalStatus(cursor.getString(cursor.getColumnIndex(Constant.maritalStatus)));
                hts.setNumChildren(cursor.getInt(cursor.getColumnIndex(Constant.numChildren)));
                hts.setNumWives(cursor.getInt(cursor.getColumnIndex(Constant.numWives)));
                hts.setTypeCounseling(cursor.getString(cursor.getColumnIndex(Constant.typeCounseling)));
                hts.setIndexClient(cursor.getString(cursor.getColumnIndex(Constant.indexClient)));
                hts.setNotificationCounseling(cursor.getString(cursor.getColumnIndex(Constant.notificationCounseling)));
                hts.setPartnerNotification(cursor.getString(cursor.getColumnIndex(Constant.agreeNotification)));
                hts.setNumberPartner(cursor.getInt(cursor.getColumnIndex(Constant.numberPartner)));
                hts.setTypeIndex(cursor.getString(cursor.getColumnIndex(Constant.typeIndex)));
                hts.setIndexClientCode(cursor.getString(cursor.getColumnIndex(Constant.indexClientCode)));
                hts.setKnowledgeAssessment1(cursor.getInt(cursor.getColumnIndex(Constant.knowledgeAssessment1)));
                hts.setKnowledgeAssessment2(cursor.getInt(cursor.getColumnIndex(Constant.knowledgeAssessment2)));
                hts.setKnowledgeAssessment3(cursor.getInt(cursor.getColumnIndex(Constant.knowledgeAssessment3)));
                hts.setKnowledgeAssessment4(cursor.getInt(cursor.getColumnIndex(Constant.knowledgeAssessment4)));
                hts.setKnowledgeAssessment5(cursor.getInt(cursor.getColumnIndex(Constant.knowledgeAssessment5)));
                hts.setKnowledgeAssessment6(cursor.getInt(cursor.getColumnIndex(Constant.knowledgeAssessment6)));
                hts.setKnowledgeAssessment7(cursor.getInt(cursor.getColumnIndex(Constant.knowledgeAssessment7)));
                hts.setRiskAssessment1(cursor.getInt(cursor.getColumnIndex(Constant.riskAssessment1)));
                hts.setRiskAssessment2(cursor.getInt(cursor.getColumnIndex(Constant.riskAssessment2)));
                hts.setRiskAssessment3(cursor.getInt(cursor.getColumnIndex(Constant.riskAssessment3)));
                hts.setRiskAssessment4(cursor.getInt(cursor.getColumnIndex(Constant.riskAssessment4)));
                hts.setRiskAssessment5(cursor.getInt(cursor.getColumnIndex(Constant.riskAssessment5)));
                hts.setRiskAssessment6(cursor.getInt(cursor.getColumnIndex(Constant.riskAssessment6)));
                hts.setTbScreening1(cursor.getInt(cursor.getColumnIndex(Constant.tbScreening1)));
                hts.setTbScreening2(cursor.getInt(cursor.getColumnIndex(Constant.tbScreening2)));
                hts.setTbScreening3(cursor.getInt(cursor.getColumnIndex(Constant.tbScreening3)));
                hts.setTbScreening4(cursor.getInt(cursor.getColumnIndex(Constant.tbScreening4)));
                hts.setStiScreening1(cursor.getInt(cursor.getColumnIndex(Constant.stiScreening1)));
                hts.setStiScreening2(cursor.getInt(cursor.getColumnIndex(Constant.stiScreening2)));
                hts.setStiScreening3(cursor.getInt(cursor.getColumnIndex(Constant.stiScreening3)));
                hts.setStiScreening4(cursor.getInt(cursor.getColumnIndex(Constant.stiScreening4)));
                hts.setStiScreening5(cursor.getInt(cursor.getColumnIndex(Constant.stiScreening5)));
                hts.setHivTestResult(cursor.getString(cursor.getColumnIndex(Constant.hivTestResult)));
                hts.setLongitude(cursor.getFloat(cursor.getColumnIndex(Constant.longitude)));
                hts.setLatitude(cursor.getFloat(cursor.getColumnIndex(Constant.latitude)));
                hts.setTestedHiv(cursor.getString(cursor.getColumnIndex(Constant.testedHiv)));
                hts.setPostTest1(cursor.getInt(cursor.getColumnIndex(Constant.postTest1)));
                hts.setPostTest2(cursor.getInt(cursor.getColumnIndex(Constant.postTest2)));
                hts.setPostTest3(cursor.getInt(cursor.getColumnIndex(Constant.postTest3)));
                hts.setPostTest4(cursor.getInt(cursor.getColumnIndex(Constant.postTest4)));
                hts.setPostTest5(cursor.getInt(cursor.getColumnIndex(Constant.postTest5)));
                hts.setPostTest6(cursor.getInt(cursor.getColumnIndex(Constant.postTest6)));
                hts.setPostTest7(cursor.getInt(cursor.getColumnIndex(Constant.postTest7)));
                hts.setPostTest8(cursor.getInt(cursor.getColumnIndex(Constant.postTest8)));
                hts.setPostTest9(cursor.getInt(cursor.getColumnIndex(Constant.postTest9)));
                hts.setPostTest10(cursor.getInt(cursor.getColumnIndex(Constant.postTest10)));
                hts.setPostTest11(cursor.getInt(cursor.getColumnIndex(Constant.postTest11)));
                hts.setPostTest12(cursor.getInt(cursor.getColumnIndex(Constant.postTest12)));
                hts.setPostTest13(cursor.getInt(cursor.getColumnIndex(Constant.postTest13)));
                hts.setPostTest14(cursor.getInt(cursor.getColumnIndex(Constant.postTest14)));
                hts.setSyphilisTestResult(cursor.getString(cursor.getColumnIndex(Constant.syphilisTestResult)));
                hts.setHepatitisbTestResult(cursor.getString(cursor.getColumnIndex(Constant.hepatitisbTestResult)));
                hts.setHepatitiscTestResult(cursor.getString(cursor.getColumnIndex(Constant.hepatitiscTestResult)));
                hts.setNote(cursor.getString(cursor.getColumnIndex(Constant.note)));
                hts.setStiReferred(cursor.getString(cursor.getColumnIndex(Constant.stiReferred)));
                hts.setArtReferred(cursor.getString(cursor.getColumnIndex(Constant.artReferred)));
                hts.setUploaded(cursor.getInt(cursor.getColumnIndex(Constant.uploaded)));
                hts.setDateRegistration(cursor.getString(cursor.getColumnIndex(Constant.dateRegistration)));
                hts.setDateStarted(cursor.getString(cursor.getColumnIndex(Constant.dateStarted)));
                hts.setTimeUploaded(cursor.getString(cursor.getColumnIndex(Constant.timeUploaded)));
            }
            db.close();
            cursor.close();
        } catch (Exception e) {

        }
        return hts;
    }


    public void updateTestResult(Hts hts) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constant.stateId, hts.getStateId());
            values.put(Constant.lgaId, hts.getLgaId());
            values.put(Constant.facilityId, hts.getFacilityId());
            values.put(Constant.facilityName, hts.getFacilityName());
            values.put(Constant.camCode, hts.getCamCode());
            values.put(Constant.dateVisit, hts.getDateVisit());
            values.put(Constant.clientCode, hts.getClientCode());
            values.put(Constant.referredFrom, hts.getReferredFrom());
            values.put(Constant.testingSetting, hts.getTestingSetting());
            values.put(Constant.surname, hts.getSurname());
            values.put(Constant.otherNames, hts.getOtherNames());
            values.put(Constant.dateBirth, hts.getDateBirth());
            values.put(Constant.age, hts.getAge());
            values.put(Constant.ageUnit, hts.getAgeUnit());//month/day
            values.put(Constant.phone, hts.getPhone());
            values.put(Constant.deviceId, hts.getDeviceconfigId());
            values.put(Constant.address, hts.getAddress());
            values.put(Constant.gender, hts.getGender());
            values.put(Constant.firstTimeVisit, hts.getFirstTimeVisit());
            values.put(Constant.state, hts.getState());
            values.put(Constant.lga, hts.getLga());
            values.put(Constant.maritalStatus, hts.getMaritalStatus());
            values.put(Constant.numChildren, hts.getNumChildren());
            values.put(Constant.numWives, hts.getNumWives());
            values.put(Constant.typeCounseling, hts.getTypeCounseling());
            values.put(Constant.indexClient, hts.getIndexClient());
            values.put(Constant.typeIndex, hts.getTypeIndex());
            values.put(Constant.indexClientCode, hts.getIndexClientCode());
            values.put(Constant.knowledgeAssessment1, hts.getKnowledgeAssessment1());
            values.put(Constant.knowledgeAssessment2, hts.getKnowledgeAssessment2());
            values.put(Constant.knowledgeAssessment3, hts.getKnowledgeAssessment3());
            values.put(Constant.knowledgeAssessment4, hts.getKnowledgeAssessment4());
            values.put(Constant.knowledgeAssessment5, hts.getKnowledgeAssessment5());
            values.put(Constant.knowledgeAssessment6, hts.getKnowledgeAssessment6());
            values.put(Constant.knowledgeAssessment7, hts.getKnowledgeAssessment7());
            values.put(Constant.riskAssessment1, hts.getRiskAssessment1());
            values.put(Constant.riskAssessment2, hts.getRiskAssessment2());
            values.put(Constant.riskAssessment3, hts.getRiskAssessment3());
            values.put(Constant.riskAssessment4, hts.getRiskAssessment4());
            values.put(Constant.riskAssessment5, hts.getRiskAssessment5());
            values.put(Constant.riskAssessment6, hts.getRiskAssessment6());
            values.put(Constant.tbScreening1, hts.getTbScreening1());
            values.put(Constant.tbScreening2, hts.getTbScreening2());
            values.put(Constant.tbScreening3, hts.getTbScreening3());
            values.put(Constant.tbScreening4, hts.getTbScreening4());
            values.put(Constant.stiScreening1, hts.getStiScreening1());
            values.put(Constant.stiScreening2, hts.getStiScreening2());
            values.put(Constant.stiScreening3, hts.getStiScreening3());
            values.put(Constant.stiScreening4, hts.getStiScreening4());
            values.put(Constant.stiScreening5, hts.getStiScreening5());
            values.put(Constant.hivTestResult, hts.getHivTestResult());
            values.put(Constant.testedHiv, hts.getTestedHiv());
            values.put(Constant.dateStarted, hts.getDateStarted());
            values.put(Constant.dateRegistration, hts.getDateRegistration());
            values.put(Constant.postTest1, hts.getPostTest1());
            values.put(Constant.postTest2, hts.getPostTest2());
            values.put(Constant.postTest3, hts.getPostTest3());
            values.put(Constant.postTest4, hts.getPostTest4());
            values.put(Constant.postTest5, hts.getPostTest5());
            values.put(Constant.postTest6, hts.getPostTest6());
            values.put(Constant.postTest7, hts.getPostTest7());
            values.put(Constant.postTest8, hts.getPostTest8());
            values.put(Constant.postTest9, hts.getPostTest9());
            values.put(Constant.postTest10, hts.getPostTest10());
            values.put(Constant.postTest11, hts.getPostTest11());
            values.put(Constant.postTest12, hts.getPostTest12());
            values.put(Constant.postTest13, hts.getPostTest13());
            values.put(Constant.postTest14, hts.getPostTest14());
            values.put(Constant.syphilisTestResult, hts.getSyphilisTestResult());
            values.put(Constant.hepatitisbTestResult, hts.getHepatitisbTestResult());
            values.put(Constant.hepatitiscTestResult, hts.getHepatitiscTestResult());
            values.put(Constant.note, hts.getNote());
            values.put(Constant.artReferred, hts.getArtReferred());
            values.put(Constant.tbReferred, hts.getTbReferred());
            values.put(Constant.stiReferred, hts.getStiReferred());
            values.put(Constant.uploaded, hts.getUploaded());
            values.put(Constant.timeUploaded, hts.getTimeUploaded() + "");
            values.put(Constant.latitude, hts.getLatitude() + "");
            values.put(Constant.longitude, hts.getLongitude() + "");
            db.update(Constant.TABLE_HTS, values, "hts_id = ?", new String[]{String.valueOf(hts.getHtsId())});
            db.close();
        } catch (Exception e) {

        }
    }


    public void updateClientCodeFromHtsByDeviceConfigId(String deviceConfigId) {
        String selectQuery = "SELECT * FROM " + Constant.TABLE_HTS;
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String clientCode = cursor.getString(cursor.getColumnIndex(Constant.clientCode));
                long htsId = cursor.getLong(cursor.getColumnIndex(Constant.htsId));
                int count = SettingConfig.countOccurences(clientCode, '/', 0);
                if (count == 2) {
                    //clientCode.indexOf("/", clientCode.indexOf("/") + 1);
                    int index = SettingConfig.ordinalIndexOf(clientCode, "/", 1);
                    String clientCode2 = clientCode.substring(0, index);
                    String serialNumber = clientCode.substring(index + 1);
                    clientCode = clientCode2 + "/" + deviceConfigId + "/" + serialNumber;
                    ContentValues values = new ContentValues();
                    values.put(Constant.clientCode, clientCode);
                    db.update(Constant.TABLE_HTS, values, "hts_id = ?", new String[]{String.valueOf(htsId)});
                    db.update(Constant.TABLE_INDEX_CONTACT, values, "hts_id = ?", new String[]{String.valueOf(htsId)});

                }
            } while ((cursor.moveToNext()));
        }
        cursor.close();
        db.close();

    }


    public int totalNumberOfPosivitive() {
        int count = 0;
        try {
            String query = "Select * FROM " + Constant.TABLE_HTS + " WHERE  UPPER(" + Constant.hivTestResult + ")='POSITIVE'";
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            count = cursor.getCount();
            db.close();
            cursor.close();
        } catch (Exception ignored) {

        }
        return count;
    }

    public int totalNumberInitiated() {
        int count = 0;
        try {
            String query = "Select * FROM " + Constant.TABLE_HTS + " WHERE " + Constant.dateStarted + " !='' OR " + Constant.dateStarted + " IS NOT NULL";
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            count = cursor.getCount();
            db.close();
            cursor.close();
        } catch (Exception ignored) {

        }
        return count;
    }


    public Patient findPatientHtsId(long htsIds) {
        String query = "Select * FROM " + Constant.TABLE_PATIENT + " WHERE " + Constant.htsId + " = " + htsIds;
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        Patient patient = new Patient();
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                patient.setPatientId(cursor.getLong(cursor.getColumnIndex(Constant.patientId)));

            }

            db.close();
            cursor.close();
        } catch (Exception ignored) {

        }

        return patient;
    }


    public int totalNumberOfHts() {
        int count = 0;
        try {
            String query = "Select * FROM " + Constant.TABLE_HTS;
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            count = cursor.getCount();
            db.close();
            cursor.close();
        } catch (Exception ignored) {

        }
        return count;
    }


    public boolean updateContact(IndexHelper contact) {
        boolean bol = false;
        String selectQuery = "SELECT  * FROM " + Constant.TABLE_HTS + " WHERE  hts_id=" + contact.getHtsId();
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0) {
            ContentValues values = new ContentValues();
            values.put(Constant.indexType, contact.getIndexType());
            values.put(Constant.deviceId, contact.getDeviceconfigId());
            values.put(Constant.dateConfirmedHiv, contact.getDateConfirmHivStatus());
            values.put(Constant.dateEnrolledHivCase, contact.getDateEnrolledHivCase());
            values.put(Constant.indexClientCode, contact.getClientIndexCode());
            values.put(Constant.hospitalNum, contact.getHuspitalNum());
            values.put(Constant.notificationCounseling, contact.getNotificationCounseling());
            values.put(Constant.agreeNotification, contact.getAgreeNotifications());
            values.put(Constant.numberPartner, contact.getNumberPartner());
            db.update(Constant.TABLE_HTS, values, "hts_id = ?", new String[]{String.valueOf(contact.getHtsId())});
            bol = true;
        } else {
            bol = false;
        }
        db.close();
        return bol;
    }

    public boolean updateContact1(Hts2 contact, String partnerNotifications1) {
        boolean bol = false;
        try {
            String selectQuery = "SELECT  * FROM " + Constant.TABLE_HTS + " WHERE  hts_id=" + contact.getHtsId();
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                ContentValues values = new ContentValues();
                values.put(Constant.agreeNotification, partnerNotifications1);
                db.update(Constant.TABLE_HTS, values, "hts_id = ?", new String[]{String.valueOf(contact.getHtsId())});
                bol = true;
            } else {
                bol = false;
            }
            db.close();
        } catch (Exception ignored) {

        }
        return bol;
    }

    public Integer getAutGeneratedClientCode() {
        Integer id = 0;
        String selectQuery = "SELECT * FROM " + Constant.TABLE_HTS + " ORDER BY " + Constant.htsId;
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        id = cursor.getCount();
        db.close();
        cursor.close();
        return id;
    }

    public void updateDateRegistration(String dateRegistration1, String htsIds) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constant.dateRegistration, dateRegistration1);
            db.update(Constant.TABLE_HTS, values, "hts_id = ?", new String[]{String.valueOf(htsIds)});
            db.close();
        } catch (Exception e) {

        }
    }


    public void updateStartDate(String dateStarted1, String htsIds) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constant.dateStarted, dateStarted1);
            db.update(Constant.TABLE_HTS, values, "hts_id = ?", new String[]{String.valueOf(htsIds)});
            db.close();
        } catch (Exception ignored) {

        }

    }


    public void updateHtsByDeviceConfig(String deviceconfigId1, float longit, float lat1) {
        String query = "SELECT * FROM " + Constant.TABLE_HTS + " WHERE " + Constant.deviceId + " =" + deviceconfigId1;
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ContentValues values = new ContentValues();
        try {
            if (cursor.moveToFirst()) {
                do {
                    float lon = cursor.getFloat(cursor.getColumnIndex(Constant.longitude));//end of the lon
                    float lat = cursor.getFloat(cursor.getColumnIndex(Constant.latitude));//end of the lat
                    if (lon == 0.0 && lat == 0.0) {
                        values.put(Constant.longitude, longit);
                        values.put(Constant.latitude, lat1);
                        db.update(Constant.TABLE_HTS, values, "deviceconfig_id = ?", new String[]{String.valueOf(deviceconfigId1)});
                    }
                } while ((cursor.moveToNext()));
            }
            cursor.close();
            db.close();
        } catch (Exception ignored) {

        }
    }

    public Integer getAutGeneratedClientIndexCode() {
        Integer id = 0;
        String selectQuery = "SELECT * FROM " + Constant.TABLE_HTS + " ORDER BY " + Constant.htsId;
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        id = cursor.getCount() + 1;
        db.close();
        cursor.close();
        return id;
    }

    public void updateTestResult2(Hts hts) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constant.surname, hts.getSurname());
            values.put(Constant.otherNames, hts.getOtherNames());
            values.put(Constant.dateBirth, hts.getDateBirth());
            values.put(Constant.age, hts.getAge());
            values.put(Constant.ageUnit, hts.getAgeUnit());
            values.put(Constant.gender, hts.getGender());
            values.put(Constant.maritalStatus, hts.getMaritalStatus());
            values.put(Constant.phone, hts.getPhone());
            values.put(Constant.address, hts.getAddress());
            values.put(Constant.state, hts.getState());
            values.put(Constant.lga, hts.getLga());
            values.put(Constant.dateVisit, hts.getDateVisit());
            db.update(Constant.TABLE_HTS, values, "hts_id = ?", new String[]{String.valueOf(hts.getHtsId())});
            db.close();
        } catch (Exception ignored) {

        }
    }


    public Hts getData1(long htsIds) {
        String selectQuery = "SELECT  * FROM " + Constant.TABLE_HTS + " WHERE  " + Constant.htsId + "=" + htsIds;
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Hts hts = new Hts();
        try {
            if (cursor.getCount() > 0) {
                hts.setHtsId(cursor.getLong(cursor.getColumnIndex(Constant.htsId)));
                hts.setCamCode(cursor.getString(cursor.getColumnIndex(Constant.clientCode)));
            }
            db.close();
            cursor.close();
        } catch (Exception ignored) {

        }
        return hts;
    }

}
