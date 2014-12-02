package com.example.ninemenmorris;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	private NineMenMorrisRules game;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new NineMenMorrisRules();
        GameView view = new GameView(this);
        view.setNineMenMorrisRules(game);
        setContentView(view);
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	//TODO FIXA S� MAN KAN V�NDA SK�RMEN UTAN ATT SPELET B�RJAR OM
    }
}