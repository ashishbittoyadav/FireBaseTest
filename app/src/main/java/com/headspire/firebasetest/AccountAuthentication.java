package com.headspire.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class AccountAuthentication extends AppCompatActivity implements View.OnClickListener
{
    private FirebaseAuth firebaseAuth;
    private TextView email;
    private TextView password;
    private Button sign_up;
    private TextView status;
    private Button sign_in;
    private TextView forget_password;
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_authentication);
        email=findViewById(R.id.useremail);
        password=findViewById(R.id.userpassword);
        sign_up=findViewById(R.id.signup);
        sign_in=findViewById(R.id.signin);
        status=findViewById(R.id.userstatus);
        forget_password=findViewById(R.id.forgetPassword);
        firebaseAuth=FirebaseAuth.getInstance();
        sign_up.setOnClickListener(this);
        forget_password.setOnClickListener(this);
        sign_in.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.signin:
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    Intent intent=new Intent(getApplicationContext(),AuthStateChange.class);
                                    intent.putExtra("email",email.getText().toString());
                                    startActivity(intent);
                                }
                                else
                                    status.setText("not connected.");
                            }
                        });
                break;
            case R.id.signup:
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),
                        password.getText().toString())
                        .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    status.setText("user created.");
                                }
                                else
                                    status.setText("something goes wrong");
                            }
                        });
                break;
            case R.id.forgetPassword:
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container,new ResetPassword())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}
