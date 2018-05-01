package com.tu.sofia.sciencegame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;

import com.tu.sofia.sciencegame.R;
import com.tu.sofia.sciencegame.constant.RealmUtils;
import com.tu.sofia.sciencegame.constant.UserTypes;
import com.tu.sofia.sciencegame.entity.User;
import com.tu.sofia.sciencegame.manager.DialogManager;
import com.tu.sofia.sciencegame.manager.SharedPreferencesManager;

import io.realm.Realm;

/**
 * Created by Aleksandar Kovachev on 29.04.2018 г..
 */
public class RegisterActivity extends AppCompatActivity {

    private AppCompatEditText username;
    private AppCompatEditText password;
    private AppCompatEditText repeatPassword;

    private Realm realm;

    private SharedPreferencesManager sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button loginButton = findViewById(R.id.login_register_context_button);
        Button registerButton = findViewById(R.id.register_button);

        username = findViewById(R.id.input_username);
        password = findViewById(R.id.input_password);
        repeatPassword = findViewById(R.id.input_repeat_password);

        realm = Realm.getDefaultInstance();

        sharedPreferences = new SharedPreferencesManager(this);

        registerButton.setOnClickListener(registerClickListener);
        loginButton.setOnClickListener(loginClickListener);
    }

    private View.OnClickListener registerClickListener = v -> {
        if (username.getText().toString().trim().isEmpty()) {
            DialogManager.showAlertDialog(RegisterActivity.this, "Неуспешно", "Потребителското име не може да бъде празно", "Добре");
            return;
        }

        if (password.getText().toString().trim().isEmpty()) {
            DialogManager.showAlertDialog(RegisterActivity.this, "Неуспешно", "Паролата не може да бъде празна", "Добре");
            return;
        }

        if (repeatPassword.getText().toString().trim().isEmpty()) {
            DialogManager.showAlertDialog(RegisterActivity.this, "Неуспешно", "Паролата не може да бъде празна", "Добре");
            return;
        }

        if (!repeatPassword.getText().toString().equals(password.getText().toString())) {
            DialogManager.showAlertDialog(RegisterActivity.this, "Неуспешно", "Въведените пароли не съвпадат", "Добре");
            return;
        }

        realm.beginTransaction();
        User user = realm.where(User.class).equalTo("username", username.getText().toString()).findFirst();

        if (user != null) {
            DialogManager.showAlertDialog(RegisterActivity.this, "Неуспешно", "Вече съществува потребител с потребителското име " + username.getText().toString(), "Добре");
        } else {
            User newUser = realm.createObject(User.class, RealmUtils.getNextId(User.class, realm));
            newUser.setUsername(username.getText().toString());
            newUser.setPassword(password.getText().toString());
            newUser.setUserType(UserTypes.USER.getUserType());
        }
        realm.commitTransaction();

        sharedPreferences.setLoginState(true, username.getText().toString(), password.getText().toString(), UserTypes.USER.getUserType());

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    };

    private View.OnClickListener loginClickListener = v -> {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    };

}
