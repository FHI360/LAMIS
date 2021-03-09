package org.fhi360.lamis.mobile.lite.Adapter;


import android.content.Context;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.fhi360.lamis.mobile.lite.DAO.FacilityDAO;
import org.fhi360.lamis.mobile.lite.Domains.Facility;
import org.fhi360.lamis.mobile.lite.Domains.IndexContact;
import org.fhi360.lamis.mobile.lite.R;

import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IndexSexualContactAdapter extends RecyclerView.Adapter<IndexSexualContactAdapter.UserViewHolder> implements Filterable {
    private List<IndexContact> indexContacts;
    private Context context;
    private int count = 0;

    public IndexSexualContactAdapter(List<IndexContact> indexContacts, Context context) {
        this.indexContacts = indexContacts;
        this.context = context;
    }


    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sexual_contacts, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, final int position) {
        String surname = indexContacts.get(position).getSurname();
        String firstLettersurname = String.valueOf(surname.charAt(0));
        String otherName = indexContacts.get(position).getOtherNames();
        String firstLettersotherName = String.valueOf(otherName.charAt(0));
        String fullSurname = firstLettersurname.toUpperCase() + surname.substring(1).toLowerCase();
        String fullOtherName = firstLettersotherName.toUpperCase() + otherName.substring(1).toLowerCase();
        String clientName = "<font color='#000'>" + fullSurname + "</font> &nbsp &nbsp" + "<font color='#000'>" + fullOtherName + "</font>";
        holder.clientName.setText(Html.fromHtml(clientName));
        Facility facility = new FacilityDAO(context).getFacilityById(indexContacts.get(position).getFacilityId());
        holder.facility_name.setText(facility.getName());

        String firstLetter = String.valueOf(indexContacts.get(position).getSurname().charAt(0));
        Random mRandom = new Random();
        int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
        ((GradientDrawable) holder.circleImage.getBackground()).setColor(color);
        holder.circleImage.setText(firstLetter);
    }


    @Override
    public int getItemCount() {

        return indexContacts.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    indexContacts = indexContacts;
                    List<IndexContact> filteredList = new ArrayList<>();

                    for (IndexContact hts1 : indexContacts) {

                        if (hts1.getSurname().toLowerCase().contains(charString) || hts1.getOtherNames().toLowerCase().contains(charString)) {

                            filteredList.add(hts1);
                        }
                        indexContacts = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = indexContacts;
                    return filterResults;
                } else {

                    List<IndexContact> filteredList = new ArrayList<>();

                    for (IndexContact hts1 : indexContacts) {

                        if (hts1.getSurname().toLowerCase().contains(charString) || hts1.getOtherNames().toLowerCase().contains(charString)) {

                            filteredList.add(hts1);
                        }
                    }

                    indexContacts = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = indexContacts;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                indexContacts = (List<IndexContact>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    /**
     * idris
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView clientName, facility_name, circleImage;

        public UserViewHolder(View view) {
            super(view);
            clientName = view.findViewById(R.id.hts_profile);
            facility_name = view.findViewById(R.id.facility_name);
            circleImage = view.findViewById(R.id.circleImage);

        }
    }


}
