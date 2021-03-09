package org.fhi360.lamis.mobile.lite.Fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
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
import android.widget.TextView;

import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.lamis.mobile.lite.Activities.HtsRegistration;
import org.fhi360.lamis.mobile.lite.DAO.AssementDAO;
import org.fhi360.lamis.mobile.lite.DAO.CamTeamDAO;
import org.fhi360.lamis.mobile.lite.Domains.Assessment;
import org.fhi360.lamis.mobile.lite.Domains.CamTeam;
import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class RiskAssessmentFragment extends Fragment {
    private EditText clientCode, dateVisit;
    private AppCompatSpinner question1,
            question2, question3,
            question4, question5,
            question6, question7,
            question8, question9,
            question10, question11,
            question12, camTeamEditext;
    private CheckBox sti1, sti2,
            sti3, sti4,
            sti5, sti6,
            sti7, sti8;
    private Button saved;
    private ScrollView assessment;
    private String facilityName;
    private String lgaId;
    private String steteId;
    private String facilityId;
    private PrefManager session;
    private String deviceconfigId;
    private String auoIncrementClientCode;
    private TextView havebeen;
    private Calendar myCalendar = Calendar.getInstance();

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_rik_assessment, container, false);
        try {
            dateVisit = rootView.findViewById(R.id.dateVisit);
            camTeamEditext = rootView.findViewById(R.id.camteam);
            clientCode = rootView.findViewById(R.id.clientCode);
            question1 = rootView.findViewById(R.id.question1);
            question2 = rootView.findViewById(R.id.question2);
            question3 = rootView.findViewById(R.id.question3);
            question4 = rootView.findViewById(R.id.question4);
            question5 = rootView.findViewById(R.id.question5);
            question6 = rootView.findViewById(R.id.question6);
            question7 = rootView.findViewById(R.id.question7);
            question8 = rootView.findViewById(R.id.question8);
            question9 = rootView.findViewById(R.id.question9);
            question10 = rootView.findViewById(R.id.question10);
            question11 = rootView.findViewById(R.id.question11);
            question12 = rootView.findViewById(R.id.question12);
            assessment = rootView.findViewById(R.id.ristassessment);
            havebeen = rootView.findViewById(R.id.havebeen);
            sti1 = rootView.findViewById(R.id.sti1);
            sti2 = rootView.findViewById(R.id.sti2);
            sti3 = rootView.findViewById(R.id.sti3);
            sti4 = rootView.findViewById(R.id.sti4);
            sti5 = rootView.findViewById(R.id.sti5);
            sti6 = rootView.findViewById(R.id.sti6);
            sti7 = rootView.findViewById(R.id.sti7);
            sti8 = rootView.findViewById(R.id.sti8);
            saved = rootView.findViewById(R.id.save);
            session = new PrefManager(getContext());
            sti1.setVisibility(View.INVISIBLE);
            sti2.setVisibility(View.INVISIBLE);
            sti3.setVisibility(View.INVISIBLE);
            sti4.setVisibility(View.INVISIBLE);
            sti5.setVisibility(View.INVISIBLE);
            sti6.setVisibility(View.INVISIBLE);
            sti7.setVisibility(View.INVISIBLE);
            sti8.setVisibility(View.INVISIBLE);
            assessment.setVisibility(View.INVISIBLE);
            clientCode.setEnabled(false);


            final ArrayList camCode = new ArrayList();
            ArrayList camTeamName = new ArrayList();
            List<CamTeam> camTeam = new CamTeamDAO(getContext()).getCamTeam();
            System.out.println("CAM " + camTeam);
            for (CamTeam camTeam1 : camTeam) {
                System.out.println("CAM " + camTeam1.getCamteam());
                camCode.add(camTeam1.getCamCode());
                camTeamName.add(camTeam1.getCamteam());
            }

            final ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(getContext(),
                    R.layout.spinner_items, camTeamName);
            districtAdapter.setDropDownViewResource(R.layout.color_spinner_layout);
            districtAdapter.notifyDataSetChanged();
            camTeamEditext.setAdapter(districtAdapter);
            camTeamEditext.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String camCodes = (String) camCode.get(position);
                    new PrefManager(getContext()).saveCam(camCodes);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            HashMap<String, String> user = session.getHtsDetails();
            facilityName = user.get("faciltyName");
            lgaId = user.get("lgaId");
            steteId = user.get("stateId");
            facilityId = user.get("facilityId");
            deviceconfigId = user.get("deviceconfig_id");
            HashMap<String, String> lastSerialNumberOfHts = session.getLastHtsSerialDigit();
            String digit = lastSerialNumberOfHts.get("serialNumber");
            final Integer sum = Integer.valueOf(digit) + 1;
            auoIncrementClientCode = steteId + "/" + facilityId + "/" + deviceconfigId + "/" + sum;
            clientCode.setText(auoIncrementClientCode);
            question1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (question1.getSelectedItem().toString().equals("Positive not on ART")) {
                        assessment.setVisibility(View.INVISIBLE);
                        showAlert();
                    }
                    if (question1.getSelectedItem().toString().equals("Positive on ART")) {
                        assessment.setVisibility(View.INVISIBLE);
                    }
                    if (question1.getSelectedItem().toString().equals("Negative less than 6 Months")) {
                        assessment.setVisibility(View.VISIBLE);
                    }
                    if (question1.getSelectedItem().toString().equals("Negative more than 6 Months")) {
                        assessment.setVisibility(View.VISIBLE);
                    }
                    if (question1.getSelectedItem().toString().equals("Unknown")) {
                        assessment.setVisibility(View.VISIBLE);
                    }
                    if (question1.getSelectedItem().toString().equals("Never Tested")) {
                        assessment.setVisibility(View.VISIBLE);
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

            dateVisit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    final DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                    mDatePicker.show();
                }


            });


            question7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (question7.getSelectedItem().toString().equals("YES")) {
                        sti1.setVisibility(View.VISIBLE);
                        sti2.setVisibility(View.VISIBLE);
                        sti3.setVisibility(View.VISIBLE);
                        sti4.setVisibility(View.VISIBLE);
                        sti5.setVisibility(View.VISIBLE);
                        sti6.setVisibility(View.VISIBLE);
                        sti7.setVisibility(View.VISIBLE);
                        sti8.setVisibility(View.VISIBLE);
                    }
                    if (question7.getSelectedItem().toString().equals("NO")) {
                        sti1.setVisibility(View.INVISIBLE);
                        sti2.setVisibility(View.INVISIBLE);
                        sti3.setVisibility(View.INVISIBLE);
                        sti4.setVisibility(View.INVISIBLE);
                        sti5.setVisibility(View.INVISIBLE);
                        sti6.setVisibility(View.INVISIBLE);
                        sti7.setVisibility(View.INVISIBLE);
                        sti8.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            saved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validateInput(dateVisit.getText().toString())) {
                        if (question1.getSelectedItem().toString().equals("")) {
                            FancyToast.makeText(getContext(), "Select Assessment One", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                        } else {
                            int total = 0;
                            if (question1.getSelectedItem().toString().equals("Negative less than 6 Months")) {
                                Assessment assessment = new Assessment();
                                assessment.setFacilityId(Long.parseLong(facilityId));
                                assessment.setDateVisit(dateVisit.getText().toString());
                                assessment.setClientCode(auoIncrementClientCode);
                                if (!question1.getSelectedItem().toString().equals("")) {
                                    assessment.setQuestion1(question1.getSelectedItem().toString());
                                    total = 1;
                                }
                                if (question2.getSelectedItem().toString().equals("YES")) {
                                    assessment.setQuestion2(1);
                                    total = 1;
                                } else {
                                    assessment.setQuestion2(0);
                                }


                                if (question3.getSelectedItem().toString().equals("YES")) {
                                    assessment.setQuestion3(1);
                                    total = 1;
                                } else {
                                    assessment.setQuestion3(0);
                                }
                                if (question4.getSelectedItem().toString().equals("YES")) {
                                    assessment.setQuestion4(1);
                                    total = 1;
                                } else {
                                    assessment.setQuestion4(0);
                                }
                                if (question5.getSelectedItem().toString().equals("YES")) {
                                    assessment.setQuestion5(1);
                                    total = 1;
                                } else {
                                    assessment.setQuestion5(0);
                                }
                                if (question6.getSelectedItem().toString().equals("YES")) {
                                    assessment.setQuestion6(1);
                                    total = 1;
                                } else {
                                    assessment.setQuestion6(0);
                                }
                                if (question7.getSelectedItem().toString().equals("YES")) {
                                    assessment.setQuestion7(1);
                                    total = 1;
                                } else {
                                    assessment.setQuestion7(0);
                                }
                                if (question8.getSelectedItem().toString().equals("YES")) {
                                    assessment.setQuestion8(1);
                                    total = 1;
                                } else {
                                    assessment.setQuestion8(0);
                                }
                                if (question9.getSelectedItem().toString().equals("YES")) {
                                    assessment.setQuestion9(1);
                                    total = 1;
                                } else {
                                    assessment.setQuestion9(0);
                                }
                                if (question10.getSelectedItem().toString().equals("YES")) {
                                    assessment.setQuestion10(1);
                                    total = 1;
                                } else {
                                    assessment.setQuestion10(0);
                                }
                                if (question11.getSelectedItem().toString().equals("YES")) {
                                    assessment.setQuestion11(1);
                                    total = 1;
                                } else {
                                    assessment.setQuestion11(0);
                                }
                                if (question12.getSelectedItem().toString().equals("YES")) {
                                    assessment.setQuestion12(1);
                                    total = 1;
                                } else {
                                    assessment.setQuestion12(0);

                                }


                                if (sti1.isChecked()) {
                                    assessment.setSti1(1);
                                } else {
                                    assessment.setSti1(0);
                                }
                                if (sti2.isChecked()) {
                                    assessment.setSti2(1);
                                } else {
                                    assessment.setSti2(0);
                                }

                                if (sti3.isChecked()) {
                                    assessment.setSti3(1);
                                } else {
                                    assessment.setSti3(0);
                                }
                                if (sti4.isChecked()) {
                                    assessment.setSti4(1);
                                } else {
                                    assessment.setSti4(0);
                                }
                                if (sti5.isChecked()) {
                                    assessment.setSti5(1);
                                } else {
                                    assessment.setSti5(0);
                                }
                                if (sti6.isChecked()) {
                                    assessment.setSti6(1);
                                } else {
                                    assessment.setSti6(0);
                                }
                                if (sti7.isChecked()) {
                                    assessment.setSti7(1);
                                } else {
                                    assessment.setSti7(0);
                                }
                                if (sti8.isChecked()) {
                                    assessment.setSti7(1);
                                } else {
                                    assessment.setSti7(0);
                                }
                                assessment.setDeviceconfigId(Long.parseLong(deviceconfigId));
                                long assessment_id = new AssementDAO(getContext()).saveRiskAssessment(assessment);
                                session.setLastHtsSerialDigit(sum);
                                if (total > 0) {
                                    FancyToast.makeText(getContext(), "Record saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                    Intent intent = new Intent(getContext(), HtsRegistration.class);
                                    session.saveAssessmentId(String.valueOf(assessment_id));
                                    session.setClientCode(auoIncrementClientCode);
                                    startActivity(intent);

                                } else {
                                    FancyToast.makeText(getContext(), "Record saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                                }

                            } else {
                                Assessment assessment = new Assessment();
                                assessment.setFacilityId(Long.parseLong(facilityId));
                                assessment.setDateVisit(dateVisit.getText().toString());
                                assessment.setClientCode(auoIncrementClientCode);
                                if (question2.getSelectedItem().toString().equals("YES")) {
                                    assessment.setQuestion2(1);
                                    total = 1;
                                } else {
                                    assessment.setQuestion2(0);

                                }


                                if (question3.getSelectedItem().toString().equals("YES")) {
                                    assessment.setQuestion3(1);
                                    total = 1;
                                } else {
                                    assessment.setQuestion3(0);
                                }
                                if (question4.getSelectedItem().toString().equals("YES")) {
                                    assessment.setQuestion4(1);
                                    total = 1;
                                } else {
                                    assessment.setQuestion4(0);
                                }
                                if (question5.getSelectedItem().toString().equals("YES")) {
                                    assessment.setQuestion5(1);
                                    total = 1;
                                } else {
                                    assessment.setQuestion5(0);
                                }
                                if (question6.getSelectedItem().toString().equals("YES")) {
                                    assessment.setQuestion6(1);
                                    total = 1;
                                } else {
                                    assessment.setQuestion6(0);
                                }
                                if (question7.getSelectedItem().toString().equals("YES")) {
                                    assessment.setQuestion7(1);
                                    total = 1;
                                } else {
                                    assessment.setQuestion7(0);
                                }
                                if (question8.getSelectedItem().toString().equals("YES")) {
                                    assessment.setQuestion8(1);
                                    total = 1;
                                } else {
                                    assessment.setQuestion8(0);

                                }
                                if (question9.getSelectedItem().toString().equals("YES")) {
                                    assessment.setQuestion9(1);
                                    total = 1;
                                } else {
                                    assessment.setQuestion9(0);
                                }
                                if (question10.getSelectedItem().toString().equals("YES")) {
                                    assessment.setQuestion10(1);
                                    total = 1;
                                } else {
                                    assessment.setQuestion10(0);
                                }
                                if (question11.getSelectedItem().toString().equals("YES")) {
                                    assessment.setQuestion11(1);
                                    total = 1;
                                } else {
                                    assessment.setQuestion11(0);
                                }
                                if (question12.getSelectedItem().toString().equals("YES")) {
                                    assessment.setQuestion12(1);
                                    total = 1;
                                } else {
                                    assessment.setQuestion12(0);
                                }


                                if (sti1.isChecked()) {
                                    assessment.setSti1(1);
                                } else {
                                    assessment.setSti1(0);
                                }
                                if (sti2.isChecked()) {
                                    assessment.setSti2(1);
                                } else {
                                    assessment.setSti2(0);
                                }

                                if (sti3.isChecked()) {
                                    assessment.setSti3(1);
                                } else {
                                    assessment.setSti3(0);
                                }
                                if (sti4.isChecked()) {
                                    assessment.setSti4(1);
                                } else {
                                    assessment.setSti4(0);
                                }
                                if (sti5.isChecked()) {
                                    assessment.setSti5(1);
                                } else {
                                    assessment.setSti5(0);
                                }
                                if (sti6.isChecked()) {
                                    assessment.setSti6(1);
                                } else {
                                    assessment.setSti6(0);
                                }
                                if (sti7.isChecked()) {
                                    assessment.setSti7(1);
                                } else {
                                    assessment.setSti7(0);
                                }
                                if (sti8.isChecked()) {
                                    assessment.setSti7(1);
                                } else {
                                    assessment.setSti7(0);
                                }
                                assessment.setDeviceconfigId(Long.parseLong(deviceconfigId));
                                long assessment_id = new AssementDAO(getContext()).saveRiskAssessment(assessment);
                                session.setLastHtsSerialDigit(sum);
                                if (total > 0) {
                                    FancyToast.makeText(getContext(), "Record saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                    Intent intent = new Intent(getContext(), HtsRegistration.class);
                                    session.saveAssessmentId(String.valueOf(assessment_id));
                                    session.setClientCode(auoIncrementClientCode);

                                    startActivity(intent);
                                } else {
                                    FancyToast.makeText(getContext(), "Record saved successfully, Client is not eligible for HTS", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                                }

                            }


                        }
                    }
                }
            });

        } catch (Exception ignored) {

        }
        return rootView;
    }

    private void showAlert() {
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.popup2, null);
        final AlertDialog dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext())).create();
        dialog.setView(promptsView);
        dialog.show();
    }


    private boolean validateInput(String dateVisits) {
        if (dateVisits.isEmpty()) {
            dateVisit.setError("Enter date of visit");
            FancyToast.makeText(getContext(), "Enter date of visit", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;

        }

        return true;


    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateVisit.setText(sdf.format(myCalendar.getTime()));

    }


}
