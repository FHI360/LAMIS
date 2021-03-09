package org.fhi360.lamis.mobile.lite.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;

import org.fhi360.lamis.mobile.lite.Domains.Defaulter;
import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;
import org.fhi360.lamis.mobile.lite.Utils.Scrambler;

import java.util.List;

public class TableViewAdapter extends RecyclerView.Adapter {
    private List<Defaulter> defualterList;
    private Context context;
    private PrefManager prefManager;

    public TableViewAdapter(Context context, List<Defaulter> defualterList) {
        this.context = context;
        this.defualterList = defualterList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_list_item, parent, false);
        prefManager = new PrefManager(context);

        return new RowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;
        int rowPos = rowViewHolder.getAdapterPosition();
        if (rowPos == 0) {
            // Header Cells. Main Headings appear here
            rowViewHolder.name.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.hospitalNum.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.address.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.phone.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.lastVisit.setBackgroundResource(R.drawable.table_header_cell_bg);


            rowViewHolder.name.setText(Html.fromHtml("<font color=\"#ffffff\">" + "Name" + "</font>"));
            rowViewHolder.hospitalNum.setText(Html.fromHtml("<font color=\"#ffffff\">" + "Hospital No" + "</font>"));
            rowViewHolder.address.setText(Html.fromHtml("<font color=\"#ffffff\">" + "Address" + "</font>"));
            rowViewHolder.phone.setText(Html.fromHtml("<font color=\"#ffffff\">" + "Phone" + "</font>"));
            rowViewHolder.lastVisit.setText(Html.fromHtml("<font color=\"#ffffff\">" + "Last Visit" + "</font>"));
        } else {
            final Defaulter modal = defualterList.get(rowPos - 1);
            rowViewHolder.name.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.hospitalNum.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.address.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.phone.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.lastVisit.setBackgroundResource(R.drawable.table_content_cell_bg);

            Scrambler scrambler = new Scrambler();
            rowViewHolder.name.setText(scrambler.unscrambleCharacters(modal.getSurname()) + " " + scrambler.unscrambleCharacters(modal.getOther_names()));
            rowViewHolder.hospitalNum.setText(modal.getHospital_num() + "");
            rowViewHolder.address.setText(scrambler.unscrambleCharacters(modal.getAddress()) + "");
            rowViewHolder.phone.setText(scrambler.unscrambleNumbers(modal.getPhone()));
            rowViewHolder.lastVisit.setText((modal.getDate_next_refill()));
            rowViewHolder.relarive_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, StatusHistoryActivity.class);
                    prefManager.createClientTracking(modal.getSurname() + "  " + modal.getOther_names(), modal.getHospital_num(), modal.getDate_current_status(), modal.getCurrent_status(), String.valueOf(modal.getPatient_id()));
                    context.startActivity(intent);
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return defualterList.size() + 1;
    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        protected TextView hospitalNum;
        protected TextView name;
        protected TextView phone;
        protected TextView lastVisit;
        protected TextView address;
        protected LinearLayout relarive_container;

        public RowViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            hospitalNum = itemView.findViewById(R.id.hospitalNum);
            address = itemView.findViewById(R.id.address);
            phone = itemView.findViewById(R.id.phone);
            relarive_container = itemView.findViewById(R.id.relarive_container);
            lastVisit = itemView.findViewById(R.id.lastVisit);
        }
    }
}
