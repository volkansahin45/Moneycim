package com.vsahin.moneycim.View.TotalSpendingQuantity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vsahin.moneycim.R;
import com.vsahin.moneycim.View.Base.BaseFragment;
import com.vsahin.moneycim.ViewModelFactory;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by Volkan Şahin on 21.08.2017.
 */

public class TotalSpendingQuantityFragment extends BaseFragment {

    private TotalSpendingQuantityViewModel viewModel;

    @BindView(R.id.quantity)
    TextView txtQuantity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_total_spending_quantity, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TotalSpendingQuantityViewModel.class);
        subscribeTotalQuantity();
    }

    private void subscribeTotalQuantity() {
        viewModel.totalSpendingQuantity.observe(this, quantity -> {
            if(quantity != null) {
                showTotalQuantityInUi(quantity, txtQuantity);
            }
            else {
                showTotalQuantityInUi(0.0, txtQuantity);
            }
        });
    }

    private void showTotalQuantityInUi(@NonNull Double quantity, final TextView txtQuantity) {
        //round two places
        quantity = Math.round(quantity * 100.0) / 100.0;
        String text = getContext().getString(R.string.turkish_lira_symbol) + String.valueOf(quantity);
        txtQuantity.setText(text);
    }
}
