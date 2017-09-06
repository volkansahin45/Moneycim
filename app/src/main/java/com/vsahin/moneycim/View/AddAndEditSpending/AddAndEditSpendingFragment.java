package com.vsahin.moneycim.View.AddAndEditSpending;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.vsahin.moneycim.Model.Entity.RawSpending;
import com.vsahin.moneycim.Model.Entity.SpendingGroup;
import com.vsahin.moneycim.R;
import com.vsahin.moneycim.View.MainActivity;
import com.vsahin.moneycim.View.OCR_Scan_Receipt.OcrCaptureActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Volkan Şahin on 27.08.2017.
 */

public class AddAndEditSpendingFragment extends LifecycleFragment implements View.OnFocusChangeListener{
    private static final int RC_OCR_CAPTURE = 3;

    @BindView(R.id.container)
    NestedScrollView container;

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

    AddAndEditSpendingViewModel viewModel;
    RawSpending spending;
    View view;
    ArrayList<SpendingGroup> spendingGroupList;

    public static AddAndEditSpendingFragment newInstance() {
        return new AddAndEditSpendingFragment();
    }

    public static AddAndEditSpendingFragment newInstance(RawSpending spending){
        Bundle bundle = new Bundle();
        bundle.putSerializable("spending", spending);
        AddAndEditSpendingFragment fragment = new AddAndEditSpendingFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_and_edit_spending, container, false);
        ButterKnife.bind(this, view);
        quantityEditText.setOnFocusChangeListener(this);
        descriptionEditText.setOnFocusChangeListener(this);

        Bundle bundle = this.getArguments();

        if (bundle == null || bundle.isEmpty()) {
            spending = new RawSpending();
        } else {
            spending = (RawSpending) bundle.getSerializable("spending");
            deleteButton.setVisibility(View.VISIBLE);
        }

        setRetainInstance(true);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(AddAndEditSpendingViewModel.class);
        subscribeSpendingGroups();
        if(spendingGroupList != null){
            fillSpinner(groupSpinner, spendingGroupList);
        }
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

    public void setTakenSpendingDataToFormElements(){
        titleTextView.setText(R.string.edit_a_spending);
        quantityEditText.setText(String.valueOf(spending.getQuantity()));
        descriptionEditText.setText(spending.getDescription());
        groupSpinner.setSelection(spending.getGroupId());

    }

    @OnClick(R.id.save_button)
    public void save(){
        int groupId = groupSpinner.getSelectedItemPosition();
        String quantityString = quantityEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        Date date;
        if(spending.getDate() == null)
        {
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
        hideKeyboard(container);
        showToast("Successfully Saved");
        getActivity().onBackPressed();
    }

    @OnClick(R.id.camera_button)
    public void openOcrCaptureActivity(){
        Intent intent = new Intent(getActivity(), OcrCaptureActivity.class);
        startActivityForResult(intent, RC_OCR_CAPTURE);
    }

    @OnClick(R.id.delete_button)
    public void deleteSpending(){
        deleteSpending(spending.id);
        getActivity().onBackPressed();
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
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String  trimQuantity(String text){
        int lastIndexOfLine = text.lastIndexOf("\n");
        if (lastIndexOfLine != -1) {
            text = text.substring(text.lastIndexOf("\n"));
        }
        text = text.replace(" ", "");
        text = text.replace(",", ".");
        text = text.replaceAll("[^\\d.]", "");

        return text;
    }

    public void addSpending(RawSpending s){
        viewModel.addSpending(s);
    }

    public void deleteSpending(int id){
        viewModel.deleteSpending(id);
    }

    public void fillSpending(int groupId, double quantity, String description, Date date){
        spending.setGroupId(groupId);
        spending.setQuantity(quantity);
        spending.setDescription(description);
        spending.setDate(date);
    }

    public void fillSpinner(Spinner groupSpinner, List<SpendingGroup> spendingGroups){
        List<String> stringGroups = new ArrayList<>();
        stringGroups.add("Please Select Group");
        for (SpendingGroup group: spendingGroups) {
            stringGroups.add(group.getGroupName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, stringGroups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(adapter);
    }

    @Override
    public void onFocusChange(View view, boolean isFocused) {
        if(!isFocused){
            hideKeyboard(view);
        }
    }

    public void showToast(String text){
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void hideKeyboard(View v){
        ((MainActivity)getActivity()).hideKeyboard(v);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
    }
}
