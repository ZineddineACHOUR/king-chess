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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	Button connexion, nouveau;
	EditText et, pass;
	static String Username;
	String Password;
	TextView tv;
	HttpPost httppost;
	StringBuffer buffer;
	HttpResponse response;
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_in);

		connexion = (Button) findViewById(R.id.btnLogin);
		nouveau = (Button) findViewById(R.id.btnNouveau);
		et = (EditText) findViewById(R.id.loginPseudo);
		pass = (EditText) findViewById(R.id.loginPassword);
		tv = (TextView) findViewById(R.id.login_error);

		nouveau.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intNouveau = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intNouveau);
			}
		});

		connexion.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				login();

				Username = et.getText().toString();
				Password = pass.getText().toString();
				

			}
		});
	}

	void login() {
		try {

			httpclient = new DefaultHttpClient();
			httppost = new HttpPost("http://192.168.1.5/BddEchecs/Connexion.php"); // make
																		// sure
																		// the
																		// url
																		// is
																		// correct.
			// add your data
			nameValuePairs = new ArrayList<NameValuePair>(2);
			// Always use the same variable name for posting i.e the android
			// side variable name and php side variable name should be similar,
			nameValuePairs.add(new BasicNameValuePair("Username", et.getText()
					.toString().trim())); // $Edittext_value =
											// $_POST['Edittext_value'];

			nameValuePairs.add(new BasicNameValuePair("Password", pass
					.getText().toString().trim()));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// Execute HTTP Post Request
			response = httpclient.execute(httppost);
			// edited by James from coderzheaven.. from here....
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String response = httpclient.execute(httppost,
					responseHandler);
			// System.out.println("Response : " + response);
			Toast.makeText(this, response, Toast.LENGTH_LONG).show();

			runOnUiThread(new Runnable() {
				public void run() {
					tv.setText("Reponse  : " + response);

				}
			});

			if (response.equalsIgnoreCase("connexion etablie")) {
				
				Intent in = new Intent(LoginActivity.this,
						FenetreUtilisateur.class);
				startActivity(in);

			} else {
				showAlert();
			}

		} catch (Exception e) {

			System.out.println("Exception : " + e.getMessage());
		}
	}

	public void showAlert() {
		LoginActivity.this.runOnUiThread(new Runnable() {
			public void run() {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						LoginActivity.this);
				builder.setTitle("Erreur de connexion.");
				builder.setMessage("pseudo ou mot de passe incorrecte.")
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
}