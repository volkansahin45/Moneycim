package com.vsahin.moneycim.Di.Modules

import androidx.lifecycle.ViewModel
import com.vsahin.moneycim.Di.ViewModelKey
import com.vsahin.moneycim.View.AddAndEditSpending.AddAndEditSpendingViewModel
import com.vsahin.moneycim.View.SpendingList.SpendingListViewModel
import com.vsahin.moneycim.View.TotalSpendingQuantity.TotalSpendingQuantityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SpendingListViewModel::class)
    abstract fun bindSpendingListViewModel(viewModel: SpendingListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddAndEditSpendingViewModel::class)
    abstract fun bindAddAndEditSpendingViewModel(viewModel: AddAndEditSpendingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TotalSpendingQuantityViewModel::class)
    abstract fun bindTotalSpendingQuantityViewModel(viewModel: TotalSpendingQuantityViewModel): ViewModel
}