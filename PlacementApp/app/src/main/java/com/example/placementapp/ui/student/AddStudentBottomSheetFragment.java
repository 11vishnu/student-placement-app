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

    AddStudentBottomSheetInterface clickIntrfc = null;
    public AddStudentBottomSheetFragment(AddStudentBottomSheetInterface intrfc) {
        clickIntrfc = intrfc;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_student_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TextView btnAddStudent = (TextView) view.findViewById(R.id.btnAddStudent);
        TextInputEditText studentEmail = (TextInputEditText)view.findViewById(R.id.studentEmilTextInputEditText);
        TextInputLayout studentEmailLayout = (TextInputLayout)view.findViewById(R.id.studentEmailTextInputLayout);
        TextInputEditText studentPassword = (TextInputEditText)view.findViewById(R.id.studentPwdTextInputEditText);
        TextInputLayout studentPasswordLayout = (TextInputLayout)view.findViewById(R.id.studentPasswordTextInputLayout);
        TextInputEditText studentName = (TextInputEditText)view.findViewById(R.id.studentNameTextInputEditText);
        TextInputLayout studentNameLayout = (TextInputLayout)view.findViewById(R.id.studentNameTextInputLayout);

        btnAddStudent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if((studentEmail.getText() !=null)&&(!TextUtils.isEmpty(studentEmail.getText().toString())) && Patterns.EMAIL_ADDRESS.matcher(studentEmail.getText().toString().trim()).matches()){
                    if((studentPassword.getText()!=null)&&(!TextUtils.isEmpty(studentPassword.getText().toString()))){
                        if((studentName.getText()!=null)&&(!TextUtils.isEmpty(studentName.getText().toString()))){
                            clickIntrfc.addStudentClick(studentEmail.getText().toString().trim(),studentPassword.getText().toString().trim(),studentName.getText().toString().trim());
                        }
                    }else{
                        studentPasswordLayout.setError(requireContext().getText(R.string.txt_password_required));
                    }
                }else{
                    studentEmailLayout.setError(requireContext().getText(R.string.txt_invalid_mail_id));
                }

            }
        });
    }

    public interface AddStudentBottomSheetInterface{
        public void  addStudentClick(String email,String pwd,String studentName);
    }
}

