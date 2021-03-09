package org.fhi360.lamis.mobile.lite.Activities;

import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.library.NavigationBar;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.lamis.mobile.lite.DAO.HtsDAO;
import org.fhi360.lamis.mobile.lite.Domains.Hts;
import org.fhi360.lamis.mobile.lite.Domains.IndexHelper;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;
import org.fhi360.lamis.mobile.lite.Utils.SettingConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import org.fhi360.lamis.mobile.lite.R;

public class EditIndex extends AppCompatActivity {
    private NavigationBar bar;
    private int position = 0;
    private String htsId;
    private String htsId1;
    private TextView header1;
    private EditText clientIndexCode, durationPartner, dateStarted, dateConfirmedHiv, serviceProvided, hospitalNum, numberOfPartners, surname, otherNames, age, address, phone, dateHivTest;
    private AppCompatSpinner index_type,
            notificationCounseling, agreeNotification, indexType, gender,
            hivStatus, linkCare, partnerNotification,
            gbv, phoneTracking, visitTracking, tracingOutcome,
            modeNotification, relation;

    private Button finishButton, finishButton2;
    private ScrollView activity_index1;
    private ScrollView activity_snt;
    private TextView sexualContact, header;
    private PrefManager session;

    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorPrimaryDark)));
        clientIndexCode = findViewById(R.id.clientIndexCode);
        dateStarted = findViewById(R.id.dateStarted);
        session = new PrefManager(getApplicationContext());
        surname = findViewById(R.id.surname);
        otherNames = findViewById(R.id.otherNames);
        age = findViewById(R.id.age);
        header = findViewById(R.id.header);
        sexualContact = findViewById(R.id.sexualContact);
        indexType = findViewById(R.id.indexType);
        gender = findViewById(R.id.gender);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        dateHivTest = findViewById(R.id.dateHivTest);
        hivStatus = findViewById(R.id.hivStatus);
        header1 = findViewById(R.id.headerName);

        linkCare = findViewById(R.id.linkCare);
        partnerNotification = findViewById(R.id.partnerNotification);
        serviceProvided = findViewById(R.id.serviceProvided);
        relation = findViewById(R.id.relation);
        gbv = findViewById(R.id.gbv);
        phoneTracking = findViewById(R.id.phoneTracking);

        visitTracking = findViewById(R.id.visitTracking);
        tracingOutcome = findViewById(R.id.tracingOutcome);
        modeNotification = findViewById(R.id.modeNotification);
        durationPartner = findViewById(R.id.durationPartner);
        dateConfirmedHiv = findViewById(R.id.dateConfirmedHiv);
        hospitalNum = findViewById(R.id.hospitalNum);
        numberOfPartners = findViewById(R.id.numberOfPartners);
        index_type = findViewById(R.id.index_type);
        notificationCounseling = findViewById(R.id.notificationCounseling);
        agreeNotification = findViewById(R.id.agreeNotification);
        finishButton = findViewById(R.id.finishButton);
        activity_index1 = findViewById(R.id.activity_index1);
        activity_snt = findViewById(R.id.activity_snt);

        finishButton2 = findViewById(R.id.finishButton2);

        HashMap<String, String> user1 = session.getProfileDetails();

        htsId = user1.get("htsId");
        SettingConfig settingConfig = new SettingConfig();
        final Hts hts1 = new HtsDAO(getApplicationContext()).getData(Long.parseLong(htsId));

        SettingConfig.setSpinText(index_type, hts1.getTypeIndex());
        hospitalNum.setText(hts1.getHospitalNum());
        if (hts1.getNotificationCounseling() == "1") {
            SettingConfig.setSpinText(notificationCounseling, "YES");
        } else if (hts1.getNotificationCounseling() == "0") {
            SettingConfig.setSpinText(notificationCounseling, "NO");
        }
        if (hts1.getPartnerNotification() == "1") {
            SettingConfig.setSpinText(agreeNotification, "YES");
        } else if (hts1.getPartnerNotification() == "0") {
            SettingConfig.setSpinText(agreeNotification, "NO");
        }
        if (hts1.getTypeIndex().equalsIgnoreCase("0")) {
            SettingConfig.setSpinText(index_type, "NO");
        } else {
            SettingConfig.setSpinText(index_type, "YES");
        }

        numberOfPartners.setText(String.valueOf(hts1.getNumberPartner()));
        dateConfirmedHiv.setText(hts1.getDateVisit());
        clientIndexCode.setText(hts1.getIndexClientCode());
        header1.setText(hts1.getSurname() + " " + hts1.getOtherNames());
        dateStarted.setText(hts1.getDateRegistration());


        final DatePickerDialog.OnDateSetListener date3 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

        };


        dateHivTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(EditIndex.this, date3, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();


            }
        });
//
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


        dateStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(EditIndex.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                // mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();


            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();
            }

        };


        dateConfirmedHiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(EditIndex.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                // mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();


            }
        });


        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IndexHelper indexHelper = new IndexHelper();
                indexHelper.setAgreeNotifications(agreeNotification.getSelectedItem().toString());
                indexHelper.setClientIndexCode(clientIndexCode.getText().toString());
                indexHelper.setDateConfirmHivStatus(dateConfirmedHiv.getText().toString());
                indexHelper.setDateEnrolledHivCase(dateStarted.getText().toString());
                indexHelper.setHtsId(Long.parseLong(htsId));
                indexHelper.setHuspitalNum(hospitalNum.getText().toString());
                indexHelper.setIndexType(index_type.getSelectedItem().toString());
                indexHelper.setDeviceconfigId(hts1.getDeviceconfigId());
                indexHelper.setNotificationCounseling(notificationCounseling.getSelectedItem().toString());
                indexHelper.setNumberPartner(numberOfPartners.getText().toString());
                   new HtsDAO(getApplicationContext()).updateContact(indexHelper);
                    FancyToast.makeText(getApplicationContext(), "Record Updated Successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();


            }

        });
    }

    private void updateLabel1() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateHivTest.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateLabel2() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateConfirmedHiv.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateStarted.setText(sdf.format(myCalendar.getTime()));

    }
}
