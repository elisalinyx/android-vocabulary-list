package com.example.finaldemo;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finaldemo.data.WordsContract;

import java.util.Random;


public class AnswerActivity extends AppCompatActivity {
    private TextView tvAnswerWord;
    private RadioButton rbDefinition1, rbDefinition2;
    private int selectAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        findView();

        Intent answerIntent = getIntent();

        tvAnswerWord.setText(answerIntent.getStringExtra("WORD"));

        String definition1 = answerIntent.getStringExtra("DEFINITION");
        rbDefinition2.setText(definition1);

        int rndBound = answerIntent.getIntExtra("ITEMSCOUNT", 0);
        int answerId = answerIntent.getIntExtra("ID", 0);

        while(true){
            Random random = new Random();
            int rndId = random.nextInt(rndBound) + 1;

            if(rndId != answerId){
                ContentResolver contentResolver = getContentResolver();
                Uri uri = WordsContract.WordsEntry.CONTENT_URI;
                String select = "id = " + String.valueOf(rndId);
                Cursor cursor = contentResolver.query(uri, null, select, null, "word asc");
                int definitionIndex = cursor.getColumnIndex(WordsContract.WordsEntry.COL_DEFINITION);
                cursor.moveToFirst();
                rbDefinition1.setText(cursor.getString(definitionIndex));
                break;
            }
        }
    }

    private void findView() {
        tvAnswerWord = (TextView) findViewById(R.id.tvAnswerDefinition);
        rbDefinition1 = (RadioButton) findViewById(R.id.rbAnswerDefinition1);
        rbDefinition2 = (RadioButton) findViewById(R.id.rbAnswerDefinition2);
        rbDefinition1.setChecked(true);
        selectAnswer = 1;
    }


    public void onAnswerClick(View view) {
        if(rbDefinition1.isChecked())
            selectAnswer = 1;
        else if (rbDefinition2.isChecked())
            selectAnswer = 2;
    }

    public void onCheckAnswerClick(View view) {
        if(selectAnswer == 2){
            new AlertDialog.Builder(AnswerActivity.this)
                    .setTitle("Check") // ????????????
                    .setIcon(R.drawable.ic_correct) // ???????????????icon
                    .setMessage("Your answer is correct.") // ??????????????????
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() { //?????????????????????
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
            // ????????????????????? ?????????????????????????????????
        } else {
            new AlertDialog.Builder(AnswerActivity.this)
                    .setTitle("Check") // ????????????
                    .setIcon(R.drawable.ic_error) // ???????????????icon
                    .setMessage("Your answer is error.") // ??????????????????
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() { //?????????????????????
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
            // ????????????????????? ?????????????????????????????????
        }
    }
}
