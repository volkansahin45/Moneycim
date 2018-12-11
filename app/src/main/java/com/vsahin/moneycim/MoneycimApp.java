package com.vsahin.moneycim;

import com.vsahin.moneycim.Di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by Volkan Şahin on 17.08.2017.
 */

public class MoneycimApp extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
