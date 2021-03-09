package org.fhi360.lamis.mobile.lite.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.lamis.mobile.lite.DAO.DeleteDAO;
import org.fhi360.lamis.mobile.lite.DAO.FacilityDAO;
import org.fhi360.lamis.mobile.lite.DAO.HtsDAO;
import org.fhi360.lamis.mobile.lite.DAO.LgaDAO;
import org.fhi360.lamis.mobile.lite.DAO.PatientDAO;
import org.fhi360.lamis.mobile.lite.DAO.RegimensDAO;
import org.fhi360.lamis.mobile.lite.DAO.StateDAO;
import org.fhi360.lamis.mobile.lite.Domains.Account;
import org.fhi360.lamis.mobile.lite.Domains.Data;
import org.fhi360.lamis.mobile.lite.Domains.Facility;
import org.fhi360.lamis.mobile.lite.Domains.Lga;
import org.fhi360.lamis.mobile.lite.Domains.Regimens;
import org.fhi360.lamis.mobile.lite.Domains.State;
import org.fhi360.lamis.mobile.lite.Service.APIService;
import org.fhi360.lamis.mobile.lite.Service.ClientAPI;
import org.fhi360.lamis.mobile.lite.Utils.GetLocation;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;
import org.fhi360.lamis.mobile.lite.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEdt;
    private TextView createAccount, text_copy_db, text_forget_password;
    private TextInputLayout inputLayoutUsername, inputLayoutPassword;
    private LocationManager locationManager;
    private Button loginBtn;
    private ShowHidePasswordEditText passwordEdt;
    private PrefManager prefManager;
    private ProgressDialog progressdialog;
    private String deviceconfigId;
    private Long faclityId;
    private String faciltyName;
    private Long stateId;
    private String stateName;
    private Long lgaId;
    private String lgaName;
    private FileOutputStream fstream;
    private Intent intent;
    GetLocation currentLoc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
       // locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
       // new PatientDAO(this).getAllPatient();
        prefManager = new PrefManager(LoginActivity.this);
        currentLoc = new GetLocation(this);
        usernameEdt = findViewById(R.id.edit_text_username);
        ((ShowHidePasswordEditText) findViewById(R.id.edit_text_password)).setTintColor(Color.RED);
        passwordEdt = findViewById(R.id.edit_text_password);
        text_forget_password = findViewById(R.id.text_forget_password);

        inputLayoutUsername = findViewById(R.id.text_input_layout_username);
        inputLayoutPassword = findViewById(R.id.text_input_layout_password);
        createAccount = findViewById(R.id.text_create_account);
       text_copy_db = findViewById(R.id.text_copy_db);
        loginBtn = findViewById(R.id.button_login);

       // HashMap<String, String> user = prefManager.getHtsDetails();
        deviceconfigId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        HashMap<String, String> isAccountCreated = prefManager.checkIfUserExist();
        String isAccountCreated1 = isAccountCreated.get("isAccountCreated");
        if (isAccountCreated1 != null) {
            createAccount.setVisibility(View.VISIBLE);
        }
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorPrimaryDark)));
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("holdState", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

        text_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(deviceconfigId);

            }
        });

        text_copy_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                createBackup();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> user2 = new PrefManager(getApplicationContext()).getHtsDetails();
                HashMap<String, String> status = new PrefManager(getApplicationContext()).getClientCodeStatus();
                if (status.get("status") != null) {
                } else {
                    deviceconfigId = user2.get("deviceconfig_id");
                    new PrefManager(getApplicationContext()).setClientCodeStatus();
                    Integer lastNumber = new HtsDAO(getApplicationContext()).getAutGeneratedClientCode();
                    new PrefManager(getApplicationContext()).setLastHtsSerialDigit(lastNumber);
                }

                ArrayList<State> states = new StateDAO(getApplicationContext()).getStates();
                ArrayList<Lga> lgas = new LgaDAO(getApplicationContext()).getLga();
                if (states.isEmpty() || lgas.isEmpty()) {
                    new DeleteDAO(getApplicationContext()).remove();
                    String username = usernameEdt.getText().toString();
                    String password = passwordEdt.getText().toString();
                    HashMap<String, String> user = prefManager.checkIfUserExist();
                    String userName = user.get("userName");
                    String passwords = user.get("password");
                    if (validateInput(username, password)) {
                        if (userName != null && userName.equals(username) && password != null && password.equals(passwords)) {
                            HashMap<String, String> user1 = prefManager.getHtsDetails();
                            String facilityName = user1.get("faciltyName");
                            String facilityId = user1.get("facilityId");
                            if (facilityId == null) {
                                Intent intent = new Intent(LoginActivity.this, Activation.class);
                                startActivity(intent);
                                finish();
                            } else {
                                progressdialog = new ProgressDialog(LoginActivity.this);
                                progressdialog.setMessage("LAMISLite Updating please wait...");
                                progressdialog.setCancelable(false);
                                progressdialog.setIndeterminate(false);
                                progressdialog.setMax(100);
                                progressdialog.show();
                                getRecordsFromLamisApi();
                            }
                        } else {
                            FancyToast.makeText(getApplicationContext(), "Wrong username or password", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                        }
                    }
                } else {
                    String username = usernameEdt.getText().toString();
                    String password = passwordEdt.getText().toString();
                    HashMap<String, String> user = prefManager.checkIfUserExist();
                    String userName = user.get("userName");
                    String passwords = user.get("password");
                    if (validateInput(username, password)) {
                        if (userName != null && userName.equals(username) && password != null && password.equals(passwords)) {
                            Intent intent = new Intent(LoginActivity.this, Activation.class);
                            startActivity(intent);
                            finish();
                        } else {
                            FancyToast.makeText(getApplicationContext(), "Wrong username or password", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                        }

                    }

                }


            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateAccount.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        currentLoc.connectGoogleApi();
    }

    @Override
    protected void onStop() {
        super.onStop();
        currentLoc.disConnectGoogleApi();
    }


    private void showAlert(final String deviceconfigId) {
        LayoutInflater li = LayoutInflater.from(LoginActivity.this);
        View promptsView = li.inflate(R.layout.forget_pop_up, null);
        final AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this).create();
        dialog.setView(promptsView);
        final TextView notitopOk, notitopNotnow;
        final EditText notitoptxt;
        notitopOk = promptsView.findViewById(R.id.notitopOk);
        notitopNotnow = promptsView.findViewById(R.id.notitopNotnow);
        notitoptxt = promptsView.findViewById(R.id.notitoptxt);
        notitopNotnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        notitopOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notitoptxt.getText().toString().isEmpty()) {
                    notitoptxt.setError("Facility Pincode can't empty");

                } else {
                    getPassworFromLamis(deviceconfigId, Long.parseLong(notitoptxt.getText().toString()));
                    dialog.dismiss();

                }

            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }


    private boolean validateInput(String username, String password) {
        if (username.isEmpty()) {
            inputLayoutUsername.setError("username can not be empty");
            inputLayoutUsername.setErrorEnabled(true);
            return false;
        } else if (password.isEmpty()) {
            inputLayoutPassword.setError("password can not be empty");
            inputLayoutPassword.setErrorEnabled(true);
            return false;
        }
        return true;


    }


    private void getRecordsFromLamisApi() {
        APIService APIService = new APIService();
        ClientAPI clientAPI = org.fhi360.lamis.mobile.lite.Service.APIService.createService(ClientAPI.class);
        HashMap<String, String> user = prefManager.checkIfUserExist();
        String accountuserName = user.get("userName");
        String passwords = user.get("password");
        HashMap<String, String> user1 = prefManager.getHtsDetails();
        String facilityName = user1.get("faciltyName");
        String facilityId = user1.get("facilityId");

        Call<Data> objectCall = clientAPI.getFacilityCode("fhi360", Long.parseLong(facilityId), deviceconfigId, accountuserName, passwords);
        objectCall.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.code() == 200) {
                    Data dataObject = response.body();
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
                        //String lgaName, String facilityName, String stateName, long lgaId, long steteId, long facilityId,long deviceId, String androidId
                        prefManager.saveDetails("", facility.getName(), "", facility.getLgaid(), facility.getStateid(), facility.getFacilityid(), facility.getDeviceconfigid(), deviceconfigId);
                        new FacilityDAO(getApplicationContext()).saveFacility(facility1);
                    }
                    Set<State> states = dataObject.getStates();
                    for (State state : states) {
                        State state1 = new State();
                        state1.setName(state.getName());
                        state1.setId(state.getId());
                        new StateDAO(getApplicationContext()).saveState(state1);
                    }
                    Set<Lga> lgas = dataObject.getLgas();
                    for (Lga lga : lgas) {
                        Lga lga1 = new Lga();
                        lga1.setId(lga.getId());
                        lga1.setName(lga.getName());
                        lga1.setState(lga.getState());
                        new LgaDAO(getApplicationContext()).saveLga(lga1);
                    }

                    Set<Regimens> regimen = dataObject.getRegimens();
                    for (Regimens regimen1 : regimen) {
                        Regimens regimen2 = new Regimens();
                        regimen2.setRegimentype(regimen1.getRegimentype());
                        regimen2.setRegimentypeId(regimen1.getRegimentypeId());
                        regimen2.setRegimenId(regimen1.getRegimenId());
                        regimen2.setRegimen(regimen1.getRegimen());
                        new RegimensDAO(getApplicationContext()).save(regimen2);
                    }
                    prefManager.update();
                    FancyToast.makeText(getApplicationContext(), "LAMISLite Updated successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    Intent streamPlayerHome = new Intent(getApplicationContext(), Home.class);
                    startActivity(streamPlayerHome);
                    finish();
                    progressdialog.dismiss();
                } else if (response.code() == 500) {
                    FancyToast.makeText(getApplicationContext(), "No Server response, contact System Admin", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    progressdialog.dismiss();
                } else if (response.code() == 400) {
                    FancyToast.makeText(getApplicationContext(), "No Server response, contact System Admin", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    progressdialog.dismiss();
                } else if (response.code() == 404) {
                    FancyToast.makeText(getApplicationContext(), "No Server response, contact System Admin", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    progressdialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                t.printStackTrace();
                FancyToast.makeText(getApplicationContext(), "No Internet Connection", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                progressdialog.dismiss();
            }


        });

    }

    private void getPassworFromLamis(String deviceId, long faciltyCode) {
        progressdialog = new ProgressDialog(LoginActivity.this);
        progressdialog.setMessage("App Loading credential please wait...");
        progressdialog.setCancelable(false);
        progressdialog.setIndeterminate(false);
        progressdialog.setMax(100);
        progressdialog.show();
        APIService APIService = new APIService();
        ClientAPI clientAPI = org.fhi360.lamis.mobile.lite.Service.APIService.createService(ClientAPI.class);
        Call<Account> objectCall = clientAPI.getUsernamePasswordFromLamis(deviceId, faciltyCode);
        objectCall.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.code() == 200) {
                    Account dataObject = response.body();
                    if (dataObject.getAccountUserName() == null && dataObject.getAccountPassword() == null || dataObject.getAccountUserName() == "" && dataObject.getAccountPassword() == "") {
                        HashMap<String, String> user = prefManager.checkIfUserExist();
                        String accountuserName = user.get("userName");
                        String passwords = user.get("password");
                        if (accountuserName == null && passwords == null) {
                            FancyToast.makeText(getApplicationContext(), "Your account does not exist", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                            progressdialog.dismiss();
                        } else {
                            usernameEdt.setText(accountuserName);
                            passwordEdt.setText(passwords);
                            progressdialog.dismiss();
                        }

                    } else {
                        usernameEdt.setText(dataObject.getAccountUserName());
                        passwordEdt.setText(dataObject.getAccountPassword());
                        progressdialog.dismiss();
                    }

                } else if (response.code() == 500) {
                    FancyToast.makeText(getApplicationContext(), "No Server response, contact System Admin", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    progressdialog.dismiss();
                } else if (response.code() == 400) {
                    FancyToast.makeText(getApplicationContext(), "No Server response, contact System Admin", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    progressdialog.dismiss();
                } else if (response.code() == 404) {
                    FancyToast.makeText(getApplicationContext(), "No Server response, contact System Admin", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    progressdialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                t.printStackTrace();
                FancyToast.makeText(getApplicationContext(), "No Internet Connection", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                progressdialog.dismiss();
            }


        });

    }

    @Override
    public void onBackPressed() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void createBackup() {
        HashMap<String, String> user = prefManager.getHtsDetails();
        try {
            String facilityId = user.get("facilityId");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                String files = "/data/data/org.fhi360.lamis.mobile.lite/databases/lamis.db";
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 23);
                File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                Facility facilities = new FacilityDAO(getApplicationContext()).getFacilityById(Long.parseLong(facilityId));
                String path = folder + "/LAMISLite/db/" + facilities.getName() + ".db";
                Log.v("OYISCOPATHE", path);
                File f = new File(path);
                f.getParentFile().mkdirs();
                f.createNewFile();
                File dbFile1 = new File(files);
                FileInputStream fis1 = new FileInputStream(dbFile1);
                OutputStream output1 = new FileOutputStream(path);
                //Transfer bytes from the inputfile to the outputfile
                Log.v("OYISCOPATHE 2", path);
                byte[] buffer1 = new byte[1024];
                int length1;
                while ((length1 = fis1.read(buffer1)) > 0) {
                    output1.write(buffer1, 0, length1);
                }
                Log.v("OYISCOPATHE 2", path);
                output1.flush();
                output1.close();
                fis1.close();
                Toast.makeText(getApplicationContext(), "Det ails Saved in " + f.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }


    public void restoreDb() {
        try {
            String files = "/data/data/org.fhi360.lamis.mobile.lite/databases/lamis.db";
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 23);
            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            String path = folder + "/LAMISLite/db/" + "lamis.db";
            Log.v("OYISCOPATHE", path);
            File f = new File(files);
            f.getParentFile().mkdirs();
            f.createNewFile();
            File dbFile1 = new File(path);
            FileInputStream fis1 = new FileInputStream(dbFile1);
            OutputStream output1 = new FileOutputStream(files);
            Log.v("OYISCO files", files);
            // Transfer bytes from the inputfile to the outputfile
            byte[] buffer1 = new byte[1024];
            int length1;
            while ((length1 = fis1.read(buffer1)) > 0) {
                output1.write(buffer1, 0, length1);
            }

            // Close the streams
            output1.flush();
            output1.close();
            fis1.close();
            Toast.makeText(getApplicationContext(), "Details Saved in " + f.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

        }
    }


    public void copyCooridnatePerfernce() {
        try {///data/data/org.fhi360.lamis.mobile.lite/shared_prefs/cordinateDb.xml
            String files = "/data/data/org.fhi360.lamis.mobile.lite/shared_prefs/cordinateDb.xml";
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 23);
            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            String path = folder + "/LAMISLite/" + "cordinateDb.xml";
            File f = new File(path);
            f.getParentFile().mkdirs();
            f.createNewFile();
            File dbFile1 = new File(files);
            FileInputStream fis1 = new FileInputStream(dbFile1);
            OutputStream output1 = new FileOutputStream(path);
            // Transfer bytes from the inputfile to the outputfile
            byte[] buffer1 = new byte[1024];
            int length1;
            while ((length1 = fis1.read(buffer1)) > 0) {
                output1.write(buffer1, 0, length1);
            }

            // Close the streams
            output1.flush();
            output1.close();
            fis1.close();
            Toast.makeText(getApplicationContext(), "Details Saved in " + f.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

        }
    }

    ///data/data/org.fhi360.lamis.mobile.lite/shared_prefs/lamisLite.xml
    public void copyUsernameAndPasswordPerfernce() {
        try {///data/data/org.fhi360.lamis.mobile.lite/shared_prefs/cordinateDb.xml
            String files = "/data/data/org.fhi360.lamis.mobile.lite/shared_prefs/lamisLite.xml";
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 23);
            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            String path = folder + "/LAMISLite/" + "lamisLite.xml";
            File f = new File(path);
            f.getParentFile().mkdirs();
            f.createNewFile();
            File dbFile1 = new File(files);
            FileInputStream fis1 = new FileInputStream(dbFile1);
            OutputStream output1 = new FileOutputStream(path);
            // Transfer bytes from the inputfile to the outputfile
            byte[] buffer1 = new byte[1024];
            int length1;
            while ((length1 = fis1.read(buffer1)) > 0) {
                output1.write(buffer1, 0, length1);
            }

            // Close the streams
            output1.flush();
            output1.close();
            fis1.close();
            Toast.makeText(getApplicationContext(), "Details Saved in " + f.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

        }
    }


}
