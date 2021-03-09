package org.fhi360.lamis.mobile.lite.Activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
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
import android.widget.ScrollView;
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
import org.fhi360.lamis.mobile.lite.Utils.SettingConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public class RecencyTesting2 extends AppCompatActivity implements View.OnClickListener {
    private EditText participatnNumber, sampleReferenceNumber, testName, confirmPartipanctNumber, fullname;
    private EditText testDate, dateSampleCollected, dateSampleTest, dateTestDone, viralLoadResult, finalViralLoadRsult;
    private ScrollView activity_index1;
    private CheckBox controlLine, verificationLine, longTermLine;
    private LinearLayout dateSampleCollectedText, sampleReferenceNumberText, viralLoadResultClassification1, dateSampleTestText, dateTestDoneText, viralLoadResultText, finalViralLoadRsultText;
    private PrefManager session;
    private EditText recencyInterpretation;
    private RecencyDAO recencyDAO;
    private Calendar myCalendar = Calendar.getInstance();
    private Button finishButton;
    private AppCompatSpinner typesample, viralLoadResultClassification;
    LinearLayout typeSampleText;
    TextInputLayout error, interpretationmessage, viralLoadResultTest1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recency);
        try {

            Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));
            session = new PrefManager(this);
            HashMap<String, String> user1 = session.getProfileDetails();
            final String htsId = user1.get("htsId");

            recencyDAO = new RecencyDAO(this);
            activity_index1 = findViewById(R.id.vrialaod);
            confirmPartipanctNumber = findViewById(R.id.participantNumber2);
            viralLoadResultTest1 = findViewById(R.id.viralLoadResultTest1);
            error = findViewById(R.id.error1);
            interpretationmessage = findViewById(R.id.interpretationmessage);
            participatnNumber = findViewById(R.id.recencyNumber);
            fullname = findViewById(R.id.recencyName);
            testName = findViewById(R.id.testName);
            testDate = findViewById(R.id.testDate);
            controlLine = findViewById(R.id.controlline);
            verificationLine = findViewById(R.id.verificationLine);
            longTermLine = findViewById(R.id.longTimeLine);
            finishButton = findViewById(R.id.finishButton);
            recencyInterpretation = findViewById(R.id.rececnyInterpretation);
            dateSampleCollected = findViewById(R.id.dateSampleCollected);
            dateSampleTest = findViewById(R.id.dateSampleTest);
            dateTestDone = findViewById(R.id.dateTestDone);
            viralLoadResult = findViewById(R.id.viralLoadResult);
            finalViralLoadRsult = findViewById(R.id.finalViralLoadRsult);
            viralLoadResultClassification1 = findViewById(R.id.viralLoadResultClassification1);
            viralLoadResultClassification = findViewById(R.id.viralLoadResultClassification);
            dateSampleCollectedText = findViewById(R.id.dateSampleCollectedText);
            dateSampleTestText = findViewById(R.id.dateSampleTestText);
            dateTestDoneText = findViewById(R.id.dateTestDoneTest);
            typeSampleText = findViewById(R.id.typeSampleText);
            viralLoadResultText = findViewById(R.id.viralLoadResultTest);
            typesample = findViewById(R.id.typesample);
            finalViralLoadRsultText = findViewById(R.id.finalViralLoadRsultTest);
            sampleReferenceNumberText = findViewById(R.id.sampleReferenceNumberText);
            sampleReferenceNumber = findViewById(R.id.sampleReferenceNumber);
            sampleReferenceNumber.setEnabled(true);

            dateSampleCollected.setVisibility(View.INVISIBLE);
            dateSampleTest.setVisibility(View.INVISIBLE);
            dateTestDone.setVisibility(View.INVISIBLE);
            viralLoadResult.setVisibility(View.INVISIBLE);
            viralLoadResultClassification1.setVisibility(View.INVISIBLE);
            viralLoadResultClassification.setVisibility(View.INVISIBLE);
            finalViralLoadRsult.setVisibility(View.INVISIBLE);
            sampleReferenceNumberText.setVisibility(View.INVISIBLE);
            sampleReferenceNumber.setVisibility(View.INVISIBLE);

            final Hts hts = new HtsDAO(this).getData(Long.parseLong(htsId));
            System.out.println("hts name " + hts.getSurname() + hts.getOtherNames());
            fullname.setText(hts.getSurname() + hts.getOtherNames());
            int recency2 = new RecencyDAO(this).checkIfRecencyExist(Long.parseLong(htsId));
            Recency recency = recencyDAO.getByHtsId(Long.parseLong(htsId));

            if (recency2 > 0) {
                controlLine.setOnClickListener(this);
                verificationLine.setOnClickListener(this);
                longTermLine.setOnClickListener(this);
                participatnNumber.setText(recency.getRecencyNumber());
                testName.setText(recency.getTestName());
                testDate.setText(recency.getTestDate());
                if (recency.getRecencyInterpretation().equalsIgnoreCase("Recent")) {
                    recencyInterpretation.setText("Recent");
                    verificationLine.setChecked(true);
                    controlLine.setChecked(true);
                    participatnNumber.setEnabled(false);
                    error.setVisibility(View.INVISIBLE);
                    confirmPartipanctNumber.setVisibility(View.INVISIBLE);
                    sampleReferenceNumberText.setVisibility(View.VISIBLE);
                    dateSampleCollectedText.setVisibility(View.VISIBLE);
                    dateSampleTestText.setVisibility(View.VISIBLE);
                    dateTestDoneText.setVisibility(View.VISIBLE);
                    viralLoadResultText.setVisibility(View.VISIBLE);
                    finalViralLoadRsultText.setVisibility(View.VISIBLE);
                    dateSampleCollected.setVisibility(View.VISIBLE);
                    dateSampleTest.setVisibility(View.VISIBLE);
                    typesample.setVisibility(View.VISIBLE);
                    typeSampleText.setVisibility(View.VISIBLE);
                    dateTestDone.setVisibility(View.VISIBLE);
                    viralLoadResult.setVisibility(View.VISIBLE);
                    viralLoadResultClassification.setVisibility(View.VISIBLE);
                    viralLoadResultClassification1.setVisibility(View.VISIBLE);
                    finalViralLoadRsult.setVisibility(View.VISIBLE);
                    sampleReferenceNumber.setVisibility(View.VISIBLE);
                    int count = new ViralLoadDAO(this).getViralLoadByReference1(recency.getRecencyNumber());
                    if (count > 0) {
                        ViralLoad viralLoad = new ViralLoadDAO(this).getViralLoadByReference(recency.getRecencyNumber());
                        dateSampleCollected.setText(viralLoad.getDateSampleCollected());
                        SettingConfig.setSpinText(typesample, viralLoad.getTypeSample());
                        if (viralLoad.getSampleReferenceNumber() != null) {
                            sampleReferenceNumber.setText(viralLoad.getSampleReferenceNumber());
                        }
                        SettingConfig.setSpinText(viralLoadResultClassification, viralLoad.getTypeSample());
                        dateSampleTest.setText(viralLoad.getDateSampleTest());
                        dateTestDone.setText(viralLoad.getDateTestDone());
                        sampleReferenceNumber.setText(viralLoad.getSampleReferenceNumber());
                        viralLoadResult.setText(viralLoad.getViralLoadResult());
                        finalViralLoadRsult.setText(viralLoad.getFinalResult());
                    } else {
                        typesample.setVisibility(View.INVISIBLE);
                        typeSampleText.setVisibility(View.INVISIBLE);
                        viralLoadResultClassification.setVisibility(View.INVISIBLE);
                        viralLoadResultClassification1.setVisibility(View.INVISIBLE);
                        dateSampleCollectedText.setVisibility(View.INVISIBLE);
                        dateSampleTestText.setVisibility(View.INVISIBLE);
                        dateTestDoneText.setVisibility(View.INVISIBLE);
                        viralLoadResultText.setVisibility(View.INVISIBLE);
                        finalViralLoadRsultText.setVisibility(View.INVISIBLE);
                        dateSampleCollected.setVisibility(View.INVISIBLE);
                        dateSampleTest.setVisibility(View.INVISIBLE);
                        dateTestDone.setVisibility(View.INVISIBLE);
                        viralLoadResult.setVisibility(View.INVISIBLE);
                        finalViralLoadRsult.setVisibility(View.INVISIBLE);
                    }
                } else {
                    if (recency.getLongTimeLine() != null) {
                        longTermLine.setChecked(true);
                    }
                    if (recency.getVerificationLine() != null) {
                        verificationLine.setChecked(true);
                    }
                    if (recency.getControlLine() != null) {
                        controlLine.setChecked(true);
                    }
                    recencyInterpretation.setText(recency.getRecencyInterpretation());
                }
            } else if (recency2 == 0) {
                controlLine.setOnClickListener(this);
                verificationLine.setOnClickListener(this);
                longTermLine.setOnClickListener(this);
                testName.setText("Asante");
            }
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
                    final DatePickerDialog mDatePicker = new DatePickerDialog(RecencyTesting2.this, date, myCalendar
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
                    final DatePickerDialog mDatePicker = new DatePickerDialog(RecencyTesting2.this, dateSampleCollected1, myCalendar
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
                    final DatePickerDialog mDatePicker = new DatePickerDialog(RecencyTesting2.this, dateSampleTest1, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    //mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
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
                    final DatePickerDialog mDatePicker = new DatePickerDialog(RecencyTesting2.this, dateTestDone1, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                    mDatePicker.show();
                }


            });

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

                @Override
                public void afterTextChanged(Editable s) {
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
                            //viralLoadResultTest1.setError("Maximum Value, select viral Result Classification");
                        } else if (Long.parseLong(viralLoad) >= Long.parseLong(vResult2) && vResult.equals(">= 1000") && viralLoad.length() > 3) {
                            finalViralLoadRsult.setText("RITA Recent");
                            finalViralLoadRsult.setEnabled(false);
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
                        int count = new RecencyDAO(getApplicationContext()).checkIfRecencyExist(Long.parseLong(htsId));
                        if (count > 0) {
                            Recency recency1 = recencyDAO.getByHtsId(Long.parseLong(htsId));
                            if (recency1 != null) {
                                if (recencyInterpretation.getText().toString().equals("Recent")) {
                                    ViralLoad viralLoad1 = new ViralLoadDAO(getApplicationContext()).getViralLoadByReference(recency.getRecencyNumber());
                                    ViralLoad viralLoad = new ViralLoad();
                                    viralLoad.setId(viralLoad1.getId());
                                    viralLoad.setViralLoadResultClassification(viralLoadResultClassification.getSelectedItem().toString());
                                    viralLoad.setDateSampleCollected(dateSampleCollected.getText().toString());
                                    viralLoad.setSampleReferenceNumber(sampleReferenceNumber.getText().toString());
                                    viralLoad.setTypeSample(typesample.getSelectedItem().toString());
                                    viralLoad.setDateSampleTest(dateSampleTest.getText().toString());
                                    viralLoad.setDateTestDone(dateTestDone.getText().toString());
                                    viralLoad.setViralLoadResult(viralLoadResult.getText().toString());
                                    viralLoad.setFinalResult(finalViralLoadRsult.getText().toString());
                                    new ViralLoadDAO(getApplicationContext()).update(viralLoad);
                                }
                                recency.setId(recency1.getId());
                                recencyDAO.update(recency);
                                FancyToast.makeText(getApplicationContext(), "Recency updated successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                            }
                        } else {
                            if (recencyInterpretation.getText().toString().equals("Recent")) {
                                ViralLoad viralLoad = new ViralLoad();
                                viralLoad.setRecencyNumber(participatnNumber.getText().toString());
                                viralLoad.setSampleReferenceNumber(sampleReferenceNumber.getText().toString());
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

                        }
                    }

                }
            });


        } catch (Exception ignored) {

        }

    }

    private void showAlert() {
        LayoutInflater li = LayoutInflater.from(RecencyTesting2.this);
        View promptsView = li.inflate(R.layout.activity_viral_load_pop_up, null);
        final AlertDialog dialog = new AlertDialog.Builder(RecencyTesting2.this).create();
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
                sampleReferenceNumberText.setVisibility(View.VISIBLE);
                sampleReferenceNumber.setVisibility(View.VISIBLE);
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
}
