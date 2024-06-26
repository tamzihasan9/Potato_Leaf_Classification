package com.example.potatoleaf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    private EditText edit_email,edit_password;
    private TextView SignupButton;
    private Button signinbutton;

     private ProgressBar progressBar;
    CheckBox LoginsavePassword;
    SharedPreferences sharedpreferences;


    private FirebaseAuth mAuth;
    String email, password;
    private FirebaseAuth authcheck = FirebaseAuth.getInstance();

    protected void onStart() {
        super.onStart();

        sharedpreferences = this.getSharedPreferences("pass", Context.MODE_PRIVATE);

        boolean checkpassbox = sharedpreferences.getBoolean("savepass", false);
        if(authcheck.getCurrentUser()!=null && checkpassbox){
            openMain();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        edit_email = findViewById(R.id.signinemailid);
        edit_password = findViewById(R.id.signinpassid);
        progressBar = findViewById(R.id.progressbarid);
        SignupButton = findViewById(R.id.gotoSignupId);

        signinbutton = findViewById(R.id.signIn);
       LoginsavePassword= findViewById(R.id.checkid);






        sharedpreferences= this.getSharedPreferences("pass", Context.MODE_PRIVATE);


        LoginsavePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean loginsave = true;

                if(isChecked){
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean("savepass", loginsave);
                    editor.apply();
                }
            }
        });

        signinbutton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {


                                                    String email = edit_email.getText().toString();
                                                    String password = edit_password.getText().toString();
                                                    if (TextUtils.isEmpty(email)) {
                                                        Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
                                                        return;
                                                    }
                                                    if (TextUtils.isEmpty(password)) {
                                                        Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
                                                        return;
                                                    } else{
                                                        SharedPreferences.Editor editor = sharedpreferences.edit();


                                                        editor.apply();
                                                        Intent i = new Intent(MainActivity.this, HomePage.class);
                                                        startActivity(i);
                                                        finish();

                                                    }
                                                    progressBar.setVisibility(View.VISIBLE);

                                                    mAuth.signInWithEmailAndPassword(email, password)
                                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                                    if (task.isSuccessful()) {
                                                                        Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                                                                        progressBar.setVisibility(View.GONE);


                                                                    } else {


                                                                        Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                                                                        progressBar.setVisibility(View.GONE);
                                                                    }
                                                                }

                                                            });
                                                }









        });
        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });



    }

    private void openMain() {
        startActivity(new Intent(this, HomePage.class));
        finish();
    }

}