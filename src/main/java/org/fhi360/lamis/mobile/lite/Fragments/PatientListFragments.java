package org.fhi360.lamis.mobile.lite.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.gson.Gson;

import org.fhi360.lamis.mobile.lite.DAO.HtsDAO;
import org.fhi360.lamis.mobile.lite.DAO.PatientDAO;
import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
import org.fhi360.lamis.mobile.lite.Domains.Hts;
import org.fhi360.lamis.mobile.lite.Domains.Patient;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;
import org.fhi360.lamis.mobile.lite.Adapter.HtsPatientsRecyclerAdapter;
import org.fhi360.lamis.mobile.lite.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by idris on 29/07/15.
 */
public class PatientListFragments extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    private RecyclerView recyclerViewHts;
    private List<Hts> listUsers;
    private List<Patient> listPatients;
    private HtsPatientsRecyclerAdapter htsRecyclerAdapter;
    private PrefManager prefManager;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view, container, false);
        recyclerViewHts = rootView.findViewById(R.id.my_client);
        prefManager = new PrefManager(getContext());
        listUsers = new ArrayList<>();
        listPatients = new ArrayList<>();
        listUsers.clear();
        listUsers.addAll(new HtsDAO(mContext).syncData());
        listPatients.clear();
        // listPatients.addAll();
        htsRecyclerAdapter = new HtsPatientsRecyclerAdapter(listUsers, listPatients, mContext);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerViewHts.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        recyclerViewHts.setLayoutManager(mLayoutManager);
        recyclerViewHts.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHts.setHasFixedSize(true);
        recyclerViewHts.setAdapter(htsRecyclerAdapter);
        htsRecyclerAdapter.notifyDataSetChanged();
        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
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

        List<Hts> filteredValues = new ArrayList<Hts>(listUsers);
        for (Hts value : listUsers) {
            if (!value.getSurname().toLowerCase().contains(newText.toLowerCase())) {
                filteredValues.remove(value);
            }
        }

        List<Patient> filteredValues1 = new ArrayList<Patient>(listPatients);
        for (Patient value : listPatients) {
            if (!value.getSurname().toLowerCase().contains(newText.toLowerCase())) {
                filteredValues1.remove(value);
            }
        }

        htsRecyclerAdapter = new HtsPatientsRecyclerAdapter(filteredValues, filteredValues1, mContext);
        recyclerViewHts.setAdapter(htsRecyclerAdapter);

        return false;
    }

    public void resetSearch() {
        htsRecyclerAdapter = new HtsPatientsRecyclerAdapter(listUsers, listPatients, mContext);
        recyclerViewHts.setAdapter(htsRecyclerAdapter);
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
