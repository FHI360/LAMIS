package org.fhi360.lamis.mobile.lite.PopUp;


public class GeoLocationPopUp {
//    private Context context;
//    private LayoutInflater inflater;
//    private Dialog dialog = null;
//    GetCurrentLocation currentLoc;
//    private LocationManager locationManager;
//    Activity activity;
//
//    public GeoLocationPopUp(Context context, Activity activity) {
//        this.context = context;
//        this.activity = activity;
//        this.inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        dialog = new Dialog(context,
//                android.R.style.Theme_Translucent_NoTitleBar);
//        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//        currentLoc = new GetCurrentLocation(activity);
//        //dialog.getWindow().getAttributes().windowAnimations = R.style.AnimationBottomToUp;
//        dialog.setCancelable(true);
//        //initializeDialoge();
//    }
//
//    public void showPopUp() {
//        if (dialog != null) {
//            loadPopup();
//        } else {
//
//        }
//    }
//
//    public void disMissPopup() {
//        if (dialog != null) {
//            if (dialog.isShowing()) {
//                dialog.dismiss();
//            }
//        }
//    }
//
//    private void loadPopup() {
//        try {
//            View promptsView = inflater.inflate(R.layout.activity_capture_location, null,
//                    false);
//            final AppCompatSpinner testingSetting = promptsView.findViewById(R.id.testingSetting);
//            final EditText name = promptsView.findViewById(R.id.name);
//            final AppCompatSpinner grid = promptsView.findViewById(R.id.grid);
//            final TextView longitude = promptsView.findViewById(R.id.tvLongitudes);
//            final TextView latitude = promptsView.findViewById(R.id.tvLatitudes);
//            final Button captureLocation = promptsView.findViewById(R.id.captureLocation);
//            final Button save = promptsView.findViewById(R.id.save);
//            captureLocation.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    currentLoc.connectGoogleApi();
//                    if (new PrefManager(context).getCoordnitae().get("longitude") != null && new PrefManager(context).getCoordnitae().get("latitude") != "") {
//                        String longitude1 = new PrefManager(context).getCoordnitae().get("longitude");
//                        String latitude1 = new PrefManager(context).getCoordnitae().get("latitude");
//                        longitude.setText(longitude1);
//                        latitude.setText(latitude1);
//
//                    }
//                }
//            });
//
//            List<GetNameId> listName = new GeoLocationDAO(context).getGeolocationName();
//            final ArrayList geoId = new ArrayList();
//            ArrayList name1 = new ArrayList();
//            name1.add("");
//            for (GetNameId listNam : listName) {
//                geoId.add(listNam.getId());
//                name1.add(listNam.getName());
//            }
//            final ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(context,
//                    R.layout.spinner_items, name1);
//            districtAdapter.setDropDownViewResource(R.layout.color_spinner_layout);
//            districtAdapter.notifyDataSetChanged();
//            grid.setAdapter(districtAdapter);
//
//            grid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                    long id1 = (long) geoId.get(position);
//                    String longitude1 = "";
//                    String latitude1 = "";
//                    List<Geolocation> geolocationss = new ArrayList<>();
//                    geolocationss = new GeoLocationDAO(context).getLongitudeAndLatitude(id1);
//                    for (Geolocation lat : geolocationss) {
//                        longitude1 = String.valueOf(lat.getLongitude());
//                        latitude1 = String.valueOf(lat.getLatitude());
//
//                    }
//                    longitude.setText(longitude1);
                    //latitude.setText(latitude1);
//
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                }
//            });
//
//            save.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String testingSeting1 = testingSetting.getSelectedItem().toString();
//                    String name1 = name.getText().toString();
//                    if (testingSeting1.isEmpty()) {
//                        FancyToast.makeText(context, "TestSetting can't be empty ", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//                    } else if (name1.isEmpty()) {
//                        name.setError("Enter Name");
//                        FancyToast.makeText(context, "Enter Name", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//                    } else {
//                        new GeoLocationDAO(context).saveGeoLocation(testingSeting1, name1, longitude.getText().toString(), latitude.getText().toString());
//                        FancyToast.makeText(context, "Geolocation captured successfully ", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
//                        dialog.dismiss();
//                    }
//                }
//
//            });
//            dialog.setContentView(promptsView);
//            dialog.show();
//        } catch (Exception e) {
//        }
//    }

}