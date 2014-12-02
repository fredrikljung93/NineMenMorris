package com.example.ninemenmorris;

import android.app.Application;

public class GlobalState extends Application {
	private NineMenMorrisRules game;
	public GlobalState(){
		setGame(new NineMenMorrisRules());
	}
	public NineMenMorrisRules getGame() {
		return game;
	}
	public void setGame(NineMenMorrisRules game) {
		this.game = game;
	}

}
