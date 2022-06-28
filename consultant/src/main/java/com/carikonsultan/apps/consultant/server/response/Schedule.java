package com.carikonsultan.apps.consultant.server.response;

import com.google.gson.annotations.SerializedName;

public class Schedule {
   private @SerializedName("active_start") String start;
   private @SerializedName("active_end") String end;
   private @SerializedName("active_status") String status;
   private @SerializedName("active_services") boolean service;
   private @SerializedName("active_day") int day;

   public String getStart() {
      return start;
   }

   public void setStart(String start) {
      this.start = start;
   }

   public String getEnd() {
      return end;
   }

   public void setEnd(String end) {
      this.end = end;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public boolean isService() {
      return service;
   }

   public void setService(boolean service) {
      this.service = service;
   }

   public int getDay() {
      return day;
   }

   public void setDay(int day) {
      this.day = day;
   }
}
