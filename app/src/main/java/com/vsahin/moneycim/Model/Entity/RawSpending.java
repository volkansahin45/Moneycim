package com.vsahin.moneycim.Model.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.vsahin.moneycim.Model.Database.Converters;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Volkan Şahin on 18.08.2017.
 */

@Entity(tableName = "spending")
public class RawSpending implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ForeignKey(entity = SpendingGroup.class, parentColumns = "group-id", childColumns = "group-id")
    @ColumnInfo(name = "group_id")
    private int groupId;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "quantity")
    private Double quantity;

    @ColumnInfo(name = "description")
    private String description;

    public int getId() {
        return id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
