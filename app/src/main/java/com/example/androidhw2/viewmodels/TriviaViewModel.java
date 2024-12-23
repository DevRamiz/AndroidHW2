package com.example.androidhw2.viewmodels;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.androidhw2.models.TriviaDatabaseHelper;
import com.example.androidhw2.models.Question;

import java.util.ArrayList;
import java.util.List;

public class TriviaViewModel extends ViewModel {
    private List<Question> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;

    public void loadQuestions(Context context) {
        TriviaDatabaseHelper dbHelper = new TriviaDatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                TriviaDatabaseHelper.TABLE_QUESTIONS,
                null, null, null, null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String questionText = cursor.getString(cursor.getColumnIndexOrThrow(TriviaDatabaseHelper.COLUMN_QUESTION));
                String[] options = new String[]{
                        cursor.getString(cursor.getColumnIndexOrThrow(TriviaDatabaseHelper.COLUMN_OPTION_1)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TriviaDatabaseHelper.COLUMN_OPTION_2)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TriviaDatabaseHelper.COLUMN_OPTION_3)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TriviaDatabaseHelper.COLUMN_OPTION_4))
                };
                int correctAnswerIndex = cursor.getInt(cursor.getColumnIndexOrThrow(TriviaDatabaseHelper.COLUMN_CORRECT_ANSWER));

                questions.add(new Question(questionText, options, correctAnswerIndex));
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
    }

    public Question getCurrentQuestion() {
        return questions.get(currentQuestionIndex);
    }

    public void moveToNextQuestion(boolean isCorrect) {
        if (isCorrect) score++;
        currentQuestionIndex++;
    }


    public boolean hasMoreQuestions() {
        return currentQuestionIndex < (questions.size() - 1);
    }



    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return questions.size();
    }
}
