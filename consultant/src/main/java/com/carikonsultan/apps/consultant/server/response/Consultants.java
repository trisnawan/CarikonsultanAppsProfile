package com.carikonsultan.apps.consultant.server.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Consultants {
   private String email, cellular, avatar, aperkei, aaji, since, desc, status, service;
   private @SerializedName("full_name") String fullName;
   private long price;
   private @SerializedName("company_name") String companyName;
   private @SerializedName("company_join") String companyJoin;
   private @SerializedName("company_job") String companyJob;
   private @SerializedName("achievement") List<Achievement> achievement;
   private @SerializedName("schedule") ScheduleBase schedules;

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getCellular() {
      return cellular;
   }

   public void setCellular(String cellular) {
      this.cellular = cellular;
   }

   public String getAvatar() {
      return avatar;
   }

   public void setAvatar(String avatar) {
      this.avatar = avatar;
   }

   public String getAperkei() {
      return aperkei;
   }

   public void setAperkei(String aperkei) {
      this.aperkei = aperkei;
   }

   public String getAaji() {
      return aaji;
   }

   public void setAaji(String aaji) {
      this.aaji = aaji;
   }

   public String getSince() {
      return since;
   }

   public void setSince(String since) {
      this.since = since;
   }

   public String getDesc() {
      return desc;
   }

   public void setDesc(String desc) {
      this.desc = desc;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public String getService() {
      return service;
   }

   public void setService(String service) {
      this.service = service;
   }

   public String getFullName() {
      return fullName;
   }

   public void setFullName(String fullName) {
      this.fullName = fullName;
   }

   public long getPrice() {
      return price;
   }

   public void setPrice(long price) {
      this.price = price;
   }

   public String getCompanyName() {
      return companyName;
   }

   public void setCompanyName(String companyName) {
      this.companyName = companyName;
   }

   public String getCompanyJoin() {
      return companyJoin;
   }

   public void setCompanyJoin(String companyJoin) {
      this.companyJoin = companyJoin;
   }

   public String getCompanyJob() {
      return companyJob;
   }

   public void setCompanyJob(String companyJob) {
      this.companyJob = companyJob;
   }

   public List<Achievement> getAchievement() {
      return achievement;
   }

   public void setAchievement(List<Achievement> achievement) {
      this.achievement = achievement;
   }

   public ScheduleBase getSchedules() {
      return schedules;
   }

   public void setSchedules(ScheduleBase schedules) {
      this.schedules = schedules;
   }
}
