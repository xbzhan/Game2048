package com.subject.lenovo.game2048;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lenovo on 2015/8/1.
 */
public class Game5x5Activiy extends ActionBarActivity {
    private GridLayout gameview;
    private Card[][] cardMap=new Card[5][5];
    private int score = 0;//计分器
    private TextView tvScore;//显示分数
    private Button start;//开始游戏键
    private Button restart;//重新游戏键
    private Button introduction5x5;
    private List<Point> emptyPoints=new ArrayList<Point>();
    private SoundPool Sound;
    private HashMap<Integer,Integer> Sounds=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5x5game);
        gameview=(GridLayout)findViewById(R.id.gameView);
        tvScore=(TextView)findViewById(R.id.tvscore);
        start=(Button)findViewById(R.id.Start);
        restart=(Button)findViewById(R.id.Restart);
        introduction5x5=(Button)findViewById(R.id.Introduce);
        //根据不同手机分辨率设置5*5方阵
        setLayoutSize();
        //设置音效
        Sound=new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        setSound();

        introduction5x5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Game5x5Activiy.this,IntroductionActiviy5x5.class);
                startActivity(intent);
            }
        });


        start.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sound.play(Sounds.get(4), 1, 1, 0, 0, 1);
                start.setClickable(false);
                startGame();
                initGameView();

                restart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Sound.play(Sounds.get(2), 1, 1, 0, 0, 1);
                        startGame();
                    }
                });

            }
        });
    }
    //入口
    public void initGameView(){




        gameview.setOnTouchListener(new View.OnTouchListener() {

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
                                Sound.play(Sounds.get(1), 1, 1, 0, 0, 1);
                                swipeLeft();
                            }
                            //意图：向右
                            else if (offsetX > 5) {
                                Sound.play(Sounds.get(1), 1, 1, 0, 0, 1);
                                swipeRight();
                            }
                        }
                        //意图：垂直方向
                        else {
                            //意图：向上
                            if (offsetY < -5) {
                                Sound.play(Sounds.get(1), 1, 1, 0, 0, 1);
                                swipeUp();
                            }
                            //意图：向下
                            else if (offsetY > 5) {
                                Sound.play(Sounds.get(1), 1, 1, 0, 0, 1);
                                swipeDown();
                            }
                        }
                        break;
                }

                return true;
            }
        });

    }
    //向左的操作函数
    public void swipeLeft(){
        boolean merge = false;
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                for(int x1=x+1;x1<5;x1++) {//从第一行第二个位置的位置向右再向下的优先顺序遍历
                    if (cardMap[x1][y].getNum()>0){//如果当前位置的数大于零
                        if (cardMap[x][y].getNum()<=0) {//判断当前位置的左边一个位置的数小于零，则与之交换位置，并清除之前位置的数字
                            cardMap[x][y].setNum(cardMap[x1][y].getNum());
                            cardMap[x][y].setColour();
                            cardMap[x][y].setNumSize();
                            cardMap[x1][y].setNum(0);
                            cardMap[x1][y].setColour();
                            cardMap[x1][y].setNumSize();
                            x--;//如果交换成功，需要重新遍历
                            merge = true;


                        }else if (cardMap[x][y].equals(cardMap[x1][y])) {//如果当前位置的左边一个位置的数大于零，判断这两个位置的数相等
                            cardMap[x][y].setNum(cardMap[x][y].getNum()*2);//合并
                            cardMap[x][y].setColour();
                            cardMap[x][y].setNumSize();
                            cardMap[x1][y].setNum(0);//消除当前位置的数
                            cardMap[x1][y].setColour();
                            cardMap[x1][y].setNumSize();
                            addScore(cardMap[x][y].getNum());//合并后加分计分
                            merge = true;

                        }
                        break;
                    }

                }
            }
        }
        if (merge) {
            addRandomNum();
            checkComplete();
        }
    }
    //向右的操作函数
    public void swipeRight(){
        boolean merge = false;
        for (int y = 0; y < 5; y++) {
            for (int x = 4; x >= 0; x--) {
                for(int x1=x-1;x1>=0;x1--) {//从第一行右数第二个位置的位置向左再向下的优先顺序遍历
                    if (cardMap[x1][y].getNum()>0){//如果当前位置的数大于零
                        if (cardMap[x][y].getNum()<=0) {//判断当前位置的右边一个位置的数小于零，则与之交换位置，并清除之前位置的数字
                            cardMap[x][y].setNum(cardMap[x1][y].getNum());
                            cardMap[x][y].setColour();
                            cardMap[x][y].setNumSize();
                            cardMap[x1][y].setNum(0);
                            cardMap[x1][y].setNumSize();
                            cardMap[x1][y].setColour();
                            x++;//如果交换成功，需要重新遍历
                            merge = true;


                        }else if (cardMap[x][y].equals(cardMap[x1][y])) {//如果当前位置的右边一个位置的数大于零，判断这两个位置的数相等
                            cardMap[x][y].setNum(cardMap[x][y].getNum()*2);//合并
                            cardMap[x][y].setColour();
                            cardMap[x][y].setNumSize();
                            cardMap[x1][y].setNum(0);//消除当前位置的数
                            cardMap[x1][y].setColour();
                            cardMap[x1][y].setNumSize();
                            addScore(cardMap[x][y].getNum());//合并后加分计分
                            merge = true;

                        }
                        break;
                    }

                }
            }
        }
        if (merge) {
            addRandomNum();
            checkComplete();

        }

    }
    //向上的操作函数
    public void swipeUp(){
        boolean merge = false;
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                for(int y1=y+1;y1<5;y1++) {//从第一列第二个位置的位置向下再向右的优先顺序遍历
                    if (cardMap[x][y1].getNum()>0){//如果当前位置的数大于零
                        if (cardMap[x][y].getNum()<=0) {//判断当前位置的上一个位置的数小于零，则与之交换位置，并清除之前位置的数字
                            cardMap[x][y].setNum(cardMap[x][y1].getNum());
                            cardMap[x][y].setColour();
                            cardMap[x][y].setNumSize();
                            cardMap[x][y1].setNum(0);
                            cardMap[x][y1].setNumSize();
                            cardMap[x][y1].setColour();
                            merge = true;

                            y--;//如果交换成功，需要重新遍历


                        }else if (cardMap[x][y].equals(cardMap[x][y1])) {//如果当前位置的上一个位置的数大于零，判断这两个位置的数相等
                            cardMap[x][y].setNum(cardMap[x][y].getNum()*2);//合并
                            cardMap[x][y].setColour();
                            cardMap[x][y].setNumSize();
                            cardMap[x][y1].setNum(0);//消除当前位置的数
                            cardMap[x][y1].setNumSize();
                            cardMap[x][y1].setColour();
                            addScore(cardMap[x][y].getNum());//合并后加分计分
                            merge = true;

                        }
                        break;
                    }

                }
            }
        }
        if (merge) {
            addRandomNum();
            checkComplete();

        }

    }
    //向下的操作函数
    public void swipeDown(){
        boolean merge = false;
        for (int x = 0; x < 5; x++) {
            for (int y = 4; y>=0; y--) {
                for(int y1=y-1;y1>=0;y1--) {//从第五列第二个位置的位置向下再向左的优先顺序遍历
                    if (cardMap[x][y1].getNum()>0){//如果当前位置的数大于零
                        if (cardMap[x][y].getNum()<=0) {//判断当前位置的下方一个位置的数小于零，则与之交换位置，并清除之前位置的数字
                            cardMap[x][y].setNum(cardMap[x][y1].getNum());
                            cardMap[x][y].setColour();
                            cardMap[x][y].setNumSize();
                            cardMap[x][y1].setNum(0);
                            cardMap[x][y1].setNumSize();
                            cardMap[x][y1].setColour();
                            y++;//如果交换成功，需要重新遍历
                            merge = true;


                        }else if (cardMap[x][y].equals(cardMap[x][y1])) {//如果当前位置的下方一个位置的数大于零，判断这两个位置的数相等
                            cardMap[x][y].setNum(cardMap[x][y].getNum()*2);//合并
                            cardMap[x][y].setColour();
                            cardMap[x][y].setNumSize();
                            cardMap[x][y1].setNum(0);//消除当前位置的数
                            cardMap[x][y1].setNumSize();
                            cardMap[x][y1].setColour();
                            addScore(cardMap[x][y].getNum());//合并后加分计分
                            merge = true;

                        }
                        break;
                    }

                }
            }
        }
        if (merge) {
            addRandomNum();
            checkComplete();

        }

    }
    //检查游戏结束
    public void checkComplete() {

        boolean complete = true;// 判断游戏是否结束

        ALL:
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                if (cardMap[x][y].getNum() == 0 ||//某一个位置为0（空的）
                        (x > 0 && cardMap[x][y].equals(cardMap[x - 1][y])) ||//相邻位置有相同的数字
                        (x < 4 && cardMap[x][y].equals(cardMap[x + 1][y])) ||
                        (y > 0 && cardMap[x][y].equals(cardMap[x][y - 1])) ||
                        (y < 4 && cardMap[x][y].equals(cardMap[x][y + 1]))) {

                    complete = false;
                    break ALL;
                }
            }

        }


        if (complete) {
            Sound.play(Sounds.get(3), 1, 1, 0, 0, 1);
            new AlertDialog.Builder(gameview.getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重来", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();
        }

    }
    //开始游戏
    public void startGame(){
        clearScore();//开始游戏分数清零

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                cardMap[x][y].setNum(0);
                cardMap[x][y].setColour();
                cardMap[x][y].setNumSize();
            }
        }

        addRandomNum();
        addRandomNum();

    }
    //添加5*5数字方阵
    public void addCards(int cardWidth,int cardHeight){
        Card c;


        for(int y=0;y<5;y++){
            for(int x=0;x<5;x++){
                c = new Card(gameview.getContext());

                gameview.addView(c, cardWidth, cardHeight);


                cardMap[x][y]=c;

            }
        }




    }
    //分数清零
    public void clearScore(){//分数清零
        score = 0;
        showScore();
    }
    //显示分数
    public void showScore(){
        tvScore.setText(score + "");
    }
    //计分
    public void addScore(int s){
        score+=s;
        showScore();
    }
    //动态设置5*5的网络布局
    public void setLayoutSize(){
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        int CardWidth=(Math.min(width,height)-10)/5;
        addCards(CardWidth, CardWidth);
    }
    //设置音效
    public void setSound( ){
        Sounds.put(1,Sound.load(this,R.raw.huadong,1));
        Sounds.put(2,Sound.load(this,R.raw.restart,1));
        Sounds.put(3, Sound.load(this,R.raw.over,1));
        Sounds.put(4, Sound.load(this, R.raw.start, 1));

    }
    //添加随机数
    public  void  addRandomNum(){
        emptyPoints.clear();//清除
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                if (cardMap[x][y].getNum() <= 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }
        Point p = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        cardMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
        cardMap[p.x][p.y].setColour();
        cardMap[p.x][p.y].setNumSize();

    }
}
