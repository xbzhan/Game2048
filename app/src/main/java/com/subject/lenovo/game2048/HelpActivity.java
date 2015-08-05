package com.subject.lenovo.game2048;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by lenovo on 2015/8/3.
 */
public class HelpActivity extends ActionBarActivity{
    private LinearLayout Helpview;
    private ImageView Helpimage;
    private int num=0;
    private int[] images={R.drawable.help1,R.drawable.help2,R.drawable.help3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Helpview=(LinearLayout)findViewById(R.id.helpview);
        Helpimage=(ImageView)findViewById(R.id.helpimage);

        Helpview.setOnTouchListener(new View.OnTouchListener() {
            private float startX, startY, offsetX, offsetY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:

                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;
                        //意图：水平方向
                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            //意图：向左
                            if (offsetX < -5) {
                                if(num<2)
                                {
                                    num++;
                                    Helpimage.setImageResource(images[num]);
                                }

                            }
                            //意图：向右
                            else if (offsetX > 5) {
                                if(num>0){
                                    num--;
                                    Helpimage.setImageResource(images[num]);
                                }

                            }
                        }

                        break;
                }

                return true;
            }
        });
    }
}
