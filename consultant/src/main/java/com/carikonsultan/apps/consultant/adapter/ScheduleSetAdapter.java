package com.carikonsultan.apps.consultant.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carikonsultan.apps.consultant.R;
import com.carikonsultan.apps.consultant.core.AppHelper;
import com.carikonsultan.apps.consultant.server.response.Schedule;

import java.util.List;

public class ScheduleSetAdapter extends RecyclerView.Adapter<ScheduleSetAdapter.MyViewHolder> {
   private final Context context;
   private final List<Schedule> list;
   private Dialog dialog;

   public ScheduleSetAdapter(Context context, List<Schedule> list) {
      this.context = context;
      this.list = list;
   }

   public interface Dialog{
      void onSwitch(int position, boolean open);
      void onClick(int position);
   }

   public void setDialog(Dialog dialog) {
      this.dialog = dialog;
   }

   @NonNull
   @Override
   public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(context).inflate(R.layout.row_schedule_set, parent, false);
      return new MyViewHolder(view);
   }

   @SuppressLint("SetTextI18n")
   @Override
   public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      holder.title.setText(AppHelper.getDayName(list.get(position).getDay()));
      if (list.get(position).getStatus().equals("OPEN")) {
         holder.status.setChecked(true);
         holder.time.setText(AppHelper.getTimeDur(list.get(position).getStart(), list.get(position).getEnd()));
      }else{
         holder.status.setChecked(false);
         holder.time.setText("Offline");
      }
      holder.status.setText("");
   }

   @Override
   public int getItemCount() {
      return list.size();
   }

   class MyViewHolder extends RecyclerView.ViewHolder{
      TextView title, time;
      @SuppressLint("UseSwitchCompatOrMaterialCode")
      Switch status;
      public MyViewHolder(@NonNull View itemView) {
         super(itemView);
         title = itemView.findViewById(R.id.title);
         time = itemView.findViewById(R.id.time);
         status = itemView.findViewById(R.id.status);
         itemView.setOnClickListener(v -> {
            dialog.onClick(getLayoutPosition());
         });
         status.setOnClickListener(view -> {
            dialog.onSwitch(getLayoutPosition(), status.isChecked());
         });
      }
   }
}
