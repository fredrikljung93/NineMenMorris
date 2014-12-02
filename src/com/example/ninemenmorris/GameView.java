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
import android.os.Vibrator;

public class GameView extends View {
	public static final int BLUE_MOVES = 1;
	public static final int RED_MOVES = 2;
	public static final int OUT_OF_BOUNDS = 1337;
	public static final int EMPTY_SPACE = 0;
	public static final int BLUE_MARKER = 4;
	public static final int RED_MARKER = 5;
	boolean timeToRemoveMarker = false;
	int winner = 0;
	int marked = 0;
	Vibrator v;
	NineMenMorrisRules game;
	Rect[] points;
	Rect notGamePlan;
	Rect big;
	Rect medium;
	Rect small;
	ArrayList<Rect> lines;

	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		points = new Rect[25];
		v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(winner!=0){ // Do nothing if winner is annonced
			return false;
		}
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			boolean pressedPoint = false;
			for (int i = 1; i <= 24; i++) {
				if (points[i].contains((int) event.getX(), (int) event.getY())) {
					handleTouch(i);
					pressedPoint = true;
					break;
				}
			}
			if (pressedPoint) {
				v.vibrate(50);
			}
			invalidate(); // RIta om
			return true;
		}

		return false;
	}

	private void handleTouch(int pressedPoint) {
		int player = game.getTurn();
		int playerMatchingMarker = RED_MARKER;
		if (player == BLUE_MOVES) {
			playerMatchingMarker = BLUE_MARKER;
		}
		int marker = game.getMarker(player);
		boolean success = false;

		if (timeToRemoveMarker) {
			success = game.remove(pressedPoint, playerMatchingMarker);
			timeToRemoveMarker = !success;

			if (game.loss(BLUE_MOVES)) {
				winner = RED_MOVES;
				Log.d("Winner", "RED SET TO WINNER");
			} else if (game.loss(RED_MOVES)) {
				winner = BLUE_MOVES;
				Log.d("Winner", "BLUE SET TO WINNER");
			}
			return;
		}

		if (marker > 0) { // If player has unplaced markers
			success = game.legalMove(pressedPoint, OUT_OF_BOUNDS, player);
			checkMoveResult(pressedPoint);
			return;
		}

		if (marked == 0) { // If no point has been marked, mark it
			marked = pressedPoint;
			return;
		}

		if (marked == pressedPoint) { // If player touches marked point, unmark it
			marked = 0;
			return;
		}
		
		if(!(game.board(marked)==playerMatchingMarker)){ // If try to move opponents points
			Log.d("NAUGHTYMOVE", "Pressed "+pressedPoint+" with "+marked+ " marked as player "+player);
			marked=0;
			return;
		}
		if(game.board(pressedPoint)!=EMPTY_SPACE){ // If try to move where there already is a marker
			Log.d("NAUGHTYMOVE", "Pressed "+pressedPoint+" with "+marked+ " marked as player "+player);
			marked=0;
			return;
		}

		success = game.legalMove(pressedPoint, marked, player);
		Log.d("GameView", "Move success= "+success);
		marked = 0;
		
		checkMoveResult(pressedPoint);
	}

	private void checkMoveResult(int pressedPoint) {

		if (game.remove(pressedPoint)) {
			Log.d("GameView", "TimeToRemoveMarker set to true");
			timeToRemoveMarker = true;
		}
		else{
			Log.d("GameView", "TimeToRemoveMarker stays at "+timeToRemoveMarker);
		}

		
	}

	@Override
	protected void onDraw(Canvas canvas) { // Definiera vad som ska ritas
		if (points[1] == null) {
			createRects();
		}
		if (points[1].isEmpty()) {
			createRects();
		}

		Log.i("TouchView.onDraw", "");

		// Background
		Paint blackPaint = new Paint();
		blackPaint.setColor(Color.BLACK);

		Paint markedBorder = new Paint();
		markedBorder.setColor(Color.MAGENTA);
		markedBorder.setStyle(Paint.Style.STROKE);

		Paint redPaint = new Paint();
		redPaint.setColor(Color.RED);

		Paint bluePaint = new Paint();
		bluePaint.setColor(Color.BLUE);

		Paint darkGreenPaint = new Paint();
		darkGreenPaint.setColor(Color.argb(255, 0, 130, 0));

		Paint darkGrayPaint = new Paint();
		darkGrayPaint.setColor(Color.DKGRAY);

		Paint magentaPaint = new Paint();
		magentaPaint.setColor(Color.MAGENTA);
		canvas.drawRect(big, darkGreenPaint);

		for (Rect line : lines) {
			canvas.drawRect(line, blackPaint);
		}

		for (int i = 1; i <= 24; i++) {
			int point = game.board(i);
			switch (point) {
			case RED_MARKER:
				canvas.drawRect(points[i], redPaint);
				break;
			case BLUE_MARKER:
				canvas.drawRect(points[i], bluePaint);
				break;
			default:
				canvas.drawRect(points[i], darkGrayPaint);
			}
			if (marked == i) {
				canvas.drawRect(points[i], markedBorder);
			}
		}
		blackPaint.setTextSize(30);
		int turn = game.getTurn();
		String turnmessage = "REDS TURN";
		if (turn == BLUE_MOVES) {
			turnmessage = "BLUES TURN";
		}
		String statistics = "R: " + game.getMarker(RED_MOVES) + ", B: "
				+ game.getMarker(BLUE_MOVES);
		if (timeToRemoveMarker) {
			switch (turn) {
			case BLUE_MOVES:
				turnmessage = "RED, REMOVE A BLUE MARKER";
				break;
			case RED_MOVES:
				turnmessage = "BLUE, REMOVE A RED MARKER";
				break;
			}
		}
		if (winner == BLUE_MOVES) {
			turnmessage = "BLUE WON THE GAME";
		} else if (winner == RED_MOVES) {
			turnmessage = "RED WON THE GAME";
		}
		canvas.drawText(turnmessage, notGamePlan.left, notGamePlan.top + 30,
				blackPaint);
		canvas.drawText(statistics, notGamePlan.left, notGamePlan.bottom,
				blackPaint);

	}

	private void createRects() {
		boolean landscape = false;
		int width = this.getWidth();
		if (this.getHeight() < width) {
			landscape = true;
			width = this.getHeight();
		}
		int top = 0;
		int bottom = width;
		int left = 0;
		int right = width;
		int smallWidth = width / 3;

		// left top, right bottom
		big = new Rect(left, top, right, bottom);
		medium = new Rect(smallWidth / 2, smallWidth / 2, (width - smallWidth)
				+ (smallWidth / 2), (width - smallWidth) + (smallWidth / 2));
		small = new Rect(smallWidth, smallWidth, width - smallWidth, width
				- smallWidth);

		if (landscape) {
			notGamePlan = new Rect(big.right, 0, this.getWidth(),
					this.getHeight());
		} else {
			notGamePlan = new Rect(0, big.bottom, this.getWidth(),
					this.getHeight());
		}
		int pointWidth = smallWidth / 4;
		points[1] = new Rect(small.left, small.top, small.left + pointWidth,
				small.top + pointWidth);
		points[2] = new Rect(medium.left, medium.top, medium.left + pointWidth,
				medium.top + pointWidth);
		points[3] = new Rect(big.left, big.top, big.left + pointWidth, big.top
				+ pointWidth);
		points[4] = new Rect((small.width() / 2) + small.left
				- (pointWidth / 2), small.top, (small.width() / 2) + small.left
				- (pointWidth / 2) + pointWidth, small.top + pointWidth);
		points[5] = new Rect((medium.width() / 2) + medium.left
				- (pointWidth / 2), medium.top, (medium.width() / 2)
				+ medium.left - (pointWidth / 2) + pointWidth, medium.top
				+ pointWidth);
		points[6] = new Rect((big.width() / 2) + big.left - (pointWidth / 2),
				big.top, (big.width() / 2) + big.left - (pointWidth / 2)
						+ pointWidth, big.top + pointWidth);
		points[7] = new Rect(small.right - pointWidth, small.top, small.right,
				small.top + pointWidth);
		points[8] = new Rect(medium.right - pointWidth, medium.top,
				medium.right, medium.top + pointWidth);
		points[9] = new Rect(big.right - pointWidth, big.top, big.right,
				big.top + pointWidth);
		points[10] = new Rect(small.right - pointWidth, small.top
				+ (small.height() / 2) - (pointWidth / 2), small.right,
				small.top + (small.height() / 2) - (pointWidth / 2)
						+ pointWidth);
		points[11] = new Rect(medium.right - pointWidth, medium.top
				+ (medium.height() / 2) - (pointWidth / 2), medium.right,
				medium.top + (medium.height() / 2) - (pointWidth / 2)
						+ pointWidth);
		points[12] = new Rect(big.right - pointWidth, big.top
				+ (big.height() / 2) - (pointWidth / 2), big.right, big.top
				+ (big.height() / 2) - (pointWidth / 2) + pointWidth);
		points[13] = new Rect(small.right - pointWidth, small.bottom
				- pointWidth, small.right, small.bottom);
		points[14] = new Rect(medium.right - pointWidth, medium.bottom
				- pointWidth, medium.right, medium.bottom);
		points[15] = new Rect(big.right - pointWidth, big.bottom - pointWidth,
				big.right, big.bottom);

		points[16] = new Rect((small.width() / 2) + small.left
				- (pointWidth / 2), small.bottom - pointWidth,
				(small.width() / 2) + small.left - (pointWidth / 2)
						+ pointWidth, small.bottom);
		points[17] = new Rect((medium.width() / 2) + medium.left
				- (pointWidth / 2), medium.bottom - pointWidth,
				(medium.width() / 2) + medium.left - (pointWidth / 2)
						+ pointWidth, medium.bottom);
		points[18] = new Rect((big.width() / 2) + big.left - (pointWidth / 2),
				big.bottom - pointWidth, (big.width() / 2) + big.left
						- (pointWidth / 2) + pointWidth, big.bottom);

		points[19] = new Rect(small.left, small.bottom - pointWidth, small.left
				+ pointWidth, small.bottom);
		points[20] = new Rect(medium.left, medium.bottom - pointWidth,
				medium.left + pointWidth, medium.bottom);
		points[21] = new Rect(big.left, big.bottom - pointWidth, big.left
				+ pointWidth, big.bottom);

		points[22] = new Rect(small.left, small.top + (small.height() / 2)
				- (pointWidth / 2), small.left + pointWidth, small.top
				+ (small.height() / 2) - (pointWidth / 2) + pointWidth);
		points[23] = new Rect(medium.left, medium.top + (medium.height() / 2)
				- (pointWidth / 2), medium.left + pointWidth, medium.top
				+ (medium.height() / 2) - (pointWidth / 2) + pointWidth);
		points[24] = new Rect(big.left, big.top + (big.height() / 2)
				- (pointWidth / 2), big.left + pointWidth, big.top
				+ (big.height() / 2) - (pointWidth / 2) + pointWidth);

		int lineWidth = points[1].width() / 3;
		lines = new ArrayList<Rect>();

		// verticals
		lines.add(new Rect(points[3].centerX() - (lineWidth / 2),
				points[3].top, points[21].centerX() - (lineWidth / 2)
						+ lineWidth, points[21].bottom));
		lines.add(new Rect(points[3].centerX() - (lineWidth / 2),
				points[3].top, points[21].centerX() - (lineWidth / 2)
						+ lineWidth, points[21].bottom));
		lines.add(new Rect(points[9].centerX() - (lineWidth / 2),
				points[9].top, points[15].centerX() - (lineWidth / 2)
						+ lineWidth, points[15].bottom));
		lines.add(new Rect(points[2].centerX() - (lineWidth / 2),
				points[2].top, points[20].centerX() - (lineWidth / 2)
						+ lineWidth, points[20].bottom));
		lines.add(new Rect(points[7].centerX() - (lineWidth / 2),
				points[7].top, points[13].centerX() - (lineWidth / 2)
						+ lineWidth, points[13].bottom));
		lines.add(new Rect(points[6].centerX() - (lineWidth / 2),
				points[6].top, points[4].centerX() - (lineWidth / 2)
						+ lineWidth, points[4].bottom));
		lines.add(new Rect(points[16].centerX() - (lineWidth / 2),
				points[16].top, points[18].centerX() - (lineWidth / 2)
						+ lineWidth, points[18].bottom));
		lines.add(new Rect(points[1].centerX() - (lineWidth / 2),
				points[1].top, points[19].centerX() - (lineWidth / 2)
						+ lineWidth, points[19].bottom));
		lines.add(new Rect(points[8].centerX() - (lineWidth / 2),
				points[8].top, points[14].centerX() - (lineWidth / 2)
						+ lineWidth, points[14].bottom));

		// horisontals
		lines.add(new Rect(points[3].centerX(), points[3].centerY()
				- (lineWidth / 2), points[9].centerX(), points[9].centerY()
				+ (lineWidth / 2)));
		lines.add(new Rect(points[2].centerX(), points[2].centerY()
				- (lineWidth / 2), points[8].centerX(), points[8].centerY()
				+ (lineWidth / 2)));
		lines.add(new Rect(points[1].centerX(), points[1].centerY()
				- (lineWidth / 2), points[7].centerX(), points[7].centerY()
				+ (lineWidth / 2)));
		lines.add(new Rect(points[19].centerX(), points[19].centerY()
				- (lineWidth / 2), points[13].centerX(), points[13].centerY()
				+ (lineWidth / 2)));
		lines.add(new Rect(points[24].centerX(), points[24].centerY()
				- (lineWidth / 2), points[22].centerX(), points[22].centerY()
				+ (lineWidth / 2)));
		lines.add(new Rect(points[10].centerX(), points[10].centerY()
				- (lineWidth / 2), points[12].centerX(), points[12].centerY()
				+ (lineWidth / 2)));
		lines.add(new Rect(points[20].centerX(), points[20].centerY()
				- (lineWidth / 2), points[14].centerX(), points[14].centerY()
				+ (lineWidth / 2)));
		lines.add(new Rect(points[21].centerX(), points[21].centerY()
				- (lineWidth / 2), points[15].centerX(), points[15].centerY()
				+ (lineWidth / 2)));

	}

	public void setNineMenMorrisRules(NineMenMorrisRules game) {
		this.game = game;
	}

}
