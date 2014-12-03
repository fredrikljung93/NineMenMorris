package com.example.ninemenmorris;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onResume(){
    	super.onResume();
        GlobalState globalState = (GlobalState) this.getApplication();
    	 GameView view = new GameView(this);
         view.setNineMenMorrisRules(globalState.getCurrentGame());
         setContentView(view);
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		 GlobalState globalState = (GlobalState) this.getApplication();
		if(id==R.id.newgames){
			GameView newGame = new GameView(this);
			newGame.setNineMenMorrisRules(globalState.newGame());
			setContentView(newGame);
		}
		return super.onOptionsItemSelected(item);
	}

}