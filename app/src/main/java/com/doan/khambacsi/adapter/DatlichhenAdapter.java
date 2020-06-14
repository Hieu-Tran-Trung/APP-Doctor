package com.doan.khambacsi.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doan.khambacsi.R;
import com.doan.khambacsi.model.ScheduleRow;

import java.util.ArrayList;

public class DatlichhenAdapter extends RecyclerView.Adapter<DatlichhenAdapter.ViewHolder> {

    private ArrayList<ScheduleRow> arrayScheduleRow;

    public DatlichhenAdapter(ArrayList<ScheduleRow> arrayScheduleRow) {
        this.arrayScheduleRow = arrayScheduleRow;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_lichhen,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTimer.setText(arrayScheduleRow.get(position).getTime());
        if (arrayScheduleRow.get(position).isChecked()){
            holder.txtTimer.setBackgroundResource(R.drawable.ic_custom_4);
            holder.txtTimer.setTextColor(Color.WHITE);
            holder.txtTimer.setTypeface(null, Typeface.BOLD);
        }else {
            holder.txtTimer.setBackgroundResource(R.drawable.ic_custom_5);
            holder.txtTimer.setTextColor(Color.BLACK);
            holder.txtTimer.setTypeface(null, Typeface.NORMAL);
        }
    }

    private void setCheckedOff(){
        for (int i = 0; i < arrayScheduleRow.size(); i++){
            if (arrayScheduleRow.get(i).isChecked()){
                arrayScheduleRow.get(i).setChecked(false);
                notifyItemChanged(i);
            }
        }
    }

    @Override
    public int getItemCount() {
        return arrayScheduleRow.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTimer;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTimer = itemView.findViewById(R.id.txtTimer);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCheckedOff();
                    arrayScheduleRow.get(getAdapterPosition()).setChecked(!arrayScheduleRow.get(getAdapterPosition()).isChecked());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
