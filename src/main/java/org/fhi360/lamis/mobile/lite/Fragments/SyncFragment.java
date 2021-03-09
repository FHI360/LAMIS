package org.fhi360.lamis.mobile.lite.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import com.shashank.sony.fancytoastlib.FancyToast;
import org.fhi360.lamis.mobile.lite.DAO.AssementDAO;
import org.fhi360.lamis.mobile.lite.DAO.BiometricDAO;
import org.fhi360.lamis.mobile.lite.DAO.ClinicDAO;
import org.fhi360.lamis.mobile.lite.DAO.DeleteDAO;
import org.fhi360.lamis.mobile.lite.DAO.HtsDAO;
import org.fhi360.lamis.mobile.lite.DAO.IndexContactDAO;
import org.fhi360.lamis.mobile.lite.DAO.PatientDAO;
import org.fhi360.lamis.mobile.lite.Domains.Biometric;
import org.fhi360.lamis.mobile.lite.Domains.Facility2;
import org.fhi360.lamis.mobile.lite.Domains.HtsList;
import org.fhi360.lamis.mobile.lite.Domains.IndexContactList;
import org.fhi360.lamis.mobile.lite.Domains.Patient;
import org.fhi360.lamis.mobile.lite.Domains.PatientDto;
import org.fhi360.lamis.mobile.lite.Domains.ServerResponse;
import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Service.APIService;
import org.fhi360.lamis.mobile.lite.Service.ClientAPI;
import org.fhi360.lamis.mobile.lite.Utils.AppConfig;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SyncFragment extends Fragment {

    private Button synchronize;
    private ProgressDialog mPb;
    private CheckBox downloadCheckbox;
    private String androidId;
    public static Retrofit retrofit = null;

    @SuppressLint("HardwareIds")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sync, container, false);
        synchronize = rootView.findViewById(R.id.synchronize);
        downloadCheckbox = rootView.findViewById(R.id.download_checkbox);
        final AppCompatSpinner defualtServer = rootView.findViewById(R.id.serUrl);
        PrefManager prefManager = new PrefManager(getContext());
        HashMap<String, String> ipAddress = prefManager.getIpAddress();
        final String localIpAddress = ipAddress.get("ipAddress");
        androidId = Settings.Secure.getString(Objects.requireNonNull(getContext()).getContentResolver(), Settings.Secure.ANDROID_ID);
        ArrayList list = new ArrayList();
        if (localIpAddress != null) {
            list.add(localIpAddress);
            list.add(AppConfig.BASE_URL);
        } else {
            list.add(AppConfig.BASE_URL);
        }
        final ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(getContext(),
                R.layout.spinner_items, list);
        districtAdapter.setDropDownViewResource(R.layout.color_spinner_layout);
        districtAdapter.notifyDataSetChanged();
        defualtServer.setAdapter(districtAdapter);
        synchronize.setOnClickListener(v -> {
            if (downloadCheckbox.isChecked()) {
                String defaults = defualtServer.getSelectedItem().toString();
                if (defaults.equals(AppConfig.BASE_URL)) {
                    getPatient(androidId);
                } else {
                    HashMap<String, String> user = new PrefManager(getContext()).getHtsDetails();
                    String id = user.get("facilityId");
                    getPatient(Long.valueOf(id), defaults);
                }
            } else {
                String defaults = defualtServer.getSelectedItem().toString();
                System.out.println("defaults " + defaults);
                if (defaults.equals(AppConfig.BASE_URL)) {
                    synchronizeAssessment();
                } else {
                    synchronizeAssessment(defaults);
                }

            }
        });

        return rootView;
    }


    private void synchronizeAssessment() {
        mPb = new ProgressDialog(getContext());
        mPb.setProgress(0);
        mPb.setMessage("Uploading data please wait...");
        mPb.setCancelable(false);
        mPb.setIndeterminate(false);
        mPb.setProgress(0);
        mPb.setMax(100);
        mPb.show();
        ClientAPI clientAPI = APIService.createService(ClientAPI.class);
        Call<ServerResponse> objectCall = clientAPI.syncAssessment(new AssementDAO(getContext()).getAssessment());
        objectCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    synchronizeHts();
                } else {
                    mPb.dismiss();
                    FancyToast.makeText(getContext(), "Syn was not successful to LAMIS Server ", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                FancyToast.makeText(getContext(), "No internet connection ", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                mPb.dismiss();
            }

        });

    }


    private void synchronizeAssessment(String localIpAddresss) {
        mPb = new ProgressDialog(getContext());
        mPb.setProgress(0);
        mPb.setMessage("Uploading data please wait...");
        mPb.setCancelable(false);
        mPb.setIndeterminate(false);
        mPb.setProgress(0);
        mPb.setMax(100);
        mPb.show();
        ClientAPI clientAPI = this.createService(localIpAddresss, ClientAPI.class);
        Call<ServerResponse> objectCall = clientAPI.syncAssessment(new AssementDAO(getContext()).getAssessment());
        objectCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    synchronizeHts(localIpAddresss);
                } else {
                    mPb.dismiss();
                    FancyToast.makeText(getContext(), "Syn was not successful to LAMIS Server", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                mPb.dismiss();
                FancyToast.makeText(getContext(), "No internet connection", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            }

        });

    }


    public void synchronizeHts(String localIpAddresss) {
        ClientAPI clientAPI = this.createService(localIpAddresss, ClientAPI.class);
        HtsList hts = new HtsList();
        hts.setHts(new HtsDAO(getContext()).syncData());
        Call<ServerResponse> objectCall = clientAPI.syncHts(hts);
        objectCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    synchronizePatients(localIpAddresss);
                } else {
                    mPb.dismiss();
                    FancyToast.makeText(getContext(), "Sync was not successful to LAMIS Server", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                mPb.dismiss();
                FancyToast.makeText(getContext(), "No Internet connection", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

            }

        });
    }

    private void synchronizePatients(String localIpAddresss) {

        ClientAPI clientAPI = this.createService(localIpAddresss, ClientAPI.class);
        Call<ServerResponse> objectCall = clientAPI.syncPatient(new PatientDAO(getContext()).getAllPatient());
        objectCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    synchronizeBiometric(localIpAddresss);
                    mPb.dismiss();
                } else {
                    mPb.dismiss();
                    FancyToast.makeText(getContext(), "Syn was not successful to LAMIS Server", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                mPb.dismiss();
                FancyToast.makeText(getContext(), "No internet connection ", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

            }

        });

    }

    private void synchronizeBiometric(String localIpAddresss) {
        ClientAPI clientAPI = this.createService(localIpAddresss, ClientAPI.class);
        List<Biometric> biometrics = new BiometricDAO(getContext()).getBiometric();
        Call<List<ServerResponse>> objectCall = clientAPI.syncBiometric(biometrics);
        objectCall.enqueue(new Callback<List<ServerResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerResponse>> call, @NonNull Response<List<ServerResponse>> response) {
                if (response.isSuccessful()) {
                    List<ServerResponse> serverResponses = response.body();
                    for (ServerResponse serverResponse : serverResponses) {
                        new DeleteDAO(getContext()).removeBiometric(serverResponse.getMessage());
                    }
                    synchronizeClinic(localIpAddresss);
                } else {
                    mPb.hide();
                    FancyToast.makeText(getContext(), "Syn was not successful to LAMIS Server", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<List<ServerResponse>> call, @NonNull Throwable t) {
                t.printStackTrace();
                mPb.hide();
                FancyToast.makeText(getContext(), "No internet connection", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

            }

        });

    }


    public void synchronizeHts() {
        ClientAPI clientAPI = APIService.createService(ClientAPI.class);
        HtsList hts = new HtsList();
        hts.setHts(new HtsDAO(getContext()).syncData());
        Call<ServerResponse> objectCall = clientAPI.syncHts(hts);
        objectCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    synchronizePatients();
                } else {
                    mPb.dismiss();
                    FancyToast.makeText(getContext(), "Sync was not successful to LAMIS Server", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                mPb.dismiss();
                FancyToast.makeText(getContext(), "No Internet connection", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

            }

        });
    }

    private void synchronizeIndexContact() {
        System.out.println("INDEX" + new IndexContactDAO(getContext()).getIndexContact());
        APIService APIService = new APIService();
        ClientAPI clientAPI = org.fhi360.lamis.mobile.lite.Service.APIService.createService(ClientAPI.class);
        IndexContactList listIndexcontact = new IndexContactList();
        listIndexcontact.setIndexcontact(new IndexContactDAO(getContext()).getIndexContact());
        Call<ServerResponse> objectCall = clientAPI.syncIndexContact(listIndexcontact);
        objectCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    FancyToast.makeText(getContext(), "LAMISLite Synchronization was successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    mPb.hide();
                } else {
                    FancyToast.makeText(getContext(), "Sync was not successful to LAMIS Server", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    mPb.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                FancyToast.makeText(getContext(), "No internet connection", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                mPb.dismiss();
            }

        });

    }

    private void synchronizeClinic(String localIpAddresss) {
        ClientAPI clientAPI = this.createService(localIpAddresss, ClientAPI.class);
        Call<ServerResponse> objectCall = clientAPI.syncClinic(new ClinicDAO(getContext()).getAllClinic());
        objectCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    FancyToast.makeText(getContext(), "LAMISLite Synchronization was successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    mPb.dismiss();
                } else {
                    mPb.dismiss();
                    FancyToast.makeText(getContext(), "Syn was not successful to LAMIS Server", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                mPb.dismiss();
                FancyToast.makeText(getContext(), "No internet connection", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

            }

        });

    }


    private void synchronizeClinic() {
        ClientAPI clientAPI = APIService.createService(ClientAPI.class);
        Call<ServerResponse> objectCall = clientAPI.syncClinic(new ClinicDAO(getContext()).getAllClinic());
        objectCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    mPb.dismiss();
                    FancyToast.makeText(getContext(), "LAMISLite Synchronization was successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                } else {
                    mPb.dismiss();
                    FancyToast.makeText(getContext(), "Syn was not successful to LAMIS Server", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                mPb.dismiss();
                FancyToast.makeText(getContext(), "No internet connection", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

            }

        });

    }

    private void synchronizePatients() {
        ClientAPI clientAPI = APIService.createService(ClientAPI.class);
        Call<ServerResponse> objectCall = clientAPI.syncPatient(new PatientDAO(getContext()).getAllPatient());
        objectCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    synchronizeBiometric();
                } else {
                    mPb.dismiss();
                    FancyToast.makeText(getContext(), "Syn was not successful to LAMIS Server", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                mPb.dismiss();
                FancyToast.makeText(getContext(), "No internet connection", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

            }

        });

    }


    private void getPatient(String deviceId) {
        mPb = new ProgressDialog(getContext());
        mPb.setProgress(0);
        mPb.setMessage("Uploading data please wait...");
        mPb.setCancelable(false);
        mPb.setIndeterminate(false);
        mPb.setProgress(0);
        mPb.setMax(100);
        mPb.show();
        ClientAPI clientAPI = APIService.createService(ClientAPI.class);
        Call<List<PatientDto>> objectCall = clientAPI.getPatients(deviceId);
        objectCall.enqueue(new Callback<List<PatientDto>>() {
            @Override
            public void onResponse(@NonNull Call<List<PatientDto>> call, @NonNull Response<List<PatientDto>> response) {
                if (response.isSuccessful()) {
                    List<PatientDto> patientDtoList = response.body();
                    for (PatientDto patientDto : patientDtoList) {
                        Patient patient = new Patient();
                        patient.setSurname(patientDto.getSurname());
                        patient.setOtherNames(patientDto.getOthernames());
                        patient.setHospitalNum(patientDto.getHospitalnum());
                        patient.setUuid(patientDto.getUuid());
                        patient.setBiometric(0);
                        Facility2 facility2 = new Facility2();
                        facility2.setFacilityId(patientDto.getFacilityid());
                        patient.setFacility(facility2);
                        patient.setFacilityName(patientDto.getFacilityname());
                        patient.setPid(patientDto.getPid());
                        try {
                            if (new PatientDAO(getContext()).findByPId(patientDto.getPid())) {
                                new PatientDAO(getContext()).updateSeverClients(patient);
                            } else {
                                new PatientDAO(getContext()).saveServerPatient(patient);
                            }
                        } catch (Exception ignored) {

                        }
                    }
                    mPb.hide();
                    FancyToast.makeText(getContext(), "Records Successfully downloaded", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                } else {
                    mPb.hide();
                    FancyToast.makeText(getContext(), "LAMISLite can't get patient records", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                }


            }

            @Override
            public void onFailure(@NonNull Call<List<PatientDto>> call, @NonNull Throwable t) {
                t.printStackTrace();
                FancyToast.makeText(getContext(), "No internet connection ", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                mPb.hide();
            }

        });

    }

    private void getPatient(Long deviceId, String localIpAddress) {
        mPb = new ProgressDialog(getContext());
        mPb.setProgress(0);
        mPb.setMessage("Uploading data please wait...");
        mPb.setCancelable(false);
        mPb.setIndeterminate(false);
        mPb.setProgress(0);
        mPb.setMax(100);
        mPb.show();
        ClientAPI clientAPI = this.createService(localIpAddress, ClientAPI.class);
        Call<List<PatientDto>> objectCall = clientAPI.getLocalPatients(deviceId);
        objectCall.enqueue(new Callback<List<PatientDto>>() {
            @Override
            public void onResponse(@NonNull Call<List<PatientDto>> call, @NonNull Response<List<PatientDto>> response) {
                if (response.isSuccessful()) {
                    List<PatientDto> patientDtoList = response.body();
                    for (PatientDto patientDto : patientDtoList) {
                        Patient patient = new Patient();
                        patient.setSurname(patientDto.getSurname());
                        patient.setOtherNames(patientDto.getOthernames());
                        patient.setHospitalNum(patientDto.getHospitalnum());
                        patient.setUuid(patientDto.getUuid());
                        patient.setBiometric(0);
                        Facility2 facility2 = new Facility2();
                        facility2.setFacilityId(patientDto.getFacilityid());
                        patient.setFacility(facility2);
                        patient.setFacilityName(patientDto.getFacilityname());
                        patient.setPid(patientDto.getPid());
                        try {
                            if (new PatientDAO(getContext()).findByHospitalNum(patientDto.getHospitalnum(), patientDto.getFacilityid())) {
                                new PatientDAO(getContext()).updateSeverClients(patient);
                            } else {
                                new PatientDAO(getContext()).saveServerPatient(patient);
                            }
                        } catch (Exception ignored) {

                        }
                    }
                    mPb.hide();
                    FancyToast.makeText(getContext(), "Records Successfully downloaded", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                } else {
                    mPb.hide();
                    FancyToast.makeText(getContext(), "LAMISLite can't get patient records", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                }


            }

            @Override
            public void onFailure(@NonNull Call<List<PatientDto>> call, @NonNull Throwable t) {
                t.printStackTrace();
                FancyToast.makeText(getContext(), "No internet connection ", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                mPb.hide();
            }

        });

    }


    private void synchronizeBiometric() {
        ClientAPI clientAPI = APIService.createService(ClientAPI.class);
        List<Biometric> biometrics = new BiometricDAO(getContext()).getBiometric();
        Call<List<ServerResponse>> objectCall = clientAPI.syncBiometric(biometrics);
        objectCall.enqueue(new Callback<List<ServerResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerResponse>> call, @NonNull Response<List<ServerResponse>> response) {
                if (response.isSuccessful()) {
                    List<ServerResponse> serverResponses = response.body();
                    for (ServerResponse serverResponse : serverResponses) {
                        new DeleteDAO(getContext()).removeBiometric(serverResponse.getMessage());
                    }
                    synchronizeClinic();
                } else {
                    mPb.hide();
                    FancyToast.makeText(getContext(), "Syn was not successful to LAMIS Server", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<List<ServerResponse>> call, @NonNull Throwable t) {
                t.printStackTrace();
                mPb.hide();
                FancyToast.makeText(getContext(), "No internet connection", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

            }

        });

    }


    private static OkHttpClient getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }

    public <S> S createService(String ipAddress, Class<S> serviceClass) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://" + ipAddress + "/")
                .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.client(getClient()).build();
        return retrofit.create(serviceClass);
    }


}
