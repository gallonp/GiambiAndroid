package com.example.giambi.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.example.giambi.InvalidUsernameOrPasswordDialogFragment;
import com.example.giambi.R;
import com.example.giambi.model.LoginAccount;
import com.example.giambi.presenter.RegisterPresenter;
import com.example.giambi.util.Util;
import com.example.giambi.view.RegisterView;

/**
 * @author zhangjialiang
 * Render register page
 */
public class RegisterActivity extends Activity implements RegisterView {
    TextView username;
    TextView password1;
    TextView password2;
    Button registerBtn;
    TextView resultText;
    RegisterPresenter registerPresenter;
    LoginAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        registerBtn = (Button) findViewById(R.id.registerButton);
        username = (TextView) this.findViewById(R.id.logResult);
        password1 = (TextView) this.findViewById(R.id.registerPassword1);
        password2 = (TextView) this.findViewById(R.id.registerPassword2);
        resultText = (TextView) findViewById(R.id.registerResult);
        registerPresenter = new RegisterPresenter(this);
    }

    //
    @Override
    public String getUsername() {
        return this.username.getText().toString();
    }

    @Override
    public String getPassword1() {
        return this.password1.getText().toString();
    }

    @Override
    public String getPassword2() {
        return this.password2.getText().toString();
    }

    @Override
    public void addClickListener(OnClickListener clickerListener) {
        this.registerBtn.setOnClickListener(clickerListener);
    }

    @Override
    public void setResonpseText(String response) {
        this.resultText.setText(response);
    }

    @Override
    public void setDialogMessage(int usernameErrorCode, int passwordErrorCode) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        DialogFragment dialog = new InvalidUsernameOrPasswordDialogFragment();
        dialog.setArguments(bundle);
        dialog.show(ft, "dialog");
        switch (usernameErrorCode) {
            case Util.USERNAME_EMPTY:
                bundle.putString("message",
                        getString(R.string.dialog_message_username_empty));
                break;
            case Util.USERNAME_LENGTH:
                bundle.putString("message",
                        getString(R.string.dialog_message_username_length));
                break;
            case Util.USERNAME_NOT_EMAIL:
                bundle.putString("message",
                        getString(R.string.dialog_message_username_not_email));
                break;
            default:
                switch (passwordErrorCode) {
                    case Util.PASSWORD_EMPTY:
                        bundle.putString("message",
                                getString(R.string.dialog_message_password_empty));
                        break;
                    case Util.PASSWORD_LENGTH:
                        bundle.putString("message",
                                getString(R.string.dialog_message_password_length));
                        break;
                    case Util.PASSWORD_EASY:
                        bundle.putString("message",
                                getString(R.string.dialog_message_password_easy));
                        break;
                    case Util.PASSWORD_NOT_MATCH:
                        bundle.putString("message",
                                getString(R.string.dialog_message_password_not_match));
                        break;
                }
        }

    }
}
