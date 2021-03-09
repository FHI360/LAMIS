package org.fhi360.lamis.mobile.lite.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;


import com.mlsdev.animatedrv.AnimatedRecyclerView;

import org.apache.commons.lang3.StringUtils;
import org.fhi360.lamis.mobile.lite.Adapter.PatientRecyclerAdapter;
import org.fhi360.lamis.mobile.lite.DAO.PatientDAO;
import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
import org.fhi360.lamis.mobile.lite.Domains.Patient;
import org.fhi360.lamis.mobile.lite.R;

import java.util.ArrayList;
import java.util.List;

import static com.neurotec.lang.NCore.getContext;

public class PatientList extends Fragment {
    private RecyclerView recyclerViewHts;
    private List<Patient> listPatients;
    private PatientRecyclerAdapter patientRecyclerAdapter;
    private AutoCompleteTextView mSearchField;
    private ImageView mSearchBtn;
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
        View rootView = inflater.inflate(R.layout.fragment_patient_recycler, container, false);
        mSearchField = (AutoCompleteTextView) rootView.findViewById(R.id.search_field);
        mSearchBtn = (ImageView) rootView.findViewById(R.id.search_btn);
        recyclerViewHts = (AnimatedRecyclerView) rootView.findViewById(R.id.patient_recycler);

        listPatients = new ArrayList<>();
        listPatients.clear();
        listPatients = new PatientDAO(mContext).getAllPatient5();
        patientRecyclerAdapter = new PatientRecyclerAdapter(listPatients, mContext);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerViewHts.setLayoutManager(mLayoutManager);
        recyclerViewHts.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHts.setHasFixedSize(true);
        recyclerViewHts.setAdapter(patientRecyclerAdapter);
        patientRecyclerAdapter.notifyDataSetChanged();
        recyclerViewHts.scheduleLayoutAnimation();


        ArrayList names = new ArrayList();
        for (Patient patient : listPatients) {
            names.add(patient.getSurname());
        }

        final ArrayAdapter districtAdapter = new ArrayAdapter<>(mContext,
                R.layout.spinner_items, names);
        mSearchField.setThreshold(2);
        mSearchField.setAdapter(districtAdapter);

        mSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    search(mSearchField.getText().toString());
                } else {
                    search(mSearchField.getText().toString());
                }

            }
        });
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = mSearchField.getText().toString();

                search(searchText);

            }
        });
        return rootView;
    }

    private void search(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            resetSearch();
            return;
        }

        List<Patient> filteredValues = new ArrayList<>(listPatients);
        for (Patient value : listPatients) {
            if (!StringUtils.containsIgnoreCase(value.getSurname(), searchText) &&
                    !StringUtils.containsIgnoreCase(value.getHospitalNum(), searchText)
                    && !StringUtils.containsIgnoreCase(value.getOtherNames(), searchText)) {
                filteredValues.remove(value);
            }
        }


        patientRecyclerAdapter = new PatientRecyclerAdapter(filteredValues, mContext);
        recyclerViewHts.setAdapter(patientRecyclerAdapter);
    }

    public void resetSearch() {
        patientRecyclerAdapter = new PatientRecyclerAdapter(listPatients, mContext);
        recyclerViewHts.setAdapter(patientRecyclerAdapter);

    }


}