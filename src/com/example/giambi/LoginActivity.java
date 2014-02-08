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

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

public class LoginActivity extends Activity {

    private MultiAutoCompleteTextView username;
    private TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Show the Up button in the action bar.
        setupActionBar();

        Button login = (Button) this.findViewById(R.id.login_button);
        username = (MultiAutoCompleteTextView) this
                .findViewById(R.id.login_username);
        password = (TextView) this.findViewById(R.id.login_password);
        login.setOnClickListener(loginClickListener);
    }

    OnClickListener loginClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            String name = username.getText().toString();
            String pwd = password.getText().toString();
            String authResult = "";
            // Checks the validity of the username&password
            if (Util.checkLogin(name, pwd)) {
                authResult = authenticate(name, pwd);
            } else {
                Log.i("Login Error", "Invalid username or password");
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                
                //Check which kind of error the username has
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
            TextView tv = (TextView) findViewById(R.id.logResult);
            tv.setText(authResult);
        }
    };

    @SuppressWarnings("unchecked")
    private String authenticate(String username, String password) {
        // URL url = new URL("")
        // should be HttpGet
        HttpPost request = new HttpPost(
                "http://giambi-server-2340.appspot.com/registerLogin");
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("username", username);
        jsonObj.put("password", password);
        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair("json", jsonObj.toString()));
        UrlEncodedFormEntity entity = null;
        Log.v("authenticate", "JSON ready");
        try {
            entity = new UrlEncodedFormEntity(postParams, "UTF-8");
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
        } catch (UnsupportedEncodingException e) {
            Log.e("EncodedFormError", e.toString());
            return "";
        }
    }

    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar() {
        ActionBar actionBar = this.getActionBar();
        actionBar.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

}
