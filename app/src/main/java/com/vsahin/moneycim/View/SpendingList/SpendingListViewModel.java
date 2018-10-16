package com.vsahin.moneycim.View.SpendingList;

import com.vsahin.moneycim.Model.Pojo.Spending;
import com.vsahin.moneycim.Model.Repository.SpendingRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Volkan Şahin on 17.08.2017.
 */

public class SpendingListViewModel extends ViewModel {
    SpendingRepository spendingRepository;

    final LiveData<List<Spending>> spendings;

    @Inject
    public SpendingListViewModel(SpendingRepository spendingRepository) {
        this.spendingRepository = spendingRepository;
        spendings = getSpendings();
    }

    private LiveData<List<Spending>> getSpendings(){
        return spendingRepository.getSpendings();
    }

    public void deleteSpending(int id){
        spendingRepository.deleteSpending(id);
    }
}
