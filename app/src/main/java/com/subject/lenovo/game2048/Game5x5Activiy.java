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
    private int score = 0;//�Ʒ���
    private TextView tvScore;//��ʾ����
    private Button start;//��ʼ��Ϸ��
    private Button restart;//������Ϸ��
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
        //���ݲ�ͬ�ֻ��ֱ�������5*5����
        setLayoutSize();
        //������Ч
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
    //���
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
                        //��ͼ��ˮƽ����
                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            //��ͼ������
                            if (offsetX < -5) {
                                Sound.play(Sounds.get(1), 1, 1, 0, 0, 1);
                                swipeLeft();
                            }
                            //��ͼ������
                            else if (offsetX > 5) {
                                Sound.play(Sounds.get(1), 1, 1, 0, 0, 1);
                                swipeRight();
                            }
                        }
                        //��ͼ����ֱ����
                        else {
                            //��ͼ������
                            if (offsetY < -5) {
                                Sound.play(Sounds.get(1), 1, 1, 0, 0, 1);
                                swipeUp();
                            }
                            //��ͼ������
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
    //����Ĳ�������
    public void swipeLeft(){
        boolean merge = false;
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                for(int x1=x+1;x1<5;x1++) {//�ӵ�һ�еڶ���λ�õ�λ�����������µ�����˳�����
                    if (cardMap[x1][y].getNum()>0){//�����ǰλ�õ���������
                        if (cardMap[x][y].getNum()<=0) {//�жϵ�ǰλ�õ����һ��λ�õ���С���㣬����֮����λ�ã������֮ǰλ�õ�����
                            cardMap[x][y].setNum(cardMap[x1][y].getNum());
                            cardMap[x][y].setColour();
                            cardMap[x][y].setNumSize();
                            cardMap[x1][y].setNum(0);
                            cardMap[x1][y].setColour();
                            cardMap[x1][y].setNumSize();
                            x--;//��������ɹ�����Ҫ���±���
                            merge = true;


                        }else if (cardMap[x][y].equals(cardMap[x1][y])) {//�����ǰλ�õ����һ��λ�õ��������㣬�ж�������λ�õ������
                            cardMap[x][y].setNum(cardMap[x][y].getNum()*2);//�ϲ�
                            cardMap[x][y].setColour();
                            cardMap[x][y].setNumSize();
                            cardMap[x1][y].setNum(0);//������ǰλ�õ���
                            cardMap[x1][y].setColour();
                            cardMap[x1][y].setNumSize();
                            addScore(cardMap[x][y].getNum());//�ϲ���ӷּƷ�
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
    //���ҵĲ�������
    public void swipeRight(){
        boolean merge = false;
        for (int y = 0; y < 5; y++) {
            for (int x = 4; x >= 0; x--) {
                for(int x1=x-1;x1>=0;x1--) {//�ӵ�һ�������ڶ���λ�õ�λ�����������µ�����˳�����
                    if (cardMap[x1][y].getNum()>0){//�����ǰλ�õ���������
                        if (cardMap[x][y].getNum()<=0) {//�жϵ�ǰλ�õ��ұ�һ��λ�õ���С���㣬����֮����λ�ã������֮ǰλ�õ�����
                            cardMap[x][y].setNum(cardMap[x1][y].getNum());
                            cardMap[x][y].setColour();
                            cardMap[x][y].setNumSize();
                            cardMap[x1][y].setNum(0);
                            cardMap[x1][y].setNumSize();
                            cardMap[x1][y].setColour();
                            x++;//��������ɹ�����Ҫ���±���
                            merge = true;


                        }else if (cardMap[x][y].equals(cardMap[x1][y])) {//�����ǰλ�õ��ұ�һ��λ�õ��������㣬�ж�������λ�õ������
                            cardMap[x][y].setNum(cardMap[x][y].getNum()*2);//�ϲ�
                            cardMap[x][y].setColour();
                            cardMap[x][y].setNumSize();
                            cardMap[x1][y].setNum(0);//������ǰλ�õ���
                            cardMap[x1][y].setColour();
                            cardMap[x1][y].setNumSize();
                            addScore(cardMap[x][y].getNum());//�ϲ���ӷּƷ�
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
    //���ϵĲ�������
    public void swipeUp(){
        boolean merge = false;
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                for(int y1=y+1;y1<5;y1++) {//�ӵ�һ�еڶ���λ�õ�λ�����������ҵ�����˳�����
                    if (cardMap[x][y1].getNum()>0){//�����ǰλ�õ���������
                        if (cardMap[x][y].getNum()<=0) {//�жϵ�ǰλ�õ���һ��λ�õ���С���㣬����֮����λ�ã������֮ǰλ�õ�����
                            cardMap[x][y].setNum(cardMap[x][y1].getNum());
                            cardMap[x][y].setColour();
                            cardMap[x][y].setNumSize();
                            cardMap[x][y1].setNum(0);
                            cardMap[x][y1].setNumSize();
                            cardMap[x][y1].setColour();
                            merge = true;

                            y--;//��������ɹ�����Ҫ���±���


                        }else if (cardMap[x][y].equals(cardMap[x][y1])) {//�����ǰλ�õ���һ��λ�õ��������㣬�ж�������λ�õ������
                            cardMap[x][y].setNum(cardMap[x][y].getNum()*2);//�ϲ�
                            cardMap[x][y].setColour();
                            cardMap[x][y].setNumSize();
                            cardMap[x][y1].setNum(0);//������ǰλ�õ���
                            cardMap[x][y1].setNumSize();
                            cardMap[x][y1].setColour();
                            addScore(cardMap[x][y].getNum());//�ϲ���ӷּƷ�
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
    //���µĲ�������
    public void swipeDown(){
        boolean merge = false;
        for (int x = 0; x < 5; x++) {
            for (int y = 4; y>=0; y--) {
                for(int y1=y-1;y1>=0;y1--) {//�ӵ����еڶ���λ�õ�λ�����������������˳�����
                    if (cardMap[x][y1].getNum()>0){//�����ǰλ�õ���������
                        if (cardMap[x][y].getNum()<=0) {//�жϵ�ǰλ�õ��·�һ��λ�õ���С���㣬����֮����λ�ã������֮ǰλ�õ�����
                            cardMap[x][y].setNum(cardMap[x][y1].getNum());
                            cardMap[x][y].setColour();
                            cardMap[x][y].setNumSize();
                            cardMap[x][y1].setNum(0);
                            cardMap[x][y1].setNumSize();
                            cardMap[x][y1].setColour();
                            y++;//��������ɹ�����Ҫ���±���
                            merge = true;


                        }else if (cardMap[x][y].equals(cardMap[x][y1])) {//�����ǰλ�õ��·�һ��λ�õ��������㣬�ж�������λ�õ������
                            cardMap[x][y].setNum(cardMap[x][y].getNum()*2);//�ϲ�
                            cardMap[x][y].setColour();
                            cardMap[x][y].setNumSize();
                            cardMap[x][y1].setNum(0);//������ǰλ�õ���
                            cardMap[x][y1].setNumSize();
                            cardMap[x][y1].setColour();
                            addScore(cardMap[x][y].getNum());//�ϲ���ӷּƷ�
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
    //�����Ϸ����
    public void checkComplete() {

        boolean complete = true;// �ж���Ϸ�Ƿ����

        ALL:
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                if (cardMap[x][y].getNum() == 0 ||//ĳһ��λ��Ϊ0���յģ�
                        (x > 0 && cardMap[x][y].equals(cardMap[x - 1][y])) ||//����λ������ͬ������
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
            new AlertDialog.Builder(gameview.getContext()).setTitle("���").setMessage("��Ϸ����").setPositiveButton("����", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();
        }

    }
    //��ʼ��Ϸ
    public void startGame(){
        clearScore();//��ʼ��Ϸ��������

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
    //���5*5���ַ���
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
    //��������
    public void clearScore(){//��������
        score = 0;
        showScore();
    }
    //��ʾ����
    public void showScore(){
        tvScore.setText(score + "");
    }
    //�Ʒ�
    public void addScore(int s){
        score+=s;
        showScore();
    }
    //��̬����5*5�����粼��
    public void setLayoutSize(){
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        int CardWidth=(Math.min(width,height)-10)/5;
        addCards(CardWidth, CardWidth);
    }
    //������Ч
    public void setSound( ){
        Sounds.put(1,Sound.load(this,R.raw.huadong,1));
        Sounds.put(2,Sound.load(this,R.raw.restart,1));
        Sounds.put(3, Sound.load(this,R.raw.over,1));
        Sounds.put(4, Sound.load(this, R.raw.start, 1));

    }
    //��������
    public  void  addRandomNum(){
        emptyPoints.clear();//���
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
