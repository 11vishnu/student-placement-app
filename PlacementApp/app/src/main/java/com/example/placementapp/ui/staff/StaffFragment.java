package com.example.placementapp.ui.staff;

import static com.example.placementapp.constants.AppConstants.CONST_FINAL_STAFF_TYPE;
import static com.example.placementapp.constants.AppConstants.CONST_VAL_STAFF_TYPE;
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

import com.example.placementapp.R;
import com.example.placementapp.constants.AppConstants;
import com.example.placementapp.databinding.FragmentGalleryBinding;
import com.example.placementapp.ui.company.ClickListener;
import com.example.placementapp.ui.company.Company;
import com.example.placementapp.ui.dataModels.User;
import com.example.placementapp.ui.dataModels.UserType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffFragment extends Fragment implements AddStaffBottomSheetFragment.AddStaffBottomSheetInterface {

    private StaffViewModel StaffViewModel;
    private FragmentGalleryBinding binding;

    private RecyclerView recyclerView;
    private StaffListAdapter staffListAdapter;
    private List<Staff> staffList = new ArrayList();

    private FirebaseAuth mAuth;
    private String TAG = "StaffFragment";

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseUserReference;
    DatabaseReference databaseUidReference;

    private User user = null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StaffViewModel =
                new ViewModelProvider(this).get(StaffViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        StaffViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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

        staffList = new ArrayList<>();
        staffListAdapter = new StaffListAdapter(staffList);
        binding.recycleViewStaff.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.recycleViewStaff.setLayoutManager(layoutManager);
        binding.recycleViewStaff.setItemAnimator(new DefaultItemAnimator());
        binding.recycleViewStaff.setAdapter(staffListAdapter);

        mAuth = FirebaseAuth.getInstance();

      /* staffListAdapter.setOnItemClickListener(new ClickListener<String Staff >(){
            @Override
            public void onClick(View view, Staff data, int position) {
                Toast.makeText(requireContext(),"Position = "+position+"\n Item = "+data.getCompanyName(),Toast.LENGTH_SHORT).show();



        });*/

        FloatingActionButton fabAddStaff = view.findViewById(R.id.fabAddStaff);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseUserReference = firebaseDatabase.getReference().child(USER);



        fabAddStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              openAddStaffBottomSheet();
            }
        });

        prepareItems();
    }

    private void openAddStaffBottomSheet() {
        AddStaffBottomSheetFragment bottomSheet = new AddStaffBottomSheetFragment(this);
        bottomSheet.show(getChildFragmentManager(), "StaffFragment");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareItems(){
        staffList.add(new Staff("Staff a","CSE","staffa@gmail.com"));
        staffList.add(new Staff("Staff b","EEE","staffB@gmail.com"));
        staffList.add(new Staff("Staff C","ECE","staffC@gmail.com"));
        staffList.add(new Staff("Staff D","MECH","staffd@gmail.com"));
        staffList.add(new Staff("Staff E","Nanotech","staffe@gmail.com"));
        staffListAdapter.notifyDataSetChanged();
    }

    @Override
    public void addStaffClick(String email, String pwd,String staffName) {
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

                            User userStaff = new User(CONST_VAL_STAFF_TYPE,staffName,email);

                            Map<String, Object> childUpdates = new HashMap<>();
                            Map<String, Object> postValues = userStaff.toMap();
                            updateStaffData(postValues,staffUser);

                           // pushStaffData();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
       });
    }

    private void updateStaffData(Map<String, Object> postValues,FirebaseUser user) {
        firebaseDatabase.getReference().child(USER).child(user.getUid()).updateChildren(postValues, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(requireContext(), "user info successfully inserted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pushStaffData(String uid) {

        databaseUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseUserReference.setValue(uid);
                // after adding this data we are showing toast message.
                Toast.makeText(requireActivity(), "data added", Toast.LENGTH_SHORT).show();
                databaseUidReference = firebaseDatabase.getReference();
               /* user.UserTypeConst(AppConstants.CONST_VAL_STAFF_TYPE);
                databaseUserReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // inside the method of on Data change we are setting
                        // our object class to our database reference.
                        // data base reference will sends data to firebase.
                        databaseUserReference.setValue(user);
                        // after adding this data we are showing toast message.
                        Toast.makeText(requireActivity(), "data added", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // if the data is not added or it is cancelled then
                        // we are displaying a failure toast message.
                        Toast.makeText(requireContext(), "Fail to add data " + error, Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(requireContext(), "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });


    }

}