package com.example.cele.piramide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    int sum = 0;
    Card selected;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button1);
        button.setOnTouchListener(new Card(13));
       // button.setOnDragListener(new CardD(13));
    }
    class Card implements View.OnTouchListener {
        private Card left;
        private Card right;
        public boolean isFree;
        public boolean isDescarted;
        int value;

        public Card(Card _left, Card _right, int _value){
            left = _left;
            right = _right;
            isFree = false;
            isDescarted = false;
            value = _value;
        }
        public Card(int _value){
            value = _value;
            isFree = true;
            isDescarted = false;
        }
        private boolean isFree(){
            if(left != null && right!= null)
                return left.isDescarted && right.isDescarted;
            return true;
        }
        public void descart(){
            //eliminar del layout
            isDescarted = true;
        }
        public boolean onTouch(View view,  MotionEvent motionEvent) {
            if(isFree() && !isDescarted){
                if(sum + value == 13){
                    if(selected == null)
                        selected.descart();
                    descart();
                    sum = 0;
                    Log.i("Click13", "Click13");
                }
                else {
                    sum = value;
                    selected = this;
                }
                return true;
            }
            return false;
        }
    }
    class CardD implements View.OnDragListener   {
        public boolean onDrag(View view, DragEvent drag) {
            return false;
        }
    }
}

