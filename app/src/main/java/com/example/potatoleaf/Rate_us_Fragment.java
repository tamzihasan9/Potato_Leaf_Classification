package com.example.potatoleaf;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;


public class Rate_us_Fragment extends Fragment {

    private RatingBar ratingbar;
    private TextView textview;
 public Rate_us_Fragment()
 {



 }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        ratingbar = (RatingBar) getView().findViewById(R.id.ratingbarid);
        textview = (TextView) getView().findViewById(R.id.textviewid);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                textview.setText("Our Rating Is :" + rating);

            }
        });

      return inflater.inflate(R.layout.fragment_rate_us_,container, false);
    }
}