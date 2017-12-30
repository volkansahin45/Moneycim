package com.vsahin.moneycim.View;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vsahin.moneycim.R;
import com.vsahin.moneycim.View.AddAndEditSpending.AddAndEditSpendingFragment;
import com.vsahin.moneycim.View.SpendingList.SpendingListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.Binds;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    FragmentManager fragmentManager;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        fragmentManager = getSupportFragmentManager();
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        //Starting hide for protect state when rotate screen
        fab.hide();

        //Add this fragment just at start and dont add to backstack
        if(getFragmentBackStackCount() == 0){
            showRootFragment(SpendingListFragment.newInstance());
            fab.show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);

        switch (item.getItemId()){
            case R.id.nav_share:
                shareApp();
                break;
            case R.id.nav_about:
                showAboutDialog();
                break;
        }
        return true;
    }

    public void shareApp(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
        String sAux = "Hey look at this application\n" + getString(R.string.app_link) +"\n";
        i.putExtra(Intent.EXTRA_TEXT, sAux);
        startActivity(Intent.createChooser(i, "Share"));
    }

    public void showAboutDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_main_dialog_about);

        ImageView playStoreImage = (ImageView) dialog.findViewById(R.id.play_store_icon);
        playStoreImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "market://search?q=pub:" + getString(R.string.app_play_store_id);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        dialog.show();

    }

    public void showRootFragment(Fragment fragment){
        appBarLayout.setExpanded(true);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {

        int backStackCount = getFragmentBackStackCount();

        //if backstack == 1 it means this is last fragment so show fab
        switch (backStackCount){
            case 1:
                fab.show();
                break;
        }

        super.onBackPressed();
    }

    @OnClick(R.id.fab)
    public void openAddSpendingFragment(){
        showFragment(AddAndEditSpendingFragment.newInstance());
    }

    public void showFragment(Fragment nextFragment){
        //be sure to not load same fragment
        if(isLastFragmentInBackstack(nextFragment)){
            return;
        }

        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container , nextFragment)
                .addToBackStack(nextFragment.getClass().getName())
                .commit();

        fab.hide();
        appBarLayout.setExpanded(false);
    }

    public boolean isLastFragmentInBackstack(Fragment fragment){
        String currentFragmentName;
        String nextFragmentName = fragment.getClass().getName();

        //if count is 0 it means there isnt any fragment in backstack
        int count = getFragmentBackStackCount();
        if(count != 0){
            currentFragmentName = getLastFragmentNameInBackStack();
            if(currentFragmentName.equals(nextFragmentName)){
                return true;
            }
        }
        return false;
    }

    public String getLastFragmentNameInBackStack(){
        return fragmentManager.getBackStackEntryAt(getFragmentBackStackCount() - 1).getName();
    }

    public int getFragmentBackStackCount(){
        return fragmentManager.getBackStackEntryCount();
    }

    public void hideKeyboard(View v){
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

}
