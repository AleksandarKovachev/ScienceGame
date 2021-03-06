package com.tu.sofia.sciencegame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.roughike.bottombar.BottomBar;
import com.tu.sofia.sciencegame.R;
import com.tu.sofia.sciencegame.constant.RealmUtils;
import com.tu.sofia.sciencegame.constant.SharedPreferencesConstants;
import com.tu.sofia.sciencegame.constant.UserTypes;
import com.tu.sofia.sciencegame.entity.User;
import com.tu.sofia.sciencegame.fragment.AddQuestionFragment;
import com.tu.sofia.sciencegame.fragment.ApproveQuestionsFragment;
import com.tu.sofia.sciencegame.fragment.HomeScreenFragment;
import com.tu.sofia.sciencegame.fragment.MyQuestionsFragment;
import com.tu.sofia.sciencegame.manager.SharedPreferencesManager;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Aleksandar Kovachev on 29.04.2018 г..
 */
public class MainActivity extends AppCompatActivity {

    private SharedPreferencesManager sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().initialData(realm -> {
            User user = realm.createObject(User.class, RealmUtils.getNextId(User.class, realm));
            user.setUserType(UserTypes.ADMIN.getUserType());
            user.setUsername("admin");
            user.setPassword("admin");
        }).deleteRealmIfMigrationNeeded().name("realm.db").build();
        Realm.setDefaultConfiguration(config);

        if(!checkLogin()){
            return;
        }

        BottomBar bottomBar = findViewById(R.id.bottomBar);

        User user;

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        user = realm.copyFromRealm(realm.where(User.class).equalTo(SharedPreferencesConstants.USERNAME, sharedPreferences.getString(SharedPreferencesConstants.USERNAME, null)).findFirst());
        realm.commitTransaction();

        bottomBar.setOnTabSelectListener(tabId -> {
            Fragment fragment = null;
            String title = null;

            switch (tabId) {
                case R.id.home:
                    fragment = new HomeScreenFragment();
                    title = "Начало";
                    break;
                case R.id.myQuestions:
                    if(user.getUserType() == UserTypes.USER.getUserType()) {
                        fragment = new MyQuestionsFragment();
                        title = "Моите въпроси";
                    } else {
                        fragment = new ApproveQuestionsFragment();
                        title = "Одобряване на въпроси";
                    }
                    break;
                case R.id.suggestQuestion:
                    fragment = new AddQuestionFragment();
                    title = "Добави нов въпрос";
                    break;
            }

            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                getFragmentManager().popBackStackImmediate();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentManager.popBackStack();
                fragmentTransaction.replace(R.id.contentContainer, fragment);
                getSupportActionBar().setTitle(title);
                fragmentTransaction.commit();
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

    private boolean checkLogin() {
        sharedPreferences = new SharedPreferencesManager(getApplicationContext());
        boolean isUserLoggedIn = sharedPreferences.getLoginState();

        if (!isUserLoggedIn) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return false;
        }
        return true;
    }

}
