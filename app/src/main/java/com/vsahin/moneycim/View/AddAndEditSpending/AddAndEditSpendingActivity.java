package com.vsahin.moneycim.View.AddAndEditSpending;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.vsahin.moneycim.Model.Entity.RawSpending;
import com.vsahin.moneycim.R;
import com.vsahin.moneycim.View.Base.BaseActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAndEditSpendingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    RawSpending spending;

    public static Intent newIntent(Context context, RawSpending extra){
        final String KEY = "spending";

        Intent intent = new Intent(context, AddAndEditSpendingActivity.class);
        intent.putExtra(KEY, extra);
        return intent;
    }

    public static Intent newIntent(Context context){
        return new Intent(context, AddAndEditSpendingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_edit_spending);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

        if (bundle == null || bundle.isEmpty()) {
            spending = new RawSpending();
        } else {
            spending = (RawSpending) bundle.getSerializable("spending");
        }

        showRootFragment(AddAndEditSpendingFragment.newInstance(spending));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
