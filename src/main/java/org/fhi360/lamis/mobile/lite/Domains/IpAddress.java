package org.fhi360.lamis.mobile.lite.Domains;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class IpAddress {
    private SharedPreferences prefs;
    public IpAddress(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }


    public void setIpAddress(String ipAddress) {
        prefs.edit().putString("ipAddress", ipAddress).commit();
    }

    public String getIpAddress() {
        String ipAddress = prefs.getString("ipAddress","");
        return ipAddress;
    }
}
