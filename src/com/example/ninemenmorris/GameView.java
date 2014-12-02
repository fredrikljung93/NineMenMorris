package com.example.ninemenmorris;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class GameView extends View {
	int marked=0;
	Rect[] points;
	Rect big;
	Rect medium;
	Rect small;
	
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
			
			boolean pressedPoint=false;
			for(int i=1;i<=24;i++){
				if(points[i].contains((int)event.getX(), (int)event.getY())){
					marked=i;
					pressedPoint=true;
					break;
				}
			}
			if(!pressedPoint){
				marked=0;
			}
			invalidate(); // RIta om
			return true;
		}
		
		return false;
	}
	@Override
	protected void onDraw(Canvas canvas) { // Definiera vad som ska ritas
		if(points[1]==null){
			createRects();
		}
		if(points[1].isEmpty()){
			createRects();
		}
		
		Log.i("TouchView.onDraw", "");
		
		// Background
		Paint blackPaint = new Paint();
		blackPaint.setColor(Color.BLACK);
		
		Paint bluePaint = new Paint();
		bluePaint.setColor(Color.BLUE);
		
		Paint redPaint = new Paint();
		redPaint.setColor(Color.RED);
		
		Paint greenPaint = new Paint();
		greenPaint.setColor(Color.GREEN);
		
		Paint darkGrayPaint = new Paint();
		darkGrayPaint.setColor(Color.DKGRAY);
		
		Paint magentaPaint = new Paint();
		magentaPaint.setColor(Color.MAGENTA);
		canvas.drawRect(big,bluePaint);
		canvas.drawRect(medium,greenPaint);
		canvas.drawRect(small,redPaint);
		
		int lineWidth=points[1].width()/3;
		ArrayList<Rect> lines = new ArrayList<Rect>();
		
		//verticals
		lines.add(new Rect(points[3].centerX()-(lineWidth/2), points[3].top,points[21].centerX()-(lineWidth/2)+lineWidth, points[21].bottom));
		lines.add(new Rect(points[3].centerX()-(lineWidth/2), points[3].top,points[21].centerX()-(lineWidth/2)+lineWidth, points[21].bottom));
		lines.add(new Rect(points[9].centerX()-(lineWidth/2), points[9].top,points[15].centerX()-(lineWidth/2)+lineWidth, points[15].bottom));
		lines.add(new Rect(points[2].centerX()-(lineWidth/2), points[2].top,points[20].centerX()-(lineWidth/2)+lineWidth, points[20].bottom));
		lines.add(new Rect(points[7].centerX()-(lineWidth/2), points[7].top,points[13].centerX()-(lineWidth/2)+lineWidth, points[13].bottom));
		lines.add(new Rect(points[6].centerX()-(lineWidth/2), points[6].top,points[4].centerX()-(lineWidth/2)+lineWidth, points[4].bottom));
		lines.add(new Rect(points[16].centerX()-(lineWidth/2), points[16].top,points[18].centerX()-(lineWidth/2)+lineWidth, points[18].bottom));
		lines.add(new Rect(points[1].centerX()-(lineWidth/2), points[1].top,points[19].centerX()-(lineWidth/2)+lineWidth, points[19].bottom));
		lines.add(new Rect(points[8].centerX()-(lineWidth/2), points[8].top,points[14].centerX()-(lineWidth/2)+lineWidth, points[14].bottom));
		
		
		//horisontals
		lines.add(new Rect(points[3].centerX(),points[3].centerY()-(lineWidth/2),points[9].centerX(),points[9].centerY()+(lineWidth/2)));
		lines.add(new Rect(points[2].centerX(),points[2].centerY()-(lineWidth/2),points[8].centerX(),points[8].centerY()+(lineWidth/2)));
		lines.add(new Rect(points[1].centerX(),points[1].centerY()-(lineWidth/2),points[7].centerX(),points[7].centerY()+(lineWidth/2)));
		lines.add(new Rect(points[19].centerX(),points[19].centerY()-(lineWidth/2),points[13].centerX(),points[13].centerY()+(lineWidth/2)));
		lines.add(new Rect(points[24].centerX(),points[24].centerY()-(lineWidth/2),points[22].centerX(),points[22].centerY()+(lineWidth/2)));
		lines.add(new Rect(points[10].centerX(),points[10].centerY()-(lineWidth/2),points[12].centerX(),points[12].centerY()+(lineWidth/2)));
		lines.add(new Rect(points[20].centerX(),points[20].centerY()-(lineWidth/2),points[14].centerX(),points[14].centerY()+(lineWidth/2)));
		lines.add(new Rect(points[21].centerX(),points[21].centerY()-(lineWidth/2),points[15].centerX(),points[15].centerY()+(lineWidth/2)));
		for(Rect line:lines){
			canvas.drawRect(line, blackPaint);
		}
		
		for(int i=1;i<=24;i++){
			if(points[i]!=null){
				if(marked==i){
				canvas.drawRect(points[i], magentaPaint);
				}
				else{
					canvas.drawRect(points[i], darkGrayPaint);
				}
			}
		}

	}
	
	private void createRects(){

		int width=this.getWidth();
		if(this.getHeight()<width){
			width=this.getHeight();
		}
		int top=0;
		int bottom=width;
		int left=0;
		int right=width;
		int smallWidth=width/3;
		big=new Rect(left, top, right, bottom);
		medium=new Rect(smallWidth/2, smallWidth/2, (width-smallWidth)+(smallWidth/2), (width-smallWidth)+(smallWidth/2));
		small=new Rect(smallWidth, smallWidth, width-smallWidth, width-smallWidth);
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
	}

}
