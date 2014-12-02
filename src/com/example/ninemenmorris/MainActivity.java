package com.example.ninemenmorris;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	private NineMenMorrisRules game;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalState globalState = (GlobalState) this.getApplication();
        GameView view = new GameView(this);
        view.setNineMenMorrisRules(globalState.getGame());
        setContentView(view);
    }
    
    @Override
    public void onResume(){
    	super.onResume();
        GlobalState globalState = (GlobalState) this.getApplication();
    	 GameView view = new GameView(this);
         view.setNineMenMorrisRules(globalState.getGame());
    }
}