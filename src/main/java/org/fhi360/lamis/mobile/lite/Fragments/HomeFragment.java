package org.fhi360.lamis.mobile.lite.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import org.fhi360.lamis.mobile.lite.Activities.Home;
import org.fhi360.lamis.mobile.lite.Activities.IndexTesting;
import org.fhi360.lamis.mobile.lite.DAO.HtsDAO;
import org.fhi360.lamis.mobile.lite.DAO.LgaDAO;
import org.fhi360.lamis.mobile.lite.DAO.StateDAO;
import org.fhi360.lamis.mobile.lite.Domains.Hts;
import org.fhi360.lamis.mobile.lite.Domains.Hts2;
import org.fhi360.lamis.mobile.lite.Domains.Lga;
import org.fhi360.lamis.mobile.lite.Domains.State;
import org.fhi360.lamis.mobile.lite.Service.NotificationService;
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

public class HomeFragment extends Fragment implements NavigationBar.OnTabClick {
    private EditText indexClientCode, surname, otherName, dateBirth, age, dateVisit, phone, address, clientCode, comments;
    private AppCompatSpinner testingSetting, gender, firstTimeVisit, state, lgas, maritalStatus, numChildren, numWives,
            typeCounseling, indexClient, typeIndex, referredFrom,
            knowledgeAssessment1, knowledgeAssessment2, knowledgeAssessment3, knowledgeAssessment4,
            knowledgeAssessment5, knowledgeAssessment6, knowledgeAssessment7,
            riskAssessment1, riskAssessment2, riskAssessment3, riskAssessment4, riskAssessment5, riskAssessment6,
            tbScreening1, tbScreening2, tbScreening3, tbScreening4, stiScreening1, stiScreening2, stiScreening3, stiScreening4, stiScreening5, hivTestResult, testedHiv2, postTest1, postTest2, postTest3, postTest4, postTest5,
            postTest6, postTest7, postTest8, postTest9, postTest10, postTest11, postTest12,
            postTest13, postTest14, syphilisTestResult, hepatitiscTestResult, hepatitisbTestResult, ageUnit;
    private CheckBox stiReferred, tbReferred, artReferred;
    private Calendar myCalendar = Calendar.getInstance();
    private Button finishButton;

    private String auoIncrementClientCode;
    private ScrollView activity_step_one;
    private ScrollView activity_step_two;
    private ScrollView activity_step_three;
    private ScrollView activity_step_four;
    private String facilityName;
    private String lgaId;
    private String steteId;
    private String facilityId;
    private  PrefManager session;
    private NavigationBar bar;
    private int position = 0;
    private  String status = "";
    private long id = 0;
    private SettingConfig settingConfig = new SettingConfig();
    private String name;
    private TextView clientDetails, stiScreening1F, stiScreening1F1;
    private String longitude = "";
    private String latitude = "";
    private HashMap<String, String> coordinate;
    private TextView clientPregnant;
    private String pin;
    private String deviceconfigId;
    private TextInputLayout inputLayoutSurname, inputLayoutotherName, inputLayoutdateBirth, inputLayoutAge, inputLayoutDateVisit, inputLayoutstate, inputLayoutlga, inputLayoutaddress, inputLayoutPhone;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        bar = rootView.findViewById(R.id.navBar);
        bar.setOnTabClick(this);
        setup(true, 4);
        activity_step_one = rootView.findViewById(R.id.activity_step_one);
        activity_step_two = rootView.findViewById(R.id.activity_step_two);
        activity_step_three = rootView.findViewById(R.id.activity_step_three);
        activity_step_four = rootView.findViewById(R.id.activity_step_four);
        session = new PrefManager(getContext());
        inputLayoutSurname = rootView.findViewById(R.id.inputLayoutSurname);
        inputLayoutotherName = rootView.findViewById(R.id.inputLayoutotherName);
        inputLayoutdateBirth = rootView.findViewById(R.id.inputLayoutdateBirth);
        inputLayoutAge = rootView.findViewById(R.id.inputLayoutAge);
        inputLayoutDateVisit = rootView.findViewById(R.id.inputLayoutDateVisit);
        inputLayoutaddress = rootView.findViewById(R.id.inputLayoutaddress);
        inputLayoutPhone = rootView.findViewById(R.id.invalidPhone);
        surname = rootView.findViewById(R.id.surname);
        otherName = rootView.findViewById(R.id.otherName);
        dateBirth = rootView.findViewById(R.id.dateBirth);
        stiScreening1F = rootView.findViewById(R.id.stiScreening1F);
        stiScreening1F1 = rootView.findViewById(R.id.stiScreening1F1);


        age = rootView.findViewById(R.id.age);
        age.setEnabled(false);
        age.setTextColor(Color.parseColor("#B22222"));
        dateVisit = rootView.findViewById(R.id.dateVisit);
        address = rootView.findViewById(R.id.address);
        gender = rootView.findViewById(R.id.gender);
        firstTimeVisit = rootView.findViewById(R.id.firstTimeVisit);
        state = rootView.findViewById(R.id.state);
        lgas = rootView.findViewById(R.id.lgas);
        maritalStatus = rootView.findViewById(R.id.maritalStatus);
        phone = rootView.findViewById(R.id.phone);
        numChildren = rootView.findViewById(R.id.numChildren);
        numWives = rootView.findViewById(R.id.numWives);
        typeCounseling = rootView.findViewById(R.id.typeCounseling);
        indexClient = rootView.findViewById(R.id.indexClient);
        typeIndex = rootView.findViewById(R.id.typeIndex);
        indexClientCode = rootView.findViewById(R.id.indexClientCode);
        referredFrom = rootView.findViewById(R.id.referredFrom);
        clientCode = rootView.findViewById(R.id.clientCode);
        clientCode.setEnabled(false);
        HashMap<String, String> user = session.getHtsDetails();
        facilityName = user.get("faciltyName");
        lgaId = user.get("lgaId");
        steteId = user.get("stateId");
        facilityId = user.get("facilityId");
        status = user.get("status");
        pin = user.get("deviceconfig_id");
        deviceconfigId = user.get("deviceconfig_id");
        clientDetails = rootView.findViewById(R.id.clientDetails);
        clientDetails.setText("Client Details   (" + facilityName + ")");
        Integer autoGeneratedClientCode = new HtsDAO(getContext()).getAutGeneratedClientCode();
        auoIncrementClientCode = steteId + "/" + facilityId + "/" + pin + "/" + autoGeneratedClientCode;
        clientCode.setText(auoIncrementClientCode);
        knowledgeAssessment1 = rootView.findViewById(R.id.knowledgeAssessment1);
        knowledgeAssessment2 = rootView.findViewById(R.id.knowledgeAssessment2);
        clientPregnant = rootView.findViewById(R.id.clientPregnant);
        clientPregnant.setVisibility(View.INVISIBLE);
        knowledgeAssessment2.setVisibility(View.INVISIBLE);

        knowledgeAssessment3 = rootView.findViewById(R.id.knowledgeAssessment3);
        knowledgeAssessment4 = rootView.findViewById(R.id.knowledgeAssessment4);
        knowledgeAssessment5 = rootView.findViewById(R.id.knowledgeAssessment5);
        knowledgeAssessment6 = rootView.findViewById(R.id.knowledgeAssessment6);
        knowledgeAssessment7 = rootView.findViewById(R.id.knowledgeAssessment7);
        riskAssessment1 = rootView.findViewById(R.id.riskAssessment1);
        riskAssessment2 = rootView.findViewById(R.id.riskAssessment2);
        riskAssessment3 = rootView.findViewById(R.id.riskAssessment3);
        riskAssessment4 = rootView.findViewById(R.id.riskAssessment4);
        riskAssessment5 = rootView.findViewById(R.id.riskAssessment5);
        riskAssessment6 = rootView.findViewById(R.id.riskAssessment6);
        tbScreening1 = rootView.findViewById(R.id.tbScreening1);
        tbScreening2 = rootView.findViewById(R.id.tbScreening2);
        tbScreening3 = rootView.findViewById(R.id.tbScreening3);
        tbScreening4 = rootView.findViewById(R.id.tbScreening4);
        stiScreening1 = rootView.findViewById(R.id.stiScreening1);
        stiScreening2 = rootView.findViewById(R.id.stiScreening2);
        stiScreening1.setVisibility(View.INVISIBLE);
        stiScreening2.setVisibility(View.INVISIBLE);
        stiScreening1F.setVisibility(View.INVISIBLE);
        stiScreening1F1.setVisibility(View.INVISIBLE);

        stiScreening3 = rootView.findViewById(R.id.tbScreening3);
        stiScreening4 = rootView.findViewById(R.id.tbScreening4);
        stiScreening5 = rootView.findViewById(R.id.stiScreening5);
        ageUnit = rootView.findViewById(R.id.ageUnit);
        hivTestResult = rootView.findViewById(R.id.hivTestResult);
        testedHiv2 = rootView.findViewById(R.id.testedHiv2);
        postTest1 = rootView.findViewById(R.id.postTest1);
        postTest2 = rootView.findViewById(R.id.postTest2);
        postTest3 = rootView.findViewById(R.id.postTest3);
        postTest4 = rootView.findViewById(R.id.postTest4);
        postTest5 = rootView.findViewById(R.id.postTest5);
        postTest6 = rootView.findViewById(R.id.postTest6);
        postTest7 = rootView.findViewById(R.id.postTest7);
        postTest8 = rootView.findViewById(R.id.postTest8);
        postTest9 = rootView.findViewById(R.id.postTest9);
        postTest10 = rootView.findViewById(R.id.postTest10);
        postTest11 = rootView.findViewById(R.id.postTest11);
        postTest12 = rootView.findViewById(R.id.postTest12);
        postTest13 = rootView.findViewById(R.id.postTest13);
        postTest14 = rootView.findViewById(R.id.postTest14);
        syphilisTestResult = rootView.findViewById(R.id.syphilisTestResult);
        hepatitisbTestResult = rootView.findViewById(R.id.hepatitisbTestResult);
        hepatitiscTestResult = rootView.findViewById(R.id.hepatitiscTestResult);
        comments = rootView.findViewById(R.id.comments);
        testingSetting = rootView.findViewById(R.id.testingSetting);
        stiReferred = rootView.findViewById(R.id.stiReferred);
        tbReferred = rootView.findViewById(R.id.tbReferred);
        artReferred = rootView.findViewById(R.id.artReferred);
        stiReferred.setVisibility(View.INVISIBLE);
        artReferred.setVisibility(View.INVISIBLE);
        tbReferred.setVisibility(View.INVISIBLE);
        finishButton = rootView.findViewById(R.id.finishButton);
        final SettingConfig settingConfig = new SettingConfig();

        ArrayList lgaIds = new ArrayList();
        final ArrayList arrayListStateId = new ArrayList();
        ArrayList arrayListStateName = new ArrayList();
        lgaIds = new ArrayList();
         ArrayList<State> states = new ArrayList<>();
        states = new StateDAO(getContext()).getStates();
        for (State state1 : states) {
            arrayListStateId.add(state1.getId());
            arrayListStateName.add(state1.getName());
        }

        final ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(getContext(),
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
                lga1 = new LgaDAO(getContext()).getLgaByStateId(stateId);
                for (Lga lga2 : lga1) {
                    lgaIds.add(lga2.getId());
                    arrayListLgaName.add(lga2.getName());

                }
                final ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(getContext(),
                        R.layout.spinner_items, arrayListLgaName);
                districtAdapter.setDropDownViewResource(R.layout.color_spinner_layout);
                districtAdapter.notifyDataSetChanged();
                lgas.setAdapter(districtAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        EditText edittext = rootView.findViewById(R.id.dateVisit);
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
                final DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), date, myCalendar
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
                final DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), date1, myCalendar
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


        setupFloatingLabelError(rootView);
        setupFloatingLabelErrorAge(rootView);


        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateInput(surname.getText().toString(),
                        dateBirth.getText().toString(), age.getText().toString(),
                        dateVisit.getText().toString()
                )) {
                    if (hivTestResult.getSelectedItem().toString().equals("")) {
                        FancyToast.makeText(getContext(), "Select HIV status", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    } else if (gender.getSelectedItem().toString().equals("")) {
                        FancyToast.makeText(getContext(), "Select gender", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    } else if (!gender.getSelectedItem().toString().equals("")) {
                        HashMap<String, String> user = session.getHtsDetails();
                        String facilityName = user.get("faciltyName");
                        String lgaId = user.get("lgaId");
                        String steteId = user.get("stateId");
                        String facilityId = user.get("facilityId");
                        Hts hts = new Hts();
                        hts.setStateId(Long.parseLong(steteId));
                        hts.setLgaId(Long.parseLong(lgaId));
                        hts.setFacilityId(Long.parseLong(facilityId));
                        hts.setFacilityName(facilityName);
                        hts.setDateVisit(dateVisit.getText().toString());
                        coordinate = session.getCoordnitae();
                        if (coordinate.get("longitude")!=null && coordinate.get("latitude")!="") {
                            longitude = coordinate.get("longitude");
                            latitude = coordinate.get("latitude");
                            hts.setLongitude(Float.parseFloat(longitude));
                            hts.setLatitude(Float.parseFloat(latitude));
                        } else {
                            longitude = "0.0";
                            latitude = "0.0";
                            hts.setLongitude(Float.parseFloat(longitude));
                            hts.setLatitude(Float.parseFloat(latitude));
                        }

                        name = surname.getText().toString() + "  " + otherName.getText().toString();
                        Integer autoGeneratedClientCode = new HtsDAO(getContext()).getAutGeneratedClientCode();
                         String auoIncrementClientCode2 = steteId + "/" + facilityId + "/" + autoGeneratedClientCode;
                        clientCode.setText(auoIncrementClientCode2);

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
                        hts.setAgeUnit(ageUnit.getSelectedItem().toString());
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
                        id = new HtsDAO(getContext()).addTestResult(hts);
                        FancyToast.makeText(getContext(), "Test Result saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        Integer autoGeneratedClientCode1 = new HtsDAO(getContext()).getAutGeneratedClientCode();
                        String auoIncrementClientCode3 = steteId + "/" + facilityId + "/" + autoGeneratedClientCode1;
                        clientCode.setText(auoIncrementClientCode3);
                        dateVisit.setText("");
                        clientCode.setText(clientCode.getText().toString());
                        surname.setText("");
                        otherName.setText("");
                        dateBirth.setText("");
                        age.setText("");
                        phone.setText("");
                        address.setText("");
                        indexClientCode.setText("");
                        comments.setText("");
                        SettingConfig.setSpinText(maritalStatus, "");
                        SettingConfig.setSpinText(state, "");
                        SettingConfig.setSpinText(lgas, "");
                        SettingConfig.setSpinText(ageUnit, "");
                        SettingConfig.setSpinText(gender, "");
                        SettingConfig.setSpinText(firstTimeVisit, "");
                        SettingConfig.setSpinText(numChildren, "");
                        SettingConfig.setSpinText(numWives, "");
                        SettingConfig.setSpinText(typeCounseling, "");
                        SettingConfig.setSpinText(typeIndex, "");
                        SettingConfig.setSpinText(knowledgeAssessment1, "");
                        SettingConfig.setSpinText(knowledgeAssessment2, "");
                        SettingConfig.setSpinText(knowledgeAssessment3, "");
                        SettingConfig.setSpinText(knowledgeAssessment4, "");
                        SettingConfig.setSpinText(knowledgeAssessment5, "");
                        SettingConfig.setSpinText(knowledgeAssessment6, "");
                        SettingConfig.setSpinText(knowledgeAssessment7, "");
                        SettingConfig.setSpinText(riskAssessment1, "");
                        SettingConfig.setSpinText(riskAssessment2, "");
                        SettingConfig.setSpinText(riskAssessment3, "");
                        SettingConfig.setSpinText(riskAssessment4, "");
                        SettingConfig.setSpinText(riskAssessment5, "");
                        SettingConfig.setSpinText(riskAssessment6, "");
                        SettingConfig.setSpinText(tbScreening1, "");
                        SettingConfig.setSpinText(tbScreening2, "");
                        SettingConfig.setSpinText(tbScreening3, "");
                        SettingConfig.setSpinText(tbScreening4, "");
                        SettingConfig.setSpinText(stiScreening1, "");
                        SettingConfig.setSpinText(stiScreening2, "");
                        SettingConfig.setSpinText(stiScreening3, "");
                        SettingConfig.setSpinText(stiScreening4, "");
                        SettingConfig.setSpinText(stiScreening5, "");
                        SettingConfig.setSpinText(postTest1, "");
                        SettingConfig.setSpinText(postTest2, "");
                        SettingConfig.setSpinText(postTest3, "");
                        SettingConfig.setSpinText(postTest4, "");
                        SettingConfig.setSpinText(postTest5, "");
                        SettingConfig.setSpinText(postTest6, "");
                        SettingConfig.setSpinText(postTest7, "");
                        SettingConfig.setSpinText(postTest8, "");
                        SettingConfig.setSpinText(postTest9, "");
                        SettingConfig.setSpinText(postTest10, "");
                        SettingConfig.setSpinText(postTest11, "");
                        SettingConfig.setSpinText(postTest12, "");
                        SettingConfig.setSpinText(postTest13, "");
                        SettingConfig.setSpinText(postTest14, "");
                        stiReferred.setVisibility(View.INVISIBLE);
                        artReferred.setVisibility(View.INVISIBLE);
                        if (hivTestResult.getSelectedItem().toString().equalsIgnoreCase("Positive")) {
                            showAlert(id);
                        } else {

                        }

                    }
                }
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        coordinate = session.getCoordnitae();
        if (coordinate.get("longitude")!=null && coordinate.get("latitude")!="") {
            longitude = coordinate.get("longitude");
            latitude = coordinate.get("latitude");
            NotificationService notificationService = new NotificationService(getContext());
            notificationService.createNotification("LAMISLite", "Your device location is captured");
        } else {
            NotificationService notificationService = new NotificationService(getContext());
            notificationService.createNotification("LAMISLite", "Enable your GPS Location");
            longitude = "0.0";
            latitude = "0.0";
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String indexClientCode2, surname2, otherName2, dateBirth2, phone2, dateVist2, address1,
                testingSetting2, gender2, firstTimeVisit2, state2, lga2, maritalStatus2, numChildren2, numWives2,
                typeCounseling2, indexClient2, typeIndex2, referredFrom2,
                knowledgeAssessment12, knowledgeAssessment22, knowledgeAssessment32, knowledgeAssessment42,
                knowledgeAssessment52, knowledgeAssessment62, knowledgeAssessment72,
                riskAssessment12, riskAssessment22, riskAssessment32, riskAssessment42, riskAssessment52, riskAssessment62,
                tbScreening12, tbScreening22, tbScreening32, tbScreening42, stiScreening12, stiScreening22, stiScreening32,
                stiScreening42, stiScreening52, hivTestResult2, testedHiv2222, testedHiv222, postTest1111, postTest21, postTest31,
                postTest41, postTest51,
                postTest61, postTest71, postTest81, postTest91, postTest101, postTest111, postTest121,
                postTest131, postTest141, syphilisTestResult1, hepatitiscTestResult1, hepatitisbTestResult1, ageUnit1,
                stiReferred1, tbReferred1, artReferred1;
        coordinate = session.getCoordnitae();
        if (coordinate.get("longitude")!=null && coordinate.get("latitude")!="") {
            longitude = coordinate.get("longitude");
            latitude = coordinate.get("latitude");
            NotificationService notificationService = new NotificationService(getContext());
            notificationService.createNotification("LAMISLite", "Your device location is captured");
        } else {
            NotificationService notificationService = new NotificationService(getContext());
            notificationService.createNotification("LAMISLite", "Enable your GPS Location");
            longitude = "0.0";
            latitude = "0.0";
        }

        HashMap<String, String> user1 = session.getHtsInstance();
        indexClientCode2 = user1.get("indexClientCode");
        surname2 = user1.get("surname");
        otherName2 = user1.get("otherName");
        dateBirth2 = user1.get("dateBirth");
        dateVist2 = user1.get("dateVisit");
        phone2 = user1.get("phone");
        String age1 = user1.get("age");
        address1 = user1.get("address");
        testingSetting2 = user1.get("testingSetting");
        gender2 = user1.get("gender");
        firstTimeVisit2 = user1.get("firstTimeVisit");
        state2 = user1.get("state");
        lga2 = user1.get("lga");
        maritalStatus2 = user1.get("maritalStatus");
        numChildren2 = user1.get("numChildren");
        numWives2 = user1.get("numWives");
        typeCounseling2 = user1.get("typeCounseling");
        indexClient2 = user1.get("indexClient");
        typeIndex2 = user1.get("typeIndex");
        referredFrom2 = user1.get("referredFrom");
        String auoIncrementClientCode3 = user1.get("auoIncrementClientCode");
        clientCode.setText(auoIncrementClientCode);
        knowledgeAssessment12 = user1.get("knowledgeAssessment1");
        knowledgeAssessment22 = user1.get("knowledgeAssessment2");
        knowledgeAssessment32 = user1.get("knowledgeAssessment3");
        knowledgeAssessment42 = user1.get("knowledgeAssessment4");
        knowledgeAssessment52 = user1.get("knowledgeAssessment5");
        knowledgeAssessment62 = user1.get("knowledgeAssessment6");
        knowledgeAssessment72 = user1.get("knowledgeAssessment7");
        riskAssessment12 = user1.get("riskAssessment1");
        riskAssessment22 = user1.get("riskAssessment2");
        riskAssessment32 = user1.get("riskAssessment3");
        riskAssessment42 = user1.get("riskAssessment4");
        riskAssessment52 = user1.get("riskAssessment5");
        riskAssessment62 = user1.get("riskAssessment6");
        tbScreening12 = user1.get("tbScreening1");
        tbScreening22 = user1.get("tbScreening2");
        tbScreening32 = user1.get("tbScreening3");
        tbScreening42 = user1.get("tbScreening4");
        stiScreening12 = user1.get("stiScreening1");
        stiScreening22 = user1.get("stiScreening2");
        stiScreening32 = user1.get("stiScreening3");
        stiScreening42 = user1.get("stiScreening4");
        stiScreening52 = user1.get("stiScreening5");
        hivTestResult2 = user1.get("hivTestResult");
        testedHiv2222 = user1.get("testedHiv");
        testedHiv222 = user1.get("testedHiv2");
        postTest1111 = user1.get("postTest1");
        postTest21 = user1.get("postTest2");
        postTest31 = user1.get("postTest3");
        postTest41 = user1.get("postTest4");
        postTest51 = user1.get("postTest5");
        postTest61 = user1.get("postTest6");
        postTest71 = user1.get("postTest7");
        postTest81 = user1.get("postTest8");
        postTest91 = user1.get("postTest9");
        postTest101 = user1.get("postTest10");
        postTest111 = user1.get("postTest11");
        postTest121 = user1.get("postTest12");
        postTest131 = user1.get("postTest13");
        postTest141 = user1.get("postTest14");
        syphilisTestResult1 = user1.get("syphilisTestResult");
        hepatitiscTestResult1 = user1.get("hepatitiscTestResult");
        hepatitisbTestResult1 = user1.get("hepatitisbTestResult");
        ageUnit1 = user1.get("ageUnit");
        String commentss = user1.get("comments");
        indexClientCode.setText(indexClientCode2);
        surname.setText(surname2);
        otherName.setText(otherName2);
        dateBirth.setText(dateBirth2);
        phone.setText(phone2);
        dateVisit.setText(dateVist2);
        comments.setText(commentss);
        age.setText(age1);
        address.setText(address1);
        SettingConfig.setSpinText(testingSetting, testingSetting2);
        SettingConfig.setSpinText(gender, gender2);
        SettingConfig.setSpinText(firstTimeVisit, firstTimeVisit2);
        SettingConfig.setSpinText(state, state2);
        SettingConfig.setSpinText(lgas, lga2);
        SettingConfig.setSpinText(maritalStatus, maritalStatus2);
        SettingConfig.setSpinText(numChildren, numChildren2);
        SettingConfig.setSpinText(numWives, numWives2);
        SettingConfig.setSpinText(typeCounseling, typeCounseling2);
        SettingConfig.setSpinText(indexClient, indexClient2);
        SettingConfig.setSpinText(typeIndex, typeIndex2);
        SettingConfig.setSpinText(referredFrom, referredFrom2);
        SettingConfig.setSpinText(knowledgeAssessment1, knowledgeAssessment12);
        SettingConfig.setSpinText(knowledgeAssessment2, knowledgeAssessment22);
        SettingConfig.setSpinText(knowledgeAssessment3, knowledgeAssessment32);
        SettingConfig.setSpinText(knowledgeAssessment4, knowledgeAssessment42);
        SettingConfig.setSpinText(knowledgeAssessment5, knowledgeAssessment52);
        SettingConfig.setSpinText(knowledgeAssessment6, knowledgeAssessment62);
        SettingConfig.setSpinText(knowledgeAssessment7, knowledgeAssessment72);
        SettingConfig.setSpinText(riskAssessment1, riskAssessment12);
        SettingConfig.setSpinText(riskAssessment2, riskAssessment22);
        SettingConfig.setSpinText(riskAssessment3, riskAssessment32);
        SettingConfig.setSpinText(riskAssessment4, riskAssessment42);
        SettingConfig.setSpinText(riskAssessment5, riskAssessment52);
        SettingConfig.setSpinText(riskAssessment6, riskAssessment62);
        SettingConfig.setSpinText(tbScreening1, tbScreening12);
        SettingConfig.setSpinText(tbScreening2, tbScreening22);
        SettingConfig.setSpinText(tbScreening3, tbScreening32);
        SettingConfig.setSpinText(tbScreening4, tbScreening42);
        SettingConfig.setSpinText(stiScreening1, stiScreening12);
        SettingConfig.setSpinText(stiScreening2, stiScreening22);
        SettingConfig.setSpinText(stiScreening3, stiScreening32);
        SettingConfig.setSpinText(stiScreening4, stiScreening42);
        SettingConfig.setSpinText(stiScreening5, stiScreening52);
        SettingConfig.setSpinText(hivTestResult, hivTestResult2);
        SettingConfig.setSpinText(testedHiv2, testedHiv222);
        SettingConfig.setSpinText(postTest1, postTest1111);
        SettingConfig.setSpinText(postTest12, postTest21);
        SettingConfig.setSpinText(postTest3, postTest31);
        SettingConfig.setSpinText(postTest4, postTest41);
        SettingConfig.setSpinText(postTest5, postTest51);
        SettingConfig.setSpinText(postTest6, postTest61);
        SettingConfig.setSpinText(postTest7, postTest71);
        SettingConfig.setSpinText(postTest8, postTest81);
        SettingConfig.setSpinText(postTest9, postTest91);
        SettingConfig.setSpinText(postTest10, postTest101);
        SettingConfig.setSpinText(postTest11, postTest111);
        SettingConfig.setSpinText(postTest12, postTest121);
        SettingConfig.setSpinText(postTest13, postTest131);
        SettingConfig.setSpinText(postTest14, postTest131);
        SettingConfig.setSpinText(syphilisTestResult, syphilisTestResult1);
        SettingConfig.setSpinText(hepatitiscTestResult, hepatitiscTestResult1);
        SettingConfig.setSpinText(hepatitisbTestResult, hepatitisbTestResult1);
        SettingConfig.setSpinText(ageUnit, ageUnit1);
        SettingConfig.setSpinText(syphilisTestResult, syphilisTestResult1);


    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("holdState", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("indexClientCode", indexClientCode.getText().toString());
        editor.putString("surname", surname.getText().toString());
        editor.putString("otherName", otherName.getText().toString());
        editor.putString("dateBirth", dateBirth.getText().toString());
        editor.putString("dateVisit", dateVisit.getText().toString());
        editor.putString("phone", phone.getText().toString());
        editor.putString("testingSetting", testingSetting.getSelectedItem().toString());
        editor.putString("gender", gender.getSelectedItem().toString());
        editor.putString("firstTimeVisit", firstTimeVisit.getSelectedItem().toString());
        editor.putString("state", state.getSelectedItem().toString());
        editor.putString("lga", lgas.getSelectedItem().toString());
        editor.putString("address", address.getText().toString());
        editor.putString("auoIncrementClientCode", auoIncrementClientCode);
        editor.putString("maritalStatus", maritalStatus.getSelectedItem().toString());
        editor.putString("numChildren", numChildren.getSelectedItem().toString());
        editor.putString("numWives", numWives.getSelectedItem().toString());
        editor.putString("typeCounseling", typeCounseling.getSelectedItem().toString());
        editor.putString("indexClient", indexClient.getSelectedItem().toString());
        editor.putString("typeIndex", typeIndex.getSelectedItem().toString());
        editor.putString("referredFrom", referredFrom.getSelectedItem().toString());
        editor.putString("knowledgeAssessment1", knowledgeAssessment1.getSelectedItem().toString());
        editor.putString("knowledgeAssessment2", knowledgeAssessment2.getSelectedItem().toString());
        editor.putString("knowledgeAssessment3", knowledgeAssessment3.getSelectedItem().toString());
        editor.putString("knowledgeAssessment4", knowledgeAssessment4.getSelectedItem().toString());
        editor.putString("knowledgeAssessment5", knowledgeAssessment5.getSelectedItem().toString());
        editor.putString("knowledgeAssessment6", knowledgeAssessment6.getSelectedItem().toString());
        editor.putString("knowledgeAssessment7", knowledgeAssessment7.getSelectedItem().toString());
        editor.putString("riskAssessment1", riskAssessment1.getSelectedItem().toString());
        editor.putString("riskAssessment2", riskAssessment2.getSelectedItem().toString());
        editor.putString("riskAssessment3", riskAssessment3.getSelectedItem().toString());
        editor.putString("riskAssessment4", riskAssessment4.getSelectedItem().toString());
        editor.putString("riskAssessment5", riskAssessment5.getSelectedItem().toString());
        editor.putString("riskAssessment6", riskAssessment6.getSelectedItem().toString());
        editor.putString("tbScreening1", tbScreening1.getSelectedItem().toString());
        editor.putString("tbScreening2", tbScreening2.getSelectedItem().toString());
        editor.putString("tbScreening3", tbScreening3.getSelectedItem().toString());
        editor.putString("tbScreening4", tbScreening4.getSelectedItem().toString());
        editor.putString("stiScreening1", stiScreening1.getSelectedItem().toString());
        editor.putString("stiScreening2", stiScreening2.getSelectedItem().toString());
        editor.putString("stiScreening3", stiScreening3.getSelectedItem().toString());
        editor.putString("stiScreening4", stiScreening4.getSelectedItem().toString());
        editor.putString("stiScreening5", stiScreening5.getSelectedItem().toString());
        editor.putString("hivTestResult", hivTestResult.getSelectedItem().toString());
        editor.putString("testedHiv2", testedHiv2.getSelectedItem().toString());
        editor.putString("postTest1", postTest1.getSelectedItem().toString());
        editor.putString("postTest2", postTest2.getSelectedItem().toString());
        editor.putString("postTest3", postTest3.getSelectedItem().toString());
        editor.putString("postTest4", postTest4.getSelectedItem().toString());
        editor.putString("postTest5", postTest5.getSelectedItem().toString());
        editor.putString("postTest6", postTest6.getSelectedItem().toString());
        editor.putString("postTest7", postTest7.getSelectedItem().toString());
        editor.putString("postTest8", postTest8.getSelectedItem().toString());
        editor.putString("postTest9", postTest9.getSelectedItem().toString());
        editor.putString("postTest10", postTest10.getSelectedItem().toString());
        editor.putString("postTest11", postTest11.getSelectedItem().toString());
        editor.putString("postTest12", postTest12.getSelectedItem().toString());
        editor.putString("postTest13", postTest13.getSelectedItem().toString());
        editor.putString("postTest14", postTest14.getSelectedItem().toString());
        editor.putString("syphilisTestResult", syphilisTestResult.getSelectedItem().toString());
        editor.putString("hepatitiscTestResult", hepatitiscTestResult.getSelectedItem().toString());
        editor.putString("hepatitisbTestResult", hepatitisbTestResult.getSelectedItem().toString());
        editor.putString("ageUnit", ageUnit.getSelectedItem().toString());
        editor.putString("comment", comments.getText().toString());
        editor.commit();


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

    private void setupFloatingLabelError(View root) {
        final TextInputLayout floatingUsernameLabel = root.findViewById(R.id.invalidPhone);
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

    private void setupFloatingLabelErrorAge(View root) {
        final TextInputLayout floatingUsernameLabel = root.findViewById(R.id.inputLayoutAge);
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
            FancyToast.makeText(getContext(), "Enter surname", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;

        } else if (dateOfBirth.isEmpty()) {
            dateBirth.setError("Enter date of birth");
            FancyToast.makeText(getContext(), "Enter date of birth", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;
        } else if (age1.isEmpty()) {
            age.setError("Enter age");
            FancyToast.makeText(getContext(), "Enter age", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;

        } else if (dateVisits.isEmpty()) {
            dateVisit.setError("Enter date of visit");
            FancyToast.makeText(getContext(), "Enter date of visit", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;

        }

        return true;


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
            FancyToast.makeText(getContext(), "SettingConfig can not be empty", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
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

    private void showAlert(final long htsIds) {
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.pop_up, null);
        final AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        dialog.setView(promptsView);
        final TextView notitopOk, notitopNotnow;
        notitopOk = promptsView.findViewById(R.id.notitopOk);
        notitopNotnow = promptsView.findViewById(R.id.notitopNotnow);
        notitopNotnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("profielDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("htsId", htsIds + "");
                editor.putString("name", name + "");
                editor.putString("clientcode", clientCode + "");
                Hts2 hts2 = new Hts2();
                hts2.setHtsId(htsIds);
                new HtsDAO(getContext()).updateContact1(hts2, "0");
                editor.commit();
                Intent intent=new Intent(getContext(), Home.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        notitopOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), IndexTesting.class);
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("profielDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("htsId", htsIds + "");
                editor.putString("name", name + "");
                editor.putString("clientcode", clientCode + "");
                Hts2 hts2 = new Hts2();
                hts2.setHtsId(htsIds);
                new HtsDAO(getContext()).updateContact1(hts2, "1");
                editor.commit();
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }


}