package com.example.androidhw2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.androidhw2.R;
import com.example.androidhw2.models.Question;
import com.example.androidhw2.viewmodels.TriviaViewModel;

public class MainActivity extends AppCompatActivity {
    private TriviaViewModel triviaViewModel;
    private TextView questionTextView;
    private RadioGroup optionsGroup;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        triviaViewModel = new ViewModelProvider(this).get(TriviaViewModel.class);
        triviaViewModel.loadQuestions(this);

        questionTextView = findViewById(R.id.question_text);
        optionsGroup = findViewById(R.id.options_group);
        submitButton = findViewById(R.id.submit_button);

        displayQuestion();

        submitButton.setOnClickListener(v -> {
            // Ensure a valid option is selected
            int selectedId = optionsGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an option.", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedOptionIndex = optionsGroup.indexOfChild(findViewById(selectedId));
            boolean isCorrect = selectedOptionIndex == triviaViewModel.getCurrentQuestion().getCorrectAnswerIndex();

            if (triviaViewModel.hasMoreQuestions()) {
                triviaViewModel.moveToNextQuestion(isCorrect);
                displayQuestion();
            } else {
                // All questions answered, transition to ResultsActivity
                showResults();
            }
        });
    }

    private void displayQuestion() {
        Question question = triviaViewModel.getCurrentQuestion();
        questionTextView.setText(question.getQuestionText());

        String[] options = question.getOptions();

        for (int i = 0; i < optionsGroup.getChildCount(); i++) {
            RadioButton optionButton = (RadioButton) optionsGroup.getChildAt(i);
            if (i < options.length) {
                optionButton.setText(options[i]);
                optionButton.setVisibility(View.VISIBLE);
            } else {
                optionButton.setVisibility(View.GONE);
            }
        }

        optionsGroup.clearCheck();
    }

    private void showResults() {
        Intent intent = new Intent(this, ResultsActivity.class);

        // Pass the score and total questions
        intent.putExtra("score", triviaViewModel.getScore());
        intent.putExtra("totalQuestions", triviaViewModel.getTotalQuestions());


        // Start ResultsActivity
        startActivity(intent);
    }

}
