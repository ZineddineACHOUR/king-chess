package dz.ubpfe.kingchess;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

public class Regle extends Activity implements OnGestureListener {
	
	private static final int SWIPE_MIN_DISTANCE 		= 120; 
	private static final int SWIPE_MAX_OFF_PATH 		= 250; 
	private static final int SWIPE_THRESHOLD_VELOCITY 	= 200;
	
	private Animation 		slideLeftIn; 
	private Animation 		slideLeftOut; 
	private Animation 		slideRightIn; 
	private Animation 		slideRightOut;
	private GestureDetector detector;
	private ViewFlipper     view;	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regles);
        
		detector		= new GestureDetector(this,this);
		view         	= (ViewFlipper)findViewById(R.id.flipper);
		slideLeftIn 	= AnimationUtils.loadAnimation(this, R.anim.slide_left_in); 
		slideLeftOut 	= AnimationUtils.loadAnimation(this, R.anim.slide_left_out); 
		slideRightIn 	= AnimationUtils.loadAnimation(this, R.anim.slide_right_in); 
		slideRightOut 	= AnimationUtils.loadAnimation(this, R.anim.slide_right_out);		
    }
    
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return detector.onTouchEvent(event);
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) return false; 
		if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
		  && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) { 
			view.setInAnimation(slideLeftIn); 
			view.setOutAnimation(slideLeftOut); 
			view.showNext(); 
		}else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
			     && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) { 
			view.setInAnimation(slideRightIn); 
			view.setOutAnimation(slideRightOut); 
			view.showPrevious(); 
		} 		
		return false;
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
