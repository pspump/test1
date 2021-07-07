package com.example.testre;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText t_fullname,t_email,t_password;

    String  v_fullname,v_email,v_password;
    Button btn_register;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t_fullname = findViewById(R.id.txtname);
        t_email= findViewById(R.id.txtuser);
        t_password = findViewById(R.id.txtpassword);
    }


    public void SaveRegister(View view) {
        v_fullname = t_fullname.getText().toString().trim();
        v_email = t_email.getText().toString().trim();
        v_password = t_password.getText().toString().trim();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("บันทึกข้อมูล");
        builder.setMessage("ยืนยันข้อมูลครบถ้วนสมบูรณ์?");
        builder.setCancelable(false);
        builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener()

        {
            public void onClick(DialogInterface dialog, int id)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);


                StrictMode.setThreadPolicy(policy);

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
               // sessionManager = new SessionManager(getApplicationContext());
              //  String URL="";
              //  URL = "http://"+sessionManager.getIpconfig().toString()+"register.php";


                try {

                    nameValuePairs.add(new BasicNameValuePair("sname",v_fullname));
                    nameValuePairs.add(new BasicNameValuePair("semail",v_email));
                    nameValuePairs.add(new BasicNameValuePair("spassword", v_password));


                    // Connect Server


                    HttpClient httpclient = new DefaultHttpClient();

                    //  HttpPost httppost = new HttpPost(URL);
                    HttpPost httppost = new HttpPost("https://192.168.100.169/apptest/resgister.php");


                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                    HttpResponse response = httpclient.execute(httppost);
                    int statusCode =  response.getStatusLine().getStatusCode();



                    Toast.makeText(getApplicationContext(),statusCode, Toast.LENGTH_LONG).show();

                    //--------Check status connect

                    if (statusCode == 200) {
                        ShowWarning("บันทึกข้อมูลเรียบร้อย");
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else if (statusCode == 404) {
                        ShowWarning("ขออภัยเกิดวามผิดพลาดในการบันทึกข้อมูล กรุณาตรวจสอบ");

                        return;
                    }
                }

                catch (Exception e) {
                    ShowWarning("ขออภัยเกิดวามผิดพลาดกรุณาตรวจสอบ");
                    return;

                }
            }
        })
                .setNegativeButton("เปลี่ยนใจ",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//Clear Activity Stack
                        startActivity(intent);
                        finish();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
    public  void ShowWarning(String xMsg)
    {
        AlertDialog.Builder sbuilder = new AlertDialog.Builder(this);
        sbuilder.setTitle("ALERT");
        sbuilder.setMessage(xMsg);
        sbuilder.setCancelable(false);
        sbuilder.setPositiveButton("OKEY", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {


            }
        });
        AlertDialog selartDialog = sbuilder.create();
        selartDialog.show();


    }


}