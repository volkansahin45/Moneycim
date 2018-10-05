package com.vsahin.moneycim.Model.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

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
