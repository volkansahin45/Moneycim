package com.vsahin.moneycim.View.SpendingList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vsahin.moneycim.Model.Entity.RawSpending;
import com.vsahin.moneycim.Model.Pojo.Spending;
import com.vsahin.moneycim.R;
import com.vsahin.moneycim.View.AddAndEditSpending.AddAndEditSpendingFragment;
import com.vsahin.moneycim.View.Base.BaseFragment;
import com.vsahin.moneycim.View.MainActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by Volkan Şahin on 17.08.2017.
 */

public class SpendingListFragment extends BaseFragment implements RecyclerViewItemClickListener {

    @Inject
    SpendingListViewModel viewModel;

    private final ArrayList<Spending> spendingList = new ArrayList<>();
    private SpendingRecyclerViewAdapter adapter;
    private StaggeredGridLayoutManager layoutManager;

    @BindView(R.id.spending_recyclerview)
    RecyclerView spendingRecyclerView;

    public static SpendingListFragment newInstance() {
        return new SpendingListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
        subscribeSpendings();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spending_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        adapter = new SpendingRecyclerViewAdapter(getActivity(), spendingList, this);

        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        spendingRecyclerView.setAdapter(adapter);
        spendingRecyclerView.setLayoutManager(layoutManager);

        return view;
    }

    private void subscribeSpendings() {
        viewModel.spendings.observe(this, new Observer<List<Spending>>() {
            @Override
            public void onChanged(final List<Spending> spendings) {
                adapter.updateItems(spendings);
            }
        });
    }

    @Override
    public void onItemClick(RawSpending clickedSpending) {
        ((MainActivity)getActivity()).showFragment(AddAndEditSpendingFragment.newInstance(clickedSpending));
    }

    @Override
    public void onItemLongClick(int longClickedSpendingId) {
        viewModel.deleteSpending(longClickedSpendingId);
    }
}
