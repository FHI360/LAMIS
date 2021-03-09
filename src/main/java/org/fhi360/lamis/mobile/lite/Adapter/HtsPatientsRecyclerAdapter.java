package org.fhi360.lamis.mobile.lite.Adapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.fhi360.lamis.mobile.lite.Activities.ProfileActivity;
import org.fhi360.lamis.mobile.lite.Activities.ProfileActivity2;
import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
import org.fhi360.lamis.mobile.lite.Domains.Patient;
import org.fhi360.lamis.mobile.lite.R;

import com.amulyakhare.textdrawable.TextDrawable;
import org.fhi360.lamis.mobile.lite.Domains.Hts;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by idris on 10/10/2016.
 */
public class HtsPatientsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final int VIEW_TYPE_HTS = 0;
    final int VIEW_TYPE_PATIENT = 1;
    private List<Hts> listHts = new ArrayList<>();
    private List<Patient> patients = new ArrayList<>();
    private Context context;
    private PrefManager prefManager;
    private String clientName;

    public HtsPatientsRecyclerAdapter(List<Hts> listHts, List<Patient> patients, Context context) {
        this.listHts = listHts;
        this.patients = patients;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HTS) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_items, parent, false);
            prefManager = new PrefManager(context);
            return new HtsViewHolder(itemView);

        }

        if (viewType == VIEW_TYPE_PATIENT) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_items, parent, false);
            prefManager = new PrefManager(context);
            return new PatienViewHolder(itemView);
        }

        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HtsViewHolder) {
            ((HtsViewHolder) holder).populateHts(listHts.get(position));
        }
        if (holder instanceof PatienViewHolder) {
            ((PatienViewHolder) holder).populatePatient(patients.get(position - listHts.size()));
        }


    }


    @Override
    public int getItemCount() {
        return listHts.size() + patients.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < listHts.size()) {
            return VIEW_TYPE_HTS;
        }
        if (position - listHts.size() < patients.size()) {
            return VIEW_TYPE_PATIENT;
        }

        return -1;
    }

    public class PatienViewHolder extends RecyclerView.ViewHolder {
        TextView facilitiesName;
        TextView textViewClientName, dateText;
        LinearLayout relativeLayout;
        TextView circleImage;
        ImageView starIcon;

        public PatienViewHolder(View view) {
            super(view);
            textViewClientName = view.findViewById(R.id.hts_profile);
            facilitiesName = view.findViewById(R.id.facility_name);
            relativeLayout = view.findViewById(R.id.relarive_container);
            circleImage = itemView.findViewById(R.id.circleImage);
            dateText = view.findViewById(R.id.dateText);
            starIcon = view.findViewById(R.id.starIcon);

        }

        public void populatePatient(final Patient patient) {
            try {
                String surname = patient.getSurname();
                String firstLettersurname = String.valueOf(patient.getSurname().charAt(0));
                String otherName = patient.getOtherNames();
                String firstLettersotherName = String.valueOf(patient.getOtherNames().charAt(0));
                String fullSurname = firstLettersurname.toUpperCase() + surname.substring(1).toLowerCase();
                String fullOtherName = firstLettersotherName.toUpperCase() + otherName.substring(1).toLowerCase();
                clientName = "<font color='#000'>" + fullSurname + "</font> &nbsp &nbsp" + "<font color='#000'>" + fullOtherName + "</font>";
                TextDrawable drawable = TextDrawable.builder()
                        .buildRound("+", Color.RED);
                starIcon.setImageDrawable(drawable);

                textViewClientName.setText(Html.fromHtml(clientName), TextView.BufferType.SPANNABLE);
                HashMap<String, String> user = prefManager.getHtsDetails();
                String facilityName = user.get("faciltyName");
                String lgaId = user.get("lgaId");
                String steteId = user.get("stateId");
                String facilityId = user.get("facilityId");
                facilitiesName.setText(facilityName);
                dateText.setText(patient.getDateRegistration());
                String firstLetter = String.valueOf(patient.getSurname().charAt(0));
                Random mRandom = new Random();
                int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
                ((GradientDrawable) circleImage.getBackground()).setColor(color);
                circleImage.setText(firstLetter);


                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ProfileActivity2.class);
                        SharedPreferences sharedPreferences = context.getSharedPreferences("patientIdDb", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", patient.getSurname() + " " + patient.getOtherNames());
                        //editor.putString("age", patient.getAge() + "");
                        editor.putString("surname", patient.getSurname() + "");
                        editor.putString("othernames", patient.getOtherNames() + "");
                        editor.putString("address", patient.getAddress());
                        editor.putString("phone", patient.getPhone());
                        editor.putString("gender", patient.getGender());
                        editor.putString("dateBirth", patient.getDateBirth());
                        editor.putString("state", patient.getState());
                        editor.putString("lga", patient.getLga());
                        editor.putString("maritalStatus", patient.getMaritalStatus());
                        editor.putString("hivStatus", "Positive");
                        editor.putString("patientId", patient.getPatientId() + "");
                        editor.putString("hospitalNumber", patient.getHospitalNum());
                        editor.putString("uniqueId", patient.getUniqueId());
                        editor.putString("surname", patient.getSurname());
                        editor.putString("othername", patient.getOtherNames());
                        editor.apply();
                        context.startActivity(intent);

                    }
                });

            } catch (Exception ignored) {

            }

        }


    }


    public class HtsViewHolder extends RecyclerView.ViewHolder {

        TextView facilitiesName;
        TextView textViewClientName, dateText;
        LinearLayout relativeLayout;
        TextView circleImage;
        ImageView starIcon;

        public HtsViewHolder(View view) {
            super(view);
            textViewClientName = view.findViewById(R.id.hts_profile);
            facilitiesName = view.findViewById(R.id.facility_name);
            relativeLayout = view.findViewById(R.id.relarive_container);
            circleImage = itemView.findViewById(R.id.circleImage);
            dateText = view.findViewById(R.id.dateText);
            starIcon = view.findViewById(R.id.starIcon);

        }

        public void populateHts(final Hts listHts) {
            try {
                String surname = listHts.getSurname();
                String firstLettersurname = String.valueOf(surname.charAt(0));
                String otherName = listHts.getOtherNames();
                String firstLettersotherName = String.valueOf(listHts.getOtherNames().charAt(0));
                String fullSurname = firstLettersurname.toUpperCase() + surname.substring(1).toLowerCase();

                String fullOtherName = firstLettersotherName.toUpperCase() + otherName.substring(1).toLowerCase();
                clientName = "<font color='#000'>" + fullSurname + "</font> &nbsp &nbsp" + "<font color='#000'>" + fullOtherName + "</font>";
                String hivStatus = listHts.getHivTestResult();
                if (hivStatus.equalsIgnoreCase("Positive")) {
                    TextDrawable drawable = TextDrawable.builder()
                            .buildRound("+", Color.RED);
                    starIcon.setImageDrawable(drawable);
                } else {
                    TextDrawable drawable = TextDrawable.builder()
                            .buildRound("-", Color.GREEN);
                    starIcon.setImageDrawable(drawable);
                }
                textViewClientName.setText(Html.fromHtml(clientName), TextView.BufferType.SPANNABLE);
                facilitiesName.setText(listHts.getFacilityName());
                dateText.setText(listHts.getDateVisit());
                final String state = listHts.getState();
                String firstLetter = String.valueOf(listHts.getSurname().charAt(0));
                Random mRandom = new Random();
                int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
                ((GradientDrawable) circleImage.getBackground()).setColor(color);
                circleImage.setText(firstLetter);

                relativeLayout.setOnClickListener(view -> {
                    Intent intent = new Intent(context, ProfileActivity.class);
                    SharedPreferences sharedPreferences = context.getSharedPreferences("profielDetails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", listHts.getSurname() + " " + listHts.getOtherNames());
                    editor.putString("age", listHts.getAge() + "");
                    editor.putString("clientcode", listHts.getClientCode());
                    editor.putString("surname", listHts.getSurname() + "");
                    editor.putString("othernames", listHts.getOtherNames() + "");
                    editor.putString("address", listHts.getAddress());
                    editor.putString("phone", listHts.getPhone());
                    editor.putString("gender", listHts.getGender());
                    editor.putString("dateVisit", listHts.getDateVisit());
                    editor.putString("dateBirth", listHts.getDateBirth());
                    editor.putString("state", state);
                    editor.putString("htsId", listHts.getHtsId() + "");
                    editor.putString("lga", listHts.getLga());
                    editor.putString("clientcode", listHts.getClientCode());
                    editor.putString("maritalStatus", listHts.getMaritalStatus());
                    editor.putString("ageUnit", listHts.getAgeUnit());
                    editor.putString("hivStatus", listHts.getHivTestResult());
                    editor.putString("indextype", listHts.getTypeIndex());
                    editor.putString("huspitalNums", listHts.getHospitalNum());
                    editor.putString("indexclientcode", listHts.getIndexClientCode());
                    editor.putString("notificationcounseling", listHts.getNotificationCounseling());
                    editor.putString("agreeNotfication", listHts.getPartnerNotification());
                    editor.putString("numpartner", listHts.getNumberPartner() + "");
                    editor.apply();
                    prefManager.saveDetails("", listHts.getFacilityName(), "", listHts.getLgaId(), listHts.getStateId(), listHts.getFacilityId());
                    context.startActivity(intent);

                });

            } catch (Exception ignored) {

            }
        }
    }
}
