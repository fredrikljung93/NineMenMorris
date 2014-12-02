package com.example.ninemenmorris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class GameView extends View {
	Rect[] points;
	
	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		points=new Rect[25];
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
		
		Paint redPaint = new Paint();
		redPaint.setColor(Color.RED);
		
		Paint greenPaint = new Paint();
		greenPaint.setColor(Color.GREEN);
		
		Paint darkGrayPaint = new Paint();
		darkGrayPaint.setColor(Color.DKGRAY);
		
		
		int width=this.getWidth();
		if(this.getHeight()<width){
			width=this.getHeight();
		}
		int top=0;
		int bottom=width;
		int left=0;
		int right=width;
		
		
		int smallWidth=width/3;
		Rect big=new Rect(left, top, right, bottom);
		Rect medium=new Rect(smallWidth/2, smallWidth/2, (width-smallWidth)+(smallWidth/2), (width-smallWidth)+(smallWidth/2));
		Rect small=new Rect(smallWidth, smallWidth, width-smallWidth, width-smallWidth);
		
		canvas.drawRect(big,bluePaint);
		canvas.drawRect(medium,greenPaint);
		canvas.drawRect(small,redPaint);
		
		int pointWidth=smallWidth/4;
		
		points[1]=new Rect(small.left, small.top, small.left+pointWidth, small.top+pointWidth);
		points[2]=new Rect(medium.left, medium.top, medium.left+pointWidth, medium.top+pointWidth);
		points[3]=new Rect(big.left, big.top, big.left+pointWidth,big.top+pointWidth);
		points[4]=new Rect((small.width()/2)+small.left-(pointWidth/2), small.top, (small.width()/2)+small.left-(pointWidth/2)+pointWidth,small.top+pointWidth);
		points[5]=new Rect((medium.width()/2)+medium.left-(pointWidth/2), medium.top, (medium.width()/2)+medium.left-(pointWidth/2)+pointWidth,medium.top+pointWidth);
		points[6]=new Rect((big.width()/2)+big.left-(pointWidth/2), big.top, (big.width()/2)+big.left-(pointWidth/2)+pointWidth,big.top+pointWidth);
		points[7]=new Rect(small.right-pointWidth, small.top, small.right, small.top+pointWidth);
		points[8]=new Rect(medium.right-pointWidth, medium.top, medium.right, medium.top+pointWidth);
		points[9]=new Rect(big.right-pointWidth, big.top, big.right, big.top+pointWidth);
		points[10]=new Rect(small.right-pointWidth, small.top+(small.height()/2)-(pointWidth/2), small.right,small.top+(small.height()/2)-(pointWidth/2)+pointWidth);
		points[11]=new Rect(medium.right-pointWidth, medium.top+(medium.height()/2)-(pointWidth/2), medium.right,medium.top+(medium.height()/2)-(pointWidth/2)+pointWidth);
		points[12]=new Rect(big.right-pointWidth, big.top+(big.height()/2)-(pointWidth/2), big.right,big.top+(big.height()/2)-(pointWidth/2)+pointWidth);
		points[13]=new Rect(small.right-pointWidth, small.bottom-pointWidth, small.right, small.bottom);
		points[14]=new Rect(medium.right-pointWidth, medium.bottom-pointWidth, medium.right, medium.bottom);
		points[15]=new Rect(big.right-pointWidth, big.bottom-pointWidth, big.right, big.bottom);
		
		points[16]=new Rect((small.width()/2)+small.left-(pointWidth/2), small.bottom-pointWidth, (small.width()/2)+small.left-(pointWidth/2)+pointWidth,small.bottom);
		points[17]=new Rect((medium.width()/2)+medium.left-(pointWidth/2), medium.bottom-pointWidth, (medium.width()/2)+medium.left-(pointWidth/2)+pointWidth,medium.bottom);
		points[18]=new Rect((big.width()/2)+big.left-(pointWidth/2), big.bottom-pointWidth, (big.width()/2)+big.left-(pointWidth/2)+pointWidth,big.bottom);
		
		points[19]=new Rect(small.left, small.bottom-pointWidth, small.left+pointWidth, small.bottom);
		points[20]=new Rect(medium.left, medium.bottom-pointWidth, medium.left+pointWidth, medium.bottom);
		points[21]=new Rect(big.left, big.bottom-pointWidth, big.left+pointWidth, big.bottom);
		
		points[22]=new Rect(small.left, small.top+(small.height()/2)-(pointWidth/2), small.left+pointWidth,small.top+(small.height()/2)-(pointWidth/2)+pointWidth);
		points[23]=new Rect(medium.left, medium.top+(medium.height()/2)-(pointWidth/2), medium.left+pointWidth,medium.top+(medium.height()/2)-(pointWidth/2)+pointWidth);
		points[24]=new Rect(big.left, big.top+(big.height()/2)-(pointWidth/2), big.left+pointWidth,big.top+(big.height()/2)-(pointWidth/2)+pointWidth);
		for(int i=1;i<=24;i++){
			if(points[i]!=null){
				canvas.drawRect(points[i], darkGrayPaint);
			}
		}

	}

}
