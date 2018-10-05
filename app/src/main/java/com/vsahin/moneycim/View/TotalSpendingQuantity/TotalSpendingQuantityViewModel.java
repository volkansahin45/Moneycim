package com.vsahin.moneycim.View.TotalSpendingQuantity;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.vsahin.moneycim.Model.Repository.SpendingRepository;
import com.vsahin.moneycim.MoneycimApp;

import javax.inject.Inject;

/**
 * Created by Volkan Şahin on 21.08.2017.
 */

public class TotalSpendingQuantityViewModel extends AndroidViewModel{
    @Inject
    SpendingRepository spendingRepository;
    final public LiveData<Double> totalSpendingQuantity;

    public TotalSpendingQuantityViewModel(Application application) {
        super(application);
        ((MoneycimApp)getApplication()).getAppComponent().inject(this);

        totalSpendingQuantity = getTotalSpendingQuantity();
    }

    private LiveData<Double> getTotalSpendingQuantity(){
        return  spendingRepository.getTotalSpendingQuantity();
    }
}
