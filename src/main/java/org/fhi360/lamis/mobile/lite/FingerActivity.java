package org.fhi360.lamis.mobile.lite;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.neurotec.biometrics.NBiometricStatus;
import com.neurotec.biometrics.NFPosition;
import com.neurotec.biometrics.NFinger;
import com.neurotec.biometrics.NSubject;
import com.neurotec.biometrics.client.NBiometricClient;
import com.neurotec.biometrics.standards.CBEFFBDBFormatIdentifiers;
import com.neurotec.biometrics.standards.CBEFFBiometricOrganizations;
import com.neurotec.biometrics.standards.FMRecord;
import com.neurotec.biometrics.view.NFingerView;
import com.neurotec.biometrics.view.NFingerViewBase.ShownImage;
import com.neurotec.devices.NDevice;
import com.neurotec.devices.NDeviceType;
import com.neurotec.devices.NFScanner;
import com.neurotec.images.NImage;
import com.neurotec.samples.licensing.LicensingManager;
import com.neurotec.samples.util.IOUtils;
import com.neurotec.samples.util.NImageUtils;
import com.neurotec.samples.util.ResourceUtils;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.lamis.mobile.lite.DAO.BiometricDAO;
import org.fhi360.lamis.mobile.lite.DAO.FacilityDAO;
import org.fhi360.lamis.mobile.lite.DAO.PatientDAO;
import org.fhi360.lamis.mobile.lite.Domains.Biometric;
import org.fhi360.lamis.mobile.lite.Domains.Facility;
import org.fhi360.lamis.mobile.lite.Domains.Facility3;
import org.fhi360.lamis.mobile.lite.Domains.Patient3;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class FingerActivity extends BiometricActivity {

    // ===========================================================
    // Private static fields
    // ===========================================================

    private static final String TAG = FingerActivity.class.getSimpleName();
    private static final String BUNDLE_KEY_STATUS = "status";
    private static final String MODALITY_ASSET_DIRECTORY = "fingers";

    // ===========================================================
    // Private fields
    // ===========================================================

    private NFingerView mFingerView;
    private Bitmap mDefaultBitmap;
    private TextView mStatus, mFingerCounter;
    private Map<String, NFPosition> mFingerPositions;

    // ===========================================================
    // Private methods
    // ===========================================================

    private void setFingerPosition() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(FingerActivity.this);
        builderSingle.setTitle("Select finger possition");
        builderSingle.setCancelable(false);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(FingerActivity.this, android.R.layout.select_dialog_singlechoice);

        // arrayAdapter.add(MainActivity.toLowerCase(NFPosition.UNKNOWN.name()));
//Right Index Finger
        //  arrayAdapter.add(MainActivity.toLowerCase(NFPosition.LEFT_LITTLE_FINGER.name()));
        //  arrayAdapter.add(MainActivity.toLowerCase(NFPosition.LEFT_RING_FINGER.name()));
        arrayAdapter.add(MainActivity.toLowerCase(NFPosition.LEFT_MIDDLE_FINGER.name()));
        arrayAdapter.add(MainActivity.toLowerCase(NFPosition.LEFT_INDEX_FINGER.name()));
        arrayAdapter.add(MainActivity.toLowerCase(NFPosition.LEFT_THUMB.name()));

        // arrayAdapter.add(MainActivity.toLowerCase(NFPosition.RIGHT_LITTLE_FINGER.name()));
        // arrayAdapter.add(MainActivity.toLowerCase(NFPosition.RIGHT_RING_FINGER.name()));
        arrayAdapter.add(MainActivity.toLowerCase(NFPosition.RIGHT_MIDDLE_FINGER.name()));
        arrayAdapter.add(MainActivity.toLowerCase(NFPosition.RIGHT_INDEX_FINGER.name()));
        arrayAdapter.add(MainActivity.toLowerCase(NFPosition.RIGHT_THUMB.name()));

        builderSingle.setNegativeButton("Close", (dialog, which) -> dialog.dismiss());

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                String strName = arrayAdapter.getItem(item);
                final String element = strName;
                new PrefManager(getApplicationContext()).fingerPrintPosition(strName);
                subject.getTemplate().getFingers().getRecords().get(0).setPosition(mFingerPositions.get(element));
            }
        });
        builderSingle.show();
    }

    private NFScanner getScanner() {
        //TODO Read last used scanner from preferences
//		for (NPlugin plugin : NDeviceManager.getPluginManager().getPlugins()) {
//			Log.i("Model", String.format("Plugin name => %s, Error => %s", plugin.getModule().getName(), plugin.getError()));
        NDevice fingerDevice = null;
        for (NDevice device : client.getDeviceManager().getDevices()) {
            Log.i("Device", String.format("Device name => %s", device.getDisplayName()));
            if (device.getDeviceType().contains(NDeviceType.FSCANNER)) {
                if (device.getId().equals(PreferenceManager.getDefaultSharedPreferences(this).getString(FingerPreferences.FINGER_CAPTURING_DEVICE, "None"))) {
                    return (NFScanner) device;
                } else if (fingerDevice == null) {
                    fingerDevice = device;
                }
            }
        }
        return (NFScanner) fingerDevice;
    }

    //TODO: Try to load as image
    private NSubject createSubjectFromImage(Uri uri) {
        NSubject subject = null;
        try {
            NImage image = NImageUtils.fromUri(this, uri);
            subject = new NSubject();
            NFinger finger = new NFinger();
            finger.setImage(image);
            subject.getFingers().add(finger);
        } catch (Exception e) {
            Log.i(TAG, "Failed to load file as NImage");
        }
        return subject;
    }

    private NSubject createSubjectFromFile(Uri uri) {
        NSubject subject = null;
        try {
            subject = NSubject.fromMemory(IOUtils.toByteBuffer(this, uri));
        } catch (Exception e) {
            Log.i(TAG, "Failed to load finger from file");
        }
        return subject;
    }

    // ===========================================================
    // Protected methods
    // ===========================================================

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            PreferenceManager.setDefaultValues(this, R.xml.finger_preferences, false);
            LinearLayout layout = findViewById(R.id.multimodal_biometric_layout);
            Spinner setPosition = findViewById(R.id.multimodal_button_unbound);
            mFingerCounter = findViewById(R.id.finger_counter);
            PrefManager session = new PrefManager(getApplicationContext());
            HashMap<String, String> user1 = session.getProfileDetails();
            HashMap<String, String> uuid = session.getUuId();


            //HashMap<String, String> printPosition = session.getPrintPosition();

            mFingerPositions = new HashMap<>();
            //  mFingerPositions.put(MainActivity.toLowerCase(NFPosition.UNKNOWN.name()), NFPosition.UNKNOWN);

            // mFingerPositions.put(MainActivity.toLowerCase(NFPosition.LEFT_LITTLE_FINGER.name()), NFPosition.LEFT_LITTLE_FINGER);
            // mFingerPositions.put(MainActivity.toLowerCase(NFPosition.LEFT_RING_FINGER.name()), NFPosition.LEFT_RING_FINGER);


            // arrayAdapter.add(MainActivity.toLowerCase(NFPosition.RIGHT_LITTLE_FINGER.name()));
            // arrayAdapter.add(MainActivity.toLowerCase(NFPosition.RIGHT_RING_FINGER.name()));

            mFingerPositions.put(MainActivity.toLowerCase(NFPosition.LEFT_MIDDLE_FINGER.name()), NFPosition.LEFT_MIDDLE_FINGER);
            mFingerPositions.put(MainActivity.toLowerCase(NFPosition.LEFT_INDEX_FINGER.name()), NFPosition.LEFT_INDEX_FINGER);
            mFingerPositions.put(MainActivity.toLowerCase(NFPosition.LEFT_THUMB.name()), NFPosition.LEFT_THUMB);

            // mFingerPositions.put(MainActivity.toLowerCase(NFPosition.RIGHT_LITTLE_FINGER.name()), NFPosition.RIGHT_LITTLE_FINGER);
            // mFingerPositions.put(MainActivity.toLowerCase(NFPosition.RIGHT_RING_FINGER.name()), NFPosition.RIGHT_RING_FINGER);
            mFingerPositions.put(MainActivity.toLowerCase(NFPosition.RIGHT_MIDDLE_FINGER.name()), NFPosition.RIGHT_MIDDLE_FINGER);
            mFingerPositions.put(MainActivity.toLowerCase(NFPosition.RIGHT_INDEX_FINGER.name()), NFPosition.RIGHT_INDEX_FINGER);
            mFingerPositions.put(MainActivity.toLowerCase(NFPosition.RIGHT_THUMB.name()), NFPosition.RIGHT_THUMB);

            mFingerView = new NFingerView(this);
            layout.addView(mFingerView);

            mStatus = new TextView(this);
            mStatus.setText("Status");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            mStatus.setLayoutParams(params);
            layout.addView(mStatus);

            mDefaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hand);
            if (savedInstanceState == null) {
                NFinger finger = new NFinger();
                finger.setImage(NImage.fromBitmap(mDefaultBitmap));
                mFingerView.setFinger(finger);
            }
            Button add = findViewById(R.id.multimodal_button_add);
            add.setText("Add");
            add.setOnClickListener(v -> {
//                Intent intent = new Intent();
//                Bundle b = new Bundle();
//                byte[] nFTemplate = subject.getTemplate().getFingers().save().toByteArray();
//                b.putByteArray(RECORD_REQUEST_FINGER, Arrays.copyOf(nFTemplate, nFTemplate.length));
//                intent.putExtras(b);
//                setResult(RESULT_OK, intent);
//                finish();
                byte[] isoTemplate = subject.getTemplateBuffer(CBEFFBiometricOrganizations.ISO_IEC_JTC_1_SC_37_BIOMETRICS,
                        CBEFFBDBFormatIdentifiers.ISO_IEC_JTC_1_SC_37_BIOMETRICS_FINGER_MINUTIAE_RECORD_FORMAT,
                        FMRecord.VERSION_ISO_CURRENT).toByteArray();

                Biometric biometric = new Biometric();
                List<Facility> facilityId = new FacilityDAO(getApplicationContext()).getFacility1();
                for (Facility fac : facilityId) {
                    Facility3 facility = new Facility3();
                    facility.setId(fac.getId());
                    biometric.setFacility(facility);
                }
                String patientId = user1.get("htsId");
                if (patientId != null) {
                    Patient3 patient3 = new Patient3();
                    patient3.setId(Long.valueOf(patientId));
                    biometric.setPatient(patient3);
                    String clientcode = user1.get("clientcode");
                    new PatientDAO(getApplicationContext()).uodateBiometric(1, Long.parseLong(patientId), clientcode);
                }
                String uuid1 = uuid.get("uuid");
                biometric.setUuid(uuid1);

                biometric.setBiometricType("FINGERPRINT");
                String position = setPosition.getSelectedItem().toString();
                System.out.println("position " + position);
                biometric.setTemplateType(position);
                // String encoded = Base64.getEncoder().encodeToString(isoTemplate);
                biometric.setTemplate(isoTemplate);
                Calendar myCalendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = dateFormat.format(myCalendar.getTime());
                biometric.setEnrollmentDate(date);
                biometric.setBuuid(UUID.randomUUID().toString());
                new BiometricDAO(getApplicationContext()).save(biometric);
                FancyToast.makeText(getApplicationContext(), "Enrollment succeeded", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                int count1 = new BiometricDAO(getApplicationContext()).count(Long.valueOf(patientId));
                mFingerCounter.setText(String.valueOf(count1));

            });

            //setPosition.setOnItemSelectedListener(this);
            setPosition.setVisibility(View.VISIBLE);
            List<String> arrayAdapter = new ArrayList<>();
            arrayAdapter.add(MainActivity.toLowerCase(NFPosition.LEFT_MIDDLE_FINGER.name()));
            arrayAdapter.add(MainActivity.toLowerCase(NFPosition.LEFT_INDEX_FINGER.name()));
            arrayAdapter.add(MainActivity.toLowerCase(NFPosition.LEFT_THUMB.name()));

            arrayAdapter.add(MainActivity.toLowerCase(NFPosition.RIGHT_MIDDLE_FINGER.name()));
            arrayAdapter.add(MainActivity.toLowerCase(NFPosition.RIGHT_INDEX_FINGER.name()));
            arrayAdapter.add(MainActivity.toLowerCase(NFPosition.RIGHT_THUMB.name()));
            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayAdapter);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // attaching data adapter to spinner
            setPosition.setAdapter(dataAdapter);

//            setPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    String strName = parent.getItemAtPosition(position).toString();
//                    //  String strName = position.getItem(item);
//                    final String element = strName;
//                    new PrefManager(getApplicationContext()).fingerPrintPosition(strName);
//                    subject.getTemplate().getFingers().getRecords().get(0).setPosition(mFingerPositions.get(element));
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });


            //setPosition.setOnClickListener(view -> setFingerPosition());

        } catch (Exception ignored) {
            //  showError(e);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(BUNDLE_KEY_STATUS, TextUtils.isEmpty(mStatus.getText()) ? "" : mStatus.getText().toString());
    }

    @Override
    protected List<String> getAdditionalComponents() {
        return additionalComponents();
    }

    @Override
    protected List<String> getMandatoryComponents() {
        return mandatoryComponents();
    }

    @Override
    protected Class<?> getPreferences() {
        return FingerPreferences.class;
    }

    @Override
    protected void updatePreferences(NBiometricClient client) {
        FingerPreferences.updateClient(client, this);
    }

    @Override
    protected boolean isCheckForDuplicates() {
        return FingerPreferences.isCheckForDuplicates(this);
    }

    @Override
    protected String getModalityAssetDirectory() {
        return MODALITY_ASSET_DIRECTORY;
    }

    @Override
    protected void onFileSelected(Uri uri) {
        NSubject subject = null;
        mFingerView.setShownImage(FingerPreferences.isReturnBinarizedImage(this) ? ShownImage.RESULT : ShownImage.ORIGINAL);
        subject = createSubjectFromImage(uri);

        if (subject == null) {
            subject = createSubjectFromFile(uri);
        }

        if (subject != null) {
            if (subject.getFingers() != null && subject.getFingers().get(0) != null) {
                mFingerView.setFinger(subject.getFingers().get(0));
            }
            extract(subject);
        } else {
            showInfo(R.string.msg_failed_to_load_image_or_standard);
        }
    }

    @Override
    protected void onStartCapturing() {
        NFScanner scanner = getScanner();
        if (scanner == null) {
            showError(R.string.msg_capturing_device_is_unavailable);
        } else {
            client.setFingerScanner(scanner);
            NSubject subject = new NSubject();
            NFinger finger = new NFinger();
            finger.addPropertyChangeListener(biometricPropertyChanged);
            mFingerView.setShownImage(FingerPreferences.isReturnBinarizedImage(this) ? ShownImage.RESULT : ShownImage.ORIGINAL);
            mFingerView.setFinger(finger);
            subject.getFingers().add(finger);
            capture(subject, null);
        }
    }

    @Override
    protected void onStatusChanged(final NBiometricStatus value) {
        runOnUiThread(() -> mStatus.setText(value == null ? "" : ResourceUtils.getEnum(FingerActivity.this, value)));
    }

    public static List<String> mandatoryComponents() {
        return Arrays.asList(LicensingManager.LICENSE_FINGER_DETECTION,
                LicensingManager.LICENSE_FINGER_EXTRACTION,
                LicensingManager.LICENSE_FINGER_MATCHING,
                LicensingManager.LICENSE_FINGER_DEVICES_SCANNERS);

    }

    public static List<String> additionalComponents() {
        return Arrays.asList(LicensingManager.LICENSE_FINGER_WSQ,
                LicensingManager.LICENSE_FINGER_STANDARDS_FINGER_TEMPLATES,
                LicensingManager.LICENSE_FINGER_STANDARDS_FINGERS);

    }


}
