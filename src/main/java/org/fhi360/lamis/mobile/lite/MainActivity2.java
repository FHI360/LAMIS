package org.fhi360.lamis.mobile.lite;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.neurotec.biometrics.NBiometricOperation;
import com.neurotec.biometrics.NBiometricStatus;
import com.neurotec.biometrics.NBiometricTask;
import com.neurotec.biometrics.NFMatchingDetails;
import com.neurotec.biometrics.NFRecord;
import com.neurotec.biometrics.NFTemplate;
import com.neurotec.biometrics.NMatchingResult;
import com.neurotec.biometrics.NSubject;
import com.neurotec.biometrics.NTemplate;
import com.neurotec.biometrics.client.NBiometricClient;
import com.neurotec.io.NBuffer;
import com.neurotec.lang.NCore;
import com.neurotec.licensing.gui.ActivationActivity;
import com.neurotec.samples.app.BaseActivity;
import com.neurotec.samples.licensing.LicensingManager;
import com.neurotec.util.concurrent.CompletionHandler;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.lamis.mobile.lite.DAO.PatientDAO;
import org.fhi360.lamis.mobile.lite.Domains.Patient;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

public final class MainActivity2 extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    private String hospitalNumber;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private static final String WARNING_PROCEED_WITH_NOT_GRANTED_PERMISSIONS = "Do you wish to proceed without granting all permissions?";
    private static final String WARNING_NOT_ALL_GRANTED = "Some permissions are not granted.";
    private static final String MESSAGE_ALL_PERMISSIONS_GRANTED = "All permissions granted";

    private static String TAG = MainActivity.class.getSimpleName();

    private final int MODALITY_CODE_FINGER = 2;
    private List<NFRecord> mFingers;
    private TextView mSubjectId;
    private TextView mFingerCounter;


    private static List<String> getMandatoryComponentsInternal() {
        List<String> components = new ArrayList<String>();
        for (String component : FingerActivity2.mandatoryComponents()) {
            if (!components.contains(component)) {
                components.add(component);
            }
        }

        return components;
    }

    private static List<String> getAdditionalComponentsInternal() {
        List<String> components = new ArrayList<String>();

        for (String component : FingerActivity2.additionalComponents()) {
            if (!components.contains(component)) {
                components.add(component);
            }
        }

        return components;
    }

    private static List<String> getRequiredPermissions() {
        List<String> permissions = new ArrayList<String>();
        permissions.add(Manifest.permission.INTERNET);
        permissions.add(Manifest.permission.ACCESS_NETWORK_STATE);
        // permissions.add(Manifest.permission.CAMERA);
        // permissions.add(Manifest.permission.RECORD_AUDIO);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (android.os.Build.VERSION.SDK_INT < 23) {
            permissions.add(Manifest.permission.WRITE_SETTINGS);
        }
        return permissions;
    }

    public static List<String> getAllComponentsInternal() {
        List<String> combinedComponents = getMandatoryComponentsInternal();
        combinedComponents.addAll(getAdditionalComponentsInternal());
        return combinedComponents;
    }

    private void updateRecordCount(List<NFRecord> fingers) {
        if (fingers != null) {
            mFingerCounter.setText(String.valueOf(fingers.size()));
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private String[] getNotGrantedPermissions() {
        List<String> neededPermissions = new ArrayList<>();

        for (String permission : getRequiredPermissions()) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                neededPermissions.add(permission);
            }
        }
        return neededPermissions.toArray(new String[neededPermissions.size()]);
    }

    private void requestPermissions(String[] permissions) {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_ID_MULTIPLE_PERMISSIONS);
    }

    private void verify() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity2.this);
        builderSingle.setTitle("Database elements (" + Model.getInstance().getClient().listIds().length + ")");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity2.this, android.R.layout.select_dialog_singlechoice);
        if (Model.getInstance().getClient().listIds().length > 0) {
            arrayAdapter.addAll(Model.getInstance().getClient().listIds());
            builderSingle.setAdapter(arrayAdapter, (dialog, item) -> {
                String strName = arrayAdapter.getItem(item);
                NSubject subject = createSubjectFromRecords(mFingers);
                subject.setId(strName);
                if (subject != null) {
                    NBiometricTask task = Model.getInstance().getClient().createTask(EnumSet.of(NBiometricOperation.VERIFY), subject);
                    Model.getInstance().getClient().performTask(task, NBiometricOperation.VERIFY, completionHandler);
                } else {
                    //showError("Empty subject");
                }
            });
        } else {
            arrayAdapter.add("Database is empty");
            builderSingle.setAdapter(arrayAdapter, (dialogInterface, i) -> {
            });
        }
        builderSingle.setNegativeButton("Close", (dialog, which) -> dialog.dismiss());

        builderSingle.show();
    }
//
//    private void showDatabase() {
//        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity2.this);
//        builderSingle.setTitle("Database elements (" + Model.getInstance().getClient().listIds().length + ")");
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity2.this, android.R.layout.select_dialog_singlechoice);
//        if (Model.getInstance().getClient().listIds().length > 0) {
//            arrayAdapter.addAll(Model.getInstance().getClient().listIds());
//            builderSingle.setAdapter(arrayAdapter, (dialog, item) -> {
//                String strName = arrayAdapter.getItem(item);
//                final String element = strName;
//                AlertDialog.Builder builderInner = new AlertDialog.Builder(MainActivity2.this);
//
//                NSubject subject = new NSubject();
//                subject.setId(strName);
//                NBiometricStatus status = Model.getInstance().getClient().get(subject);
//                StringBuilder sb = new StringBuilder();
//                sb.append("Subject ID: ");
//                sb.append(strName);
//                if (status == NBiometricStatus.OK) {
//                    sb.append("\n");
//                    if (subject.getTemplate() != null) {
//                        if (subject.getTemplate().getFingers() != null) {
//                            for (NFRecord record : subject.getTemplate().getFingers().getRecords()) {
//                                sb.append("\tFinger, quality: ");
//                                sb.append(record.getQuality());
//                                sb.append(", position: ");
//                                sb.append(toLowerCase(record.getPosition().name()));
//                                sb.append("\n");
//                            }
//                        }
//                    }
//                }
//
//                builderInner.setMessage(sb.toString());
//                builderInner.setTitle("Do you wish to delete item?");
//                builderInner.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Model.getInstance().getClient().delete(element);
//                    }
//                });
//                builderInner.setNegativeButton("Close", (dialogInterface, i) -> dialogInterface.dismiss());
//                builderInner.show();
//            });
//
//        } else {
//            arrayAdapter.add("Database is empty");
//            builderSingle.setAdapter(arrayAdapter, (dialogInterface, i) -> {
//
//            });
//        }
//
//        builderSingle.setNegativeButton("Close", (dialog, which) -> dialog.dismiss());
//        builderSingle.show();
//    }

    private NSubject createSubjectFromRecords(List<NFRecord> fingers) {
        if (!fingers.isEmpty()) {
            NSubject subject = new NSubject();
            NTemplate template = new NTemplate();
            NFTemplate fingerTemplate = new NFTemplate();
            for (NFRecord record : fingers) {
                fingerTemplate.getRecords().add(record);
            }
            template.setFingers(fingerTemplate);
            subject.setTemplate(template);
            return subject;
        } else {
            return null;
        }
    }

    private CompletionHandler<NBiometricTask, NBiometricOperation> completionHandler = new CompletionHandler<NBiometricTask, NBiometricOperation>() {
        @Override
        public void completed(NBiometricTask task, NBiometricOperation operation) {
            try {
                String message = null;
                NBiometricStatus status = task.getStatus();
                Log.i(TAG, String.format("Operation: %s, Status: %s", operation, status));

                if (status == NBiometricStatus.CANCELED) return;

                if (task.getError() != null) {
                    showError(task.getError());
                } else {
                    switch (operation) {
                        case ENROLL:
                        case ENROLL_WITH_DUPLICATE_CHECK: {
                            if (status == NBiometricStatus.OK) {
                                message = getString(R.string.msg_enrollment_succeeded);
                            } else {
                                message = getString(R.string.msg_enrollment_failed, status.toString());
                            }
                        }
                        break;
                        case IDENTIFY: {
                            if (status == NBiometricStatus.OK) {
                                StringBuilder sb = new StringBuilder();
                                NSubject subject = task.getSubjects().get(0);
                                for (NMatchingResult result : subject.getMatchingResults()) {
                                    sb.append("MATCHED WITH: " + getString(R.string.msg_identification_results, result.getId())).append('\n');
                                    sb.append("\tMatching score: " + result.getScore() + "\n");
                                    if (result.getMatchingDetails() != null && !result.getMatchingDetails().getFingers().isEmpty()) {
                                        int index = 0;
                                        sb.append("\tFingers fused score: " + result.getMatchingDetails().getFingersScore() + "\n");
                                        for (NFMatchingDetails score : result.getMatchingDetails().getFingers()) {
                                            sb.append("\t\tNF " + index + " with: " + score.getMatchedIndex() + " Score: " + score.getScore() + "\n");
                                            index++;
                                        }
                                    }
                                    sb.append("\n");
                                }
                                message = sb.toString();
                            } else {
                                message = getString(R.string.msg_no_matches);
                            }
                        }
                        break;
                        case VERIFY: {
                            if (status == NBiometricStatus.OK) {
                                StringBuilder sb = new StringBuilder();
                                message = getString(R.string.msg_verification_succeeded);
                            } else {
                                message = getString(R.string.msg_verification_failed, status.toString());
                            }
                        }
                        break;
                        default: {
                            throw new AssertionError("Invalid NBiometricOperation");
                        }
                    }
                }
                showInfo(message);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }

        @Override
        public void failed(Throwable throwable, NBiometricOperation nBiometricOperation) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NCore.setContext(this);
        setContentView(R.layout.multi_modal_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mFingers = new ArrayList<>();
        mFingerCounter = findViewById(R.id.finger_counter);
        mSubjectId = findViewById(R.id.subject_id);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

//        PrefManager session = new PrefManager(getApplicationContext());
//        HashMap<String, String> user1 = session.getPatientId();
//
//        hospitalNumber = user1.get("hospitalNumber");
//        mSubjectId.setText(hospitalNumber);
//        mSubjectId.setEnabled(false);

        ImageView imageFinger = findViewById(R.id.finger);
        imageFinger.setOnClickListener(view -> {
            Intent fingerActivity = new Intent(MainActivity2.this, FingerActivity2.class);
            startActivityForResult(fingerActivity, MODALITY_CODE_FINGER);
        });

        Button enrollSubject = findViewById(R.id.multimodal_button_enroll);
        enrollSubject.setOnClickListener(view -> {
            if (mSubjectId.getText().toString() == "" || mSubjectId.getText().toString().isEmpty()) {
                //showError("Missing ");
            } else {
                NSubject subject = createSubjectFromRecords(mFingers);
                if (subject != null) {
                    NBiometricOperation operation = MultimodalPreferences.isCheckForDuplicates() ? NBiometricOperation.ENROLL_WITH_DUPLICATE_CHECK : NBiometricOperation.ENROLL;
                    NBiometricTask task = Model.getInstance().getClient().createTask(EnumSet.of(operation), subject);
                    Model.getInstance().getClient().performTask(task, operation, completionHandler);
                    FancyToast.makeText(getApplicationContext(), getString(R.string.msg_enrollment_succeeded), FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    Patient patient = new Patient();
                    patient.setHospitalNum(hospitalNumber);
                    patient.setBiometric(1);
                    new PatientDAO(getApplicationContext()).updatePatientHos(patient);
                }  //showError("Empty subject");

            }
        });

//        Button mVerify = findViewById(R.id.multimodal_button_verify);
//        mVerify.setOnClickListener(view -> verify());

        updateRecordCount(mFingers);

        String[] neededPermissions = getNotGrantedPermissions();
        if (neededPermissions.length == 0) {
            new InitializationTask().execute();
        } else {
            requestPermissions(neededPermissions);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult code: " + resultCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case MODALITY_CODE_FINGER: {
                    if (data != null) {
                        Bundle b = data.getExtras();
                        byte[] template = b.getByteArray("finger");
                        NFTemplate nFTemplate = new NFTemplate(new NBuffer(template));
                        if (nFTemplate != null) {
                            for (NFRecord rec : nFTemplate.getRecords()) {
                                mFingers.add(rec);
                            }
                        }
                    }
                }
                break;
                default: {
                    throw new AssertionError("Unrecognised request code");
                }
            }
        }
        updateRecordCount(mFingers);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String toLowerCase(String string) {
        StringBuilder sb = new StringBuilder();
        sb.append(string.substring(0, 1).toUpperCase());
        sb.append(string.substring(1).toLowerCase());
        return sb.toString().replaceAll("_", " ");
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.multimodal_menu, menu);
//        return true;
//    }

    //@Override
    //public boolean onOptionsItemSelected(MenuItem item) {
    //   switch (item.getItemId()) {
//            case R.id.action_clear_db: {
//                new AlertDialog.Builder(this)
//                        .setTitle("Clear database")
//                        .setMessage("Are you sure you want to clear database?")
//                        .setPositiveButton("Clear", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Model.getInstance().getClient().clear();
//                            }
//                        }).setNegativeButton("Cancel", null).show();
//            }
//            break;
//            case R.id.action_view_db: {
//                showDatabase();
//            }
    // break;
//            case R.id.action_activation: {
//                Intent activation = new Intent(this, ActivationActivity.class);
//                Bundle params = new Bundle();
//                params.putStringArrayList(ActivationActivity.LICENSES, new ArrayList<>(getAllComponentsInternal()));
//                activation.putExtras(params);
//                startActivity(activation);
//            }
//            break;
//            case R.id.action_preferences: {
//                startActivity(new Intent(this, MultimodalPreferences.class));
//            }
//            break;
//        }
//        return true;
    //}

    private boolean ifAllPermissionsGranted(int[] results) {
        boolean finalResult = true;
        for (int permissionResult : results) {
            finalResult &= (permissionResult == PackageManager.PERMISSION_GRANTED);
            if (!finalResult) break;
        }
        return finalResult;
    }

    public void onRequestPermissionsResult(int requestCode, final String[] permissions, final int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {

                    // Check if all permissions granted
                    if (!ifAllPermissionsGranted(grantResults)) {
                        showDialogOK(WARNING_PROCEED_WITH_NOT_GRANTED_PERMISSIONS,
                                (dialog, which) -> {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            Log.w(TAG, WARNING_NOT_ALL_GRANTED);
                                            for (int i = 0; i < permissions.length; i++) {
                                                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                                    Log.w(TAG, permissions[i] + ": PERMISSION_DENIED");
                                                }
                                            }
                                            new InitializationTask().execute();
                                            break;
                                        case DialogInterface.BUTTON_NEGATIVE:
                                            requestPermissions(permissions);
                                            break;
                                        default:
                                            throw new AssertionError("Unrecognised permission dialog parameter value");
                                    }
                                });
                    } else {
                        Log.i(TAG, MESSAGE_ALL_PERMISSIONS_GRANTED);
                        new InitializationTask().execute();
                    }
                }
            }
        }
    }

    final class InitializationTask extends AsyncTask<Object, Boolean, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // showProgress(R.string.msg_initializing);
        }

        @Override
        protected Boolean doInBackground(Object... params) {
            //showProgress(R.string.msg_obtaining_licenses);
            try {
                LicensingManager.getInstance().obtain(MainActivity2.this, getAdditionalComponentsInternal());
                if (LicensingManager.getInstance().obtain(MainActivity2.this, getMandatoryComponentsInternal())) {
                    //showToast(R.string.msg_licenses_obtained);
                    //FancyToast.makeText(getApplicationContext(), "Licenses were obtained", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    Intent fingerActivity = new Intent(MainActivity2.this, FingerActivity2.class);
                    startActivityForResult(fingerActivity, MODALITY_CODE_FINGER);

                } else {
                    showToast(R.string.msg_licenses_partially_obtained);
                    // FancyToast.makeText(getApplicationContext(), "Not all Licenses were obtained", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                }
            } catch (Exception e) {
                showError(e.getMessage(), false);
            }
            // showProgress(R.string.msg_initializing_client);

            try {
                NBiometricClient client = Model.getInstance().getClient();
            } catch (Exception e) {
                //  Log.e(TAG, e.getMessage(), e);
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            hideProgress();
        }
    }
}
