package com.carikonsultan.apps.consultant.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.carikonsultan.apps.consultant.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.trisnasejati.androidtools.CurrencyTools;
import com.trisnasejati.androidtools.DialogTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PriceDialogFragment extends BottomSheetDialogFragment {
    private Spinner spinner;
    private Button btnSave;
    private Dialog dialog;
    private double price = 0;
    private final ArrayList<String> prices = new ArrayList<>();
    private final List<HashMap<String, String>> priceData = new ArrayList<>();
    private int position = 0;

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public interface Dialog{
        void onSave(double price);
    }

    public PriceDialogFragment(double price) {
        this.price = price;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_price_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner = view.findViewById(R.id.spinner);
        btnSave = view.findViewById(R.id.btn_save);

        double[] priceDouble = {15000, 25000, 50000, 75000, 100000};
        for (int i=0; i<priceDouble.length; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(priceDouble[i]));
            map.put("name", CurrencyTools.rupiah(priceDouble[i]));
            priceData.add(map);
            prices.add(CurrencyTools.rupiah(priceDouble[i]));
            if (price == priceDouble[i]){
                position = i;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, prices);
        spinner.setAdapter(adapter);
        spinner.setSelection(position);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (priceData.get(i).get("id")!=null) {
                    price = Double.parseDouble(Objects.requireNonNull(priceData.get(i).get("id")));
                }else{
                    price = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSave.setOnClickListener(v -> {
            if (price>0){
                dialog.onSave(price);
                dismiss();
            }else{
                DialogTools.toast(requireContext(), "Silahkan masukan harga chat terlebih dahulu!");
            }
        });
    }
}