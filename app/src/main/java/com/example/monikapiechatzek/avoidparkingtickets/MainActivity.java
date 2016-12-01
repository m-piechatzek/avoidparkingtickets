package com.example.monikapiechatzek.avoidparkingtickets;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.example.monikapiechatzek.avoidparkingtickets.R.string.ticketter_spotted;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;

    Button register_button;
    Button login_button;

    EditText register_email;
    EditText register_password;
    EditText login_email;
    EditText login_password;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null)
        {
            Intent mainPage = new Intent(getApplicationContext(), LocationFinder.class);
            startActivity(mainPage);
        }

        progressDialog = new ProgressDialog(this);

        register_button = (Button) findViewById(R.id.register_button);
        login_button = (Button) findViewById(R.id.login_button);

        register_email = (EditText) findViewById(R.id.register_email_editText);
        register_password = (EditText) findViewById(R.id.register_password_editText);
        login_email = (EditText) findViewById(R.id.login_email_EditText);
        login_password = (EditText) findViewById(R.id.login_password_editText);


        register_button.setOnClickListener(this);
        login_button.setOnClickListener(this);
    }

    private void registerUser()
    {
        String email = register_email.getText().toString().trim();
        String password = register_password.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter an email!", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter a password!", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("User is being registered, please wait!");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    //TODO: get haytham to explain what this is, is scope involved?
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //if task successful
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Registered successfully!", Toast.LENGTH_LONG).show();
                    //TODO: do i need the method finish() here?
                    Intent mainPage = new Intent(getApplicationContext(), LocationFinder.class);
                    startActivity(mainPage);

                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Failed to register!", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    private void userLogin()
    {
        String email = login_email.getText().toString().trim();
        String password = login_password.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter an email!", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter a password!", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("User is being registered, please wait!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new  OnCompleteListener<AuthResult>() {
                    @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Registered successfully!", Toast.LENGTH_LONG).show();

                            Intent mainPage = new Intent(getApplicationContext(), LocationFinder.class);
                            startActivity(mainPage);

                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Failed to register!", Toast.LENGTH_LONG).show();

                        }
        }

        });
    }

    @Override
    public void onClick(View view) {

        if(view == register_button)
        {
            registerUser();
        }
        if(view == login_button)
        {
            userLogin();

        }

    }
}