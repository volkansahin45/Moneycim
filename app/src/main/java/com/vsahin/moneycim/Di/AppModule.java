package com.vsahin.moneycim.Di;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.vsahin.moneycim.Model.Database.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Volkan Şahin on 18.08.2017.
 */

@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Application providesApplication() {
        return application;
    }

    @Singleton
    @Provides
    AppDatabase provideAppDatabase(Application application){
        return Room.databaseBuilder(application, AppDatabase.class, "app_database")
                .allowMainThreadQueries().addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        String query = "INSERT INTO SPENDING_GROUP VALUES ('1', 'Gas'), ('2', 'Food'), ('3', 'Clothes')";
                        db.execSQL(query);
                    }

                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                    }
                })
                .build();
    }
}
