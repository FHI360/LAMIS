package org.fhi360.lamis.mobile.lite.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.fhi360.lamis.mobile.lite.Fragments.DashBoard;
import org.fhi360.lamis.mobile.lite.Fragments.FragmentDrawer;
import org.fhi360.lamis.mobile.lite.Fragments.ListServerClients;
import org.fhi360.lamis.mobile.lite.Fragments.PatientRegistrationFragment;
import org.fhi360.lamis.mobile.lite.Fragments.PatientListFragments;
import org.fhi360.lamis.mobile.lite.Fragments.ReportFragment;
import org.fhi360.lamis.mobile.lite.Fragments.RiskAssessmentFragment;
import org.fhi360.lamis.mobile.lite.Fragments.SetUp;
//import org.fhi360.lamis.mobile.lite.Service.DefaulterServicesHandler;
//import org.fhi360.lamis.mobile.lite.Service.LocalSystemIpAddressHandler;
//import org.fhi360.lamis.mobile.lite.Service.ServiceScheduler;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;
import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Fragments.SyncFragment;

public class Home extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private PrefManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mains_fragment);
        session = new PrefManager(this);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        displayView(0);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logOut) {
            session.logOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new PatientListFragments();
                title = getString(R.string.nav_item_list);
                break;
            case 1:
                fragment = new RiskAssessmentFragment();
                title = getString(R.string.nav_item_hts);
                break;
            case 2:
                fragment = new ListServerClients();
                title = getString(R.string.nav_item_client);
                break;
            case 3:
                title = getString(R.string.nav_item_patient);
                fragment = new PatientRegistrationFragment();
                break;
            case 4:
                title = getString(R.string.nav_item_sync);
                fragment = new SyncFragment();
                break;
            case 5:
                fragment = new ReportFragment();
                title = getString(R.string.nav_item_report);
                break;
            case 6:
                title = getString(R.string.nav_item_dashboard);
                fragment = new DashBoard();
                break;
            case 7:
                title = getString(R.string.set_up);
                Intent intent = new Intent(getApplicationContext(), SetUp.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }


}