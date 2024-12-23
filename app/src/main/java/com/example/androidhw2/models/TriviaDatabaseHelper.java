package com.example.androidhw2.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TriviaDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "trivia.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_QUESTIONS = "questions";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_OPTION_1 = "option1";
    public static final String COLUMN_OPTION_2 = "option2";
    public static final String COLUMN_OPTION_3 = "option3";
    public static final String COLUMN_OPTION_4 = "option4";
    public static final String COLUMN_CORRECT_ANSWER = "correct_answer";

    private static final String CREATE_TABLE_QUESTIONS =
            "CREATE TABLE " + TABLE_QUESTIONS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_QUESTION + " TEXT, " +
                    COLUMN_OPTION_1 + " TEXT, " +
                    COLUMN_OPTION_2 + " TEXT, " +
                    COLUMN_OPTION_3 + " TEXT, " +
                    COLUMN_OPTION_4 + " TEXT, " +
                    COLUMN_CORRECT_ANSWER + " INTEGER)";

    public TriviaDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUESTIONS);

        // Seed initial questions
        db.execSQL("INSERT INTO " + TABLE_QUESTIONS + " (" +
                COLUMN_QUESTION + ", " +
                COLUMN_OPTION_1 + ", " +
                COLUMN_OPTION_2 + ", " +
                COLUMN_OPTION_3 + ", " +
                COLUMN_OPTION_4 + ", " +
                COLUMN_CORRECT_ANSWER + ") VALUES " +
                "('What is the capital of France?', 'Paris', 'London', 'Berlin', 'Madrid', 0)," +
                "('What is 2 + 2?', '3', '4', '5', '6', 1)," +
                "('Which planet is known as the Red Planet?', 'Earth', 'Mars', 'Venus', 'Jupiter', 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        onCreate(db);
    }

}
