package com.vsahin.moneycim.Di;

import com.vsahin.moneycim.View.AddAndEditSpending.AddAndEditSpendingViewModel;
import com.vsahin.moneycim.View.SpendingList.SpendingListViewModel;
import com.vsahin.moneycim.View.TotalSpendingQuantity.TotalSpendingQuantityViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Volkan Şahin on 18.08.2017.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(SpendingListViewModel spendingListViewModel);
    void inject(TotalSpendingQuantityViewModel totalSpendingQuantityViewModel);
    void inject(AddAndEditSpendingViewModel addSpendingViewModel);
}
