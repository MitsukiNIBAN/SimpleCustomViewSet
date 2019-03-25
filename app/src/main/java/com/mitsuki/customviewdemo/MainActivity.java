package com.mitsuki.customviewdemo;

import android.app.Activity;
import android.os.Bundle;

import com.mitsuki.variinstructtextview.VariInstructTextView;

public class MainActivity extends Activity {

    VariInstructTextView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Spannable t1 = new SpannableStringBuilder("99" + "%");
//        t1.setSpan(new AbsoluteSizeSpan(160), 0, "99".length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        view.setText(t1);
//


    }
}
