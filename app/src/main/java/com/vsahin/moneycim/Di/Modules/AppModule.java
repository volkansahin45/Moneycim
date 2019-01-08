package com.vsahin.moneycim.Di.Modules;

import android.app.Application;

import com.vsahin.moneycim.MoneycimApp;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Volkan Şahin on 18.08.2017.
 */

@Module
public abstract class AppModule {

    @Binds
    @Singleton
    abstract Application application(MoneycimApp app);
}
