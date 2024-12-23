package com.example.androidhw2.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.androidhw2.R;

public class ResultsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Retrieve data passed from MainActivity
        int score = getIntent().getIntExtra("score", -1);
        int totalQuestions = getIntent().getIntExtra("totalQuestions", -1);

        Log.d("TriviaApp", "ResultsActivity launched. Score: "
                + score + " / " + totalQuestions);

        TextView scoreTextView = findViewById(R.id.score_text);

        // Validate data and display it
        if (score != -1 && totalQuestions != -1) {
            scoreTextView.setText("Your score: " + score + " / " + totalQuestions);
        } else {
            scoreTextView.setText("Error: Unable to retrieve results.");
        }
    }
}
