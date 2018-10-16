package com.vsahin.moneycim.Di.Modules;

import android.app.Application;

import com.vsahin.moneycim.Model.Database.AppDatabase;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

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
