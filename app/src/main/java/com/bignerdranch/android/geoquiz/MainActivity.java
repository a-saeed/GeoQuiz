package com.bignerdranch.android.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //adding a constant that will be a key for the key value pair
    //that will be stored in the bundle
    //because when need to save the mCurrentIndex whenever
    //the screen rotation configuration
    //is changed during runtime
    private static final String KEY_INDEX = "index";
    //also
    private static final  String CHEATER_INDEX = "cheater index";

    Button mTrueButton , mFalseButton , mCheatButton;
    ImageButton mNextButton , mPreviousButton;
    TextView mQuestionTextView;
    int mCurrentIndex=0;
    private boolean mIsCheater;

    TrueFalse[] mQuestionBank= new TrueFalse[]{
            new TrueFalse(R.string.question_oceans,true),
            new TrueFalse(R.string.question_mideast,false),
            new TrueFalse(R.string.question_africa,false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia,true)
    };

    //override the saveInstanceState method
    //to save the current index whenever
    //a runtime config change happens.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(CHEATER_INDEX,mIsCheater);
    }

    //encapsulate updateQuestion
    private void updateQuestion(){
        int questionId= mQuestionBank[mCurrentIndex].getmQuestion();
            mQuestionTextView.setText(questionId);
    }
    //check user answer
    private  void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue= mQuestionBank[mCurrentIndex].ismTrueQuestion();
        int messageResId=0;
        if(mIsCheater) {
            messageResId = R.string.judgment_toast;

        }
        else {
            if (userPressedTrue == answerIsTrue)
                messageResId = R.string.correct_toast;
            else
                messageResId = R.string.incorrect_toast;
        }

            Toast.makeText(getApplicationContext(),
                    messageResId, Toast.LENGTH_SHORT).show();
    }

    //override the method that retrieves the extra data sent by the child activity
    //it does not use requestCode in this case , will be used in different cases
    @Override
    protected void onActivityResult(int requestCode , int resultCode ,Intent data )
    {
        if(data == null)
            return;
        mIsCheater=data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN,false);
    }

        //onCreate is like a Main function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///based on the "currentIndex" , we get the question id
        //that is currently being pointed at.

        mQuestionTextView=(TextView) findViewById(R.id.textView_question);
        //maintain current index value if any
        //config runtime change occur(rotate screen)
        if(savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater= savedInstanceState.getBoolean(CHEATER_INDEX,false);
        }

        updateQuestion();

        mTrueButton=(Button) findViewById(R.id.button_true);
        mFalseButton=(Button) findViewById(R.id.button_false);
        mNextButton=(ImageButton) findViewById(R.id.button_next);
        mPreviousButton= (ImageButton) findViewById(R.id.button_prev);
        mCheatButton= (Button) findViewById(R.id.button_cheat);

        //setting button listeners
        //next button increments current index and updates question
        mTrueButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                checkAnswer(true);
             }
         });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                checkAnswer(false);
             }
         });
        mNextButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 mCurrentIndex= (mCurrentIndex+1) % mQuestionBank.length;
                updateQuestion();
             }
         });
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CheatActivity.class);
                //extra info to be sent to the started activity
                //extras are structured as key-value pairs
                //the value to be sent is specified in the parent activity
                //the key(a string) is defined in the activity that retrieves the extra.
                //answerISTrue is teh value part.
                //EXTRA_ANSWER_IS_TRUE is the key
                boolean answerIsTrue=mQuestionBank[mCurrentIndex].ismTrueQuestion();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE,answerIsTrue);
                //2nd parameter is the request code sent to child activity
                //then retrieved back by parent activity
                startActivityForResult(i,0);
            }
        });

        //challenge#1: add a listener to textView
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex++;
                updateQuestion();
            }
        });

        //challenge#2: adding a previous button to show prev question.
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex--;
                updateQuestion();
            }
        });

    }

}
