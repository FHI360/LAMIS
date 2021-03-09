package org.fhi360.lamis.mobile.lite.Activities;

import android.app.ProgressDialog;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.fhi360.lamis.mobile.lite.DAO.DefaultersDAO;
import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
import org.fhi360.lamis.mobile.lite.Domains.Defaulter;
import org.fhi360.lamis.mobile.lite.Domains.ListDefaulters;
import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Service.APIService;
import org.fhi360.lamis.mobile.lite.Service.ClientAPI;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientTracking2 extends AppCompatActivity {
    private LAMISLiteDb lamisLiteDb;
    private PrefManager prefManager;
    private ProgressDialog progressdialog;

    private String deviceconfigId;
    private RecyclerView recyclerView;
    private TableViewAdapter adapter;
    private List<Defaulter> defaulterList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_tracking);
        lamisLiteDb = new LAMISLiteDb(ClientTracking2.this);
        prefManager = new PrefManager(ClientTracking2.this);
        defaulterList = new ArrayList<>();
        defaulterList.clear();
        recyclerView = findViewById(R.id.recyclerViewDeliveryProductList);
        defaulterList.addAll(new DefaultersDAO(this).getDefaulters());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ClientTracking2.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TableViewAdapter(ClientTracking2.this, defaulterList);
        recyclerView.setAdapter(adapter);
    }

    public void getDefualters() {
        progressdialog = new ProgressDialog(ClientTracking2.this);
        progressdialog.setMessage("LAMISLite Updating please wait...");
        progressdialog.setCancelable(false);
        progressdialog.setIndeterminate(false);
        progressdialog.setMax(100);
        progressdialog.show();

        APIService APIService = new APIService();
        ClientAPI clientAPI = org.fhi360.lamis.mobile.lite.Service.APIService.createService(ClientAPI.class);
        HashMap<String, String> user1 = prefManager.getHtsDetails();
        String facilityName = user1.get("faciltyName");
        String facilityId = user1.get("facilityId");

        Call<ListDefaulters> objectCall = clientAPI.clientTracking(Long.parseLong(facilityId));
        objectCall.enqueue(new Callback<ListDefaulters>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ListDefaulters> call, Response<ListDefaulters> response) {
                if (response.code() == 200) {
                    ListDefaulters dataObject = response.body();
                    List<Defaulter> defaulters = dataObject.getDefaulter();
                    for (Defaulter defaulter : defaulters) {
                        new DefaultersDAO(getApplicationContext()).saveDefaulters(defaulter);
                    }

                    adapter = new TableViewAdapter(ClientTracking2.this, defaulters);
                    recyclerView.setAdapter(adapter);

                }
                progressdialog.hide();
            }

            @Override
            public void onFailure(Call<ListDefaulters> call, Throwable t) {
                t.printStackTrace();
                progressdialog.hide();
            }


        });

    }


}
