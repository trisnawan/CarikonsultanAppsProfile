package com.carikonsultan.apps.consultant.server.service;

import com.carikonsultan.apps.consultant.server.response.BaseResponse;
import com.carikonsultan.apps.consultant.server.response.Consultants;
import com.carikonsultan.apps.consultant.server.response.Schedule;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PlannerService {

    @FormUrlEncoded
    @POST("users/connect")
    Call<BaseResponse<Consultants>> connectAccount(@Field("reference") String reference, @Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("users/consultant")
    Call<BaseResponse<Consultants>> profile(@Field("reference_id") String id);

    @FormUrlEncoded
    @POST("users/consultant_update")
    Call<BaseResponse<Consultants>> savePrice(@Field("reference_id") String id, @Field("consultant_price") double price);

    @FormUrlEncoded
    @POST("users/schedule_update")
    Call<BaseResponse<List<Schedule>>> saveSchedule(@Field("reference_id") String id, @Field("day") int day, @Field("start") String start, @Field("end") String end, @Field("status") String status);

}
