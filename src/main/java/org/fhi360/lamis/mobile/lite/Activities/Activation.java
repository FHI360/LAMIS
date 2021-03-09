package org.fhi360.lamis.mobile.lite.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.lamis.mobile.lite.DAO.CamTeamDAO;
import org.fhi360.lamis.mobile.lite.DAO.FacilityDAO;
import org.fhi360.lamis.mobile.lite.DAO.LgaDAO;
import org.fhi360.lamis.mobile.lite.DAO.RegimensDAO;
import org.fhi360.lamis.mobile.lite.DAO.StateDAO;
import org.fhi360.lamis.mobile.lite.Domains.CamTeam;
import org.fhi360.lamis.mobile.lite.Domains.Data;
import org.fhi360.lamis.mobile.lite.Domains.Facility;
import org.fhi360.lamis.mobile.lite.Domains.Lga;
import org.fhi360.lamis.mobile.lite.Domains.Regimens;
import org.fhi360.lamis.mobile.lite.Domains.State;

import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Service.APIService;
import org.fhi360.lamis.mobile.lite.Service.ClientAPI;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activation extends AppCompatActivity {
    Button activate;
    EditText edit_text_password, edit_text_username;
    Long faclityId;
    String faciltyName;
    Long stateId;
    String stateName;
    Long lgaId;
    String lgaName;
    int SPLASH_TIME = 2500;
    PrefManager prefManager;

    String androidId;
    ProgressDialog progressdialog;
    TextView serviceProvided;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activate);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorPrimaryDark)));
        activate = findViewById(R.id.activate);

        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        edit_text_username = findViewById(R.id.edit_text_username);
        edit_text_password = findViewById(R.id.edit_text_password);
        serviceProvided = findViewById(R.id.serviceProvided);
        prefManager = new PrefManager(Activation.this);
        HashMap<String, String> ipAddress = prefManager.getIpAddress();
        final String localIpAdress = ipAddress.get("ipAddress");
        System.out.println("localIpAdress" + localIpAdress);
        serviceProvided.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });
        ArrayList<Facility> states = new FacilityDAO(getApplicationContext()).getFacility();
        if (states.isEmpty()) {
            activate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validateInput(edit_text_username.getText().toString(), edit_text_password.getText().toString()) && localIpAdress != null) {
                        progressdialog = new ProgressDialog(Activation.this);
                        progressdialog.setMessage("Initializing App please wait...");
                        progressdialog.setCancelable(false);
                        progressdialog.setIndeterminate(false);
                        progressdialog.setMax(100);
                        progressdialog.show();
                        //getRecordsFromLamisApi1(localIpAdress, edit_text_username.getText().toString(), Long.parseLong(edit_text_password.getText().toString()), androidId);
                    } else {
                        progressdialog = new ProgressDialog(Activation.this);
                        progressdialog.setMessage("Initializing App please wait...");
                        progressdialog.setCancelable(false);
                        progressdialog.setIndeterminate(false);
                        progressdialog.setMax(100);
                        progressdialog.show();
                        getRecordsFromLamisApi(edit_text_username.getText().toString(), Long.parseLong(edit_text_password.getText().toString()), androidId);
                    }
                }
            });
        } else {
            Intent streamPlayerHome = new Intent(Activation.this, Home.class);
            startActivity(streamPlayerHome);
            finish();
        }

    }

    private void getRecordsFromLamisApi(String userName, long pin, String diviceId) {

        ClientAPI clientAPI = APIService.createService(ClientAPI.class);
        HashMap<String, String> user = prefManager.checkIfUserExist();
        String accountuserName = user.get("userName");
        String passwords = user.get("password");
        Call<Data> objectCall = clientAPI.getFacilityCode(userName, pin, diviceId, accountuserName, passwords);
        objectCall.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.code() == 200) {
                    Data dataObject = response.body();
                    assert dataObject != null;
                    Set<Facility> facilities = dataObject.getFacilities();
                    for (Facility facility : facilities) {
                        faclityId = facility.getFacilityid();
                        faciltyName = facility.getName();
                        stateId = facility.getStateid();
                        lgaId = facility.getLgaid();
                        Facility facility1 = new Facility();
                        facility1.setStateid(facility.getStateid());
                        facility1.setName(facility.getName());
                        facility1.setLgaid(facility.getLgaid());
                        facility1.setFacilityid(facility.getFacilityid());
                        facility1.setDeviceconfigid(facility.getDeviceconfigid());
                        try {
                            prefManager.saveDetails("", facility.getName(), "", facility.getLgaid(), facility.getStateid(), facility.getFacilityid(), facility.getDeviceconfigid(), androidId);
                            new FacilityDAO(Activation.this).saveFacility(facility1);

                        } catch (Exception ignored) {

                        }

                    }
                    Set<State> states = dataObject.getStates();
                    for (State state : states) {
                        State state1 = new State();
                        state1.setName(state.getName());
                        state1.setId(state.getId());
                        try {
                            new StateDAO(Activation.this).saveState(state1);
                        } catch (Exception ignored) {

                        }
                    }
                    Set<Lga> lgas = dataObject.getLgas();
                    for (Lga lga : lgas) {
                        Lga lga1 = new Lga();
                        lga1.setId(lga.getId());
                        lga1.setName(lga.getName());
                        lga1.setState(lga.getState());
                        try {
                            new LgaDAO(Activation.this).saveLga(lga1);
                        } catch (Exception ignored) {

                        }
                    }

                    Set<Regimens> regimen = dataObject.getRegimens();
                    for (Regimens regimen1 : regimen) {
                        Regimens regimen2 = new Regimens();
                        regimen2.setRegimentype(regimen1.getRegimentype());
                        regimen2.setRegimentypeId(regimen1.getRegimentypeId());
                        regimen2.setRegimenId(regimen1.getRegimenId());
                        regimen2.setRegimen(regimen1.getRegimen());
                        try {
                            new RegimensDAO(Activation.this).save(regimen2);
                        } catch (Exception ignored) {

                        }
                    }
                    Set<CamTeam> camTeams = dataObject.getCamTeams();
                    for (CamTeam camTeam : camTeams) {
                        CamTeam camTeam1 = new CamTeam();
                        camTeam1.setFacilityId(camTeam.getFacilityId());
                        camTeam1.setCamteam(camTeam.getCamteam());
                        camTeam1.setCamCode(camTeam.getCamCode());
                        try {
                            new CamTeamDAO(Activation.this).save(camTeam1);
                        } catch (Exception ignored) {

                        }
                    }

                    prefManager.update();
                    Intent streamPlayerHome = new Intent(Activation.this, Home.class);
                    startActivity(streamPlayerHome);
                    finish();
                    progressdialog.dismiss();
                } else if (response.code() == 500) {
                    FancyToast.makeText(Activation.this, "No Server response, contact System Admin", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    progressdialog.dismiss();
                } else if (response.code() == 400) {
                    FancyToast.makeText(Activation.this, "No Server response, contact System Admin", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    progressdialog.dismiss();
                } else if (response.code() == 404) {
                    FancyToast.makeText(Activation.this, "No Server response, contact System Admin", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    progressdialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                t.printStackTrace();
                FancyToast.makeText(Activation.this, "No Internet Connection", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                progressdialog.dismiss();
            }


        });

    }
//
//    private void getRecordsFromLamisApi1(String localIpAdress, String userName, long pin, String diviceId) {
//        //LocalSystemIpAddressServices APIService = new LocalSystemIpAddressServices();
//        ClientAPI clientAPI = null;// APIService.createService(localIpAdress, ClientAPI.class);
//        HashMap<String, String> user = prefManager.checkIfUserExist();
//        String accountuserName = user.get("userName");
//        String passwords = user.get("password");
//        Call<Data> objectCall = clientAPI.getFacilityCode(userName, pin, diviceId, accountuserName, passwords);
//        objectCall.enqueue(new Callback<Data>() {
//            @Override
//            public void onResponse(Call<Data> call, Response<Data> response) {
//                if (response.code() == 200) {
//                    Data dataObject = response.body();
//                    Set<Facility> facilities = dataObject.getFacilities();
//                    for (Facility facility : facilities) {
//                        faclityId = facility.getFacilityid();
//                        faciltyName = facility.getName();
//                        stateId = facility.getStateid();
//                        lgaId = facility.getLgaid();
//                        Facility facility1 = new Facility();
//                        facility1.setStateid(facility.getStateid());
//                        facility1.setName(facility.getName());
//                        facility1.setLgaid(facility.getLgaid());
//                        facility1.setFacilityid(facility.getFacilityid());
//                        facility1.setDeviceconfigid(facility.getDeviceconfigid());
//                        prefManager.saveDetails("", facility.getName(), "", facility.getLgaid(), facility.getStateid(), facility.getFacilityid(), facility.getDeviceconfigid(), androidId);
//                        new FacilityDAO(getApplicationContext()).saveFacility(facility1);
//                    }
//                    Set<CamTeam> camTeams = dataObject.getCamTeams();
//                    for (CamTeam camTeam : camTeams) {
//                        CamTeam camTeam1 = new CamTeam();
//                        camTeam1.setFacilityId(camTeam.getFacilityId());
//                        camTeam1.setCamteam(camTeam.getCamteam());
//                        camTeam1.setCamCode(camTeam.getCamCode());
//                        new CamTeamDAO(getApplicationContext()).save(camTeam1);
//                    }
//                    Set<State> states = dataObject.getStates();
//                    for (State state : states) {
//                        State state1 = new State();
//                        state1.setName(state.getName());
//                        state1.setId(state.getId());
//                        new StateDAO(getApplicationContext()).saveState(state1);
//                    }
//                    Set<Lga> lgas = dataObject.getLgas();
//                    for (Lga lga : lgas) {
//                        Lga lga1 = new Lga();
//                        lga1.setId(lga.getId());
//                        lga1.setName(lga.getName());
//                        lga1.setState(lga.getState());
//                        new LgaDAO(getApplicationContext()).saveLga(lga1);
//                    }
//
//                    Set<Regimens> regimen = dataObject.getRegimens();
//                    for (Regimens regimen1 : regimen) {
//                        Regimens regimen2 = new Regimens();
//                        regimen2.setRegimentype(regimen1.getRegimentype());
//                        regimen2.setRegimentypeId(regimen1.getRegimentypeId());
//                        regimen2.setRegimenId(regimen1.getRegimenId());
//                        regimen2.setRegimen(regimen1.getRegimen());
//                        new RegimensDAO(getApplicationContext()).save(regimen2);
//                    }
//
//                    prefManager.update();
//                    Intent streamPlayerHome = new Intent(getApplicationContext(), Home.class);
//                    startActivity(streamPlayerHome);
//                    finish();
//                    progressdialog.dismiss();
//                } else if (response.code() == 500) {
//                    FancyToast.makeText(getApplicationContext(), "No Server response, contact System Admin", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//                    progressdialog.dismiss();
//                } else if (response.code() == 400) {
//                    FancyToast.makeText(getApplicationContext(), "No Server response, contact System Admin", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//                    progressdialog.dismiss();
//                } else if (response.code() == 404) {
//                    FancyToast.makeText(getApplicationContext(), "No Server response, contact System Admin", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//                    progressdialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Data> call, Throwable t) {
//                t.printStackTrace();
//                FancyToast.makeText(getApplicationContext(), "No Internet Connection", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//                progressdialog.dismiss();
//            }
//
//
//        });
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressdialog != null && progressdialog.isShowing()) {
            progressdialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private boolean validateInput(String username1, String password) {
        if (username1.isEmpty()) {
            edit_text_username.setError("username can not be empty");
            return false;
        } else if (password.isEmpty()) {
            edit_text_password.setError("Facility pin code can not be empty");
            return false;
        }
        return true;


    }

    private void showAlert() {
        LayoutInflater li = LayoutInflater.from(Activation.this);
        View promptsView = li.inflate(R.layout.forget_pop_up, null);
        final AlertDialog dialog = new AlertDialog.Builder(Activation.this).create();
        dialog.setView(promptsView);
        final TextView notitopOk, notitopNotnow;
        final EditText notitoptxt;
        notitopOk = promptsView.findViewById(R.id.notitopOk);
        notitopNotnow = promptsView.findViewById(R.id.notitopNotnow);
        notitoptxt = promptsView.findViewById(R.id.notitoptxt);
        notitoptxt.setHint("Enter server URL");
        notitopNotnow.setOnClickListener(v -> dialog.dismiss());
        notitopOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notitoptxt.getText().toString().isEmpty()) {
                    notitoptxt.setError("Ip address  can't empty");
                } else {
                    new PrefManager(getApplicationContext()).saveIpAddress(notitoptxt.getText().toString());
                    FancyToast.makeText(getApplicationContext(), "Ip Address saved successfully ", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                    dialog.dismiss();

                }

            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }

}
