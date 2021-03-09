package org.fhi360.lamis.mobile.lite.Utils;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class SettingConfig {
    public static void setSpinText(Spinner spinner, Object value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    public static int getAge(String dateOfbirth) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar dob = Calendar.getInstance();
        dob.setTime(sdf.parse(dateOfbirth));
        Calendar today = Calendar.getInstance();
        int curYear = today.get(Calendar.YEAR);
        int dobYear = dob.get(Calendar.YEAR);
        int age = curYear - dobYear;
        int curMonth = today.get(Calendar.MONTH);
        int dobMonth = dob.get(Calendar.MONTH);
        if (dobMonth > curMonth) { // this year can't be counted!
            age--;
        } else if (dobMonth == curMonth) { // same month? check for day
            int curDay = today.get(Calendar.DAY_OF_MONTH);
            int dobDay = dob.get(Calendar.DAY_OF_MONTH);
            if (dobDay > curDay) { // this year can't be counted!
                age--;
            }
        }

        return age;
    }

    public String getTodaysmonth() {
        String month1 = "";
        String day1 = "";
        java.util.Calendar cal = new java.util.GregorianCalendar();
        int month = (cal.get(GregorianCalendar.MONTH) + 1);
        if (new Integer(month).toString().length() < 2) {
            month1 = "0" + month;

        }
        String TODAY = month1;
        return TODAY;
    }

    public String getTodaysyear() {
        String month1 = "";
        String day1 = "";
        java.util.Calendar cal = new java.util.GregorianCalendar();
        int year = cal.get(GregorianCalendar.YEAR);
        String TODAY = year + "";
        return TODAY;
    }



    public static int countOccurences(
            String someString, char searchedChar, int index) {
        if (index >= someString.length()) {
            return 0;
        }

        int count = someString.charAt(index) == searchedChar ? 1 : 0;
        return count + countOccurences(
                someString, searchedChar, index + 1);
    }

    public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = -1;
        do {
            pos = str.indexOf(substr, pos + 1);
        } while (n-- > 0 && pos != -1);
        return pos;
    }



}