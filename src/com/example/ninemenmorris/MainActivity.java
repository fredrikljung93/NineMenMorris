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
    	//TODO FIXA SÅ MAN KAN VÄNDA SKÄRMEN UTAN ATT SPELET BÖRJAR OM
    }
}