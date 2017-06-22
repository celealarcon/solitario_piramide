package com.example.cele.piramide;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    List<Integer> cards;
    int sum = 0, n_palos =2;
    Card selected;
    ImageButton button;
    ImageButton buttons[] = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttons = new ImageButton[28]; //28 cartas
        cards = new ArrayList<>(13*2*n_palos);
        for(int i=1;i<=13*n_palos;i++) {cards.add(i);cards.add(i);}
        Collections.shuffle(cards); //Desordenar cartas

        for(int i=1;i<=28;i++){
            String str = "button"+i;
            int cardval = cards.remove(0);
            int val= (cardval-1)%13;
            int resID = getResources().getIdentifier(str,"id",getPackageName());
            buttons[i] = (ImageButton)findViewById(resID);
            buttons[i].setOnClickListener(new Card(cardval));
            buttons[i].setTag(val+1);
        }

       // button.setOnDragListener(new CardD(13));
    }
    class Card implements View.OnClickListener {
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
        public void onClick(View view) {
            if(isFree() && !isDescarted){
                if(sum + value == 13){
                    if(selected == null)
                        selected.descart();
                    descart();
                    sum = 0;
               //     Log.i("Click13", "Click13");
                }
                else {
                    sum = value;
                    selected = this;
                }
            }
        }
    }
    class CardD implements View.OnDragListener   {
        public boolean onDrag(View view, DragEvent drag) {
            return false;
        }
    }
}

