package com.carikonsultan.apps.consultant.ui.auth;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.carikonsultan.apps.consultant.R;
import com.carikonsultan.apps.consultant.core.AppInterface;
import com.carikonsultan.apps.consultant.core.Credential;
import com.carikonsultan.apps.consultant.server.ServiceGenerator;
import com.carikonsultan.apps.consultant.server.response.BaseResponse;
import com.carikonsultan.apps.consultant.server.response.Consultants;
import com.carikonsultan.apps.consultant.server.service.PlannerService;
import com.trisnasejati.androidtools.DialogTools;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectFragment extends Fragment {
    private EditText editEmail, editPassword;
    private Button btnLogin;
    private PlannerService plannerService;
    private Credential credential;
    private AppInterface appInterface;
    private ProgressDialog progressDialog;

    public ConnectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_connect, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appInterface = (AppInterface) requireActivity();
        editEmail = view.findViewById(R.id.email);
        editPassword = view.findViewById(R.id.password);
        btnLogin = view.findViewById(R.id.btn_login);

        credential = new Credential(requireContext());
        plannerService = ServiceGenerator.createBaseService(requireContext(), PlannerService.class);
        progressDialog = new ProgressDialog(requireActivity());
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Silahkan tunggu!");
        progressDialog.setCancelable(false);

        btnLogin.setOnClickListener(v -> {
            progressDialog.show();
            DialogTools.snackBar(view, "Loading...");
            plannerService.connectAccount(credential.getReference(), editEmail.getText().toString(), editPassword.getText().toString()).enqueue(new Callback<BaseResponse<Consultants>>() {
                @Override
                public void onResponse(@NonNull Call<BaseResponse<Consultants>> call, @NonNull Response<BaseResponse<Consultants>> response) {
                    if (response.isSuccessful() && response.body()!=null){
                        if (response.body().isStatus()){
                            appInterface.onBack();
                        } else {
                            DialogTools.snackBar(view, response.body().getMessage(), true);
                        }
                    } else {
                        DialogTools.snackBar(view, response.message(), true);
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NonNull Call<BaseResponse<Consultants>> call, @NonNull Throwable t) {
                    DialogTools.snackBar(view, t.getLocalizedMessage(), true);
                    progressDialog.dismiss();
                }
            });
        });
    }
}