package org.fhi360.lamis.mobile.lite.Activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.fhi360.lamis.mobile.lite.MainActivity;
import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;

import java.util.HashMap;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private TextView name;
    private TextView age;
    private TextView address;
    private TextView phone;
    private TextView gender;
    private TextView dateOfVisit;
    private String names;
    private String ages;
    private String addresss;
    private String phones;
    private String genders;
    private String dateOfVisits;
    private String hivStatus;
    private ImageView dotMenu;
    private PrefManager session;
    private String patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorPrimaryDark)));
        session = new PrefManager(getApplicationContext());
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        gender = findViewById(R.id.gender);
        dateOfVisit = findViewById(R.id.dateVisit);

        HashMap<String, String> user1 = session.getProfileDetails();
        names = user1.get("name");
        ages = user1.get("age");
        addresss = user1.get("address");
        phones = user1.get("phone");
        genders = user1.get("gender");
        dateOfVisits = user1.get("dateVisit");
        hivStatus = user1.get("hivStatus");
        patientId = user1.get("patientId");
        name.setText(names);
        age.setText(ages + "");
        address.setText(addresss);
        phone.setText(phones);
        gender.setText(genders);
        dateOfVisit.setText(dateOfVisits);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (hivStatus.equalsIgnoreCase("Positive")) {
            getMenuInflater().inflate(R.menu.menu_dot, menu);
            return true;
        } else {
            getMenuInflater().inflate(R.menu.edit, menu);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id == R.id.biometric) {
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//        } else

            if (id == R.id.recencyTesting1) {
            Intent intent = new Intent(getApplicationContext(), RecencyTesting2.class);
            startActivity(intent);
        } else if (id == R.id.indexTesting) {
            Intent intent = new Intent(getApplicationContext(), IndexTesting.class);
            startActivity(intent);
        } else if (id == R.id.enrollment) {
            Intent intent = new Intent(getApplicationContext(), PatientRegistration.class);
            startActivity(intent);
        } else if (id == R.id.editHts) {
            Intent intent2 = new Intent(getApplicationContext(), EditHts.class);
            startActivity(intent2);
        } else if (id == R.id.editPatien) {
            Intent intent2 = new Intent(getApplicationContext(), EditPatient.class);
            startActivity(intent2);
        } else if (id == R.id.editART) {
            Intent intent2 = new Intent(getApplicationContext(), EditArtCommencement.class);
            startActivity(intent2);
        }
        return super.onOptionsItemSelected(item);
    }


//    private void showOptionsMenu(View view) {
//        PopupMenu popup = new PopupMenu(getApplicationContext(), view);
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.menu_dot, popup.getMenu());
//        popup.setOnMenuItemClickListener(new ProfileMenuItemClickListener());
//        popup.show();
//    }
//
//
//    private class ProfileMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
//
//        @Override
//        public boolean onMenuItemClick(MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.indexTesting:
//                    //Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.enrollment:
//                    //Toast.makeText(mContext, "Order Now", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getApplicationContext(), PatientRegistration.class);
//                    startActivity(intent);
//                    return true;
//
//                case R.id.commencement:
//                    //Toast.makeText(mContext, "Order Now", Toast.LENGTH_SHORT).show();
//                    Intent intent2 = new Intent(getApplicationContext(), ArtCommencement.class);
//                    startActivity(intent2);
//                    return true;
//            }
//            return false;
//        }
//    }
}
