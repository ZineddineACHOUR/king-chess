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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	Button btnRegister;
	Button btnLinkToLogin,btnVerif;
	EditText enregNom,enregPrenom,enregPass,enregMail,enregPseudo,enregDns,enregMobile,confirmPass;
	TextView registerErrorMsg,verifPseudo;
	int tailleNom,taillePrenom,taillePseudo,tailleDns,tailleEmail,tailleMobile,taillePasse,tailleConfirmation;
	List<NameValuePair> nameValuePairs;
	HttpClient httpClient;
	HttpPost httppost;
	String pseudo,nom,prenom,email,mobile,password,dns,confirmation;
	HttpResponse response,reponse1;
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		// Importing all assets like buttons, text fields
		enregPseudo=(EditText)findViewById(R.id.EnregPseudo);
		enregNom = (EditText) findViewById(R.id.EnregNom);
		
		enregPrenom = (EditText) findViewById(R.id.EnregPrenom);
		enregMail=(EditText)findViewById(R.id.EnregEmail);
		enregDns=(EditText)findViewById(R.id.EnregDns);
		enregMobile=(EditText)findViewById(R.id.EnregMobile);
		enregPass = (EditText) findViewById(R.id.EnregPassw);
		confirmPass=(EditText)findViewById(R.id.ConfirmPass);
		
		btnRegister = (Button) findViewById(R.id.btnRegister);
		
		btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
		btnVerif=(Button)findViewById(R.id.btnVerifier);
		
		registerErrorMsg = (TextView) findViewById(R.id.register_error);
		verifPseudo=(TextView)findViewById(R.id.verifPseudo);
		
		enregNom.setEnabled(false);
		enregPrenom.setEnabled(false);
		enregMail.setEnabled(false);
		enregDns.setEnabled(false);
		enregMobile.setEnabled(false);
		enregPass.setEnabled(false);
		confirmPass.setEnabled(false);
		btnRegister.setEnabled(false);
		
		
		
		
		btnVerif.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pseudo=enregPseudo.getText().toString();
				int tailleP=pseudo.length();
				if(tailleP>0){
				verifier();}else{
					verifPseudo.setText("le champ est vide");
				}
			}
		});
		
		
		// Register Button Click event
		btnRegister.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View view) {
				/* recuperer*/
				 pseudo=enregPseudo.getText().toString();
				 nom = enregNom.getText().toString();
				prenom= enregPrenom.getText().toString();
				email = enregMail.getText().toString();
				dns=enregDns.getText().toString();		
				mobile=enregMobile.getText().toString();
				password = enregPass.getText().toString();
				confirmation=confirmPass.getText().toString(); 
				/* calcul de la taille pour verifier
			
				 */
				
				
				
				taillePseudo=pseudo.length();
				tailleNom=nom.length();
	            taillePrenom=prenom.length();
				tailleEmail=email.length();
				tailleDns=dns.length();
				tailleMobile=mobile.length();
				taillePasse=password.length();
				tailleConfirmation=confirmation.length();
				
				if ((taillePseudo>0)&&(tailleNom>0)&&(taillePrenom>0)&&(tailleEmail>0)&&(tailleDns>0)
				    &&(tailleMobile>0) &&(taillePasse>0)&&(tailleConfirmation>0)){
					if(password.equals(confirmation)){
						ajout();
						
						
						
					} else {
						Toast.makeText(RegisterActivity.this, "Erreur d'enregistrement",Toast.LENGTH_SHORT).show();
						registerErrorMsg.setText("Le mot de passe est différent de la confirmation");
						enregPass.setText("");
						confirmPass.setText("");
						enregPass.setBackgroundColor(Color.RED);
						confirmPass.setBackgroundColor(Color.RED);
					}
						
					} else {
						Toast.makeText(RegisterActivity.this, "Erreur d'enregistrement",Toast.LENGTH_SHORT).show();
						registerErrorMsg.setText("Veuillez remplir tous les champs");
					}
			}
		});

		// Link to Login Screen
		btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(i);
				// Close Registration View
				finish();
			}
		});
	}
	
	public void ajout(){
		try {

			httpClient = new DefaultHttpClient();
			httppost = new HttpPost("http://192.168.1.5/BddEchecs/ajout.php"); // make
																		// sure
																		// the
																		// url
																		// is
																		// correct.
			// add your data
			nameValuePairs = new ArrayList<NameValuePair>(2);
			// Always use the same variable name for posting i.e the android
			// side variable name and php side variable name should be similar,
			nameValuePairs.add(new BasicNameValuePair("pseudo", enregPseudo.getText()
					.toString())); // 

			nameValuePairs.add(new BasicNameValuePair("nom",enregNom.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("prenom",enregPrenom.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("email",enregMail.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("password",enregPass.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("dns",enregDns.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("mobile", enregMobile.getText().toString()));
			
			// c est ici que sa se passse l'ajout !!!
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// Execute HTTP Post Request
			response = httpClient.execute(httppost);
			// edited by James from coderzheaven.. from here....
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String response = httpClient.execute(httppost,
					responseHandler);
			// System.out.println("Response : " + response);
			Toast.makeText(this, response, Toast.LENGTH_LONG).show();

			runOnUiThread(new Runnable() {
				public void run() {
					registerErrorMsg.setText("Response from PHP : " + response);

				}
			});

			if (response.equalsIgnoreCase("le compte a bien ete ajoute")) {
				runOnUiThread(new Runnable() {
					public void run() {
						
						/*
						 * TextView tv2 = (TextView) findViewById(R.id.tv2);
						 * tv2.setText("hello");
						 */
					}
				});
				Intent in = new Intent(RegisterActivity.this,
						LoginActivity.class);
				startActivity(in);

			} else {
				showAlert();
			}

		} catch (Exception e) {

			System.out.println("Exception : " + e.getMessage());
		}
	}
	
	public void showAlert() {
		RegisterActivity.this.runOnUiThread(new Runnable() {
			public void run() {
				AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
				builder.setTitle("Login Error.");
				builder.setMessage("User not Found.")
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
	}
	
	public void verifier(){
		try {
		
		httpClient = new DefaultHttpClient();
		httppost = new HttpPost("http://192.168.1.5/BddEchecs/verifierPseudo.php");
		
		nameValuePairs = new ArrayList<NameValuePair>(2);
		// Always use the same variable name for posting i.e the android
		// side variable name and php side variable name should be similar,
		nameValuePairs.add(new BasicNameValuePair("pseudo", enregPseudo.getText()
				.toString())); // $Edittext_value =
		
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		// Execute HTTP Post Request
		reponse1 = httpClient.execute(httppost);
		// edited by James from coderzheaven.. from here....
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		final String reponse1 = httpClient.execute(httppost,responseHandler);
		
		
		runOnUiThread(new Runnable() {
			public void run() {
				registerErrorMsg.setText("Response from PHP : " + reponse1);

			}
		});
		
		if (reponse1.equalsIgnoreCase("pseudo existe")) {
			verifPseudo.setText("Le pseudo est deja utilisé");
			
		}else{
			verifPseudo.setText("Le pseudo est disponible");
			btnRegister.setEnabled(true);
			enregNom.setEnabled(true);
			enregPrenom.setEnabled(true);
			enregDns.setEnabled(true);
			enregMail.setEnabled(true);
			enregMobile.setEnabled(true);
			enregPass.setEnabled(true);
			confirmPass.setEnabled(true);
			
		}
		
		
	}catch (Exception e) {

		System.out.println("Exception : " + e.getMessage());
	}
	}
}
