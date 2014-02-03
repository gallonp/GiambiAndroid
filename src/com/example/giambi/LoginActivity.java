package com.example.giambi;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Show the Up button in the action bar.
        setupActionBar();
        
        Button login = (Button) this.findViewById(R.id.login_button);
        MultiAutoCompleteTextView username =
                (MultiAutoCompleteTextView) this.findViewById(R.id.login_username);
        TextView password = (TextView) this.findViewById(R.id.login_password);
        login.setOnClickListener(new OnClickListener(){

    		@Override
    		public void onClick(View arg0) {
    			// Verify user input, and if valid, perform login action
    			
    		}
        	
        });
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
