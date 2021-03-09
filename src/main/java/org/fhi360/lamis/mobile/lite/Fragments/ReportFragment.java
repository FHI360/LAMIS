package org.fhi360.lamis.mobile.lite.Fragments;

import android.content.Context;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.SearchView;


import org.fhi360.lamis.mobile.lite.DAO.HtsDAO;
import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
import org.fhi360.lamis.mobile.lite.Domains.Hts;
import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Adapter.ReportAdapter;

import java.util.ArrayList;
import java.util.List;

public class ReportFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    private RecyclerView recyclerViewHts;
    private List<Hts> listUsers;
    private ReportAdapter reportAdapter;
    private LAMISLiteDb databaseHelper;
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
        databaseHelper = new LAMISLiteDb(mContext);
        listUsers = new ArrayList<>();
        listUsers.clear();
        listUsers.addAll(new HtsDAO(getContext()).syncData());
        reportAdapter = new ReportAdapter(listUsers,mContext);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerViewHts.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        recyclerViewHts.setLayoutManager(mLayoutManager);
        recyclerViewHts.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHts.setHasFixedSize(true);
        recyclerViewHts.setAdapter(reportAdapter);
        reportAdapter.notifyDataSetChanged();
        return rootView;
    }
//    private void initViews(View rootView) {
//        recyclerViewHts = rootView.findViewById(R.id.my_client);
//    }

//    private void initObjects() {
//        listUsers = new ArrayList<>();
//        reportAdapter = new ReportAdapter(listUsers,mContext);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
//        recyclerViewHts.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
//        recyclerViewHts.setLayoutManager(mLayoutManager);
//        recyclerViewHts.setItemAnimator(new DefaultItemAnimator());
//        recyclerViewHts.setHasFixedSize(true);
//        recyclerViewHts.setAdapter(reportAdapter);
//        databaseHelper = new LAMISLiteDb(mContext);
//        getDataFromSQLite();
//    }

//    private void getDataFromSQLite() {
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... params) {
//               listUsers.clear();
//                listUsers.addAll(databaseHelper.syncData());
//
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//                reportAdapter.notifyDataSetChanged();
//            }
//        }.execute();
//    }

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

        reportAdapter = new ReportAdapter(filteredValues,mContext);
        recyclerViewHts.setAdapter(reportAdapter);///setListAdapter(mAdapter);

        return false;
    }
    public void resetSearch() {
        reportAdapter = new ReportAdapter(listUsers,mContext);
        recyclerViewHts.setAdapter(reportAdapter);
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
