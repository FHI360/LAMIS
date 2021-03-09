package org.fhi360.lamis.mobile.lite.Activities;

import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import org.fhi360.lamis.mobile.lite.DAO.IndexContactDAO;
import org.fhi360.lamis.mobile.lite.Domains.IndexContact;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;

import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Adapter.IndexSexualContactAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by idris on 29/07/15.
 */
public class EditIndexSexualContact extends AppCompatActivity implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private RecyclerView recyclerViewHts;
    private List<IndexContact> indexContacts;
    private IndexSexualContactAdapter indexSexualContactAdapter;
    private PrefManager prefManager;
    private String htsId;
    private HashMap<String, String> user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorPrimaryDark)));
        prefManager = new PrefManager(getApplicationContext());
        recyclerViewHts = findViewById(R.id.my_client);
        user = prefManager.getProfileDetails();
        htsId = user.get("htsId");

        indexContacts = new ArrayList<>();
        indexSexualContactAdapter = new IndexSexualContactAdapter(indexContacts, getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewHts.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerViewHts.setLayoutManager(mLayoutManager);
        recyclerViewHts.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHts.setHasFixedSize(true);
        recyclerViewHts.setAdapter(indexSexualContactAdapter);
        getDataFromSQLite(Long.parseLong(htsId));

    }


    private void getDataFromSQLite(final long htsId1) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                indexContacts.clear();
                indexContacts.addAll(new IndexContactDAO(getApplicationContext()).getIndexContact1(htsId1));
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                indexSexualContactAdapter.notifyDataSetChanged();
            }
        }.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
            return false;
        }

        List<IndexContact> filteredValues = new ArrayList<IndexContact>(indexContacts);
        for (IndexContact value : indexContacts) {
            if (!value.getSurname().toLowerCase().contains(newText.toLowerCase())) {
                filteredValues.remove(value);
            }
        }


        indexSexualContactAdapter = new IndexSexualContactAdapter(filteredValues, getApplicationContext());
        recyclerViewHts.setAdapter(indexSexualContactAdapter);

        return false;
    }

    public void resetSearch() {
        indexSexualContactAdapter = new IndexSexualContactAdapter(indexContacts, getApplicationContext());
        recyclerViewHts.setAdapter(indexSexualContactAdapter);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }


}
