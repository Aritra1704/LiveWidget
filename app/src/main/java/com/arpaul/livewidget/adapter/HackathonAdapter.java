package com.arpaul.livewidget.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arpaul.livewidget.R;
import com.arpaul.livewidget.dataobject.HackathonDO;

import java.util.ArrayList;

/**
 * Created by ARPaul on 03-03-2017.
 */

public class HackathonAdapter extends RecyclerView.Adapter<HackathonAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HackathonDO> arrPrefLocationDO = new ArrayList<>();

    public HackathonAdapter(Context context, ArrayList<HackathonDO> arrTours) {
        this.context = context;
        this.arrPrefLocationDO = arrTours;
    }

    public void refresh(ArrayList<HackathonDO> arrTours) {
        this.arrPrefLocationDO = arrTours;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_hackathonlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HackathonDO objPrefLocationDO = arrPrefLocationDO.get(position);

        holder.tvTitle.setText(objPrefLocationDO.title);
        holder.tvDescription.setText(objPrefLocationDO.description);

        holder.tvStatus.setText("STATUS: " + objPrefLocationDO.status);
        holder.tvCollege.setText("College: " + objPrefLocationDO.college);

    }

    @Override
    public int getItemCount() {
        if(arrPrefLocationDO != null)
            return arrPrefLocationDO.size();

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvTitle;
        public final TextView tvDescription;
        public final TextView tvStatus;
        public final TextView tvCollege;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvTitle                 = (TextView) view.findViewById(R.id.tvTitle);
            tvDescription           = (TextView) view.findViewById(R.id.tvDescription);
            tvStatus                = (TextView) view.findViewById(R.id.tvStatus);
            tvCollege               = (TextView) view.findViewById(R.id.tvCollege);
        }

        @Override
        public String toString() {
            return "";
        }
    }
}
