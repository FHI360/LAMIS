package org.fhi360.lamis.mobile.lite.Activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.lamis.mobile.lite.DAO.HtsDAO;
import org.fhi360.lamis.mobile.lite.DAO.RecencyDAO;
import org.fhi360.lamis.mobile.lite.DAO.ViralLoadDAO;
import org.fhi360.lamis.mobile.lite.Domains.Hts;
import org.fhi360.lamis.mobile.lite.Domains.Hts2;
import org.fhi360.lamis.mobile.lite.Domains.Recency;
import org.fhi360.lamis.mobile.lite.Domains.ViralLoad;
import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public class RecencyTesting extends AppCompatActivity implements View.OnClickListener {
    private EditText participatnNumber, testName, fullname, confirmPartipanctNumber;
    private EditText testDate, dateSampleCollected, sampleReferenceNumber, dateSampleTest, dateTestDone, viralLoadResult, finalViralLoadRsult;
    private EditText recencyInterpretation;
    private CheckBox controlLine, verificationLine, longTermLine;
    private LinearLayout dateSampleCollectedText, sampleReferenceNumberText, viralLoadResultClassification1, dateSampleTestText, dateTestDoneText, viralLoadResultText, finalViralLoadRsultText;
    private PrefManager session;
    private RecencyDAO recencyDAO;
    private Calendar myCalendar = Calendar.getInstance();
    private Button finishButton;
    private AppCompatSpinner typesample, viralLoadResultClassification;
    LinearLayout typeSampleText;
    TextInputLayout error, interpretationmessage, viralLoadResultTest1;
    Hts hts;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recency);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));
        session = new PrefManager(this);
        HashMap<String, String> user1 = session.getProfileDetails();
        final String htsId = user1.get("htsId");
        recencyDAO = new RecencyDAO(this);

        fullname = findViewById(R.id.recencyName);
        testName = findViewById(R.id.testName);
        testDate = findViewById(R.id.testDate);
        controlLine = findViewById(R.id.controlline);
        verificationLine = findViewById(R.id.verificationLine);
        longTermLine = findViewById(R.id.longTimeLine);
        finishButton = findViewById(R.id.finishButton);
        typeSampleText = findViewById(R.id.typeSampleText);
        recencyInterpretation = findViewById(R.id.rececnyInterpretation);
        confirmPartipanctNumber = findViewById(R.id.participantNumber2);
        participatnNumber = findViewById(R.id.recencyNumber);
        viralLoadResultTest1 = findViewById(R.id.viralLoadResultTest1);
        error = findViewById(R.id.error1);
        interpretationmessage = findViewById(R.id.interpretationmessage);

        controlLine.setOnClickListener(this);
        verificationLine.setOnClickListener(this);
        longTermLine.setOnClickListener(this);
        dateSampleCollected = findViewById(R.id.dateSampleCollected);
        dateSampleTest = findViewById(R.id.dateSampleTest);
        dateTestDone = findViewById(R.id.dateTestDone);
        viralLoadResult = findViewById(R.id.viralLoadResult);
        finalViralLoadRsult = findViewById(R.id.finalViralLoadRsult);
        viralLoadResultClassification = findViewById(R.id.viralLoadResultClassification);
        viralLoadResultClassification1 = findViewById(R.id.viralLoadResultClassification1);
        sampleReferenceNumberText = findViewById(R.id.sampleReferenceNumberText);
        sampleReferenceNumber = findViewById(R.id.sampleReferenceNumber);


        dateSampleCollectedText = findViewById(R.id.dateSampleCollectedText);
        dateSampleTestText = findViewById(R.id.dateSampleTestText);
        dateTestDoneText = findViewById(R.id.dateTestDoneTest);
        viralLoadResultText = findViewById(R.id.viralLoadResultTest);
        typesample = findViewById(R.id.typesample);
        finalViralLoadRsultText = findViewById(R.id.finalViralLoadRsultTest);

        dateSampleCollected.setVisibility(View.INVISIBLE);
        dateSampleTest.setVisibility(View.INVISIBLE);
        dateTestDone.setVisibility(View.INVISIBLE);
        viralLoadResult.setVisibility(View.INVISIBLE);
        sampleReferenceNumberText.setVisibility(View.INVISIBLE);
        sampleReferenceNumber.setVisibility(View.INVISIBLE);
        viralLoadResultClassification1.setVisibility(View.INVISIBLE);
        viralLoadResultClassification.setVisibility(View.INVISIBLE);
        finalViralLoadRsult.setVisibility(View.INVISIBLE);
//        participatnNumber.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
//        confirmPartipanctNumber.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

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

        testDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(RecencyTesting.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();
            }


        });

        final DatePickerDialog.OnDateSetListener dateSampleCollected1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabe2();
            }

        };


        dateSampleCollected.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final DatePickerDialog mDatePicker = new DatePickerDialog(RecencyTesting.this, dateSampleCollected1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.show();
            }


        });


        final DatePickerDialog.OnDateSetListener dateSampleTest1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabe3();
            }

        };


        dateSampleTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(RecencyTesting.this, dateSampleTest1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.show();
            }


        });


        final DatePickerDialog.OnDateSetListener dateTestDone1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabe4();
            }

        };


        dateTestDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final DatePickerDialog mDatePicker = new DatePickerDialog(RecencyTesting.this, dateTestDone1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.show();
            }


        });
        hts = new HtsDAO(this).getData(Long.parseLong(htsId));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fullname.setText(Html.fromHtml("</font><br><br>" + "<font color=\"#000000\">" + hts.getSurname() + " " + hts.getOtherNames() + "</font>", Html.FROM_HTML_MODE_COMPACT));
        } else {
            fullname.setText(Html.fromHtml("</font><br><br>" + "<font color=\"#000000\">" + hts.getSurname() + " " + hts.getOtherNames() + "</font>"));
        }
        final InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; ++i) {
                    if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890]*").matcher(String.valueOf(source.charAt(i))).matches()) {
                        return "";
                    }
                }

                return null;
            }
        };

        confirmPartipanctNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                String participatnNumber1 = participatnNumber.getText().toString();
                int errorColor;
                String confirm = confirmPartipanctNumber.getText().toString();

                String length = participatnNumber1.substring(0, 2);
                if (!(length.matches("^.*[a-zA-Z].*$") && length.length() == 2)) {
                    error.setError("Participant State code must be two digit characters");
                    participatnNumber.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(2)});
                } else {
                    if (text.length() == 10 && participatnNumber1.length() == 10 && participatnNumber1.equals(confirm)) {
                        error.setError("Participant Number Match");
                        error.setErrorEnabled(true);
                        errorColor = ContextCompat.getColor(getApplicationContext(), R.color.lightGreen);
                        error.setErrorTextColor(ColorStateList.valueOf(errorColor));
                        testDate.setEnabled(true);
                        controlLine.setEnabled(true);
                        verificationLine.setEnabled(true);
                        longTermLine.setEnabled(true);
                        finishButton.setEnabled(true);
                    } else {
                        error.setError("Participant do not Match");
                        error.setErrorEnabled(true);
                        errorColor = ContextCompat.getColor(getApplicationContext(), R.color.colorCancel);
                        error.setErrorTextColor(ColorStateList.valueOf(errorColor));
                        testDate.setEnabled(false);
                        controlLine.setEnabled(false);
                        verificationLine.setEnabled(false);
                        longTermLine.setEnabled(false);
                        finishButton.setEnabled(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                String participatnNumber1 = participatnNumber.getText().toString();
//                if (participatnNumber1.equals("")) {
//                    participatnNumber.setText("");
//                } else {
//                    if (confirmPartipanctNumber.getText().toString().equals(participatnNumber1)) {
//                        FancyToast.makeText(getApplicationContext(), "Participant Number Match", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//                    } else {
//                        finishButton.setClickable(false);
//                        FancyToast.makeText(getApplicationContext(), "Participant Number are not the same", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//                    }
//                }
            }
        });

        viralLoadResultClassification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String vResult = viralLoadResultClassification.getSelectedItem().toString();
                if (vResult.equals("< 1000")) {
                    viralLoadResult.setEnabled(true);
                    finalViralLoadRsult.setEnabled(false);
                    finalViralLoadRsult.setText(" ");
                    viralLoadResult.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(3)});
                } else if (vResult.equals(">= 1000")) {
                    viralLoadResult.setEnabled(true);
                    finalViralLoadRsult.setEnabled(false);
                    finalViralLoadRsult.setText("");
                    viralLoadResult.setText("");
                    viralLoadResult.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(10)});
                } else {
                    viralLoadResult.setEnabled(false);
                    finalViralLoadRsult.setEnabled(false);
                    viralLoadResult.setText("");
                    finalViralLoadRsult.setText("RITA inconclusive");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        viralLoadResult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String viralLoad = viralLoadResult.getText().toString();
                if (viralLoad.equals("")) {
                    finalViralLoadRsult.setText("");
                } else {
                    final String vResult = viralLoadResultClassification.getSelectedItem().toString();
                    String vResult2 = "";
                    if (vResult.contains("< ")) {
                        vResult2 = vResult.replace("< ", "");
                    } else if (vResult.contains(">= ")) {
                        vResult2 = vResult.replace(">= ", "");
                    }

                    if (Long.parseLong(viralLoad) < Long.parseLong(vResult2) && vResult.equals("< 1000") && viralLoad.length() <= 3) {
                        finalViralLoadRsult.setText("Long-Term Infection");
                        finalViralLoadRsult.setEnabled(false);
                        viralLoadResultTest1.setError("Maximum Value select viral Result Classification");
                    } else if (Long.parseLong(viralLoad) >= Long.parseLong(vResult2) && vResult.equals(">= 1000") && viralLoad.length() > 3) {
                        finalViralLoadRsult.setText("RITA Recent");
                        finalViralLoadRsult.setEnabled(false);
                        viralLoadResultTest1.setError(" ");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput(testDate.getText().toString(), recencyInterpretation.getText().toString())) {
                    int count = new RecencyDAO(getApplicationContext()).checkIfRecencyExist(Long.parseLong(htsId));
                    if (count > 0) {
                        FancyToast.makeText(getApplicationContext(), "Recency Already Exist", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    } else {
                        Recency recency = new Recency();
                        recency.setRecencyNumber(participatnNumber.getText().toString());
                        recency.setTestDate(testDate.getText().toString());
                        recency.setTestName(testName.getText().toString());
                        Hts2 hts1 = new Hts2();
                        hts1.setHtsId(Long.parseLong(htsId));
                        recency.setHts(hts1);
                        if (controlLine.isChecked()) {
                            recency.setControlLine(1L);
                        }
                        if (verificationLine.isChecked()) {
                            recency.setVerificationLine(1L);
                        }
                        if (longTermLine.isChecked()) {
                            recency.setLongTimeLine(1L);
                        }
                        recency.setRecencyInterpretation(recencyInterpretation.getText().toString());
                        if (recencyInterpretation.getText().toString().equals("Recent")) {
                            ViralLoad viralLoad = new ViralLoad();
                            viralLoad.setRecencyNumber(participatnNumber.getText().toString());
                            viralLoad.setSampleReferenceNumber(sampleReferenceNumber.getText().toString());
                            viralLoad.setViralLoadResultClassification(viralLoadResultClassification.getSelectedItem().toString());
                            viralLoad.setDateSampleCollected(dateSampleCollected.getText().toString());
                            viralLoad.setTypeSample(typesample.getSelectedItem().toString());
                            viralLoad.setDateSampleTest(dateSampleTest.getText().toString());
                            viralLoad.setDateTestDone(dateTestDone.getText().toString());
                            viralLoad.setViralLoadResult(viralLoadResult.getText().toString());
                            viralLoad.setFinalResult(finalViralLoadRsult.getText().toString());
                            new ViralLoadDAO(getApplicationContext()).save(viralLoad);
                        }
                        recencyDAO.save(recency);
                        FancyToast.makeText(getApplicationContext(), "Recency saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        showAlert1(Long.parseLong(htsId));
                    }
                }
            }
        });
    }


    private void showAlert() {
        LayoutInflater li = LayoutInflater.from(RecencyTesting.this);
        View promptsView = li.inflate(R.layout.activity_viral_load_pop_up, null);
        final AlertDialog dialog = new AlertDialog.Builder(RecencyTesting.this).create();
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
                dateSampleCollectedText.setVisibility(View.VISIBLE);
                dateSampleTestText.setVisibility(View.VISIBLE);
                dateTestDoneText.setVisibility(View.VISIBLE);
                viralLoadResultText.setVisibility(View.VISIBLE);
                finalViralLoadRsultText.setVisibility(View.VISIBLE);
                typeSampleText.setVisibility(View.VISIBLE);
                typesample.setVisibility(View.VISIBLE);
                dateSampleCollected.setVisibility(View.VISIBLE);
                viralLoadResultClassification.setVisibility(View.VISIBLE);
                viralLoadResultClassification1.setVisibility(View.VISIBLE);
                dateSampleTest.setVisibility(View.VISIBLE);
                dateTestDone.setVisibility(View.VISIBLE);
                viralLoadResult.setVisibility(View.VISIBLE);
                finalViralLoadRsult.setVisibility(View.VISIBLE);
                sampleReferenceNumberText.setVisibility(View.INVISIBLE);
                sampleReferenceNumber.setVisibility(View.INVISIBLE);
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }


    private boolean validateInput(String testDate1, String interpretation) {
        if (testDate1.isEmpty()) {
            testDate.setError("Enter date confirmed HIV status");
            FancyToast.makeText(getApplicationContext(), "Enter test date", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;
        }
        if (interpretation.isEmpty()) {
            recencyInterpretation.setText("Enter  Interpretation");
            FancyToast.makeText(getApplicationContext(), "Enter test Interpretation", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
        }
        return true;


    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        testDate.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateLabe2() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateSampleCollected.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateLabe3() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateSampleTest.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateLabe4() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateTestDone.setText(sdf.format(myCalendar.getTime()));

    }

    @Override
    public void onClick(View v) {
        if (controlLine.isChecked() && !verificationLine.isChecked() && !longTermLine.isChecked()) {
            recencyInterpretation.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String recencyInterpretation1 = recencyInterpretation.getText().toString();
                    String value = "Negative";
                    if (recencyInterpretation1.equals("Negative")) {
                        interpretationmessage.setError("Interpretation Match");
                        int errorColor = ContextCompat.getColor(getApplicationContext(), R.color.lightGreen);
                        interpretationmessage.setErrorTextColor(ColorStateList.valueOf(errorColor));
                    } else {
                        interpretationmessage.setError("Interpretation do not Match");
                        int errorColor = ContextCompat.getColor(getApplicationContext(), R.color.colorCancel);
                        interpretationmessage.setErrorTextColor(ColorStateList.valueOf(errorColor));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            dateSampleCollectedText.setVisibility(View.INVISIBLE);
            dateSampleTestText.setVisibility(View.INVISIBLE);
            typeSampleText.setVisibility(View.INVISIBLE);
            typesample.setVisibility(View.INVISIBLE);
            sampleReferenceNumber.setVisibility(View.INVISIBLE);
            sampleReferenceNumberText.setVisibility(View.INVISIBLE);
            dateTestDoneText.setVisibility(View.INVISIBLE);
            viralLoadResultText.setVisibility(View.INVISIBLE);
            finalViralLoadRsultText.setVisibility(View.INVISIBLE);
            viralLoadResultClassification.setVisibility(View.INVISIBLE);
            viralLoadResultClassification1.setVisibility(View.INVISIBLE);
            dateSampleCollected.setVisibility(View.INVISIBLE);
            dateSampleTest.setVisibility(View.INVISIBLE);
            dateTestDone.setVisibility(View.INVISIBLE);
            viralLoadResult.setVisibility(View.INVISIBLE);
            finalViralLoadRsult.setVisibility(View.INVISIBLE);
        }
        if (controlLine.isChecked() && verificationLine.isChecked() && !longTermLine.isChecked()) {
            recencyInterpretation.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String recencyInterpretation1 = recencyInterpretation.getText().toString();
                    if (recencyInterpretation1.equals("Recent")) {
                        interpretationmessage.setError("Interpretation Match");
                        int errorColor = ContextCompat.getColor(getApplicationContext(), R.color.lightGreen);
                        interpretationmessage.setErrorTextColor(ColorStateList.valueOf(errorColor));
                        showAlert();
                    } else {
                        interpretationmessage.setError("Interpretation do not Match");
                        int errorColor = ContextCompat.getColor(getApplicationContext(), R.color.colorCancel);
                        interpretationmessage.setErrorTextColor(ColorStateList.valueOf(errorColor));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }
        if (controlLine.isChecked() && verificationLine.isChecked() && longTermLine.isChecked()) {
            recencyInterpretation.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String recencyInterpretation1 = recencyInterpretation.getText().toString();
                    String value = "Long-Term";
                    if (recencyInterpretation1.equals(value)) {
                        interpretationmessage.setError("Interpretation Match but Viral Load request not required");
                        int errorColor = ContextCompat.getColor(getApplicationContext(), R.color.lightGreen);
                        interpretationmessage.setErrorTextColor(ColorStateList.valueOf(errorColor));

                    } else {
                        interpretationmessage.setError("Interpretation do not Match");
                        int errorColor = ContextCompat.getColor(getApplicationContext(), R.color.colorCancel);
                        interpretationmessage.setErrorTextColor(ColorStateList.valueOf(errorColor));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            dateSampleCollectedText.setVisibility(View.INVISIBLE);
            dateSampleTestText.setVisibility(View.INVISIBLE);
            typeSampleText.setVisibility(View.INVISIBLE);
            typesample.setVisibility(View.INVISIBLE);
            sampleReferenceNumber.setVisibility(View.INVISIBLE);
            sampleReferenceNumberText.setVisibility(View.INVISIBLE);
            dateTestDoneText.setVisibility(View.INVISIBLE);
            viralLoadResultText.setVisibility(View.INVISIBLE);
            finalViralLoadRsultText.setVisibility(View.INVISIBLE);
            viralLoadResultClassification.setVisibility(View.INVISIBLE);
            viralLoadResultClassification1.setVisibility(View.INVISIBLE);
            dateSampleCollected.setVisibility(View.INVISIBLE);
            dateSampleTest.setVisibility(View.INVISIBLE);
            dateTestDone.setVisibility(View.INVISIBLE);
            viralLoadResult.setVisibility(View.INVISIBLE);
            finalViralLoadRsult.setVisibility(View.INVISIBLE);
        }
        if (!controlLine.isChecked() && verificationLine.isChecked() && longTermLine.isChecked()) {
            recencyInterpretation.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String recencyInterpretation1 = recencyInterpretation.getText().toString();
                    if (recencyInterpretation1.equalsIgnoreCase("Invalid")) {
                        interpretationmessage.setError("Interpretation Match");
                        int errorColor = ContextCompat.getColor(getApplicationContext(), R.color.lightGreen);
                        interpretationmessage.setErrorTextColor(ColorStateList.valueOf(errorColor));
                    } else {
                        interpretationmessage.setError("Interpretation do not Match");
                        int errorColor = ContextCompat.getColor(getApplicationContext(), R.color.colorCancel);
                        interpretationmessage.setErrorTextColor(ColorStateList.valueOf(errorColor));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            dateSampleCollectedText.setVisibility(View.INVISIBLE);
            dateSampleTestText.setVisibility(View.INVISIBLE);
            typeSampleText.setVisibility(View.INVISIBLE);
            typesample.setVisibility(View.INVISIBLE);
            sampleReferenceNumber.setVisibility(View.INVISIBLE);
            sampleReferenceNumberText.setVisibility(View.INVISIBLE);
            dateTestDoneText.setVisibility(View.INVISIBLE);
            viralLoadResultText.setVisibility(View.INVISIBLE);
            finalViralLoadRsultText.setVisibility(View.INVISIBLE);
            viralLoadResultClassification.setVisibility(View.INVISIBLE);
            viralLoadResultClassification1.setVisibility(View.INVISIBLE);
            dateSampleCollected.setVisibility(View.INVISIBLE);
            dateSampleTest.setVisibility(View.INVISIBLE);
            dateTestDone.setVisibility(View.INVISIBLE);
            viralLoadResult.setVisibility(View.INVISIBLE);
            finalViralLoadRsult.setVisibility(View.INVISIBLE);
        }
        if (!controlLine.isChecked() && !verificationLine.isChecked() && longTermLine.isChecked()) {
            recencyInterpretation.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String recencyInterpretation1 = recencyInterpretation.getText().toString();
                    if (recencyInterpretation1.equalsIgnoreCase("Invalid")) {
                        interpretationmessage.setError("Interpretation Match");
                        int errorColor = ContextCompat.getColor(getApplicationContext(), R.color.lightGreen);
                        interpretationmessage.setErrorTextColor(ColorStateList.valueOf(errorColor));
                    } else {
                        interpretationmessage.setError("Interpretation do not Match");
                        int errorColor = ContextCompat.getColor(getApplicationContext(), R.color.colorCancel);
                        interpretationmessage.setErrorTextColor(ColorStateList.valueOf(errorColor));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });

            dateSampleCollectedText.setVisibility(View.INVISIBLE);
            dateSampleTestText.setVisibility(View.INVISIBLE);
            typeSampleText.setVisibility(View.INVISIBLE);
            typesample.setVisibility(View.INVISIBLE);
            sampleReferenceNumber.setVisibility(View.INVISIBLE);
            sampleReferenceNumberText.setVisibility(View.INVISIBLE);
            dateTestDoneText.setVisibility(View.INVISIBLE);
            viralLoadResultText.setVisibility(View.INVISIBLE);
            finalViralLoadRsultText.setVisibility(View.INVISIBLE);
            viralLoadResultClassification.setVisibility(View.INVISIBLE);
            viralLoadResultClassification1.setVisibility(View.INVISIBLE);
            dateSampleCollected.setVisibility(View.INVISIBLE);
            dateSampleTest.setVisibility(View.INVISIBLE);
            dateTestDone.setVisibility(View.INVISIBLE);
            viralLoadResult.setVisibility(View.INVISIBLE);
            finalViralLoadRsult.setVisibility(View.INVISIBLE);
        }
        if (controlLine.isChecked() && !verificationLine.isChecked() && longTermLine.isChecked()) {
            recencyInterpretation.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String recencyInterpretation1 = recencyInterpretation.getText().toString();
                    String value = "Invalid";
                    if (recencyInterpretation1.equals(value)) {
                        interpretationmessage.setError("Interpretation Match");
                        int errorColor = ContextCompat.getColor(getApplicationContext(), R.color.lightGreen);
                        interpretationmessage.setErrorTextColor(ColorStateList.valueOf(errorColor));
                    } else {
                        interpretationmessage.setError("Interpretation do not Match");
                        int errorColor = ContextCompat.getColor(getApplicationContext(), R.color.colorCancel);
                        interpretationmessage.setErrorTextColor(ColorStateList.valueOf(errorColor));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            dateSampleCollectedText.setVisibility(View.INVISIBLE);
            dateSampleTestText.setVisibility(View.INVISIBLE);
            typeSampleText.setVisibility(View.INVISIBLE);
            typesample.setVisibility(View.INVISIBLE);
            sampleReferenceNumber.setVisibility(View.INVISIBLE);
            sampleReferenceNumberText.setVisibility(View.INVISIBLE);
            dateTestDoneText.setVisibility(View.INVISIBLE);
            viralLoadResultText.setVisibility(View.INVISIBLE);
            finalViralLoadRsultText.setVisibility(View.INVISIBLE);
            viralLoadResultClassification.setVisibility(View.INVISIBLE);
            viralLoadResultClassification1.setVisibility(View.INVISIBLE);
            dateSampleCollected.setVisibility(View.INVISIBLE);
            dateSampleTest.setVisibility(View.INVISIBLE);
            dateTestDone.setVisibility(View.INVISIBLE);
            viralLoadResult.setVisibility(View.INVISIBLE);
            finalViralLoadRsult.setVisibility(View.INVISIBLE);
        }
        if (!controlLine.isChecked() && verificationLine.isChecked() && !longTermLine.isChecked()) {
            recencyInterpretation.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String recencyInterpretation1 = recencyInterpretation.getText().toString();
                    String value = "Invalid";
                    if (recencyInterpretation1.equals(value)) {
                        interpretationmessage.setError("Interpretation Match");
                        int errorColor = ContextCompat.getColor(getApplicationContext(), R.color.lightGreen);
                        interpretationmessage.setErrorTextColor(ColorStateList.valueOf(errorColor));
                    } else {
                        interpretationmessage.setError("Interpretation do not Match");
                        int errorColor = ContextCompat.getColor(getApplicationContext(), R.color.colorCancel);
                        interpretationmessage.setErrorTextColor(ColorStateList.valueOf(errorColor));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            dateSampleCollectedText.setVisibility(View.INVISIBLE);
            dateSampleTestText.setVisibility(View.INVISIBLE);
            typeSampleText.setVisibility(View.INVISIBLE);
            typesample.setVisibility(View.INVISIBLE);
            sampleReferenceNumber.setVisibility(View.INVISIBLE);
            sampleReferenceNumberText.setVisibility(View.INVISIBLE);
            dateTestDoneText.setVisibility(View.INVISIBLE);
            viralLoadResultText.setVisibility(View.INVISIBLE);
            finalViralLoadRsultText.setVisibility(View.INVISIBLE);
            viralLoadResultClassification.setVisibility(View.INVISIBLE);
            viralLoadResultClassification1.setVisibility(View.INVISIBLE);
            dateSampleCollected.setVisibility(View.INVISIBLE);
            dateSampleTest.setVisibility(View.INVISIBLE);
            dateTestDone.setVisibility(View.INVISIBLE);
            viralLoadResult.setVisibility(View.INVISIBLE);
            finalViralLoadRsult.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private void showAlert1(final long htsIds) {
        LayoutInflater li = LayoutInflater.from(RecencyTesting.this);
        View promptsView = li.inflate(R.layout.pop_up, null);
        final AlertDialog dialog = new AlertDialog.Builder(RecencyTesting.this).create();
        dialog.setView(promptsView);
        final TextView notitopOk, notitopNotnow;
        notitopOk = promptsView.findViewById(R.id.notitopOk);
        notitopNotnow = promptsView.findViewById(R.id.notitopNotnow);
        notitopNotnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = RecencyTesting.this.getSharedPreferences("profielDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("htsId", htsIds + "");
                editor.putString("name", hts.getSurname() + " " + hts.getOtherNames());
                editor.putString("clientcode", hts.getClientCode() + "");
                Hts2 hts2 = new Hts2();
                hts2.setHtsId(htsIds);
                new HtsDAO(getApplicationContext()).updateContact1(hts2, "0");
                editor.commit();
                Intent intent = new Intent(RecencyTesting.this, Home.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        notitopOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecencyTesting.this, IndexTesting.class);
                SharedPreferences sharedPreferences = RecencyTesting.this.getSharedPreferences("profielDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("htsId", htsIds + "");
                editor.putString("name", hts.getSurname() + " " + hts.getOtherNames());
                editor.putString("clientcode", hts.getClientCode() + "");
                Hts2 hts2 = new Hts2();
                hts2.setHtsId(htsIds);
                new HtsDAO(getApplicationContext()).updateContact1(hts2, "1");
                editor.commit();
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }


}
