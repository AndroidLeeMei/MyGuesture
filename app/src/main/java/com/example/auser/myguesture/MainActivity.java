package com.example.auser.myguesture;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.name;

public class MainActivity extends AppCompatActivity {
    TextView result;
    GestureOverlayView gestureOverlayView;
    GestureLibrary gestureLibrary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result=(TextView)findViewById(R.id.result);
        gestureOverlayView=(GestureOverlayView)findViewById(R.id.goView);//這是一個可以畫手勢的件
        gestureOverlayView.addOnGesturePerformedListener(listener);//設定監聽器
        findGestureLib();
    }

    void findGestureLib(){  //找出存檔的手勢
        gestureLibrary= GestureLibraries.fromRawResource(this,R.raw.gestures);  //GestureLibraries複數型態
        if (!gestureLibrary.load())
            finish();//若手勢資料庫不存在則強製結束

    }

    GestureOverlayView.OnGesturePerformedListener listener= new GestureOverlayView.OnGesturePerformedListener() {
        @Override
        public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
            ArrayList<Prediction> list=gestureLibrary.recognize(gesture); //Prediction預測,透過library去得到一個分析的結果,存放在
            if (list.size()>0 && list.get(0).score>5) {//判斷是否有比對物件list.size()>0,裏可能存在多個Prediction物件,把最好的放在第一位,所以取出第一個,2  數字5代表最相像,分數愈高愈好
                String name = list.get(0).name;  //取得己經儲存的手勢名稱
                double score = list.get(0).score;
                result.setText("手勢名稱" + name + ",分數" + score);
            }

        }
    };
}
