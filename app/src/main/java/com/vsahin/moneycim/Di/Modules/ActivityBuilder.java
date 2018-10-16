package com.vsahin.moneycim.Di.Modules;

import com.vsahin.moneycim.Di.Scope.ActivityScope;
import com.vsahin.moneycim.View.MainActivity;
import com.vsahin.moneycim.View.OCR_Scan_Receipt.OcrCaptureActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector
    abstract MainActivity mainActivity();

    @ActivityScope
    @ContributesAndroidInjector
    abstract OcrCaptureActivity ocrCaptureActivity();
}
