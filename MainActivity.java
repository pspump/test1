package com.example.projdatabase;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    SessionManager sessionManager;
    String ipConn = "";

    private String URL = "";
    private EditText textUsername1,textPassword1;
    Button Btn_Register;
    private String jsonResult;
    ProgressBar pB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(getApplicationContext());
        URL = "http://"+sessionManager.getIpconfig().toString()+"app_login.php/";

        textUsername1 = findViewById(R.id.txtuser1);
        textPassword1 = findViewById(R.id.txtpsword1);

    }


    public void GoMainMenu(View view) {
        if (textUsername1.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "กรุณากรอก ชื่อผู้ใช้!",
                    Toast.LENGTH_LONG).show();


            return;

        } else if (textPassword1.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "กรุณากรอกรหัสผ่าน!",
                    Toast.LENGTH_LONG).show();

            return;
        } else {
           accessWebService(URL);
        }
    }

        public class JsonReadTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(params[0]);
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            private StringBuilder inputStreamToString(InputStream is) {
                String rLine = "";
                StringBuilder answer = new StringBuilder();
                BufferedReader rd = new BufferedReader(new
                        InputStreamReader(is));
                try {
                    while ((rLine = rd.readLine()) != null) {
                        answer.append(rLine);

                    }
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Error..." + e.toString(), Toast.LENGTH_LONG).show();
                }
                return answer;
            }

            public void onPostExecute(String result) {
                ListDrwaer();
            }
        }


     //   @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)

        public void accessWebService (String URLS){

            MainActivity.JsonReadTask task = new MainActivity.JsonReadTask();
            // passes values for the urls string array
            URLS = URL + "username=" + textUsername1.getText().toString().trim();
            URLS = URL + "password=" + textPassword1.getText().toString().trim();

            task.execute(new String[]{URLS});
        }

        public void ListDrwaer () {
            ShowWarning("CCC");
            ShowWarning(textUsername1.toString());
            ShowWarning(textPassword1.toString());

            try {
             JSONObject jsonResponse = new JSONObject(jsonResult);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("data");
                //ShowWarning("DDD");
                if (jsonMainNode.length() == 0) {
                    ShowWarning("ไม่พบข้อมูลกรุณาลองใหม่");
                    textUsername1.setText("");
                    textPassword1.setText("");
                    return;

                } else if (jsonMainNode.length() > 0) {

                    for (int i = 0; i < jsonMainNode.length(); i++) {
                        JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                        String u_name = jsonChildNode.optString("username");

                        sessionManager.setNAME(u_name);

                        Intent intent = new Intent(getApplicationContext(),Mainmenu.class);

                       intent.putExtra("u_name", u_name);

                        startActivity(intent);
                        finish();
                    }
                }
            }
            catch (JSONException e) {

                Log.e("TAG", "onClick: ", e);
                ShowWarning("ERROR Warning" + e);
                return;

            }
        }
        public void ShowWarning (String xMsg)
        {
            AlertDialog.Builder sbuilder = new AlertDialog.Builder(this);
            sbuilder.setTitle("แจ้งเตือน");
            sbuilder.setMessage(xMsg)
                    .setCancelable(false)
                    .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
            AlertDialog salertDialog = sbuilder.create();
            salertDialog.show();
        }








           public  void backlogin1(View view){

        Toast.makeText(getApplicationContext(),"Back to Page Login", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(),backlogin.class);
        startActivity(intent);

    }
    public  void openregister(View view){

        Toast.makeText(getApplicationContext(),"Going to Register!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(),pageregister.class);
        startActivity(intent);

    }


}






