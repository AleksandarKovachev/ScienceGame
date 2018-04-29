package com.tu.sofia.sciencegame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;

import com.tu.sofia.sciencegame.R;
import com.tu.sofia.sciencegame.entity.User;
import com.tu.sofia.sciencegame.manager.DialogManager;
import com.tu.sofia.sciencegame.manager.SharedPreferencesManager;

import io.realm.Realm;

/**
 * Created by Aleksandar Kovachev on 29.04.2018 г..
 */
public class LoginActivity extends AppCompatActivity {

    private SharedPreferencesManager sharedPreferences;

    private Button loginButton;
    private Button registerButton;
    private AppCompatEditText username;
    private AppCompatEditText password;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_login_context_button);

        username = findViewById(R.id.input_username);
        password = findViewById(R.id.input_password);

        sharedPreferences = new SharedPreferencesManager(getApplicationContext());
        realm = Realm.getDefaultInstance();

        boolean isUserLoggedIn = sharedPreferences.getLoginState();

        if (isUserLoggedIn) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        registerButton.setOnClickListener(registerClickListener);
        loginButton.setOnClickListener(loginClickListener);
    }

    private View.OnClickListener loginClickListener = v -> {
        if (username.getText().toString().trim().isEmpty()) {
            DialogManager.showAlertDialog(LoginActivity.this, "Неуспешно", "Потребителското име не може да бъде празно", "Добре");
            return;
        }

        if (password.getText().toString().trim().isEmpty()) {
            DialogManager.showAlertDialog(LoginActivity.this, "Неуспешно", "Паролата не може да бъде празна", "Добре");
            return;
        }

        realm.beginTransaction();
        User user = realm.where(User.class).equalTo("username", username.getText().toString()).equalTo("password", password.getText().toString()).findFirst();

        if (user == null) {
            DialogManager.showAlertDialog(LoginActivity.this, "Неуспешно", "Липсва потребител с въведените потребителско име и парола", "Добре");
            realm.commitTransaction();
            return;
        }
        realm.commitTransaction();

        sharedPreferences.setLoginState(true, username.getText().toString(), password.getText().toString(), user.getUserType());

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    };

    private View.OnClickListener registerClickListener = v -> {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

}
