package org.fhi360.lamis.mobile.lite.Fragments;

        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.text.Html;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import org.fhi360.lamis.mobile.lite.DAO.HtsDAO;
        import org.fhi360.lamis.mobile.lite.Db.LAMISLiteDb;
        import org.fhi360.lamis.mobile.lite.R;

public class DashBoard extends Fragment {
    private TextView totalTested, totalPositive, totalInitiated;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dashboard, container, false);

        totalTested = rootView.findViewById(R.id.totalTested);
        totalPositive = rootView.findViewById(R.id.totalPositive);
        totalInitiated = rootView.findViewById(R.id.totalInitiated);
        int totalNumTest = new HtsDAO(getContext()).totalNumberOfHts();
        int totalPositive2 = new HtsDAO(getContext()).totalNumberOfPosivitive();
        int totalInitiated2 = new HtsDAO(getContext()).totalNumberInitiated();
        totalTested.setText(Html.fromHtml("<font color=\"#FFFFFF\">" + "Total Tested " + "</font><br><br>" + "<font color=\"#000000\">" + totalNumTest + "</font>"));
        totalPositive.setText(Html.fromHtml("<font color=\"#FFFFFF\">" + "Total Positive " + "</font><br><br>" + "<font color=\"#000000\">" + totalPositive2 + "</font>"));
        totalInitiated.setText(Html.fromHtml("<font color=\"#FFFFFF\">" + "Total Initiated " + "</font><br><br>" + "<font color=\"#000000\">" + totalInitiated2 + "</font>"));
        return rootView;
    }
}
