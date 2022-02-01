package com.example.demoncleaner.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.demoncleaner.R;

public class EditStreakActivity extends AppCompatActivity {

    public static final String EXTRA_EDIT_STREAK_NOTE = "High score";

    private EditText editNoteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_streak);

        editNoteEditText = findViewById(R.id.edit_streak_note);

        final Button button = findViewById(R.id.button_update_streak);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();

                if(TextUtils.isEmpty(editNoteEditText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String note = editNoteEditText.getText().toString();
                    replyIntent.putExtra(EXTRA_EDIT_STREAK_NOTE, note);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}