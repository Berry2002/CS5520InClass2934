package com.example.myapplication.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.Interfaces.IconnectToActivity;
import com.example.myapplication.Models.User;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class FragmentRegisterPage extends Fragment implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private EditText editTextFirstName, editTextLastName, editTextEmail, editTextPassword, editTextRepPassword;
    private Button buttonRegister;
    private String fname, lname, fullname, email, password, rep_password;
    private IconnectToActivity mListener;

    public FragmentRegisterPage() {
        // Required empty public constructor
    }
    public static FragmentRegisterPage newInstance() {
        FragmentRegisterPage fragment = new FragmentRegisterPage();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IconnectToActivity) {
            mListener = (IconnectToActivity) context;
        }
        else {
            throw new IllegalStateException(context.toString()
                    + "must implement the right interface.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_register_page, container, false);

        editTextFirstName = rootView.findViewById(R.id.editTextRegister_FirstName);
        editTextLastName = rootView.findViewById(R.id.editText_LastName);
        editTextEmail = rootView.findViewById(R.id.editTextRegister_Email);
        editTextPassword = rootView.findViewById(R.id.editTextRegister_Password);
        editTextRepPassword = rootView.findViewById(R.id.editTextRegister_Rep_Password);
        buttonRegister = rootView.findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        this.fname = String.valueOf(editTextFirstName.getText()).trim();
        this.lname = String.valueOf(editTextLastName.getText()).trim();
        this.email = String.valueOf(editTextEmail.getText()).trim();
        this.password = String.valueOf(editTextPassword.getText()).trim();
        this.rep_password = String.valueOf(editTextRepPassword.getText()).trim();

        if(view.getId()== R.id.buttonRegister){
//            Validations........
            if(fname.equals("")){
                editTextFirstName.setError("Must input first name!");
            }if(lname.equals("")){
                editTextLastName.setError("Must input last name!");
            }
            if(email.equals("")){
                editTextEmail.setError("Must input email!");
            }
            if(password.equals("")){
                editTextPassword.setError("Password must not be empty!");
            }
            if(!rep_password.equals(password)){
                editTextRepPassword.setError("Passwords must match!");
            }

            fullname = fname + " " + lname;

//            Validation complete.....
            if(!fullname.equals("") && !email.equals("")
                    && !password.equals("")
                    && rep_password.equals(password)){

                User user = new User(fname, lname, email);

                //              Firebase authentication: Create user.......
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    mUser = task.getResult().getUser();

//                                    Adding name to the FirebaseUser...
                                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(fullname)
                                            .build();

                                    mUser.updateProfile(profileChangeRequest)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()) {
                                                        mListener.registerDone(mUser, user);
                                                    }
                                                }
                                            });
                                }
                            }
                        });
            }
        }
    }
}