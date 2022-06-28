package com.carikonsultan.apps.consultant.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.carikonsultan.apps.consultant.R;
import com.carikonsultan.apps.consultant.core.AppInterface;
import com.carikonsultan.apps.consultant.core.Credential;
import com.carikonsultan.apps.consultant.server.ServiceGenerator;
import com.carikonsultan.apps.consultant.server.response.BaseResponse;
import com.carikonsultan.apps.consultant.server.response.Consultants;
import com.carikonsultan.apps.consultant.server.service.PlannerService;
import com.trisnasejati.androidtools.CurrencyTools;
import com.trisnasejati.androidtools.DialogTools;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private Credential credential;
    private Consultants consultants;
    private AppInterface appInterface;
    private PlannerService plannerService;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayout mainLayout, btnAvatar;
    private ImageView avatar;
    private TextView textName, textPrice, textSchedule;
    private RelativeLayout btnProfile, btnConsultant, btnPrice, btnSchedule;

    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appInterface = (AppInterface) requireActivity();
        refreshLayout = view.findViewById(R.id.refresh_layout);
        mainLayout = view.findViewById(R.id.main_layout);
        btnAvatar = view.findViewById(R.id.btn_avatar);
        avatar = view.findViewById(R.id.avatar);
        textName = view.findViewById(R.id.full_name);
        textPrice = view.findViewById(R.id.text_price);
        textSchedule = view.findViewById(R.id.text_schedule);
        btnProfile = view.findViewById(R.id.btn_profile);
        btnConsultant = view.findViewById(R.id.btn_consultant);
        btnPrice = view.findViewById(R.id.btn_price);
        btnSchedule = view.findViewById(R.id.btn_schedule);
        mainLayout.setVisibility(View.GONE);

        consultants = new Consultants();
        credential = new Credential(requireContext());
        plannerService = ServiceGenerator.createBaseService(requireContext(), PlannerService.class);
        refreshLayout.setOnRefreshListener(this::getData);
        btnAvatar.setOnClickListener(v -> {
            DialogTools.toast(requireContext(), "Dalam pengembangan!");
        });
        btnProfile.setOnClickListener(v -> {
            DialogTools.toast(requireContext(), "Dalam pengembangan!");
        });
        btnConsultant.setOnClickListener(v -> {
            DialogTools.toast(requireContext(), "Dalam pengembangan!");
        });
        btnPrice.setOnClickListener(v -> {
            PriceDialogFragment dialogFragment = new PriceDialogFragment(consultants.getPrice());
            dialogFragment.setDialog(priceDialogInterface);
            dialogFragment.show(getParentFragmentManager(), null);
        });
        btnSchedule.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            ScheduleEditorFragment editorFragment = new ScheduleEditorFragment(consultants.getSchedules().getAll());
            transaction.replace(R.id.frame_layout, editorFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
        getData();
    }

    private final PriceDialogFragment.Dialog priceDialogInterface = price -> {
        refreshLayout.setRefreshing(true);
        mainLayout.setVisibility(View.GONE);
        DialogTools.snackBar(refreshLayout, "Loading...");
        plannerService.savePrice(credential.getReference(), price).enqueue(new Callback<BaseResponse<Consultants>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<Consultants>> call, @NonNull Response<BaseResponse<Consultants>> response) {
                if (response.isSuccessful() && response.body()!=null){
                    if (response.body().isStatus() && response.body().getData()!=null){
                        consultants = response.body().getData();
                        setView();
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
            public void onFailure(@NonNull Call<BaseResponse<Consultants>> call, @NonNull Throwable t) {
                DialogTools.snackBar(refreshLayout, t.getLocalizedMessage(), true);
                refreshLayout.setRefreshing(false);
            }
        });
    };

    private void getData(){
        refreshLayout.setRefreshing(true);
        mainLayout.setVisibility(View.GONE);
        DialogTools.snackBar(refreshLayout, "Loading...");
        plannerService.profile(credential.getReference()).enqueue(new Callback<BaseResponse<Consultants>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<Consultants>> call, @NonNull Response<BaseResponse<Consultants>> response) {
                if (response.isSuccessful() && response.body()!=null){
                    if (response.body().isStatus() && response.body().getData()!=null){
                        consultants = response.body().getData();
                        setView();
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
            public void onFailure(@NonNull Call<BaseResponse<Consultants>> call, @NonNull Throwable t) {
                DialogTools.snackBar(refreshLayout, t.getLocalizedMessage(), true);
                refreshLayout.setRefreshing(false);
            }
        });
    }

    public void setView(){
        Glide.with(requireContext()).load(consultants.getAvatar()).into(avatar);
        textName.setText(consultants.getFullName());
        textPrice.setText(CurrencyTools.rupiah((double) consultants.getPrice()));
        textSchedule.setText("Atur jadwal aktif anda disini");
        mainLayout.setVisibility(View.VISIBLE);
    }
}