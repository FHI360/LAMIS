package org.fhi360.lamis.mobile.lite.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.neurotec.lang.NCore;
import com.neurotec.samples.licensing.LicensingManager;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.lamis.mobile.lite.DAO.HtsDAO;
import org.fhi360.lamis.mobile.lite.DAO.PatientDAO;
import org.fhi360.lamis.mobile.lite.Domains.Facility2;
import org.fhi360.lamis.mobile.lite.Domains.Patient;
import org.fhi360.lamis.mobile.lite.FingerActivity;
import org.fhi360.lamis.mobile.lite.FingerActivity2;
import org.fhi360.lamis.mobile.lite.MainActivity;
import org.fhi360.lamis.mobile.lite.MainActivity2;
import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;
import org.fhi360.lamis.mobile.lite.Utils.SettingConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import static org.fhi360.lamis.mobile.lite.MainActivity.getAdditionalComponentsInternal;
import static org.fhi360.lamis.mobile.lite.MainActivity.getMandatoryComponentsInternal;

public class PatientRegistration extends AppCompatActivity {
    private EditText phoneKin, addressKin, dateRegistration, nextKinName, hospitalNum, ageUnit, maritalStatus, gender, state, lga, uniqueId, surname, otherNames, dateBirth, age, dateConfirmedHiv, address, phone;
    private AppCompatSpinner relationKin, pregnancyStatus, tbStatus, occupation, education, entryPoint, statusRegistration;
    private Button save;
    private String lgaName;
    private String facilityName;
    private String stateName;
    private String lgaId;
    private String steteId;
    private String facilityId;
    private String htsId;
    private TextView pregnancyStatus2;
    private PrefManager session;
    private String surname2, otherName2, dateBirth2, phone2, dateVist2, address1, age2, gender2, state2, lga2, maritalStatus2, ageUnits;
    private SettingConfig settingConfig = new SettingConfig();
    private HashMap<String, String> user = null;
    private HashMap<String, String> user1 = null;
    private Calendar myCalendar = Calendar.getInstance();
    private String deviceconfigId;
    private Patient patient;

    private final int MODALITY_CODE_FINGER = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patients);
        NCore.setContext(getApplicationContext());
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorPrimaryDark)));
        session = new PrefManager(getApplicationContext());
        phoneKin = findViewById(R.id.phoneKin);
        addressKin = findViewById(R.id.addressKin);
        statusRegistration = findViewById(R.id.statusRegistration);
        dateRegistration = findViewById(R.id.dateRegistrationss);
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

        maritalStatus = findViewById(R.id.maritalStatus1);
        occupation = findViewById(R.id.occupations);
        education = findViewById(R.id.educations);
        state = findViewById(R.id.states);
        lga = findViewById(R.id.lgas);
        entryPoint = findViewById(R.id.spinner_entry_point);
        phone = findViewById(R.id.phones);
        save = findViewById(R.id.finishButton);


        HashMap<String, String> user1 = session.getProfileDetails();
        surname2 = user1.get("surname");
        otherName2 = user1.get("othernames");
        age2 = user1.get("age");
        address1 = user1.get("address");
        phone2 = user1.get("phone");
        gender2 = user1.get("gender");
        dateVist2 = user1.get("dateVisit");
        dateBirth2 = user1.get("dateBirth");
        lga2 = user1.get("lga");
        state2 = user1.get("state");
        maritalStatus2 = user1.get("maritalStatus");
        ageUnits = user1.get("ageUnit");
        htsId = user1.get("htsId");

        patient = new PatientDAO(this).findPatientHtsId(Long.parseLong(htsId));
        try {
            if (patient != null) {
                if (patient.getHospitalNum() == null && patient.getHtsId() != 0) {
                    HashMap<String, String> user = session.getProfileDetails();
                    String hosNum = user.get("clientcode");
                    hospitalNum.setText(hosNum);
                } else {
                    hospitalNum.setText(patient.getHospitalNum());
                }
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
                if (gender2.equals("Male")) {
                    pregnancyStatus.setVisibility(View.INVISIBLE);
                    pregnancyStatus2.setVisibility(View.INVISIBLE);
                } else {
                    SettingConfig.setSpinText(pregnancyStatus, patient.getPregnant());
                    pregnancyStatus2.setVisibility(View.VISIBLE);
                    pregnancyStatus.setVisibility(View.VISIBLE);
                }
            }

        } catch (Exception ignored) {

        }
        surname.setEnabled(false);
        surname.setText(surname2);

        otherNames.setEnabled(false);
        otherNames.setText(otherName2);
        age.setEnabled(false);
        age.setText(age2);

        maritalStatus.setText(maritalStatus2);
        maritalStatus.setEnabled(false);

        lga.setText(lga2);
        lga.setEnabled(false);


        state.setText(state2);
        state.setEnabled(false);


        address.setText(address1);
        address.setEnabled(false);

        gender.setText(gender2);
        gender.setEnabled(false);

        phone.setText(phone2);
        // phone.setEnabled(false);

        dateBirth.setText(dateBirth2);
        dateBirth.setEnabled(false);

        ageUnit.setEnabled(false);
        ageUnit.setText(ageUnits);

        dateConfirmedHiv.setText(dateVist2);


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
                final DatePickerDialog mDatePicker = new DatePickerDialog(PatientRegistration.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();


            }
        });


        save.setOnClickListener(v -> {
            try {

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


                if (validateInput1(dateRegistration.getText().toString())) {
                    if (new PatientDAO(getApplicationContext()).findCheckIfPatientExistByHtsId(Long.parseLong(htsId))) {
                        FancyToast.makeText(getApplicationContext(), "Patient already exist", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    } else {
                        String hosNum = hospitalNum.getText().toString();
                        if (hosNum.isEmpty()) {
                            HashMap<String, String> user = session.getProfileDetails();
                            hosNum = user.get("clientcode");
                        } else {
                            hosNum = hospitalNum.getText().toString();
                        }

                        Patient patient1 = new Patient();
                        Facility2 facility2 = new Facility2();
                        facility2.setFacilityId(Long.parseLong(facilityId));
                        patient1.setFacility(facility2);
                        session.createHospitalNumber(hosNum, surname.getText().toString() + "   " + otherNames.getText().toString(), uniqueId.getText().toString());
                        patient1.setUniqueId(uniqueId.getText().toString());
                        patient1.setSurname(surname.getText().toString());
                        patient1.setOtherNames(otherNames.getText().toString());
                        patient1.setGender(gender.getText().toString());
                        patient1.setDateBirth(dateBirth.getText().toString());
                        patient1.setAge(Integer.parseInt(age.getText().toString()));
                        patient1.setMaritalStatus(maritalStatus.getText().toString());
                        patient1.setEducation(education.getSelectedItem().toString());
                        patient1.setOccupation(occupation.getSelectedItem().toString());
                        patient1.setAddress(address.getText().toString());
                        patient1.setPhone(phone.getText().toString());
                        patient1.setHtsId(Long.parseLong(htsId));
                        patient1.setState(state.getText().toString());
                        patient1.setLga(lga.getText().toString());
                        patient1.setNextKin(nextKinName.getText().toString());
                        patient1.setAddressKin(addressKin.getText().toString());
                        patient1.setEducation(education.getSelectedItem().toString());
                        patient1.setOccupation(occupation.getSelectedItem().toString());
                        patient1.setPhoneKin(phoneKin.getText().toString());
                        patient1.setRelationKin(relationKin.getSelectedItem().toString());
                        patient1.setEntryPoint(entryPoint.getSelectedItem().toString());
                        patient1.setStatusRegistration(statusRegistration.getSelectedItem().toString());
                        patient1.setTargetGroup("");
                        patient1.setDeviceconfigId(Long.parseLong(deviceconfigId));
                        patient1.setDateConfirmedHiv(dateConfirmedHiv.getText().toString());
                        new HtsDAO(getApplicationContext()).updateDateRegistration(dateRegistration.getText().toString(), htsId);
                        patient1.setDateRegistration(dateRegistration.getText().toString());
                        patient1.setTbStatus(tbStatus.getSelectedItem().toString());
                        if (pregnancyStatus.getSelectedItem().toString().equals("Pregnant")) {
                            patient1.setPregnant(1);
                            patient1.setBreastfeeding(0);
                        } else if (pregnancyStatus.getSelectedItem().toString().equals("Breastfeeding")) {
                            patient1.setPregnant(0);
                            patient1.setBreastfeeding(1);
                        } else if (pregnancyStatus.getSelectedItem().toString().equals("Not Pregnant")) {
                            patient1.setPregnant(0);
                            patient1.setBreastfeeding(0);
                        } else {
                            patient1.setPregnant(0);
                            patient1.setBreastfeeding(0);
                        }
                        patient1.setUserId(0);
                        String uuid = UUID.randomUUID().toString();
                        patient1.setUuid(uuid);
                        new PrefManager(getApplicationContext()).saveUuId(uuid);
                        new PatientDAO(getApplicationContext()).insertPatient(patient1);
                        FancyToast.makeText(getApplicationContext(), "Patient saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                        try {
                            LicensingManager.getInstance().obtain(getApplicationContext(), getAdditionalComponentsInternal());
                            if (LicensingManager.getInstance().obtain(getApplicationContext(), getMandatoryComponentsInternal())) {
                                startActivityForResult(new Intent(getApplicationContext(), FingerActivity.class), MODALITY_CODE_FINGER);

                            } else {
                                FancyToast.makeText(getApplicationContext(), "Not all Licenses were obtained", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                            }
                        } catch (Exception e) {
                            FancyToast.makeText(getApplicationContext(), e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        }
                    }
                }

            } catch (Exception ignored) {

            }
        });

    }

    private boolean validateInput1(String dateVist2reg) {
//        if (hospitalNum2.isEmpty()) {
//            hospitalNum.setError("Enter Hospital number");
//            FancyToast.makeText(getApplicationContext(), "Enter Hospital number", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//            return false;
//
//        }
//
//        if (uniqueId1.isEmpty()) {
//            uniqueId.setError("Enter UniqueId");
//            FancyToast.makeText(getApplicationContext(), "Enter UniqueId", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//            return false;
//
//        }
        if (dateVist2reg.isEmpty()) {
            uniqueId.setError("Enter Date");
            FancyToast.makeText(getApplicationContext(), "Enter Date", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;

        }

        return true;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //  Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_commencemt2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.commencement) {
            if (patient.getHospitalNum() != null) {
                Intent intent2 = new Intent(getApplicationContext(), ArtCommencement.class);
                startActivity(intent2);
            } else {
                FancyToast.makeText(getApplicationContext(), "Enroll Client before proceeding", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

            }

        }
        if (id == R.id.biometric) {
            if (patient.getHospitalNum() == null && patient.getHtsId() != 0) {
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent2);
            } else if (patient.getHospitalNum() != null && patient.getHtsId() != 0) {
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent2);
            }
        }


        return super.onOptionsItemSelected(item);
    }


    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateRegistration.setText(sdf.format(myCalendar.getTime()));

    }

}
