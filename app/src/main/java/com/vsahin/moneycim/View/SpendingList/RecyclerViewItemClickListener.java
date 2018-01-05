package com.vsahin.moneycim.View.SpendingList;

import com.vsahin.moneycim.Model.Entity.RawSpending;

/**
 * Created by Volkan Şahin on 1.09.2017.
 */

public interface RecyclerViewItemClickListener {
    void onItemClick(RawSpending clickedSpending);
    void onItemLongClick(int longClickedSpendingId);
}
