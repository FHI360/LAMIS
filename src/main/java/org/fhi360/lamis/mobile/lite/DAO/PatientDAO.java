package org.fhi360.lamis.mobile.lite.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
import org.fhi360.lamis.mobile.lite.Domains.Facility2;
import org.fhi360.lamis.mobile.lite.Domains.Hts;
import org.fhi360.lamis.mobile.lite.Domains.Patient;
import org.fhi360.lamis.mobile.lite.Domains.Patient2;
import org.fhi360.lamis.mobile.lite.Domains.PatientReturn;
import org.fhi360.lamis.mobile.lite.Utils.Constant;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    private Context context;
    private SQLiteOpenHelper lAMISLiteDb;

    public PatientDAO(Context context) {
        this.context = context;
    }


    public void insertPatient(Patient patient) {
        try {

            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constant.htsId, patient.getHtsId());
            values.put(Constant.deviceId, patient.getDeviceconfigId());
            values.put(Constant.facilityId, patient.getFacility().getFacilityId());
            values.put(Constant.hospitalNum, patient.getHospitalNum());
            values.put(Constant.uniqueId, patient.getUniqueId());
            values.put(Constant.surname, patient.getSurname());
            values.put(Constant.otherNames, patient.getOtherNames());
            values.put(Constant.ageUnit, patient.getAgeUnit());
            values.put(Constant.gender, patient.getGender());
            values.put(Constant.dateBirth, patient.getDateBirth());
            values.put(Constant.age, patient.getAge());
            values.put(Constant.maritalStatus, patient.getMaritalStatus());
            values.put(Constant.education, patient.getEducation());
            values.put(Constant.occupation, patient.getOccupation());
            values.put(Constant.address, patient.getAddress());
            values.put(Constant.phone, patient.getPhone());//month/day
            values.put(Constant.state, patient.getState());
            values.put(Constant.lga, patient.getLga());
            values.put(Constant.nextKin, patient.getNextKin());
            values.put(Constant.addressKin, patient.getAddressKin());
            values.put(Constant.phoneKin, patient.getPhoneKin());
            values.put(Constant.relationKin, patient.getRelationKin());
            values.put(Constant.entryPoint, patient.getEntryPoint());
            values.put(Constant.targetGroup, patient.getTargetGroup());
            values.put(Constant.dateConfirmedHiv, patient.getDateConfirmedHiv());
            values.put(Constant.tbStatus, patient.getTbStatus());
            values.put(Constant.pregnant, patient.getPregnant());
            values.put(Constant.breastfeeding, patient.getBreastfeeding());
            values.put(Constant.timeStamp, patient.getTimeStamp());
            values.put(Constant.uploaded, patient.getUploaded());
            values.put(Constant.timeUploaded, patient.getTimeUploaded());
            values.put(Constant.dateRegistration, patient.getDateRegistration());
            values.put(Constant.statusRegistration, patient.getStatusRegistration());
            values.put(Constant.uuid, patient.getUuid());
            db.insert(Constant.TABLE_PATIENT, null, values);
            db.close();
        } catch (Exception e) {

        }
    }


    public List<Patient> getAllPatient5() {
        List<Patient> patients = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM patient";
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Patient patient = new Patient();
                    patient.setPatientId(cursor.getLong(cursor.getColumnIndex(Constant.pid)));
                    Facility2 facility2 = new Facility2();
                    facility2.setFacilityId(cursor.getLong(cursor.getColumnIndex(Constant.facilityId)));
                    patient.setFacility(facility2);
                    patient.setBiometric(cursor.getInt(cursor.getColumnIndex("biometric")));
                    patient.setHtsId(cursor.getLong(cursor.getColumnIndex(Constant.htsId)));
                    patient.setDeviceconfigId(cursor.getLong(cursor.getColumnIndex(Constant.deviceId)));
                    patient.setHospitalNum(cursor.getString(cursor.getColumnIndex(Constant.hospitalNum)));
                    patient.setUniqueId(cursor.getString(cursor.getColumnIndex(Constant.uniqueId)));
                    patient.setSurname(cursor.getString(cursor.getColumnIndex(Constant.surname)));
                    patient.setOtherNames(cursor.getString(cursor.getColumnIndex(Constant.otherNames)));
                    patient.setDateBirth(cursor.getString(cursor.getColumnIndex(Constant.dateBirth)));
                    patient.setAge(cursor.getInt(cursor.getColumnIndex(Constant.age)));
                    patient.setAgeUnit(cursor.getString(cursor.getColumnIndex(Constant.ageUnit)));
                    patient.setMaritalStatus(cursor.getString(cursor.getColumnIndex(Constant.maritalStatus)));
                    patient.setOccupation(cursor.getString(cursor.getColumnIndex(Constant.occupation)));
                    patient.setEducation(cursor.getString(cursor.getColumnIndex(Constant.education)));
                    patient.setAddress(cursor.getString(cursor.getColumnIndex(Constant.address)));
                    patient.setPhone(cursor.getString(cursor.getColumnIndex(Constant.phone)));
                    patient.setState(cursor.getString(cursor.getColumnIndex(Constant.state)));
                    patient.setGender(cursor.getString(cursor.getColumnIndex(Constant.gender)));
                    patient.setLga(cursor.getString(cursor.getColumnIndex(Constant.lga)));
                    patient.setNextKin(cursor.getString(cursor.getColumnIndex(Constant.nextKin)));
                    patient.setAddressKin(cursor.getString(cursor.getColumnIndex(Constant.addressKin)));
                    patient.setPhoneKin(cursor.getString(cursor.getColumnIndex(Constant.phoneKin)));
                    patient.setRelationKin(cursor.getString(cursor.getColumnIndex(Constant.relationKin)));
                    patient.setEntryPoint(cursor.getString(cursor.getColumnIndex(Constant.entryPoint)));
                    patient.setTargetGroup(cursor.getString(cursor.getColumnIndex(Constant.targetGroup)));
                    patient.setDateConfirmedHiv(cursor.getString(cursor.getColumnIndex(Constant.dateConfirmedHiv)));
                    patient.setTbStatus(cursor.getString(cursor.getColumnIndex(Constant.tbStatus)));
                    patient.setPregnant(cursor.getInt(cursor.getColumnIndex(Constant.pregnant)));
                    patient.setBreastfeeding(cursor.getInt(cursor.getColumnIndex(Constant.breastfeeding)));
                    patient.setTimeStamp(cursor.getString(cursor.getColumnIndex(Constant.timeStamp)));
                    patient.setUploaded(cursor.getString(cursor.getColumnIndex(Constant.uploaded)));
                    patient.setTimeUploaded(cursor.getString(cursor.getColumnIndex(Constant.timeUploaded)));
                    patient.setDateRegistration(cursor.getString(cursor.getColumnIndex(Constant.dateRegistration)));
                    patient.setStatusRegistration(cursor.getString(cursor.getColumnIndex(Constant.statusRegistration)));
                    patient.setUuid(cursor.getString(cursor.getColumnIndex(Constant.uuid)));
                    patients.add(patient);
                } while ((cursor.moveToNext()));
            }
            db.close();
            cursor.close();
        } catch (Exception ignored) {

        }

        return patients;
    }

    public List<Patient> getAllPatient6() {
        List<Patient> patients = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM patient  WHERE hts_id !=0";
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Patient patient = new Patient();
                    patient.setPatientId(cursor.getLong(cursor.getColumnIndex(Constant.pid)));
                    Facility2 facility2 = new Facility2();
                    facility2.setFacilityId(cursor.getLong(cursor.getColumnIndex(Constant.facilityId)));
                    patient.setFacility(facility2);
                    patient.setHtsId(cursor.getLong(cursor.getColumnIndex(Constant.htsId)));
                    patient.setDeviceconfigId(cursor.getLong(cursor.getColumnIndex(Constant.deviceId)));
                    patient.setHospitalNum(cursor.getString(cursor.getColumnIndex(Constant.hospitalNum)));
                    patient.setUniqueId(cursor.getString(cursor.getColumnIndex(Constant.uniqueId)));
                    patient.setSurname(cursor.getString(cursor.getColumnIndex(Constant.surname)));
                    patient.setOtherNames(cursor.getString(cursor.getColumnIndex(Constant.otherNames)));
                    patient.setDateBirth(cursor.getString(cursor.getColumnIndex(Constant.dateBirth)));
                    patient.setAge(cursor.getInt(cursor.getColumnIndex(Constant.age)));
                    patient.setAgeUnit(cursor.getString(cursor.getColumnIndex(Constant.ageUnit)));
                    patient.setMaritalStatus(cursor.getString(cursor.getColumnIndex(Constant.maritalStatus)));
                    patient.setOccupation(cursor.getString(cursor.getColumnIndex(Constant.occupation)));
                    patient.setEducation(cursor.getString(cursor.getColumnIndex(Constant.education)));
                    patient.setAddress(cursor.getString(cursor.getColumnIndex(Constant.address)));
                    patient.setPhone(cursor.getString(cursor.getColumnIndex(Constant.phone)));
                    patient.setState(cursor.getString(cursor.getColumnIndex(Constant.state)));
                    patient.setGender(cursor.getString(cursor.getColumnIndex(Constant.gender)));
                    patient.setLga(cursor.getString(cursor.getColumnIndex(Constant.lga)));
                    patient.setNextKin(cursor.getString(cursor.getColumnIndex(Constant.nextKin)));
                    patient.setAddressKin(cursor.getString(cursor.getColumnIndex(Constant.addressKin)));
                    patient.setPhoneKin(cursor.getString(cursor.getColumnIndex(Constant.phoneKin)));
                    patient.setRelationKin(cursor.getString(cursor.getColumnIndex(Constant.relationKin)));
                    patient.setEntryPoint(cursor.getString(cursor.getColumnIndex(Constant.entryPoint)));
                    patient.setTargetGroup(cursor.getString(cursor.getColumnIndex(Constant.targetGroup)));
                    patient.setDateConfirmedHiv(cursor.getString(cursor.getColumnIndex(Constant.dateConfirmedHiv)));
                    patient.setTbStatus(cursor.getString(cursor.getColumnIndex(Constant.tbStatus)));
                    patient.setPregnant(cursor.getInt(cursor.getColumnIndex(Constant.pregnant)));
                    patient.setBreastfeeding(cursor.getInt(cursor.getColumnIndex(Constant.breastfeeding)));
                    patient.setTimeStamp(cursor.getString(cursor.getColumnIndex(Constant.timeStamp)));
                    patient.setUploaded(cursor.getString(cursor.getColumnIndex(Constant.uploaded)));
                    patient.setTimeUploaded(cursor.getString(cursor.getColumnIndex(Constant.timeUploaded)));
                    patient.setDateRegistration(cursor.getString(cursor.getColumnIndex(Constant.dateRegistration)));
                    patient.setStatusRegistration(cursor.getString(cursor.getColumnIndex(Constant.statusRegistration)));
                    patient.setUuid(cursor.getString(cursor.getColumnIndex(Constant.uuid)));
                    patients.add(patient);
                } while ((cursor.moveToNext()));
            }
            db.close();
            cursor.close();
        } catch (Exception ignored) {

        }

        return patients;
    }


    @SuppressLint("NewApi")
    public List<PatientReturn> getAllPatient() {
        List<PatientReturn> patients = new ArrayList<>();
        try {
            System.out.println("oyiscofirst");
            String selectQuery = "SELECT  * FROM " + Constant.TABLE_PATIENT + " WHERE hts_id !=0 and biometric!=0 ";
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    PatientReturn patient = new PatientReturn();
                    Facility2 facility2 = new Facility2();
                    facility2.setId(cursor.getLong(cursor.getColumnIndex(Constant.facilityId)));
                    patient.setFacility(facility2);
                    patient.setHtsId(cursor.getLong(cursor.getColumnIndex(Constant.htsId)));
                    Hts hts = new HtsDAO(context).getData1(cursor.getLong(cursor.getColumnIndex(Constant.htsId)));
                    if (hts != null) {
                        patient.setHospitalNum(hts.getClientCode());
                    }
                    //patient.setDeviceconfigId(cursor.getLong(cursor.getColumnIndex(Constant.deviceId)));
                    patient.setHospitalNum(cursor.getString(cursor.getColumnIndex(Constant.hospitalNum)));
                    patient.setUniqueId(cursor.getString(cursor.getColumnIndex(Constant.uniqueId)));
                    patient.setSurname(cursor.getString(cursor.getColumnIndex(Constant.surname)));
                    patient.setOtherNames(cursor.getString(cursor.getColumnIndex(Constant.otherNames)));
                    patient.setDateBirth(cursor.getString(cursor.getColumnIndex(Constant.dateBirth)));

                    //patient.setAge(cursor.getInt(cursor.getColumnIndex(Constant.age)));
                    ///System.out.println("AGEUNIT " + cursor.getString(cursor.getColumnIndex(Constant.ageUnit)));
                    patient.setGender(cursor.getString(cursor.getColumnIndex(Constant.gender)));
                    //  patient.setAgeUnit(cursor.getString(cursor.getColumnIndex(Constant.ageUnit)));
                    patient.setMaritalStatus(cursor.getString(cursor.getColumnIndex(Constant.maritalStatus)));
                    patient.setOccupation(cursor.getString(cursor.getColumnIndex(Constant.occupation)));
                    patient.setEducation(cursor.getString(cursor.getColumnIndex(Constant.education)));
                    patient.setAddress(cursor.getString(cursor.getColumnIndex(Constant.address)));
                    patient.setPhone(cursor.getString(cursor.getColumnIndex(Constant.phone)));
                    patient.setState(cursor.getString(cursor.getColumnIndex(Constant.state)));
                    patient.setLga(cursor.getString(cursor.getColumnIndex(Constant.lga)));
                    patient.setNextKin(cursor.getString(cursor.getColumnIndex(Constant.nextKin)));
                    patient.setAddressKin(cursor.getString(cursor.getColumnIndex(Constant.addressKin)));
                    patient.setPhoneKin(cursor.getString(cursor.getColumnIndex(Constant.phoneKin)));
                    patient.setRelationKin(cursor.getString(cursor.getColumnIndex(Constant.relationKin)));
                    patient.setEntryPoint(cursor.getString(cursor.getColumnIndex(Constant.entryPoint)));
                    patient.setTargetGroup(cursor.getString(cursor.getColumnIndex(Constant.targetGroup)));
                    patient.setDateConfirmedHiv(cursor.getString(cursor.getColumnIndex(Constant.dateConfirmedHiv)));
                    patient.setTbStatus(cursor.getString(cursor.getColumnIndex(Constant.tbStatus)));
                    patient.setPregnant(Boolean.parseBoolean(String.valueOf(cursor.getInt(cursor.getColumnIndex(Constant.pregnant)))));
                    patient.setBreastfeeding(Boolean.parseBoolean(String.valueOf(cursor.getInt(cursor.getColumnIndex(Constant.breastfeeding)))));
                    // patient.setTimeStamp(cursor.getString(cursor.getColumnIndex(Constant.timeStamp)));
                    // patient.setUploaded(cursor.getString(cursor.getColumnIndex(Constant.uploaded)));
                    // patient.setTimeUploaded(cursor.getString(cursor.getColumnIndex(Constant.timeUploaded)));
                    patient.setDateRegistration(cursor.getString(cursor.getColumnIndex(Constant.dateRegistration)));
                    patient.setUuid(cursor.getString(cursor.getColumnIndex(Constant.uuid)));
                    patients.add(patient);
                } while ((cursor.moveToNext()));
            }

            db.close();
            cursor.close();
        } catch (Exception e) {

        }
        return patients;
    }


    public Patient findPatientHtsId1(long patientIds) {
        String query = "Select * FROM " + Constant.TABLE_PATIENT + " WHERE " + Constant.patientId + " = " + patientIds;
        lAMISLiteDb = LAMISLiteDb.getInstance(context);
        SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
        Patient patient = new Patient();
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                patient.setPatientId(cursor.getLong(cursor.getColumnIndex(Constant.patientId)));
                Facility2 facility2 = new Facility2();
                facility2.setFacilityId(cursor.getLong(cursor.getColumnIndex(Constant.facilityId)));
                patient.setFacility(facility2);
                patient.setHtsId(cursor.getLong(cursor.getColumnIndex(Constant.htsId)));
                patient.setHospitalNum(cursor.getString(cursor.getColumnIndex(Constant.hospitalNum)));
                patient.setUniqueId(cursor.getString(cursor.getColumnIndex(Constant.uniqueId)));
                patient.setSurname(cursor.getString(cursor.getColumnIndex(Constant.surname)));
                patient.setOtherNames(cursor.getString(cursor.getColumnIndex(Constant.otherNames)));
                patient.setDateBirth(cursor.getString(cursor.getColumnIndex(Constant.dateBirth)));
                patient.setAge(cursor.getInt(cursor.getColumnIndex(Constant.age)));
                patient.setAgeUnit(cursor.getString(cursor.getColumnIndex(Constant.ageUnit)));
                patient.setMaritalStatus(cursor.getString(cursor.getColumnIndex(Constant.maritalStatus)));
                patient.setOccupation(cursor.getString(cursor.getColumnIndex(Constant.occupation)));
                patient.setEducation(cursor.getString(cursor.getColumnIndex(Constant.education)));
                patient.setAddress(cursor.getString(cursor.getColumnIndex(Constant.address)));
                patient.setPhone(cursor.getString(cursor.getColumnIndex(Constant.phone)));
                patient.setState(cursor.getString(cursor.getColumnIndex(Constant.state)));
                patient.setLga(cursor.getString(cursor.getColumnIndex(Constant.lga)));
                patient.setGender(cursor.getString(cursor.getColumnIndex(Constant.gender)));
                patient.setNextKin(cursor.getString(cursor.getColumnIndex(Constant.nextKin)));
                patient.setAddressKin(cursor.getString(cursor.getColumnIndex(Constant.addressKin)));
                patient.setPhoneKin(cursor.getString(cursor.getColumnIndex(Constant.phoneKin)));
                patient.setRelationKin(cursor.getString(cursor.getColumnIndex(Constant.relationKin)));
                patient.setEntryPoint(cursor.getString(cursor.getColumnIndex(Constant.entryPoint)));
                patient.setTargetGroup(cursor.getString(cursor.getColumnIndex(Constant.targetGroup)));
                patient.setDateConfirmedHiv(cursor.getString(cursor.getColumnIndex(Constant.dateConfirmedHiv)));
                patient.setTbStatus(cursor.getString(cursor.getColumnIndex(Constant.tbStatus)));
                patient.setPregnant(cursor.getInt(cursor.getColumnIndex(Constant.pregnant)));
                patient.setBreastfeeding(cursor.getInt(cursor.getColumnIndex(Constant.breastfeeding)));
                patient.setTimeStamp(cursor.getString(cursor.getColumnIndex(Constant.timeStamp)));
                patient.setUploaded(cursor.getString(cursor.getColumnIndex(Constant.uploaded)));
                patient.setTimeUploaded(cursor.getString(cursor.getColumnIndex(Constant.timeUploaded)));
                patient.setStatusRegistration(cursor.getString(cursor.getColumnIndex(Constant.statusRegistration)));
                patient.setDateRegistration(cursor.getString(cursor.getColumnIndex(Constant.dateRegistration)));
            }

            db.close();
            cursor.close();

        } catch (Exception e) {

        }

        return patient;
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
                Facility2 facility2 = new Facility2();
                facility2.setFacilityId(cursor.getLong(cursor.getColumnIndex(Constant.facilityId)));
                patient.setFacility(facility2);
                patient.setHtsId(cursor.getLong(cursor.getColumnIndex(Constant.htsId)));
                patient.setHospitalNum(cursor.getString(cursor.getColumnIndex(Constant.hospitalNum)));
                patient.setUniqueId(cursor.getString(cursor.getColumnIndex(Constant.uniqueId)));
                patient.setSurname(cursor.getString(cursor.getColumnIndex(Constant.surname)));
                patient.setOtherNames(cursor.getString(cursor.getColumnIndex(Constant.otherNames)));
                patient.setDateBirth(cursor.getString(cursor.getColumnIndex(Constant.dateBirth)));
                patient.setAge(cursor.getInt(cursor.getColumnIndex(Constant.age)));
                patient.setGender(cursor.getString(cursor.getColumnIndex(Constant.gender)));
                patient.setAgeUnit(cursor.getString(cursor.getColumnIndex(Constant.ageUnit)));
                patient.setMaritalStatus(cursor.getString(cursor.getColumnIndex(Constant.maritalStatus)));
                patient.setOccupation(cursor.getString(cursor.getColumnIndex(Constant.occupation)));
                patient.setEducation(cursor.getString(cursor.getColumnIndex(Constant.education)));
                patient.setAddress(cursor.getString(cursor.getColumnIndex(Constant.address)));
                patient.setPhone(cursor.getString(cursor.getColumnIndex(Constant.phone)));
                patient.setState(cursor.getString(cursor.getColumnIndex(Constant.state)));
                patient.setLga(cursor.getString(cursor.getColumnIndex(Constant.lga)));
                patient.setNextKin(cursor.getString(cursor.getColumnIndex(Constant.nextKin)));
                patient.setAddressKin(cursor.getString(cursor.getColumnIndex(Constant.addressKin)));
                patient.setPhoneKin(cursor.getString(cursor.getColumnIndex(Constant.phoneKin)));
                patient.setRelationKin(cursor.getString(cursor.getColumnIndex(Constant.relationKin)));
                patient.setEntryPoint(cursor.getString(cursor.getColumnIndex(Constant.entryPoint)));
                patient.setTargetGroup(cursor.getString(cursor.getColumnIndex(Constant.targetGroup)));
                patient.setDateConfirmedHiv(cursor.getString(cursor.getColumnIndex(Constant.dateConfirmedHiv)));
                patient.setTbStatus(cursor.getString(cursor.getColumnIndex(Constant.tbStatus)));
                patient.setPregnant(cursor.getInt(cursor.getColumnIndex(Constant.pregnant)));
                patient.setBreastfeeding(cursor.getInt(cursor.getColumnIndex(Constant.breastfeeding)));
                patient.setTimeStamp(cursor.getString(cursor.getColumnIndex(Constant.timeStamp)));
                patient.setUploaded(cursor.getString(cursor.getColumnIndex(Constant.uploaded)));
                patient.setTimeUploaded(cursor.getString(cursor.getColumnIndex(Constant.timeUploaded)));
                patient.setStatusRegistration(cursor.getString(cursor.getColumnIndex(Constant.statusRegistration)));
                patient.setDateRegistration(cursor.getString(cursor.getColumnIndex(Constant.dateRegistration)));
            }

            db.close();
            cursor.close();
        } catch (Exception e) {

        }

        return patient;
    }


    public boolean findCheckIfPatientExistByHtsId(long htsId2) {
        boolean bol = false;
        try {
            String query = "Select * FROM " + Constant.TABLE_PATIENT + " WHERE " + Constant.htsId + " = " + htsId2;
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.getCount() > 0) {
                bol = true;
            }
            db.close();
            cursor.close();
        } catch (Exception e) {

        }
        return bol;
    }


    public boolean findCheckIfPatientExistByHostipitalNumber(String hospitalNum1) {
        boolean bool = false;
        try {
            String query = "Select * FROM " + Constant.TABLE_PATIENT + " WHERE " + Constant.hospitalNum + " = " + hospitalNum1;
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                bool = true;
            }
            cursor.close();
        } catch (Exception e) {

        }

        return bool;
    }


    public String findById(Long patientId1) {
        String uuid = "";
        try {
            String query = "Select * FROM " + Constant.TABLE_PATIENT + " WHERE " + Constant.pid + " = " + patientId1;
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.getCount() > 0) {
                uuid = cursor.getString(cursor.getColumnIndex(Constant.uuid));
            }
            cursor.close();
        } catch (Exception ignored) {

        }
        return uuid;
    }

    public Patient2 findPatientById(long patientId1) {
        Patient2 patient = new Patient2();
        try {
            String query = "Select * FROM " + Constant.TABLE_PATIENT + " WHERE " + Constant.patientId + " = " + patientId1;
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                patient.setPatientId(cursor.getLong(cursor.getColumnIndex(Constant.patientId)));
                patient.setHospitalNum(cursor.getString(cursor.getColumnIndex(Constant.hospitalNum)));

            }
            cursor.close();
        } catch (Exception e) {

        }
        return patient;
    }


    public void updatePatientHos(Patient hts) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("biometric", hts.getBiometric());
            values.put(Constant.hospitalNum, hts.getBiometric());
            db.update(Constant.TABLE_PATIENT, values, "patient_id = ?", new String[]{String.valueOf(hts.getPatientId())});
            db.close();
        } catch (Exception ignored) {

        }
    }

    public void updatePatientResult2(Patient hts) {
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
            values.put(Constant.dateConfirmedHiv, hts.getDateConfirmedHiv());
            db.update(Constant.TABLE_PATIENT, values, "patient_id = ?", new String[]{String.valueOf(hts.getPatientId())});
            db.close();
        } catch (Exception e) {

        }

    }


    public void updatePatient(Patient patient) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constant.htsId, patient.getHtsId());
            values.put(Constant.deviceId, patient.getDeviceconfigId());
            values.put(Constant.facilityId, patient.getFacility().getFacilityId());
            values.put(Constant.hospitalNum, patient.getHospitalNum());
            values.put(Constant.uniqueId, patient.getUniqueId());
            values.put(Constant.surname, patient.getSurname());
            values.put(Constant.otherNames, patient.getOtherNames());
            values.put(Constant.gender, patient.getGender());
            values.put(Constant.dateBirth, patient.getDateBirth());
            values.put(Constant.age, patient.getAge());
            values.put(Constant.maritalStatus, patient.getMaritalStatus());
            values.put(Constant.education, patient.getEducation());
            values.put(Constant.occupation, patient.getOccupation());
            values.put(Constant.address, patient.getAddress());
            values.put(Constant.phone, patient.getPhone());
            values.put(Constant.state, patient.getState());
            values.put(Constant.lga, patient.getLga());
            values.put(Constant.nextKin, patient.getNextKin());
            values.put(Constant.addressKin, patient.getAddressKin());
            values.put(Constant.phoneKin, patient.getPhoneKin());
            values.put(Constant.relationKin, patient.getRelationKin());
            values.put(Constant.entryPoint, patient.getEntryPoint());
            values.put(Constant.targetGroup, patient.getTargetGroup());
            values.put(Constant.dateConfirmedHiv, patient.getDateConfirmedHiv());
            values.put(Constant.tbStatus, patient.getTbStatus());
            values.put(Constant.pregnant, patient.getPregnant());
            values.put(Constant.breastfeeding, patient.getBreastfeeding());
            values.put(Constant.timeStamp, patient.getTimeStamp());
            values.put(Constant.uploaded, patient.getUploaded());
            values.put(Constant.timeUploaded, patient.getTimeUploaded());
            values.put(Constant.dateRegistration, patient.getDateRegistration());
            values.put(Constant.statusRegistration, patient.getStatusRegistration());
            db.update(Constant.TABLE_PATIENT, values, Constant.patientId + " = ?", new String[]{String.valueOf(patient.getPatientId())});
            db.close();
        } catch (Exception e) {

        }
    }

    public void updatePatient1(Patient patient) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constant.htsId, patient.getHtsId());
            values.put(Constant.deviceId, patient.getDeviceconfigId());
            values.put(Constant.facilityId, patient.getFacility().getFacilityId());
            values.put(Constant.hospitalNum, patient.getHospitalNum());
            values.put(Constant.uniqueId, patient.getUniqueId());
            values.put(Constant.surname, patient.getSurname());
            values.put(Constant.otherNames, patient.getOtherNames());
            values.put(Constant.gender, patient.getGender());//auto generated// state/lga/facility/001
            values.put(Constant.dateBirth, patient.getDateBirth());
            values.put(Constant.age, patient.getAge());
            values.put(Constant.maritalStatus, patient.getMaritalStatus());
            values.put(Constant.education, patient.getEducation());
            values.put(Constant.occupation, patient.getOccupation());
            values.put(Constant.address, patient.getAddress());
            values.put(Constant.phone, patient.getPhone());//month/day
            values.put(Constant.state, patient.getState());
            values.put(Constant.lga, patient.getLga());
            values.put(Constant.nextKin, patient.getNextKin());
            values.put(Constant.addressKin, patient.getAddressKin());
            values.put(Constant.phoneKin, patient.getPhoneKin());
            values.put(Constant.relationKin, patient.getRelationKin());
            values.put(Constant.entryPoint, patient.getEntryPoint());
            values.put(Constant.targetGroup, patient.getTargetGroup());
            values.put(Constant.dateConfirmedHiv, patient.getDateConfirmedHiv());
            values.put(Constant.tbStatus, patient.getTbStatus());
            values.put(Constant.pregnant, patient.getPregnant());
            values.put(Constant.breastfeeding, patient.getBreastfeeding());
            values.put(Constant.timeStamp, patient.getTimeStamp());
            values.put(Constant.uploaded, patient.getUploaded());
            values.put(Constant.timeUploaded, patient.getTimeUploaded());
            values.put(Constant.dateRegistration, patient.getDateRegistration());
            values.put(Constant.statusRegistration, patient.getStatusRegistration());
            db.update(Constant.TABLE_PATIENT, values, Constant.patientId + " = ?", new String[]{String.valueOf(patient.getPatientId())});
            db.close();
        } catch (Exception ignored) {

        }
    }


    public boolean findByPId(Long patientId1) {
        boolean bol = false;
        try {
            String query = "Select * FROM " + Constant.TABLE_PATIENT + " WHERE " + Constant.pid + " = " + patientId1;
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.getCount() > 0) {
                bol = true;
            }
            cursor.close();
        } catch (Exception ignored) {

        }
        return bol;
    }

    public boolean findByHospitalNum(String hospitalNum, Long facilityId) {
        boolean bol = false;
        try {
            String query = "Select * FROM " + Constant.TABLE_PATIENT + " WHERE " + Constant.hospitalNum + " = " + hospitalNum + " and " + Constant.facilityId + " = " + facilityId;
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.getCount() > 0) {
                bol = true;
            }
            cursor.close();
        } catch (Exception ignored) {

        }
        return bol;
    }

    public void updateSeverClients(Patient patient) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("biometric", patient.getBiometric());
            values.put("uuid", patient.getUuid());
            values.put(Constant.hospitalNum, patient.getHospitalNum());
            values.put(Constant.facilityId, patient.getFacility().getFacilityId());
            values.put(Constant.uuid, patient.getUuid());
            values.put(Constant.surname, patient.getSurname());
            values.put(Constant.otherNames, patient.getOtherNames());
            values.put(Constant.pid, patient.getPid());
            db.update(Constant.TABLE_PATIENT, values, "pid = ?", new String[]{String.valueOf(patient.getId())});
            db.close();
        } catch (Exception ignored) {

        }
    }

    public void uodateBiometric(int biometric, long hstId, String hospitalNum) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("biometric", biometric);
            values.put(Constant.hospitalNum, hospitalNum);
            db.update(Constant.TABLE_PATIENT, values, "hts_id = ?", new String[]{String.valueOf(hstId)});
            db.close();
        } catch (Exception ignored) {

        }
    }

    public void uodateBiometric1(int biometric, Long pid) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("biometric", biometric);
            db.update(Constant.TABLE_PATIENT, values, "pid = ?", new String[]{String.valueOf(pid)});
            db.close();
        } catch (Exception ignored) {

        }
    }

    public void saveServerPatient(Patient patient) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("biometric", patient.getBiometric());
            values.put("uuid", patient.getUuid());
            values.put(Constant.hospitalNum, patient.getHospitalNum());
            values.put(Constant.facilityId, patient.getFacility().getFacilityId());
            values.put(Constant.uuid, patient.getUuid());
            values.put(Constant.surname, patient.getSurname());
            values.put(Constant.otherNames, patient.getOtherNames());
            values.put(Constant.pid, patient.getPid());
            db.insert(Constant.TABLE_PATIENT, null, values);
            db.close();
        } catch (Exception ignored) {

        }
    }

    public void update(Patient hts) {
        try {
            lAMISLiteDb = LAMISLiteDb.getInstance(context);
            SQLiteDatabase db = lAMISLiteDb.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constant.uploaded, hts.getBiometric());
            db.update(Constant.TABLE_PATIENT, values, "patient_id = ?", new String[]{String.valueOf(hts.getPatientId())});
            db.close();
        } catch (Exception ignored) {

        }

    }

}
