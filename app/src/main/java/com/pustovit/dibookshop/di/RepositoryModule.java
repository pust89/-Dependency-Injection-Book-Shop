package com.pustovit.dibookshop.di;

import android.app.Application;

import com.pustovit.dibookshop.model.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {


    @Provides
    @Singleton
    Repository providesRepository(Application application) {
        return new Repository(application);
    }
}
