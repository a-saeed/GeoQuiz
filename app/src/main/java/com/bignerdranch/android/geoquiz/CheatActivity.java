package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {
    //key part of the extra being received from the incoming parent intent .
    public static final String EXTRA_ANSWER_IS_TRUE =
            "com.bignerdranch.android.geoquiz.answer_is_true";
    //key part of the extra being sent back to the parent as a result
    //of the parent calling the startActivityForResult() method
    public static final String EXTRA_ANSWER_SHOWN=
            "com.bignerdranch.android.geoquiz.answer_shown";
    private boolean mAnswerIsTrue;//hold the value of the boolean extra sent by parent activity

    //save it to bundle whenever a runtime
    //configuration is changed
    private static final String CHEAT_INDEX= "cheat";
    //the value part of the CHEAT_INDEX key
    boolean isAnswerShown_bundle;

    private TextView mAnswerTextView;    private Button mShowAnswer;
    //create an intent , put an extra to it , set a result.
    private void setAnswerShownResult(boolean isAnswerShown)
    {
        Intent data = new Intent();
        isAnswerShown=isAnswerShown_bundle;
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK,data);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(CHEAT_INDEX,isAnswerShown_bundle);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if(savedInstanceState != null)
            isAnswerShown_bundle= savedInstanceState.getBoolean(CHEAT_INDEX, false);
        //return the intent(i),then return the extra being sent with it.
        mAnswerIsTrue= getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView)findViewById(R.id.textView_answer);
        mShowAnswer = (Button)findViewById(R.id.button_showAnswer);

        //answer will not be shown until the user
        //presses the button
        setAnswerShownResult(false);

        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAnswerIsTrue)
                    mAnswerTextView.setText(R.string.true_button);
                else
                    mAnswerTextView.setText(R.string.false_button);
                //save the fact that the user cheated
                //and keep it in bundle whenever a runtime
                //config. change happens.
                isAnswerShown_bundle=true;
                setAnswerShownResult(true);
            }
        });

    }
}
