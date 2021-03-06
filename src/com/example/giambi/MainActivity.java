package com.example.giambi;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.example.giambi.activity.LoginActivity;
import com.example.giambi.activity.RegisterActivity;

/**
 * @author WenglingChen
 *
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom_page);

        Button login = (Button) this.findViewById(R.id.welcome_login);
        Button register = (Button) this.findViewById(R.id.welcome_Register);
        login.setOnClickListener(buttonClickHandler);
        register.setOnClickListener(buttonClickHandler);

        ActionBar actionBar = this.getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
                ActionBar.DISPLAY_HOME_AS_UP);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcom_page, menu);
        return true;
    }

    OnClickListener buttonClickHandler = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.welcome_login:
                    jumpToLogin();
                    break;
                case R.id.welcome_Register:
                    jumpToRegister();
                    break;
            }
        }

    };

    /**
     * Starts new activity for login.
     */
    protected void jumpToLogin() {
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.zoom_out);
    }

    /**
     * Starts new Activity for register.
     */
    protected void jumpToRegister() {

        Intent intent = new Intent();
        intent.setClass(this, RegisterActivity.class);
        startActivity(intent);

        overridePendingTransition(R.anim.in_from_right, R.anim.zoom_out);
    }
}
