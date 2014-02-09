package com.example.giambi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RegisterActivity extends Activity {
    TextView username;
    TextView password1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Button registerBtn = (Button) findViewById(R.id.registerButton);
        username = (TextView) this.findViewById(R.id.logResult);
        password1 = (TextView) this.findViewById(R.id.registerPassword1);
        registerBtn.setOnClickListener(registerClickListener);
    }

    OnClickListener registerClickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            String name = username.getText().toString();
            String pwd = password1.getText().toString();
            boolean valid = Util.checkLogin(name, pwd);
            if (valid) {
                String rep = register(username.getText().toString(), password1
                        .getText().toString());
                TextView resultText = (TextView) findViewById(R.id.registerResult);
                resultText.setText(rep);
            } else {
                Log.i("Register Error", "Invalid username or password");
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                
                switch (Util.checkUserName(name)){
                case Util.USERNAME_EMPTY:
                    bundle.putString("message", getString(R.string.dialog_message_username_empty));
                    break;
                case Util.USERNAME_LENGTH:
                    bundle.putString("message", getString(R.string.dialog_message_username_length));
                    break;
                case Util.USERNAME_NOT_EMAIL:
                    bundle.putString("message", getString(R.string.dialog_message_username_not_email));
                    break;
                default:
                    switch (Util.checkPassword(pwd)){
                    case Util.PASSWORD_EMPTY:
                        bundle.putString("message", getString(R.string.dialog_message_password_empty)); 
                        break;
                    case Util.PASSWORD_LENGTH:
                        bundle.putString("message", getString(R.string.dialog_message_password_length));
                        break;
                    case Util.PASSWORD_EASY:
                        bundle.putString("message", getString(R.string.dialog_message_password_easy));
                        break;
                    }
                }
                DialogFragment dialog = new InvalidUsernameOrPasswordDialogFragment();
                dialog.setArguments(bundle);
                dialog.show(ft, "dialog");
            }
        }

    };

    @SuppressWarnings("unchecked")
    private String register(String username, String password) {
        // URL url = new URL("")
        // should be HttpGet
        HttpPost request = new HttpPost(
                "http://giambi-server-2340.appspot.com/register");
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("username", username);
        jsonObj.put("password", password);
        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair("json", jsonObj.toString()));
        UrlEncodedFormEntity entity;
        Log.v("authenticate", "JSON ready");
        try {
            entity = new UrlEncodedFormEntity(postParams, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            Log.e("EncodedForm", e.toString());
            entity = null;
        }
        request.setEntity(entity);
        String content = "";
        try {
            content = Util.HttpContentReader(GiambiHttpClient
                    .getResponse(request).getEntity().getContent());
        } catch (IllegalStateException e) {
            content = e.getMessage();
        } catch (IOException e) {
            content = e.getMessage();
        }
        return content;
    }
}
