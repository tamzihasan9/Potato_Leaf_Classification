package com.example.potatoleaf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_email, edt_password, edt_username;

    private Button signupBtn, pickdate;
    private TextView textView, signintextview;
    private ProgressBar progressBar;

    private DatePickerDialog datePickerDialog;
    private FirebaseAuth mAuth;

    String name, username,DateOfBIRTH;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);


        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbarid);
        signupBtn = findViewById(R.id.signUpButton);
        signintextview = findViewById(R.id.signintextviewid);
        pickdate = findViewById(R.id.idBtnPickDate);
        textView = (TextView) findViewById(R.id.Dateid);

        edt_email = findViewById(R.id.emailId);
        edt_password = findViewById(R.id.passwordId);
        edt_username = findViewById(R.id.usernameId);
        pickdate.setOnClickListener(this);


        signintextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegister();
                Toast.makeText(SignUp.this, "signup", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void userRegister() {

        String email = edt_email.getText().toString();
        String password = edt_password.getText().toString();

        if (email.isEmpty()) {
            edt_email.setError("email required");
            edt_email.requestFocus();
            return;
        } else if (password.isEmpty()) {
            edt_password.setError("password required");
            edt_password.requestFocus();
            return;
        } else if (password.length() < 6) {
            edt_password.setError("password should be at least 6 charecter");
            edt_password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {

                    Toast.makeText(getApplicationContext(), "SignUp is Successful", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    uploadUserData(String.valueOf(user.getUid()));
                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "SignUp Failed", Toast.LENGTH_SHORT).show();


                }
            }


        });


    }

    void uploadUserData(String userID) {


        name = edt_username.getText().toString();
        username = edt_email.getText().toString();
        DateOfBIRTH =  textView.getText().toString();
        HashMap<String, String> user = new HashMap<>();

        user.put("username", name);
        user.put("email", username);
        user.put("DOB",  DateOfBIRTH);
        user.put("phone", "");
        user.put("district", "");
        user.put("upazila", "");


        database.child("users").child(userID).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "User successfully created", Toast.LENGTH_SHORT).show();
//                            openMain();

                        } else {
                            Toast.makeText(SignUp.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUp.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onClick(View v) {

            DatePicker datePicker = new DatePicker(this);

            int currentday = datePicker.getDayOfMonth();
            int currentmonth = (datePicker.getMonth()) + 1;
            int currentyear = datePicker.getYear();

            datePickerDialog = new DatePickerDialog(this,

                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            textView.setText(dayOfMonth + "/" + month + "/" + year);

                        }
                    }, currentyear, currentmonth, currentday);


            datePickerDialog.show();

    }
    }















