package org.fhi360.lamis.mobile.lite.Activities;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.fhi360.lamis.mobile.lite.DAO.DefaultersDAO;
import org.fhi360.lamis.mobile.lite.Domains.Defaulter;
import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;
import java.util.ArrayList;
import java.util.List;

public class ClientTracking extends Fragment {
    private PrefManager prefManager;
    private RecyclerView recyclerView;
    private TableViewAdapter adapter;
    private List<Defaulter> defaulterList;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contact_tracking, container, false);
        prefManager = new PrefManager(getContext());
        defaulterList = new ArrayList<>();
        defaulterList.clear();
        recyclerView = rootView.findViewById(R.id.recyclerViewDeliveryProductList);
        defaulterList.addAll(new DefaultersDAO(getContext()).getDefaulters());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TableViewAdapter(getContext(), defaulterList);
        recyclerView.setAdapter(adapter);

        return rootView;


    }


}
