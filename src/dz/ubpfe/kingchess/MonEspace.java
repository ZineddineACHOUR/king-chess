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

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MonEspace extends Activity {

	int tailleAncienPass, tailleNouveauPass, tailleConfirmation;
	String oldPass, newPass, confirmation;
	Button modifierCompte, supprimerCompte, enregistrer;
	TextView pseudo, ancienPass, nouveuPass, confirmerPass, reponse;
	EditText ancienMp, nouveauMp, confirmerMp;

	ScrollView scroll;
	HttpResponse response;
	HttpClient httpclient;
	HttpPost httppost;
	List<NameValuePair> nameValuePairs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mon_espace);

		modifierCompte = (Button) findViewById(R.id.ModifierCompte);
		supprimerCompte = (Button) findViewById(R.id.SupprimerCompte);
		enregistrer = (Button) findViewById(R.id.Enregistrer);

		ancienMp = (EditText) findViewById(R.id.ancienMP);
		nouveauMp = (EditText) findViewById(R.id.NouveauMP);
		confirmerMp = (EditText) findViewById(R.id.ConfirmerMP);

		pseudo = (TextView) findViewById(R.id.Pseudo);
		ancienPass = (TextView) findViewById(R.id.AncienPass);
		nouveuPass = (TextView) findViewById(R.id.NouveauPass);
		confirmerPass = (TextView) findViewById(R.id.ConfirmerPass);
		reponse = (TextView) findViewById(R.id.Reponse);

		scroll = (ScrollView) findViewById(R.id.scroll);

		oldPass = ancienMp.getText().toString();
		newPass = nouveauMp.getText().toString();
		confirmation = confirmerMp.getText().toString();

		tailleAncienPass = ancienMp.getText().toString().length();
		tailleNouveauPass = nouveauMp.getText().toString().length();
		tailleConfirmation = confirmerMp.getText().toString().length();

		scroll.setVisibility(View.INVISIBLE);
		pseudo.setText(LoginActivity.Username);
		pseudo.setVisibility(View.INVISIBLE);

		modifierCompte.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				scroll.setVisibility(View.VISIBLE);

			}
		});

		supprimerCompte.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						MonEspace.this);

				// Le titre
				alertDialog.setTitle("Suppression");

				// Le message
				alertDialog
						.setMessage("Voulez-vous vraiment supprimer votre compte ?");

				// L'icone
				alertDialog.setIcon(android.R.drawable.ic_menu_info_details);

				// Le premier bouton "Oui" ( positif )
				alertDialog.setPositiveButton("OUI",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								supprimerCompte();

							}
						});
				
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

		enregistrer.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				oldPass = ancienMp.getText().toString();
				newPass = nouveauMp.getText().toString();
				confirmation = confirmerMp.getText().toString();

				tailleAncienPass = ancienMp.getText().toString().length();
				tailleNouveauPass = nouveauMp.getText().toString().length();
				tailleConfirmation = confirmerMp.getText().toString().length();

				if ((tailleAncienPass == 0) || (tailleNouveauPass == 0)
						|| (tailleConfirmation == 0)) {

					Toast.makeText(MonEspace.this,
							"veuillez remplir tous les champs",
							Toast.LENGTH_SHORT).show();

				} else {
					if (newPass.equals(confirmation)) {
						ChangerMp();
						/*
						 * Intent in1 = new Intent(MonEspace.this,
						 * FenetreUtilisateur.class); startActivity(in1);
						 */
					} else {
						Toast.makeText(
								MonEspace.this,
								"le mot de passe ne correspond pas à la confirmation",
								Toast.LENGTH_LONG).show();

						nouveauMp.setText("");
						nouveauMp.setBackgroundColor(Color.RED);
						confirmerMp.setText("");
						confirmerMp.setBackgroundColor(Color.RED);

						// Intent in1=new
						// Intent(MonEspace.this,FenetreUtilisateur.class);
						// startActivity(in1);

					}

				}
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mon_espace, menu);
		return true;
	}

	void ChangerMp() {
		try {

			httpclient = new DefaultHttpClient();
			httppost = new HttpPost(
					"http://192.168.1.5/BddEchecs/ModifierMotDePasse.php"); // make
			// sure
			// the
			// url
			// is
			// correct.
			// add your data
			nameValuePairs = new ArrayList<NameValuePair>(2);
			// Always use the same variable name for posting i.e the android
			// side variable name and php side variable name should be similar,
			nameValuePairs.add(new BasicNameValuePair("pseudo", pseudo
					.getText().toString().trim())); // $Edittext_value =
													// $_POST['Edittext_value'];

			nameValuePairs.add(new BasicNameValuePair("oldPassword", ancienMp
					.getText().toString().trim()));
			nameValuePairs.add(new BasicNameValuePair("newPassword", nouveauMp
					.getText().toString().trim()));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			response = httpclient.execute(httppost);
			// edited by James from coderzheaven.. from here....
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String response = httpclient.execute(httppost,
					responseHandler);
			// System.out.println("Response : " + response);
			// reponse.setText(response);

			// Toast.makeText(this,response, Toast.LENGTH_LONG).show();

			runOnUiThread(new Runnable() {
				public void run() {
					reponse.setText(response);

				}
			});

			

				Intent in1 = new Intent(this, FenetreUtilisateur.class);
				startActivity(in1);

			

		} catch (Exception e) {

			System.out.println("Exception : " + e.getMessage());
		}
	}

	void supprimerCompte() {
		try {

			httpclient = new DefaultHttpClient();
			httppost = new HttpPost(
					"http://192.168.1.5/BddEchecs/supprimerCompte.php"); // make
			// sure
			// the
			// url
			// is
			// correct.
			// add your data
			nameValuePairs = new ArrayList<NameValuePair>(2);
			// Always use the same variable name for posting i.e the android
			// side variable name and php side variable name should be similar,
			nameValuePairs.add(new BasicNameValuePair("pseudo", pseudo
					.getText().toString().trim())); // $Edittext_value =
													// $_POST['Edittext_value'];

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			response = httpclient.execute(httppost);
			// edited by James from coderzheaven.. from here....
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String response = httpclient.execute(httppost,
					responseHandler);
			// System.out.println("Response : " + response);
			// reponse.setText(response);

			// Toast.makeText(this,response, Toast.LENGTH_LONG).show();

			runOnUiThread(new Runnable() {
				public void run() {

				}
			});

			if (response.equalsIgnoreCase("Le compte a bien ete supprimee")) {

				Intent in = new Intent(MonEspace.this, MenuPrincipale.class);
				startActivity(in);

			} else {

			}

		} catch (Exception e) {

			System.out.println("Exception : " + e.getMessage());
		}
	}

}
