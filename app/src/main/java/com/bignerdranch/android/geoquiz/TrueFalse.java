package com.bignerdranch.android.geoquiz;

public class TrueFalse {
    private  int mQuestion;
    private boolean mTrueQuestion;

    ///constructor

    public TrueFalse(int mQuestion, boolean mTrueQuestion) {
        this.mQuestion = mQuestion;
        this.mTrueQuestion = mTrueQuestion;
    }
    public int getmQuestion() {
        return mQuestion;
    }


    public boolean ismTrueQuestion() {
        return mTrueQuestion;
    }


}
