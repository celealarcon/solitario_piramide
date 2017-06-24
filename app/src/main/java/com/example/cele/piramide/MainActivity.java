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
    List<Stack> stack;
    int sum = 0, n_palos =4;
    Card selected;
    Card deck;

    ImageButton deckbutton;
    ImageButton carddeck;
    ImageButton descarts;
    ImageButton buttons[][] = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttons = new ImageButton[7][7]; //28 cartas
        cards = new ArrayList<>(13*2*n_palos);
        stack = new ArrayList<>(200);
        deck = new Card(0);
        deckbutton = (ImageButton)findViewById(R.id.maso);
        carddeck = (ImageButton)findViewById(R.id.card);
        descarts = (ImageButton)findViewById(R.id.descarte);
        deckbutton.setOnClickListener(new clickCartaMaso());
        descarts.setOnClickListener(new clickDescart());
        carddeck.setOnClickListener(deck);
        carddeck.setTag("free");

        for(int i=1;i<=13*n_palos;i++) {cards.add(i);}
        Collections.shuffle(cards); //Desordenar cartas

        for(int i=0;i<7;i++){
            for(int j=0;j<i+1;j++){
                String str = "button"+i+""+j;
                int cardval = cards.remove(0);
                int resID = getResources().getIdentifier(str,"id",getPackageName());
                int imgID = getResources().getIdentifier("c"+cardval,"drawable",getPackageName());
                buttons[i][j] = (ImageButton)findViewById(resID);
                buttons[i][j].setOnClickListener(new Card(cardval,i,j));
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
        int carta;
        public Card(int _value, int _x, int _y){
            x = _x;
            y = _y;

            carta = _value;
            value = (carta-1)%13 +1;
            isDescarted = false;
            isMaso = false;
        }
        public Card(int _value){
            carta = _value;
            value = (carta-1)%13 +1;
            isMaso = true;
            isDescarted = false;
        }
        public void setValue(int _value){
            carta = _value;
            value = (carta-1)%13 +1;
        }
        public void descart(){
            if(isMaso) {

                stack.add(new Stack(deck.carta));
                deck.setValue(cards.remove(cards.size()-1));
                int imgID = getResources().getIdentifier("c" + deck.carta, "drawable", getPackageName());
                carddeck.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), imgID));
                return;
            }
            stack.add(new Stack(x,y,carta));
            buttons[x][y].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.blanco));
            buttons[x][y].setTag("descart");
        }
        private boolean isFree(){
            if(isMaso) return true;
            if(buttons[x][y].getTag().equals("free")) return true;
            return false;
        }
        public void onClick(View view) {
            if(isFree()){
                if(value == 13 || sum+value == 13){
                    if(selected != null && sum != 0)
                        if(sum + value == 13){
                            selected.descart();
                        }
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
                        if(i<6){
                            if(buttons[i+1][j].getTag().equals("descart") && buttons[i+1][j+1].getTag().equals("descart")) {
                                buttons[i][j].setTag("free");
                            }
                        }
                        else buttons[i][j].setTag("free");
                    }
        }
    }
    class Stack {
        int x, y, val, carta;
        int imgID;
        boolean isMaso;
        public Stack(int _x, int _y, int _carta){
            x = _x;
            y = _y;
            carta = _carta;
            val = (_carta-1)%13 +1;
            if(carta<=52 && carta>0){
                imgID = getResources().getIdentifier("c" + carta, "drawable", getPackageName());
            }
            isMaso = false;
        }
        public Stack(int _carta){
            isMaso = true;
            carta = _carta;
            val = (_carta-1)%13 +1;
            if(carta<=52 && carta>0) {
                imgID = getResources().getIdentifier("c" + carta, "drawable", getPackageName());
            }
        }
        public int value(){
            return val;
        }
        public int card(){
            return carta;
        }
        public void reapear(){
            if(isMaso) {
                // Si la carta reaparece en el maso
                // //Si es devolver arriba una carta
                if(carta  == 99){
                    cards.add(0,deck.carta);
                    deck.setValue(cards.remove(cards.size()-1));
                    imgID = getResources().getIdentifier("c" + deck.value, "drawable", getPackageName());
                    carddeck.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), imgID));
                    return;
                }
                // Si tiene algun valor
                cards.add(deck.carta);
                deck.setValue(carta);
                //imgID = getResources().getIdentifier("c" + carta, "drawable", getPackageName());
                carddeck.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), imgID));
                return;
            }

            // cuando es una carta de la piramide
            buttons[x][y].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), imgID));
            buttons[x][y].setTag("free");
            if(y>0){
                buttons[x][y-1].setTag("block");
                if(x>0) buttons[x-1][y-1].setTag("block");
            }
        }
    }
    class clickCartaMaso implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            selected = null;
            sum=0;

            cards.add(deck.carta);
            if(deck.value != 0) {
                stack.add(new Stack(99));
            }
            deck.setValue(cards.remove(0));
            if(deck.value == 0){
                carddeck.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.blanco));
            }
            else {
                int imgID = getResources().getIdentifier("c" + deck.carta, "drawable", getPackageName());
                carddeck.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), imgID));
            }
        }
    }
    class clickDescart implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(!stack.isEmpty()){
                Stack s = stack.remove(stack.size()-1);
                s.reapear();
                if(s.value() < 13 && s.card()!=99 && !stack.isEmpty()) {
                    s = stack.remove(stack.size()-1);
                    s.reapear();
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

