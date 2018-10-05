package com.vsahin.moneycim.Model.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.vsahin.moneycim.Model.Database.AppDatabase;
import com.vsahin.moneycim.Model.Entity.RawSpending;
import com.vsahin.moneycim.Model.Entity.SpendingGroup;
import com.vsahin.moneycim.Model.Pojo.Spending;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Volkan Şahin on 17.08.2017.
 */

@Singleton
public class SpendingRepository {
    private final AppDatabase appDatabase;
    @Inject
    public SpendingRepository(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    public LiveData<List<Spending>> getSpendings(){
        return appDatabase.spendingDao().getSpendingsWithGroups();
    }

    public LiveData<Double> getTotalSpendingQuantity(){
        return appDatabase.spendingDao().getTotalSpendingQuantity();
    }

    public void addSpending(RawSpending s){
        appDatabase.spendingDao().addSpending(s);
    }

    public LiveData<List<SpendingGroup>> getSpendingGroups(){
        return appDatabase.spendingGroupDao().getAllSpendingGroups();
    }

    public void deleteSpending(int id){
        appDatabase.spendingDao().deleteSpending(id);
    }
}
