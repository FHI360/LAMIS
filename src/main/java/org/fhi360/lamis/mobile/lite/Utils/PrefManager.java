package org.fhi360.lamis.mobile.lite.Utils;

import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.content.SharedPreferences;
import android.content.Intent;
import android.content.SharedPreferences.Editor;

import org.fhi360.lamis.mobile.lite.Activities.LoginActivity;

import java.util.HashMap;

public class PrefManager {
    Context context;

    public PrefManager(Context context) {
        this.context = context;
    }

    public void saveDetails(String lgaName, String facilityName, String stateName, long lgaId, long steteId, long facilityId, long deviceId, String androidId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("lamisLite", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString("stateId", steteId + "");
        editor.putString("steteName", stateName);
        editor.putString("deviceconfig_id", deviceId + "");
        editor.putString("androidId", androidId);
        editor.putString("facilityId", facilityId + "");
        editor.putString("lgaId", lgaId + "");
        editor.putString("lgaNam", lgaName);
        editor.putString("faciltyName", facilityName);
        editor.putString("status", "1");
        editor.commit();

    }

    public void saveDetails(String lgaName, String facilityName, String stateName, long lgaId, long steteId, long facilityId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("lamisLite", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString("stateId", steteId + "");
        editor.putString("steteName", stateName);
        editor.putString("facilityId", facilityId + "");
        editor.putString("lgaId", lgaId + "");
        editor.putString("lgaNam", lgaName);
        editor.putString("faciltyName", facilityName);
        editor.putString("status", "1");
        editor.commit();

    }

    public void update() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("lamisLite", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString("status", "2");
        editor.commit();
    }

    public void setPatientId(String patientId, String hospitalNumber, String uniqueId, String surname, String otherName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("patientIdDb", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString("patientId", patientId);
        editor.putString("hospitalNumber", hospitalNumber);
        editor.putString("uniqueId", uniqueId);
        editor.putString("surname", surname);
        editor.putString("othername", otherName);
        editor.apply();
    }

    public void saveAssessmentId(String assessmentId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("assessmentIddb", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString("assessmentId", assessmentId);
        editor.apply();
    }


    public HashMap<String, String> getAssessmentId() {
        HashMap<String, String> user = new HashMap<String, String>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("assessmentIddb", Context.MODE_PRIVATE);
        user.put("assessmentId", sharedPreferences.getString("assessmentId", null));
        return user;
    }


    public void saveUuId(String uuid) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("uuiddb", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString("uuid", uuid);
        editor.apply();
    }


    public HashMap<String, String> getUuId() {
        HashMap<String, String> user = new HashMap<String, String>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("uuiddb", Context.MODE_PRIVATE);
        user.put("uuid", sharedPreferences.getString("uuid", null));
        return user;
    }



    public HashMap<String, String> getPatientId() {
        HashMap<String, String> user = new HashMap<String, String>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("patientIdDb", Context.MODE_PRIVATE);
        user.put("patientId", sharedPreferences.getString("patientId", null));
        user.put("id", sharedPreferences.getString("id", null));
        user.put("hospitalNumber", sharedPreferences.getString("hospitalNumber", null));
        user.put("uniqueId", sharedPreferences.getString("uniqueId", null));
        user.put("surname", sharedPreferences.getString("surname", null));
        user.put("gender", sharedPreferences.getString("gender", null));
        user.put("othername", sharedPreferences.getString("othername", null));
        return user;
    }


    public HashMap<String, String> getHtsDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("lamisLite", Context.MODE_PRIVATE);
        user.put("stateId", sharedPreferences.getString("stateId", null));
        user.put("facilityId", sharedPreferences.getString("facilityId", null));
        user.put("lgaId", sharedPreferences.getString("lgaId", null));
        user.put("steteName", sharedPreferences.getString("steteName", null));
        user.put("lgaNam", sharedPreferences.getString("lgaNam", null));
        user.put("faciltyName", sharedPreferences.getString("faciltyName", null));
        user.put("status", sharedPreferences.getString("status", null));
        user.put("deviceconfig_id", sharedPreferences.getString("deviceconfig_id", null));
        user.put("androidId", sharedPreferences.getString("androidId", null));
        return user;
    }

    public HashMap<String, String> getCam() {
        HashMap<String, String> user = new HashMap<String, String>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("camteam", Context.MODE_PRIVATE);
        user.put("camCode", sharedPreferences.getString("camCode", null));
        return user;
    }

    public void saveCam(String camTeam) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("camteam", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("camCode", camTeam);
        editor.apply();
    }

    public void fingerPrintPosition(String position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("positionDb", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("position", position);
        editor.apply();
    }
    public HashMap<String, String> getPrintPosition() {
        HashMap<String, String> positions = new HashMap<String, String>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("positionDb", Context.MODE_PRIVATE);
        positions.put("position", sharedPreferences.getString("position", null));
        return positions;
    }

    public HashMap<String, String> getIpAddress() {
        HashMap<String, String> user = new HashMap<String, String>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("ipAddressDb", Context.MODE_PRIVATE);
        user.put("ipAddress", sharedPreferences.getString("ipAddress", null));
        return user;
    }

    public void saveIpAddress(String ipAddress) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ipAddressDb", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ipAddress", ipAddress);
        editor.apply();
    }


    public void createCoordinate(String longitudeBest, String latitudeBest) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("cordinateDb", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("longitude", longitudeBest);
        editor.putString("latitude", latitudeBest);
        editor.commit();
    }


    public void createLoginSession(String userName, String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("lamisLite", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString("userName", userName);
        editor.putString("password", password);
        editor.putString("isAccountCreated", "2");
        editor.commit();
    }


    public void saveRegimen(String regimenType, String regimen) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("regimen", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString("regimenType", regimenType);
        editor.putString("regimen", regimen);
        editor.commit();
    }

    public HashMap<String, String> getRegiment() {
        HashMap<String, String> coordinate = new HashMap<String, String>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("regimen", Context.MODE_PRIVATE);
        coordinate.put("regimenType", sharedPreferences.getString("regimenType", null));
        coordinate.put("regimen", sharedPreferences.getString("regimen", null));
        return coordinate;
    }

    public boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("lamisLite", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("htsTemp", false);
    }

    public void logOut() {
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public HashMap<String, String> getCoordnitae() {
        HashMap<String, String> coordinate = new HashMap<String, String>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("cordinateDb", Context.MODE_PRIVATE);
        coordinate.put("longitude", sharedPreferences.getString("longitude", null));
        coordinate.put("latitude", sharedPreferences.getString("latitude", null));
        return coordinate;
    }


    public HashMap<String, String> getLastHtsSerialDigit() {
        HashMap<String, String> status = new HashMap<String, String>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("serialNumberDb", Context.MODE_PRIVATE);
        status.put("serialNumber", sharedPreferences.getString("serialNumber", null));
        return status;
    }


    public void setLastHtsSerialDigit(Integer serialNumber) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("serialNumberDb", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString("serialNumber", String.valueOf(serialNumber));
        editor.commit();
    }


    public HashMap<String, String> getClientCode() {
        HashMap<String, String> status = new HashMap<String, String>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("clientCodeDb", Context.MODE_PRIVATE);
        status.put("clientCode", sharedPreferences.getString("clientCode", null));
        return status;
    }


    public void setClientCode(String clientCode) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("clientCodeDb", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString("clientCode", clientCode);
        editor.commit();
    }


    public HashMap<String, String> getClientCodeStatus() {
        HashMap<String, String> status = new HashMap<String, String>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("clientCodeStatusDb", Context.MODE_PRIVATE);
        status.put("status", sharedPreferences.getString("status", null));
        return status;
    }


    public void setClientCodeStatus() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("clientCodeStatusDb", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString("status", "1");
        editor.commit();
    }

    public void createHospitalNumber(String hospitalNumber, String fullName, String patientId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("hostpitalNumberDb", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString("hospitalNumber", hospitalNumber);
        editor.putString("patientId", patientId);
        editor.putString("fullName", fullName);
        editor.commit();
    }

    public void createHospitalNumber1(String uniqueId, String fullName, String patientId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("hostpitalNumberDb1", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString("uniqueId", uniqueId);
        editor.putString("patientId", patientId);
        editor.putString("fullName", fullName);
        editor.commit();
    }

    public void createClientTracking(String name, String hospitalNum, String dateCurrentStatus, String currentStatus, String patientId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("clientTracking", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("hospitalNum", hospitalNum);
        editor.putString("dateCurrentStatus", dateCurrentStatus);
        editor.putString("currentStatus", currentStatus);
        editor.putString("patientId", patientId);
        editor.commit();
    }

    public HashMap<String, String> getClientTracking() {
        HashMap<String, String> coordinate = new HashMap<String, String>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("clientTracking", Context.MODE_PRIVATE);
        coordinate.put("name", sharedPreferences.getString("name", null));
        coordinate.put("hospitalNum", sharedPreferences.getString("hospitalNum", null));
        coordinate.put("dateCurrentStatus", sharedPreferences.getString("dateCurrentStatus", null));
        coordinate.put("currentStatus", sharedPreferences.getString("currentStatus", null));
        coordinate.put("patientId", sharedPreferences.getString("patientId", null));
        return coordinate;
    }

    public HashMap<String, String> getHospitalNumber() {
        HashMap<String, String> coordinate = new HashMap<String, String>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("hostpitalNumberDb", Context.MODE_PRIVATE);
        coordinate.put("hospitalNumber", sharedPreferences.getString("hospitalNumber", null));
        coordinate.put("fullName", sharedPreferences.getString("fullName", null));
        coordinate.put("patientId", sharedPreferences.getString("patientId", null));
        return coordinate;
    }


    public HashMap<String, String> getUnique1() {
        HashMap<String, String> coordinate = new HashMap<String, String>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("hostpitalNumberDb1", Context.MODE_PRIVATE);
        coordinate.put("uniqueId", sharedPreferences.getString("uniqueId", null));
        coordinate.put("fullName", sharedPreferences.getString("fullName", null));
        coordinate.put("patientId", sharedPreferences.getString("patientId", null));
        return coordinate;
    }

    public HashMap<String, String> checkIfUserExist() {
        HashMap<String, String> user = new HashMap<String, String>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("lamisLite", Context.MODE_PRIVATE);
        user.put("userName", sharedPreferences.getString("userName", null));
        user.put("password", sharedPreferences.getString("password", null));
        user.put("isAccountCreated", sharedPreferences.getString("isAccountCreated", null));
        return user;
    }

    public HashMap<String, String> getDeletStatus() {
        HashMap<String, String> user = new HashMap<String, String>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("deleteStatusUp", Context.MODE_PRIVATE);
        user.put("deleteStatus", sharedPreferences.getString("deleteStatus", null));
        return user;
    }

    public void deleteStatus(String status) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("deleteStatusUp", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        if (status.equalsIgnoreCase("0")) {
            editor.putString("deleteStatus", "0");
            editor.commit();
        } else {
            editor.putString("deleteStatus", status);
            editor.commit();
        }


    }


    public void clearSession() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("lamisLite", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void countPages(String num) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("countPages", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString("count", num);
        editor.commit();
    }


    public HashMap<String, String> getProfileDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("profielDetails", Context.MODE_PRIVATE);
        user.put("name", sharedPreferences.getString("name", null));
        user.put("clientcode", sharedPreferences.getString("clientcode", null));
        user.put("indexclientcode", sharedPreferences.getString("indexclientcode", null));
        user.put("age", sharedPreferences.getString("age", null));
        user.put("address", sharedPreferences.getString("address", null));
        user.put("phone", sharedPreferences.getString("phone", null));
        user.put("gender", sharedPreferences.getString("gender", null));
        user.put("surname", sharedPreferences.getString("surname", null));
        user.put("othernames", sharedPreferences.getString("othernames", null));
        user.put("dateBirth", sharedPreferences.getString("dateBirth", null));
        user.put("dateVisit", sharedPreferences.getString("dateVisit", null));
        user.put("htsId", sharedPreferences.getString("htsId", null));
        user.put("lga", sharedPreferences.getString("lga", null));
        user.put("ageUnit", sharedPreferences.getString("ageUnit", null));
        user.put("state", sharedPreferences.getString("state", null));
        user.put("maritalStatus", sharedPreferences.getString("maritalStatus", null));
        user.put("hivStatus", sharedPreferences.getString("hivStatus", null));
        user.put("indextype", sharedPreferences.getString("indextype", null));
        user.put("huspitalNums", sharedPreferences.getString("huspitalNums", null));
        user.put("notificationcounseling", sharedPreferences.getString("notificationcounseling", null));
        user.put("agreeNotfication", sharedPreferences.getString("agreeNotfication", null));
        user.put("numpartner", sharedPreferences.getString("numpartner", null));

        return user;
    }


    public HashMap<String, String> getHtsInstance() {
        HashMap<String, String> user = new HashMap<String, String>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("holdState", Context.MODE_PRIVATE);
        user.put("indexClientCode", sharedPreferences.getString("indexClientCode", null));
        user.put("surname", sharedPreferences.getString("surname", null));//.getText().toString());
        user.put("otherName", sharedPreferences.getString("otherName", null));//.getText().toString());
        user.put("dateBirth", sharedPreferences.getString("dateBirth", null));//.getText().toString());
        user.put("dateVisit", sharedPreferences.getString("dateVisit", null));
        user.put("address", sharedPreferences.getString("address", null));
        user.put("phone", sharedPreferences.getString("phone", null));
        user.put("testingSetting", sharedPreferences.getString("testingSetting", null));
        user.put("gender", sharedPreferences.getString("gender", null));
        user.put("firstTimeVisit", sharedPreferences.getString("firstTimeVisit", null));
        user.put("state", sharedPreferences.getString("state", null));
        user.put("lga", sharedPreferences.getString("lga", null));
        user.put("age", sharedPreferences.getString("age", null));
        user.put("maritalStatus", sharedPreferences.getString("maritalStatus", null));
        user.put("numChildren", sharedPreferences.getString("numChildren", null));
        user.put("numWives", sharedPreferences.getString("numWives", null));
        user.put("typeCounseling", sharedPreferences.getString("typeCounseling", null));
        user.put("indexClient", sharedPreferences.getString("indexClient", null));
        user.put("typeIndex", sharedPreferences.getString("typeIndex", null));
        user.put("referredFrom", sharedPreferences.getString("referredFrom", null));
        user.put("knowledgeAssessment1", sharedPreferences.getString("knowledgeAssessment1", null));
        user.put("knowledgeAssessment2", sharedPreferences.getString("knowledgeAssessment2", null));
        user.put("knowledgeAssessment3", sharedPreferences.getString("knowledgeAssessment3", null));
        user.put("knowledgeAssessment4", sharedPreferences.getString("knowledgeAssessment4", null));
        user.put("knowledgeAssessment5", sharedPreferences.getString("knowledgeAssessment5", null));
        user.put("knowledgeAssessment6", sharedPreferences.getString("knowledgeAssessment6", null));
        user.put("knowledgeAssessment7", sharedPreferences.getString("knowledgeAssessment7", null));
        user.put("riskAssessment1", sharedPreferences.getString("riskAssessment1", null));
        user.put("riskAssessment2", sharedPreferences.getString("riskAssessment2", null));
        user.put("riskAssessment3", sharedPreferences.getString("riskAssessment3", null));
        user.put("riskAssessment4", sharedPreferences.getString("riskAssessment4", null));
        user.put("riskAssessment5", sharedPreferences.getString("riskAssessment5", null));
        user.put("riskAssessment6", sharedPreferences.getString("riskAssessment6", null));
        user.put("tbScreening1", sharedPreferences.getString("tbScreening1", null));
        user.put("tbScreening2", sharedPreferences.getString("tbScreening2", null));
        user.put("tbScreening3", sharedPreferences.getString("tbScreening3", null));
        user.put("tbScreening4", sharedPreferences.getString("tbScreening4", null));
        user.put("stiScreening1", sharedPreferences.getString("stiScreening1", null));
        user.put("stiScreening2", sharedPreferences.getString("stiScreening2", null));
        user.put("stiScreening3", sharedPreferences.getString("stiScreening3", null));
        user.put("stiScreening4", sharedPreferences.getString("stiScreening4", null));
        user.put("stiScreening5", sharedPreferences.getString("stiScreening5", null));
        user.put("hivTestResult", sharedPreferences.getString("hivTestResult", null));
        user.put("testedHiv", sharedPreferences.getString("testedHiv", null));
        user.put("testedHiv2", sharedPreferences.getString("testedHiv2", null));
        user.put("postTest1", sharedPreferences.getString("postTest1", null));
        user.put("postTest2", sharedPreferences.getString("postTest2", null));
        user.put("postTest3", sharedPreferences.getString("postTest3", null));
        user.put("postTest4", sharedPreferences.getString("postTest4", null));
        user.put("postTest5", sharedPreferences.getString("postTest5", null));
        user.put("postTest6", sharedPreferences.getString("postTest6", null));
        user.put("postTest7", sharedPreferences.getString("postTest7", null));
        user.put("postTest8", sharedPreferences.getString("postTest8", null));
        user.put("postTest9", sharedPreferences.getString("postTest9", null));
        user.put("postTest10", sharedPreferences.getString("postTest10", null));
        user.put("postTest11", sharedPreferences.getString("postTest11", null));
        user.put("postTest12", sharedPreferences.getString("postTest12", null));
        user.put("postTest13", sharedPreferences.getString("postTest13", null));
        user.put("postTest14", sharedPreferences.getString("postTest14", null));
        user.put("syphilisTestResult", sharedPreferences.getString("syphilisTestResult", null));
        user.put("hepatitiscTestResult", sharedPreferences.getString("hepatitiscTestResult", null));
        user.put("hepatitisbTestResult", sharedPreferences.getString("hepatitisbTestResult", null));
        user.put("ageUnit", sharedPreferences.getString("ageUnit", null));
        user.put("comments", sharedPreferences.getString("comments", null));
        return user;
    }

}
