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
    ImageButton deckbutton;
    Card deck;
    ImageButton carddeck;
    ImageButton descarts;
    int valuecarddeck = 0;
    ImageButton buttons[][] = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttons = new ImageButton[7][7]; //28 cartas
        cards = new ArrayList<>(13*2*n_palos);

        deckbutton = (ImageButton)findViewById(R.id.maso);
        carddeck = (ImageButton)findViewById(R.id.card);
        descarts = (ImageButton)findViewById(R.id.descarte);
        deckbutton.setOnClickListener(new clickCartaMaso());
        deck = new Card(valuecarddeck);
        carddeck.setOnClickListener(deck);
        carddeck.setTag("free");

        for(int i=1;i<=13*n_palos;i++) {cards.add(i);cards.add(i);}
        Collections.shuffle(cards); //Desordenar cartas
        cards.add(99);
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
        int value, x ,y;
        boolean isMaso;

        public Card(int _value, int _x, int _y){
            x = _x;
            y = _y;
            value = _value;
            isDescarted = false;
            isMaso = false;
        }
        public Card(int _value){
            value = _value;
            isMaso = true;
            isDescarted = false;
        }
        public void setValue(int _value){
            value = _value;
        }
        public void descart(){
            Log.i("descart",""+value);
            if(isMaso) {
                Log.i("descartMASO",""+value);
                valuecarddeck = 0;
                carddeck.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.blanco));
                return;
            }
            buttons[x][y].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.blanco));
            isDescarted = true;
            buttons[x][y].setTag("descart");
        }
        private boolean isFree(){
            if(isMaso) return true;
            if(buttons[x][y].getTag().equals("free")) return true;
            return false;
        }
        public void onClick(View view) {
            Log.i("click", ""+value);
            if(isFree() && !isDescarted){
                Log.i("clickYES", ""+value);
                if(sum + value == 13){
                    Log.i("click13", ""+value);
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

    class clickCartaMaso implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(valuecarddeck != 0) cards.add(valuecarddeck);
            valuecarddeck = cards.remove(0);
            if(valuecarddeck == 99){
                carddeck.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.blanco));
                deck.setValue(0);
            }
            else {
                int imgID = getResources().getIdentifier("c" + valuecarddeck, "drawable", getPackageName());
                carddeck.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), imgID));
                deck.setValue((valuecarddeck-1)%13 +1);
            }
        }
    }


    class CardD implements View.OnDragListener   {
        public boolean onDrag(View view, DragEvent drag) {
            return false;
        }
    }
}

