package com.vsahin.moneycim.View.AddAndEditSpending;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.material.snackbar.Snackbar;
import com.vsahin.moneycim.Model.Entity.RawSpending;
import com.vsahin.moneycim.Model.Entity.SpendingGroup;
import com.vsahin.moneycim.R;
import com.vsahin.moneycim.View.Base.BaseFragment;
import com.vsahin.moneycim.View.OCR_Scan_Receipt.OcrCaptureActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

public class AddAndEditSpendingFragment extends BaseFragment {

    private static final int RC_OCR_CAPTURE = 3;

    @BindView(R.id.root_view)
    ConstraintLayout rootView;

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

    @Inject
    AddAndEditSpendingViewModel viewModel;

    private RawSpending spending;
    private ArrayList<SpendingGroup> spendingGroupList;
    private AlertDialog deleteConfirmDialog;

    public static AddAndEditSpendingFragment newInstance(RawSpending spending) {

        Bundle args = new Bundle();
        args.putSerializable("spending", spending);
        AddAndEditSpendingFragment fragment = new AddAndEditSpendingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_and_edit_spending, container, false);
        unbinder = ButterKnife.bind(this, view);

        spending = (RawSpending) getArguments().getSerializable("spending");

        if (spending == null) {
            spending = new RawSpending();
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
        subscribeSpendingGroups();
    }

    private void subscribeSpendingGroups() {
        viewModel.spendingGroups.observe(this, spendingGroups -> {
            spendingGroupList = (ArrayList<SpendingGroup>) spendingGroups;
            fillSpinner(groupSpinner, spendingGroupList);
            if(spending.getId() != 0){
                setTakenSpendingDataToFormElements();
                deleteButton.setVisibility(View.VISIBLE);
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
            showSnackBar("Please Select Group");
            return;
        }

        if(quantityString.equals("")) {
            showSnackBar("Please write correct quantity");
            return;
        }

        fillSpending(groupId, Double.valueOf(quantityString), description, date );
        addSpending(spending);
        showSnackBar("Successfully Saved");
        getActivity().onBackPressed();
    }

    private void addSpending(RawSpending s){
        viewModel.addSpending(s);
    }

    private void showSnackBar(String text){
        Snackbar.make(rootView, text, Snackbar.LENGTH_SHORT)
                .setAction("OK", v -> { })
                .show();
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
        askForDelete();
    }

    private void askForDelete()
    {
        if(deleteConfirmDialog == null){
            Resources stringResources  = getResources();
            String title = stringResources.getString(R.string.delete);
            String message = stringResources.getString(R.string.delete_message);
            String cancel = stringResources.getString(R.string.cancel);

            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        viewModel.deleteSpending(spending.id);
                        dialog.dismiss();
                        getActivity().onBackPressed();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            };

            deleteConfirmDialog = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setIcon(R.drawable.icon_delete)
                .setPositiveButton(title, dialogClickListener)
                .setNegativeButton(cancel, dialogClickListener)
                .create();
        }

        deleteConfirmDialog.show();
    }

    @OnClick(R.id.camera_button)
    public void openOcrCaptureActivity(){
        Intent intent = new Intent(getActivity(), OcrCaptureActivity.class);
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
                showSnackBar("Error");
            }
        }
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
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stringGroups);
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
