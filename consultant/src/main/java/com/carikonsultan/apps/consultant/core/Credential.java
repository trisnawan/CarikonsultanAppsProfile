package com.carikonsultan.apps.consultant.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Credential {
   private final Context context;
   private final SharedPreferences sharedpreferences;
   private final String TAG = "Credential";

   public Credential(Context context){
      this.context = context;
      this.sharedpreferences = context.getSharedPreferences("com.carikonsultan.plugin", Context.MODE_PRIVATE);
   }

   public void setCredential(String username, String password, String key, String reference){
      Log.d(TAG, "Credential initialized! Reference : "+reference);
      SharedPreferences.Editor editor = sharedpreferences.edit();
      editor.putString("ck_username", username);
      editor.putString("ck_password", password);
      editor.putString("ck_key", key);
      editor.putString("ck_reference", reference);
      editor.apply();
   }

   public String getUsername() {
      return sharedpreferences.getString("ck_username", null);
   }

   public String getPassword() {
      return sharedpreferences.getString("ck_password", null);
   }

   public String getKey() {
      return sharedpreferences.getString("ck_key", null);
   }

   public String getReference() {
      return sharedpreferences.getString("ck_reference", null);
   }
}
