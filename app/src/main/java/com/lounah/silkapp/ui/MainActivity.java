package com.lounah.silkapp.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lounah.silkapp.R;
import com.lounah.silkapp.ui.dialogs.DialogsFragment;
import com.lounah.silkapp.ui.information.InformationFragment;
import com.lounah.silkapp.ui.products.ProductsFragment;
import com.lounah.silkapp.ui.settings.SettingsFragment;
import com.ncapdevi.fragnav.FragNavController;
import com.ncapdevi.fragnav.FragNavTransactionOptions;
import com.ncapdevi.fragnav.tabhistory.FragNavTabHistoryController;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.Arrays;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BaseFragment.Navigation,
        FragNavController.RootFragmentListener {

    private static final int INFORMATION_FRAGMENT_ID = 0;
    private static final int DIALOGS_FRAGMENT_ID = 1;
    private static final int PRODUCTS_FRAGMENT_ID = 2;
    private static final int SETTINGS_FRAGMENT_ID = 3;

    private static final int FRAGMENT_CONTAINER_ID = R.id.fragment_container;

    private FragNavController fragNavController;

//    @BindView(R.id.toolbar)
//    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initUI(savedInstanceState, getSupportFragmentManager());
    }



    private void initUI(@Nullable final Bundle savedInstanceState, @NonNull final FragmentManager supportFragmentManager) {

//        setSupportActionBar(toolbar);
//
//        toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        initFragmentNavigationController(savedInstanceState, supportFragmentManager);

    }

    private void initFragmentNavigationController(@Nullable final Bundle savedInstanceState, @NonNull final FragmentManager supportFragmentManager) {
        fragNavController = FragNavController.newBuilder(savedInstanceState, supportFragmentManager, FRAGMENT_CONTAINER_ID)
                .transactionListener(new FragNavController.TransactionListener() {
                    @Override
                    public void onTabTransaction(@Nullable Fragment fragment, int i) {
                        if (getSupportActionBar() != null && fragNavController != null) {
                            getSupportActionBar().setDisplayHomeAsUpEnabled(!fragNavController.isRootFragment());
                        }
                    }

                    @Override
                    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
                        if (getSupportActionBar() != null && fragNavController != null) {
                            getSupportActionBar().setDisplayHomeAsUpEnabled(!fragNavController.isRootFragment());
                        }
                    }
                })
                .rootFragmentListener(this, 4)
                .selectedTabIndex(INFORMATION_FRAGMENT_ID)
                .build();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (!fragNavController.isRootFragment()) {
                fragNavController.popFragment();
            } else super.onBackPressed();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        fragNavController.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                break;

            case android.R.id.home:
                fragNavController.popFragment();
                break;
        }

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        final int id = item.getItemId();

        switch (id) {
            case R.id.nav_information:
                fragNavController.switchTab(INFORMATION_FRAGMENT_ID);
                break;

            case R.id.nav_dialogs:
                fragNavController.switchTab(DIALOGS_FRAGMENT_ID);
                break;

            case R.id.nav_products:
                fragNavController.switchTab(PRODUCTS_FRAGMENT_ID);
                break;

            case R.id.nav_settings:
                fragNavController.switchTab(SETTINGS_FRAGMENT_ID);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void pushFragment(@NonNull Fragment fragment, @NonNull final boolean animationsAllowed) {
        if (fragNavController != null) {
            if (animationsAllowed) {
                fragNavController.pushFragment(fragment, FragNavTransactionOptions.newBuilder()
                        .customAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .build());
            } else {
                fragNavController.pushFragment(fragment, FragNavTransactionOptions.newBuilder()
                        .build());
            }
        }
    }

    private void setupDefaultFragment() {
        pushFragment(new InformationFragment(), false);
    }

    public void onUpdateToolbar(@NonNull final Toolbar toolbar) {
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public Fragment getRootFragment(int i) {
        Fragment root = null;
        switch (i) {
            case INFORMATION_FRAGMENT_ID:
                root =  new InformationFragment();
                break;
            case DIALOGS_FRAGMENT_ID:
                root =  new DialogsFragment();
                break;
            case PRODUCTS_FRAGMENT_ID:
                root =  new ProductsFragment();
                break;
            case SETTINGS_FRAGMENT_ID:
                root =  new SettingsFragment();
                break;
        }
        return root;
    }

}


