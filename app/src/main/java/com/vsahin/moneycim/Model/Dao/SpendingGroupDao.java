package com.vsahin.moneycim.Model.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.vsahin.moneycim.Model.Entity.SpendingGroup;

import java.util.List;

/**
 * Created by Volkan Şahin on 18.08.2017.
 */

@Dao
public interface SpendingGroupDao {

    @Query("SELECT * FROM SPENDING_GROUP")
    LiveData<List<SpendingGroup>> getAllSpendingGroups();

    @Insert
    void addSpendingGroup(SpendingGroup spendingGroup);
}
