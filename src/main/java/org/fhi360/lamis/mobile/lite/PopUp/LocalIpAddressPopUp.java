package org.fhi360.lamis.mobile.lite.PopUp;


public class LocalIpAddressPopUp {
//    private Context context;
//    private LayoutInflater inflater;
//    private Dialog dialog = null;
//
//    public LocalIpAddressPopUp(Context context) {
//        this.context = context;
//        this.inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        dialog = new Dialog(context,
//                android.R.style.Theme_Translucent_NoTitleBar);
//        //dialog.getWindow().getAttributes().windowAnimations = R.style.AnimationBottomToUp;
//        dialog.setCancelable(true);
//        //initializeDialoge();
//    }
//
//    public void showPopUp() {
//        if(dialog!=null){
//            loadPopup();
//        }else{
//
//        }
//    }
//
//    public void disMissPopup(){
//        if(dialog!=null) {
//            if (dialog.isShowing()) {
//                dialog.dismiss();
//            }
//        }
//    }
//
//    private void loadPopup() {
//        try {
//            View view = inflater.inflate(R.layout.set_up, null,
//                    false);
//          final   TextView ipaddress =  view.findViewById(R.id.ipaddress);
//            Button button_account =  view.findViewById(R.id.button_account);
//            button_account.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String ipaddress1 =ipaddress.getText().toString();
//                    if (ipaddress1.isEmpty()) {
//                        ipaddress.setError("Enter Ip address");
//                        FancyToast.makeText(context, "Enter Ip address", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//                        dialog.dismiss();
//                    }else {
//                            new PrefManager(context).saveIpAddress(ipaddress.getText().toString());
//                            LocalSystemIpAddressHandler localSystemIpAddressHandler = new LocalSystemIpAddressHandler(context);
//                            localSystemIpAddressHandler.start();
//                            FancyToast.makeText(context, "Ip Address saved successfully ", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
//                            dialog.dismiss();
//                        }
//                    }
//
//            });
//            dialog.setContentView(view);
//            dialog.show();
//        } catch (Exception e) {
//
//        }
//    }

}