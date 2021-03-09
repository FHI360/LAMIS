package org.fhi360.lamis.mobile.lite.Activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.shashank.sony.fancytoastlib.FancyToast;
import org.fhi360.lamis.mobile.lite.R;
import org.fhi360.lamis.mobile.lite.Utils.PrefManager;

import java.util.HashMap;

public class CreateAccount extends AppCompatActivity {
    private EditText usernameEdt, passwordEdt;
    private TextInputLayout inputLayoutUsername, inputLayoutPassword;
    private Button button_create;
    private PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorPrimaryDark)));

        usernameEdt = findViewById(R.id.edit_text_username);
        passwordEdt = findViewById(R.id.edit_text_password);
        inputLayoutUsername = findViewById(R.id.text_input_layout_username);
        inputLayoutPassword = findViewById(R.id.text_input_layout_password);
        button_create = findViewById(R.id.button_account);
        prefManager = new PrefManager(getApplicationContext());
        button_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEdt.getText().toString();
                String password = passwordEdt.getText().toString();
                HashMap<String, String> user = prefManager.checkIfUserExist();
                String userName = user.get("userName");
                String passwords = user.get("password");
                if (validateInput(username, password)) {
                    if (userName != null && passwords == null) {
                        FancyToast.makeText(getApplicationContext(), "Account already exist", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                    } else {
                        prefManager.createLoginSession(username, password);
                        FancyToast.makeText(getApplicationContext(), "Account created successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        Intent intent = new Intent(CreateAccount.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }
            }
        });


    }

    private boolean validateInput(String username, String password) {
        if (username.isEmpty()) {
            inputLayoutUsername.setError("username can not be empty");
            inputLayoutUsername.setErrorEnabled(true);
            return false;
        } else if (password.isEmpty()) {
            inputLayoutPassword.setError("Password can not be empty");
            inputLayoutPassword.setErrorEnabled(true);
            return false;
        } else {
            return true;
        }

    }

}
