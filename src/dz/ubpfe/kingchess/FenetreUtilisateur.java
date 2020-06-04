package dz.ubpfe.kingchess;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FenetreUtilisateur extends Activity {

	ProgressBar progressBarre;
	int progress = 0;
	Handler h = new Handler();
	TextView pseudo;
	Button gererCompte, afficherListe, deconnexion;
	List<NameValuePair> nameValuePairs;
	HttpClient httpClient;
	HttpPost httppost;
	HttpResponse reponse;
	String Pseudo;

	@Override
protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fenetre_utilisateur);

		gererCompte = (Button) findViewById(R.id.btnGererCompte);
		afficherListe = (Button) findViewById(R.id.btnListeJoueurs);
		deconnexion = (Button) findViewById(R.id.btnDeconnexion);
		pseudo = (TextView) findViewById(R.id.pseudo);

		pseudo.setTextColor(Color.BLACK);
		pseudo.setText(LoginActivity.Username);
		progressBarre = (ProgressBar) findViewById(R.id.barre);
		progressBarre.setVisibility(View.INVISIBLE);
		
		Pseudo=pseudo.getText().toString();
		
		
		gererCompte.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent in=new Intent(FenetreUtilisateur.this,MonEspace.class);
				startActivity(in);
				
			}
		});
		
		afficherListe.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent in=new Intent(FenetreUtilisateur.this,JoueursEnLigne.class);
				startActivity(in);
				
			}
		});
		

		deconnexion.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				FenetreUtilisateur.this.runOnUiThread(new Runnable() {
					public void run() {
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(
								FenetreUtilisateur.this);

						// Le titre
						alertDialog.setTitle("Deconnexion");

						// Le message
						alertDialog
								.setMessage("Voulez-vous vraiment vous deconnecter?");

						// L'icone
						alertDialog.setIcon(android.R.drawable.ic_menu_info_details);

						// Le premier bouton "Oui" ( positif )
						alertDialog.setPositiveButton("OUI",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {

										deconnexion();
										new Thread(new Runnable() {

											@Override
											public void run() {

												// TODO Auto-generated method
												// stub
												for (int i = 0; i < 2; i++) {
													progress += 50;
													h.post(new Runnable() {

														@Override
														public void run() {
															progressBarre
																	.setVisibility(View.VISIBLE);
															// TODO
															// Auto-generated
															// method stub
															progressBarre
																	.setProgress(progress);
															if (progress == progressBarre
																	.getMax()) {
																Intent in = new Intent(
																		FenetreUtilisateur.this,
																		MenuPrincipale.class);
																startActivity(in);

															}
														}
													});
													try {
														Thread.sleep(2000);
													} catch (InterruptedException e) {
														// TODO: handle
														// exception
													}
												}

											}
										}).start();

									}
								});

						// Le deuxième bouton "NON" ( négatif )
						alertDialog.setNegativeButton("NON",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {

										dialog.cancel();
									}
								});

						// Affiche la boite du dialogue
						alertDialog.show();
					}
				});

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fenetre_utilisateur, menu);
		return true;
	}

	public void deconnexion() {
		try {

			httpClient = new DefaultHttpClient();
			httppost = new HttpPost(
					"http://192.168.1.5/BddEchecs/Deconnexion.php"); // make
			// sure
			// the
			// url
			// is
			// correct.
			// add your data
			nameValuePairs = new ArrayList<NameValuePair>(2);
			// Always use the same variable name for posting i.e the android
			// side variable name and php side variable name should be similar,
			nameValuePairs.add(new BasicNameValuePair("Pseudo", pseudo.getText().toString())); 

			// c est ici que sa se passse l'ajout !!!
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// Execute HTTP Post Request
			reponse = httpClient.execute(httppost);
			// edited by James from coderzheaven.. from here....
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String response = httpClient.execute(httppost,
					responseHandler);
			// System.out.println("Response : " + response);

		} catch (Exception e) {

			System.out.println("Exception : " + e.getMessage());
		}
	}

}
