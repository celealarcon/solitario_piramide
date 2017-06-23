package com.example.cele.piramide;

import android.media.Image;
import android.support.v4.content.ContextCompat;
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
    ImageButton buttons[][] = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttons = new ImageButton[7][7]; //28 cartas
        cards = new ArrayList<>(13*2*n_palos);
        for(int i=1;i<=13*n_palos;i++) {cards.add(i);cards.add(i);}
        Collections.shuffle(cards); //Desordenar cartas
        Card aux1, aux2;
        for(int i=0;i<7;i++){
            for(int j=0;j<i+1;j++){
                int aux = i+1;
                String str = "button"+i+""+j;
                int cardval = cards.remove(0);
                int val= (cardval-1)%13;
                int resID = getResources().getIdentifier(str,"id",getPackageName());
                int imgID = getResources().getIdentifier("c"+cardval,"drawable",getPackageName());
                buttons[i][j] = (ImageButton)findViewById(resID);
                buttons[i][j].setOnClickListener(new Card(val+1,i,j));
                buttons[i][j].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), imgID));
                if(i==6)buttons[i][j].setTag("free");
                else  buttons[i][j].setTag("block");
            }
        }
    }
    class Card implements View.OnClickListener {
        public boolean isDescarted;
        int value, pos,x ,y;

        public Card(int _value, int _x, int _y){
            x = _x;
            y = _y;
            value = _value;
            isDescarted = false;
        }

        public void descart(){
            buttons[x][y].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.blanco));
            isDescarted = true;
            buttons[x][y].setTag("descart");
        }
        private boolean isFree(){
            if(buttons[x][y].getTag().equals("free")) return true;
            return false;
        }
        public void onClick(View view) {
            if(isFree() && !isDescarted){
                if(sum + value == 13){
                    if(selected != null)
                        selected.descart();
                    descart();
                    sum = 0;

                }
                else {
                    sum = value;
                    selected = this;
                }
            }


            for(int i=0;i<7;i++)
                for(int j=0;j<i+1;j++)
                    if(buttons[i][j].getTag().equals("block")){
                        if(buttons[i+1][j].getTag().equals("descart") && buttons[i+1][j+1].getTag().equals("descart")) {
                            buttons[i][j].setTag("free");
                            Log.i("free","button"+i+""+j);
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

