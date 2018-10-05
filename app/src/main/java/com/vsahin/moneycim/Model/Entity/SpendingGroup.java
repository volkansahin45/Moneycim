package com.vsahin.moneycim.Model.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Volkan Şahin on 18.08.2017.
 */

@Entity(tableName = "spending_group")
public class SpendingGroup {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "group_id")
    private int groupId;

    @ColumnInfo(name = "group_name")
    private String groupName;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
