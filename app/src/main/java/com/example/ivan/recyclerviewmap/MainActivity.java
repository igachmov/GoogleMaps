package com.example.ivan.recyclerviewmap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.ivan.recyclerviewmap.Gasolino.LoginFragment;
import com.example.ivan.recyclerviewmap.Gasolino.RegistrationFragment;
import com.example.ivan.recyclerviewmap.test.IActivity;
import com.example.ivan.recyclerviewmap.test.IBaseView;

public class MainActivity extends AppCompatActivity implements IActivity {

    private Button registration;
    private Button login;
    private Button facebook;
    private Button google;
    private IBaseView currentFragment;
    private FrameLayout container;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        registration.setOnClickListener(v-> {
                showLoading();
                openFragment(RegistrationFragment.class,new Bundle(),true);

        });

        login.setOnClickListener(v-> {
                    showLoading();
                    openFragment(LoginFragment.class,new Bundle(),true);

        });



    }


    private void init(){
        registration = (Button) findViewById(R.id.registration_btn);
        login = (Button) findViewById(R.id.log_ing_btn);
        facebook = (Button) findViewById(R.id.facebook_login_btn);
        google = (Button) findViewById(R.id.google_login_btn);
        container = (FrameLayout) findViewById(R.id.container);
        linearLayout = (LinearLayout) findViewById(R.id.main_ll);
    }


    @Override
    public <View extends IBaseView> void openFragment(Class<View> clazz, Bundle arguments) {

        openFragment(clazz, arguments, true);
    }

    @Override
    public <View extends IBaseView> void openFragment(Class<View> clazz, Bundle arguments, boolean addToBackStack) {

        String name = clazz.getCanonicalName();
        if (name == null) {
            return;
        }
        if (currentFragment != null && currentFragment.getClass().getCanonicalName().equals(name)) {
            return;
        }
        if (getSupportFragmentManager().findFragmentByTag(name) != null) {
            currentFragment = popBackStack(getSupportFragmentManager(), name, arguments);
        } else {
            Fragment fragmentToOpen = Fragment.instantiate(this, name, arguments);
            if (addToBackStack) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragmentToOpen, name)
                        .addToBackStack(name).commitAllowingStateLoss();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragmentToOpen, name)
                        .disallowAddToBackStack().commitAllowingStateLoss();
            }
            getSupportFragmentManager().executePendingTransactions();
            currentFragment = (IBaseView) fragmentToOpen;
        }
    }

    @Override
    public void onBackPressed() {

        if (currentFragment.onBack()) {
            return;
        }

        FragmentManager fm = getSupportFragmentManager();

        if (fm.getBackStackEntryCount() > 1) {
            int topEntryIndex = fm.getBackStackEntryCount() - 1;
            FragmentManager.BackStackEntry backStackEntry = fm.getBackStackEntryAt(topEntryIndex - 1);
            for (int i = topEntryIndex; i >= 0; i--) {
                if (fm.getBackStackEntryAt(i) != null && backStackEntry.getName() != null) {
                    currentFragment = popBackStack(fm, backStackEntry.getName(), null);
                    break;
                }
            }
        } else {
            supportFinishAfterTransition();
        }
    }

    protected IBaseView popBackStack(FragmentManager fragmentManager, String fragmentViewName, Bundle args) {

        Fragment fragment = fragmentManager.findFragmentByTag(fragmentViewName);
        if (fragment != null && fragment.getArguments() != null && args != null) {
            fragment.getArguments().putAll(args);
        }
        fragmentManager.popBackStack(fragmentViewName, 0);
        fragmentManager.executePendingTransactions();

        return (IBaseView) fragment;
    }


    @Override
    public void showLoading() {
        container.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        container.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);

    }
}