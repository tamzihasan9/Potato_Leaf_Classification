package com.example.potatoleaf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Rate_us extends AppCompatActivity {

    private RatingBar ratingBar;
    private TextView textView;
    private Button button;


    private FirebaseDatabase database;
    private DatabaseReference ratingRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);


        database = FirebaseDatabase.getInstance();
        ratingRef = database.getReference("ratings");

        ratingBar = findViewById(R.id.ratingbar1);
        textView = findViewById(R.id.textviewid);
        button = findViewById(R.id.rateId);


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                textView.setText("You Rated: " + rating);

                // Save rating to Firebase on button click
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveRatingToFirebase(rating);
                    }
                });
            }
        });
    }

    private void saveRatingToFirebase(float rating) {
        ratingRef.setValue(rating)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Rating saved successfully
                        Toast.makeText(Rate_us.this, "Thank You For your Feedback", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Rating save failed
                        Toast.makeText(Rate_us.this, "Rating save failed!", Toast.LENGTH_SHORT).show();
                        Log.e("Rate_us", "Error saving rating:", e);
                    }
                });
    }
}
