package com.example.placementapp.ui.student;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.placementapp.R;
import com.example.placementapp.ui.staff.AddStaffBottomSheetFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddStudentBottomSheetFragment extends BottomSheetDialogFragment {

    AddStaffBottomSheetFragment.AddStaffBottomSheetInterface clickIntrfc = null;
    public AddStudentBottomSheetFragment(AddStaffBottomSheetFragment.AddStaffBottomSheetInterface intrfc) {
        clickIntrfc = intrfc;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_staff_bottom_sheet_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView btnAddStaff = (TextView) view.findViewById(R.id.btnAddStaff);
        TextInputEditText staffEmail = (TextInputEditText)view.findViewById(R.id.staffEmilTextInputEditText);
        TextInputLayout staffEmailLayout = (TextInputLayout)view.findViewById(R.id.staffEmailTextInputLayout);
        TextInputEditText staffPassword = (TextInputEditText)view.findViewById(R.id.staffPwdTextInputEditText);
        TextInputLayout staffPasswordLayout = (TextInputLayout)view.findViewById(R.id.staffPasswordTextInputLayout);
        TextInputEditText staffName = (TextInputEditText)view.findViewById(R.id.staffNameTextInputEditText);
        TextInputLayout staffNameLayout = (TextInputLayout)view.findViewById(R.id.staffNameTextInputLayout);

        btnAddStaff.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!TextUtils.isEmpty(staffEmail.getText().toString()) && Patterns.EMAIL_ADDRESS.matcher(staffEmail.getText().toString().trim()).matches()){
                    if(!TextUtils.isEmpty(staffPassword.getText().toString())){
                        if(!TextUtils.isEmpty(staffName.getText().toString())){
                            clickIntrfc.addStaffClick(staffEmail.getText().toString().trim(),staffPassword.getText().toString().trim(),staffName.getText().toString().trim());
                        }
                    }else{
                        staffPasswordLayout.setError(requireContext().getText(R.string.txt_password_required));
                    }
                }else{
                    staffEmailLayout.setError(requireContext().getText(R.string.txt_invalid_mail_id));
                }

            }
        });
    }

    public interface AddStaffBottomSheetInterface{
        public void  addStaffClick(String email,String pwd,String staffName);
    }
}

