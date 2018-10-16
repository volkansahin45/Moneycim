package com.vsahin.moneycim.View.TotalSpendingQuantity;

import com.vsahin.moneycim.Model.Repository.SpendingRepository;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Volkan Şahin on 21.08.2017.
 */

public class TotalSpendingQuantityViewModel extends ViewModel {
    SpendingRepository spendingRepository;

    final public LiveData<Double> totalSpendingQuantity;

    @Inject
    public TotalSpendingQuantityViewModel(SpendingRepository spendingRepository) {
        this.spendingRepository = spendingRepository;

        totalSpendingQuantity = getTotalSpendingQuantity();
    }

    private LiveData<Double> getTotalSpendingQuantity(){
        return  spendingRepository.getTotalSpendingQuantity();
    }
}
