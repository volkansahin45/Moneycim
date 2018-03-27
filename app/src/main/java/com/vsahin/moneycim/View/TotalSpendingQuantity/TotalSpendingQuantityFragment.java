package com.vsahin.moneycim.View.TotalSpendingQuantity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vsahin.moneycim.R;
import com.vsahin.moneycim.View.Base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Volkan Şahin on 21.08.2017.
 */

public class TotalSpendingQuantityFragment extends BaseFragment {

    private TotalSpendingQuantityViewModel viewModel;

    @BindView(R.id.quantity)
    TextView txtQuantity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(TotalSpendingQuantityViewModel.class);
        subscribeTotalQuantity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_total_spending_quantity, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    private void subscribeTotalQuantity(){
        viewModel.totalSpendingQuantity.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(final Double quantity) {
                if(quantity != null){
                    showTotalQuantityInUi(quantity, txtQuantity);
                } else {
                    showTotalQuantityInUi(0.0, txtQuantity);
                }
            }
        });
    }

    private void showTotalQuantityInUi(@NonNull Double quantity, final TextView txtQuantity){
        //round two places
        quantity = Math.round(quantity * 100.0) / 100.0;
        String text = getContext().getString(R.string.turkish_lira_symbol) + String.valueOf(quantity);
        txtQuantity.setText(text);
    }
}
