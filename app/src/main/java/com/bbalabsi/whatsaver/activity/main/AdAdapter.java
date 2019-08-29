package com.bbalabsi.whatsaver.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bbalabsi.whatsaver.R;

import java.util.ArrayList;

public class AdAdapter extends RecyclerView.Adapter<AdAdapter.MyViewHolder> {

    private ArrayList<AdModel> dataSet;
    private Activity           mActivity;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView  root;
        TextView  textViewName;
        TextView  textViewVersion;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.root            = itemView.findViewById(R.id.root_cv);
            this.textViewName    = itemView.findViewById(R.id.textViewName);
            this.textViewVersion = itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon   = itemView.findViewById(R.id.imageView);
        }
    }

    public AdAdapter(Activity mActivity, ArrayList<AdModel> data) {
        this.dataSet   = data;
        this.mActivity = mActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ad_item, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        CardView  root            = holder.root;
        TextView  textViewName    = holder.textViewName;
        TextView  textViewVersion = holder.textViewVersion;
        ImageView imageView       = holder.imageViewIcon;

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (listPosition)
                {
                    case 0:
                        openApp("com.bbalabsi.whatsaver");
                        break;

                    case 1:
                        openApp("com.bbalabsi.stickersapp");
                        break;
                }

            }
        });

        textViewName.setText(dataSet.get(listPosition).getmName());
        textViewVersion.setText(dataSet.get(listPosition).getmDetails());
        imageView.setImageResource(dataSet.get(listPosition).getmImage());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    private void openApp(String packagee)
    {
        Intent i;
        PackageManager manager = mActivity.getPackageManager();
        try {
            i = manager.getLaunchIntentForPackage(packagee);
            if (i == null)
                throw new PackageManager.NameNotFoundException();
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            mActivity.startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            try {
                mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packagee)));
            } catch (android.content.ActivityNotFoundException anfe) {
                mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packagee)));
            }
        }
    }
}
