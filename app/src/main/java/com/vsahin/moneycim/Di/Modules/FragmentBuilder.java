package com.vsahin.moneycim.Di.Modules;

import com.vsahin.moneycim.Di.Scope.FragmentScope;
import com.vsahin.moneycim.View.AddAndEditSpending.AddAndEditSpendingFragment;
import com.vsahin.moneycim.View.SpendingList.SpendingListFragment;
import com.vsahin.moneycim.View.TotalSpendingQuantity.TotalSpendingQuantityFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilder {

    @FragmentScope
    @ContributesAndroidInjector
    abstract SpendingListFragment spendingListFragment();

    @FragmentScope
    @ContributesAndroidInjector
    abstract AddAndEditSpendingFragment addAndEditSpendingFragment();

    @FragmentScope
    @ContributesAndroidInjector
    abstract TotalSpendingQuantityFragment totalSpendingQuantityFragment();
}
