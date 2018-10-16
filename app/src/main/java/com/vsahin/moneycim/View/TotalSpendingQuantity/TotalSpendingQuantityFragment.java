package com.vsahin.moneycim.View.TotalSpendingQuantity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vsahin.moneycim.R;
import com.vsahin.moneycim.View.Base.BaseFragment;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by Volkan Şahin on 21.08.2017.
 */

public class TotalSpendingQuantityFragment extends BaseFragment {

    @Inject
    TotalSpendingQuantityViewModel viewModel;

    @BindView(R.id.quantity)
    TextView txtQuantity;

    Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
        subscribeTotalQuantity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_total_spending_quantity, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
