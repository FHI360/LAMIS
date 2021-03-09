package org.fhi360.lamis.mobile.lite.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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


public class PatientRecyclerAdapter extends RecyclerView.Adapter<PatientRecyclerAdapter.ViewHolder> {
    //These variables will hold the data for the views
    private List<Patient> patientList;
    private Context context;
    TextView circleImage;
    ImageView starIcon;

    //Provide a reference to views used in the recycler view. Each ViewHolder will display a CardView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    //Pass data to the adapter in its constructor
    public PatientRecyclerAdapter(List<Patient> patientList, Context context) {
        this.patientList = patientList;
        this.context = context;

    }

    //Create a new view and specify what layout to use for the contents of the ViewHolder
    // @NonNull
    @NonNull
    @Override
    public PatientRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_card_view, parent, false);
        return new ViewHolder(cardView);
    }

    //Set the values inside the given view.
    //This method get called whenever the recyler view needs to display data the in a view holder
    //It takes two parameters: the view holder that data needs to be bound to and the position in the data set of the data that needs to be bound.
    //Declare variable position as final because we are referencing it in the inner class View.OnClickListener
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        CardView cardView = holder.cardView;
        String firstLettersurname = String.valueOf(patientList.get(position).getSurname().charAt(0));
        Random mRandom = new Random();
        TextView circleImages = cardView.findViewById(R.id.circleImage);
        int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
        ((GradientDrawable) circleImages.getBackground()).setColor(color);
        circleImages.setText(firstLettersurname);

        TextView profileView = cardView.findViewById(R.id.patient_profile);

        String surname = patientList.get(position).getSurname();
        String otherNames = patientList.get(position).getOtherNames();

        String firstLettersOtherName = String.valueOf(patientList.get(position).getOtherNames().charAt(0));

        String fullSurname = firstLettersurname.toUpperCase() + surname.substring(1).toLowerCase();

        String fullOtherName = firstLettersOtherName.toUpperCase() + otherNames.substring(1).toLowerCase();

        String clientName = "<font color='#000'>" + fullSurname + "</font> &nbsp &nbsp" + "<font color='#000'>" + fullOtherName + "</font>";

        profileView.setText(Html.fromHtml(clientName), TextView.BufferType.SPANNABLE);

        TextView facilityView = cardView.findViewById(R.id.facility_name);
        TextView dateRegistration = cardView.findViewById(R.id.dateText);
        HashMap<String, String> user = new PrefManager(context).getHtsDetails();
        String facilityName = user.get("faciltyName");

        Long patientId = patientList.get(position).getPatientId();
        int count1 = new BiometricDAO(context).count(patientId);
        dateRegistration.setText(String.valueOf(count1));
        starIcon = cardView.findViewById(R.id.starIcon);

        if (patientList.get(position).getBiometric() == 0) {
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound("+", Color.RED);
            starIcon.setImageDrawable(drawable);
        }
        if (patientList.get(position).getBiometric() == 1) {
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound("\u221A", Color.BLUE);
            starIcon.setImageDrawable(drawable);
        }

        facilityView.setText(facilityName);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PrefManager(context).saveUuId(patientList.get(position).getUuid());
                SharedPreferences sharedPreferences = context.getSharedPreferences("patientIdDb", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", patientList.get(position).getSurname() + " " + patientList.get(position).getOtherNames());
                //editor.putString("age", patient.getAge() + "");
                editor.putString("surname", patientList.get(position).getSurname() + "");
                editor.putString("othernames", patientList.get(position).getOtherNames() + "");
                editor.putString("address", patientList.get(position).getAddress());
                editor.putString("phone", patientList.get(position).getPhone());
                editor.putString("gender", patientList.get(position).getGender());
                editor.putString("dateBirth", patientList.get(position).getDateBirth());
                editor.putString("state", patientList.get(position).getState());
                editor.putString("lga", patientList.get(position).getLga());
                editor.putString("maritalStatus", patientList.get(position).getMaritalStatus());
                editor.putString("hivStatus", "Positive");
                System.out.println("PatientId " + patientList.get(position).getPatientId());
                editor.putString("patientId", patientList.get(position).getPatientId() + "");
                editor.putString("hospitalNumber", patientList.get(position).getHospitalNum());
                editor.putString("id", patientList.get(position).getPatientId() + "");
                editor.putString("uniqueId", patientList.get(position).getUniqueId());
                editor.putString("surname", patientList.get(position).getSurname());
                editor.putString("othername", patientList.get(position).getOtherNames());
                editor.apply();

                Long patientId = patientList.get(position).getPatientId();
                int count1 = new BiometricDAO(context).count(patientId);
                //System.out.println("COUNT1 " + count1);
                if (count1 != 6) {
                    Intent fingerActivity = new Intent(context, MainActivity2.class);
                    context.startActivity(fingerActivity);
                } else {
                    FancyToast.makeText(context, "Enrollment Already Exist", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return patientList.size();
    }

}