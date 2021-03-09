package org.fhi360.lamis.mobile.lite.Fragments;

import android.app.DatePickerDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.lamis.mobile.lite.DAO.LgaDAO;
import org.fhi360.lamis.mobile.lite.DAO.PatientDAO;
import org.fhi360.lamis.mobile.lite.DAO.StateDAO;
import org.fhi360.lamis.mobile.lite.Domains.Facility2;
import org.fhi360.lamis.mobile.lite.Domains.Lga;
import org.fhi360.lamis.mobile.lite.Domains.Patient;
import org.fhi360.lamis.mobile.lite.Domains.State;
import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;
import org.fhi360.lamis.mobile.lite.Utils.SettingConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class PatientRegistrationFragment extends Fragment {
    private EditText phoneKin, addressKin, dateRegistration, nextKinName, hospitalNum, uniqueId, surname, otherNames, dateBirth, age, dateConfirmedHiv, address, phone;
    private AppCompatSpinner relationKin, pregnancyStatus,statusRegistration, tbStatus, ageUnit, gender, maritalStatus, occupation, education, state, lga, entryPoint;
    private Button save;
    private  String lgaName;
    private String facilityName;
    private String stateName;
    private String lgaId;
    private  String steteId;
    private String facilityId;
    private PrefManager session;
    private TextView pregnancyStatus2;
    private SettingConfig settingConfig = new SettingConfig();
    private HashMap<String, String> user = null;
    private HashMap<String, String> user1 = null;
    private Calendar myCalendar = Calendar.getInstance();
    private String deviceconfigId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.patient_registration, container, false);
        session = new PrefManager(getContext());
        phoneKin = rootView.findViewById(R.id.phoneKin);
        addressKin = rootView.findViewById(R.id.addressKin);
        dateRegistration = rootView.findViewById(R.id.dateRegistrations);
        statusRegistration = rootView.findViewById(R.id.statusRegistration);
        pregnancyStatus2 = rootView.findViewById(R.id.pregnancyStatus2);
        nextKinName = rootView.findViewById(R.id.nextKinName);
        hospitalNum = rootView.findViewById(R.id.hospitalNum);
        uniqueId = rootView.findViewById(R.id.uniqueId);
        surname = rootView.findViewById(R.id.surnames);
        otherNames = rootView.findViewById(R.id.otherNamess);
        dateBirth = rootView.findViewById(R.id.dateBirthEnrollemt);
        age = rootView.findViewById(R.id.ageEnrollemt);
        age.setEnabled(false);
        dateConfirmedHiv = rootView.findViewById(R.id.dateConfirmedHiv);
        address = rootView.findViewById(R.id.addresss);
        relationKin = rootView.findViewById(R.id.relationKin);
        pregnancyStatus = rootView.findViewById(R.id.pregnancyStatus1);
        tbStatus = rootView.findViewById(R.id.tbStatus1s);
        ageUnit = rootView.findViewById(R.id.ageUnits);
        gender = rootView.findViewById(R.id.genders);
        maritalStatus = rootView.findViewById(R.id.maritalStatuss);
        occupation = rootView.findViewById(R.id.occupations);
        education = rootView.findViewById(R.id.educations);
        state = rootView.findViewById(R.id.states);
        lga = rootView.findViewById(R.id.lgapat);
        entryPoint = rootView.findViewById(R.id.spinner_entry_point);
        phone = rootView.findViewById(R.id.phones);
        save = rootView.findViewById(R.id.finishButton);

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
                ArrayList<Lga> lga1;
                lga1 = new LgaDAO(getContext()).getLgaByStateId(stateId);
                for (Lga lga2 : lga1) {
                    lgaIds.add(lga2.getId());
                    arrayListLgaName.add(lga2.getName());

                }

                final ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(getContext(),
                        R.layout.spinner_items, arrayListLgaName);
                districtAdapter.setDropDownViewResource(R.layout.color_spinner_layout);
                districtAdapter.notifyDataSetChanged();
                lga.setAdapter(districtAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        dateConfirmedHiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                final DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();


            }
        });


        final DatePickerDialog.OnDateSetListener date3 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabe3();
            }

        };


        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (gender.getSelectedItem().toString().equals("Male")) {
                    pregnancyStatus.setVisibility(View.INVISIBLE);
                    pregnancyStatus2.setVisibility(View.INVISIBLE);
                } else if (gender.getSelectedItem().toString().equals("Female")) {
                    pregnancyStatus.setVisibility(View.VISIBLE);
                    pregnancyStatus2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dateRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), date3, myCalendar
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
                final DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();


            }
        });

        setupFloatingLabelError(rootView);
        save.setOnClickListener(v -> {

            user = session.getHtsDetails();
            if (user != null) {
                facilityName = user.get("faciltyName");
                lgaId = user.get("lgaId");
                steteId = user.get("stateId");
                facilityId = user.get("facilityId");
                stateName = user.get("steteName");
                lgaName = user.get("lgaNam");
                deviceconfigId=user.get("deviceconfig_id");
            }
            if (validateInput1(hospitalNum.getText().toString(), uniqueId.getText().toString(), surname.getText().toString(), otherNames.getText().toString(), age.getText().toString(), dateBirth.getText().toString())) {
                if (gender.getSelectedItem().toString().equals("")) {
                    FancyToast.makeText(getContext(), "Select gender", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                } else if (new PatientDAO(getContext()).findCheckIfPatientExistByHostipitalNumber(hospitalNum.getText().toString())) {
                    FancyToast.makeText(getContext(), "Patient already exist", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                } else {
                    Patient patient = new Patient();
                    Facility2 facility2 = new Facility2();
                    facility2.setFacilityId(Long.parseLong(facilityId));
                    patient.setFacility(facility2);
                    patient.setHospitalNum(hospitalNum.getText().toString());
                    patient.setUniqueId(uniqueId.getText().toString());
                    patient.setSurname(surname.getText().toString());
                    patient.setOtherNames(otherNames.getText().toString());
                    session.createHospitalNumber1(uniqueId.getText().toString(), surname.getText().toString() + "   " + otherNames.getText().toString(), uniqueId.getText().toString());
                    patient.setGender(gender.getSelectedItem().toString());
                    patient.setDateBirth(dateBirth.getText().toString());
//                    if (age.getText().toString().equals("")) {
//                        patient.setAge(0);
//                    } else {
//                        patient.setAge(Integer.parseInt(age.getText().toString()));
//                    }
                  // patient.setStatusRegistration(statusRegistration.getSelectedItem().toString());
                    patient.setMaritalStatus(maritalStatus.getSelectedItem().toString());
                    patient.setEducation(education.getSelectedItem().toString());
                    patient.setOccupation(occupation.getSelectedItem().toString());
                    patient.setAddress(address.getText().toString());
                    patient.setPhone(phone.getText().toString());
                    patient.setState(state.getSelectedItem().toString());
                    if (state.getSelectedItem().toString().equals("")) {
                        patient.setLga("");
                    } else {
                        patient.setLga(lga.getSelectedItem().toString());
                    }

                    patient.setDeviceconfigId(Long.parseLong(deviceconfigId));
                    patient.setNextKin(nextKinName.getText().toString());
                    patient.setAddressKin(addressKin.getText().toString());
                    patient.setPhoneKin(phoneKin.getText().toString());
                    patient.setRelationKin(relationKin.getSelectedItem().toString());
                    patient.setEntryPoint(entryPoint.getSelectedItem().toString());
                    patient.setEducation(education.getSelectedItem().toString());
                    patient.setOccupation(occupation.getSelectedItem().toString());
                    patient.setTargetGroup("");//see org
                    patient.setDateRegistration(dateRegistration.getText().toString());
                    patient.setDateConfirmedHiv(dateConfirmedHiv.getText().toString());
                    patient.setTbStatus(tbStatus.getSelectedItem().toString());

                    if (pregnancyStatus.getSelectedItem().toString().equals("Pregnant")) {
                        patient.setPregnant(1);
                        patient.setBreastfeeding(0);
                    } else if (pregnancyStatus.getSelectedItem().toString().equals("Breastfeeding")) {
                        patient.setPregnant(0);
                        patient.setBreastfeeding(1);
                    } else if (pregnancyStatus.getSelectedItem().toString().equals("Not Pregnant")) {
                        patient.setPregnant(0);
                        patient.setBreastfeeding(0);
                    } else {
                        patient.setPregnant(0);
                        patient.setBreastfeeding(0);
                    }
                    //patient.setUserId(0);//see oga

                    new PatientDAO(getContext()).insertPatient(patient);

                    FancyToast.makeText(getContext(), "Patient saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                    hospitalNum.setText("");
                    uniqueId.setText("");
                    surname.setText("");
                    otherNames.setText("");
                    SettingConfig.setSpinText(gender, "");
                    dateBirth.setText("");
                    age.setText("");
                    SettingConfig.setSpinText(maritalStatus, "");
                    address.setText("");
                    phone.setText("");
                    SettingConfig.setSpinText(state, "");
                    SettingConfig.setSpinText(lga, "");
                    SettingConfig.setSpinText(ageUnit, "");
                    SettingConfig.setSpinText(education, "");
                    SettingConfig.setSpinText(occupation, "");
                    nextKinName.setText("");
                    addressKin.setText("");
                    phoneKin.setText("");
                    dateConfirmedHiv.setText("");
                    SettingConfig.setSpinText(tbStatus, "");
                    SettingConfig.setSpinText(pregnancyStatus, "");

                }
            }
        });


        return rootView;


    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateConfirmedHiv.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateLabe3() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateRegistration.setText(sdf.format(myCalendar.getTime()));

    }

    private void setupFloatingLabelError(View root) {
        final TextInputLayout floatingUsernameLabel = root.findViewById(R.id.invalidPhone);
        floatingUsernameLabel.getEditText().addTextChangedListener(new TextWatcher() {
            // ...
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

    private void updateLabe2() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
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


    private boolean validateInput1(String hospitalNum2, String uniqueId1, String
            surname1, String otherName1, String age1, String dateBirth1) {
        if (hospitalNum2.isEmpty()) {
            hospitalNum.setError("Enter Hospital number");
            //hospitalNum.setText(hospitalNum.getText().toString());
            FancyToast.makeText(getContext(), "Enter Hospital number", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;

        }

        if (uniqueId1.isEmpty()) {
            uniqueId.setError("Enter UniqueId");
            //hospitalNum.setText(hospitalNum.getText().toString());
            FancyToast.makeText(getContext(), "Enter UniqueId", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;

        }

        if (surname1.isEmpty()) {
            surname.setError("Enter surname");
            //surname.setText(surname.getText().toString());
            FancyToast.makeText(getContext(), "Enter surname", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;

        } else if (otherName1.isEmpty()) {
            otherNames.setError("Enter othername");
            // hospitalNum.setText(hospitalNum.getText().toString());
            FancyToast.makeText(getContext(), "Enter other name", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;

        }
        if (dateBirth1.isEmpty()) {
            dateBirth.setError("Enter Date of Birth");
            //hospitalNum.setText(hospitalNum.getText().toString());
            FancyToast.makeText(getContext(), "Enter Date of Birth", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;

        }
        if (age1.isEmpty()) {
            age.setError("Enter age");
            //hospitalNum.setText(hospitalNum.getText().toString());
            FancyToast.makeText(getContext(), "Enter age", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;

        }


//        if (gender1.equals(gender1)) {
//            //gender.setError("Enter gender");
//            //hospitalNum.setText(hospitalNum.getText().toString());
//            FancyToast.makeText(getContext(), "Select gender", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//            return false;
//
//        }

//        if (state1.equals(state1)) {
//            //hospitalNum.setText(hospitalNum.getText().toString());
//            FancyToast.makeText(getContext(), "Select state", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//            return false;
//
//        }
//        if (lga1.equals(lga1)) {
//            //hospitalNum.setText(hospitalNum.getText().toString());
//            FancyToast.makeText(getContext(), "Select lga", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//            return false;
//
//        }
        return true;


    }


}
