package com.vsahin.moneycim.View.AddAndEditSpending;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.vsahin.moneycim.Model.Entity.RawSpending;
import com.vsahin.moneycim.Model.Entity.SpendingGroup;
import com.vsahin.moneycim.Model.Pojo.Spending;
import com.vsahin.moneycim.R;
import com.vsahin.moneycim.View.Base.BaseActivity;
import com.vsahin.moneycim.View.OCR_Scan_Receipt.OcrCaptureActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
