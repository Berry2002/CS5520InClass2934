package com.example.myapplication.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Interfaces.IconnectToActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FragmentLoginPage extends Fragment implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, buttonOpenRegister;
    private String userEmail;
    private String password;
    private FirebaseAuth mAuth;
    private IconnectToActivity mListener;

    public FragmentLoginPage() {
        // Required empty public constructor
    }

    public static FragmentLoginPage newInstance() {
        FragmentLoginPage fragment = new FragmentLoginPage();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Getting Firebase Authentication instance.....
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater
                .inflate(R.layout.fragment_login_page, container, false);

        editTextEmail = rootView.findViewById(R.id.editTextLoginEmail);
        editTextPassword = rootView.findViewById(R.id.editTextLoginPassword);
        buttonLogin =rootView.findViewById(R.id.buttonLogin);
        buttonOpenRegister = rootView.findViewById(R.id.buttonOpenRegister);

        buttonLogin.setOnClickListener(this);
        buttonOpenRegister.setOnClickListener(this);

        return rootView;
    }
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonLogin){
            userEmail = editTextEmail.getText().toString().trim();
            password = editTextPassword.getText().toString().trim();
            if(userEmail.equals("")){
                editTextEmail.setError("Must input email!");
            }
            if(password.equals("")){
                editTextPassword.setError("Password must not be empty!");
            }
            if(!userEmail.equals("") && !password.equals("")){
//                    Sign in to the account....
                mAuth.signInWithEmailAndPassword(userEmail,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(getContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Login Failed!"+e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    mListener.populateMainFragment(mAuth.getCurrentUser());
                                }
                            }
                        })
                ;
            }

        } else if(view.getId()== R.id.buttonOpenRegister){
            mListener.populateRegisterFragment();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IconnectToActivity) {
            this.mListener = (IconnectToActivity) context;
        }
        else {
            throw new IllegalStateException(context.toString()
                    + "must implement the right interface.");
        }
    }
}