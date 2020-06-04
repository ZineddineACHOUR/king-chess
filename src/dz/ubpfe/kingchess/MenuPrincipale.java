package dz.ubpfe.kingchess;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

public class MenuPrincipale extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_b);
		
		
		TranslateAnimation trans1= new TranslateAnimation(320,0,0,0);
		trans1.setStartOffset(320);
		trans1.setFillAfter(true);
		trans1.setDuration(1000);
		this.findViewById(R.id.ceConnecter).startAnimation(trans1);
		
		TranslateAnimation trans2= new TranslateAnimation(320,0,0,0);
		trans2.setStartOffset(320);
		trans2.setFillAfter(true);
		trans2.setDuration(1500);
		this.findViewById(R.id.jouerEnLocal).startAnimation(trans2);

		TranslateAnimation trans3= new TranslateAnimation(320,0,0,0); // dépare de l'annimation 
		trans3.setStartOffset(320);
		trans3.setFillAfter(true);  // le bouton reste a sa place d'origine
		trans3.setDuration(2000);   //duree de debut de lanimation 
		this.findViewById(R.id.regleDeJeu).startAnimation(trans3);
		
		TranslateAnimation trans4= new TranslateAnimation(320,0,0,0); // dépare de l'annimation 
		trans4.setStartOffset(320);
		trans4.setFillAfter(true);  // le bouton reste a sa place d'origine
		trans4.setDuration(2500);   //duree de debut de lanimation 
		this.findViewById(R.id.apropos).startAnimation(trans4);
		
		

		 final Button authentification = (Button) findViewById(R.id.ceConnecter);
		 authentification.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MenuPrincipale.this, DashboardActivity.class);
				startActivity(intent);
				
			}
		}) ;
		 
		 final Button jouerLocal = (Button) findViewById(R.id.jouerEnLocal);
		 jouerLocal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MenuPrincipale.this, Jouer.class);
				startActivity(intent);
				
			}
		}) ;
		 
		 final Button regles = (Button) findViewById(R.id.regleDeJeu);
		 regles.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MenuPrincipale.this, Regle.class);
				startActivity(intent);
				
			}
		}) ;
		 
		 final Button info = (Button) findViewById(R.id.apropos);
		 info.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MenuPrincipale.this, Info.class);
				startActivity(intent);
				
			}
		}) ;
		 

		
		 
		 
		
		
		}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
