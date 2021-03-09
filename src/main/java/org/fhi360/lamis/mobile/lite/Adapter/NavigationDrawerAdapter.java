package org.fhi360.lamis.mobile.lite.Adapter;

/**
 * Created by Ravi on 29/07/15.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.fhi360.lamis.mobile.lite.Domains.NavDrawerItem;
import java.util.Collections;
import java.util.List;
import org.fhi360.lamis.mobile.lite.R;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    private List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavDrawerItem current = data.get(position);
//        if(current.getTitle().equalsIgnoreCase("Patient List")){
//            holder.title.setText(current.getTitle());
//          //  holder.imageView.setImageResource(R.drawable.ic_home_black_24dp);
//        }
        holder.title.setText(current.getTitle());
     //   holder.imageView.setImageResource(R.drawable.ic_home_black_24dp);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            title =  itemView.findViewById(R.id.title);

        }
    }
}
