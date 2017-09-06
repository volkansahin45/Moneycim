package com.vsahin.moneycim.View.SpendingList;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.vsahin.moneycim.Model.Entity.RawSpending;
import com.vsahin.moneycim.Model.Pojo.Spending;
import com.vsahin.moneycim.Model.Repository.SpendingRepository;
import com.vsahin.moneycim.MoneycimApp;

import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Volkan Şahin on 17.08.2017.
 */

public class SpendingListViewModel extends AndroidViewModel {

    @Inject public SpendingRepository spendingRepository;
    final public LiveData<List<Spending>> spendings;

    public SpendingListViewModel(Application application) {
        super(application);
        ((MoneycimApp)getApplication()).getAppComponent().inject(this);

        spendings = getSpendings();
    }

    public LiveData<List<Spending>> getSpendings(){
        return spendingRepository.getSpendings();
    }

    public void deleteSpending(int id){
        spendingRepository.deleteSpending(id);
    }
}
