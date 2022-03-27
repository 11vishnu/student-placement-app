package com.example.placementapp.ui.student;

import static com.example.placementapp.constants.AppConstants.CONST_VAL_STAFF_TYPE;
import static com.example.placementapp.constants.AppConstants.CONST_VAL_STUDENT_TYPE;
import static com.example.placementapp.constants.AppConstants.USER;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.placementapp.databinding.FragmentGalleryBinding;
import com.example.placementapp.databinding.FragmentStudentBinding;
import com.example.placementapp.ui.company.ClickListener;
import com.example.placementapp.ui.company.Company;
import com.example.placementapp.ui.dataModels.User;
import com.example.placementapp.ui.staff.AddStaffBottomSheetFragment;
import com.example.placementapp.ui.staff.Staff;
import com.example.placementapp.ui.staff.StaffListAdapter;
import com.example.placementapp.ui.staff.StaffViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentFragment extends Fragment implements AddStudentBottomSheetFragment.AddStudentBottomSheetInterface {

    private StudentViewModel StudentViewModel;
    private FragmentStudentBinding binding;

    private RecyclerView recyclerView;
    private StudentListAdapter studentListAdapter;
    private List<Student> studentList = new ArrayList();

    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseUserReference;

    private String TAG = "StudentFragment";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StudentViewModel =
                new ViewModelProvider(this).get(StudentViewModel.class);

        binding = FragmentStudentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();

        final TextView textView = binding.textGallery;
        StudentViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        setHasOptionsMenu(false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        studentList = new ArrayList<>();
        studentListAdapter = new StudentListAdapter(studentList);
        binding.recycleViewStudent.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.recycleViewStudent.setLayoutManager(layoutManager);
        binding.recycleViewStudent.setItemAnimator(new DefaultItemAnimator());
        binding.recycleViewStudent.setAdapter(studentListAdapter);


      /* studentListAdapter.setOnItemClickListener(new ClickListener<String Staff >(){
            @Override
            public void onClick(View view, Staff data, int position) {
                Toast.makeText(requireContext(),"Position = "+position+"\n Item = "+data.getCompanyName(),Toast.LENGTH_SHORT).show();
        });*/

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseUserReference = firebaseDatabase.getReference().child(USER);

        prepareItems();


        binding.fabAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddStudentBottomSheet();
            }
        });

    }

    private void openAddStudentBottomSheet() {
        AddStudentBottomSheetFragment bottomSheet = new AddStudentBottomSheetFragment(this);
        bottomSheet.show(getChildFragmentManager(), "StudentFragment");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareItems(){
        studentList.add(new Student("Staff a","CSE","staffa@gmail.com"));
        studentList.add(new Student("Staff b","EEE","staffB@gmail.com"));
        studentList.add(new Student("Staff C","ECE","staffC@gmail.com"));
        studentList.add(new Student("Staff D","MECH","staffd@gmail.com"));
        studentList.add(new Student("Staff E","Nanotech","staffe@gmail.com"));
        studentListAdapter.notifyDataSetChanged();
    }

    @Override
    public void addStudentClick(String email, String pwd, String studentName) {
        Toast.makeText(requireContext(), email+ "    "+pwd, Toast.LENGTH_SHORT).show();

        mAuth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();

                            FirebaseUser staffUser = task.getResult().getUser();
                            Log.d(TAG, "onComplete: uid=" + staffUser.getUid());

                            User userStaff = new User(CONST_VAL_STUDENT_TYPE,studentName,email);

                            Map<String, Object> childUpdates = new HashMap<>();
                            Map<String, Object> postValues = userStaff.toMap();
                            updateStudentData(postValues,staffUser);

                            // pushStaffData();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void updateStudentData(Map<String, Object> postValues,FirebaseUser user) {
        firebaseDatabase.getReference().child(USER).child(user.getUid()).updateChildren(postValues, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(requireContext(), "user info successfully inserted", Toast.LENGTH_SHORT).show();
            }
        });
    }
}