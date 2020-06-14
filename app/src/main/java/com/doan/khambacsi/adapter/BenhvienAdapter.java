package com.doan.khambacsi.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doan.khambacsi.R;
import com.doan.khambacsi.config.Constants;
import com.doan.khambacsi.model.Hospital;
import com.doan.khambacsi.view.BenhvienActivity;

import java.util.ArrayList;

public class BenhvienAdapter extends RecyclerView.Adapter<BenhvienAdapter.ViewHolder> {

    private ArrayList<Hospital> arrayHospital;

    public BenhvienAdapter(ArrayList<Hospital> arrayHospital) {
        this.arrayHospital = arrayHospital;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_benhvien,parent,false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.imgImage)
                .load(arrayHospital.get(position).getImage())
                .into(holder.imgImage);

        holder.txtName.setText(arrayHospital.get(position).getName());
        holder.txtHotline.setText("Hotline: " + arrayHospital.get(position).getHotline());
        holder.txtWorkTime.setText("Thời gian làm việc: " + arrayHospital.get(position).getTimeStart() + " - " + arrayHospital.get(position).getTimeEnd());
    }

    @Override
    public int getItemCount() {
        return arrayHospital.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgImage;
        TextView txtName,txtHotline,txtWorkTime;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgImage    = itemView.findViewById(R.id.imgImage);
            txtName     = itemView.findViewById(R.id.txtName);
            txtHotline  = itemView.findViewById(R.id.txtHotline);
            txtWorkTime = itemView.findViewById(R.id.txtWorkTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), BenhvienActivity.class);
                    intent.putExtra(Constants.KEY_HOSPITAL,arrayHospital.get(getAdapterPosition()));
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
