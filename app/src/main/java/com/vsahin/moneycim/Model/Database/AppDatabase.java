package com.vsahin.moneycim.Model.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.vsahin.moneycim.Model.Dao.SpendingDao;
import com.vsahin.moneycim.Model.Dao.SpendingGroupDao;
import com.vsahin.moneycim.Model.Entity.RawSpending;
import com.vsahin.moneycim.Model.Entity.SpendingGroup;

/**
 * Created by Volkan Şahin on 17.08.2017.
 */

@Database(version = 1, entities = {RawSpending.class, SpendingGroup.class})
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase{

    public abstract SpendingDao spendingDao();
    public abstract SpendingGroupDao spendingGroupDao();

}
