package com.headspire.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class AuthStateChange extends AppCompatActivity implements View.OnClickListener{
    private Button logout;
    private TextView loginstatus;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_state_change);
        logout=findViewById(R.id.log_out);
        loginstatus=findViewById(R.id.loginstatus);
        logout.setOnClickListener(this);
        Bundle bundle=getIntent().getExtras();
        loginstatus.setText(bundle.get("email").toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.log_out:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),AccountAuthentication.class));
                break;
        }
    }
}
