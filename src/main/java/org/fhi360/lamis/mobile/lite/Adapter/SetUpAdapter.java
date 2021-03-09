package org.fhi360.lamis.mobile.lite.Adapter;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.shashank.sony.fancytoastlib.FancyToast;
import org.fhi360.lamis.mobile.lite.DAO.GeoLocationDAO;
import org.fhi360.lamis.mobile.lite.Domains.GetNameId;
import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Domains.SetUpModel;
//import org.fhi360.lamis.mobile.lite.Service.LocalSystemIpAddressHandler;
import org.fhi360.lamis.mobile.lite.Utils.GetCurrentLocation;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;

import java.util.ArrayList;
import java.util.List;

public class SetUpAdapter extends RecyclerView.Adapter<SetUpAdapter.MyViewHolder> {
    GetCurrentLocation currentLoc;
    private LocationManager locationManager;
    private Context mContext;
    private Activity activity;
    private List<SetUpModel> setUpModelList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvtitle;
        private ImageView imgMenu;
        private CardView cdview;

        public MyViewHolder(View view) {
            super(view);
            tvtitle = view.findViewById(R.id.tvmenutitle);
            imgMenu = view.findViewById(R.id.imgmenu);
            cdview = view.findViewById(R.id.card_view);
        }
    }

    public SetUpAdapter(Context mContext, Activity activity, List<SetUpModel> setUpModelList) {
        this.mContext = mContext;
        this.activity = activity;
        this.setUpModelList = setUpModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_menu, parent, false);
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        currentLoc = new GetCurrentLocation(activity);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final SetUpModel SetUpModel = setUpModelList.get(position);
        holder.tvtitle.setText(SetUpModel.getTitle());
        Glide.with(mContext)
                .load(SetUpModel.getImg())
                .into(holder.imgMenu);
        if (position == 1) {
            holder.cdview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    geoLocationPopUp();
                }
            });
        } else {
            holder.cdview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    localIpAddressPopUp();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return setUpModelList.size();
    }


    private void geoLocationPopUp() {
        LayoutInflater li = LayoutInflater.from(mContext);
        View promptsView = li.inflate(R.layout.activity_capture_location, null);
        final AlertDialog view = new AlertDialog.Builder(mContext).create();
        view.setView(promptsView);
        final AppCompatSpinner testingSetting = promptsView.findViewById(R.id.testingSetting);
        final EditText name = promptsView.findViewById(R.id.name);
        final AppCompatSpinner grid = promptsView.findViewById(R.id.grid);
        final TextView longitude = promptsView.findViewById(R.id.tvLongitudes);
        final TextView latitude = promptsView.findViewById(R.id.tvLatitudes);
        final Button captureLocation = promptsView.findViewById(R.id.captureLocation);
        final Button save = promptsView.findViewById(R.id.save);
        captureLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentLoc.connectGoogleApi();
                if (new PrefManager(mContext).getCoordnitae().get("longitude") != null && new PrefManager(mContext).getCoordnitae().get("latitude") != "") {
                    String longitude1 = new PrefManager(mContext).getCoordnitae().get("longitude");
                    String latitude1 = new PrefManager(mContext).getCoordnitae().get("latitude");
                    longitude.setText(longitude1);
                    latitude.setText(latitude1);

                }
            }
        });

        List<GetNameId> listName = new GeoLocationDAO(mContext).getGeolocationName();
        final ArrayList geoId = new ArrayList();
        ArrayList name1 = new ArrayList();
        for (GetNameId listNam : listName) {
            geoId.add(listNam.getId());
            name1.add(listNam.getName());
        }

        final ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(mContext,
                R.layout.spinner_items, name1);
        districtAdapter.setDropDownViewResource(R.layout.color_spinner_layout);
        districtAdapter.notifyDataSetChanged();
        grid.setAdapter(districtAdapter);

         grid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                    long id1 = (long) geoId.get(position);
//                    String longitude1 = "";
//                    String latitude1 = "";
//                    List<Geolocation> geolocationss = new ArrayList<>();
//                    geolocationss = new GeoLocationDAO(mContext).getLongitudeAndLatitude(id1);
//                    for (Geolocation lat : geolocationss) {
//                     longitude1 = String.valueOf(lat.getLongitude());
//                     latitude1 = String.valueOf(lat.getLatitude());
//                     }
//                    longitude.setText(longitude1);
//                    latitude.setText(latitude1);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String testingSeting1 = testingSetting.getSelectedItem().toString();
                String name1 = name.getText().toString();
                if (testingSeting1.isEmpty()) {
                    FancyToast.makeText(mContext, "TestSetting can't be empty ", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                } else if (name1.isEmpty()) {
                    name.setError("Enter Name");
                    FancyToast.makeText(mContext, "Enter Name", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                } else {
                    new GeoLocationDAO(mContext).saveGeoLocation(testingSeting1, name1, longitude.getText().toString(), latitude.getText().toString());
                    FancyToast.makeText(mContext, "Geolocation captured successfully ", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    view.dismiss();
                }
            }

        });

        view.setCancelable(true);
        view.show();
    }


    private void localIpAddressPopUp() {
        LayoutInflater li = LayoutInflater.from(mContext);
        View promptsView = li.inflate(R.layout.set_up, null);
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        dialog.setView(promptsView);
        final TextView ipaddress = promptsView.findViewById(R.id.ipaddress);
        Button button_account = promptsView.findViewById(R.id.button_account);
        button_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ipaddress1 = ipaddress.getText().toString();
                if (ipaddress1.isEmpty()) {
                    ipaddress.setError("Enter Ip address");
                    FancyToast.makeText(mContext, "Enter Ip address", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                } else {
                    new PrefManager(mContext).saveIpAddress(ipaddress.getText().toString());
                   // LocalSystemIpAddressHandler localSystemIpAddressHandler = new LocalSystemIpAddressHandler(mContext);
                   // localSystemIpAddressHandler.start();
                    FancyToast.makeText(mContext, "Ip Address saved successfully ", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    dialog.dismiss();
                }
            }

        });
        dialog.setCancelable(true);
        dialog.show();
    }


}