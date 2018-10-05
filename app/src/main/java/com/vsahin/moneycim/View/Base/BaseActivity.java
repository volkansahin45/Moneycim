package com.vsahin.moneycim.View.Base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import com.vsahin.moneycim.R;

public abstract class BaseActivity  extends AppCompatActivity {

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getSupportFragmentManager();
    }

    protected void showRootFragment(Fragment fragment){
        if(getFragmentBackStackCount() == 0) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    protected void showFragment(Fragment nextFragment){
        //be sure to not load same fragment
        if(isLastFragmentInBackstack(nextFragment)){
            return;
        }

        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container , nextFragment)
                .addToBackStack(nextFragment.getClass().getName())
                .commit();
    }

    private boolean isLastFragmentInBackstack(Fragment fragment) {
        String nextFragmentName = fragment.getClass().getName();

        return getFragmentBackStackCount() != 0 && getLastFragmentNameInBackStack().equals(nextFragmentName);
    }

    private String getLastFragmentNameInBackStack(){
        return fragmentManager.getBackStackEntryAt(getFragmentBackStackCount() - 1).getName();
    }

    protected int getFragmentBackStackCount(){
        return fragmentManager.getBackStackEntryCount();
    }
}
