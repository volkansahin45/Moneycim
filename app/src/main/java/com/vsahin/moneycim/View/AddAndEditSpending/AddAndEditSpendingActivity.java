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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAndEditSpendingActivity extends BaseActivity {
    private static final int RC_OCR_CAPTURE = 3;

    @BindView(R.id.group_spinner)
    Spinner groupSpinner;

    @BindView(R.id.save_button)
    ImageButton saveButton;

    @BindView(R.id.quantity_edittext)
    EditText quantityEditText;

    @BindView(R.id.description_edittext)
    EditText descriptionEditText;

    @BindView(R.id.title_textView)
    TextView titleTextView;

    @BindView(R.id.camera_button)
    ImageButton cameraButton;

    @BindView(R.id.delete_button)
    ImageButton deleteButton;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private AddAndEditSpendingViewModel viewModel;
    private RawSpending spending;
    private ArrayList<SpendingGroup> spendingGroupList;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show back button
        viewModel = ViewModelProviders.of(this).get(AddAndEditSpendingViewModel.class);
        subscribeSpendingGroups();

        Bundle bundle =  getIntent().getExtras();

        if (bundle == null || bundle.isEmpty()) {
            spending = new RawSpending();
        } else {
            spending = (RawSpending) bundle.getSerializable("spending");
            deleteButton.setVisibility(View.VISIBLE);
        }
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

    private void subscribeSpendingGroups() {
        viewModel.spendingGroups.observe(this, new Observer<List<SpendingGroup>>() {
            @Override
            public void onChanged(@Nullable List<SpendingGroup> spendingGroups) {
                spendingGroupList = (ArrayList<SpendingGroup>) spendingGroups;
                fillSpinner(groupSpinner, spendingGroupList);
                if(spending.getId() != 0){
                    setTakenSpendingDataToFormElements();
                }
            }
        });
    }

    @OnClick(R.id.save_button)
    public void save(){
        int groupId = groupSpinner.getSelectedItemPosition();
        String quantityString = quantityEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        Date date;
        if(spending.getDate() == null) {
            date = new Date(System.currentTimeMillis());
        } else {
            date = spending.getDate();
        }

        if(groupId == 0){
            showToast("Please Select Group");
            return;
        }

        if(quantityString.equals("")) {
            showToast("Please write correct quantity");
            return;
        }

        fillSpending(groupId, Double.valueOf(quantityString), description, date );
        addSpending(spending);
        showToast("Successfully Saved");
        finish();
    }

    private void addSpending(RawSpending s){
        viewModel.addSpending(s);
    }

    private void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.camera_button)
    public void openOcrCaptureActivity(){
        Intent intent = new Intent(this, OcrCaptureActivity.class);
        startActivityForResult(intent, RC_OCR_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    String text = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
                    text = trimQuantity(text);
                    quantityEditText.setText(text);
                }
            } else {
                showToast("Error");
            }
        }
    }

    private String trimQuantity(String text){
        int lastIndexOfLine = text.lastIndexOf("\n");
        if (lastIndexOfLine != -1) {
            text = text.substring(text.lastIndexOf("\n"));
        }
        text = text.replace(" ", "");
        text = text.replace(",", ".");
        text = text.replaceAll("[^\\d.]", "");

        return text;
    }

    @OnClick(R.id.delete_button)
    public void deleteSpending(){
        viewModel.deleteSpending(spending.id);
        finish();
    }

    private void fillSpending(int groupId, double quantity, String description, Date date){
        spending.setGroupId(groupId);
        spending.setQuantity(quantity);
        spending.setDescription(description);
        spending.setDate(date);
    }

    private void fillSpinner(Spinner groupSpinner, List<SpendingGroup> spendingGroups){
        List<String> stringGroups = new ArrayList<>();
        stringGroups.add("Please Select Group");
        for (SpendingGroup group: spendingGroups) {
            stringGroups.add(group.getGroupName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stringGroups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(adapter);
    }

    private void setTakenSpendingDataToFormElements(){
        titleTextView.setText(R.string.edit_a_spending);
        quantityEditText.setText(String.valueOf(spending.getQuantity()));
        descriptionEditText.setText(spending.getDescription());
        groupSpinner.setSelection(spending.getGroupId());

    }
}
