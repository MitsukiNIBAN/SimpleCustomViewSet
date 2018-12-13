package com.mitsuki.customviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;

import com.mitsuki.variinstructtextview.VariInstructTextView;

public class MainActivity extends AppCompatActivity {

    VariInstructTextView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.content);

        Spannable t1 = new SpannableStringBuilder("99" + "%");
        t1.setSpan(new AbsoluteSizeSpan(160), 0, "99".length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        view.setText(t1);



    }
}
