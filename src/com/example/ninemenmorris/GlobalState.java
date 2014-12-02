package com.example.ninemenmorris;

import android.app.Application;

public class GlobalState extends Application {
	private NineMenMorrisRules game;
	public GlobalState(){
		this.game=new NineMenMorrisRules();
	}
	public NineMenMorrisRules getCurrentGame() {
		return game;
	}
	
	public NineMenMorrisRules newGame() {
		this.game = new NineMenMorrisRules();
		return game;
	}
	
	

}
