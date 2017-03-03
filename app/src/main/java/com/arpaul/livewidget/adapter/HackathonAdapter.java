package com.arpaul.livewidget.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.arpaul.livewidget.R;
import com.arpaul.livewidget.dataobject.HackathonDO;
import com.arpaul.utilitieslib.ColorUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ARPaul on 03-03-2017.
 */

public class HackathonAdapter extends RecyclerView.Adapter<HackathonAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HackathonDO> arrHackathonDO = new ArrayList<>();

    public HackathonAdapter(Context context, ArrayList<HackathonDO> arrTours) {
        this.context = context;
        this.arrHackathonDO = arrTours;
    }

    public void refresh(ArrayList<HackathonDO> arrTours) {
        this.arrHackathonDO = arrTours;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_hackathonlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HackathonDO objHackathonDO = arrHackathonDO.get(position);

        holder.tvTitle.setText(objHackathonDO.title);
        holder.tvDescription.setText(objHackathonDO.description);

        if(objHackathonDO.status.equalsIgnoreCase("ongoing"))
            holder.tvStatus.setBackgroundColor(ColorUtils.getColor(context,R.color.color_Green));
        else
            holder.tvStatus.setBackgroundColor(ColorUtils.getColor(context,R.color.color_DirtyYellow));
        holder.tvStatus.setText(objHackathonDO.status);
        StringBuilder college = new StringBuilder();
        college.append("College: ");
        if(objHackathonDO.college.equalsIgnoreCase("true"))
            college.append("Yes");
        else
            college.append("No");
        holder.tvCollege.setText(college.toString());
        holder.tvChallenge.setText(objHackathonDO.challenge_type);

        holder.tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //opens the url in one of the Android Browsers.
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(objHackathonDO.url));
                context.startActivity(intent);
            }
        });

        if(!TextUtils.isEmpty(objHackathonDO.thumbnail)) {
            Picasso.with(context).load(objHackathonDO.thumbnail).into(holder.ivHackthonThumb);
        }
    }

    @Override
    public int getItemCount() {
        if(arrHackathonDO != null)
            return arrHackathonDO.size();

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvTitle;
        public final TextView tvDescription;
        public final TextView tvStatus;
        public final TextView tvCollege;
        public final TextView tvChallenge;
        public final TextView tvLink;
        public final ImageView ivHackthonThumb;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvTitle                 = (TextView) view.findViewById(R.id.tvTitle);
            tvDescription           = (TextView) view.findViewById(R.id.tvDescription);
            tvStatus                = (TextView) view.findViewById(R.id.tvStatus);
            tvCollege               = (TextView) view.findViewById(R.id.tvCollege);
            tvChallenge             = (TextView) view.findViewById(R.id.tvChallenge);
            tvLink                  = (TextView) view.findViewById(R.id.tvLink);

            ivHackthonThumb         = (ImageView) view.findViewById(R.id.ivHackthonThumb);
        }

        @Override
        public String toString() {
            return "";
        }
    }
}
