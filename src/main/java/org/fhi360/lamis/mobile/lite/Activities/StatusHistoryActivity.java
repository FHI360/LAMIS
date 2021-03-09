package org.fhi360.lamis.mobile.lite.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.lamis.mobile.lite.DAO.StatusHistoryDAO;
import org.fhi360.lamis.mobile.lite.Domains.Patient;
import org.fhi360.lamis.mobile.lite.Domains.StatusHistory;
import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;
import org.fhi360.lamis.mobile.lite.Utils.Scrambler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class StatusHistoryActivity extends AppCompatActivity {
    private EditText hospitalNum, dateCurrentStatus, dateNewStatus, currentStatus, dateTracked, dateAgreedReturn;
    private TextView clientDetails;
    private AppCompatSpinner newStatus, causeDeath, reasonInteruption;
    private Button save;
    private long patientId;
    private PrefManager prefManager;
    private Calendar myCalendar = Calendar.getInstance();
    private String facilityName;
    private String lgaId;
    private String steteId;
    private String facilityId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_tracking);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorPrimaryDark)));
        hospitalNum = findViewById(R.id.hospitalNum);
        clientDetails = findViewById(R.id.clientDetails);
        dateCurrentStatus = findViewById(R.id.dateCurrentStatus);
        dateNewStatus = findViewById(R.id.dateNewStatus);
        dateTracked = findViewById(R.id.dateTracked);
        newStatus = findViewById(R.id.newStatus);
        causeDeath = findViewById(R.id.causeDeath);
        currentStatus = findViewById(R.id.currentStatus);
        reasonInteruption = findViewById(R.id.reasonInteruption);
        dateAgreedReturn = findViewById(R.id.dateAgreedReturn);
        save = findViewById(R.id.save);
        dateNewStatus.setVisibility(View.INVISIBLE);
        causeDeath.setEnabled(false);
        reasonInteruption.setEnabled(false);
        dateTracked.setVisibility(View.INVISIBLE);
        prefManager = new PrefManager(getApplicationContext());
        HashMap<String, String> user = prefManager.getHtsDetails();

        HashMap<String, String> clients = prefManager.getClientTracking();
        String hospitalNum1 = clients.get("hospitalNum");
        String currentStatus1 = clients.get("currentStatus");
        String dateCurrentStatus1 = clients.get("dateCurrentStatus");
        final String patientId = clients.get("patientId");
        String name = clients.get("name");
        Scrambler scrambler = new Scrambler();
        clientDetails.setText("Client Status Update For " + scrambler.scrambleCharacters(name));
        hospitalNum.setText(hospitalNum1);
        dateCurrentStatus.setText(dateCurrentStatus1);
        currentStatus.setText(currentStatus1);

        facilityName = user.get("faciltyName");
        lgaId = user.get("lgaId");
        steteId = user.get("stateId");
        facilityId = user.get("facilityId");

        newStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (newStatus.getSelectedItem().toString().equals("ART Restart")
                ) {
                    dateNewStatus.setVisibility(View.VISIBLE);
                    reasonInteruption.setEnabled(false);
                    dateTracked.setVisibility(View.INVISIBLE);
                    dateAgreedReturn.setVisibility(View.INVISIBLE);
                }
                if (newStatus.getSelectedItem().toString().equals("ART Transfer Out")) {
                    dateNewStatus.setVisibility(View.VISIBLE);
                    reasonInteruption.setEnabled(false);
                    dateTracked.setVisibility(View.INVISIBLE);
                }
                if (newStatus.getSelectedItem().toString().equals("Pre-ART Transfer Out")) {
                    dateNewStatus.setVisibility(View.VISIBLE);
                    reasonInteruption.setEnabled(false);
                    dateTracked.setVisibility(View.INVISIBLE);
                    dateAgreedReturn.setVisibility(View.INVISIBLE);
                }
                if (newStatus.getSelectedItem().toString().equals("Lost to Follow Up")) {
                    dateNewStatus.setVisibility(View.VISIBLE);
                    reasonInteruption.setEnabled(false);
                    dateTracked.setVisibility(View.INVISIBLE);
                    dateAgreedReturn.setVisibility(View.INVISIBLE);
                }
                if (newStatus.getSelectedItem().toString().equals("Stopped Treatment")) {
                    dateNewStatus.setVisibility(View.VISIBLE);
                    reasonInteruption.setEnabled(true);
                    dateTracked.setVisibility(View.INVISIBLE);
                    dateAgreedReturn.setVisibility(View.INVISIBLE);
                }
                if (newStatus.getSelectedItem().toString().equals("Died(Confirmed)")) {
                    dateNewStatus.setVisibility(View.VISIBLE);
                    causeDeath.setEnabled(true);
                    reasonInteruption.setEnabled(false);
                    dateTracked.setVisibility(View.INVISIBLE);
                    dateAgreedReturn.setVisibility(View.INVISIBLE);
                }
                if (newStatus.getSelectedItem().toString().equals("Previousely Undocumented Patient Transfer (Confirmed)")) {
                    dateNewStatus.setVisibility(View.INVISIBLE);
                    causeDeath.setEnabled(false);
                    reasonInteruption.setEnabled(false);
                    dateTracked.setVisibility(View.VISIBLE);
                    dateAgreedReturn.setVisibility(View.INVISIBLE);
                }

                if (newStatus.getSelectedItem().toString().equals("Traced Patient (Unable to locate)")) {
                    dateNewStatus.setVisibility(View.INVISIBLE);
                    causeDeath.setVisibility(View.INVISIBLE);
                    reasonInteruption.setVisibility(View.INVISIBLE);
                    dateTracked.setVisibility(View.VISIBLE);
                    dateAgreedReturn.setVisibility(View.INVISIBLE);
                }

                if (newStatus.getSelectedItem().toString().equals("Traced Patient and agreed to return  care")) {
                    dateNewStatus.setVisibility(View.INVISIBLE);
                    causeDeath.setVisibility(View.INVISIBLE);
                    reasonInteruption.setVisibility(View.INVISIBLE);
                    dateTracked.setVisibility(View.VISIBLE);
                    dateAgreedReturn.setVisibility(View.VISIBLE);

                }

                if (newStatus.getSelectedItem().toString().equals("Did Not Attempt to Trace Patient")) {
                    dateNewStatus.setVisibility(View.INVISIBLE);
                    causeDeath.setVisibility(View.INVISIBLE);
                    reasonInteruption.setVisibility(View.INVISIBLE);
                    dateTracked.setVisibility(View.VISIBLE);
                    dateAgreedReturn.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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

        dateNewStatus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(StatusHistoryActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();
            }


        });


        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabe2();
            }

        };
        dateTracked.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(StatusHistoryActivity.this, date2, myCalendar
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
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabe3();
            }

        };

        dateAgreedReturn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(StatusHistoryActivity.this, date3, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();
            }


        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newStatus.getSelectedItem().equals("ART Restart")
                        || newStatus.getSelectedItem().equals("ART Transfer Out")
                        || newStatus.getSelectedItem().equals("Pre-ART Transfer Out")
                        || newStatus.getSelectedItem().equals("Lost to Follow Up")) {
                    StatusHistory statusHistory = new StatusHistory();
                    Patient patient = new Patient();
                    patient.setPatientId(Long.parseLong(patientId));
                    statusHistory.setPatient(patient);
                    statusHistory.setFacilityId(Long.parseLong(facilityId));
                    statusHistory.setCurrentStatus(newStatus.getSelectedItem().toString());
                    statusHistory.setDateCurrentStatus(dateNewStatus.getText().toString());
                    statusHistory.setReasonInterrupt("");
                    statusHistory.setCauseDeath("");
                    statusHistory.setDateTracked("");
                    statusHistory.setOutcome("");
                    statusHistory.setAgreedDate("");
                    new StatusHistoryDAO(getApplicationContext()).saveStatusHistory(statusHistory);
                    FancyToast.makeText(StatusHistoryActivity.this, "Record saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    Intent intent = new Intent(StatusHistoryActivity.this, ClientTracking2.class);
                    startActivity(intent);
                }

                if (newStatus.getSelectedItem().equals("Stopped Treatment")) {
                    StatusHistory statusHistory = new StatusHistory();
                    Patient patient = new Patient();
                    patient.setPatientId(Long.parseLong(patientId));
                    statusHistory.setPatient(patient);
                    statusHistory.setFacilityId(Long.parseLong(facilityId));
                    statusHistory.setCurrentStatus(newStatus.getSelectedItem().toString());
                    statusHistory.setDateCurrentStatus(dateNewStatus.getText().toString());
                    statusHistory.setReasonInterrupt(reasonInteruption.getSelectedItem().toString());
                    statusHistory.setCauseDeath("");
                    statusHistory.setDateTracked("");
                    statusHistory.setOutcome("");
                    statusHistory.setAgreedDate("");
                    new StatusHistoryDAO(getApplicationContext()).saveStatusHistory(statusHistory);
                    FancyToast.makeText(StatusHistoryActivity.this, "Record saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    Intent intent = new Intent(StatusHistoryActivity.this, ClientTracking2.class);
                    startActivity(intent);
                }

                if (newStatus.getSelectedItem().equals("Died(Confirmed)")) {
                    StatusHistory statusHistory = new StatusHistory();
                    Patient patient = new Patient();
                    patient.setPatientId(Long.parseLong(patientId));
                    statusHistory.setPatient(patient);
                    statusHistory.setFacilityId(Long.parseLong(facilityId));
                    statusHistory.setCurrentStatus("Known Death");
                    statusHistory.setDateCurrentStatus(dateNewStatus.getText().toString());
                    statusHistory.setReasonInterrupt("");
                    statusHistory.setCauseDeath(causeDeath.getSelectedItem().toString());
                    statusHistory.setDateTracked(dateTracked.getText().toString());
                    statusHistory.setOutcome(newStatus.getSelectedItem().toString());
                    statusHistory.setAgreedDate("");
                    new StatusHistoryDAO(getApplicationContext()).saveStatusHistory(statusHistory);
                    FancyToast.makeText(StatusHistoryActivity.this, "Record saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                }

                if (newStatus.getSelectedItem().equals("Previousely Undocumented Patient Transfer (Confirmed)")
                        || newStatus.getSelectedItem().equals("Traced Patient (Unable to locate)")
                        || newStatus.getSelectedItem().equals("Did Not Attempt to Trace Patient")) {
                    StatusHistory statusHistory = new StatusHistory();
                    Patient patient = new Patient();
                    patient.setPatientId(Long.parseLong(patientId));
                    statusHistory.setPatient(patient);
                    statusHistory.setFacilityId(Long.parseLong(facilityId));
                    statusHistory.setCurrentStatus(currentStatus.getText().toString());
                    statusHistory.setDateCurrentStatus(dateCurrentStatus.getText().toString());
                    statusHistory.setReasonInterrupt("");
                    statusHistory.setCauseDeath("");
                    statusHistory.setDateTracked(dateTracked.getText().toString());
                    statusHistory.setOutcome(newStatus.getSelectedItem().toString());
                    statusHistory.setAgreedDate("");
                    new StatusHistoryDAO(getApplicationContext()).saveStatusHistory(statusHistory);
                    FancyToast.makeText(StatusHistoryActivity.this, "Record saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    Intent intent = new Intent(StatusHistoryActivity.this, ClientTracking2.class);
                    startActivity(intent);
                }


                if (newStatus.getSelectedItem().equals("Traced Patient and agreed to return  care")) {
                    StatusHistory statusHistory = new StatusHistory();
                    Patient patient = new Patient();
                    patient.setPatientId(Long.parseLong(patientId));
                    statusHistory.setPatient(patient);
                    statusHistory.setFacilityId(Long.parseLong(facilityId));
                    statusHistory.setCurrentStatus(currentStatus.getText().toString());
                    statusHistory.setDateCurrentStatus(dateCurrentStatus.getText().toString());
                    statusHistory.setReasonInterrupt("");
                    statusHistory.setCauseDeath("");
                    statusHistory.setDateTracked(dateTracked.getText().toString());
                    statusHistory.setOutcome(newStatus.getSelectedItem().toString());
                    statusHistory.setAgreedDate(dateAgreedReturn.getText().toString());
                    new StatusHistoryDAO(getApplicationContext()).saveStatusHistory(statusHistory);
                    FancyToast.makeText(getApplicationContext(), "Record saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    Intent intent = new Intent(StatusHistoryActivity.this, ClientTracking2.class);
                    startActivity(intent);
                }


            }
        });

    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateNewStatus.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateLabe3() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateAgreedReturn.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateLabe2() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateTracked.setText(sdf.format(myCalendar.getTime()));

    }


}
