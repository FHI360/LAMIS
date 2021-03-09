package org.fhi360.lamis.mobile.lite.Adapter;

import android.content.Context;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.fhi360.lamis.mobile.lite.DAO.ClinicDAO;
import org.fhi360.lamis.mobile.lite.DAO.IndexContactDAO;
import org.fhi360.lamis.mobile.lite.DAO.PatientDAO;
import org.fhi360.lamis.mobile.lite.Domains.Clinic;
import org.fhi360.lamis.mobile.lite.Domains.Patient;
import org.fhi360.lamis.mobile.lite.R;

import org.fhi360.lamis.mobile.lite.Domains.Hts;

import java.util.List;

/**
 * Created by idris on 10/10/2016.
 */

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.UserViewHolder> {

    private List<Hts> listHts;
    private Context context;
    private int count = 0;

    public ReportAdapter(List<Hts> listHts, Context context) {
        this.listHts = listHts;
        this.context = context;
    }


    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, final int position) {

//        holder.txtTitle.setText("Testing & Counseling");
        count = new IndexContactDAO(context).countPages1(listHts.get(position).getHtsId());

        String clientName = "<font color='#000'>" + listHts.get(position).getSurname() + "</font> &nbsp &nbsp" + "<font color='#000'>" + listHts.get(position).getOtherNames() + "</font>";
        String dateOfBirth = "<font color='#000'>" + listHts.get(position).getDateBirth() + "</font> &nbsp &nbsp" + "<font color='#000'>" + "</font>";
        String age = "<font color='#000'>" + listHts.get(position).getAge() + "</font> &nbsp &nbsp" + "<font color='#000'>" + "</font>";
        long patientId = 0;
        if (listHts.get(position).getHivTestResult().equalsIgnoreCase("Positive")) {
            final Patient patient = new PatientDAO(context).findPatientHtsId((listHts.get(position).getHtsId()));
            if (patient != null) {
                Clinic clinic = new ClinicDAO(context).getAllClinicByPatientId(patient.getPatientId());
                if (clinic != null) {
                    holder.onArt.setText("Patient Enrolled and on ART");
                } else {
                    holder.onArt.setText("Patient not ART");
                }
            } else {
                holder.onArt.setText("Patient is positive not Enrolled");
            }

        } else {
            holder.onArt.setVisibility(View.INVISIBLE);
            holder.onArt1.setVisibility(View.INVISIBLE);
        }
        String dateOfVisit = listHts.get(position).getDateVisit();

        String phoneNumber = listHts.get(position).getPhone();

        String address = listHts.get(position).getAddress();

        String state = listHts.get(position).getState();

        String lga = listHts.get(position).getLga();

        String gender = listHts.get(position).getGender();

        String maritalStatus = listHts.get(position).getMaritalStatus();
        if (count == 0) {
            holder.numberOfElicitClient.setVisibility(View.INVISIBLE);
            holder.numberOfElicitClient1.setVisibility(View.INVISIBLE);
        } else {
            holder.numberOfElicitClient.setVisibility(View.VISIBLE);
            holder.numberOfElicitClient1.setVisibility(View.VISIBLE);
        }
        holder.numberOfElicitClient.setText(String.valueOf(count));
        holder.clientName.setText(Html.fromHtml(clientName));
        holder.dateBirth.setText(Html.fromHtml(dateOfBirth));
        holder.age.setText(Html.fromHtml(age));
        holder.dateVisit.setText(Html.fromHtml(dateOfVisit));
//        if (Html.fromHtml(phoneNumber) != null) {
//            holder.phoneNumber.setText(Html.fromHtml(phoneNumber));
//        } else {
//            holder.phoneNumber.setText("");
//
//        }
        holder.address.setText(Html.fromHtml(address));
        holder.state.setText(Html.fromHtml(state));
        holder.lga.setText(Html.fromHtml(lga));
        holder.sex.setText(Html.fromHtml(gender));
        holder.maritalStatus.setText(Html.fromHtml(maritalStatus));
        holder.hivStatus.setText(listHts.get(position).getHivTestResult());


    }


    @Override
    public int getItemCount() {
        Log.v(HtsPatientsRecyclerAdapter.class.getSimpleName(), "" + listHts.size());
        return listHts.size();
    }


    /**
     * idris
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView onArt1, numberOfElicitClient1, clientName, numberOfElicitClient, onArt, dateBirth, age, dateVisit, phoneNumber, address, sex, txtTitle, state, lga, maritalStatus, hivStatus;

        public UserViewHolder(View view) {
            super(view);
            clientName = view.findViewById(R.id.clientName);
            dateBirth = view.findViewById(R.id.dateBirth);
            age = view.findViewById(R.id.age);
            dateVisit = view.findViewById(R.id.dateVisit);
            phoneNumber = view.findViewById(R.id.phoneNumber);
            address = view.findViewById(R.id.address);
            sex = view.findViewById(R.id.sex);
            address = view.findViewById(R.id.address);
            state = view.findViewById(R.id.state);
            lga = view.findViewById(R.id.lga);
            maritalStatus = view.findViewById(R.id.maritalStatus);
            hivStatus = view.findViewById(R.id.hivStatus);
            onArt = (view).findViewById(R.id.onArt);
            numberOfElicitClient = (view).findViewById(R.id.numberOfElicitClient);
            onArt1 = (view).findViewById(R.id.onArt1);
            numberOfElicitClient1 = (view).findViewById(R.id.numberOfElicitClient1);

        }
    }


}
