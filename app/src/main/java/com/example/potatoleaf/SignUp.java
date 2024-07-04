package com.example.potatoleaf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_email, edt_password, edt_username,edt_confirmpass;

    private Button signupBtn, pickdate;
    private TextView textView, signintextview;
    private ProgressBar progressBar;

    private DatePickerDialog datePickerDialog;
    private FirebaseAuth mAuth;

    String name,username,DateOfBIRTH,email,password,confirmpassword,Division,District;
    private Boolean signUpFlag=false;
    private String selectedDistrict, selectedDivision;
    private Spinner divisionSpinner, districtSpinner;
    private ArrayAdapter<CharSequence> stateAdapter, districtAdapter;


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
        edt_confirmpass = findViewById(R.id.conpassID);
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

        divisionSpinner = findViewById(R.id.spinner_bd_division);
        stateAdapter = ArrayAdapter.createFromResource(this, R.array.array_division, R.layout.layout_spinner);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        divisionSpinner.setAdapter(stateAdapter);





        divisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                districtSpinner = findViewById(R.id.spinner_bd_districts);

                selectedDivision = divisionSpinner.getSelectedItem().toString();

                int parentID = parent.getId();
                if (parentID == R.id.spinner_bd_division){
                    switch (selectedDivision){
                        case "Select Division": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_default_districts, R.layout.layout_spinner);
                            break;
                        case "Dhaka": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_dhaka_districts, R.layout.layout_spinner);
                            break;
                        case "Chittagong": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_chittagong_districts, R.layout.layout_spinner);
                            break;
                        case "Sylhet": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_sylhet_districts, R.layout.layout_spinner);
                            break;
                        case "Rajshahi": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_rajshahi_districts, R.layout.layout_spinner);
                            break;
                        case "Rangpur": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_rangpur_districts, R.layout.layout_spinner);
                            break;
                        case "Barisal": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_barisal_districts, R.layout.layout_spinner);
                            break;
                        case "Khulna": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_khulna_districts, R.layout.layout_spinner);
                            break;
                        case "Mymensingh": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_mymensingh_districts, R.layout.layout_spinner);
                            break;
                        default:  break;
                    }
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    districtSpinner.setAdapter(districtAdapter);

                    districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedDistrict = districtSpinner.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        edt_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseReference mData = FirebaseDatabase.getInstance().getReference("users");
                String typename = String.valueOf(editable);
                mData.orderByChild("username").equalTo(typename).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            edt_username.setError("Username is not available");
                            edt_username.requestFocus();
                            signUpFlag=true;
                        }
                        else{
                            signUpFlag=false;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

        });



        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override

                public void onClick(View view) {
                    if(!signUpFlag){
                        validateData();
                    }else {
                        Toast.makeText(SignUp.this, "Correct all fields.", Toast.LENGTH_SHORT).show();
                    }
                }

            private void validateData() {

                username =edt_username.getText().toString();
                email =  edt_email.getText().toString();
                password = edt_password.getText().toString();
                confirmpassword =  edt_confirmpass.getText().toString();

                if(username.isEmpty()){
                    edt_username.setError("Required");
                    edt_username.requestFocus();
                }else if(email .isEmpty()){
                    edt_email.setError("Required");
                    edt_email.requestFocus();
                }else if(password.isEmpty()){
                    edt_password.setError("Required");
                    edt_password.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    edt_email.setError("Invalid email address");
                    edt_email.requestFocus();
                }
                else if(confirmpassword.isEmpty()){
                    edt_confirmpass.setError("Required");
                    edt_confirmpass.requestFocus();
                } if(!password.equals(confirmpassword)){
                    edt_confirmpass.setError("Password didn't matched");
                    edt_confirmpass.requestFocus();
                }
                else {
                    createUser();
                }
            }


        });

        }
    private void createUser() {




        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {




                        if(task.isSuccessful()){

                            FirebaseUser user = mAuth.getCurrentUser();
                            uploadUserData(String.valueOf(user.getUid()));
                        }
                        else {
                            Toast.makeText(SignUp.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUp.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }





    void uploadUserData(String userID) {


        name = edt_username.getText().toString();
        username = edt_email.getText().toString();
        DateOfBIRTH =  textView.getText().toString();
        Division = divisionSpinner.getSelectedItem().toString();
        HashMap<String, String> user = new HashMap<>();

        user.put("username", name);
        user.put("email", username);
        user.put("DOB",  DateOfBIRTH);
        user.put("Division - ",  Division );



        database.child("users").child(userID).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "User successfully created", Toast.LENGTH_SHORT).show();
                          Intent intent = new Intent(SignUp.this, MainActivity.class);startActivity(intent);
                         finish();

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















