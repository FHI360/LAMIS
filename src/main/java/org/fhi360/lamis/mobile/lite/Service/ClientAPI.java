package org.fhi360.lamis.mobile.lite.Service;

import org.fhi360.lamis.mobile.lite.Domains.Account;
import org.fhi360.lamis.mobile.lite.Domains.Assessment;
import org.fhi360.lamis.mobile.lite.Domains.AssessmentList;
import org.fhi360.lamis.mobile.lite.Domains.AssessmentReturn;
import org.fhi360.lamis.mobile.lite.Domains.Biometric;
import org.fhi360.lamis.mobile.lite.Domains.ClinicReturn;
import org.fhi360.lamis.mobile.lite.Domains.Data;
import org.fhi360.lamis.mobile.lite.Domains.ClinicList;
import org.fhi360.lamis.mobile.lite.Domains.HtsList;
import org.fhi360.lamis.mobile.lite.Domains.IndexContactList;
import org.fhi360.lamis.mobile.lite.Domains.ListDefaulters;
import org.fhi360.lamis.mobile.lite.Domains.MobileDto;
import org.fhi360.lamis.mobile.lite.Domains.PatientDto;
import org.fhi360.lamis.mobile.lite.Domains.PatientList;
import org.fhi360.lamis.mobile.lite.Domains.PatientReturn;
import org.fhi360.lamis.mobile.lite.Domains.RecencyReturn;
import org.fhi360.lamis.mobile.lite.Domains.ServerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ClientAPI {
    @Headers("Content-Type: application/json")
    @GET("resources/webservice/mobile/initialize")
    Call<Data> getData();

    @Headers("Content-Type: application/json")
    @GET("resources/hts/mobile/initialize/{userName}/{facilityId}/{deviceId}/{accountUserName}/{accountPassword}")
    Call<Data> getFacilityCode(@Path("userName") String username, @Path("facilityId") long pin, @Path("deviceId") String deviceId, @Path("accountUserName") String accountUserName, @Path("accountPassword") String accountPassword);

    @Headers("Content-Type: application/json")
    @GET("resources/webservice/mobile/initialize/{deviceId}/{facilityId}")
    Call<Account> getUsernamePasswordFromLamis(@Path("deviceId") String deviceId, @Path("facilityId") long facilityId);


    @Headers("Content-Type: application/json")
    @GET("resources/webservice/mobile/defaulter/{facilityId}")
    Call<ListDefaulters> clientTracking(@Path("facilityId") Long facilityId);

    @Headers("Content-Type: application/json")
    @POST("resources/hts/mobile/sync/assessment")
    Call<ServerResponse> syncAssessment(@Body List<AssessmentReturn> assessmentList);

    @Headers("Content-Type: application/json")
    @POST("resources/hts/mobile/sync/hts")
    Call<ServerResponse> syncHts(@Body HtsList hts);

    @Headers("Content-Type: application/json")
    @POST("resources/hts/mobile/sync/indexcontact")
    Call<ServerResponse> syncIndexContact(@Body IndexContactList indexContact);

    @Headers("Content-Type: application/json")
    @POST("resources/hts/mobile/sync/patient")
    Call<ServerResponse> syncPatient(@Body List<PatientReturn> patients);

    @Headers("Content-Type: application/json")
    @POST("resources/hts/mobile/sync/clinic")
    Call<ServerResponse> syncClinic(@Body List<ClinicReturn> clinics);


//    @Headers("Content-Type: application/json")
//    @POST("resources/hts/mobile/sync/data")
//    Call<ServerResponse> mobileData(@Body MobileDto mobileDto);

    @Headers("Content-Type: application/json")
    @POST("resources/hts/mobile/sync/recency")
    Call<ServerResponse> syncRecencies(@Body RecencyReturn recencies);


    @Headers("Content-Type: application/json")
    @GET("resources/hts/mobile/patients/{deviceId}")
    Call<List<PatientDto>> getPatients(@Path("deviceId") String deviceId);

    @Headers("Content-Type: application/json")
    @GET("resources/hts/mobile/patients-local/{facilityId}")
    Call<List<PatientDto>> getLocalPatients(@Path("facilityId") Long facilityId);


    @Headers("Content-Type: application/json")
    @POST("resources/hts/mobile/sync/biometric")
    Call<List<ServerResponse>> syncBiometric(@Body List<Biometric> biometrics);

}
