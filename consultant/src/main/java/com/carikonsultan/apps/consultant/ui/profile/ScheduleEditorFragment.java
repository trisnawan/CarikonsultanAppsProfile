package com.carikonsultan.apps.consultant.ui.profile;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.carikonsultan.apps.consultant.R;
import com.carikonsultan.apps.consultant.adapter.ScheduleSetAdapter;
import com.carikonsultan.apps.consultant.core.AppInterface;
import com.carikonsultan.apps.consultant.core.Credential;
import com.carikonsultan.apps.consultant.server.ServiceGenerator;
import com.carikonsultan.apps.consultant.server.response.BaseResponse;
import com.carikonsultan.apps.consultant.server.response.Schedule;
import com.carikonsultan.apps.consultant.server.service.PlannerService;
import com.trisnasejati.androidtools.CurrencyTools;
import com.trisnasejati.androidtools.DialogTools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleEditorFragment extends Fragment {
    private SwipeRefreshLayout refreshLayout;
    private AppInterface appInterface;
    private final List<Schedule> list;
    private ScheduleSetAdapter setAdapter;
    private Schedule dataSchedule;
    private PlannerService plannerService;
    private Credential credential;

    public ScheduleEditorFragment(List<Schedule> list) {
        this.list = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule_editor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appInterface = (AppInterface) requireActivity();
        refreshLayout = view.findViewById(R.id.refresh_layout);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        credential = new Credential(requireContext());
        plannerService = ServiceGenerator.createBaseService(requireContext(), PlannerService.class);
        dataSchedule = new Schedule();
        setAdapter = new ScheduleSetAdapter(requireContext(), list);
        setAdapter.setDialog(new ScheduleSetAdapter.Dialog() {
            @Override
            public void onSwitch(int position, boolean open) {
                if (open){
                    setTime("OPEN", position, 0);
                }else{
                    dataSchedule.setStatus("CLOSED");
                    dataSchedule.setDay(list.get(position).getDay());
                    dataSchedule.setStart("00:00:00");
                    dataSchedule.setEnd("00:00:00");
                    saveTime(dataSchedule);
                }
            }

            @Override
            public void onClick(int position) {
                setTime("OPEN", position, 0);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(setAdapter);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setNestedScrollingEnabled(false);

        refreshLayout.setOnRefreshListener(this::getData);
        getData();
    }

    private void getData(){
        refreshLayout.setRefreshing(false);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    private void setTime(String status, int pos, int count){
        dataSchedule.setStatus(status);
        dataSchedule.setDay(list.get(pos).getDay());
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), (timePicker, i, i1) -> {
            if (count == 0) {
                dataSchedule.setStart(timePicker.getHour() + ":" + timePicker.getMinute() + ":00");
                setTime(status, pos, count + 1);
            } else {
                dataSchedule.setEnd(timePicker.getHour() + ":" + timePicker.getMinute() + ":00");
                saveTime(dataSchedule);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.setOnCancelListener(dialogInterface -> setAdapter.notifyDataSetChanged());
        if (count==0){
            timePickerDialog.setTitle("Waktu mulai aktif");
        }else{
            timePickerDialog.setTitle("Waktu berakhir aktif");
        }
        timePickerDialog.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void saveTime(Schedule schedule){
        refreshLayout.setRefreshing(true);
        DialogTools.snackBar(refreshLayout, "Loading...");
        plannerService.saveSchedule(credential.getReference(), schedule.getDay(), schedule.getStart(), schedule.getEnd(), schedule.getStatus()).enqueue(new Callback<BaseResponse<List<Schedule>>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<List<Schedule>>> call, @NonNull Response<BaseResponse<List<Schedule>>> response) {
                if (response.isSuccessful() && response.body()!=null){
                    if (response.body().isStatus() && response.body().getData()!=null){
                        list.clear();
                        list.addAll(response.body().getData());
                        setAdapter.notifyDataSetChanged();
                    } else {
                        DialogTools.snackBar(refreshLayout, response.body().getMessage(), true);
                        if (response.body().getErrorCode()==401){
                            appInterface.onUnauthorized();
                        }
                    }
                } else {
                    DialogTools.snackBar(refreshLayout, response.message(), true);
                }
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<List<Schedule>>> call, @NonNull Throwable t) {
                DialogTools.snackBar(refreshLayout, t.getLocalizedMessage(), true);
                refreshLayout.setRefreshing(false);
            }
        });
    }
}