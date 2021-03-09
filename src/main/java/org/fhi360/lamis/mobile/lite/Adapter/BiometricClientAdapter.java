package org.fhi360.lamis.mobile.lite.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.fhi360.lamis.mobile.lite.DAO.BiometricDAO;
import org.fhi360.lamis.mobile.lite.Domains.Patient;
import org.fhi360.lamis.mobile.lite.MainActivity2;
import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class BiometricClientAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Patient> patients;
    private Context context;
    private PrefManager prefManager;
    private String clientName;
    // private final int MODALITY_CODE_FINGER = 2;

    public BiometricClientAdapter(List<Patient> patients, Context context) {
        this.patients = patients;
        this.context = context;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items, parent, false);
        prefManager = new PrefManager(context);
        return new PatienViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof BiometricClientAdapter.PatienViewHolder) {
            ((BiometricClientAdapter.PatienViewHolder) holder).populatePatient(patients.get(position));
        }

    }


    @Override
    public int getItemCount() {
        return patients.size();
    }

    @Override
    public int getItemViewType(int position) {
        return patients.size();
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

                if (patient.getBiometric() == 0) {
                    TextDrawable drawable = TextDrawable.builder()
                            .buildRound("+", Color.RED);
                    starIcon.setImageDrawable(drawable);
                } else {
                    TextDrawable drawable = TextDrawable.builder()
                            .buildRound("\u221A", Color.GREEN);
                    starIcon.setImageDrawable(drawable);
                }


                textViewClientName.setText(Html.fromHtml(clientName), TextView.BufferType.SPANNABLE);
                HashMap<String, String> user = prefManager.getHtsDetails();
                String facilityName = user.get("faciltyName");
                String lgaId = user.get("lgaId");
                String steteId = user.get("stateId");
                String facilityId = user.get("facilityId");
                facilitiesName.setText(facilityName);
                // System.out.println("facilityName " + facilityName);
                dateText.setText(patient.getDateRegistration());
                String firstLetter = String.valueOf(patient.getSurname().charAt(0));
                Random mRandom = new Random();
                int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
                ((GradientDrawable) circleImage.getBackground()).setColor(color);
                circleImage.setText(firstLetter);

                relativeLayout.setOnClickListener(view -> {
                    new PrefManager(context).saveUuId(patient.getUuid());

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
                   // System.out.println("PatientId " + patient.getPatientId());
                    editor.putString("patientId", patient.getPatientId() + "");
                    editor.putString("hospitalNumber", patient.getHospitalNum());
                    editor.putString("id", patient.getPatientId() + "");
                    editor.putString("uniqueId", patient.getUniqueId());
                    editor.putString("surname", patient.getSurname());
                    editor.putString("othername", patient.getOtherNames());
                    editor.apply();
                    Long patientId = patient.getPatientId();
                    int count1 = new BiometricDAO(context).count(patientId);
                    if (count1 != 6) {
                        Intent fingerActivity = new Intent(context, MainActivity2.class);
                        context.startActivity(fingerActivity);
                    } else {
                        FancyToast.makeText(context, "Enrollment Already Exist", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    }


                });

            } catch (Exception ignored) {

            }

        }


    }


}
