package com.subject.lenovo.game2048;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by lenovo on 2015/8/1.
 */
public class FirstActivity extends ActionBarActivity {
    private Button game5x5,game4x4,game404,Help;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        game5x5=(Button)findViewById(R.id.game5x5);
        game4x4=(Button)findViewById(R.id.game4x4);
        game404=(Button)findViewById(R.id.game4x4_0);
        Help=(Button)findViewById(R.id.help);
        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FirstActivity.this,HelpActivity.class);
                startActivity(intent);
            }
        });
        game404.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FirstActivity.this,Game404Activiy.class);
                startActivity(intent);
            }
        });
        game5x5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FirstActivity.this,Game5x5Activiy.class);
                startActivity(intent);
            }
        });
        game4x4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FirstActivity.this,Game4x4Activity.class);
                startActivity(intent);

            }
        });

    }
}
