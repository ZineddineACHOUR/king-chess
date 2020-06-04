package dz.ubpfe.kingchess;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ListActivity;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
public class JoueursEnLigne extends ListActivity {
    /** Called when the activity is first created. */
    
    


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        String result = null;
     InputStream is = null;
     JSONObject json_data=null;
     ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
     ArrayList<String> donnees = new ArrayList<String>();
     
     


   
     try{
     //commandes httpClient
     HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://192.168.1.5/joueurEnLigne.php");
        
        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        is = entity.getContent();
     }
     catch(Exception e){
      Log.i("taghttppost",""+e.toString());
            Toast.makeText(getBaseContext(),e.toString() ,Toast.LENGTH_LONG).show();
       }
   
      
     //conversion de la réponse en chaine de caractère
        try
        {
         BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
        
         StringBuilder sb  = new StringBuilder();
        
         String line = null;
        
         while ((line = reader.readLine()) != null)
         {
         sb.append(line + "\n");
         }
        
         is.close();
        
         result = sb.toString();
        }
        catch(Exception e)
        {
         Log.i("tagconvertstr",""+e.toString());
        }
        //recuperation des donnees json
        try{
          JSONArray jArray = new JSONArray(result);
           
             for(int i=0;i<jArray.length();i++)
             {
           
                   json_data = jArray.getJSONObject(i);
                   donnees.add(json_data.getString("pseudo"));
                   
                  
               }
                setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, donnees));
                
            }
            catch(JSONException e){
             Log.i("tagjsonexp",""+e.toString());
            } catch (ParseException e) {
             Log.i("tagjsonpars",""+e.toString());
       }
        
    }
}