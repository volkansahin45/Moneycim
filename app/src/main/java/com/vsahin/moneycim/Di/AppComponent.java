package com.vsahin.moneycim.Di;

import com.vsahin.moneycim.Di.Modules.ActivityBuilder;
import com.vsahin.moneycim.Di.Modules.DataModule;
import com.vsahin.moneycim.Di.Modules.AppModule;
import com.vsahin.moneycim.Di.Modules.FragmentBuilder;
import com.vsahin.moneycim.Di.Modules.ViewModelModule;
import com.vsahin.moneycim.MoneycimApp;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Volkan Şahin on 18.08.2017.
 */

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class,
        FragmentBuilder.class,
        DataModule.class,
        ViewModelModule.class
})
interface AppComponent extends AndroidInjector<MoneycimApp> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MoneycimApp>{}
}
