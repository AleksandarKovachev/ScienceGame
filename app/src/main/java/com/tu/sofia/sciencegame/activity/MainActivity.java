package com.tu.sofia.sciencegame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.roughike.bottombar.BottomBar;
import com.tu.sofia.sciencegame.R;
import com.tu.sofia.sciencegame.manager.SharedPreferencesManager;

import io.realm.Realm;

/**
 * Created by Aleksandar Kovachev on 29.04.2018 г..
 */
public class MainActivity extends AppCompatActivity {

    private SharedPreferencesManager sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(getApplicationContext());

        checkLogin();

        BottomBar bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(tabId -> {
            switch (tabId) {
                case R.id.home:
                    break;
                case R.id.myQuestions:
                    break;
                case R.id.suggestQuestion:
                    break;
            }
        });
    }

    @Override
    protected void onRestart() {
        checkLogin();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        checkLogin();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_logout:
                sharedPreferences.clearLoginState();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return false;
        }
        return false;
    }

    private void checkLogin() {
        sharedPreferences = new SharedPreferencesManager(getApplicationContext());
        boolean isUserLoggedIn = sharedPreferences.getLoginState();

        if (!isUserLoggedIn) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

}
