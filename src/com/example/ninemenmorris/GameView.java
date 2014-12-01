package com.example.ninemenmorris;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class GameView extends View {
	public GameView(Context context) {
		super(context);
		
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.i("TouchView.onTouchEvent", "event = " + event);
		
		if(event.getAction() == MotionEvent.ACTION_DOWN || 
				event.getAction() == MotionEvent.ACTION_UP) {
			
			// Skärmen är tryckt. Do stuff
			invalidate(); // RIta om
			return true;
		}
		
		return false;
	}
	@Override
	protected void onDraw(Canvas canvas) { // Definiera vad som ska ritas
		
		Log.i("TouchView.onDraw", "");
		
		// Background
		Paint bgPaint = new Paint();
		bgPaint.setColor(Color.BLACK);
		
		Paint bluePaint = new Paint();
		bluePaint.setColor(Color.BLUE);
		canvas.drawPaint(bgPaint);
		
		
		int width=this.getWidth();
		if(this.getHeight()<width){
			width=this.getHeight();
		}
		int top=0;
		int bottom=width;
		int left=0;
		int right=width;
		
		Rect rect=new Rect(left, top, right, bottom);
		canvas.drawRect(rect,bluePaint);

	}

}
