package com.pustovit.dibookshop.di;

import com.pustovit.dibookshop.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ApplicationModule.class, RepositoryModule.class})
@Singleton
public interface BookComponent {

    void inject(MainActivity mainActivity);
}
