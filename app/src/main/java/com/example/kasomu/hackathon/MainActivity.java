package com.example.kasomu.hackathon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends Activity implements View.OnClickListener {

    public EditText editText,editText2,editText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        editText = (EditText)findViewById(R.id.editText);
        editText2 = (EditText)findViewById(R.id.editText2);
        editText3 = (EditText)findViewById(R.id.editText3);
        editText.setOnClickListener(this);
        editText2.setOnClickListener(this);
        editText3.setOnClickListener(this);

      /*  final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getId()==R.id.button)
                {
                    Intent firstpage=new Intent(this,secondactivity.class);
                    startActivity(secondactivity.class);
                }
            }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View v) {
        if(v==editText) {
            //Log.d("myTag", "Karthik : "+editText.getText().toString());
            if("Name".equals(editText.getText().toString())) {
                editText.setText("");
            }
        }
        if(v==editText2) {
            if("Phone Number".equals(editText2.getText().toString()))
            editText2.setText("");
        }
        if(v==editText3) {
            if(editText3.getText().toString().equals("E-Mail"))
            editText3.setText("");
        }
    }

    public void validate(View view) throws IOException {

            boolean phone_valid = isValidPhoneNumber(editText2.getText().toString());
            if(!phone_valid)
            {
                Toast bread = Toast.makeText(getApplicationContext(), "Invalid phone number", Toast.LENGTH_LONG);
                bread.show();
                return;
            }
            boolean email_valid = isValidEmail(editText3.getText().toString());
            if(!email_valid)
            {
                Toast bread = Toast.makeText(getApplicationContext(), "Invalid email id", Toast.LENGTH_LONG);
                bread.show();
            }
            if(phone_valid && email_valid) {
                //new TheTask().doInBackground();
                /*DefaultHttpClient httpclient = new DefaultHttpClient();
                HttpGet httpget = new HttpGet("http://www.google.com");
                HttpResponse response = httpclient.execute(httpget);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer sb = new StringBuffer("");
                String line = "";
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line + NL);
                }
                in.close();
                String result = sb.toString();
                Log.d("My Response :: ", result);*/
                try {
                    InputStream content = null;
                    String url = "https://rails-tutorial-kasirajanss93.c9.io/users/1";
                    HttpGet httpGet = new HttpGet(url);
                    HttpClient httpclient = new DefaultHttpClient();
                    // Execute HTTP Get Request
                    HttpResponse response = httpclient.execute(httpGet);
                    Log.d("Response : ", response.toString());
                    InputStream is = response.getEntity().getContent();
                    try {
                        String response1;
                        BufferedReader reader = new BufferedReader(new InputStreamReader( is, "UTF-8"), 8);
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null)
                        {
                            sb.append(line + "\n");
                        }
                        is.close();
                        response1 = sb.toString();
                        Log.d("Latest response : ",response1);
                    }
                    catch (Exception e)
                    {
                        Log.e("Buffer Error", "Error: " + e.toString());
                    }
                    //return response;
                    content = response.getEntity().getContent();
                    Log.d("Content : ",content.toString());
                } catch (Exception e) {
                    //handle the exception !
                }
                try {

                    JSONObject Parent = new JSONObject();
                    JSONArray array = new JSONArray();
                    JSONObject obj = new JSONObject();
                    JSONObject obj1 = new JSONObject();
                    obj.put("name",editText.getText().toString());
                    obj.put("user_name",editText.getText().toString());
                    obj.put("phone_no",editText2.getText().toString());
                    obj.put("email",editText3.getText().toString());
                    obj.put("password",editText.getText().toString());
                    obj.put("password_confirmation",editText.getText().toString());
                    array.put(obj);
                    Parent.put("datastreams", array);
                    Parent.put("version", "1.0");
                    Parent.put("user",obj);
                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost("https://rails-tutorial-kasirajanss93.c9.io/users/");
                    StringEntity se = new StringEntity( Parent.toString());
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setHeader("Accept", "application/json");
                    post.setHeader("Content-type", "application/json");
                    post.setEntity(se);
                    HttpResponse response = client.execute(post);
                    Log.d("Response : ", response.toString());
                    InputStream is = response.getEntity().getContent();
                    try {
                        String response1;
                        BufferedReader reader = new BufferedReader(new InputStreamReader( is, "UTF-8"), 8);
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null)
                        {
                            sb.append(line + "\n");
                        }
                        is.close();
                        response1 = sb.toString();
                        Log.d("Latest response : ",response1);
                    }
                    catch (Exception e)
                    {
                        Log.e("Buffer Error", "Error: " + e.toString());
                    }
                   /*
                    JSONObject obj = new JSONObject();
                    try{
                        obj.put("name",editText.getText().toString());
                        obj.put("phone",editText2.getText().toString());
                        obj.put("mailid",editText3.getText().toString());
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }*/
                    Log.d("My App", obj.toString());

                } catch (Throwable t) {
                    Log.e("My App", "Could not parse malformed JSON: \"" + "asf" + "\"");
                }
                Intent intent = new Intent(this, secondactivity.class);
                startActivity(intent);
            }
    }

    private boolean isValidEmail(CharSequence email) {
        if (!TextUtils.isEmpty(email)) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        return false;
    }

    private boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }

    }
