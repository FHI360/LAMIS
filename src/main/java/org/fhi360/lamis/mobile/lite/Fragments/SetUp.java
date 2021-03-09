package org.fhi360.lamis.mobile.lite.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.neurotec.lang.NCore;
import com.neurotec.licensing.gui.ActivationActivity;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.lamis.mobile.lite.DAO.GeoLocationDAO;
import org.fhi360.lamis.mobile.lite.Domains.Geolocation;
import org.fhi360.lamis.mobile.lite.Domains.GetNameId;
import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Utils.GetCurrentLocation;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;
import org.fhi360.lamis.mobile.lite.Utils.SettingConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;

import static org.fhi360.lamis.mobile.lite.MainActivity.getAllComponentsInternal;

public class SetUp extends AppCompatActivity {
    GetCurrentLocation currentLoc;
    private LocationManager locationManager;
    ExpandableRelativeLayout expandableLayout1, expandableLayout2, expandableLayout3, expandableLayout4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expandable_layout);

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        currentLoc = new GetCurrentLocation(SetUp.this);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorPrimaryDark)));
        geoLocationPopUp();
        localIpAddressPopUp();

    }

    public void expandableButton1(View view) {
        expandableLayout1 = findViewById(R.id.expandableLayout1);
        expandableLayout1.toggle(); // toggle expand and collapse

    }

    public void expandableButton2(View view) {
        expandableLayout2 = findViewById(R.id.expandableLayout2);
        expandableLayout2.toggle(); // toggle expand and collapse


    }


    private void geoLocationPopUp() {

        final AppCompatSpinner testingSetting = findViewById(R.id.testingSetting);
        final EditText name = findViewById(R.id.name);
        final AppCompatSpinner grid = findViewById(R.id.grid);
        final TextView longitude = findViewById(R.id.tvLongitudes);
        final TextView latitude = findViewById(R.id.tvLatitudes);
        final Button captureLocation = findViewById(R.id.captureLocation);
        final Button save = findViewById(R.id.save);
        captureLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentLoc.connectGoogleApi();
                if (new PrefManager(getApplicationContext()).getCoordnitae().get("longitude") != null && new PrefManager(getApplicationContext()).getCoordnitae().get("latitude") != "") {
                    String longitude1 = new PrefManager(getApplicationContext()).getCoordnitae().get("longitude");
                    String latitude1 = new PrefManager(getApplicationContext()).getCoordnitae().get("latitude");
                    longitude.setText(longitude1);
                    latitude.setText(latitude1);

                }
            }
        });

        List<GetNameId> listName = new GeoLocationDAO(getApplicationContext()).getGeolocationName();
        final ArrayList geoId = new ArrayList();
        final ArrayList name1 = new ArrayList();
        geoId.add(0L);
        name1.add(0, "");
        for (GetNameId listNam : listName) {
            geoId.add(listNam.getId());
            name1.add(listNam.getName());
        }

        final ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.spinner_items, name1);
        districtAdapter.setDropDownViewResource(R.layout.color_spinner_layout);
        districtAdapter.notifyDataSetChanged();
        grid.setAdapter(districtAdapter);
        grid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                long id1 = (long) geoId.get(position);
                String name2 = "";
                String longitude1 = "";
                String latitude1 = "";
                List<Geolocation> geolocationss;
                geolocationss = new GeoLocationDAO(getApplicationContext()).getLongitudeAndLatitude(id1);
                for (Geolocation lat : geolocationss) {
                    longitude1 = String.valueOf(lat.getLongitude());
                    latitude1 = String.valueOf(lat.getLatitude());
                    name2 = lat.getName();
                }
                longitude.setText(longitude1);
                latitude.setText(latitude1);
                name.setText(name2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String testingSeting1 = testingSetting.getSelectedItem().toString();
                String name1 = name.getText().toString();
                if (testingSeting1.isEmpty()) {
                    FancyToast.makeText(getApplicationContext(), "TestSetting can't be empty ", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                } else if (name1.isEmpty()) {
                    name.setError("Enter Name");
                    FancyToast.makeText(getApplicationContext(), "Enter Name", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                } else {
                    System.out.printf("TESTING SETTINGS UI" + testingSetting.getSelectedItem().toString());
                    new GeoLocationDAO(getApplicationContext()).saveGeoLocation(testingSetting.getSelectedItem().toString(), name.getText().toString(), longitude.getText().toString(), latitude.getText().toString());
                    FancyToast.makeText(getApplicationContext(), "Geolocation captured successfully ", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    name.setText("");
                    SettingConfig.setSpinText(testingSetting, "");
                    latitude.setText("");
                    longitude.setText("");
                }
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.multimodal_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_activation) {
            Intent activation = new Intent(this, ActivationActivity.class);
            Bundle params = new Bundle();
            NCore.setContext(getApplication());
            params.putStringArrayList(ActivationActivity.LICENSES, new ArrayList<>(getAllComponentsInternal()));
            activation.putExtras(params);
            startActivity(activation);
        }
        return true;
    }

    private void localIpAddressPopUp() {
        final TextView ipaddress = findViewById(R.id.ipaddress);
        Button button_account = findViewById(R.id.button_account);
        button_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ipaddress1 = ipaddress.getText().toString();
                if (ipaddress1.isEmpty()) {
                    ipaddress.setError("Enter Ip address");
                    FancyToast.makeText(getApplicationContext(), "Enter Ip address", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                } else {
                    new PrefManager(getApplicationContext()).saveIpAddress(ipaddress.getText().toString());
                    FancyToast.makeText(getApplicationContext(), "Ip Address saved successfully ", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    ipaddress.setText("");
                }
            }

        });

    }
}




