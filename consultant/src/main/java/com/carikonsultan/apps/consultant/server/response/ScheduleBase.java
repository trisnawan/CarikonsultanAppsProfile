package com.carikonsultan.apps.consultant.server.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduleBase {
   private @SerializedName("list") List<Schedule> all;
   private @SerializedName("now") Schedule now;

   public List<Schedule> getAll() {
      return all;
   }

   public void setAll(List<Schedule> all) {
      this.all = all;
   }

   public Schedule getNow() {
      return now;
   }

   public void setNow(Schedule now) {
      this.now = now;
   }
}
