package org.fhi360.lamis.mobile.lite.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import org.fhi360.lamis.mobile.lite.DAO.DeleteDAO;
import org.fhi360.lamis.mobile.lite.DAO.HtsDAO;
import org.fhi360.lamis.mobile.lite.DAO.LgaDAO;
import org.fhi360.lamis.mobile.lite.DAO.PatientDAO;
import org.fhi360.lamis.mobile.lite.DAO.StateDAO;
import org.fhi360.lamis.mobile.lite.Domains.Hts;
import org.fhi360.lamis.mobile.lite.Domains.Lga;
import org.fhi360.lamis.mobile.lite.Domains.Patient;
import org.fhi360.lamis.mobile.lite.Domains.State;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;
import org.fhi360.lamis.mobile.lite.Utils.SettingConfig;

import com.library.NavigationBar;
import com.library.NvTab;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import org.fhi360.lamis.mobile.lite.R;

public class EditHts extends AppCompatActivity implements NavigationBar.OnTabClick {
    private  EditText indexClientCode, surname, otherName, dateBirth, age, dateVisit, phone, address, clientCode, comments;
    private  AppCompatSpinner testingSetting, gender, firstTimeVisit, state, lgas, maritalStatus, numChildren, numWives,
            typeCounseling, indexClient, typeIndex, referredFrom,
            knowledgeAssessment1, knowledgeAssessment2, knowledgeAssessment3, knowledgeAssessment4,
            knowledgeAssessment5, knowledgeAssessment6, knowledgeAssessment7,
            riskAssessment1, riskAssessment2, riskAssessment3, riskAssessment4, riskAssessment5, riskAssessment6,
            tbScreening1, tbScreening2, tbScreening3, tbScreening4, stiScreening1, stiScreening2, stiScreening3, stiScreening4, stiScreening5, hivTestResult, testedHiv2, postTest1, postTest2, postTest3, postTest4, postTest5,
            postTest6, postTest7, postTest8, postTest9, postTest10, postTest11, postTest12,
            postTest13, postTest14, syphilisTestResult, hepatitiscTestResult, hepatitisbTestResult, ageUnit;
    private  CheckBox stiReferred, tbReferred, artReferred;
    private  Calendar myCalendar = Calendar.getInstance();
    private Button finishButton;

    private String auoIncrementClientCode;
    private ScrollView activity_step_one;
    private ScrollView activity_step_two;
    private ScrollView activity_step_three;
    private  ScrollView activity_step_four;
    private String lgaName;
    private String facilityName;
    private String stateName;
    private String lgaId;
    private String steteId;
    private String facilityId;
    private PrefManager session;
    private NavigationBar bar;
    private int position = 0;
    private String status = "";
    private long id = 0;
    private  Button btNext, btn_next, btn_prev;
    private  SettingConfig settingConfig = new SettingConfig();
    private String name;
    private TextView clientDetails, settings1, stiScreening1F, stiScreening1F1;

    private HashMap<String, String> coordinate;
    private TextView clientPregnant;
    private String pin;
    private  String deviceconfigId;
    private TextInputLayout inputLayoutSurname, inputLayoutotherName, inputLayoutdateBirth, inputLayoutAge, inputLayoutDateVisit, inputLayoutstate, inputLayoutlga, inputLayoutaddress, inputLayoutPhone;
    private String htsId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorPrimaryDark)));
        bar = findViewById(R.id.navBar);
        bar.setOnTabClick(this);
        setup(true, 4);
        activity_step_one = findViewById(R.id.activity_step_one);
        activity_step_two = findViewById(R.id.activity_step_two);
        activity_step_three = findViewById(R.id.activity_step_three);
        activity_step_four = findViewById(R.id.activity_step_four);
        session = new PrefManager(getApplicationContext());
        inputLayoutSurname = findViewById(R.id.inputLayoutSurname);
        inputLayoutotherName = findViewById(R.id.inputLayoutotherName);
        inputLayoutdateBirth = findViewById(R.id.inputLayoutdateBirth);
        inputLayoutAge = findViewById(R.id.inputLayoutAge);
        inputLayoutDateVisit = findViewById(R.id.inputLayoutDateVisit);
        inputLayoutaddress = findViewById(R.id.inputLayoutaddress);
        inputLayoutPhone = findViewById(R.id.invalidPhone);
        surname = findViewById(R.id.surname);
        otherName = findViewById(R.id.otherName);
        dateBirth = findViewById(R.id.dateBirth);
        stiScreening1F = findViewById(R.id.stiScreening1F);
        stiScreening1F1 = findViewById(R.id.stiScreening1F1);


        age = findViewById(R.id.age);
        age.setEnabled(false);
        age.setTextColor(Color.parseColor("#B22222"));
        dateVisit = findViewById(R.id.dateVisit);
        address = findViewById(R.id.address);
        gender = findViewById(R.id.gender);
        firstTimeVisit = findViewById(R.id.firstTimeVisit);
        state = findViewById(R.id.state);
        lgas = findViewById(R.id.lgas);
        maritalStatus = findViewById(R.id.maritalStatus);
        phone = findViewById(R.id.phone);
        numChildren = findViewById(R.id.numChildren);
        numWives = findViewById(R.id.numWives);
        typeCounseling = findViewById(R.id.typeCounseling);
        indexClient = findViewById(R.id.indexClient);
        typeIndex = findViewById(R.id.typeIndex);
        indexClientCode = findViewById(R.id.indexClientCode);
        referredFrom = findViewById(R.id.referredFrom);
        clientCode = findViewById(R.id.clientCode);
        clientCode.setEnabled(false);
        HashMap<String, String> user = session.getHtsDetails();
        facilityName = user.get("faciltyName");
        lgaId = user.get("lgaId");
        steteId = user.get("stateId");
        facilityId = user.get("facilityId");
        status = user.get("status");
        pin = user.get("deviceconfig_id");
        deviceconfigId = user.get("deviceconfig_id");
        clientDetails = findViewById(R.id.clientDetails);
        clientDetails.setText("Client Details   (" + facilityName + ")");

        knowledgeAssessment1 = findViewById(R.id.knowledgeAssessment1);
        knowledgeAssessment2 = findViewById(R.id.knowledgeAssessment2);
        clientPregnant = findViewById(R.id.clientPregnant);
        clientPregnant.setVisibility(View.INVISIBLE);
        knowledgeAssessment2.setVisibility(View.INVISIBLE);

        knowledgeAssessment3 = findViewById(R.id.knowledgeAssessment3);
        knowledgeAssessment4 = findViewById(R.id.knowledgeAssessment4);
        knowledgeAssessment5 = findViewById(R.id.knowledgeAssessment5);
        knowledgeAssessment6 = findViewById(R.id.knowledgeAssessment6);
        knowledgeAssessment7 = findViewById(R.id.knowledgeAssessment7);
        riskAssessment1 = findViewById(R.id.riskAssessment1);
        riskAssessment2 = findViewById(R.id.riskAssessment2);
        riskAssessment3 = findViewById(R.id.riskAssessment3);
        riskAssessment4 = findViewById(R.id.riskAssessment4);
        riskAssessment5 = findViewById(R.id.riskAssessment5);
        riskAssessment6 = findViewById(R.id.riskAssessment6);
        tbScreening1 = findViewById(R.id.tbScreening1);
        tbScreening2 = findViewById(R.id.tbScreening2);
        tbScreening3 = findViewById(R.id.tbScreening3);
        tbScreening4 = findViewById(R.id.tbScreening4);
        stiScreening1 = findViewById(R.id.stiScreening1);
        stiScreening2 = findViewById(R.id.stiScreening2);
        stiScreening1.setVisibility(View.INVISIBLE);
        stiScreening2.setVisibility(View.INVISIBLE);
        stiScreening1F.setVisibility(View.INVISIBLE);
        stiScreening1F1.setVisibility(View.INVISIBLE);

        stiScreening3 = findViewById(R.id.tbScreening3);
        stiScreening4 = findViewById(R.id.tbScreening4);
        stiScreening5 = findViewById(R.id.stiScreening5);
        ageUnit = findViewById(R.id.ageUnit);
        hivTestResult = findViewById(R.id.hivTestResult);
        testedHiv2 = findViewById(R.id.testedHiv2);
        postTest1 = findViewById(R.id.postTest1);
        postTest2 = findViewById(R.id.postTest2);
        postTest3 = findViewById(R.id.postTest3);
        postTest4 = findViewById(R.id.postTest4);
        postTest5 = findViewById(R.id.postTest5);
        postTest6 = findViewById(R.id.postTest6);
        postTest7 = findViewById(R.id.postTest7);
        postTest8 = findViewById(R.id.postTest8);
        postTest9 = findViewById(R.id.postTest9);
        postTest10 = findViewById(R.id.postTest10);
        postTest11 = findViewById(R.id.postTest11);
        postTest12 = findViewById(R.id.postTest12);
        postTest13 = findViewById(R.id.postTest13);
        postTest14 = findViewById(R.id.postTest14);
        syphilisTestResult = findViewById(R.id.syphilisTestResult);
        hepatitisbTestResult = findViewById(R.id.hepatitisbTestResult);
        hepatitiscTestResult = findViewById(R.id.hepatitiscTestResult);
        comments = findViewById(R.id.comments);
        testingSetting = findViewById(R.id.testingSetting);
        stiReferred = findViewById(R.id.stiReferred);
        tbReferred = findViewById(R.id.tbReferred);
        artReferred = findViewById(R.id.artReferred);
        stiReferred.setVisibility(View.INVISIBLE);
        artReferred.setVisibility(View.INVISIBLE);
        tbReferred.setVisibility(View.INVISIBLE);
        testedHiv2.setVisibility(View.INVISIBLE);
        HashMap<String, String> user1 = session.getProfileDetails();
        htsId = user1.get("htsId");
        Hts hts =new HtsDAO(this).getData(Long.parseLong(htsId));
        indexClientCode.setText(hts.getIndexClientCode());

        clientCode.setText(hts.getClientCode());

        surname.setText(hts.getSurname());
        otherName.setText(hts.getOtherNames());
        dateBirth.setText(hts.getDateBirth());
        phone.setText(hts.getPhone());
        dateVisit.setText(hts.getDateVisit());
        comments.setText(hts.getNote());
        age.setText(String.valueOf(hts.getAge()));
        address.setText(hts.getAddress());
        SettingConfig.setSpinText(testingSetting, hts.getTestingSetting());
        SettingConfig.setSpinText(gender, hts.getGender());
        SettingConfig.setSpinText(firstTimeVisit, hts.getFirstTimeVisit());

        SettingConfig.setSpinText(maritalStatus, hts.getMaritalStatus());

        SettingConfig.setSpinText(numChildren, String.valueOf(hts.getNumChildren()));
        SettingConfig.setSpinText(numWives, String.valueOf(hts.getNumWives()));

        SettingConfig.setSpinText(typeCounseling, hts.getTypeCounseling());
        SettingConfig.setSpinText(indexClient, hts.getIndexClient());
        SettingConfig.setSpinText(typeIndex, hts.getTypeIndex());
        SettingConfig.setSpinText(referredFrom, hts.getReferredFrom());

        if (hts.getKnowledgeAssessment1() == 0) {
            SettingConfig.setSpinText(knowledgeAssessment1, "NO");
        } else if (hts.getKnowledgeAssessment1() == 1) {
            SettingConfig.setSpinText(knowledgeAssessment1, "YES");
        }


        if (hts.getKnowledgeAssessment2() == 0) {
            SettingConfig.setSpinText(knowledgeAssessment2, "NO");
        } else if (hts.getKnowledgeAssessment2() == 1) {
            SettingConfig.setSpinText(knowledgeAssessment2, "YES");
        }

        if (hts.getKnowledgeAssessment3() == 0) {
            SettingConfig.setSpinText(knowledgeAssessment3, "NO");
        } else if (hts.getKnowledgeAssessment3() == 1) {
            SettingConfig.setSpinText(knowledgeAssessment3, "YES");
        }

        if (hts.getKnowledgeAssessment4() == 0) {
            SettingConfig.setSpinText(knowledgeAssessment4, "NO");
        } else if (hts.getKnowledgeAssessment4() == 1) {
            SettingConfig.setSpinText(knowledgeAssessment4, "YES");
        }
        if (hts.getKnowledgeAssessment5() == 0) {
            SettingConfig.setSpinText(knowledgeAssessment5, "NO");
        } else if (hts.getKnowledgeAssessment5() == 1) {
            SettingConfig.setSpinText(knowledgeAssessment5, "YES");
        }
        if (hts.getKnowledgeAssessment6() == 0) {
            SettingConfig.setSpinText(knowledgeAssessment6, "NO");
        } else if (hts.getKnowledgeAssessment6() == 1) {
            SettingConfig.setSpinText(knowledgeAssessment6, "YES");
        }
        if (hts.getKnowledgeAssessment7() == 0) {
            SettingConfig.setSpinText(knowledgeAssessment7, "NO");
        } else if (hts.getKnowledgeAssessment7() == 1) {
            SettingConfig.setSpinText(knowledgeAssessment7, "YES");
        }


        if (hts.getRiskAssessment1() == 0) {
            SettingConfig.setSpinText(riskAssessment1, "NO");
        } else if (hts.getRiskAssessment1() == 1) {
            SettingConfig.setSpinText(riskAssessment1, "YES");
        }
        if (hts.getRiskAssessment2() == 0) {
            SettingConfig.setSpinText(riskAssessment2, "NO");
        } else if (hts.getRiskAssessment2() == 1) {
            SettingConfig.setSpinText(riskAssessment2, "YES");
        }

        if (hts.getRiskAssessment3() == 0) {
            SettingConfig.setSpinText(riskAssessment3, "NO");
        } else if (hts.getRiskAssessment3() == 1) {
            SettingConfig.setSpinText(riskAssessment3, "YES");
        }

        if (hts.getRiskAssessment4() == 0) {
            SettingConfig.setSpinText(riskAssessment4, "NO");
        } else if (hts.getRiskAssessment4() == 1) {
            SettingConfig.setSpinText(riskAssessment4, "YES");
        }

        if (hts.getRiskAssessment5() == 0) {
            SettingConfig.setSpinText(riskAssessment5, "NO");
        } else if (hts.getRiskAssessment5() == 1) {
            SettingConfig.setSpinText(riskAssessment5, "YES");
        }

        if (hts.getRiskAssessment6() == 0) {
            SettingConfig.setSpinText(riskAssessment6, "NO");
        } else if (hts.getRiskAssessment6() == 1) {
            SettingConfig.setSpinText(riskAssessment6, "YES");
        }


        if (hts.getTbScreening1() == 0) {
            SettingConfig.setSpinText(tbScreening1, "NO");
        } else if (hts.getTbScreening1() == 1) {
            SettingConfig.setSpinText(tbScreening1, "YES");
            tbReferred.setChecked(true);
        }

        if (hts.getTbScreening2() == 0) {
            SettingConfig.setSpinText(tbScreening2, "NO");
        } else if (hts.getTbScreening2() == 1) {
            SettingConfig.setSpinText(tbScreening2, "YES");
            tbReferred.setChecked(true);
        }

        if (hts.getTbScreening3() == 0) {
            SettingConfig.setSpinText(tbScreening3, "NO");
        } else if (hts.getTbScreening3() == 1) {
            SettingConfig.setSpinText(tbScreening3, "YES");
            tbReferred.setChecked(true);
        }

        if (hts.getTbScreening4() == 0) {
            SettingConfig.setSpinText(tbScreening4, "NO");
        } else if (hts.getTbScreening4() == 1) {
            SettingConfig.setSpinText(tbScreening4, "YES");
            tbReferred.setChecked(true);
        }

        if (hts.getStiScreening1() == 0) {
            SettingConfig.setSpinText(stiScreening1, "NO");
        } else if (hts.getStiScreening1() == 1) {
            SettingConfig.setSpinText(stiScreening1, "YES");
            stiReferred.setChecked(true);
        }
        if (hts.getStiScreening2() == 0) {
            SettingConfig.setSpinText(stiScreening2, "NO");
        } else if (hts.getStiScreening2() == 1) {
            SettingConfig.setSpinText(stiScreening2, "YES");
            stiReferred.setChecked(true);
        }
        if (hts.getStiScreening3() == 0) {
            SettingConfig.setSpinText(stiScreening3, "NO");
        } else if (hts.getStiScreening3() == 1) {
            SettingConfig.setSpinText(stiScreening3, "YES");
            stiReferred.setChecked(true);
        }
        if (hts.getStiScreening4() == 0) {
            SettingConfig.setSpinText(stiScreening4, "NO");
        } else if (hts.getStiScreening4() == 1) {
            SettingConfig.setSpinText(stiScreening4, "YES");
            stiReferred.setChecked(true);
        }
        if (hts.getStiScreening5() == 0) {
            SettingConfig.setSpinText(stiScreening5, "NO");
        } else if (hts.getStiScreening5() == 1) {
            SettingConfig.setSpinText(stiScreening5, "YES");
            stiReferred.setChecked(true);
        }

        if (hts.getHivTestResult().equalsIgnoreCase("Positive")) {
            SettingConfig.setSpinText(hivTestResult, hts.getHivTestResult());
            artReferred.setChecked(true);
        } else {
            SettingConfig.setSpinText(hivTestResult, hts.getHivTestResult());
        }

        SettingConfig.setSpinText(testedHiv2, hts.getTestedHiv());
        if (hts.getPostTest1() == 0) {
            SettingConfig.setSpinText(postTest1, "NO");
        } else if (hts.getPostTest1() == 1) {
            SettingConfig.setSpinText(postTest1, "YES");
        }
        if (hts.getPostTest2() == 0) {
            SettingConfig.setSpinText(postTest2, "NO");
        } else if (hts.getPostTest2() == 1) {
            SettingConfig.setSpinText(postTest2, "YES");
        }
        if (hts.getPostTest3() == 0) {
            SettingConfig.setSpinText(postTest3, "NO");
        } else if (hts.getPostTest3() == 1) {
            SettingConfig.setSpinText(postTest3, "YES");
        }
        if (hts.getPostTest4() == 0) {
            SettingConfig.setSpinText(postTest4, "NO");
        } else if (hts.getPostTest4() == 1) {
            SettingConfig.setSpinText(postTest4, "YES");
        }
        if (hts.getPostTest5() == 0) {
            SettingConfig.setSpinText(postTest5, "NO");
        } else if (hts.getPostTest5() == 1) {
            SettingConfig.setSpinText(postTest5, "YES");
        }


        if (hts.getPostTest6() == 0) {
            SettingConfig.setSpinText(postTest6, "NO");
        } else if (hts.getPostTest6() == 1) {
            SettingConfig.setSpinText(postTest6, "YES");
        }
        if (hts.getPostTest7() == 0) {
            SettingConfig.setSpinText(postTest7, "NO");
        } else if (hts.getPostTest7() == 1) {
            SettingConfig.setSpinText(postTest7, "YES");
        }
        if (hts.getPostTest8() == 0) {
            SettingConfig.setSpinText(postTest8, "NO");
        } else if (hts.getPostTest8() == 1) {
            SettingConfig.setSpinText(postTest8, "YES");
        }
        if (hts.getPostTest9() == 0) {
            SettingConfig.setSpinText(postTest9, "NO");
        } else if (hts.getPostTest9() == 1) {
            SettingConfig.setSpinText(postTest9, "YES");
        }


        if (hts.getPostTest10() == 0) {
            SettingConfig.setSpinText(postTest10, "NO");
        } else if (hts.getPostTest10() == 1) {
            SettingConfig.setSpinText(postTest10, "YES");
        }
        if (hts.getPostTest11() == 0) {
            SettingConfig.setSpinText(postTest11, "NO");
        } else if (hts.getPostTest11() == 1) {
            SettingConfig.setSpinText(postTest11, "YES");
        }
        if (hts.getPostTest12() == 0) {
            SettingConfig.setSpinText(postTest12, "NO");
        } else if (hts.getPostTest12() == 1) {
            SettingConfig.setSpinText(postTest12, "YES");
        }
        if (hts.getPostTest13() == 0) {
            SettingConfig.setSpinText(postTest13, "NO");
        } else if (hts.getPostTest13() == 1) {
            SettingConfig.setSpinText(postTest13, "YES");
        }
        if (hts.getPostTest14() == 0) {
            SettingConfig.setSpinText(postTest14, "NO");
        } else if (hts.getPostTest14() == 1) {
            SettingConfig.setSpinText(postTest14, "YES");
        }

        SettingConfig.setSpinText(syphilisTestResult, hts.getSyphilisTestResult());
        SettingConfig.setSpinText(hepatitiscTestResult, hts.getHepatitiscTestResult());
        SettingConfig.setSpinText(hepatitisbTestResult, hts.getHepatitisbTestResult());
        SettingConfig.setSpinText(ageUnit, hts.getAgeUnit());
        SettingConfig.setSpinText(syphilisTestResult, hts.getSyphilisTestResult());


        finishButton = findViewById(R.id.finishButton);
        final SettingConfig settingConfig = new SettingConfig();

        ArrayList lgaIds = new ArrayList();

        final ArrayList arrayListStateId = new ArrayList();
        ArrayList arrayListStateName = new ArrayList();
        lgaIds = new ArrayList();
        ArrayList<State> states = new ArrayList<>();
        states =new StateDAO(this) .getStates();
        for (State state1 : states) {
            arrayListStateId.add(state1.getId());
            arrayListStateName.add(state1.getName());
        }


        final ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(EditHts.this,
                R.layout.spinner_items, arrayListStateName);
        districtAdapter.setDropDownViewResource(R.layout.color_spinner_layout);
        districtAdapter.notifyDataSetChanged();
        state.setAdapter(districtAdapter);


        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                long  stateId = (long) arrayListStateId.get(position);

                ArrayList lgaIds = new ArrayList();
                ArrayList arrayListLgaName = new ArrayList();
                ArrayList<Lga> lga1 = new ArrayList<>();
                lga1 = new LgaDAO(getApplicationContext()).getLgaByStateId(stateId);
                for (Lga lga2 : lga1) {
                    lgaIds.add(lga2.getId());
                    arrayListLgaName.add(lga2.getName());
                }

                final ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(EditHts.this,
                        R.layout.spinner_items, arrayListLgaName);
                districtAdapter.setDropDownViewResource(R.layout.color_spinner_layout);
                districtAdapter.notifyDataSetChanged();
                lgas.setAdapter(districtAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        SettingConfig.setSpinText(state, hts.getState());
        SettingConfig.setSpinText(lgas, hts.getLga());

        EditText edittext = findViewById(R.id.dateVisit);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(EditHts.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();
            }


        });

        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabe2();
            }

        };

        dateBirth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(EditHts.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();


            }
        });

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String gender1 = gender.getSelectedItem().toString();
                if (gender1.equalsIgnoreCase("Female")) {
                    clientPregnant.setVisibility(View.VISIBLE);
                    knowledgeAssessment2.setVisibility(View.VISIBLE);
                    stiScreening1F.setVisibility(View.VISIBLE);
                    stiScreening1F1.setVisibility(View.VISIBLE);
                    stiScreening1.setVisibility(View.VISIBLE);
                    stiScreening1.setVisibility(View.VISIBLE);
                } else if (gender1.equalsIgnoreCase("Male")) {
                    clientPregnant.setVisibility(View.INVISIBLE);
                    knowledgeAssessment2.setVisibility(View.INVISIBLE);
                    stiScreening1F.setVisibility(View.INVISIBLE);
                    stiScreening1F1.setVisibility(View.INVISIBLE);
                    stiScreening1.setVisibility(View.INVISIBLE);
                    stiScreening1.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        tbScreening1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tbScreen = tbScreening1.getSelectedItem().toString();
                if (tbScreen.equals("YES")) {
                    tbReferred.setVisibility(View.VISIBLE);
                } else if (tbScreen.equals("NO")) {
                    tbReferred.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tbScreening2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tbScreen = tbScreening2.getSelectedItem().toString();
                if (tbScreen.equals("YES")) {
                    tbReferred.setVisibility(View.VISIBLE);
                } else if (tbScreen.equals("NO")) {
                    tbReferred.setVisibility(View.INVISIBLE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tbScreening3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tbScreen = tbScreening3.getSelectedItem().toString();
                if (tbScreen.equals("YES")) {
                    tbReferred.setVisibility(View.VISIBLE);
                } else if (tbScreen.equals("NO")) {
                    tbReferred.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tbScreening4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tbScreen = tbScreening4.getSelectedItem().toString();
                if (tbScreen.equals("YES")) {
                    tbReferred.setVisibility(View.VISIBLE);
                } else if (tbScreen.equals("NO")) {
                    tbReferred.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        stiScreening1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String stIScreen = stiScreening1.getSelectedItem().toString();
                if (stIScreen.equals("YES")) {
                    stiReferred.setVisibility(View.VISIBLE);
                } else if (stIScreen.equals("NO")) {
                    stiReferred.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        stiScreening2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String stIScreen = stiScreening2.getSelectedItem().toString();
                if (stIScreen.equals("YES")) {
                    stiReferred.setVisibility(View.VISIBLE);
                } else if (stIScreen.equals("NO")) {
                    stiReferred.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        stiScreening3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String stIScreen = stiScreening3.getSelectedItem().toString();
                if (stIScreen.equals("YES")) {
                    stiReferred.setVisibility(View.VISIBLE);
                } else if (stIScreen.equals("NO")) {
                    stiReferred.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        stiScreening4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String stIScreen = stiScreening4.getSelectedItem().toString();
                if (stIScreen.equals("YES")) {
                    stiReferred.setVisibility(View.VISIBLE);
                } else if (stIScreen.equals("NO")) {
                    stiReferred.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        stiScreening5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String stIScreen = stiScreening5.getSelectedItem().toString();
                if (stIScreen.equals("YES")) {
                    stiReferred.setVisibility(View.VISIBLE);
                } else if (stIScreen.equals("NO")) {
                    stiReferred.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        hivTestResult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String hivTest2 = hivTestResult.getSelectedItem().toString();
                if (hivTest2.equalsIgnoreCase("Positive")) {
                    artReferred.setVisibility(View.VISIBLE);

                } else if (hivTest2.equalsIgnoreCase("Negative")) {
                    artReferred.setVisibility(View.INVISIBLE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        setupFloatingLabelError();
        setupFloatingLabelErrorAge();


        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> user = session.getHtsDetails();
                String facilityName = user.get("faciltyName");
                String lgaId = user.get("lgaId");
                String steteId = user.get("stateId");
                String facilityId = user.get("facilityId");
                Hts hts = new Hts();
                hts.setHtsId(Long.parseLong(htsId));
                hts.setStateId(Long.parseLong(steteId));
                hts.setLgaId(Long.parseLong(lgaId));
                hts.setFacilityId(Long.parseLong(facilityId));
                hts.setFacilityName(facilityName);
                hts.setDateVisit(dateVisit.getText().toString());
                name = surname.getText().toString() + "  " + otherName.getText().toString();
                hts.setClientCode(clientCode.getText().toString());
                if (referredFrom.getSelectedItem().toString().equalsIgnoreCase("")) {
                    hts.setReferredFrom("");
                }else {
                    hts.setReferredFrom(referredFrom.getSelectedItem().toString());
                }
                if (testingSetting.getSelectedItem().toString().equalsIgnoreCase("")) {
                    hts.setTestingSetting("");
                }else {
                    hts.setTestingSetting(testingSetting.getSelectedItem().toString());
                }
                hts.setSurname(surname.getText().toString());
                hts.setOtherNames(otherName.getText().toString());
                hts.setDateBirth(dateBirth.getText().toString());
                if (!age.getText().toString().equals("")) {
                    hts.setAge(Integer.parseInt(age.getText().toString()));
                }
                hts.setPhone(phone.getText().toString());
                hts.setAddress(address.getText().toString());
                hts.setGender(gender.getSelectedItem().toString());
                hts.setFirstTimeVisit(firstTimeVisit.getSelectedItem().toString());
                hts.setState(state.getSelectedItem().toString());
                if (state.getSelectedItem().toString().equals("")) {
                    hts.setLga("");
                } else {
                    hts.setLga(lgas.getSelectedItem().toString());
                }

                hts.setMaritalStatus(maritalStatus.getSelectedItem().toString());
                if (!numChildren.getSelectedItem().toString().equals("")) {
                    hts.setNumChildren(Integer.parseInt(numChildren.getSelectedItem().toString()));
                }

                if (!numWives.getSelectedItem().toString().equals("")) {
                    hts.setNumWives(Integer.parseInt(numWives.getSelectedItem().toString()));
                }
                hts.setTypeCounseling(typeCounseling.getSelectedItem().toString());
                hts.setIndexClient(indexClient.getSelectedItem().toString());
                hts.setTypeIndex(typeIndex.getSelectedItem().toString());
                hts.setIndexClientCode(indexClientCode.getText().toString());
                if (knowledgeAssessment1.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setKnowledgeAssessment1(1);
                } else if (knowledgeAssessment1.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setKnowledgeAssessment1(0);
                }
                if (knowledgeAssessment2.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setKnowledgeAssessment2(1);
                } else if (knowledgeAssessment2.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setKnowledgeAssessment2(0);
                }
                if (knowledgeAssessment3.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setKnowledgeAssessment3(1);
                } else if (knowledgeAssessment3.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setKnowledgeAssessment3(0);
                }
                if (knowledgeAssessment4.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setKnowledgeAssessment4(1);
                } else if (knowledgeAssessment4.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setKnowledgeAssessment4(0);
                }
                if (knowledgeAssessment5.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setKnowledgeAssessment5(1);
                } else if (knowledgeAssessment5.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setKnowledgeAssessment5(0);
                }
                if (knowledgeAssessment6.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setKnowledgeAssessment6(1);
                } else if (knowledgeAssessment6.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setKnowledgeAssessment6(0);
                }
                if (knowledgeAssessment7.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setKnowledgeAssessment7(1);
                } else if (knowledgeAssessment7.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setKnowledgeAssessment7(0);
                }
                if (riskAssessment1.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setRiskAssessment1(1);
                } else if (riskAssessment1.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setRiskAssessment1(0);
                }
                if (riskAssessment2.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setRiskAssessment2(1);
                } else if (riskAssessment2.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setRiskAssessment2(0);
                }
                if (riskAssessment3.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setRiskAssessment3(1);
                } else if (riskAssessment3.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setRiskAssessment3(0);
                }
                if (riskAssessment4.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setRiskAssessment4(1);
                } else if (riskAssessment4.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setRiskAssessment4(0);
                }
                if (riskAssessment5.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setRiskAssessment5(1);
                } else if (riskAssessment5.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setRiskAssessment5(0);
                }
                if (riskAssessment6.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setRiskAssessment6(1);
                } else if (riskAssessment6.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setRiskAssessment6(0);
                }
                if (tbScreening1.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setTbScreening1(1);
                } else if (tbScreening1.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setTbScreening1(0);
                }
                if (tbScreening2.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setTbScreening2(1);
                } else if (tbScreening2.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setTbScreening2(0);
                }
                if (tbScreening3.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setTbScreening3(1);
                } else if (tbScreening3.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setTbScreening3(0);
                }
                if (tbScreening4.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setTbScreening4(1);
                } else if (tbScreening4.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setTbScreening4(0);
                }
                if (stiScreening1.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setStiScreening1(1);
                } else if (stiScreening1.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setStiScreening1(0);
                }
                if (stiScreening2.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setStiScreening2(1);
                } else if (stiScreening2.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setStiScreening2(0);
                }
                if (stiScreening3.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setStiScreening3(1);
                } else if (stiScreening3.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setStiScreening3(0);
                }
                if (stiScreening4.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setStiScreening4(1);
                } else if (stiScreening4.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setStiScreening4(0);
                }
                if (stiScreening5.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setStiScreening5(1);
                } else if (stiScreening5.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setStiScreening5(0);
                }
                hts.setHivTestResult(hivTestResult.getSelectedItem().toString());
                hts.setTestedHiv(testedHiv2.getSelectedItem().toString());
                if (postTest1.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setPostTest1(1);
                } else if (postTest1.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setPostTest1(0);
                }
                if (postTest2.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setPostTest2(1);
                } else if (postTest2.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setPostTest2(0);
                }
                if (postTest3.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setPostTest3(1);
                } else if (postTest3.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setPostTest3(0);
                }
                if (postTest4.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setPostTest4(1);
                } else if (postTest4.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setPostTest4(0);
                }
                if (postTest5.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setPostTest5(1);
                } else if (postTest5.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setPostTest5(0);
                }
                if (postTest6.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setPostTest6(1);
                } else if (postTest6.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setPostTest6(0);
                }
                if (postTest7.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setPostTest7(1);
                } else if (postTest7.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setPostTest7(0);
                }
                if (postTest8.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setPostTest8(1);
                } else if (postTest8.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setPostTest8(0);
                }
                if (postTest9.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setPostTest9(1);
                } else if (postTest9.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setPostTest9(0);
                }
                if (postTest10.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setPostTest10(1);
                } else if (postTest10.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setPostTest10(0);
                }

                if (postTest11.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setPostTest11(1);
                } else if (postTest11.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setPostTest11(0);
                }

                if (postTest12.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setPostTest12(1);
                } else if (postTest12.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setPostTest12(0);
                }

                if (postTest13.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setPostTest13(1);
                } else if (postTest13.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setPostTest13(0);
                }

                if (postTest14.getSelectedItem().toString().equalsIgnoreCase("YES")) {
                    hts.setPostTest14(1);
                } else if (postTest14.getSelectedItem().toString().equalsIgnoreCase("NO")) {
                    hts.setPostTest14(0);
                }
                hts.setSyphilisTestResult(syphilisTestResult.getSelectedItem().toString());

                hts.setDeviceconfigId(Long.parseLong(deviceconfigId));
                hts.setHepatitisbTestResult(hepatitisbTestResult.getSelectedItem().toString());

                hts.setHepatitiscTestResult(hepatitiscTestResult.getSelectedItem().toString());

                hts.setNote(comments.getText().toString());
                if (stiReferred.isChecked()) {
                    hts.setStiReferred(stiReferred.getText().toString());
                } else {
                    hts.setStiReferred("");
                }
                if (artReferred.isChecked()) {
                    hts.setArtReferred(artReferred.getText().toString());
                } else {
                    hts.setArtReferred("");
                }

                hts.setUploaded(0);
                hts.setTimeUploaded(System.currentTimeMillis() + "");
                Patient patient = new PatientDAO(getApplicationContext()).findPatientHtsId(Long.parseLong(htsId));
                if (patient != null) {
                    Patient patient1 = new Patient();
                    patient1.setPatientId(patient.getPatientId());
                    patient1.setSurname(surname.getText().toString());
                    patient1.setOtherNames(otherName.getText().toString());
                    patient1.setDateBirth(dateBirth.getText().toString());
                    patient1.setAge(Integer.parseInt(age.getText().toString()));
                    patient1.setAgeUnit(ageUnit.getSelectedItem().toString());
                    patient1.setGender(gender.getSelectedItem().toString());
                    patient1.setMaritalStatus(maritalStatus.getSelectedItem().toString());
                    patient1.setPhone(phone.getText().toString());
                    patient1.setAddress(address.getText().toString());
                    patient1.setState(state.getSelectedItem().toString());
                    patient1.setLga(lgas.getSelectedItem().toString());
                    patient1.setDateConfirmedHiv(dateVisit.getText().toString());
                    new PatientDAO(getApplicationContext()).updatePatientResult2(patient1);
                }
                new HtsDAO(getApplicationContext()).updateTestResult(hts);
                FancyToast.makeText(getApplicationContext(), "Test Result updated successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

            }

        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void updateLabe2() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateBirth.setText(sdf.format(myCalendar.getTime()));
        int age1 = 0;
        try {
            age1 = SettingConfig.getAge(dateBirth.getText().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        String checkIfAgeContainsNegative = String.valueOf(age1);
        if (checkIfAgeContainsNegative.contains("-")) {
            age.setError("Invalid Age");
        } else {
            age.setText(String.valueOf(age1));
        }
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateVisit.setText(sdf.format(myCalendar.getTime()));

    }

    public static boolean isValidPhoneNumber(String phone) {
        if (!phone.trim().equals("") || phone.length() == 11) {
            return Patterns.PHONE.matcher(phone).matches();
        }

        return false;
    }

    private void setupFloatingLabelError() {
        final TextInputLayout floatingUsernameLabel = findViewById(R.id.invalidPhone);
        floatingUsernameLabel.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() > 0 && text.length() <= 11) {
                    floatingUsernameLabel.setErrorEnabled(false);
                } else {
                    floatingUsernameLabel.setError("Invalid Phone");
                    floatingUsernameLabel.setErrorEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void setupFloatingLabelErrorAge() {
        final TextInputLayout floatingUsernameLabel = findViewById(R.id.inputLayoutAge);
        floatingUsernameLabel.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() > 0 && text.length() <= 11) {
                    floatingUsernameLabel.setErrorEnabled(false);
                } else {
                    floatingUsernameLabel.setError("Invalid Phone");
                    floatingUsernameLabel.setErrorEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void setup(boolean reset, int count) {
        if (reset)
            bar.resetItems();
        bar.setTabCount(count);
        bar.animateView(3000);
        bar.setCurrentPosition(position <= 0 ? 0 : position);
    }

    @Override
    public void onTabClick(int touchPosition, NvTab prev, NvTab nvTab) {
        switch (touchPosition) {
            case 0:
                activity_step_one.setVisibility(View.VISIBLE);
                activity_step_two.setVisibility(View.INVISIBLE);
                activity_step_three.setVisibility(View.INVISIBLE);
                activity_step_four.setVisibility(View.INVISIBLE);
                break;

            case 1:
                activity_step_one.setVisibility(View.INVISIBLE);
                activity_step_two.setVisibility(View.VISIBLE);
                activity_step_three.setVisibility(View.INVISIBLE);
                activity_step_four.setVisibility(View.INVISIBLE);
                break;
            case 2:

                activity_step_one.setVisibility(View.INVISIBLE);
                activity_step_two.setVisibility(View.INVISIBLE);
                activity_step_three.setVisibility(View.VISIBLE);
                activity_step_four.setVisibility(View.INVISIBLE);
                break;
            case 3:

                activity_step_one.setVisibility(View.INVISIBLE);
                activity_step_two.setVisibility(View.INVISIBLE);
                activity_step_three.setVisibility(View.INVISIBLE);
                activity_step_four.setVisibility(View.VISIBLE);
                break;
            default:
                activity_step_one.setVisibility(View.VISIBLE);
                activity_step_two.setVisibility(View.INVISIBLE);
                activity_step_three.setVisibility(View.INVISIBLE);
                activity_step_four.setVisibility(View.INVISIBLE);
                break;
        }


    }


    private boolean validateInput(String surname1, String dateOfBirth, String age1, String dateVisits) {
        if (surname1.isEmpty()) {
            surname.setError("Enter surname");
            FancyToast.makeText(getApplicationContext(), "Enter surname", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;

        } else if (dateOfBirth.isEmpty()) {
            dateBirth.setError("Enter date of birth");
            FancyToast.makeText(getApplicationContext(), "Enter date of birth", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;
        } else if (age1.isEmpty()) {
            age.setError("Enter age");
            FancyToast.makeText(getApplicationContext(), "Enter age", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;

        } else if (dateVisits.isEmpty()) {
            dateVisit.setError("Enter date of visit");
            FancyToast.makeText(getApplicationContext(), "Enter date of visit", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;

        }

        return true;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deletehts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.deleteHtss) {
            showAlert(Long.parseLong(htsId));
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAlert(final long htsIds) {
        LayoutInflater li = LayoutInflater.from(EditHts.this);
        View promptsView = li.inflate(R.layout.delete_pop_up, null);
        final AlertDialog dialog = new AlertDialog.Builder(EditHts.this).create();
        dialog.setView(promptsView);
        final TextView notitopOk, notitopNotnow;
        notitopOk = promptsView.findViewById(R.id.notitopOk);
        notitopNotnow = promptsView.findViewById(R.id.notitopNotnow);
        notitopNotnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        notitopOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteDAO(getApplicationContext()).removeHts(htsIds);
                dialog.dismiss();
                FancyToast.makeText(getApplicationContext(), "Record deleted successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                Intent intent = new Intent(EditHts.this, Home.class);
                startActivity(intent);
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }

    private void settingsSpinnerError(Spinner spinner) {
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error");
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText("SettingConfig can not be empty"); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.
            FancyToast.makeText(getApplicationContext(), "SettingConfig can not be empty", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
        }
    }

    private void ageUnitSpinnerError(Spinner spinner) {
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error1");
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText("Age can not be empty"); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.

        }
    }

    private void sexSpinnerError(Spinner spinner) {
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error2");
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText("Sex can not be empty"); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.

        }
    }


}