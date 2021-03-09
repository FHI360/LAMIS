package org.fhi360.lamis.mobile.lite.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.lamis.mobile.lite.DAO.DeleteDAO;
import org.fhi360.lamis.mobile.lite.DAO.HtsDAO;
import org.fhi360.lamis.mobile.lite.DAO.LgaDAO;
import org.fhi360.lamis.mobile.lite.DAO.PatientDAO;
import org.fhi360.lamis.mobile.lite.DAO.StateDAO;
import org.fhi360.lamis.mobile.lite.Fragments.PatientRegistrationFragment;
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

public class EditPatient3 extends AppCompatActivity {
    private EditText phoneKin, addressKin, dateRegistration, statusRegistration, nextKinName, hospitalNum, uniqueId, surname, otherNames, dateBirth, age, dateConfirmedHiv, address, phone;
    private AppCompatSpinner gender, state, lga, ageUnit, relationKin, pregnancyStatus, maritalStatus, tbStatus, occupation, education, entryPoint;
    private Button save;
    private  String lgaName;
    private String facilityName;
    private String stateName;
    private String lgaId;
    private String steteId;
    private String facilityId;
    private String htsId;
    private TextView pregnancyStatus2,header;
    private PrefManager session;
    private SettingConfig settingConfig = new SettingConfig();
    private HashMap<String, String> user = null;
    private HashMap<String, String> user1 = null;
    private Calendar myCalendar = Calendar.getInstance();
    private  String deviceconfigId;
    private  String patientId;
    private Patient patient;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_registration);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorPrimaryDark)));
        session = new PrefManager(getApplicationContext());
        phoneKin = findViewById(R.id.phoneKin);
        addressKin = findViewById(R.id.addressKin);
        dateRegistration = findViewById(R.id.dateRegistrations);
        pregnancyStatus2 = findViewById(R.id.pregnancyStatus2);
        nextKinName = findViewById(R.id.nextKinName);
        hospitalNum = findViewById(R.id.hospitalNum);
        uniqueId = findViewById(R.id.uniqueId);
        surname = findViewById(R.id.surnames);
        otherNames = findViewById(R.id.otherNamess);
        dateBirth = findViewById(R.id.dateBirthEnrollemt);
        age = findViewById(R.id.ageEnrollemt);
        dateConfirmedHiv = findViewById(R.id.dateConfirmedHiv);
        address = findViewById(R.id.addresss);
        relationKin = findViewById(R.id.relationKin);
        pregnancyStatus = findViewById(R.id.pregnancyStatus1);
        tbStatus = findViewById(R.id.tbStatus1s);
        ageUnit = findViewById(R.id.ageUnits);
        gender = findViewById(R.id.genders);
        header=findViewById(R.id.header);
        maritalStatus = findViewById(R.id.maritalStatuss);
        occupation = findViewById(R.id.occupations);
        education = findViewById(R.id.educations);
        state = findViewById(R.id.states);
        lga = findViewById(R.id.lgapat);
        entryPoint = findViewById(R.id.spinner_entry_point);
        phone = findViewById(R.id.phones);
        save = findViewById(R.id.finishButton);
        header.setText("Edit Patient");
        final HashMap<String, String> patientId1 = session.getPatientId();
        user = session.getHtsDetails();
        if (patientId1 != null) {
            patientId = patientId1.get("patientId");

        }
         patient = new PatientDAO(this).findPatientHtsId1(Long.parseLong(patientId));
        if (patient != null) {
            hospitalNum.setText(patient.getHospitalNum());
            uniqueId.setText(patient.getUniqueId());
            SettingConfig.setSpinText(occupation, patient.getOccupation());
            SettingConfig.setSpinText(education, patient.getEducation());
            SettingConfig.setSpinText(entryPoint, patient.getEntryPoint());
            SettingConfig.setSpinText(tbStatus, patient.getTbStatus());
            nextKinName.setText(patient.getNextKin());
            addressKin.setText(patient.getAddressKin());
            SettingConfig.setSpinText(relationKin, patient.getRelationKin());
            dateRegistration.setText(patient.getDateRegistration());
            phoneKin.setText(patient.getPhoneKin());
            String gender1 = patient.getGender();
            if (gender1=="Male") {
                pregnancyStatus.setVisibility(View.INVISIBLE);
                pregnancyStatus2.setVisibility(View.INVISIBLE);
            } else {
                if (patient.getPregnant() == 0) {
                    SettingConfig.setSpinText(pregnancyStatus, "Not Pregnant");
                } else if (patient.getPregnant() == 1) {
                    SettingConfig.setSpinText(pregnancyStatus, "Pregnant");
                } else {
                    SettingConfig.setSpinText(pregnancyStatus, "Breastfeeding");
                }
                pregnancyStatus2.setVisibility(View.VISIBLE);
                pregnancyStatus.setVisibility(View.VISIBLE);
            }

            surname.setText(patient.getSurname());
            if (patient.getOccupation() == null) {

            } else {
                SettingConfig.setSpinText(occupation, patient.getOccupation());
            }
            if (patient.getEducation() == null) {

            } else {
                SettingConfig.setSpinText(education, patient.getEducation());
            }

            SettingConfig.setSpinText(tbStatus, patient.getTbStatus());
            SettingConfig.setSpinText(entryPoint, patient.getEntryPoint());
            //otherNames.setEnabled(false);
            otherNames.setText(patient.getOtherNames());
            //age.setEnabled(false);
            age.setText(String.valueOf(patient.getAge()));
            SettingConfig.setSpinText(maritalStatus, patient.getMaritalStatus());

            SettingConfig.setSpinText(lga, patient.getLga());
            SettingConfig.setSpinText(state, patient.getState());

            address.setText(patient.getAddress());

            SettingConfig.setSpinText(gender, patient.getGender());


            phone.setText(patient.getPhone());

            dateBirth.setText(patient.getDateBirth());
            SettingConfig.setSpinText(ageUnit, patient.getAgeUnit());
            dateConfirmedHiv.setText(patient.getDateConfirmedHiv());
        } else if(patient==null) {
            showAlert();
        }


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


        dateRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(EditPatient3.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();


            }
        });

        ArrayList lgaIds = new ArrayList();

        final ArrayList arrayListStateId = new ArrayList();
        ArrayList arrayListStateName = new ArrayList();
        lgaIds = new ArrayList();
        ArrayList<State> states = new ArrayList<>();
        states = new StateDAO(getApplicationContext()).getStates();
        for (State state1 : states) {
            arrayListStateId.add(state1.getId());
            arrayListStateName.add(state1.getName());
        }


        final ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(getApplicationContext(),
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

                final ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(getApplicationContext(),
                        R.layout.spinner_items, arrayListLgaName);
                districtAdapter.setDropDownViewResource(R.layout.color_spinner_layout);
                districtAdapter.notifyDataSetChanged();
                lga.setAdapter(districtAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = session.getHtsDetails();
                if (user != null) {
                    facilityName = user.get("faciltyName");
                    lgaId = user.get("lgaId");
                    steteId = user.get("stateId");
                    facilityId = user.get("facilityId");
                    stateName = user.get("steteName");
                    lgaName = user.get("lgaNam");
                    deviceconfigId = user.get("deviceconfig_id");
                }
                Patient patient = new Patient();
                Facility2 facility2 = new Facility2();
                facility2.setFacilityId(Long.parseLong(facilityId));
                patient.setFacility(facility2);
                patient.setHospitalNum(hospitalNum.getText().toString());
                session.createHospitalNumber(hospitalNum.getText().toString(), surname.getText().toString() + "   " + otherNames.getText().toString(), uniqueId.getText().toString());
                patient.setUniqueId(uniqueId.getText().toString());
                patient.setSurname(surname.getText().toString());
                patient.setOtherNames(otherNames.getText().toString());
                patient.setGender(gender.getSelectedItem().toString());
                patient.setDateBirth(dateBirth.getText().toString());
                patient.setAge(Integer.parseInt(age.getText().toString()));
                patient.setMaritalStatus(maritalStatus.getSelectedItem().toString());
                patient.setEducation(education.getSelectedItem().toString());
                patient.setOccupation(occupation.getSelectedItem().toString());
                patient.setAddress(address.getText().toString());
                patient.setPhone(phone.getText().toString());
                patient.setHtsId(0);
                patient.setState(state.getSelectedItem().toString());
                patient.setLga(lga.getSelectedItem().toString());
                patient.setAgeUnit(ageUnit.getSelectedItem().toString());
                patient.setNextKin(nextKinName.getText().toString());
                patient.setAddressKin(addressKin.getText().toString());
                patient.setEducation(education.getSelectedItem().toString());
                patient.setOccupation(occupation.getSelectedItem().toString());
                patient.setPhoneKin(phoneKin.getText().toString());
                patient.setRelationKin(relationKin.getSelectedItem().toString());
                patient.setEntryPoint(entryPoint.getSelectedItem().toString());
                patient.setTargetGroup("");
                patient.setDeviceconfigId(Long.parseLong(deviceconfigId));
                patient.setPatientId(Long.parseLong(patientId));
                patient.setDateConfirmedHiv(dateConfirmedHiv.getText().toString());
                new HtsDAO(getApplicationContext()).updateDateRegistration(dateRegistration.getText().toString(), htsId);
                patient.setDateRegistration(dateRegistration.getText().toString());
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
                patient.setUserId(0);//see oga
                new PatientDAO(getApplicationContext()).updatePatient1(patient);
                FancyToast.makeText(getApplicationContext(), "Patient updated successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
            }

        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deletepatient, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.deletePatients) {
            showAlert(patient.getPatientId(), Long.valueOf(htsId));
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAlert(final long patientidss1,Long htsId) {
        LayoutInflater li = LayoutInflater.from(EditPatient3.this);
        View promptsView = li.inflate(R.layout.delete_pop_up, null);
        final AlertDialog dialog = new AlertDialog.Builder(EditPatient3.this).create();
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
                dialog.dismiss();
                new DeleteDAO(getApplicationContext()).removePatient(patientidss1,htsId);
                dialog.dismiss();
                FancyToast.makeText(getApplicationContext(), "Record deleted successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                Intent intent = new Intent(EditPatient3.this, Home.class);
                startActivity(intent);

            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }
    private void showAlert() {
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.edit_patients, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(promptsView);
        final TextView notitopOk;
        notitopOk = promptsView.findViewById(R.id.notitopOk);
        notitopOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PatientRegistrationFragment.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateRegistration.setText(sdf.format(myCalendar.getTime()));

    }

}
